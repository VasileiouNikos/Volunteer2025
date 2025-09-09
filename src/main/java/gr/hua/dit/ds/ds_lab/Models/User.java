package gr.hua.dit.ds.ds_lab.Models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // αλλαγή ονόματος πίνακα επειδή το user είναι δεσμευμένη λέξη
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Αυτόματη δημιουργία μοναδικού αναγνωριστικού για τον χρήστη
    private Long id;

    @Column(nullable = false, unique = true)
    // Υποχρεωτικό και μοναδικό πεδίο για το όνομα χρήστη
    private String username;

    @Column(nullable = false, unique = true)
    // Υποχρεωτικό και μοναδικό πεδίο για το email του χρήστη
    private String email;

    @Column(nullable = false)
    // Υποχρεωτικό πεδίο για τον κωδικό πρόσβασης του χρήστη
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    // Σχέση "πολλοί προς πολλούς" με την οντότητα 'Role' για τους ρόλους του χρήστη
    @JoinTable(
            name = "user_roles", // Ο πίνακας join που θα αποθηκεύει τους ρόλους
            joinColumns = @JoinColumn(name = "user_id"), // Στήλη για το user_id
            inverseJoinColumns = @JoinColumn(name = "role_id") // Στήλη για το role_id
    )
    private Set<Role> roles = new HashSet<>();


    @Column(nullable = false)
    // Υποχρεωτικό πεδίο για τον έλεγχο αν ο χρήστης είναι ενεργός ή όχι
    private boolean enabled;


    // Δημόσιος no-arg constructor (κατάλληλος για την JPA)
    public User() {
    }

    // constructor user
    public User(Long id, String username, String email, String password, boolean enabled, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabledField() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    // Μέθοδοι για το UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Επιστρέφει τους ρόλους του χρήστη ως συλλογή από Authorities
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Επιστρέφει αν ο λογαριασμός του χρήστη δεν έχει λήξει
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Επιστρέφει αν ο λογαριασμός του χρήστη δεν είναι κλειδωμένος
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Επιστρέφει αν τα πιστοποιητικά του χρήστη δεν έχουν λήξει
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Επιστρέφει αν ο χρήστης είναι ενεργός
        return enabled;
    }
}

