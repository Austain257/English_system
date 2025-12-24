(function () {
    class LogoutConfirmModal {
        constructor() {
            this.modal = null;
            this.confirmBtn = null;
            this.cancelBtn = null;
            this.closeBtn = null;
            this.titleEl = null;
            this.messageEl = null;
            this.detailEl = null;
            this.pendingAction = null;
            this.isProcessing = false;
            this.createModal();
        }

        createModal() {
            if (document.getElementById('logoutConfirmModal')) {
                this.modal = document.getElementById('logoutConfirmModal');
                this.cacheElements();
                return;
            }

            const backdrop = document.createElement('div');
            backdrop.id = 'logoutConfirmModal';
            backdrop.className = 'logout-confirm-backdrop';
            backdrop.innerHTML = `
                <div class="logout-confirm-card" role="dialog" aria-modal="true">
                    <button class="logout-close-btn" aria-label="关闭提示">&times;</button>
                    <div class="logout-icon-wrap">
                        <span class="logout-icon-glow"></span>
                        <i class="fas fa-power-off"></i>
                    </div>
                    <h3 class="logout-title">退出登录</h3>
                    <p class="logout-message">确定要退出当前账号吗？</p>
                    <p class="logout-detail">我们会安全保存您的学习数据，随时可再次登录继续学习。</p>
                    <div class="logout-actions">
                        <button class="logout-btn ghost" data-action="cancel">暂不退出</button>
                        <button class="logout-btn danger" data-action="confirm">
                            <span class="btn-label">确认退出</span>
                            <span class="btn-spinner"><i class="fas fa-spinner"></i></span>
                        </button>
                    </div>
                </div>
            `;

            document.body.appendChild(backdrop);
            this.modal = backdrop;
            this.cacheElements();
            this.bindEvents();
            this.injectStyles();
        }

        cacheElements() {
            this.confirmBtn = this.modal.querySelector('[data-action="confirm"]');
            this.cancelBtn = this.modal.querySelector('[data-action="cancel"]');
            this.closeBtn = this.modal.querySelector('.logout-close-btn');
            this.titleEl = this.modal.querySelector('.logout-title');
            this.messageEl = this.modal.querySelector('.logout-message');
            this.detailEl = this.modal.querySelector('.logout-detail');
        }

        bindEvents() {
            this.confirmBtn.addEventListener('click', () => this.handleConfirm());
            this.cancelBtn.addEventListener('click', () => this.close());
            this.closeBtn.addEventListener('click', () => this.close());
            this.modal.addEventListener('click', (event) => {
                if (event.target === this.modal) {
                    this.close();
                }
            });
            document.addEventListener('keydown', (event) => {
                if (!this.modal.classList.contains('show')) return;
                if (event.key === 'Escape') {
                    this.close();
                }
            });
        }

        injectStyles() {
            if (document.getElementById('logoutConfirmStyles')) return;

            const style = document.createElement('style');
            style.id = 'logoutConfirmStyles';
            style.textContent = `
                .logout-confirm-backdrop {
                    position: fixed;
                    inset: 0;
                    backdrop-filter: blur(6px);
                    background: rgba(5, 9, 27, 0.65);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    z-index: 9999;
                    opacity: 0;
                    visibility: hidden;
                    transition: opacity 0.25s ease, visibility 0.25s ease;
                    padding: 20px;
                }
                .logout-confirm-backdrop.show {
                    opacity: 1;
                    visibility: visible;
                }
                .logout-confirm-card {
                    width: min(420px, 100%);
                    border-radius: 28px;
                    background: radial-gradient(circle at top, rgba(86, 64, 214, 0.25), rgba(16, 22, 54, 0.95));
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    box-shadow: 0 35px 80px rgba(4, 8, 32, 0.55);
                    padding: 38px 32px 30px;
                    color: #f5f6ff;
                    position: relative;
                    text-align: center;
                }
                .logout-close-btn {
                    position: absolute;
                    top: 18px;
                    right: 18px;
                    width: 36px;
                    height: 36px;
                    border-radius: 12px;
                    border: none;
                    background: rgba(255, 255, 255, 0.08);
                    color: #fff;
                    font-size: 1.2rem;
                    cursor: pointer;
                    transition: background 0.2s ease;
                }
                .logout-close-btn:hover {
                    background: rgba(255, 255, 255, 0.18);
                }
                .logout-icon-wrap {
                    width: 84px;
                    height: 84px;
                    margin: 0 auto 18px;
                    border-radius: 22px;
                    background: rgba(255, 255, 255, 0.08);
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    position: relative;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    color: #ff7b8a;
                    font-size: 1.8rem;
                    overflow: hidden;
                }
                .logout-icon-glow {
                    position: absolute;
                    width: 120%;
                    height: 120%;
                    background: radial-gradient(circle, rgba(255, 122, 140, 0.35), transparent 70%);
                    animation: pulseGlow 2.4s ease-in-out infinite;
                }
                .logout-icon-wrap i {
                    position: relative;
                }
                .logout-title {
                    font-size: 1.5rem;
                    margin-bottom: 10px;
                    letter-spacing: 0.05em;
                }
                .logout-message {
                    font-size: 1rem;
                    color: rgba(255, 255, 255, 0.85);
                    margin-bottom: 6px;
                }
                .logout-detail {
                    font-size: 0.9rem;
                    color: rgba(255, 255, 255, 0.65);
                    margin-bottom: 24px;
                }
                .logout-actions {
                    display: flex;
                    gap: 12px;
                    flex-wrap: wrap;
                    justify-content: center;
                }
                .logout-btn {
                    min-width: 140px;
                    padding: 12px 22px;
                    border-radius: 16px;
                    border: 1px solid transparent;
                    font-size: 0.95rem;
                    font-weight: 600;
                    cursor: pointer;
                    transition: transform 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
                    display: inline-flex;
                    align-items: center;
                    justify-content: center;
                    gap: 8px;
                }
                .logout-btn.ghost {
                    background: rgba(255, 255, 255, 0.08);
                    color: rgba(255, 255, 255, 0.9);
                    border-color: rgba(255, 255, 255, 0.2);
                }
                .logout-btn.danger {
                    background: linear-gradient(135deg, #ff8ea1, #ff586b);
                    color: #fff;
                    box-shadow: 0 18px 32px rgba(255, 88, 107, 0.4);
                }
                .logout-btn:hover {
                    transform: translateY(-1px);
                }
                .logout-btn:disabled {
                    opacity: 0.7;
                    cursor: not-allowed;
                    transform: none !important;
                }
                .btn-spinner {
                    display: none;
                    font-size: 0.9rem;
                }
                .btn-spinner i {
                    animation: spin 1s linear infinite;
                }
                .logout-btn.loading .btn-label {
                    display: none;
                }
                .logout-btn.loading .btn-spinner {
                    display: inline-flex;
                }
                @keyframes pulseGlow {
                    0% { transform: scale(0.9); opacity: 0.6; }
                    50% { transform: scale(1.05); opacity: 1; }
                    100% { transform: scale(0.9); opacity: 0.6; }
                }
                @keyframes spin {
                    0% { transform: rotate(0deg); }
                    100% { transform: rotate(360deg); }
                }
                @media (max-width: 480px) {
                    .logout-confirm-card {
                        padding: 32px 22px 26px;
                    }
                    .logout-actions {
                        flex-direction: column;
                    }
                    .logout-btn {
                        width: 100%;
                    }
                }
            `;
            document.head.appendChild(style);
        }

        open(options = {}) {
            const {
                title = '退出登录',
                message = '确定要退出当前账号吗？',
                detail = '我们会安全保存您的学习数据，随时可再次登录继续学习。',
                confirmText = '确认退出',
                cancelText = '暂不退出',
                onConfirm = null
            } = options;

            this.titleEl.textContent = title;
            this.messageEl.textContent = message;
            this.detailEl.textContent = detail;
            this.confirmBtn.querySelector('.btn-label').textContent = confirmText;
            this.cancelBtn.textContent = cancelText;
            this.pendingAction = typeof onConfirm === 'function' ? onConfirm : null;

            this.modal.classList.add('show');
            this.modal.focus();
        }

        close() {
            if (this.isProcessing) return;
            this.modal.classList.remove('show');
            this.pendingAction = null;
        }

        async handleConfirm() {
            if (!this.pendingAction || this.isProcessing) {
                return;
            }

            try {
                this.isProcessing = true;
                this.confirmBtn.classList.add('loading');
                await this.pendingAction();
            } catch (error) {
                console.error('Logout confirm action failed:', error);
            } finally {
                this.isProcessing = false;
                this.confirmBtn.classList.remove('loading');
                this.close();
            }
        }
    }

    let modalInstance = null;

    function getLogoutModal() {
        if (!modalInstance) {
            modalInstance = new LogoutConfirmModal();
        }
        return modalInstance;
    }

    window.showLogoutConfirm = function (onConfirm, options = {}) {
        const modal = getLogoutModal();
        modal.open({ ...options, onConfirm });
    };
})();
