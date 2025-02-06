package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import repository.UserRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebFilter(value = {
        "/about-me",
        "/todo"
})
public class AuthFilter implements Filter {

    UserRepository userRepository;

    public AuthFilter() {
        userRepository = new UserRepository();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String username = (String) session.getAttribute("username");

        try {
            if (username != null && userRepository.isContainsUserByUsername(username)) {
                chain.doFilter(request, response);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
