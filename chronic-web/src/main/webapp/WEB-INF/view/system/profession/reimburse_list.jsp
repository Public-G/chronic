<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
    <link rel="stylesheet" href="http://www.chronic.com/static/ztree/zTreeStyle.css" media="all"/>
    <script src="http://www.chronic.com/static/ztree/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
<blockquote class="layui-elem-quote">
    <div class="layui-form" id="divForm">

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="reimburse_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>

        <div class="layui-inline">
            <input type="text" id="areaSel" class="layui-input" readonly value="" autocomplete="off" placeholder="请选择区域" onclick="showMenu(); return false;">
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="username_search" placeholder="请输入姓名">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="reimburse_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>

        <div id="areaContent" class="areaContent" style="display:none;">
            <ul id="areaTree" class="ztree" style="margin-top:0; width:160px;"></ul>
        </div>

    </div>
</blockquote>

<table id="reimburseId" class="layui-table" lay-filter="tb_reimburse" >
    <thead>
    <tr>
        <th lay-data="{field:'rid',checkbox:true, fixed:'left'}"></th>
        <th lay-data="{field:'reimburse_id', width:280, align:'center'}">报销单号</th>
        <th lay-data="{field:'identity', width:180, align:'center'}">身份证号</th>
        <th lay-data="{field:'username', width:100, align:'center'}">姓名</th>
        <th lay-data="{field:'join_id', width:200, align:'center'}">参合证号</th>
        <th lay-data="{field:'proof_id', width:120, align:'center'}">慢性病证号</th>
        <th lay-data="{field:'proof_name', width:120, align:'center'}">疾病名称</th>
        <th lay-data="{field:'medical_name', width:160, align:'center'}">医疗机构名称</th>
        <th lay-data="{field:'medical_invoice', width:160, align:'center'}">医院发票号</th>
        <th lay-data="{field:'medical_expenses', width:100, align:'center'}">医疗费用</th>
        <th lay-data="{field:'medical_time', width:120, align:'center'}">就诊时间</th>
        <th lay-data="{field:'reimburse_amount', width:100, align:'center'}">报销金额</th>
        <th lay-data="{field:'reimburse_time', width:180, align:'center'}">报销时间</th>
        <th lay-data="{field:'org_name', width:200, align:'center'}">经办机构</th>
        <th lay-data="{field:'job_id', width:120, align:'center'}">操作人工号</th>
        <th lay-data="{field:'ip', width:140, align:'center'}">操作IP</th>
    </tr>
    </thead>
    <tbody id="reimburse_list_body">
    </tbody>
</table>
<div id="reimburse_page"></div>

</body>
</html>
<script>
    var form, laypage, layer, table;
    var areaCode;
    layui.use(['form', 'layer', 'laypage', 'element', 'table'], function () {
        form = layui.form;
        layer = layui.layer;
        laypage = layui.laypage;
        table = layui.table;

        $("table").hide();

        $("#reimburse_refresh").click(function () {
            reloadReimburseList(1);
            var areaObj = $("#areaSel");
            areaObj.attr("value", "");
        });
        initAreaTree();
    });

    function reloadReimburseList(page) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/profession/reimburse_list", {
                curr: page,
                area_code:areaCode,
                username: $("#username_search").val()
            }, function (data) {
                areaCode = null; //清空全局变量，搜索不到后再搜索时不带条件
                console.log(data)
                if (data.code === code_success) {
                    $("table").show();
                    $("#reimburse_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "reimburse_page", data.count, data.curr, data.limit, reloadReimburseList); //分页
                    table.init('tb_reimburse', {
                        height: 'full-190'
                    });

                    $("#reimburse_delete").click(function () {
                        layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
                            var checkStatusData = table.checkStatus('reimburseId').data; //获取选中行的数据
                            var checkids = new Array();
                            checkStatusData.forEach(function (val, index) { //var:当前元素
                                checkids.push(val.rid);
                            });
                            alert(checkids);
                            doDelete(layer, checkids, "http://www.chronic.com/profession/reimburse_del", reloadReimburseList(1));
                            layer.close(index);
                        });
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
                    + '<td>' + this.rid + '</td>'
                    + '<td>' + this.reimburse_id + '</td>'
                    + '<td>' + this.identity + '</td>'
                    + '<td >' + this.username + '</td>'
                    + '<td >' + this.join_id + '</td>'
                    + '<td >' + this.proof_id + '</td>'
                    + '<td >' + this.proof_name + '</td>'
                    + '<td >' + this.medical_name + '</td>'
                    + '<td >' + this.medical_invoice + '</td>'
                    + '<td >' + this.medical_expenses + '元</td>'
                    + '<td >' + new Date(this.medical_time).format("yyyy-MM-dd")+ '</td>'
                    + '<td >' + this.reimburse_amount +  '元</td>'
                    + '<td >' + new Date(this.reimburse_time).format("yyyy-MM-dd hh:mm:ss") + '</td>'
                    + '<td >' + this.org_name + '</td>'
                    + '<td >' + this.job_id + '</td>'
                    + '<td >' + this.ip + '</td>'
                    + '<td >'
                    + '</td>'
                    + '</tr>';
            });
        }
        return dataHtml;
    }

    function initAreaTree() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "area_code",
                    pIdKey: "pid"
                },
                key: {
                    name: "area_name"
                }
            },
            callback: {
                onClick: onClick  //用于捕获节点被点击的事件回调函数，如果设置了 setting.callback.beforeClick 方法，且返回 false，将无法触发 onClick 事件回调函数。
            }
        };
        //查询行政区域
        $.getJSON("http://www.chronic.com/permission/area_treeList", function (data) {
            var nodes = data.data;
            $.fn.zTree.init($("#areaTree"), setting, nodes);
        });
    }

    function onClick(e, treeId, treeNode) {
        ////有了这个方法，用户不再需要自己设定全局变量来保存 zTree 初始化后得到的对象了，
        //而且在所有回调函数中全都会返回 treeId 属性，用户可以随时使用此方法获取需要进行操作的 zTree 对象
        var zTree = $.fn.zTree.getZTreeObj("areaTree"),
            nodes = zTree.getSelectedNodes(), //获取 zTree 当前被选中的节点数据集合
            v = "";

        /*nodes.sort(function compare(a,b){return a.id-b.id;});*/
        for (var i=0, l=nodes.length; i<l; i++) {
            areaCode = nodes[i].area_code;
            v += nodes[i].area_name + ",";
        }
        if (v.length > 0 ) v = v.substring(0, v.length-1);
        var areaObj = $("#areaSel");
        areaObj.attr("value", v);
    }

    function showMenu() {
        var areaObj = $("#areaSel");
        var cityOffset = $("#areaSel").offset(); //获取匹配元素在当前视口的相对偏移。返回的对象包含两个整型属性：top 和 left。此方法只对可见元素有效。

        //slideDown：通过高度变化（向下增大）来动态地显示所有匹配的元素，在显示完成后可选地触发一个回调函数。
        $("#areaContent").css({left:cityOffset.left + "px", top:cityOffset.top + areaObj.outerHeight() + "px"}).slideDown("fast");

        //mousedown事件在鼠标在元素上点击后会触发
        $("body").bind("mousedown", onBodyDown); //为每个匹配元素的特定事件绑定事件处理函数。
    }

    function hideMenu() {
        $("#areaContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown); //从每一个匹配的元素中删除绑定的事件。
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "areaContent" || $(event.target).parents("#areaContent").length>0)) {
            hideMenu();
        }
    }
</script>