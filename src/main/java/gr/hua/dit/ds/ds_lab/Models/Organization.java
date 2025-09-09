package gr.hua.dit.ds.ds_lab.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "organization")
public class Organization {

    // Πρωτεύον κλειδί για την κλάση Organization
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Όνομα του Organization
    private String name;

    // Email του Organization
    private String email;



    // Σχέση με Event - Ένας οργανισμός μπορεί να έχει πολλά events
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    // Κατασκευαστής με παραμέτρους
    public Organization(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Κατασκευαστής χωρίς παραμέτρους
    public Organization() {
    }

    // Getters και Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
