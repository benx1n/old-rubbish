package com.filter;

import com.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp =(HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        // 获得用户请求的URI
        String path = req.getRequestURI();
        // 从session取得已经登录验证的凭证
        User user = (User) session.getAttribute("user");
        String[] urls = {"/login","/json",".js",".css",".ico",".jpg",".png","toreg","reg","/admin/login"};
        boolean flag = true;
        for (String str : urls) {
            if (path.indexOf(str) != -1) {
                flag =false;
                break;
            }
        }

        if (flag){
            if(path.indexOf("login") > -1 || path.indexOf("app") > -1 ) {//注意：登录页面千万不能过滤  不然过滤器就。。。。。自行调试不要偷懒！这样记忆深刻
                filterChain.doFilter(req, resp);
                return;
            } else {//如果不是login.jsp进行过滤
                if (user == null ) {
                    // 跳转到登陆页面
                    resp.sendRedirect("/admin/login");
                } else {
                    // 已经登陆,继续此次请求
                    filterChain.doFilter(req, resp);
                }
            }
        }else {
            filterChain.doFilter(req, resp);
        }


    }


    public void destroy() {

    }
}
