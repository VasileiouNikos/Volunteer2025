package gr.hua.dit.ds.ds_lab.Service;

import gr.hua.dit.ds.ds_lab.Models.Registration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Service για τη διαχείριση εγγραφών εθελοντών
@Service
public class RegistrationService {
    private List<Registration> registrations = new ArrayList<>(); // Λίστα όλων των εγγραφών
    private Long idCounter = 1L; // Μετρητής για μοναδικά IDs

    // Δημιουργία νέας εγγραφής εθελοντή
    public Registration registerVolunteer(Registration registration) {
        registration.setId(idCounter++); // Ρύθμιση μοναδικού ID
        registrations.add(registration); // Προσθήκη στη λίστα εγγραφών
        return registration; // Επιστροφή της εγγραφής
    }

    // Επιστροφή όλων των εγγραφών για συγκεκριμένο γεγονός (με βάση το eventId)
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        List<Registration> eventRegistrations = new ArrayList<>();
        for (Registration registration : registrations) {
            if (registration.getEventId().equals(eventId)) { // Έλεγχος αν ανήκει στο event
                eventRegistrations.add(registration);
            }
        }
        return eventRegistrations; // Επιστροφή της λίστας εγγραφών για το event
    }

    // Έγκριση εγγραφής (ενημέρωση του approved σε true)
    public Registration approveRegistration(Long id) {
        Registration registration = getRegistrationById(id);
        if (registration != null) {
            registration.setApproved(true); // Ενημέρωση κατάστασης
        }
        return registration;
    }

    // Απόρριψη εγγραφής (ενημέρωση του approved σε false)
    public Registration rejectRegistration(Long id) {
        Registration registration = getRegistrationById(id);
        if (registration != null) {
            registration.setApproved(false); // Ενημέρωση κατάστασης
        }
        return registration;
    }

    // Διαγραφή εγγραφής με βάση το ID
    public boolean deleteRegistration(Long id) {
        Optional<Registration> registrationOptional = registrations.stream()
                .filter(registration -> registration.getId().equals(id)) // Αναζήτηση εγγραφής
                .findFirst();
        if (registrationOptional.isPresent()) {
            registrations.remove(registrationOptional.get()); // Αφαίρεση από τη λίστα
            return true; // Επιστροφή επιτυχίας
        }
        return false; // Επιστροφή αποτυχίας αν η εγγραφή δε βρέθηκε
    }

    // Βοηθητική μέθοδος για την εύρεση εγγραφής με βάση το ID
    private Registration getRegistrationById(Long id) {
        return registrations.stream()
                .filter(registration -> registration.getId().equals(id)) // Φιλτράρισμα με βάση το ID
                .findFirst()
                .orElse(null); // Επιστροφή null αν δε βρέθηκε
    }
}
