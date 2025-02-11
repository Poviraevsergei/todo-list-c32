import model.User;
import repository.SQLQuery;
import repository.UserRepository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserRepository userRepository = new UserRepository();
        Connection connection = userRepository.databaseService.getConnection();
        CallableStatement statement = connection.prepareCall(SQLQuery.CALL_DELETE_USER_BY_FIRSTNAME);
        statement.execute();
    }
}