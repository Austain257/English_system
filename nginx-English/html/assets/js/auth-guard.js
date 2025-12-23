(function () {
    const body = document.body;
    if (!body) {
        return;
    }

    const requireAuth = body.dataset.requireAuth === 'true';
    const redirectIfAuthed = body.dataset.redirectIfAuthed === 'true';
    const token = localStorage.getItem('auth_token');

    if (requireAuth && !token) {
        const returnUrl = encodeURIComponent(window.location.pathname + window.location.search);
        window.location.replace(`/landing?return=${returnUrl}`);
        return;
    }

    if (!requireAuth && redirectIfAuthed && token) {
        // 已登录的用户访问登录/注册/落地页时直接进入功能页
        const fallback = '/function';
        window.location.replace(fallback);
    }
})();
