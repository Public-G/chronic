<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <select id="role_name_select" lay-filter="roleSelect">
            </select>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="grant-btn">
                <i class="fa fa-user-plus"></i> 分配
            </button>
        </div>

    </div>
</blockquote>

<table class="layui-table" lay-skin="line">
    <thead>
    <tr>
        <th>角色名称</th>
        <th>角色标识</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="role_list_body">
    </tbody>
</table>
<div id="role_page"></div>

</body>
</html>
<script>
    var form, laypage, layer;
    layui.use(['form', 'layer', 'laypage'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;

        reloadRoleList(1);

        form.on('select(roleSelect)', function (data) {
            $("#grant-btn").click(function () {
                $.post("http://www.chronic.com/permission/user_grantRole", {
                    user_id: '${user_id}',
                    role_id: data.value //得到被选中的值
                }, function (data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg, {time: 500});
                        setTimeout(function () {
                            document.location.reload();
                           /* reloadRoleList(1);*/
                        }, loadingTime5h);
                    } else {
                        layer.msg(data.msg);
                    }
                }, "json");
            });
        });
    });


    function reloadRoleList(curr) {
        var index = layer.load(2);
        setTimeout(function () {
            $.post("http://www.chronic.com/permission/user_role", {
                curr: curr,
                user_id: '${user_id}'
            }, function (data) {
                if (data.code === code_success) {
                    $("#role_name_select").html(renderSelect(data.data[1])); //解析select选择框
                    form.render('select'); //刷新select选择框渲染
                    $("#role_list_body").html(renderTable(data.data[0])); //解析表格
                    renderPage(laypage, "role_page", data.count, data.curr, data.limit, reloadRoleList);

                    //取消分配
                    $(".revoke-role-btn").click(function () {
                        var role_id = $(this).attr("role_id");
                        layer.confirm('确认取消已分配角色?', {icon: 3, title: '取消分配'}, function (index) {
                            $.post("http://www.chronic.com/permission/user_revokeRole", {
                                user_id: '${user_id}',
                                role_id: role_id
                            }, function (data) {
                                if (data.code === code_success) {
                                    layer.msg(data.msg, {time: 500});
                                    setTimeout(function () {
                                        reloadRoleList(1);
                                    }, loadingTime5h);
                                } else {
                                    layer.msg(data.msg);
                                }
                            }, "json");
                            layer.close(index);
                        });
                    });

                } else {
                    layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                }
                layer.close(index);
            }, "json");
        }, loadingTime);
    }

    function renderSelect(data) {
        var selectHtml = '<option value="">请选择角色</option>';
        if (data.length !== 0) {
            $.each(data, function () {
                selectHtml += '<option value="' + this.role_id + '">' + this.role_name + '</option>';
            });

        } else {
            selectHtml = '<option value="">暂无数据</option>';
        }
        return selectHtml;
    }

    function renderTable(data) {
        var dataHtml = '';
        if (data.length !== 0) {
            $.each(data, function () {
                dataHtml += '<tr>'
                    + '<td>' + this.role_name + '</td>'
                    + '<td>' + this.role_label + '</td>'
                    + '<td >'
                    + '<button class="layui-btn layui-btn-xs layui-btn-danger revoke-role-btn" role_id="' + this.role_id + '"><i class="fa fa-user-times"></i>取消角色</button>'
                    + '</td>'
                    + '</tr>';
            });
        } else {
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }
</script>