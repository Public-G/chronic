<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="user_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="user_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="user_name_search" placeholder="请输入用户名">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="user_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>
    </div>
</blockquote>

<table class="layui-table">
    <thead>
        <th><input type="checkbox" class="check_all_btn"></th>
        <th>用户名</th>
        <th>工号</th>
        <th>所属机构</th>
        <th>联系电话</th>
        <th>邮箱</th>
        <th>创建时间</th>
        <th>操作</th>
    </thead>
    <tbody id="user_list_body"></tbody>
</table>
<div id="user_page"></div>
</body>
</html>
<script>
    var form, laypage, layer, table;
    layui.use(['form', 'layer', 'laypage', 'table'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        reloadUserList(1);

        $("#user_add").click(function () {
            alertByFull(layer, "添加", "http://www.chronic.com/permission/user_forward?forwardType=user_add");
        });

        $("#user_refresh").click(function () {
            reloadUserList(1);
        });
    });

    function reloadUserList(curr) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/permission/user_list", {
                curr: curr,
                user_name: $("#user_name_search").val()
            }, function (data) {
                if (data.code === code_success) {
                    $("#user_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "user_page", data.count, data.curr, data.limit, reloadUserList); //分页
                } else {
                    layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                }
                checkall_reverse($(".check_all_btn"), $(".check_btn")); //全选

                //删除
                $("#user_delete").click(function () {
                    layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
                        var checkids = "";
                        $(".check_btn:checked").each(function () {
                            //取出自定义的id属性
                            checkids += $(this).attr("del_id") + ",";
                        });
                        doDelete(layer, checkids, "http://www.chronic.com/permission/user_del", reloadUserList(1));
                        layer.close(index);
                    });
                });

                //编辑
                $(".update-btn").click(function () {
                    var $td = $(this).parent().siblings();
                    var user_id = $(this).attr("user-id");
                    var user_name = $td.eq(1).text();
                    var cellphone = $td.eq(4).text();
                    var email = $td.eq(5).text();
                    alertByFull(layer, "编辑", "http://www.chronic.com/permission/user_forward?forwardType=user_update&user_id=" + user_id + "&user_name=" + user_name
                        + "&cellphone=" + cellphone + "&email=" + email);
                });

                //重置密码
                $(".reset-pwd-btn").click(function () {
                    var user_id = $(this).attr("user-id");
                    layer.confirm('确认重置密码?', {icon: 3, title: '重置密码'}, function (index) {
                        $.post("http://www.chronic.com/permission/user_resetPwd", {
                            user_id: user_id
                        }, function (data) {
                            if (data.code === 200) {
                                layer.msg(data.msg, {time: loadingTime5h});
                                setTimeout(function () {
                                    reloadUserList(1);
                                }, loadingTime5h);
                            }else{
                                layer.msg(data.msg, {time: loadingTime1k});
                                setTimeout(function () {
                                    reloadUserList(1);
                                }, loadingTime1k);
                            }
                        }, "json");
                        layer.close(index);
                    });
                });

                //分配角色
                $(".grant-role-btn").click(function () {
                    var user_id = $(this).attr("user-id");
                    var $td = $(this).parent().siblings();
                    var user_name = $td.eq(1).text();
                    alertByFull(layer, "分配角色(" + user_name + ")", "http://www.chronic.com/permission/user_forward?forwardType=user_role&user_id=" + user_id);
                });

                //分配经办点
                $(".grant-location-btn").click(function () {
                    var user_id = $(this).attr("user-id");
                    var $td = $(this).parent().siblings();
                    var user_name = $td.eq(1).text();
                    alertByFull(layer, "分配经办点(" + user_name + ")", "http://www.chronic.com/permission/user_forward?forwardType=user_location&user_id=" + user_id);
                });

            }, "json");
            layer.close(index);
        }, loadingTime);
    }

    function renderTable(data) {
        var dataHtml = '';
        if (data.length !== 0) {
            $.each(data, function () {
                dataHtml += '<tr>'
                    + '<td><input type="checkbox" class="check_btn " del_id="' + this.user_id + '"></td>'
                    + '<td>' + this.user_name + '</td>'
                    + '<td>' + (this.job_id == null ? "" : this.job_id) + '</td>'
                    + '<td>' + (this.org_name == null ? "" : this.org_name) + '</td>'
                    + '<td >' + this.cellphone + '</td>'
                    + '<td >' + this.email + '</td>'
                    + '<td >' + new Date(this.create_time).format("yyyy-MM-dd") + '</td>'
                    + '<td >'
                    + '<button class="layui-btn layui-btn-xs update-btn" user-id="' + this.user_id + '"><i class="fa fa-edit"></i>编辑</button>'
                    + '<button class="layui-btn layui-btn-xs reset-pwd-btn" user-id="' + this.user_id + '"><i class="fa fa-retweet"></i>重置密码</button>'
                    + '<button class="layui-btn layui-btn-xs grant-role-btn" user-id="' + this.user_id + '"><i class="fa fa-user-plus"></i>分配角色</button>'
                    + '<button class="layui-btn layui-btn-xs grant-location-btn" user-id="' + this.user_id + '"><i class="fa fa-map-marker"></i>分配机构</button>'
                    + '</td>'
                    + '</tr>';
            });
        } else {
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }
</script>
