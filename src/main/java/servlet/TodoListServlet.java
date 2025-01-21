package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/todo")
public class TodoListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("username", req.getSession().getAttribute("username"));
        req.getRequestDispatcher("/page/todo-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. Достать таску
        //2. Достать юзера из сессии
        //3. достаем и добавляем все таски для этого пользователя
        //4. готовим ответ: отправляем все его таски
    }
}

//TODO: 1. добавлять, удалять, изменять таски
//TODO: 2. фильтр аутентификации 
