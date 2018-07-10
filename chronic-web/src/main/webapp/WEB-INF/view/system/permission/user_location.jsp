<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
    <input type="hidden" name="user_id" value="${param.user_id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">所属经办点</label>
        <div class="layui-input-inline">
          <s:select  name="locid"  list="locationList" listKey="locid" listValue="org_name" headerKey="" headerValue="请选择" lay-verify="required"></s:select>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="grant_location_submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</body>
</html>
<script>
    var form, layer;
    layui.use(['form', 'layer'], function () {
        form = layui.form;
        layer = layui.layer;

        form.on('submit(grant_location_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/user_grantLocation",data.field,function(data) {
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
