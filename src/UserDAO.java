import java.util.Optional;
import java.util.Set;

public interface UserDAO {
    Optional<User> getUser(int id);
    Set<User> getAllUsers();
}
