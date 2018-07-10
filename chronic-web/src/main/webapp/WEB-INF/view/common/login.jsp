<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    <link rel="stylesheet" href="http://www.chronic.com/static/css/common/login.css"/>
</head>
<body>
<form class="layui-form" method="post" action="/user/userLogin">
    <div class="layadmin-user-login layadmin-user-display-show">
        <div class="layadmin-user-login-main">
            <div class="layadmin-user-login-box layadmin-user-login-header">
                <h2>Chronic CMS</h2>
            </div>
            <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-username"></label>
                    <input type="text" name="user_name" placeholder="用户名" class="layui-input" lay-verify="required">
                </div>
                <div class="layui-form-item">
                    <label class="layadmin-user-login-icon layui-icon layui-icon-password"></label>
                    <input type="password" name="password_md5" placeholder="密码" class="layui-input" lay-verify="required">
                </div>
                <div class="layui-form-item">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="layadmin-user-login-icon layui-icon layui-icon-vercode"></label>
                            <input type="text" maxlength="4" placeholder="图形验证码" class="layui-input" name="verifyCode" lay-verify="required">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img src="http://www.chronic.com/static/code/verifycode.jsp" class="layadmin-user-login-codeimg" id="verify-code" onclick="initVerify()">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 录</button>
                    <s:actionerror style="color:red;padding:10px"/>
                </div>

                <div class="layui-form-item">
                    <span style="color: #b3b3b3; font-size:12px;">还没有账号?
                        <a style="color: #4CAF50;" href="">立即创建</a></span>
                </div>

            </div>
        </div>
    </div>
</form>

</body>
</html>
<script src="http://www.chronic.com/static/js/plugin/quietflow.js"></script>
<script>

    layui.use(['form', 'layer'], function () {
        var form = layui.form;
        var layer = layui.layer;
    });



    $("body").quietflow({
        theme : "bouncingBalls",
        specificColors : [
            "rgba(255, 10, 50, .5)",
            "rgba(10, 255, 50, .5)",
            "rgba(10, 50, 255, .5)",
            "rgba(0, 0, 0, .5)",
        ]
    });

    function initVerify() {
        $("#verify-code").attr('src', 'http://www.chronic.com/static/code/verifycode.jsp?' + Math.floor(Math.random() * 100));
    }

</script>