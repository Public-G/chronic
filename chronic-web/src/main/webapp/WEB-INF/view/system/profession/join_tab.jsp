<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">
        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="username_search" placeholder="请输入户主姓名">
        </div>

        <div class="layui-inline">
            <button class="layui-btn" id="join_refresh">查询</button>
        </div>
    </div>
</blockquote>

<table class="layui-table" lay-filter="tb_join">
    <thead>
    <tr>
        <th lay-data="{field:'family_id', width:180, align:'center'}">家庭编号</th>
        <th lay-data="{field:'username', width:100, align:'center'}">姓名</th>
        <th lay-data="{field:'sex', width:60, align:'center'}">性别</th>
        <th lay-data="{field:'identity', width:180, align:'center'}">身份证号</th>
        <th lay-data="{field:'birth', width:120, align:'center'}">出生日期</th>
        <th lay-data="{field:'address', width:280, align:'center'}">家庭住址</th>
        <th lay-data="{field:'cellphone', width:120, align:'center'}">联系电话</th>
        <th lay-data="{field:'relation', width:120, align:'center'}">与户主关系</th>
        <th lay-data="{field:'join_id', width:200, align:'center'}">参合证号</th>
        <th lay-data="{field:'invoice', width:200, align:'center'}">参合发票号</th>
        <th lay-data="{field:'create_time', width:160, align:'center'}">参合时间</th>
        <th lay-data="{field:'operation', width:200, fixed:'right', align:'center'}">操作</th>
    </tr>
    </thead>
    <tbody id="join_list_body">
    </tbody>
</table>
<div id="join_page"></div>

</body>
</html>
<script>
    var form, laypage, layer, table;
    layui.use(['form', 'layer', 'laypage', 'table'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        $("table").hide();

        $("#join_refresh").click(function () {
            reloadJoinList(1);
        });
    });

    function reloadJoinList(page) {
        var username = $("#username_search").val();
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/profession/join_list", {
                curr: page,
                username: username
            }, function (data) {
                if (data.code === code_success) {
                    $("table").show();
                    $("#join_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "join_page", data.count, data.curr, data.limit, reloadJoinList); //分页
                    table.init('tb_join', {
                        height: 'full-190'
                    });

                    $(".add-btn").click(function () {
                        var jid = $(this).attr("join-id");
                        var isJoin = $(this).attr("is-join");
                        if (isJoin === "null") {
                            layer.confirm('确认参合?', {icon: 3, title: '提示'}, function (index) {
                                $.post("http://www.chronic.com/profession/join_add", {
                                    jid: jid
                                }, function (data) {
                                    if (data.code === code_success) {
                                        layer.msg(data.msg,{
                                            time: loadingTime5h
                                        });
                                        setTimeout(function () {
                                            reloadJoinList(1);
                                        }, loadingTime5h);
                                    } else {
                                        layer.msg(data.msg);
                                    }
                                });
                                layer.close(index);
                            });
                        } else {
                            layer.msg('已参合', {
                                time: loadingTime5h, //0.5秒关闭（如果不配置，默认是3秒）
                                icon: 5
                            });
                        }
                    });


                    $(".del-btn").click(function () {
                        var jid = $(this).attr("join-id");
                        var isJoin = $(this).attr("is-join");
                        if (isJoin !== "null") {
                            layer.confirm('确认取消参合?', {icon: 3, title: '提示'}, function (index) {
                                $.post("http://www.chronic.com/profession/join_del", {
                                    jid: jid
                                }, function (data) {
                                    if (data.code === code_success) {
                                        layer.msg(data.msg,{
                                            time: loadingTime5h
                                        });
                                        setTimeout(function () {
                                            reloadJoinList(1);
                                        }, loadingTime5h);
                                    } else {
                                        layer.msg(data.msg);
                                    }
                                });
                                layer.close(index);
                            });
                        } else {
                            layer.msg('未参合', {
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
                    + '<td>' + this[1] + '</td>'
                    + '<td >' + this[2] + '</td>'
                    + '<td >' + this[3] + '</td>'
                    + '<td >' + new Date(this[4]).format("yyyy-MM-dd") + '</td>'
                    + '<td >' + this[5] + '</td>'
                    + '<td >' + this[6] + '</td>'
                    + '<td >' + ( this[7] === null ? "" : this[7]) + '</td>'
                    + '<td >' + ( this[9] === null ? "" : this[9]) + '</td>'
                    + '<td >' + ( this[10] === null ? "" : this[10]) + '</td>'
                    + '<td >' + ( this[11] === null ? "" : new Date(this[11]).format("yyyy-MM-dd hh:mm:ss")) + '</td>'
                    + '<td >'
                    + '<button class="layui-btn layui-btn-xs add-btn" join-id="' + this[8] + '" is-join = "' + this[9] + '"><i class="fa fa-check"></i>参合</button>'
                    + '<button class="layui-btn  layui-btn-danger layui-btn-xs del-btn" join-id="' + this[8] + '" is-join = "' + this[9] + '"><i class="fa fa-times"></i>取消参合</button>'
                    + '</td>'
                    + '</tr>';
            });
        }
        return dataHtml;
    }

</script>