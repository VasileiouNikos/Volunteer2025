package gr.hua.dit.ds.ds_lab.Service;

import gr.hua.dit.ds.ds_lab.DAOs.UserDAO;
import gr.hua.dit.ds.ds_lab.Models.LoginRequest;
import gr.hua.dit.ds.ds_lab.Models.User;
import gr.hua.dit.ds.ds_lab.Repositories.UserRepository;
import gr.hua.dit.ds.ds_lab.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;


    // Εύρεση όλων των χρηστών
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Καταχώρηση νέου χρήστη
    public User registerUser(User user) {
        return userDAO.saveUser(user);
    }

    // Αυθεντικοποίηση χρήστη
    public User authenticateUser(LoginRequest loginRequest) {
        Optional<User> user = userDAO.findUserByUsername(loginRequest.getUsername());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            // Επιστροφή ενός token για αυθεντικοποίηση (προσομοίωση εδώ)
            return user.get();
        }
        throw new RuntimeException("Μη Έγκυρα Διαπιστευτήρια");
    }

    // Εύρεση χρήστη με βάση το ID
    public User getUserById(long id) {
        return userDAO.findUserById(id)
                .orElseThrow(() -> new RuntimeException("Ο χρήστης δεν βρέθηκε"));
    }

    // Ενημέρωση χρήστη
    public void updateUser(Long id, User user) {
        User existingUser = userDAO.findUserById(id)
                .orElseThrow(() -> new RuntimeException("Ο χρήστης δεν βρέθηκε"));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        userDAO.updateUser(existingUser);
    }

    // Διαγραφή χρήστη
    public void deleteUser(long id) {
        User existingUser = userDAO.findUserById(id)
                .orElseThrow(() -> new RuntimeException("Ο χρήστης δεν βρέθηκε"));

        userDAO.deleteUser(id);
    }

}
