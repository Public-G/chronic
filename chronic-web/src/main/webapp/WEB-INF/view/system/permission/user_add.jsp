<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
</head>
<body>

<form class="layui-form">

    <div class="layui-form-item" style="margin-top: 15px;">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="text" name="user_name"
                   id="user_name" lay-verify="required|usernameVerify"
                   placeholder="请输入用户名" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="tip"></div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="text" name="password_md5"
                   lay-verify="required" placeholder="请输入密码"
                   autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-inline">
            <input type="text" name="cellphone" id="cellphone"
                   lay-verify="required|phone|phoneVerify" placeholder="请输入手机号"
                   autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="phone-tip"></div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline">
            <input type="text" name="email"
                   lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="user_submit">提交</button>
        </div>
    </div>
</form>

</body>
</html>
<script>
    layui.use([ 'form', 'layer' ],function() {
        var form = layui.form, layer = layui.layer;

        //用户名唯一性校验
        $("#user_name").blur(function () {
              var user_name = $("#user_name").val();
            $.getJSON("http://www.chronic.com/permission/user_fieldVerify?user_name=" + user_name, function(data) {

                form.verify({
                    usernameVerify : function(value) {
                        if (data.code === code_success) {
                            return '用户名已存在！';
                        }
                    }});
                if (data.code === code_success) {
                    $('#tip').html("<i class='fa fa-frown-o' style='font-size: 16px; color: #FF5722;'><font color='#FF5722'> 用户名已存在</font></i>");
                } else {
                    $('#tip').html("<i class='fa fa-smile-o' style='font-size: 16px; color: #FFB800;'><font color='#FFB800'> 用户名可以使用</font></i>");
                }
            });
        });

        //手机号唯一性校验
        $("#cellphone").blur(function () {
            var cellphone = $("#cellphone").val();
            $.getJSON("http://www.chronic.com/permission/user_fieldVerify?cellphone=" + cellphone, function(data) {

                form.verify({
                    phoneVerify : function(value) {
                        if (data.code === code_success) {
                            return '手机号已存在！';
                        }
                    }});
                if (data.code === code_success) {
                    $('#phone-tip').html("<i class='fa fa-frown-o' style='font-size: 16px; color: #FF5722;'><font color='#FF5722'> 手机号已存在</font></i>");
                } else {
                    $('#phone-tip').html("<i class='fa fa-smile-o' style='font-size: 16px; color: #FFB800;'><font color='#FFB800'> 手机号可以使用</font></i>");
                }
            });
        });


        form.on('submit(user_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/user_add",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadUserList(1); //刷新父页面
                        }, loadingTime5h);
                    } else {
                        layer.msg(data.msg, function () {
                            //关闭后的操作
                            submitBtn.removeClass("layui-btn-disabled");
                        });
                    }
                }, "json");
            }
            return false;
        });
    });
</script>
