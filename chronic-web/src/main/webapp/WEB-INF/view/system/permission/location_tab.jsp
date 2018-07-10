<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="location_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="location_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="location_name_search" placeholder="请输入机构关键字">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="location_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>
    </div>
</blockquote>


<table class="layui-table">
    <thead>
    <tr>
        <th><input type="checkbox" class="check_all_btn"></th>
        <th>机构编码</th>
        <th>区域编码</th>
        <th>机构名称</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="location_list_body"></tbody>
</table>

<div id="location_page"></div>
</body>
</html>
<script>
    var form, laypage, layer, table;
    var ids = '';
    layui.use(['form', 'layer', 'laypage', 'table'], function() {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        reloadLocationList(1);

        $("#location_add").click(function () {
            alertByFull(layer, "添加", "http://www.chronic.com/permission/location_add?forwardType=addSelect");
        });

        $("#location_refresh").click(function () {
            reloadLocationList(1);
        });

    });

    function reloadLocationList(curr) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/permission/location_list", {
                curr: curr,
                org_name: $("#location_name_search").val()
            }, function (data) {
                if(data.code === code_success){
                    $("#location_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "location_page", data.count, data.curr, data.limit, reloadLocationList); //分页
                } else{
                    layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                }

                checkall_reverse($(".check_all_btn"), $(".check_btn")); //全选

                //删除
                $("#location_delete").click(function(){
                    layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index) {
                        var checkids = "";
                        $(".check_btn:checked").each(function(){
                            //取出自定义的id属性
                            checkids += $(this).attr("del_id")+",";
                        });
                        doDelete(layer, checkids, "http://www.chronic.com/permission/location_del", reloadLocationList(1));
                        layer.close(index);
                    });
                });

                //编辑
                $(".update-btn").click(function () {
                    var locid = $(this).attr("locid");
                    var $td =  $(this).parent().siblings();
                    var org_code = $td.eq(1).text();
                    var org_name = $td.eq(3).text();
                    alertByFull(layer, "编辑", "http://www.chronic.com/permission/location_update?forwardType=updateSelect&locid=" + locid
                    + "&org_code=" + org_code + "&org_name=" + org_name);
                });
            }, "json");
            layer.close(index);
        }, loadingTime);
    }


    /*<input type="checkbox" class="check_btn " del_id="'+this.locid+'">*/
    function renderTable(data){
        var dataHtml = '';
        if(data.length !== 0){
            $.each(data, function(){
                dataHtml += '<tr>'
                    +'<td><input type="checkbox" class="check_btn " del_id="'+this.locid+'"></td>'
                    +'<td>'+this.org_code+'</td>'
                    +'<td>'+this.area_code+'</td>'
                    +'<td>'+this.org_name+'</td>'
                    +'<td >'
                    +'<button class="layui-btn layui-btn-xs update-btn" locid="'+this.locid+'"><i class="fa fa-edit"></i>编辑</button>'
                    +'</td>'
                    +'</tr>';
            });
        } else{
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }

</script>
