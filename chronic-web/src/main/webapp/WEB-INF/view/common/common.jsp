<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}"/> --%>
<%--网站编码--%>
<meta charset="utf-8"/>
<%--网页宽度及初始、最大缩放比例--%>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
<%--CSS--%>
<link rel="stylesheet" href="http://www.chronic.com/static/css/common/common.css" media="all"/>
<link rel="stylesheet" href="http://www.chronic.com/static/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="http://www.chronic.com/static/font/css/font-awesome.min.css" media="all"/>
<%--JS--%>
<script src="http://www.chronic.com/static/js/plugin/jquery-3.3.1.min.js"></script>
<script src="http://www.chronic.com/static/js/common/common.js"></script>
<script src="http://www.chronic.com/static/layui/layui.js"></script>
<style>
    .layui-table-cell .layui-form-checkbox[lay-skin=primary], .layui-table-cell .layui-form-radio[lay-skin=primary] {
        top: 50%;
        vertical-align: middle;
    }
</style>
<script>
    /**
     * 延迟加载时间
     */
    var loadingTime = 100;

    var loadingTime5h = 500;

    var loadingTime1k = 1000;

    /**
     * 成功返回码
     */
    var code_success = 200;
    /**
     * 失败返回码
     */
    var code_fail = 500;
</script>
