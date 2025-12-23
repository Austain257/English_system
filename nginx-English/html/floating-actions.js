// 可收起的浮动按钮组件JavaScript
let isFabOpen = false;

// 浮动按钮功能
function toggleFab() {
    const fabMain = document.getElementById('fabMain');
    const fabSubs = document.querySelectorAll('.fab-sub');
    const fabOverlay = document.getElementById('fabOverlay');
    
    if (!isFabOpen) {
        // 打开浮动按钮
        fabMain.classList.add('active');
        fabOverlay.classList.add('show');
        
        fabSubs.forEach((sub, index) => {
            setTimeout(() => {
                sub.style.display = 'flex';
                setTimeout(() => {
                    sub.classList.add('show');
                }, 10);
            }, index * 100);
        });
        
        isFabOpen = true;
    } else {
        closeFab();
    }
}

function closeFab() {
    if (!isFabOpen) return;
    
    const fabMain = document.getElementById('fabMain');
    const fabSubs = document.querySelectorAll('.fab-sub');
    const fabOverlay = document.getElementById('fabOverlay');
    
    // 关闭浮动按钮
    fabMain.classList.remove('active');
    fabOverlay.classList.remove('show');
    
    fabSubs.forEach((sub) => {
        sub.classList.remove('show');
        setTimeout(() => {
            sub.style.display = 'none';
        }, 300);
    });
    
    isFabOpen = false;
}

// 滚动到顶部
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

// 导航函数
function navigateToPage(path) {
    window.location.href = path;
}

// 初始化浮动按钮
function initFloatingActions() {
    // 添加键盘快捷键
    document.addEventListener('keydown', function(e) {
        // ESC: 关闭浮动按钮
        if (e.key === 'Escape') {
            closeFab();
        }
        // Alt + S: 学习记录
        if (e.altKey && e.key === 's') {
            navigateToPage('/allrecord');
        }
        // Alt + J: 知识积累
        if (e.altKey && e.key === 'j') {
            navigateToPage('/jotting');
        }
        // Alt + H: 返回首页
        if (e.altKey && e.key === 'h') {
            navigateToPage('/');
        }
    });

    // 点击页面其他区域关闭浮动按钮
    document.addEventListener('click', function(e) {
        const floatingActions = document.querySelector('.floating-actions');
        if (isFabOpen && floatingActions && !floatingActions.contains(e.target)) {
            closeFab();
        }
    });
}

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    initFloatingActions();
});

// 创建浮动按钮HTML结构
function createFloatingActions() {
    const floatingActionsHTML = `
        <!-- 可收起的浮动按钮组件 -->
        <div class="fab-overlay" id="fabOverlay" onclick="closeFab()"></div>
        <div class="floating-actions" id="floatingActions">
            <div class="fab-container">
                <!-- 子按钮 -->
                <button class="fab fab-sub" onclick="navigateToPage('/allrecord')" title="学习记录">
                    <i class="fas fa-chart-line"></i>
                </button>
                <button class="fab fab-sub" onclick="navigateToPage('/jotting')" title="知识积累">
                    <i class="fas fa-book-open"></i>
                </button>
                <button class="fab fab-sub" onclick="scrollToTop()" title="返回顶部">
                    <i class="fas fa-arrow-up"></i>
                </button>
                
                <!-- 主按钮 -->
                <button class="fab fab-main" id="fabMain" onclick="toggleFab()" title="快捷功能">
                    <i class="fas fa-plus"></i>
                </button>
            </div>
        </div>
    `;
    
    return floatingActionsHTML;
}

// 动态插入浮动按钮到页面
function insertFloatingActions() {
    // 检查是否已经存在浮动按钮
    if (document.querySelector('.floating-actions')) {
        return;
    }
    
    // 在body结束前插入浮动按钮
    document.body.insertAdjacentHTML('beforeend', createFloatingActions());
}

// 自动插入浮动按钮（可选）
// insertFloatingActions();
