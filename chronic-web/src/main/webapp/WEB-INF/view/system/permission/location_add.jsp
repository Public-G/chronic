<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
</head>
<body>
<form class="layui-form">
    <div class="layui-form-item" style="margin-top: 15px;">
        <label class="layui-form-label">机构编码</label>
        <div class="layui-input-inline">
            <input type="text" name="org_code" id="org_code" lay-verify="required|orgCodeVerify"
                   placeholder="请输入机构编码" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux" id="tip" ></div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">机构名称</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" autocomplete="off" name="org_name" lay-verify="required">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">所属区域</label>
        <div class="layui-inline">
        <s:select name="area_code" list="areaList" listKey="area_code" listValue="area_name" headerKey="" headerValue="请选择" lay-verify="required"/>
    </div>
    </div>
    

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="location_submit">提交</button>
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

        //机构编码唯一性校验
        $("#org_code").blur(function () {
            var org_code = $("#org_code").val();
            $.getJSON("http://www.chronic.com/permission/location_fieldVerify?org_code=" + org_code, function(data) {
                form.verify({
                    orgCodeVerify : function(value) {
                        if (data.code === code_success) {
                            return '区域编码已存在';
                        }
                    }});
                if (data.code === code_success) {
                    $('#tip').html("<i class='fa fa-frown-o' style='font-size: 16px; color: #FF5722;'><font color='#FF5722'> 区域编码已存在</font></i>");
                } else {
                    $('#tip').html("<i class='fa fa-smile-o' style='font-size: 16px; color: #FFB800;'><font color='#FFB800'> 区域编码可以使用</font></i>");
                }
            });
        });


        form.on('submit(location_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/location_add?forwardType=add",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadLocationList(1); //刷新父页面
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
