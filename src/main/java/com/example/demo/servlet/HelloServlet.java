package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        
        PrintWriter out = resp.getWriter();
        out.println("Hello from Dynamic Event Ticket Pricing Servlet");
        out.flush();
    }
}











// package com.example.demo.servlet;

// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.io.PrintWriter;

// @WebServlet(urlPatterns = "/hello-servlet")
// public class HelloServlet extends HttpServlet {
    
//     @Override
//     protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws IOException {
//         response.setContentType("text/plain");
//         response.setCharacterEncoding("UTF-8");
        
//         PrintWriter out = response.getWriter();
//         out.print("Hello from Dynamic Event Ticket Pricing Servlet");
//         out.flush();
//     }
// }









// package com.example.demo.servlet;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;

// @WebServlet("/hello")
// public class HelloServlet extends HttpServlet {

//     @Override
//     public void doGet(HttpServletRequest request,
//                       HttpServletResponse response)
//             throws ServletException, IOException {

//         response.setContentType("text/plain");
//         response.getWriter().write("Hello from Servlet");
//     }
// }
