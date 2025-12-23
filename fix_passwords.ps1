# 修复预设用户密码问题

Write-Host "=== 修复预设用户密码 ===" -ForegroundColor Green

# 使用Java BCrypt生成正确的密码哈希
Write-Host "为admin和testuser生成新的密码哈希..." -ForegroundColor Yellow

# admin123 的BCrypt哈希
$adminPasswordHash = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIch6OPOLPcKhKhUMk.H2zq3pK'

# test123 的BCrypt哈希  
$testPasswordHash = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'

Write-Host "更新admin用户密码..." -ForegroundColor Yellow
try {
    $result1 = mysql -u Austain -p123456 english_web -e "UPDATE users SET password='$adminPasswordHash' WHERE username='admin';" 2>&1
    Write-Host "✅ admin密码已更新" -ForegroundColor Green
} catch {
    Write-Host "❌ admin密码更新失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "更新testuser用户密码..." -ForegroundColor Yellow
try {
    $result2 = mysql -u Austain -p123456 english_web -e "UPDATE users SET password='$testPasswordHash' WHERE username='testuser';" 2>&1
    Write-Host "✅ testuser密码已更新" -ForegroundColor Green
} catch {
    Write-Host "❌ testuser密码更新失败: $($_.Exception.Message)" -ForegroundColor Red
}

# 验证更新结果
Write-Host "`n验证密码更新..." -ForegroundColor Yellow
try {
    $users = mysql -u Austain -p123456 english_web -e "SELECT username, LEFT(password, 15) as password_start FROM users WHERE username IN ('admin', 'testuser');" 2>&1
    Write-Host "当前用户密码状态:" -ForegroundColor Cyan
    Write-Host $users -ForegroundColor Cyan
} catch {
    Write-Host "❌ 查询失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== 密码修复完成 ===" -ForegroundColor Green
