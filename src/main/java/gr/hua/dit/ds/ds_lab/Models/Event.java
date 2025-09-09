package gr.hua.dit.ds.ds_lab.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Event {

    // Πρωτεύον κλειδί για την κλάση Event
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Όνομα του Event
    private String name;

    // Περιγραφή του Event
    private String description;

    // Ημερομηνία Event
    @Column(name = "event_date")
    private LocalDate eventDate;

    // Πεδίο για την ένδειξη έγκρισης (true/false)
    private boolean approved;


    // Κατάσταση του Event, χρησιμοποιώντας το Enum EventStatus
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    // Συσχέτιση πολλών προς ένα με την κλάση Organization
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id") // Ορισμός συσχέτισης
    private Organization organization;

    // Κατασκευαστής με παραμέτρους
    public Event(String name, String description, LocalDate eventDate, EventStatus status, Organization organization) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.status = status;
        this.organization = organization;
        this.approved = false; // Default τιμή
    }

    // Κατασκευαστής με παραμέτρους που περιλαμβάνει το id για να ταιριάζει με το EventTest
    public Event(Long id, String name, String description, boolean approved, EventStatus status, Organization organization, LocalDate eventDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.approved = approved;
        this.status = status;
        this.organization = organization;
        this.eventDate = eventDate;
    }

    public Event() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Organization getOrganization() {
        return organization;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }


    public void setOrganization(Organization organization) {

        this.organization = organization;
    }


}
