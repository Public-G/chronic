<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp"%>
    <link rel="stylesheet" href="http://www.chronic.com/static/ztree/zTreeStyle.css" media="all"/>
    <script src="http://www.chronic.com/static/ztree/jquery.ztree.all-3.5.min.js"></script>
</head>
<body>
<form class="layui-form layui-form-pane" >
    <div class="layui-form-item">
        <ul id="menuTree" class="ztree"></ul>
    </div>
</form>
</body>
</html>
<script>
    var form, laypage, layer;
    var treeNodeMsg;
    layui.use(['form', 'layer'], function () {
        form = layui.form;
        layer = layui.layer;
        initPermissionTree();

    });

    //初始化菜单树
    function initPermissionTree() {
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
                addDiyDom: showIcon, //自定义显示的效果
                addHoverDom: showBtn, //鼠标移动到节点上时，显示用户自定义控件
                removeHoverDom: hideBtn //鼠标移出节点时，隐藏用户自定义控件
            },
        };

        //查询菜单
        $.getJSON("http://www.chronic.com/permission/menu_list", function (data) {
            var nodes = data.data;
            //给每一个节点修改或者添加一些属性
            $.each(nodes, function () {
                if (this.pid === 0) {
                    this.open = true; //父节点展开
                }
            });
            $.fn.zTree.init($("#menuTree"), setting, nodes);
        });
    }

    //自定义图标
    function showIcon(treeId, treeNode) {
        //console.log(treeId);
        $("#" + treeNode.tId + "_ico").removeClass().addClass(
            treeNode.menu_icon);
    }

    //鼠标移动到节点上时，显示用户自定义控件
    function showBtn(treeId, treeNode) {
        treeNodeMsg = treeNode; //treeNode作为全局变量供传值
        var aObj =  $("#" + treeNode.tId + "_a");
        aObj.nextAll("span").remove();

        var addBtn = '<button class="layui-btn layui-btn-primary layui-btn-xs menu-add"> <i class="layui-icon">&#xe608;</i></button>';
        var editBtn = '<button class="layui-btn layui-btn-primary layui-btn-xs menu-edit"> <i class="layui-icon">&#xe642;</i></button>';
        var delBtn = '<button class="layui-btn layui-btn-primary layui-btn-xs menu-del"> <i class="layui-icon">&#xe640;</i></button>';

        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length > 0) return;

        var btn = '<span id="addBtn_'+ treeNode.tId + '">';
        if ( treeNode.level === 0 ) {
            btn += addBtn;
            btn += editBtn;
            btn += delBtn;
        }else{
            btn += editBtn;
            btn += delBtn;
        }
        btn += '</span>';
        //在a标签之后添加按钮
        aObj.after(btn);
    }

    //鼠标移出节点时，隐藏用户自定义控件
    function hideBtn(treeId, treeNode) {
        $("#" + treeNode.tId + "_a").nextAll("span").remove();
    }


    //添加
    $("form").on("click",".menu-add",function(){
        alertByFull(layer, "添加", "http://www.chronic.com/permission/menu_forward?forwardType=menu_add");
        return false;
    });

    //更新
    $("form").on("click",".menu-edit", treeNodeMsg, function(){
        var menu_id = treeNodeMsg.menu_id;
        var menu_name = treeNodeMsg.menu_name;
        var menu_icon = treeNodeMsg.menu_icon;
        var pid = treeNodeMsg.pid;
        var request_url = treeNodeMsg.request_url;
        alertByFull(layer, "编辑", "http://www.chronic.com/permission/menu_forward?forwardType=menu_update&menu_id=" + menu_id
            +"&menu_name=" + menu_name + "&menu_icon=" + menu_icon + "&pid=" + pid + "&request_url=" + request_url);
        return false;
    });

    //删除
    $("form").on("click",".menu-del", treeNodeMsg, function(){
        layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(index) {
          var checkids = treeNodeMsg.menu_id;
            doDelete(layer, checkids, "http://www.chronic.com/permission/menu_del");
            layer.close(index);
            setTimeout(function () {
                document.location.reload();
            }, loadingTime5h);
        });
        return false;

    });

</script>