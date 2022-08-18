<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>欢迎页面-X-admin2.0</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <script type="text/javascript" src="static/layui/js/jquery.min.js"></script>
        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="/static/layui/css/font.css">
        <link rel="stylesheet" href="/static/layui/css/xadmin.css">
        <script>
            $(function () {
                updatetime();
                setInterval(function () {
                    updatetime();

                },1000)
            })

            function updatetime() {
                var time = new Date();
                var day = time.getDate();
                var month = time.getMonth()+1;
                if (month <9){
                    month = "0" + month;
                }
                var year = time.getFullYear();
                var hours = time.getHours();
                var minutes = time.getMinutes();
                var seconds = time.getSeconds();
                if (seconds<10){
                    seconds = "0"+seconds;
                }
                var date = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
                $("#nowTime").text(date);
            }
        </script>
    </head>
    <body>
    <div class="x-body layui-anim layui-anim-up">
        <blockquote class="layui-elem-quote">欢迎：
            <span class="x-red">${user.realname}</span>！当前时间:<span id="nowTime"></span></blockquote>

        <div>
            <h1>欢迎使用系统</h1>
        </div>
    </div>
        <script>
        var _hmt = _hmt || [];
        (function() {
          var hm = document.createElement("script");
          hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
          var s = document.getElementsByTagName("script")[0]; 
          s.parentNode.insertBefore(hm, s);
        })();
        </script>
    </body>
</html>