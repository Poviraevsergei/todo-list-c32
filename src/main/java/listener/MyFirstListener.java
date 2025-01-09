package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MyFirstListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App init!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App destroy!");
    }
}
