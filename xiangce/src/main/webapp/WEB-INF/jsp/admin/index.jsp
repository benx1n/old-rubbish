<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/admin-head.jsp" %>
<body>
<!-- 顶部开始 -->
<div class="container">
    <div class="logo"><a href="/layui/index.jsp">相册</a></div>
    <div class="left_open">
        <i title="展开左侧栏" class="iconfont">&#xe699;</i>
    </div>

    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">

                   ${sessionScope.user.realname}

              </a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
                <dd><a href="/logout">退出</a></dd>
            </dl>
        </li>
    </ul>

</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">


          <c:if test="${sessionScope.user.type eq 0}">
              <li>
                  <a href="javascript:;">
                      <i class="iconfont">&#xe6b8;</i>
                      <cite>用户管理</cite>
                      <i class="iconfont nav_right">&#xe697;</i>
                  </a>
                  <ul class="sub-menu">
                      <li>
                          <a _href="/admin/user/list?type=1">
                              <i class="iconfont">&#xe6a7;</i>
                              <cite>用户管理</cite>
                          </a>
                      </li>

                  </ul>
              </li>
              <li>
                  <a href="javascript:;">
                      <i class="iconfont">&#xe6cb;</i>
                      <cite>相册管理</cite>
                      <i class="iconfont nav_right">&#xe697;</i>
                  </a>
                  <ul class="sub-menu">
                      <li>
                          <a _href="/admin/photos/list">
                              <i class="iconfont">&#xe6a7;</i>
                              <cite>相册列表</cite>
                          </a>
                      </li >
                  </ul>
              </li>
          </c:if>

            <c:if test="${sessionScope.user.type eq 1}">
                <li>
                    <a href="javascript:;">
                        <i class="iconfont">&#xe6cb;</i>
                        <cite>相册管理</cite>
                        <i class="iconfont nav_right">&#xe697;</i>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a _href="/admin/photos/list?uid=${sessionScope.user.id}">
                                <i class="iconfont">&#xe6a7;</i>
                                <cite>相册列表</cite>
                            </a>
                        </li >
                    </ul>
                </li>

            </c:if>


        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='/welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
    </div>
</div>
<div class="page-content-bg"></div>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<!-- 底部开始 -->
<div class="footer">
    <div class="copyright">by ZJICM_benx1n  Framework @x-admin by layui</div>
</div>
<!-- 底部结束 -->

</body>
</html>