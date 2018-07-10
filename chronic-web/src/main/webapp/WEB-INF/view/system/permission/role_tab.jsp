<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="role_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="role_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="role_name_search" placeholder="请输入角色名称">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="role_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>
    </div>
</blockquote>

<table class="layui-table" lay-skin="line" align="center">
    <thead>
    <th><input type="checkbox" class="check_all_btn"></th>
    <th>角色名称</th>
    <th>角色标识</th>
    <th>操作</th>
    </thead>
    <tbody id="role_list_body"></tbody>
</table>
<div id="role_page"></div>

</body>
</html>
<script>
    var form, laypage, layer, table;
    layui.use(['form', 'layer', 'laypage'], function() {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;

        reloadRoleList(1);

        $("#role_add").click(function () {
            alertByFull(layer, "添加", "http://www.chronic.com/permission/role_forward?forwardType=role_add");
        });

        $("#role_refresh").click(function () {
            reloadRoleList(1);
        });

    });

    function reloadRoleList(curr) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/permission/role_list", {
                curr: curr,
                role_name: $("#role_name_search").val()
            }, function (data) {
                if(data.code === code_success){
                    $("#role_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "role_page", data.count, data.curr, data.limit, reloadRoleList); //分页
                } else{
                    layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                }

                checkall_reverse($(".check_all_btn"), $(".check_btn")); //全选
                
                //删除
                $("#role_delete").click(function(){
                    layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index) {
                        var checkids = "";
                        $(".check_btn:checked").each(function(){
                            //取出自定义的id属性
                            checkids += $(this).attr("del_id")+",";
                        });
                        doDelete(layer, checkids, "http://www.chronic.com/permission/role_del", reloadRoleList(1));
                        layer.close(index);
                    });
                });

                //编辑
                $(".update-btn").click(function () {
                    var role_id = $(this).attr("role-id");
                    var $td =  $(this).parent().prevAll();
                    var role_name = $td.eq(1).text();
                    var role_label = $td.eq(0).text();
                    alertByFull(layer, "编辑", "http://www.chronic.com/permission/role_forward?forwardType=role_update&role_id=" + role_id
                        + "&role_name=" + role_name + "&role_label=" + role_label);
                });

                //分配菜单
                $(".grant-menu-btn").click(function () {
                    var role_id = $(this).attr("role-id");
                    alertByFull(layer, "分配菜单", "http://www.chronic.com/permission/role_forward?forwardType=role_menu&role_id=" + role_id);
                });
            }, "json");
            layer.close(index);
        }, loadingTime);
    }

    function renderTable(data){
        var dataHtml = '';
        if(data.length !== 0){
            $.each(data, function(){
                dataHtml += '<tr>'
                    +'<td><input type="checkbox" class="check_btn " del_id="'+this.role_id+'"></td>'
                    +'<td>'+this.role_name+'</td>'
                    +'<td>'+this.role_label+'</td>'
                    +'<td >'
                    +'<button class="layui-btn layui-btn-xs update-btn" role-id="'+this.role_id+'"><i class="fa fa-edit"></i>编辑</button>'
                    +'<button class="layui-btn layui-btn-xs grant-menu-btn" role-id="'+this.role_id+'"><i class="fa fa-sitemap"></i>分配菜单</button>'
                    +'</td>'
                    +'</tr>';
            });
        } else{
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }
</script>

