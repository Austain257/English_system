(() => {
    const API_BASE = 'http://localhost:8080';
    const STORAGE_KEY = 'learning_tracker_session';
    const MAX_SESSION_LENGTH_MS = 3 * 60 * 60 * 1000; // 3小时兜底

    const SCENE_TITLES = {
        COURSE_SELECT: '课本选择',
        STUDY_PLAN: '学习清单',
        WORD_DICTATION: '单词听写',
        SENTENCE_LEARN: '句子学习',
        KNOWLEDGE_NOTE: '知识积累',
        AI_ARTICLE: 'AI文章生成',
        WORD_RECITE: '单词背诵',
        WRONG_WORD: '错词本常练'
    };
    const BOOK_NAME_STORAGE_KEY = 'learning_tracker_last_book_name';
    const WORD_BANK_STORAGE_KEY = 'learning_tracker_last_word_bank';

    const getBodyDataset = () => {
        if (typeof document === 'undefined' || !document.body) return {};
        return document.body.dataset || {};
    };

    const persistDatasetValue = (key, value) => {
        const body = document.body;
        if (!body) return;
        if (value === undefined || value === null || value === '') {
            body.removeAttribute(`data-${key}`);
        } else {
            body.setAttribute(`data-${key}`, value);
        }
    };

    function buildAuthHeaders() {
        const token = localStorage.getItem('auth_token');
        return token ? {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        } : null;
    }

    function safeFetch(url, options = {}) {
        const headers = buildAuthHeaders();
        if (!headers) {
            console.warn('学习跟踪：缺少认证信息，已跳过调用', url);
            return Promise.resolve(null);
        }
        const fetchOptions = {
            keepalive: Boolean(options.keepalive),
            method: options.method || 'GET',
            headers: { ...headers, ...(options.headers || {}) }
        };
        if (options.body !== undefined) {
            fetchOptions.body = options.body;
        }
        return fetch(url, fetchOptions)
            .then(resp => {
                if (!resp.ok) {
                    throw new Error(`HTTP ${resp.status}`);
                }
                return resp.json();
            })
            .then(result => {
                if (!result || result.code !== 1) {
                    throw new Error(result?.msg || '未知错误');
                }
                return result.data;
            })
            .catch(err => {
                console.error('学习跟踪接口失败：', err);
                return null;
            });
    }

    const formatDuration = (seconds = 0) => {
        if (seconds < 60) return `${seconds}s`;
        if (seconds < 3600) return `${Math.floor(seconds / 60)}m`;
        const hours = (seconds / 3600).toFixed(1);
        return `${hours}h`;
    };

    class LearningTracker {
        static trackPageSession(options = {}) {
            this.scene = options.scene || 'GENERIC';
            this.source = options.source || 'web';
            if (options.autoStart === false) {
                return;
            }

            const persisted = this.restoreSession();
            if (persisted && persisted.scene !== this.scene) {
                this.endSession(persisted);
            }

            if (!persisted || persisted.scene !== this.scene) {
                this.startSession();
            } else {
                this.session = persisted;
                this.touchSession();
            }

            if (!this._persistenceListenersAttached) {
                window.addEventListener('pagehide', () => this.touchSession());
                this._persistenceListenersAttached = true;
            }
        }

        static startSession() {
            if (this.session && this.session.sessionId && this.session.scene === this.scene) {
                return;
            }
            if (this._startingSession) return;

            const payload = {
                scene: this.scene || 'GENERIC',
                source: this.source || 'web'
            };
            this._startingSession = true;
            safeFetch(`${API_BASE}/sessions/start`, {
                method: 'POST',
                body: JSON.stringify(payload)
            })
                .then(data => {
                    if (data) {
                        this.session = {
                            sessionId: data.id,
                            startedAt: Date.now(),
                            scene: payload.scene,
                            source: payload.source
                        };
                        this.saveSession(this.session);
                    }
                })
                .finally(() => {
                    this._startingSession = false;
                });
        }

        static endSession(sessionOverride = null) {
            const active = sessionOverride || this.session;
            if (!active || !active.sessionId) {
                return;
            }
            const now = Date.now();
            const startedAt = active.startedAt || now;
            const duration = Math.max(1, Math.floor((now - startedAt) / 1000));
            const payload = {
                sessionId: active.sessionId,
                durationSeconds: duration
            };

            if (!sessionOverride) {
                this.session = null;
            }
            this.clearSession();

            return safeFetch(`${API_BASE}/sessions/end`, {
                method: 'POST',
                body: JSON.stringify(payload),
                keepalive: true
            });
        }

        static restoreSession() {
            if (this.session && this.session.sessionId) {
                return this.session;
            }
            try {
                const raw = localStorage.getItem(STORAGE_KEY);
                if (!raw) return null;
                const stored = JSON.parse(raw);
                if (!stored || !stored.sessionId) {
                    return null;
                }
                if (Date.now() - (stored.startedAt || 0) > MAX_SESSION_LENGTH_MS) {
                    this.endSession(stored);
                    return null;
                }
                this.session = stored;
                return stored;
            } catch (err) {
                console.warn('学习跟踪：无法恢复会话', err);
                return null;
            }
        }

        static touchSession() {
            if (!this.session || !this.session.sessionId) return;
            this.session.lastSeenAt = Date.now();
            this.saveSession(this.session);
        }

        static saveSession(session) {
            if (!session) return;
            localStorage.setItem(STORAGE_KEY, JSON.stringify(session));
        }

        static clearSession() {
            localStorage.removeItem(STORAGE_KEY);
        }

        static endDanglingSession() {
            const stored = this.restoreSession();
            if (!stored) return;
            this.endSession(stored);
        }

        static setBookContext(context = {}) {
            this._bookContext = { ...(this._bookContext || {}), ...context };
            if (context.bookName) {
                localStorage.setItem(BOOK_NAME_STORAGE_KEY, context.bookName);
                persistDatasetValue('book-name', context.bookName);
            }
            if (context.wordBank) {
                localStorage.setItem(WORD_BANK_STORAGE_KEY, context.wordBank);
                persistDatasetValue('word-bank', context.wordBank);
            }
            if (context.bookId) {
                persistDatasetValue('book-id', context.bookId);
            }
            if (context.bookCode) {
                persistDatasetValue('book-code', context.bookCode);
            }
        }

        static resolveBookContext(overrides = {}) {
            const dataset = getBodyDataset();
            const fallbackBook = localStorage.getItem(BOOK_NAME_STORAGE_KEY);
            const fallbackBank = localStorage.getItem(WORD_BANK_STORAGE_KEY);
            const base = this._bookContext || {};
            return {
                bookId: overrides.bookId ?? base.bookId ?? dataset.bookId ?? null,
                bookName: overrides.bookName ?? base.bookName ?? dataset.bookName ?? fallbackBook ?? null,
                bookCode: overrides.bookCode ?? base.bookCode ?? dataset.bookCode ?? null,
                wordBank: overrides.wordBank ?? base.wordBank ?? dataset.wordBank ?? fallbackBank ?? null
            };
        }

        static prepareMasteryPayload(wordPayload = {}) {
            const context = this.resolveBookContext(wordPayload);
            const payload = {
                bookId: context.bookId,
                bookName: context.bookName,
                bookCode: context.bookCode,
                wordBank: wordPayload.wordBank || context.wordBank || context.bookName || null,
                wordId: wordPayload.wordId,
                wordText: wordPayload.wordText,
                proficiencyScore: wordPayload.proficiencyScore ?? 100,
                mastered: wordPayload.mastered !== false
            };

            if (!payload.wordId && !payload.wordText) {
                console.warn('学习跟踪：缺少单词信息，无法记录掌握状态');
                return null;
            }
            if (!payload.bookId && !payload.bookName) {
                console.warn('学习跟踪：缺少课本信息，无法记录掌握状态');
                return null;
            }
            if (!payload.wordBank) {
                payload.wordBank = payload.bookName;
            }
            return payload;
        }

        static markMastery(wordPayload = {}) {
            const payload = this.prepareMasteryPayload(wordPayload);
            if (!payload) return Promise.resolve(null);
            return safeFetch(`${API_BASE}/mastery/mark`, {
                method: 'POST',
                body: JSON.stringify(payload)
            });
        }

        static regressMastery(wordPayload = {}) {
            const payload = this.prepareMasteryPayload({ ...wordPayload, mastered: false });
            if (!payload) return Promise.resolve(null);
            return safeFetch(`${API_BASE}/mastery/regress`, {
                method: 'POST',
                body: JSON.stringify(payload)
            });
        }

        static fetchDashboard() {
            return safeFetch(`${API_BASE}/learning/dashboard`);
        }

        static formatScene(scene) {
            if (!scene) return 'GENERIC';
            return SCENE_TITLES[scene] || scene;
        }

        static footprintSummary(dashboard, ui = {}) {
            if (!dashboard) return;
            const learningTime = dashboard.learningTime || [];
            const mastery = dashboard.wordMastery || [];

            const aggregate = learningTime.reduce((acc, item) => {
                acc.total += item.totalSeconds || 0;
                acc.today += item.todaySeconds || 0;
                acc.last7 += item.last7DaysSeconds || 0;
                return acc;
            }, { total: 0, today: 0, last7: 0 });

            const masteredCount = mastery.reduce((acc, item) => acc + (item.masteredCount || 0), 0);

            if (ui.todayEl) ui.todayEl.textContent = formatDuration(aggregate.today);
            if (ui.totalEl) ui.totalEl.textContent = formatDuration(aggregate.total);
            if (ui.last7El) ui.last7El.textContent = formatDuration(aggregate.last7);
            if (ui.masteredEl) ui.masteredEl.textContent = masteredCount.toString();

            if (ui.summaryList) {
                ui.summaryList.innerHTML = '';
                learningTime.slice(0, 5).forEach(item => {
                    const li = document.createElement('li');
                    li.textContent = `${this.formatScene(item.scene)} · ${formatDuration(item.totalSeconds || 0)}`;
                    ui.summaryList.appendChild(li);
                });
            }
        }

        static autoTrackSevenFeatures() {
            const pageMeta = document.querySelector('[data-track-scene]');
            if (!pageMeta) {
                this.endDanglingSession();
                return;
            }
            const scene = pageMeta.getAttribute('data-track-scene');
            const source = pageMeta.getAttribute('data-track-source') || 'web';
            this.trackPageSession({ scene, source });
        }
    }

    window.LearningTracker = LearningTracker;

    document.addEventListener('DOMContentLoaded', () => {
        LearningTracker.autoTrackSevenFeatures();
    }, { once: true });
})();
