<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/admin-head.jsp"%>
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div class="message">注册</div>
        <div id="darkbannerwrap"></div>
        
        <form method="post" class="layui-form" action="/login">
            <input name="type"  type="hidden" value="1" lay-verify="required" class="layui-input" >
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input name="tel" placeholder="电话号"  type="number" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="age" placeholder="年龄"  type="number" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="realname" placeholder="姓名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="email" placeholder="邮箱"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="address" placeholder="地址"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input value="注册" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >

        </form>
    </div>

    <script>
        $(function  () {
            layui.use('form', function(){
              var form = layui.form;
              // layer.msg('玩命卖萌中', function(){
              //   //关闭后的操作
              //   });
              //监听提交
              form.on('submit(login)', function(data){
                  $.post("/reg",data.field,function (i) {
                      if (i ==1){
                          layer.alert("成功", {icon: 6},function(){
                              location.href="/admin/login";
                          })
                      } else {
                          layer.alert("失败", {icon: 5});
                      }
                  })
                return false;
              });
            });
        })

        
    </script>


</body>
</html>