package gr.hua.dit.ds.ds_lab.Repositories;

import gr.hua.dit.ds.ds_lab.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository για τη διαχείριση χρηστών στην εφαρμογή
public interface UserRepository extends JpaRepository<User, Long> {
    // Εύρεση χρήστη με βάση το όνομα χρήστη
     Optional<User> findByUsername(String username);
}
