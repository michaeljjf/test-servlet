package com.example.TestServlet;

import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet生命周期
 * 1、构造方法执行一次
 * 2、初始化方法执行一次
 * 3、服务方法，执行多次（每次响应客户端请求时执行）
 * 4、销毁方法，执行一次（在tomcat服务器停止时执行）
 */
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    // servlet中我们一般不要轻易使用成员变量！！可能会造成线程安全问题
    // 如果要使用成员变量，应该尽量避免对成员变量产生影响
    // 如果要产生影响，应该注意线程安全问题
    private int count = 0;
    public void init() {
        message = "Hello World!";
        System.out.println("调用servlet初始化方法");
    }

    public HelloServlet() {
        System.out.println("调用servlet构建方法");
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
        System.out.println("调用servlet服务方法");
        count++;// 这里会产生线程安全问题
        System.out.println("i的值:" + count);
    }

    public void destroy() {
        System.out.println("调用servlet销毁方法");
    }
}