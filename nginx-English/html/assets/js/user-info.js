/**
 * 通用用户信息组件
 * 用于在页面右上角显示用户信息
 */

let logoutModalReadyPromise = null;

function ensureLogoutModalLoaded() {
    if (window.showLogoutConfirm) {
        return Promise.resolve();
    }

    if (logoutModalReadyPromise) {
        return logoutModalReadyPromise;
    }

    logoutModalReadyPromise = new Promise((resolve, reject) => {
        const existingScript = document.querySelector('script[data-logout-modal="true"]');
        if (existingScript) {
            existingScript.addEventListener('load', () => resolve());
            existingScript.addEventListener('error', reject);
            return;
        }

        const script = document.createElement('script');
        script.src = '/assets/js/logout-modal.js';
        script.async = true;
        script.dataset.logoutModal = 'true';
        script.addEventListener('load', () => resolve());
        script.addEventListener('error', (err) => {
            console.error('加载退出登录弹窗脚本失败:', err);
            reject(err);
        });
        document.head.appendChild(script);
    });

    return logoutModalReadyPromise;
}

class UserInfo {
    constructor(options = {}) {
        this.options = {
            anchorSelector: options.anchorSelector || '[data-user-info-anchor="true"]',
            containerId: options.containerId || 'userInfoContainer',
            position: options.position || 'top-right',
            showOnPages: options.showOnPages || [],
            hideOnPages: options.hideOnPages || ['login.html', 'register.html', 'landing.html'],
            autoInit: options.autoInit !== false,
            ...options
        };

        this.currentUser = null;
        this.authToken = null;
        this.isAuthenticated = false;
        this.anchorElement = null;
        this.usingFloatingWidget = false;

        if (this.options.autoInit) {
            this.init();
        }
    }

    async init() {
        // 检查当前页面是否需要显示用户信息
        if (!this.shouldShowOnCurrentPage()) {
            return;
        }

        // 检查认证状态
        await this.checkAuth();
        
        // 创建用户信息UI
        this.createUserInfoUI();
        
        // 绑定事件
        this.bindEvents();
    }

    shouldShowOnCurrentPage() {
        const currentPage = window.location.pathname.split('/').pop() || 'index.html';
        
        // 如果指定了显示页面列表
        if (this.options.showOnPages.length > 0) {
            return this.options.showOnPages.includes(currentPage);
        }
        
        // 如果指定了隐藏页面列表
        if (this.options.hideOnPages.length > 0) {
            return !this.options.hideOnPages.includes(currentPage);
        }
        
        return true;
    }

    async checkAuth() {
        this.authToken = localStorage.getItem('auth_token');
        
        if (!this.authToken) {
            this.isAuthenticated = false;
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/user/info', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${this.authToken}`,
                    'Content-Type': 'application/json',
                }
            });

            const result = await response.json();
            if (result.code === 1) {
                this.currentUser = result.data;
                this.isAuthenticated = true;
                localStorage.setItem('user_info', JSON.stringify(this.currentUser));
            } else {
                this.handleAuthError();
            }
        } catch (error) {
            console.error('检查认证状态失败:', error);
            this.handleAuthError();
        }
    }

    handleAuthError() {
        this.isAuthenticated = false;
        this.currentUser = null;
        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_info');
    }

    createUserInfoUI() {
        const anchor = document.querySelector(this.options.anchorSelector);
        this.anchorElement = anchor;

        if (anchor) {
            anchor.classList.add('user-info-anchor');
            anchor.innerHTML = this.isAuthenticated ? this.createUserInfoHTML(false) : this.createLoginPromptHTML(false);
            this.usingFloatingWidget = false;
        } else {
            // 回退方案：创建浮动组件
            let container = document.getElementById(this.options.containerId);
            if (!container) {
                container = document.createElement('div');
                container.id = this.options.containerId;
                document.body.appendChild(container);
            }

            container.className = `user-info-widget ${this.options.position}`;
            
            if (!this.isAuthenticated) {
                container.innerHTML = this.createLoginPromptHTML(true);
            } else {
                container.innerHTML = this.createUserInfoHTML(true);
            }

            this.usingFloatingWidget = true;
        }

        this.injectStyles();
    }

    createLoginPromptHTML(isFloating = false) {
        const returnUrl = encodeURIComponent(window.location.pathname + window.location.search);
        return `
            <div class="user-info-card ${isFloating ? 'user-info-card--floating' : ''}">
                <div class="login-prompt">
                    <div class="login-meta">
                        <div class="status-dot"></div>
                        <span class="status-text">未登录</span>
                    </div>
                    <div class="login-actions">
                        <a href="/login?return=${returnUrl}" class="btn-login">立即登录</a>
                        <a href="/register" class="btn-register">注册账号</a>
                    </div>
                </div>
            </div>
        `;
    }

    createUserInfoHTML(isFloating = false) {
        const user = this.currentUser;
        const roleText = user.role === 'ADMIN' ? '管理员' : '学员';
        const avatarUrl = user.avatar || '/assets/images/default-avatar.png';

        return `
            <div class="user-info-card ${isFloating ? 'user-info-card--floating' : ''}">
                <button class="user-info-content" onclick="userInfoWidget.toggleDropdown()">
                    <div class="avatar-wrapper">
                        <img src="${avatarUrl}" alt="用户头像" class="user-avatar">
                        <span class="online-indicator"></span>
                    </div>
                    <div class="user-details">
                        <span class="user-nickname">${user.nickname || user.username}</span>
                        <span class="user-role">${roleText}</span>
                    </div>
                    <i class="fas fa-chevron-down dropdown-arrow" aria-hidden="true"></i>
                </button>
                
                <div class="user-dropdown" id="userDropdown">
                    <div class="dropdown-header">
                        <img src="${avatarUrl}" alt="用户头像" class="dropdown-avatar">
                        <div class="dropdown-user-info">
                            <strong>${user.nickname || user.username}</strong>
                            <span>@${user.username}</span>
                            <small>${roleText}</small>
                        </div>
                    </div>
                    
                    <div class="dropdown-divider"></div>
                    
                    <div class="dropdown-menu">
                        <a href="/profile" class="dropdown-item">
                            <i class="fas fa-user"></i>
                            <div>
                                <strong>个人中心</strong>
                                <p>管理头像、昵称与资料</p>
                            </div>
                        </a>
                        <a href="/wrongbook" class="dropdown-item">
                            <i class="fas fa-book-open"></i>
                            <div>
                                <strong>智能错词本</strong>
                                <p>查看与巩固错词</p>
                            </div>
                        </a>
                        <a href="/index.html?focus=core" class="dropdown-item" data-action="learning-workbench">
                            <i class="fas fa-layer-group"></i>
                            <div>
                                <strong>学习工作台</strong>
                                <p>进入个性化学习内容</p>
                            </div>
                        </a>
                        
                        <div class="dropdown-divider"></div>
                        
                        <button onclick="userInfoWidget.logout()" class="dropdown-item logout-btn">
                            <i class="fas fa-power-off"></i>
                            <span>退出登录</span>
                        </button>
                    </div>
                </div>
            </div>
        `;
    }

    injectStyles() {
        if (document.getElementById('userInfoStyles')) {
            return;
        }

        const styles = `
            <style id="userInfoStyles">
                :root {
                    --ui-primary: #4a6ee0;
                    --ui-secondary: #f4f6fb;
                    --ui-muted: #7b87a1;
                    --ui-text: #1f2d3d;
                }

                .user-info-anchor {
                    display: flex;
                    justify-content: flex-end;
                    align-items: center;
                    min-height: 48px;
                    position: relative;
                    z-index: 50;
                }

                .user-info-card {
                    background: rgba(255, 255, 255, 0.92);
                    border-radius: 16px;
                    border: 1px solid rgba(74, 110, 224, 0.1);
                    box-shadow: 0 10px 40px rgba(29, 52, 120, 0.08);
                    backdrop-filter: blur(8px);
                    position: relative;
                    min-width: 260px;
                    z-index: 100;
                }

                .user-info-card--floating {
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    z-index: 9999;
                }

                .login-prompt {
                    padding: 14px 18px;
                    display: flex;
                    flex-direction: column;
                    gap: 10px;
                }

                .login-meta {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    font-size: 0.9rem;
                    color: var(--ui-muted);
                }

                .status-dot {
                    width: 10px;
                    height: 10px;
                    border-radius: 50%;
                    background: #f37262;
                    box-shadow: 0 0 10px rgba(243, 114, 98, 0.4);
                }

                .login-actions {
                    display: grid;
                    grid-template-columns: repeat(2, minmax(0, 1fr));
                    gap: 10px;
                }

                .btn-login, .btn-register {
                    text-align: center;
                    border-radius: 999px;
                    padding: 10px 0;
                    font-size: 0.9rem;
                    font-weight: 600;
                    border: none;
                    cursor: pointer;
                    text-decoration: none;
                    transition: transform 0.2s ease, box-shadow 0.2s ease;
                }

                .btn-login {
                    background: linear-gradient(135deg, #4a6ee0, #8c6ff7);
                    color: #fff;
                    box-shadow: 0 10px 20px rgba(74, 110, 224, 0.35);
                }

                .btn-register {
                    border: 1px solid rgba(74, 110, 224, 0.25);
                    color: var(--ui-primary);
                    background: #fff;
                }

                .btn-login:hover, .btn-register:hover {
                    transform: translateY(-1px);
                    box-shadow: 0 8px 24px rgba(74, 110, 224, 0.25);
                }

                .user-info-content {
                    width: 100%;
                    border: none;
                    background: transparent;
                    padding: 12px 18px;
                    display: flex;
                    align-items: center;
                    gap: 14px;
                    cursor: pointer;
                    transition: background 0.2s ease;
                }

                .user-info-content:hover {
                    background: rgba(74, 110, 224, 0.07);
                }

                .avatar-wrapper {
                    position: relative;
                }

                .user-avatar {
                    width: 42px;
                    height: 42px;
                    border-radius: 14px;
                    object-fit: cover;
                    border: 2px solid rgba(74, 110, 224, 0.3);
                }

                .online-indicator {
                    position: absolute;
                    bottom: 2px;
                    right: 2px;
                    width: 12px;
                    height: 12px;
                    border-radius: 50%;
                    background: #5dd39e;
                    border: 2px solid #fff;
                }

                .user-details {
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    align-items: flex-start;
                }

                .user-nickname {
                    font-weight: 600;
                    color: var(--ui-text);
                    font-size: 0.95rem;
                }

                .user-role {
                    font-size: 0.78rem;
                    color: var(--ui-muted);
                    letter-spacing: 0.02em;
                }

                .dropdown-arrow {
                    color: var(--ui-muted);
                    transition: transform 0.2s ease;
                }

                .user-info-content.active .dropdown-arrow {
                    transform: rotate(180deg);
                }

                .user-dropdown {
                    position: absolute;
                    top: calc(100% + 8px);
                    right: 0;
                    background: #fff;
                    border-radius: 18px;
                    border: 1px solid rgba(26, 32, 44, 0.05);
                    box-shadow: 0 25px 70px rgba(15, 23, 42, 0.18);
                    min-width: 320px;
                    opacity: 0;
                    visibility: hidden;
                    transform: translateY(-10px);
                    transition: all 0.2s ease;
                    overflow: hidden;
                    z-index: 20;
                }

                .user-dropdown.show {
                    opacity: 1;
                    visibility: visible;
                    transform: translateY(0);
                }

                .dropdown-header {
                    padding: 20px;
                    display: flex;
                    gap: 14px;
                    background: linear-gradient(135deg, rgba(74, 110, 224, 0.08), rgba(140, 111, 247, 0.12));
                }

                .dropdown-avatar {
                    width: 60px;
                    height: 60px;
                    border-radius: 20px;
                    object-fit: cover;
                    border: 2px solid rgba(255, 255, 255, 0.8);
                }

                .dropdown-user-info strong {
                    color: var(--ui-text);
                    font-size: 1.05rem;
                }

                .dropdown-user-info span,
                .dropdown-user-info small {
                    color: var(--ui-muted);
                    font-size: 0.85rem;
                    display: block;
                }

                .dropdown-divider {
                    height: 1px;
                    background: rgba(226, 232, 240, 0.8);
                }

                .dropdown-menu {
                    display: flex;
                    flex-direction: column;
                }

                .dropdown-item {
                    display: flex;
                    gap: 12px;
                    padding: 16px 20px;
                    color: var(--ui-text);
                    text-decoration: none;
                    transition: background 0.2s ease;
                    border: none;
                    background: transparent;
                    width: 100%;
                    text-align: left;
                    cursor: pointer;
                }

                .dropdown-item i {
                    width: 40px;
                    height: 40px;
                    border-radius: 12px;
                    background: rgba(74, 110, 224, 0.1);
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    color: var(--ui-primary);
                }

                .dropdown-item div p {
                    font-size: 0.82rem;
                    color: var(--ui-muted);
                    margin-top: 2px;
                }

                .dropdown-item:hover {
                    background: rgba(74, 110, 224, 0.08);
                }

                .logout-btn {
                    color: #ed5e68;
                }

                .logout-btn i {
                    background: rgba(237, 94, 104, 0.15);
                    color: #ed5e68;
                }

                @media (max-width: 768px) {
                    .user-info-anchor {
                        justify-content: center;
                    }

                    .user-info-card {
                        width: 100%;
                        min-width: auto;
                    }

                    .user-dropdown {
                        left: 0;
                        right: 0;
                        min-width: 0;
                        width: 100%;
                    }
                }
            </style>
        `;

        document.head.insertAdjacentHTML('beforeend', styles);
    }

    bindEvents() {
        // 点击页面其他地方关闭下拉菜单
        document.addEventListener('click', (e) => {
            const userInfoCard = e.target.closest('.user-info-card');
            if (!userInfoCard) {
                this.closeDropdown();
            }
        });

        // ESC键关闭下拉菜单
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape') {
                this.closeDropdown();
            }
        });
    }

    toggleDropdown() {
        const dropdown = document.getElementById('userDropdown');
        const content = document.querySelector('.user-info-content');
        
        if (!dropdown) return;

        const isVisible = dropdown.classList.contains('show');
        
        if (isVisible) {
            this.closeDropdown();
        } else {
            dropdown.classList.add('show');
            content.classList.add('active');
        }
    }

    closeDropdown() {
        const dropdown = document.getElementById('userDropdown');
        const content = document.querySelector('.user-info-content');
        
        if (dropdown) {
            dropdown.classList.remove('show');
        }
        if (content) {
            content.classList.remove('active');
        }
    }

    async logout() {
        try {
            await ensureLogoutModalLoaded();
        } catch (error) {
            console.warn('使用备用confirm: ', error);
        }

        if (typeof window.showLogoutConfirm === 'function') {
            window.showLogoutConfirm(() => this.performLogout());
        } else if (confirm('确定要退出登录吗？')) {
            this.performLogout();
        }
    }

    async performLogout() {
        try {
            if (this.authToken) {
                await fetch('http://localhost:8080/user/logout', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${this.authToken}`,
                        'Content-Type': 'application/json',
                    }
                });
            }
        } catch (error) {
            console.error('退出登录接口调用失败:', error);
        }

        localStorage.removeItem('auth_token');
        localStorage.removeItem('user_info');
        window.location.href = '/login';
    }

    // 刷新用户信息
    async refresh() {
        await this.checkAuth();
        this.createUserInfoUI();
    }

    // 销毁组件
    destroy() {
        const container = document.getElementById(this.options.containerId);
        if (container) {
            container.remove();
        }

        const styles = document.getElementById('userInfoStyles');
        if (styles) {
            styles.remove();
        }
    }
}

// 全局实例
let userInfoWidget = null;

// 自动初始化
document.addEventListener('DOMContentLoaded', function() {
    userInfoWidget = new UserInfo();
});

// 导出供其他脚本使用
if (typeof module !== 'undefined' && module.exports) {
    module.exports = UserInfo;
}
