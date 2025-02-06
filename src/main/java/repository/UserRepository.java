package repository;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserRepository {

    public DatabaseService databaseService;

    public UserRepository() {
        databaseService = new DatabaseService();
    }

    public Set<User> getAllUsers() throws SQLException {
        Set<User> users = new HashSet<>();
        Connection connection = databaseService.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_ALL_USERS);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            users.add(parseUser(result));
        }
        return users;
    }

    public Boolean isValid(String username, String password) throws SQLException {
        User userFromDatabase = null;
        Connection connection = databaseService.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.IS_VALID);
        statement.setString(1,username);
        statement.setString(2,password);
        
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            userFromDatabase = parseUser(result);
        }
        
        if (userFromDatabase == null || userFromDatabase.getUsername() == null || userFromDatabase.getUserPassword() == null) {
            return false;
        }
        if (userFromDatabase.getUsername().equals(username)) {
            return userFromDatabase.getUserPassword().equals(password);
        }
        return false;
    }

    public Boolean isContainsUserByUsername(String username) throws SQLException {
        Set<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User parseUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setUsername(result.getString("username"));
        user.setUserPassword(result.getString("user_password"));
        user.setCreated(result.getDate("created"));
        user.setChanged(result.getDate("changed"));
        return user;
    }
}
