package repository;

import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    //TODO: CHANGE LOGIC TO SECURITY
    public Boolean isValid(String username, String password) throws SQLException {
        User userFromDatabase = null;
        Connection connection = databaseService.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.IS_VALID);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();
        while (result.next()) {
            userFromDatabase = parseUser(result);
        }
        
/*        if (userFromDatabase == null || userFromDatabase.getUsername() == null || userFromDatabase.getUserPassword() == null) {
            return false;
        }
        if (userFromDatabase.getUsername().equals(username)) {
            return userFromDatabase.getUserPassword().equals(password);
        }*/
        return false;
    }

    //TODO: CHANGE LOGIC TO SECURITY
    public Boolean isContainsUserByUsername(String username) throws SQLException {
        Set<User> allUsers = getAllUsers();
        for (User user : allUsers) {
   /*         if (user.getUsername().equals(username)) {
                return true;
            }*/
        }
        return false;
    }

    public User parseUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setFirstName(result.getString("firstname"));
        user.setSecondName(result.getString("secondname"));
        user.setAge(result.getInt("age"));
        user.setCreated(result.getDate("created"));
        user.setChanged(result.getDate("changed"));
        return user;
    }

    public Boolean addUser(String firstName, String secondName, int age, String login, String password) throws SQLException {
        Connection connection = databaseService.getConnection();
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, firstName);
            createUserStatement.setString(2, secondName);
            createUserStatement.setDate(3, new Date(System.currentTimeMillis()));
            createUserStatement.setDate(4, new Date(System.currentTimeMillis()));
            createUserStatement.setInt(5, age);
            createUserStatement.executeUpdate();

            ResultSet generatedKeys = createUserStatement.getGeneratedKeys();
            long userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }

            PreparedStatement createSecurityStatement = connection.prepareStatement(SQLQuery.CREATE_SECURITY);
            createSecurityStatement.setString(1, login);
            createSecurityStatement.setString(2, password);
            createSecurityStatement.setLong(3, userId);
            createSecurityStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }
        return false;
    }
}
