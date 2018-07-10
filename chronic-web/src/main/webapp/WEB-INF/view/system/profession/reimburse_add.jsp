<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
    <input type="hidden" id="fid" name="fid" value="${param.fid}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="username" value="${param.username}" autocomplete="off" readonly class="layui-input"
                   lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
        <label class="layui-form-label">身份证号</label>
        <div class="layui-input-inline">
            <input type="text" name="identity" id="identity" value="${param.identity}" readonly autocomplete="off" class="layui-input"
                   lay-verify="required|identity">
        </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">参合证号</label>
            <div class="layui-input-inline">
                <input type="text" name="join_id" value="${param.join_id}" readonly autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">医院名称</label>
            <div class="layui-input-inline">
                <input type="text" name="medical_name" placeholder="请输入医疗机构名称" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">医院发票号</label>
            <div class="layui-input-inline">
                <input type="text" name="medical_invoice" placeholder="请输入医院发票号" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">医疗费用</label>
        <div class="layui-input-inline">
            <input type="text" name="medical_expenses" id="medical_expenses" autocomplete="off" placeholder="请输入医疗费用" class="layui-input" lay-verify="required|number">
        </div>
        <div class="layui-form-mid layui-word-aux">精确点小数点后一位</div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">就诊时间</label>
        <div class="layui-input-inline">
            <input type="text" name="medical_time" id="medical_time" placeholder="请输入选择就诊时间" autocomplete="off" class="layui-input"
                   lay-verify="required">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">慢性病号</label>
            <div class="layui-input-inline">
                <input type="text" id="proof_id" name="proof_id" placeholder="请输入慢性病号" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">疾病名称</label>
            <div class="layui-input-inline">
                <input type="text" id="proof_name" name="proof_name" placeholder="请输入疾病名称" autocomplete="off" class="layui-input"
                       lay-verify="required">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button id="check-btn" class="layui-btn" lay-submit="" lay-filter="reimburse_submit">校验合法性</button>
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
            elem: '#medical_time',
            type: 'date',
            theme: 'molv',
            calendar: true
        });

        form.on('submit(reimburse_submit)',function(data) {
           var data_field = data.field;
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.ajax({
                    url: 'http://www.chronic.com/profession/reimburse_check',
                    type: 'post',
                    traditional: true,
                    data: {
                        fid:$("#fid").val(),
                        proof_id:$("#proof_id").val(),
                        proof_name:$("#proof_name").val(),
                        identity:$("#identity").val(),
                        medical_time:$("#medical_time").val(),
                        medical_expenses:$("#medical_expenses").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        submitBtn.removeClass("layui-btn-disabled");
                        if(data.code === code_success){
                            layer.confirm(data.msg, {icon: 3, title: '确认报销'}, function (confirm_index) {
                                $.post("http://www.chronic.com/profession/reimburse_add?reimburse_amount=" + data.data, data_field, function(data) {
                                    if (data.code === code_success) {
                                        layer.msg(data.msg);
                                        setTimeout(function() {
                                            parent.layer.close(index); ///关闭
                                        }, loadingTime5h);
                                    }
                                }, "json");
                                layer.close(confirm_index);
                            });
                        } else if(data.code === code_fail){
                            layer.confirm(data.msg, {icon: 3, title: '确认退出'}, function (confirm_index) {
                                parent.layer.close(index); ///关闭
                                layer.close(confirm_index);
                            });
                        }else{
                            layer.msg("系统正忙，请稍后重试", function () {
                                //关闭后的操作
                                /*$("#check-btn").text("校验合法性");
                                submitBtn.removeClass("layui-btn-disabled");*/
                            });
                        }
                    }
                });

            }
            return false;
        });

    });
</script>
