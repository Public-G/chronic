<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
    <link rel="stylesheet" href="http://www.chronic.com/static/ztree/zTreeStyle.css" media="all"/>
    <script src="http://www.chronic.com/static/ztree/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
<form class="layui-form layui-form-pane" >
    <div class="layui-form-item">
        <ul id="menuTree" class="ztree"></ul>
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="grant_menu_submit">立即提交</button>
    </div>
</form>
</body>
</html>
<script>
    var form, laypage, layer;
    var ztreeObject;
    layui.use(['form', 'layer'], function () {
        form = layui.form;
        layer = layui.layer;

        initPermissionTree('${role_id}');

        //分配菜单
        form.on('submit(grant_menu_submit)', function (data) {
            var submitBtn = $(this);
            var nodes = ztreeObject.getCheckedNodes(true);
            var checkids = "";
            $.each(nodes, function () {
                checkids += this.menu_id + ",";
            });
/*            var role_id = $(this).attr("role_id");*/
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); //得到当前iframe层的索引
                $.ajax({
                    url: "http://www.chronic.com/permission/role_grantMenu",
                    type: "post",
                    traditional: true,
                    data: {
                        role_id: '${role_id}',
                        checkids: checkids
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code === code_success) {
                            layer.msg(data.msg, {
                                offset: 't'
                            });
                            setTimeout(function () {
                                parent.layer.close(index); ////关闭    
                                parent.reloadRoleList(1); //刷新父页面
                            }, loadingTime5h);
                        } else {
                            layer.msg(data.msg, function () {
                                offset: 't'
                                //关闭后的操作
                                submitBtn.removeClass("layui-btn-disabled");
                            });
                        }
                    }
                });
            }
            return false;
        });
    });

    //初始化菜单树
    function initPermissionTree(role_id) {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "menu_id",
                    pIdKey: "pid",
                },
                key: {
                    url: "haha",
                    name: "menu_name"
                }
            },
            view: {
                //自定义显示的效果
                addDiyDom: showIcon
            },
            check: {
                enable: true
            }
        };

        //查询菜单
        $.getJSON("http://www.chronic.com/permission/role_menu", function (data) {
            var nodes = data.data;
            //给每一个节点修改或者添加一些属性
            $.each(nodes, function () {
                if (this.pid === 0) {
                    this.open = true; //父节点展开
                }
            });

            //把初始化好的ztree对象作为全局变量，可以通用操作这个对象，来改变树
            ztreeObject = $.fn.zTree.init($("#menuTree"), setting, nodes);

            checkcurPermisson(role_id);
        });
    }

    //自定义图标
    function showIcon(treeId, treeNode) {
        //console.log(treeId);
        $("#" + treeNode.tId + "_ico").removeClass().addClass(
            treeNode.menu_icon);
    }

    //传入角色id，将当前角色拥有的权限勾选
    function checkcurPermisson(role_id) {
        $.getJSON("http://www.chronic.com/permission/role_haveMenu?role_id=" + role_id,
            function (data) {
                var nodes = data.data;
                $.each(nodes, function () {
                    //从ztree中获取到要勾选的对象
                    var node = ztreeObject.getNodeByParam("menu_id",
                        this.menu_id, null); //根据节点数据的属性搜索，获取条件完全匹配的节点数据 JSON 对象
                    ztreeObject.checkNode(node, true, false); //勾选 或 取消勾选 单个节点,node:需要勾选 或 取消勾选 的节点数据. true: 表示勾选节点. false:示只修改此节点勾选状态，无任何勾选联动操作
                });
            });
    }
    
</script>