package gr.hua.dit.ds.ds_lab.Service;

import gr.hua.dit.ds.ds_lab.Models.Event;
import gr.hua.dit.ds.ds_lab.Models.Organization;
import gr.hua.dit.ds.ds_lab.Models.Registration;
import gr.hua.dit.ds.ds_lab.Models.RegistrationStatus;
import gr.hua.dit.ds.ds_lab.Repositories.EventRepository;
import gr.hua.dit.ds.ds_lab.Repositories.OrganizationRepository;
import gr.hua.dit.ds.ds_lab.Repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Δημιουργία νέου οργανισμού.
     */
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    /**
     * Ενημέρωση προφίλ οργανισμού.
     */
    public Organization updateOrganization(Long id, Organization updatedOrganization) {
        Organization existingOrganization = findOrganizationById(id);
        existingOrganization.setName(updatedOrganization.getName());
        existingOrganization.setEmail(updatedOrganization.getEmail());
        return organizationRepository.save(existingOrganization);
    }

    /**
     * Επιστροφή όλων των εγγραφών για έναν οργανισμό.
     */
    public List<Registration> getRegistrationsByOrganization(Long organizationId) {
        return registrationRepository.findByEvent_Organization_Id(organizationId);
    }

    /**
     * Επιβεβαίωση εγγραφής εθελοντή.
     */
    public Registration approveRegistration(Long registrationId) {
        Registration registration = findRegistrationById(registrationId);
        registration.setStatus(RegistrationStatus.APPROVED); // Χρήση του enum
        return registrationRepository.save(registration);
    }

    /**
     * Απόρριψη εγγραφής εθελοντή.
     */
    public Registration rejectRegistration(Long registrationId) {
        Registration registration = findRegistrationById(registrationId);
        registration.setStatus(RegistrationStatus.REJECTED); // Χρήση του enum
        return registrationRepository.save(registration);
    }

    /**
     * Επιστροφή όλων των εκδηλώσεων ενός οργανισμού.
     */
    public List<Event> getEventsByOrganization(Long id) {
        Organization organization = findOrganizationById(id);
        return organization.getEvents();
    }

    /**
     * Επιστροφή όλων των εγγραφών για μία εκδήλωση ενός οργανισμού.
     */
    public List<Registration> getRegistrationsForEvent(Long organizationId, Long eventId) {
        // Βρίσκουμε το event από το eventId
        Event event = findEventById(eventId);

        // Ελέγχουμε εάν το event ανήκει στον οργανισμό με βάση το organizationId
        if (!event.getOrganization().equals(organizationId)) {
            throw new IllegalArgumentException("Event does not belong to the specified organization.");
        }

        // Επιστρέφουμε τις εγγραφές για το συγκεκριμένο event
        return registrationRepository.findByEvent_Id(eventId);
    }

    /**
     * Καταχώριση νέου οργανισμού.
     */
    public Organization registerOrganization(Organization organization) {
        // Επιπλέον επεξεργασία αν απαιτείται (π.χ., έλεγχος διπλότυπων)
        return organizationRepository.save(organization);
    }

    /**
     * Εύρεση όλων των οργανισμών
     */
    public List<Organization> findAllOrganizations() {
        return organizationRepository.findAll();
    }

    /**
     * Μέθοδος για εύρεση οργανισμού από το ID.
     */
     public Organization findOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id: " + id));
    }

    /**
     * Βοηθητική μέθοδος για εύρεση εγγραφής από το ID.
     */
    private Registration findRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found with id: " + id));
    }

    public Event createEvent(Long organizationId, Event event) {
        Organization organization = findOrganizationById(organizationId);

        // Βεβαιωνόμαστε ότι η εκδήλωση ανήκει στον οργανισμό που την δημιουργεί
        event.setOrganization(organization);

        return eventRepository.save(event);
    }

    /**
     * Βοηθητική μέθοδος για εύρεση εκδήλωσης από το ID.
     */
    private Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));
    }

}
