<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
    <div class="layui-form-item" style="margin-top: 15px;">
        <label class="layui-form-label">请求路径</label>
        <div class="layui-input-block">
            <input type="text" name="request_url" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" autocomplete="off" name="menu_name" lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单图标</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" autocomplete="off" name="menu_icon" id="menu_icon" onblur="showIcon()"
                   lay-verify="required">
        </div>
        <div class="layui-form-mid layui-word-aux">图标参考&nbsp;&nbsp;<i id="icon-show"></i></div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">上级菜单</label>
        <div class="layui-input-inline">
            <select id="parent_select" name="pid" lay-filter="parentSelect" lay-verify="required">
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="menu_submit">立即提交</button>
        </div>
    </div>
</form>
</body>
</html>
<script>
    var form,layer;
    layui.use(['form', 'layer'], function () {
        var form = layui.form, layer = layui.layer;
        reloadMenuList();

        form.on('submit(menu_submit)', function (data) {
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); //得到当前iframe层的索引
                $.post("http://www.chronic.com/permission/menu_add?forwardType=add", data.field, function (data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function () {
                            parent.layer.close(index); ////关闭
                            parent.initPermissionTree(); //刷新父页面
                        }, loadingTime5h);
                    } else {
                        layer.msg(data.msg, function () {
                            //关闭后的操作
                            submitBtn.removeClass("layui-btn-disabled");
                        });
                    }
                }, "json");
            }
            return false;
        });

        function reloadMenuList() {
            var index = layer.load(2);
            setTimeout(function () {
                $.getJSON("http://www.chronic.com/permission/menu_add?forwardType=menuList", function (data) {
                    if (data.code === code_success) {
                        $("#parent_select").html(renderSelect(data.data)); //解析select选择框
                        form.render('select'); //刷新select选择框渲染
                    } else {
                        layer.msg('Sorry 系统出现问题了 ', {icon: 5});
                    }
                });
                layer.close(index);
            }, loadingTime);
        }

    });

    function renderSelect(data) {
        var selectHtml = '';
        selectHtml += '<option value="">请选择</option>';
        selectHtml += '<option value="0">无</option>';
        if (data.length !== 0) {
            $.each(data, function () {
                selectHtml += '<option value="' + this.menu_id + '">' + this.menu_name + '</option>';
            });
        } else {
            selectHtml = '<option value="">暂无数据</option>';
        }
        return selectHtml;
    }

    function showIcon(){
        var cla = $("#menu_icon").val();
        $("#icon-show").removeAttr("class").addClass(cla);
/*        $("#icon-show").removeAttr("class").addClass("fa").addClass("fa-"+cla);*/
    }
</script>
