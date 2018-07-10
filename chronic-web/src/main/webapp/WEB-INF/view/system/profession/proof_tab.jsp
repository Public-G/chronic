<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">
        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="username_search" placeholder="请输入参合人员姓名">
        </div>

        <div class="layui-inline">
            <button class="layui-btn" id="proof_refresh">查询</button>
        </div>
    </div>
</blockquote>

<table class="layui-table" lay-filter="tb_proof" >
    <thead>
    <tr>
        <th lay-data="{field:'username', width:100, align:'center'}">姓名</th>
        <th lay-data="{field:'sex', width:60, align:'center'}">性别</th>
        <th lay-data="{field:'identity', width:180, align:'center'}">身份证号</th>
        <th lay-data="{field:'birth', width:120, align:'center'}">出生日期</th>
        <th lay-data="{field:'address', width:280, align:'center'}">家庭住址</th>
        <th lay-data="{field:'cellphone', width:120, align:'center'}">联系电话</th>
        <th lay-data="{field:'join_id', width:200, align:'center'}">参合证号</th>
        <th lay-data="{field:'proof_id', width:100, align:'center'}">慢性病证号</th>
        <th lay-data="{field:'proof_name', width:200, align:'center'}">疾病名称</th>
        <th lay-data="{field:'start_time', width:140, align:'center'}">起始时间</th>
        <th lay-data="{field:'end_time', width:140, align:'center'}">终止时间</th>
        <th lay-data="{field:'operation', width:220, fixed:'right', align:'center'}">操作</th>
    </tr>
    </thead>
    <tbody id="proof_list_body">
    </tbody>
</table>
<div id="proof_page"></div>
</body>
</html>
<script>
    var form, laypage, layer, table;
    layui.use(['form', 'layer', 'laypage', 'table'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        $(".layui-table").hide();

        $("#proof_refresh").click(function () {
            reloadProofList(1);
        });

    });


    function reloadProofList(page) {
        var username = $("#username_search").val();
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/profession/proof_list", {
                curr: page,
                username: username
            }, function (data) {
                if (data.code === code_success) {
                    $(".layui-table").show();
                    $("#proof_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "proof_page", data.count, data.curr, data.limit, reloadProofList); //分页
                    table.init('tb_proof', {
                        height: 'full-190'
                    });

                    $(".add-btn").click(function () {
                        var pfid = $(this).attr("proof-id");
                        var isProof = $(this).attr("is-proof");
                        if (isProof === "null") {
                            alertByFull(layer, "添加慢性病证", "http://www.chronic.com/profession/proof_forward?forwardType=proof_add&pfid=" + pfid);
                        } else {
                            layer.msg('已添加', {
                                time: loadingTime5h, //0.5秒关闭（如果不配置，默认是3秒）
                                icon: 5
                            });
                        }
                    });

                    $(".edit-btn").click(function () {
                        var pfid = $(this).attr("proof-id");
                        var isProof = $(this).attr("is-proof");
                        if (isProof !== "null") {
                            alertByFull(layer, "修改慢性病证", "http://www.chronic.com/profession/proof_forward?forwardType=proof_update&pfid=" + pfid);
                        } else {
                            layer.msg('未添加', {
                                time: loadingTime5h, //0.5秒关闭（如果不配置，默认是3秒）
                                icon: 5
                            });
                        }

                    });

                    $(".del-btn").click(function () {
                        var pfid = $(this).attr("proof-id");
                        var isProof = $(this).attr("is-proof");
                        if (isProof !== "null") {
                            layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
                                $.post("http://www.chronic.com/profession/proof_del", {
                                    pfid: pfid
                                }, function (data) {
                                    if (data.code === code_success) {
                                        layer.msg(data.msg,{
                                            time: loadingTime5h
                                        });
                                        setTimeout(function () {
                                            reloadProofList(1);
                                        }, loadingTime5h);
                                    } else {
                                        layer.msg(data.msg);
                                    }
                                });
                                layer.close(index);
                            });
                        } else {
                            layer.msg('未添加', {
                                time: loadingTime5h, //0.5秒关闭（如果不配置，默认是3秒）
                                icon: 5
                            });
                        }
                    });

                }
                layer.close(index);
            }, "json");
        }, loadingTime);
    }


    function renderTable(data) {
        var dataHtml = '';
        if (data.length !== 0) {
            $.each(data, function () {
                dataHtml += '<tr>'
                    + '<td>' + this[0] + '</td>'
                    + '<td >' + this[1] + '</td>'
                    + '<td >' + this[2] + '</td>'
                    + '<td >' + new Date(this[3]).format("yyyy-MM-dd") + '</td>'
                    + '<td >' + this[4] + '</td>'
                    + '<td >' + this[5] + '</td>'
                    + '<td>' + this[7] + '</td>'
                    + '<td >' + ( this[8] === null ? "" : this[8] ) + '</td>'
                    + '<td >' + ( this[9]  === null ? "" : this[9] ) + '</td>'
                    + '<td >' + ( this[10]  === null ? "" : new Date(this[10] ).format("yyyy-MM-dd")) + '</td>'
                    + '<td >' + ( this[11]  === null ? "" : new Date(this[11] ).format("yyyy-MM-dd")) + '</td>'
                    + '<td >'
                    + '<button class="layui-btn layui-btn-xs add-btn" proof-id="' + this[6] + '" is-proof = "' + this[8] + '"><i class="fa fa-check-circle"></i>添加</button>'
                    + '<button class="layui-btn layui-btn-normal layui-btn-xs edit-btn" proof-id="'+this[6]+'" is-proof = "' + this[8] + '" ><i class="fa fa-pencil-square"></i>修改</button>'
                    + '<button class="layui-btn layui-btn-danger layui-btn-xs del-btn" proof-id="' + this[6] + '" is-proof = "' + this[8] + '"><i class="fa fa-times-circle"></i>删除</button>'
                    + '</td>'
                    + '</tr>';
            });
        }
        return dataHtml;
    }

</script>
