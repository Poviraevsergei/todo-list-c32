package servlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello Servlet \n");

        HttpSession session = req.getSession();

        session.setMaxInactiveInterval(-1);
        writer.println("JSessionId: " + session.getId() + "\n");
        writer.println("IsNew: " + session.isNew() + "\n");

        String name = (String) session.getAttribute("name");
        if (name != null){
            writer.println("Name: " + session.getAttribute("name") + "\n");
        } else {
            writer.println("Name not found!");
            session.setAttribute("name","Kirill");
        }
    }
}
