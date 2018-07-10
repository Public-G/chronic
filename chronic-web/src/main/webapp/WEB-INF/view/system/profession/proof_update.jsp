<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
    <input type="hidden" name="pfid" value="${param.pfid}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">疾病名称</label>
        <div class="layui-input-inline">
            <input type="text" name="proof_name" value="<s:property value="proof.proof_name"/>" autocomplete="off" placeholder="请输入疾病名称" class="layui-input"
                   lay-verify="required">
        </div>
    </div>


    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">起始时间</label>
            <div class="layui-input-inline">
                <input type="text" name="start_time" id="start_time" value="<s:date name="proof.start_time" format="yyyy-MM-dd"/>" autocomplete="off" placeholder="请选择起始时间" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">终止时间</label>
            <div class="layui-input-inline">
                <input type="text" name="end_time" id="end_time"  value="<s:date name="proof.end_time" format="yyyy-MM-dd"/>" autocomplete="off" placeholder="请选择终止时间" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="proof_submit">立即提交</button>
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
            elem: '#start_time',
            type: 'date',
            theme: 'molv',
            calendar: true
        });

        //日期选择器
        laydate.render({
            elem: '#end_time',
            type: 'date',
            theme: 'molv',
            calendar: true
        });

        form.on('submit(proof_submit)',function(data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/profession/proof_update",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadProofList(1); //刷新父页面
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
