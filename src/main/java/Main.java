import model.User;
import repository.SQLQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<User> users = new ArrayList<>();
        
        final String URL = "jdbc:postgresql://localhost:5432/database-32";
        final String DB_LOGIN = "user32";
        final String DB_PASSWORD = "qwerty";
        
        //1. Скачать драйвер (в POM)
        //2. Подгрузить драйвер в оперативу (зарегистрировать)
        Class.forName("org.postgresql.Driver");
        
        //3. Создать соединение
        Connection connection = DriverManager.getConnection(URL, DB_LOGIN, DB_PASSWORD);
        
        //4. Выбрать стейтмент 
        //  4.1 Statement                       SELECT * FROM users
        //Statement statement = connection.createStatement();  //autocommit = true  
        //ResultSet result = statement.executeQuery(SQLQuery.GET_USER_BY_ID+12);
        
        //  4.2 PreparedStatement               SELECT * FROM users WHERE id>?
        PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_USER_BY_ID); //select * from users where sdfsdf=1 AND sdfsdf=16 AND sdfsfs=21
        statement.setLong(1,11L);
        ResultSet result = statement.executeQuery();
        
        //  4.3 CallableStatement               Вызов хранимых процедур(функции)    drop_all_tables.sql
        
        //5. Парсим ResultSet
        while (result.next()) {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setUsername(result.getString("username"));
            user.setUserPassword(result.getString("user_password"));
            user.setCreated(result.getDate("created"));
            user.setChanged(result.getDate("changed"));
            users.add(user);
        }
        
        System.out.println(users);
    }
}
