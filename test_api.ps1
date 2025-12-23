# 测试登录和注册API的PowerShell脚本

Write-Host "=== 英语学习系统 API 测试 ===" -ForegroundColor Green

# 1. 测试用户名检查接口
Write-Host "`n1. 测试用户名检查接口..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/user/check-username?username=newuser123" -Method GET
    $result = $response.Content | ConvertFrom-Json
    Write-Host "✅ 用户名检查成功: $($result.data)" -ForegroundColor Green
} catch {
    Write-Host "❌ 用户名检查失败: $($_.Exception.Message)" -ForegroundColor Red
}

# 2. 测试注册功能
Write-Host "`n2. 测试注册功能..." -ForegroundColor Yellow
$registerData = @{
    username = "newuser123"
    password = "password123"
    confirmPassword = "password123"
    email = "newuser@test.com"
    nickname = "新用户123"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/user/register" -Method POST -ContentType "application/json" -Body $registerData
    if ($response.code -eq 1) {
        Write-Host "✅ 注册成功: $($response.data)" -ForegroundColor Green
    } else {
        Write-Host "❌ 注册失败: $($response.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ 注册请求失败: $($_.Exception.Message)" -ForegroundColor Red
}

# 3. 测试登录功能（使用现有admin账户）
Write-Host "`n3. 测试登录功能..." -ForegroundColor Yellow
$loginData = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/user/login" -Method POST -ContentType "application/json" -Body $loginData
    if ($response.code -eq 1) {
        $token = $response.data
        Write-Host "✅ 登录成功，Token: $($token.Substring(0, 20))..." -ForegroundColor Green
        
        # 4. 测试获取用户信息
        Write-Host "`n4. 测试获取用户信息..." -ForegroundColor Yellow
        try {
            $headers = @{
                "Authorization" = "Bearer $token"
                "Content-Type" = "application/json"
            }
            $userInfo = Invoke-RestMethod -Uri "http://localhost:8080/user/info" -Method GET -Headers $headers
            if ($userInfo.code -eq 1) {
                Write-Host "✅ 获取用户信息成功:" -ForegroundColor Green
                Write-Host "   用户名: $($userInfo.data.username)" -ForegroundColor Cyan
                Write-Host "   昵称: $($userInfo.data.nickname)" -ForegroundColor Cyan
                Write-Host "   角色: $($userInfo.data.role)" -ForegroundColor Cyan
                Write-Host "   邮箱: $($userInfo.data.email)" -ForegroundColor Cyan
            } else {
                Write-Host "❌ 获取用户信息失败: $($userInfo.message)" -ForegroundColor Red
            }
        } catch {
            Write-Host "❌ 获取用户信息请求失败: $($_.Exception.Message)" -ForegroundColor Red
        }
    } else {
        Write-Host "❌ 登录失败: $($response.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ 登录请求失败: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== 测试完成 ===" -ForegroundColor Green
