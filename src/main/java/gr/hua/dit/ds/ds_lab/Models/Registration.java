package gr.hua.dit.ds.ds_lab.Models;

import jakarta.persistence.*;

@Entity
public class Registration {
    public static Object Status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Το μοναδικό αναγνωριστικό της εγγραφής

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event; // Το γεγονός στο οποίο ανήκει η εγγραφή

    private String volunteerName; // Το όνομα του εθελοντή
    private String email; // Το email του εθελοντή

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status; // Η κατάσταση της εγγραφής (PENDING, APPROVED, REJECTED)

    // Δημόσιος no-arg constructor (κατάλληλος για την JPA)
    public Registration() {
    }

    // Registration constructor
    public Registration(Long id, Event event, String volunteerName, String email, RegistrationStatus status) {
        this.id = id;
        this.event = event;
        this.volunteerName = volunteerName;
        this.email = email;
        this.status = status;
    }


    // Getters και Setters για κάθε πεδίο
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    // Μέθοδος για να επιστρέφει το ID του Event
    public Long getEventId() {
        return event != null ? event.getId() : null;
    }

    // Μέθοδος για να θέτει την κατάσταση ως APPROVED ή REJECTED
    public void setApproved(boolean b) {
        if (b) {
            this.status = RegistrationStatus.APPROVED;
        } else {
            this.status = RegistrationStatus.REJECTED;
        }
    }
}

