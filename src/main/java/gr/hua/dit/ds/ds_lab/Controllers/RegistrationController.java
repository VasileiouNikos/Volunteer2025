package gr.hua.dit.ds.ds_lab.Controllers;

import gr.hua.dit.ds.ds_lab.Models.*;
import gr.hua.dit.ds.ds_lab.Service.EventService;
import gr.hua.dit.ds.ds_lab.Service.OrganizationService;
import gr.hua.dit.ds.ds_lab.Service.RegistrationService; // Import της RegistrationService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/registrations") // Κοινό prefix για τα endpoints
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private EventService eventService;
    @Autowired
    private OrganizationService organizationService;

    // Δημιουργία νέας εγγραφής εθελοντή
    @PostMapping("/register/{eventId}")
    public String registerVolunteer(@ModelAttribute("eventId") Long eventId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Event event = eventService.getEventById(eventId);

        List<Registration> registrationsForEvent = registrationService.getRegistrationsByEvent(event.getId());
        for (Registration registration : registrationsForEvent) {
            if (registration.getVolunteerName().equals(user.getUsername())) {
                // Η καταχώρηση εθελοντή έχει ήδη γίνει, επιστρέφουμε στις εκδηλώσεις
                return "redirect:/events/all";
            }
        }

        // Αν δεν έχει γίνει καταχώρηση του εθελοντή για αυτή την εκδήλωση, συνεχίζουμε με την εγγραφή
        Registration registration = new Registration();

        registration.setEmail(user.getEmail());
        registration.setVolunteerName(user.getUsername());
        registration.setStatus(RegistrationStatus.PENDING);

        registration.setEvent(event);
        registrationService.registerVolunteer(registration);
        return "redirect:/events/all";
    }

    // Επιστροφή εγγραφών για συγκεκριμένο event
    @GetMapping("/event/{eventId}")
    public String getRegistrationsByEvent(@ModelAttribute("eventId") Long eventId, Model model, RedirectAttributes redirectAttributes) {
        registrationService.getRegistrationsByEvent(eventId);
        Event event = eventService.getEventById(eventId);
        redirectAttributes.addAttribute("organizationId", event.getOrganization().getId());
        return "redirect:/organizations/{organizationId}/events";
    }

    // Έγκριση εγγραφής
    @PatchMapping("/{id}/approve")
    public ResponseEntity<Registration> approveRegistration(@PathVariable Long id) {
        Registration approvedRegistration = registrationService.approveRegistration(id);
        return new ResponseEntity<>(approvedRegistration, HttpStatus.OK);
    }

    // Απόρριψη εγγραφής
    @PatchMapping("/{id}/reject")
    public ResponseEntity<Registration> rejectRegistration(@PathVariable Long id) {
        Registration rejectedRegistration = registrationService.rejectRegistration(id);
        return new ResponseEntity<>(rejectedRegistration, HttpStatus.OK);
    }

    // Διαγραφή εγγραφής
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

