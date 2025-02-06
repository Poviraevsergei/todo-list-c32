package repository;

public interface SQLQuery {
    String GET_ALL_USERS = "SELECT * FROM users";
    String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
    String IS_VALID = "SELECT * FROM users WHERE username = ? AND user_password = ?";
}
