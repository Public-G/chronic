<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
<div class="layui-form-item" style="margin-top: 15px;">
    <label class="layui-form-label">区域编码</label>
    <div class="layui-input-inline">
        <input type="text" id="area_code" name="area_code" autocomplete="off" class="layui-input" lay-verify="required|areaCodeVerify">
    </div>
    <div class="layui-form-mid layui-word-aux" id="tip"></div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">区域名称</label>
    <div class="layui-input-inline">
        <input type="text" class="layui-input" autocomplete="off" name="area_name" lay-verify="required">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">上级区域</label>
    <div class="layui-inline">
        <s:select name="pid" list="areaList" listKey="area_code" listValue="area_name" headerKey="0" headerValue="默认为顶级区域"/>
    </div>
</div>

    <div class="layui-form-item">
        <label class="layui-form-label">级别</label>
        <div class="layui-inline">
            <select id="level" name="level" lay-verify="required">
                <option value="">请选择</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
            </select>
            <div class="layui-form-mid layui-word-aux" id="level-tip">1:县 2:乡镇 3:村委 4:村</div>
        </div>
    </div>

<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit lay-filter="area_submit">立即提交</button>
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

        //区域编码唯一性校验
        $("#area_code").blur(function () {
            var area_code = $("#area_code").val();
            $.getJSON("http://www.chronic.com/permission/area_fieldVerify?area_code=" + area_code, function(data) {
                form.verify({
                    areaCodeVerify : function(value) {
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


        form.on('submit(area_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/area_add?forwardType=add",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadAreaList(1); //刷新父页面
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
