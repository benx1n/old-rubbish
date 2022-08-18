<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/admin-head.jsp" %>

<body>
<div class="x-body">
    <form class="layui-form" method="post">

                         
                    <input name="id" value="${model.id}" type="hidden" />
                    <input name="pid" value="${model.pid}" type="hidden" />

            
             
                <img src="${model.image}" style="width: 180px" name="image" />
               <input name="image" value="${model.image}" type="hidden" lay-verify="required"/>
                  <div class="layui-form-item text-center">
                    <div style="margin:0 auto;width: 140px;">
                        <button type="button" class="layui-btn" id="image" name="file">
                            <i class="layui-icon">&#xe67c;</i>上传图片
                        </button>
                    </div>
                  </div>                        
            
             

               
            

        

             <div class="layui-form-item">
            <label for="submit" class="layui-form-label">
            </label>
            <button class="layui-btn" lay-filter="add" lay-submit="" id="submit">
                保存
            </button>
        </div>
    </form>
</div>
<script>
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer;

        //自定义验证规则
        form.verify({
            nikename: function (value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            }
            , pass: [/(.+){6,12}$/, '密码必须6到12位']
            , repass: function (value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(add)', function (data) {
            console.log(data);
            //发异步，把数据提交给php
            $.post("/admin/photograph/save", data.field, function (result) {
                if (result.code == 200) {
                    layer.alert("成功", {icon: 6}, function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                        window.parent.location.reload();//刷新父页面
                    });
                } else {
                    layer.alert("失败", {icon: 5});
                }
            })
            return false;

        });


    });
    //上传
    layui.use('upload', function () {
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#image' //绑定元素
             ,accept: 'file'
            , url: '/upload' //上传接口
            , done: function (res) {
                //上传完毕回调
                $("img[name=image]").attr("src", res.data);
                $("input[name=image]").val(res.data);
            }
            , error: function () {
                //请求异常回调
                layer.alert("上传失败", {icon: 5});
            }
        });
    });
    
    
    layui.use('laydate', function() {
        var laydate = layui.laydate;
        
        
    
            
    
            
    
            
    
                laydate.render({
                    elem: '#time3'
                    , type: 'datetime'
                 });
            
        
        
       
        //自定义格式
       
    })


 
   
</script>

</body>

</html>