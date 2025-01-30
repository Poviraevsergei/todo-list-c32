package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.TaskRepository;

import java.io.IOException;
import java.util.Set;

@WebServlet("/todo")
public class TodoListServlet extends HttpServlet {
    private final TaskRepository taskRepository;

    public TodoListServlet() {
        this.taskRepository = new TaskRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        req.setAttribute("username", username);

        Set<String> tasks = taskRepository.getTasksByUsername(username);

        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/page/todo-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String task = req.getParameter("task");
        String deletedTask = req.getParameter("deletedTask");

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        Set<String> tasks = taskRepository.getTasksByUsername(username);

        if (task != null) {
            tasks.add(task);
            taskRepository.getTaskList().put(username, tasks);
        }

        if (deletedTask != null) {
            tasks.remove(deletedTask);
            taskRepository.getTaskList().put(username, tasks);
        }

        req.setAttribute("tasks", tasks);

        req.getRequestDispatcher("/WEB-INF/page/todo-list.jsp").forward(req, resp);
    }
}

//TODO: 1. изменять таски
