package gr.hua.dit.ds.ds_lab.Controllers;

import gr.hua.dit.ds.ds_lab.Models.Organization;
import gr.hua.dit.ds.ds_lab.Models.Event;
import gr.hua.dit.ds.ds_lab.Models.Registration;
import gr.hua.dit.ds.ds_lab.Models.User;
import gr.hua.dit.ds.ds_lab.Service.OrganizationService;
import gr.hua.dit.ds.ds_lab.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/organizations")
public class OrganizationController {



    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/all")
    public String getAllOrganizations(Model model) {
        List<Organization> organizations = organizationService.findAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization/organizations";
    }

    // Φόρμα Δημιουργίας νέου οργανισμού
    @GetMapping("/register")
    public String registerOrganizationForm(Model model) {
        Organization organization = new Organization();
        model.addAttribute("organization", organization);
        return "organization/register_organization";
    }

    // Δημιουργία νέου λογαριασμού Οργανισμού
    @PostMapping("/register")
    public String registerOrganization(@ModelAttribute("organization") Organization organization) {
        organizationService.registerOrganization(organization);
        return "redirect:/organizations/all";
    }

    // Ενημέρωση προφίλ Οργανισμού
    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id, @RequestBody Organization organization) {
        Organization updatedOrganization = organizationService.updateOrganization(id, organization);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }

    // Επιστροφή όλων των εκδηλώσεων που δημοσίευσε ο Οργανισμός
    @GetMapping("/{id}/events")
    public String getEventsByOrganization(@PathVariable Long id, Model model) {
        List<Event> events = organizationService.getEventsByOrganization(id);
        model.addAttribute("events", events);

        Organization organization = organizationService.findOrganizationById(id);
        model.addAttribute("organizationName", organization.getName());

        // Βρίσκουμε όλες τις εγγραφές εθελοντών για κάθε εκδήλωση
        List<Registration> allRegistrations = new ArrayList<>();
        for (Event event : events) {
            List<Registration> registrationsForEvent = registrationService.getRegistrationsByEvent(event.getId());
            allRegistrations.addAll(registrationsForEvent);
        }

        model.addAttribute("registrations", allRegistrations);
        return "event/organization_events"; // Ανακατεύθυνση στις εκδηλώσεις του οργανισμού
    }

    // Επισκόπηση συμμετεχόντων σε συγκεκριμένη εκδήλωση
    @GetMapping("/{organizationId}/events/{eventId}/registrations")
    public ResponseEntity<List<Registration>> getRegistrationsForEvent(
            @PathVariable Long organizationId, @PathVariable Long eventId) {
        List<Registration> registrations = organizationService.getRegistrationsForEvent(organizationId, eventId);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    // Επιβεβαίωση συμμετοχής εθελοντή
    @PostMapping("/{organizationId}/events/{eventId}/registrations/{registrationId}/approve")
    public String approveRegistration(
            @ModelAttribute("organizationId") Long organizationId, @ModelAttribute("eventId") Long eventId, @ModelAttribute("registrationId") Long registrationId, RedirectAttributes redirectAttributes) {
        registrationService.approveRegistration(registrationId);
        redirectAttributes.addAttribute("organizationId", organizationId);
        return "redirect:/organizations/{organizationId}/events";
    }

    // Απόρριψη συμμετοχής εθελοντή
    @PostMapping("/{organizationId}/events/{eventId}/registrations/{registrationId}/reject")
    public String rejectRegistration(
            @PathVariable Long organizationId, @PathVariable Long eventId, @PathVariable Long registrationId,  RedirectAttributes redirectAttributes) {
        registrationService.rejectRegistration(registrationId);
        redirectAttributes.addAttribute("organizationId", organizationId);
        return "redirect:/organizations/{organizationId}/events";
    }
}

