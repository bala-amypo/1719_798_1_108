package com.example.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Hello from Dynamic Event Ticket Pricing Servlet");
    }
}










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
