# 调试登录问题的详细测试脚本

Write-Host "=== 调试登录问题 ===" -ForegroundColor Green

# 测试1: 使用新注册的用户登录
Write-Host "`n1. 测试新注册用户登录..." -ForegroundColor Yellow
$loginData1 = @{
    username = "newuser123"
    password = "password123"
} | ConvertTo-Json -Depth 10

Write-Host "请求数据: $loginData1" -ForegroundColor Cyan

try {
    $response1 = Invoke-RestMethod -Uri "http://localhost:8080/user/login" -Method POST -ContentType "application/json; charset=utf-8" -Body $loginData1
    if ($response1.code -eq 1) {
        Write-Host "✅ 新用户登录成功: Token = $($response1.data.Substring(0, 20))..." -ForegroundColor Green
    } else {
        Write-Host "❌ 新用户登录失败: $($response1.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ 新用户登录请求异常: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "详细错误: $($_.Exception)" -ForegroundColor DarkRed
}

# 测试2: 使用admin用户登录  
Write-Host "`n2. 测试admin用户登录..." -ForegroundColor Yellow
$loginData2 = @{
    username = "admin"
    password = "admin123"
} | ConvertTo-Json -Depth 10

Write-Host "请求数据: $loginData2" -ForegroundColor Cyan

try {
    $response2 = Invoke-RestMethod -Uri "http://localhost:8080/user/login" -Method POST -ContentType "application/json; charset=utf-8" -Body $loginData2
    if ($response2.code -eq 1) {
        Write-Host "✅ admin登录成功: Token = $($response2.data.Substring(0, 20))..." -ForegroundColor Green
        
        # 测试获取用户信息
        Write-Host "`n3. 测试获取admin用户信息..." -ForegroundColor Yellow
        $headers = @{
            "Authorization" = "Bearer $($response2.data)"
            "Content-Type" = "application/json"
        }
        
        try {
            $userInfo = Invoke-RestMethod -Uri "http://localhost:8080/user/info" -Method GET -Headers $headers
            if ($userInfo.code -eq 1) {
                Write-Host "✅ 获取用户信息成功:" -ForegroundColor Green
                Write-Host "   用户名: $($userInfo.data.username)" -ForegroundColor Cyan
                Write-Host "   昵称: $($userInfo.data.nickname)" -ForegroundColor Cyan
                Write-Host "   角色: $($userInfo.data.role)" -ForegroundColor Cyan
                Write-Host "   邮箱: $($userInfo.data.email)" -ForegroundColor Cyan
            }
        } catch {
            Write-Host "❌ 获取用户信息失败: $($_.Exception.Message)" -ForegroundColor Red
        }
    } else {
        Write-Host "❌ admin登录失败: $($response2.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ admin登录请求异常: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "详细错误: $($_.Exception)" -ForegroundColor DarkRed
}

# 测试3: 使用testuser登录
Write-Host "`n4. 测试testuser登录..." -ForegroundColor Yellow
$loginData3 = @{
    username = "testuser"
    password = "test123"
} | ConvertTo-Json -Depth 10

Write-Host "请求数据: $loginData3" -ForegroundColor Cyan

try {
    $response3 = Invoke-RestMethod -Uri "http://localhost:8080/user/login" -Method POST -ContentType "application/json; charset=utf-8" -Body $loginData3
    if ($response3.code -eq 1) {
        Write-Host "✅ testuser登录成功: Token = $($response3.data.Substring(0, 20))..." -ForegroundColor Green
    } else {
        Write-Host "❌ testuser登录失败: $($response3.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ testuser登录请求异常: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "详细错误: $($_.Exception)" -ForegroundColor DarkRed
}

Write-Host "`n=== 调试完成 ===" -ForegroundColor Green
