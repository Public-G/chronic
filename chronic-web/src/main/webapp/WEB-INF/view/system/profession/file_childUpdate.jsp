<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common.jsp" %>
</head>
<body>
<form class="layui-form layui-form-pane">
    <input type="hidden" name="fid" value="${param.fid}"/>
    <s:iterator value="fileList" var="file">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="username" value="<s:property value="#file.username"/>" autocomplete="off" placeholder="请输入姓名" class="layui-input"
                       lay-verify="required">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">身份证号</label>
            <div class="layui-input-inline">
                <input type="text" name="identity" value="<s:property value="#file.identity"/>" placeholder="请输入身份证号" autocomplete="off" class="layui-input"
                       lay-verify="required|identity">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">出生日期</label>
            <div class="layui-input-inline">
                <input type="text" name="birth" value="<s:date name="#file.birth" format="yyyy-MM-dd" />" id="birth" placeholder="请选择" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item" pane="">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-block">
                <s:if test="%{#file.sex == '男'}">
                    <input type="radio" name="sex" value="男" title="男"  checked="">
                    <input type="radio" name="sex" value="女" title="女">
                </s:if>
                <s:elseif test="%{#file.sex == '女'}">
                    <input type="radio" name="sex" value="男" title="男">
                    <input type="radio" name="sex" value="女" title="女" checked="">
                </s:elseif>
                <input type="radio" name="sex" value="" title="中性" disabled>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-inline">
                    <input type="text" name="cellphone" value="<s:property value="#file.cellphone"/>" placeholder="请输入联系电话" autocomplete="off" class="layui-input"
                           lay-verify="phone">
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">与户主关系</label>
                <div class="layui-input-inline">
                    <input type="text" name="relation" value="<s:property value="#file.relation"/>" placeholder="请输入与户主关系" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">家庭住址</label>
            <div class="layui-input-block">
                <textarea name="address"  placeholder="请输入家庭住址" class="layui-textarea" lay-verify="required"><s:property value="#file.address"/></textarea>
            </div>
        </div>

    </s:iterator>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="file_submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</body>
</html>
<script>
    var form, layer, laydate;
    layui.use(['form', 'layer', 'laydate'], function () {
        form = layui.form;
        layer = layui.layer;
        laydate = layui.laydate;

        //日期选择器
        laydate.render({
            elem: '#birth',
            type: 'date',
            theme: 'molv',
            calendar: true
        });

        form.on('submit(file_submit)',function(data) {
            console.log(data.field);
            var submitBtn = $(this);
            if (!submitBtn.hasClass("layui-btn-disabled")) {
                submitBtn.addClass("layui-btn-disabled");
                var index = parent.layer.getFrameIndex(window.name); ////得到当前iframe层的索引
                $.post("http://www.chronic.com/profession/file_update",data.field,function(data) {
                    if (data.code === code_success) {
                        layer.msg(data.msg);
                        setTimeout(function() {
                            parent.layer.close(index); ///关闭
                            parent.reloadFileList(1); //刷新父页面
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

    });
</script>
