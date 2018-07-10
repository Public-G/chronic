<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
</head>
<body>
<div class="layui-container">
    <div class="layui-form-item">
        <label class="layui-form-label">身份证号:</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="identity">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn" onclick="initReimburse()">查询</button>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var form, layer;
    layui.use(['form', 'layer'], function () {
        form = layui.form;
        layer = layui.layer;

    });


    function initReimburse(){
        $.post("http://www.chronic.com/profession/reimburse_search", {
            identity: $("#identity").val()
        }, function (data) {
            if(data.code === code_success){
                var fid = (data.data)[0];
                var identity = (data.data)[1];
                var username =  (data.data)[2];
                var join_id =  (data.data)[3];
                alertByFull(layer, "慢性病报销", "http://www.chronic.com/profession/reimburse_forward?forwardType=reimburse_add&fid=" + fid + "&identity=" + identity
                    + "&username=" + username + "&join_id=" + join_id);
            } else {
                layer.alert(data.msg, {
                    skin: 'layui-layer-molv' //样式类名
                    ,closeBtn: 0
                });
            }
        }, "json");
    }



</script>
