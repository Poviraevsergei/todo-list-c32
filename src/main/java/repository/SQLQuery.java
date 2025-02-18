package repository;

public interface SQLQuery {
    String GET_ALL_USERS = "SELECT * FROM users";
    String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    String GET_SECURITY_BY_LOGIN = "SELECT * FROM security WHERE login=?";
    String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
    String IS_VALID = "SELECT * FROM users WHERE id=(SELECT user_id FROM security WHERE login=? AND password=?)";
    String CREATE_USER = "INSERT INTO users (id, firstname, secondname, created, changed, age) " +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    String CREATE_SECURITY = "INSERT INTO security (id, login, password, user_id) VALUES (DEFAULT, ?, ?, ?)";
    String CALL_DELETE_USER_BY_FIRSTNAME = "CALL delete_user_by_firstname('Bill')";
}
