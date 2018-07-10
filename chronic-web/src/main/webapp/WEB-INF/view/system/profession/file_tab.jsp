<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
    <link rel="stylesheet" href="http://www.chronic.com/static/ztree/zTreeStyle.css" media="all"/>
    <script src="http://www.chronic.com/static/ztree/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
<span class="layui-breadcrumb">

    <c:if test="${pid eq 0}">
        <a><cite>顶级</cite></a>
    </c:if>

    <c:if test="${pid ne 0}">
        <a href="http://www.chronic.com/profession/file_forward?pid=0">顶级</a>
        <a><cite>${username}</cite></a>
    </c:if>
</span>

<c:forEach items="${sessionScope.loginUser.roles}" var="var">
    <c:if test="${var.role_label eq 'countyManager'}"><c:set value="countyManager" var="role_label" scope="page"/></c:if>
</c:forEach>
<blockquote class="layui-elem-quote">
    <div class="layui-form" id="divForm">
        <c:if test="${role_label ne 'countyManager'}">
        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" id="file_add">
                <i class="fa fa-plus"></i> 添加
            </button>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-danger" id="file_delete">
                <i class="fa fa-remove"></i> 删除
            </button>
        </div>
        </c:if>


        <div class="layui-inline">
            <input type="text" id="areaSel" class="layui-input" readonly value="" autocomplete="off" placeholder="请选择区域" onclick="showMenu(); return false;">
        </div>

        <div class="layui-inline">
            <input type="text" class="layui-input" autocomplete="off" id="username_search" placeholder="请输入姓名">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-warm" id="file_refresh">
                <i class="fa fa-search"></i> 搜索
            </button>
        </div>

        <div id="areaContent" class="areaContent" style="display:none;">
            <ul id="areaTree" class="ztree" style="margin-top:0; width:160px;"></ul>
        </div>

    </div>
</blockquote>

<table class="layui-table" lay-filter="tb_file" lay-data="{id: 'idFile'}" >
    <thead>
    <tr>
        <th lay-data="{field:'fid',checkbox:true, fixed:'left'}"></th>
        <th lay-data="{field:'area_code', width:160, align:'center'}">区域编码</th>
        <th lay-data="{field:'group_id', width:80, align:'center'}">组编号</th>
        <th lay-data="{field:'family_id', width:180, align:'center'}">家庭编号</th>
        <th lay-data="{field:'username', width:100, align:'center'}">姓名</th>
        <th lay-data="{field:'sex', width:60, align:'center'}">性别</th>
        <th lay-data="{field:'identity', width:180, align:'center'}">身份证号</th>
        <th lay-data="{field:'birth', width:120, align:'center'}">出生日期</th>
        <th lay-data="{field:'address', width:280, align:'center'}">家庭住址</th>
        <th lay-data="{field:'cellphone', width:120, align:'center'}">联系电话</th>
        <c:if test="${pid ne 0}">
            <th lay-data="{field:'relation', width:120, align:'center'}">与户主关系</th>
        </c:if>
        <c:if test="${pid eq 0}">
        <th lay-data="{field:'create_time', width:160, align:'center'}">建档时间</th>
        </c:if>
        <th lay-data="{field:'operation', width:200, fixed:'right', align:'center'}">操作</th>
    </tr>
    </thead>
    <tbody id="file_list_body">
    </tbody>
</table>
<div id="file_page"></div>
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
        
        <c:if test="${pid eq 0}">
        $("#file_add").click(function () {
            alertByFull(layer, "添加档案", "http://www.chronic.com/profession/file_forward?forwardType=addSelect");
        });
        </c:if>

        <c:if test="${pid ne 0}">
        $("#file_add").click(function () {
            alertByFull(layer, "添加家庭成员", "http://www.chronic.com/profession/file_forward?forwardType=addChild&pid=" + ${pid});
        });
        </c:if>

        $("#file_refresh").click(function () {
            reloadFileList(1);
            var areaObj = $("#areaSel");
            areaObj.attr("value", "");
        });
        reloadFileList(1);

        initAreaTree();
    });

    function reloadFileList(page) {
        var index = layer.load();
        setTimeout(function () {
            $.post("http://www.chronic.com/profession/file_list", {
                curr: page,
                pid: '${param.pid}',
                area_code:areaCode,
                username: $("#username_search").val()
            }, function (data) {
                areaCode = null; //清空全局变量，搜索不到后再搜索时不带条件
                if (data.code === code_success) {
                    $("#file_list_body").html(renderTable(data.data)); //解析表格
                    renderPage(laypage, "file_page", data.count, data.curr, data.limit, reloadFileList); //分页
                    table.init('tb_file', {
                        height: 'full-190'
                    });

                    <c:if test="${pid eq 0}">
                    $(".update-btn").click(function () {
                        var updateId = $(this).attr("update-id");
                        alertByFull(layer, "编辑档案", "http://www.chronic.com/profession/file_forward?forwardType=update&fid=" + updateId);
                    });
                    </c:if>

                    <c:if test="${pid ne 0}">
                    $(".update-btn").click(function () {
                        var updateId = $(this).attr("update-id");
                        alertByFull(layer, "编辑家庭成员", "http://www.chronic.com/profession/file_forward?forwardType=childUpdate&fid=" + updateId);
                    });
                    </c:if>

                    $("#file_delete").click(function () {
                        layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
                            var checkStatusData = table.checkStatus('idFile').data; //获取选中行的数据

                            var checkids = new Array();
                            checkStatusData.forEach(function (val, index) { //var:当前元素
                                checkids.push(val.fid);
                            });
                            doDelete(layer, checkids, "http://www.chronic.com/profession/file_del?pid=" + ${pid}, reloadFileList(1));
                            layer.close(index);
                        });
                    });

                    $(".child-btn").click(function () {
                        var fid = $(this).attr("parent-id");
                        var username = $(this).attr("user-name");
                        window.location.href = 'http://www.chronic.com/profession/file_forward?pid=' + fid + "&username=" + username;
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
                    + '<td>' + this.fid + '</td>'
                    + '<td>' + this.area_code + '</td>'
                    + '<td>' + this.group_id + '</td>'
                    + '<td >' + this.family_id + '</td>'
                    + '<td >' + this.username + '</td>'
                    + '<td >' + this.sex + '</td>'
                    + '<td >' + this.identity + '</td>'
                    + '<td >' + new Date(this.birth).format("yyyy-MM-dd") + '</td>'
                    + '<td >' + this.address + '</td>'
                    + '<td >' + (this.cellphone === null ? "" : this.cellphone) + '</td>'
                    <c:if test="${pid ne 0}">
                    + '<td >' + (this.relation === null ? "" : this.relation) + '</td>'
                    </c:if>
                    <c:if test="${pid eq 0}">
                    + '<td >' + new Date(this.create_time).format("yyyy-MM-dd hh:mm:ss") + '</td>'
                    </c:if>
                    + '<td >'
                    <c:if test="${role_label ne 'countyManager'}">
                    + '<button class="layui-btn layui-btn-xs update-btn" update-id="' + this.fid + '"><i class="fa fa-edit"></i>编辑</button>'
                    </c:if>
                    <c:if test="${pid eq 0}">
                    + '<button class="layui-btn layui-btn-xs child-btn" parent-id="' + this.fid + '" user-name="' + this.username + '"><i class="fa fa-level-down"></i>查看成员</button>'
                    </c:if>
                    + '</td>'
                    + '</tr>';
            });
        }
        return dataHtml;
    }

    function initAreaTree() {
        var setting = {
/*            view: {
                dblClickExpand: false //双击节点时，是否自动展开父节点的标识
            },*/
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
                //beforeClick: beforeClick, //用于捕获单击节点之前的事件回调函数，并且根据返回值确定是否允许单击操作
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