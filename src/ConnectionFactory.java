import java.sql.*;
import java.util.Scanner;

public class ConnectionFactory {
    public static final String URL = System.getenv("url");
    public static final String USER = System.getenv("user");
    public static final String PASSWORD = System.getenv("password");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        getConnection();
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.getAllUsers();
        System.out.println(userDAO.getUserByName("'Zámbó Imre'"));
        System.out.println("Welcome to the menu!\nPlease choose an option");
        boolean b = false;
        while (!b) {
            System.out.println("1. Show all users\n" +
                    "2. Search user by ID\n" +
                    "3. Search user by name\n" +
                    "4. Change password\n" +
                    "5. Delete user\n" +
                    "6. Generate new password\n" +
                    "7. Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("All users: ");
                    System.out.println(userDAO.getAllUsers());
                    break;
                case 2:
                    System.out.println("Please enter the user ID");
                    System.out.println(userDAO.getUser(scanner.nextInt()));
                    break;
                case 3:
                    System.out.println("Please enter the user name");
                    System.out.println(userDAO.getUserByName(scanner.next()));
                    break;
                case 4:
                    System.out.println("Please enter the user ID, and the new password");
                    System.out.println(userDAO.updatePassword(scanner.nextInt(), scanner.next()));
                    break;
                case 5:
                    System.out.println("Please enter the user name to delete");
                    System.out.println(userDAO.deleteUser(scanner.nextInt()));
                    break;
                case  6:
                    System.out.println("Please enter the user ID to update the password");
                    System.out.println(userDAO.generateNewPassword(scanner.nextInt()));
                    break;
                case 7:
                    System.out.println("Have a nice day");
                    b = true;
            }
        }
    }
}
