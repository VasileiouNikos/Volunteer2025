package gr.hua.dit.ds.ds_lab.Service;

import gr.hua.dit.ds.ds_lab.Models.Event;
import gr.hua.dit.ds.ds_lab.Models.EventStatus;
import gr.hua.dit.ds.ds_lab.Models.Organization;
import gr.hua.dit.ds.ds_lab.Repositories.EventRepository;
import gr.hua.dit.ds.ds_lab.Repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    // Δημιουργία νέου Event
    public Event createEvent(Event event) {
        // Αν η οργάνωση δεν υπάρχει, τη δημιουργούμε
        Organization organization = event.getOrganization();
        if (organization != null && !organizationRepository.existsById(organization.getId())) {
            organizationRepository.save(organization);  // Δημιουργούμε την οργάνωση αν δεν υπάρχει
        }
        event.setStatus(EventStatus.PENDING);
        return eventRepository.save(event);
    }

    // Επιστροφή όλων των Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Αναζήτηση Event με βάση το ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null); // Αν δεν υπάρχει το Event, επιστρέφει null
    }

    // Αλλαγή κατάστασης Event (Έγκριση ή Απόρριψη)
    public Event updateEventStatus(Long id, EventStatus status, boolean approved) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setStatus(status);
            event.setApproved(approved);
            return eventRepository.save(event); // Επιστροφή της τροποποιημένης εκδήλωσης
        }
        return null; // Επιστροφή null αν δεν υπάρχει η εκδήλωση
    }

    // Έγκριση Event
    public Event approveEvent(Long id) {
        return updateEventStatus(id, EventStatus.APPROVED, true);
    }

    // Απόρριψη Event
    public Event rejectEvent(Long id) {
        return updateEventStatus(id, EventStatus.REJECTED, false);
    }

    // Διαγραφή Event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
