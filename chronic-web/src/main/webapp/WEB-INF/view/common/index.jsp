<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <link rel="stylesheet" href="http://www.chronic.com/static/css/common/index.css" media="all"/>
</head>
<body class="layui-layout-body ">
<!-- 布局容器 -->
<div class="layui-layout layui-layout-admin">
    <!-- 头部 -->
    <div class="layui-header header">
        <!-- logo -->
        <div class="layui-logo ">慢性病系统</div>
        <!-- 隐藏按钮 -->
        <div class="layui-menu-hide-btn" onselectstart="return false;" onclick="toggleSide()">
            <i class="fa fa-bars"></i>
        </div>

        <!-- 顶部右侧菜单 -->
        <ul class="layui-nav top_menu">
            <li class="layui-nav-item" id="userInfo">
                <a href="javascript:;"><img src="http://www.chronic.com/static/image/wzxy.jpg" class="layui-nav-img userAvatar"
                                            width="35" height="35"><cite
                        class="adminName">${sessionScope.loginUser.user_name}</cite></a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" data-url="page/user/userInfo.html"><i class="fa fa-id-card-o"></i><cite>个人资料</cite></a>
                    </dd>
                    <dd><a href="javascript:;" data-url="page/user/changePwd.html"><i
                            class="fa fa-edit"></i><cite>修改密码</cite></a>
                    </dd>
                    <dd><a href="javascript:;" class="refresh refreshThis"><i
                            class="fa fa-refresh"></i><cite>刷新当前</cite></a>
                    </dd>
                    <dd><a href="javascript:;" class="signOut" onclick="logout()"><i class="fa fa-power-off"></i><cite>退出</cite></a>
                    </dd>
                </dl>
            </li>
        </ul>
    </div>

    <!-- 侧边栏 -->
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!--  左侧导航区域 -->
            <ul class="layui-nav layui-nav-tree">
                <!-- 遍历菜单 -->
                <c:forEach items="${sessionScope.userMenus }" var="p_menu">
                    <c:choose>
                        <c:when test="${empty p_menu.childs }">
                            <li class="layui-nav-item layui-nav-itemed">
                                <a href="javascript:;"
                                   onclick="addTab('${p_menu.menu_id}','${p_menu.menu_name}','${p_menu.menu_icon}','${p_menu.request_url}')"><i
                                        class="${p_menu.menu_icon }"></i>${p_menu.menu_name}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="layui-nav-item">
                                <a href="javascript:;"><i class="${p_menu.menu_icon}"></i>${p_menu.menu_name }</a>
                                <dl class="layui-nav-child">
                                    <c:forEach items="${p_menu.childs}" var="c_menu">
                                        <dd>
                                            <a href="javascript:;"
                                               onclick=" addTab( '${c_menu.menu_id}', '${c_menu.menu_name}', '${c_menu.menu_icon}', '${c_menu.request_url}')"><i
                                                    class="${c_menu.menu_icon }"></i>${c_menu.menu_name}</a>
                                        </dd>
                                    </c:forEach>
                                </dl>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>

    <!-- 主体 -->
    <div class="layui-body">
        <!-- 顶部切换卡 -->
        <div class="layui-tab" style="margin-top: 0px;" lay-allowClose="true" lay-filter="body-tab">
            <ul class="layui-tab-title">
                <li class="layui-this" lay-id="0"><i class="layui-menu-title fa fa-home"></i>主页</li>
            </ul>

            <div class="layui-tab-content ">
                <div class="layui-tab-item layui-show">
                    <blockquote class="layui-elem-quote layui-bg-green">
                        <div id="nowTime"></div>
                    </blockquote>
                </div>
            </div>
        </div>
    </div>

    <!-- 底部 -->
    <div class="layui-footer footer">
        &copy; 2018 Powered by <a href="https://github.com" target="_blank">chronic</a>
    </div>
</div>
</body>
</html>
<script>
    var element, layer;
    layui.use(['element', 'layer'], function () {
        element = layui.element, layer = layui.layer;
    });

    //刷新
    $(".refresh").on("click", function () {
        if ($(this).hasClass("refreshThis")) {
            $(this).removeClass("refreshThis");
            $(".layui-tab-content .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload(true);
            setTimeout(function () {
                $(".refresh").addClass("refreshThis");
            }, loadingTime1k)
        } else {
            layer.msg("您点击的速度超过了服务器的响应速度，还是等一秒再刷新吧！");
        }
    });

    $(function () {
        //获取系统时间
        var newDate = '';
        getLangDate();
    });

    //值小于10时，在前面补0
    function dateFilter(date) {
        if (date < 10) {
            return "0" + date;
        }
        return date;
    }

    function getLangDate() {
        var dateObj = new Date(); //表示当前系统时间的Date对象
        var year = dateObj.getFullYear(); //当前系统时间的完整年份值
        var month = dateObj.getMonth() + 1; //当前系统时间的月份值
        var date = dateObj.getDate(); //当前系统时间的月份中的日
        var day = dateObj.getDay(); //当前系统时间中的星期值
        var weeks = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        var week = weeks[day]; //根据星期值，从数组中获取对应的星期字符串
        var hour = dateObj.getHours(); //当前系统时间的小时值
        var minute = dateObj.getMinutes(); //当前系统时间的分钟值
        var second = dateObj.getSeconds(); //当前系统时间的秒钟值
        var timeValue = "" + ((hour >= 12) ? (hour >= 18) ? "晚上" : "下午" : "上午" ); //当前时间属于上午、晚上还是下午
        newDate = dateFilter(year) + "年" + dateFilter(month) + "月" + dateFilter(date) + "日 " + " " + dateFilter(hour) + ":" + dateFilter(minute) + ":" + dateFilter(second);
        document.getElementById("nowTime").innerHTML = '<c:if test="${sessionScope.loginUser.job_id ne null}" >工号：<c:out value="${sessionScope.loginUser.job_id}"></c:out>，</c:if>' + "${sessionScope.loginUser.user_name}，" + timeValue + "好！当前时间为： " + newDate + "　" + week;
        setTimeout("getLangDate()", 1000);
    }

    function toggleSide() {
        if ($(".topLevelMenus li.layui-this a").data("url")) {
            layer.msg("此栏目状态下左侧菜单不可展开");  //主要为了避免左侧显示的内容与顶部菜单不匹配
            return false;
        }
        $(".layui-layout-admin").toggleClass("showMenu");
    }

    function addTab(id, name, icon, url) {
        if (url) {
            if (!$(".layui-tab-title li[lay-id=" + id + "]")[0]) {
                var title = '<i class="' + icon + '"></i>' + name;
                var iframe = '<iframe id="frame-' + id +
                    '" onload="changeFrameHeight(\'frame-' + id +
                    '\')" src="${ctx}' + url +
                    '" style="width: 100%; border: 0px;"></iframe>';
                element.tabAdd('body-tab', {
                    title: title,
                    content: iframe,
                    id: id
                });
            }
            element.tabChange('body-tab', id);
        }
    }

    function changeFrameHeight(id) {
        var iframe = $("#" + id);
        iframe.height(iframe[0].attributes.style.ownerDocument.documentElement.clientHeight - 175);
    }

    function logout() {
        layer.confirm('确认退出登录?', {icon: 3, title: '确认退出'}, function (index) {
            $.getJSON("http://www.chronic.com/user/userLogout", function (data) {
                if (data.code == 200) {
                    layer.msg(data.msg, {icon: 1, time: 300}, function () {
                        location.href = 'http://www.chronic.com/';
                    });
                }
            });
            layer.close(index);
        });
    }
</script>
