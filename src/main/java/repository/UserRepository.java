package repository;

import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
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

    public Boolean isValid(String login, String password) throws SQLException {
        Connection connection = databaseService.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.IS_VALID);
        statement.setString(1, login);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();
        return result.next();
    }

    public Boolean isContainsUserByUsername(String login) throws SQLException {
        Connection connection = databaseService.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_SECURITY_BY_LOGIN);
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        return result.next();
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
