<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form">
    <input type="hidden" name="role_id" value="${param.role_id}"/>
    <div class="layui-form-item" style="margin-top: 15px;">
        <label class="layui-form-label">角色名</label>
        <div class="layui-input-inline">
            <input type="text" name="role_name" value="${param.role_name}" id="role_name" lay-verify="required|rolenameVerify"
                   placeholder="请输入角色名称" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="tip"></div>
    </div>


    <div class="layui-form-item" style="margin-top: 15px;">
        <label class="layui-form-label">角色标识</label>
        <div class="layui-input-inline">
            <input type="text" name="role_label" value="${param.role_label}" id="role_label" lay-verify="required|roleLabelVerify"
                   placeholder="请输入角色标识" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="roleLabel-tip"></div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="role_submit">提交</button>
        </div>
    </div>
</form>
</body>
</html>
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;

        //校验角色名称
        $("#role_name").blur(function () {
            var role_name = $("#role_name").val();
            $.getJSON("http://www.chronic.com/permission/role_fieldVerify?role_name=" + role_name, function (data) {
                form.verify({
                    rolenameVerify: function (value) {
                        if (data.code === code_success) {
                            return '角色已存在！';
                        }
                    }
                });
                if (data.code === code_success) {
                    $('#tip').html("<i class='fa fa-frown-o' style='font-size: 16px; color: #FF5722;'><font color='#FF5722'> 角色已存在</font></i>");
                } else {
                    $('#tip').html("<i class='fa fa-smile-o' style='font-size: 16px; color: #FFB800;'><font color='#FFB800'> 角色名称可以使用</font></i>");
                }
            });
        });


        //校验角色标识
        $("#role_label").blur(function () {
            var role_label = $("#role_label").val();
            $.getJSON("http://www.chronic.com/permission/role_fieldVerify?role_label=" + role_label, function (data) {
                form.verify({
                    roleLabelVerify: function (value) {
                        if (data.code === code_success) {
                            return '角色标识已存在';
                        }
                    }
                });
                if (data.code === code_success) {
                    $('#roleLabel-tip').html("<i class='fa fa-frown-o' style='font-size: 16px; color: #FF5722;'><font color='#FF5722'> 角色标识已存在</font></i>");
                } else {
                    $('#roleLabel-tip').html("<i class='fa fa-smile-o' style='font-size: 16px; color: #FFB800;'><font color='#FFB800'> 角色标识可以使用</font></i>");
                }
            });
        });

        form.on('submit(role_submit)', function (data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); //得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/role_update", data.field, function (data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function () {
                            parent.layer.close(index); ////关闭
                            parent.reloadRoleList(1); //刷新父页面
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