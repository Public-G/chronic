<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <link rel="stylesheet" href="http://www.chronic.com/static/css/common/admin.css" media="all"/>
    <link rel="stylesheet" href="http://www.chronic.com/static/css/common/admin.css"/>
    <link rel="stylesheet" href="http://www.chronic.com/static/css/common/login.css"/>
    <script src="http://www.chronic.com/static/js/common/admin.js"></script>
</head>
<body>
<form class="layui-form" action="#">
    <div class="layadmin-user-login layadmin-user-display-show">
        <div class="layadmin-user-login-main">
            <div class="layadmin-user-login-box layadmin-user-login-header">
                <h2>慢病报销系统</h2>
            </div>
            <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="username"></label>
                    <input type="text" name="username" id="username" lay-verify="username" placeholder="用户名"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="password"></label>
                    <input type="password" name="password" id="password" lay-verify="password" placeholder="密码"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"></label>
                            <input type="text" maxlength="4" placeholder="图形验证码" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img src="http://www.chronic.com/static/code/verifycode.jsp" class="layadmin-user-login-codeimg"
                                     id="verify-code" onclick="initVerify()">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 录</button>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
