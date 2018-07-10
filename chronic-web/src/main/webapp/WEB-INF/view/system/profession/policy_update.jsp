<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">

    <input type="hidden" name="plid" value="${param.plid}"/>

    <div class="layui-form-item">
        <label class="layui-form-label">封顶线</label>
        <div class="layui-input-inline">
            <input type="text" name="capline" value="${param.capline}" autocomplete="off" placeholder="请输入金额" class="layui-input" lay-verify="required|number">
        </div>
        <div class="layui-form-mid layui-word-aux">精确点小数点后一位</div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">报销比例</label>
        <div class="layui-input-inline">
            <input type="text" name="scale" value="${param.scale}" autocomplete="off" placeholder="请输入比例"
                   class="layui-input" lay-verify="required|number">
        </div>
        <div class="layui-form-mid layui-word-aux">精确点小数点后两位</div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">年份</label>
        <div class="layui-input-inline">
            <input type="text" name="year" value="${param.year}" autocomplete="off" id="year" placeholder="请选择年份" class="layui-input"
                   lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="policy_submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</body>
</html>
<script>
    var form, layer, laydate;
    layui.use(['form', 'layer', 'laydate'], function () {
        form = layui.form;
        layer = layui.layer;
        laydate = layui.laydate;

        //日期选择器
        laydate.render({
            elem: '#year',
            type: 'year',
            theme: 'molv',
            calendar: true
        });

        form.on('submit(policy_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/profession/policy_update",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadPolicyList(1); //刷新父页面
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
