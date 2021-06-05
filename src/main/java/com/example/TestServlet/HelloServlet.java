package com.example.TestServlet;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 只对post请求启作用
        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("userName");

        // 针对get请求，如果tomcat版本过低（tomcat9不需要），如tomcat7，还是会乱码，这种时候需要如下处理
        // 1、手动编码解码（不推荐）
//        byte[] bytes = userName.getBytes(StandardCharsets.ISO_8859_1);
//        userName = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(userName);
        // 2、设置tomcat连接器的URL编码为UTF-8 URIEncoding="utf-8"
        /*
        apache-tomcat-9.0.46\conf\server.xml 里配置
            <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
			   URIEncoding="utf-8"
               redirectPort="8443" />
         */

        // 打印所有的请求参数名称
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String element = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(element);
            System.out.println(element + "：" + Arrays.toString(parameterValues));
        }

        // 响应乱码设置
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + userName + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}