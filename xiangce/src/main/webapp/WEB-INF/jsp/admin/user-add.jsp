<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/admin-head.jsp" %>

<body>
<div class="x-body">
    <form class="layui-form" method="post">

                         
                    <input name="id" value="${model.id}" type="hidden" />
                
            
             
  	          <div class="layui-form-item">
                    <label for="username" class="layui-form-label">
                        <span class="x-red">*</span>用户名
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="username" name="username" 
                        required="" lay-verify="required" value="${model.username}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="password" class="layui-form-label">
                        <span class="x-red">*</span>密码
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="password" name="password" 
                        required="" lay-verify="required" value="${model.password}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="email" class="layui-form-label">
                        <span class="x-red">*</span>邮箱
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="email" name="email" 
                        required="" lay-verify="required" value="${model.email}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="address" class="layui-form-label">
                        <span class="x-red">*</span>地址
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="address" name="address" 
                        required="" lay-verify="required" value="${model.address}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="phone" class="layui-form-label">
                        <span class="x-red">*</span>联系方式
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="phone" name="phone" 
                        required="" lay-verify="required" value="${model.phone}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="realname" class="layui-form-label">
                        <span class="x-red">*</span>姓名
                    </label>
                    <div class="layui-input-block">
                        <input type=" text " id="realname" name="realname" 
                        required="" lay-verify="required" value="${model.realname}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="type" class="layui-form-label">
                        <span class="x-red">*</span>身份
                    </label>
                    <div class="layui-input-block">
                        <input type="number" id="type" name="type" 
                        required="" lay-verify="required" value="${model.type}"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
               
            
             
  	          <div class="layui-form-item">
                    <label for="state" class="layui-form-label">
                        <span class="x-red">*</span>是否禁用
                    </label>
                    <div class="layui-input-block">
                        <select class="layui-select" name="state"  required="" lay-verify="required" >
                            <option value="0" <c:if test="${model.state eq 0}">selected="selected"</c:if>>启用</option>
                            <option value="1" <c:if test="${model.state eq 1}">selected="selected"</c:if>>禁用</option>
                        </select>
                    </div>
                </div>
               
            
             
                 <div class="layui-form-item">
                <label for="time" class="layui-form-label">
                    <span class="x-red">*</span>注册时间
                </label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input" id="time9" value="<fmt:formatDate value="${model.createtime}" pattern="yyyy-MM-dd HH:mm:ss" />" name="createtime"  placeholder="yyyy-MM-dd HH:mm:ss">
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
            $.post("/admin/user/save", data.field, function (result) {
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
                    elem: '#time9'
                    , type: 'datetime'
                 });
            
        
        
       
        //自定义格式
       
    })


 
   
</script>

</body>

</html>