<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/admin-head.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body class="layui-anim layui-anim-up">
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">管理</a>
        <a>
          <cite>数据</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so" action="/admin/photos/search" method="post">
        
          <input type="text" name="name"  placeholder="请输入关键字" autocomplete="off" class="layui-input" value="${key}">
          <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
        </form>
      </div>
   <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>
        <button class="layui-btn" onclick="x_admin_show('添加','/admin/photos/add')"><i class="layui-icon"></i>添加</button>
  
      </xblock>
      <table class="layui-table">
        <thead>
          <tr>
            <th>
              <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i></div>
            </th>
             <th>主键</th>
             <th>相册封面图片</th>
             <th>用户</th>
             <th>相册名称</th>
             <th>描述</th>
             <th>创建时间</th>
            <th>操作</th></tr>
        </thead>
        <tbody>

          <c:forEach items="${list}" var="model">  
          
         <tr>
                <td>
                <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${model.id}'><i class="layui-icon">&#xe605;</i></div>
              </td>
    
            <td>
                 ${model.id}
            </td>
    
            <td>
                <img src="${model.image}" width="150px"/>
            </td>
    
            <td>
                 ${model.uid}
            </td>
    
            <td>
                 ${model.name}
            </td>
    
            <td>
                 ${model.dis}
            </td>
    
            <td>
                <fmt:formatDate value="${model.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
            </td>
           <td class="td-manage">

        
                 <button class="layui-btn layui-btn layui-btn-xs layui-bg-blue"
                        onclick="x_admin_show('编辑','/admin/photos/edit?id=${model.id}')"><i
                         class="layui-icon">&#x1005;</i>修改
                 </button>
             
             
                  <button class="layui-btn layui-btn layui-btn-xs layui-bg-red"
                          onclick="member_del(this,'${model.id}')"><i
                          class="layui-icon">&#x1005;</i>删除
                  </button>

               <button class="layui-btn layui-btn layui-btn-xs layui-bg-green"
                       onclick="x_admin_show('相册','/admin/photos/photos?id=${model.id}')"><i
                       class="layui-icon">&#x1005;</i>查看相册
               </button>

               <button class="layui-btn layui-btn layui-btn-xs layui-bg-orange"
                       onclick="x_admin_show('管理相册','/admin/photograph/list?pid=${model.id}')"><i
                       class="layui-icon">&#x1005;</i>管理相册
               </button>
              </td>
           </tr>
          </c:forEach>

        </tbody>
      </table>


    </div>
    <script>
      layui.use('laydate', function(){
        var laydate = layui.laydate;
        
        //执行一个laydate实例
        laydate.render({
          elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
          elem: '#end' //指定元素
        });
      });


   
      /*用户-删除*/
      function member_del(obj,id){
          layer.confirm('确认要删除吗？删除后无法恢复',function(index){
              //发异步删除数据
              $.post("/admin/photos/delete",{id:id},function(result){
                  if (result.code == 200){
                      $(obj).parents("tr").remove();
                      layer.msg('已删除!',{icon:1,time:1000});
                  } else {
                      $(obj).parents("tr").remove();
                      layer.msg('删除失败!',{icon:2,time:1000});
                  }
              })

          });
      }
        function delAll (argument) {

        var data = tableCheck.getData();
        data = data.toString();
        layer.confirm('确认要删除吗？',function(index){
            //捉到所有被选中的，发异步进行删除
             var ids = data.split(",")
            for (i =0;i<ids.length; i++){
                 $.post("/admin/photos/delete",{id:ids[i]},function(result){
                  
              })

             }
            layer.msg('删除成功', {icon: 1});
            $(".layui-form-checked").not('.header').parents('tr').remove();
        });
      }

    </script>

  </body>

</html>