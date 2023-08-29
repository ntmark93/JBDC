import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class UserDAOImpl implements UserDAO {
    Set<User> allUsers = new HashSet<>();

    @Override
    public Optional<User> getUser(int id) {
        try (Statement st = ConnectionFactory.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM info WHERE id =" + id)) {
            if (rs.next()) {
                return Optional.of(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean updatePassword(int id, String newPassword) {
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement("SELECT * FROM info WHERE id =" + id);
             ResultSet rs = ps.executeQuery("SELECT * FROM info WHERE id = " + id)) {
            if (rs.next()) {
                int b = ps.executeUpdate("UPDATE info SET password =" + newPassword);
                if (b != 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Optional<User> getUserByName(String name) {
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement("SELECT * FROM info WHERE name =" + "name");
             ResultSet rs = ps.executeQuery("SELECT * FROM info WHERE name =" + "name")) {
            if (rs.next()) {
                return Optional.of(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        try (Statement st = ConnectionFactory.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM info WHERE id =" + id)) {
            if (rs.next()) {
                int b = st.executeUpdate("DELETE FROM info WHERE id=" + id);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Set<User> getAllUsers() {
        try (Statement st = ConnectionFactory.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM info")) {
            if (rs.next()) {
                allUsers.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public boolean generateNewPassword(int id) {
        Random newPassword = new Random();
        int first = newPassword.nextInt(1000, 9999);
        int second = newPassword.nextInt(1000, 9999);
        int third = newPassword.nextInt(1000, 9999);
        try (Statement st = ConnectionFactory.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM info WHERE id =" + id)) {
            if (rs.next()) {
                int b = st.executeUpdate("UPDATE info SET password =" + first + second + third);
                if (b != 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
