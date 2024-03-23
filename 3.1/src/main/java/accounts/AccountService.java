package accounts;

import java.sql.*;

public class AccountService {
    private final Connection connection;

    public AccountService() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users",
                "root1", "root");
    }

    public void addNewUser(UserProfile userProfile) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO new_table (login, password) VALUES (?, ?)");
        statement.setString(1, userProfile.getLogin());
        statement.setString(2, userProfile.getPass());
        statement.executeUpdate();
    }

    public UserProfile getUserByLogin(String login) throws SQLException {
        String name = "Select login from new_table";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(name);
        while (resultSet.next()) {
            if (resultSet.getString("login").equalsIgnoreCase(login)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT password from new_table where login = ?");
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                String pass = resultSet.getString("password");
                return new UserProfile(login, pass, "email");
            }
        }
        return null;
    }
}
