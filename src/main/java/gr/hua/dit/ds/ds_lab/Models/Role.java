package gr.hua.dit.ds.ds_lab.Models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;


@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Αυτόματη δημιουργία μοναδικού αναγνωριστικού για τον ρόλο
    private Long id;

    @Column(nullable = false)
    // Υποχρεωτικό και μοναδικό πεδίο για το όνομα του ρόλου
    private String name;

    // Δημόσιος no-arg constructor (κατάλληλος για την JPA)
    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        // Επιστρέφει το όνομα του ρόλου ως Authority
        return name;
    }

}