package gr.hua.dit.ds.ds_lab.Controllers;

import gr.hua.dit.ds.ds_lab.Models.Event;
import gr.hua.dit.ds.ds_lab.Models.Organization;
import gr.hua.dit.ds.ds_lab.Models.Registration;
import gr.hua.dit.ds.ds_lab.Service.EventService;
import gr.hua.dit.ds.ds_lab.Service.OrganizationService;
import gr.hua.dit.ds.ds_lab.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RegistrationService registrationService;



    @GetMapping("/create/{organizationId}")
    public String getCreateEventForOrganizationForm(@PathVariable Long organizationId, Model model) {
        Organization organization = organizationService.findOrganizationById(organizationId);
        Event newEvent = new Event();
        newEvent.setOrganization(organization); // Ανάθεση του οργανισμού για την εκδήλωση
        model.addAttribute("event", newEvent);
        return "event/create_organization_event";
    }

    @PostMapping("/create/{organizationId}")
    public String createEventForOrganization(@PathVariable String organizationId, @ModelAttribute Event event, @RequestParam("eventDate") String eventDate, RedirectAttributes redirectAttributes) {
        event.setEventDate(LocalDate.parse(eventDate));
        eventService.createEvent(event);
        redirectAttributes.addAttribute("organizationId", organizationId);
        return "redirect:/organizations/{organizationId}/events";
    }


    @GetMapping("/all")
    public String getAllEvents(Model model, Principal principal) {
        // Παίρνουμε όλες τις εκδηλώσεις
        List<Event> events = eventService.getAllEvents();
        // Φτιάχνουμε ένα map για κάθε εγγραφή σε εκδήλωση
        Map<Long, Registration> eventRegistrationMap = new HashMap<>();

        for (Event event : events) {
            List<Registration> registrationsForEvent = registrationService.getRegistrationsByEvent(event.getId());
            // Bρίσκουμε την εγγραφή του χρήστη στην εκδήλωση για το event αν υπάρχει
            registrationsForEvent.stream()
                    .filter(reg -> reg.getVolunteerName() != null &&
                            reg.getVolunteerName().trim().equalsIgnoreCase(principal.getName().trim()))
                    .findFirst()
                    .ifPresent(registration -> eventRegistrationMap.put(event.getId(), registration));
        }

        model.addAttribute("events", events);
        model.addAttribute("eventRegistrationMap", eventRegistrationMap);

        return "event/events"; // Ανακατεύθυνση στις εκδηλώσεις
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/approve")
    public String approveEvent(@PathVariable Long id) {
        eventService.approveEvent(id);
        return "redirect:/events/all"; // Ανακατεύθυνση μετά την έγκριση
    }

    @PostMapping("/{id}/reject")
    public String rejectEvent(@PathVariable Long id) {
        eventService.rejectEvent(id);
        return "redirect:/events/all"; // Ανακατεύθυνση μετά την απόρριψη
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}





