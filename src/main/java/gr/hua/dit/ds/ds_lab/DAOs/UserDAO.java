package gr.hua.dit.ds.ds_lab.DAOs;

import gr.hua.dit.ds.ds_lab.Models.User;
import gr.hua.dit.ds.ds_lab.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAO {
    @Autowired
    private UserRepository userRepository;

    //Δημιουργία ή αποθήκευση χρήστη
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Εύρεση με βάση το ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    //Ενημέρωση χρήστη
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    //Διαγραφή Χρήστη
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //Εύρεση όλων των χρηστών
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}