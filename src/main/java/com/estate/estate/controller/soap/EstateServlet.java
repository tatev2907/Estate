package com.estate.estate.controller.soap;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "estateServlet", value = "/estate-servlet")
public class EstateServlet extends HttpServlet {
    private String message;
    private String runnningAddress;

    public void init() {
        message = "Estate Application";
        runnningAddress = "http://localhost:8888/ws/soap";
        Endpoint.publish(runnningAddress, new SoapServices());

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h2> Now you can find soap requests at " +runnningAddress  + "</h2>");
        out.println("</body></html>");

    }

    public void destroy() {
    }
}