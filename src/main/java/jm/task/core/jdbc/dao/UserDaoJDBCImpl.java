package jm.task.core.jdbc.dao;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            String command = "CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name TEXT,"
                    + "lastName TEXT,"
                    + "age TINYINT UNSIGNED, "
                    + "PRIMARY KEY(id))";

            statement.executeUpdate(command);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            String command = "DROP TABLE IF EXISTS users";

            statement.executeUpdate(command);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String command = "INSERT INTO users(name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String command = "DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = null;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            String command = "SELECT * FROM users";

            ResultSet set = statement.executeQuery(command);
            list = new ArrayList<>();

            while (set.next()) {
                User user = new User(set.getString("name"), set.getString("lastName"),
                        set.getByte("age"));
                list.add(user);
            }

            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String command = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(command)) {

            statement.executeUpdate(command);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
