<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="area_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="area_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="area_name_search" placeholder="请输入区域名关键字">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="area_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>
    </div>
</blockquote>
<table class="layui-table" lay-skin="line">
    <thead>
    <tr>
        <th><input type="checkbox" class="check_all_btn"></th>
        <th>区域编码</th>
        <th>上级区域编码</th>
        <th>区域名称</th>
        <th>级别</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="area_list_body"></tbody>
</table>

<div id="area_page"></div>
</body>
</html>
<script>
    var form, laypage, layer, table;
    layui.use(['form', 'layer', 'laypage', 'table'], function() {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        reloadAreaList(1);

        $("#area_add").click(function () {
            alertByFull(layer, "添加", "http://www.chronic.com/permission/area_add?forwardType=addSelect");
        });

        $("#area_refresh").click(function () {
            reloadAreaList(1);
        });

    });

    function reloadAreaList(curr) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/permission/area_list", {
                curr: curr,
                area_name: $("#area_name_search").val()
            }, function (data) {
                if(data.code === code_success){
                    $("#area_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "area_page", data.count, data.curr, data.limit, reloadAreaList); //分页
                } else{
                    layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                }

                checkall_reverse($(".check_all_btn"), $(".check_btn")); //全选

                //删除
                $("#area_delete").click(function(){
                    layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index) {
                        var checkids = "";
                        $(".check_btn:checked").each(function(){
                            //取出自定义的id属性
                            checkids += $(this).attr("del_id")+",";
                        });
                        doDelete(layer, checkids, "http://www.chronic.com/permission/area_del", reloadAreaList(1));
                        layer.close(index);
                    });
                });

                //编辑
                $(".update-btn").click(function () {
                    var aid = $(this).attr("area-id");
                    var $td =  $(this).parent().siblings();
                    var area_code = $td.eq(1).text();
                    var area_name = $td.eq(3).text();
                    alertByFull(layer, "编辑", "http://www.chronic.com/permission/area_update?forwardType=addSelect&aid=" + aid
                        + "&area_code=" + area_code + "&area_name=" + area_name);
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
                    +'<td><input type="checkbox" class="check_btn " del_id="'+this.aid+'"></td>'
                    +'<td>'+this.area_code+'</td>'
                    +'<td>'+this.pid+'</td>'
                    +'<td>'+this.area_name+'</td>'
                    +'<td>'+this.level+'</td>'
                    +'<td >'
                    +'<button class="layui-btn layui-btn-xs update-btn" area-id="'+this.aid+'"><i class="fa fa-edit"></i>编辑</button>'
                    +'</td>'
                    +'</tr>';
            });
        } else{
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }

</script>