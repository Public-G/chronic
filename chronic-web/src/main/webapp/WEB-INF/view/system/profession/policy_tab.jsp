<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="policy_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="policy_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="year" placeholder="请选择年份">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="policy_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>
    </div>
</blockquote>

<table class="layui-table" lay-filter="tb_policy" lay-data="{id: 'idPolicy'}">
    <thead>
    <tr>
        <th lay-data="{field:'plid',checkbox:true, fixed:'left'}"></th>
        <th lay-data="{field:'year', width:100, align:'center'}">年度</th>
        <th lay-data="{field:'capline', width:120, align:'center'}">封顶线</th>
        <th lay-data="{field:'scale', width:100, align:'center'}">报销比例</th>
        <th lay-data="{field:'operation', width:100, fixed:'right', align:'center'}">操作</th>
    </tr>
    </thead>
    <tbody id="policy_list_body">
    </tbody>
</table>
<div id="policy_page"></div>

</body>
</html>
<script>
    var form, laypage, layer, table, laydate;
    layui.use(['form', 'layer', 'laypage', 'table', 'laydate'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;
        laydate = layui.laydate;

        //日期选择器
        laydate.render({
            elem: '#year',
            type: 'year',
            theme: 'molv',
            calendar: true
        });

        $("#policy_add").click(function () {
            alertByFull(layer, "添加", "http://www.chronic.com/profession/policy_forward?forwardType=policy_add");
        });

        $("#policy_refresh").click(function () {
            reloadPolicyList(1);
        });

        reloadPolicyList(1);
    });


    function reloadPolicyList(curr) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/profession/policy_list", {
                curr: curr,
                year: $("#year").val()
            }, function (data) {
                if(data.code === code_success){
                    $("#policy_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "policy_page", data.count, data.curr, data.limit, reloadPolicyList); //分页
                    table.init('tb_policy', {
                        height: 'full-190'
                    });

                    //删除
                    $("#policy_delete").click(function () {
                        layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
                            var checkStatusData = table.checkStatus('idPolicy').data; //获取选中行的数据
                            var checkids = new Array();
                            checkStatusData.forEach(function (val, index) { //var:当前元素
                                checkids.push(val.plid);
                            });
                            doDelete(layer, checkids, "http://www.chronic.com/profession/policy_del", reloadPolicyList(1));
                            layer.close(index);
                        });
                    });

                    //编辑
                    $(".update-btn").click(function () {
                        var plid = $(this).attr("update-id");
                        var year = $(this).attr("year");
                        var capline = $(this).attr("capline");
                        var scale = $(this).attr("scale");
                        alertByFull(layer, "编辑", "http://www.chronic.com/profession/policy_forward?forwardType=policy_update&plid=" + plid
                            + "&year=" + year + "&capline=" + capline + "&scale=" + scale);
                    });
                }
            }, "json");
            layer.close(index);
        }, loadingTime);
    }


    function renderTable(data) {
        var dataHtml = '';
        if (data.length !== 0) {
            $.each(data, function () {
                dataHtml += '<tr>'
                    + '<td>' + this.plid + '</td>'
                    + '<td>' + this.year + '</td>'
                    + '<td>' + this.capline + '</td>'
                    + '<td >' + this.scale + '</td>'
                    + '<td >'
                    + '<button class="layui-btn layui-btn-xs update-btn" update-id="'+this.plid+'" year="'+this.year+'" capline="'+this.capline+'" scale="'+this.scale+'"><i class="fa fa-edit"></i>编辑</button>'
                    + '</td>'
                    + '</tr>';
            });
        }
        return dataHtml;
    }



</script>