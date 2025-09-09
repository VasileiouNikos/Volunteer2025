package gr.hua.dit.ds.ds_lab.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        // Ελέγχουμε αν ο χρήστης είναι συνδεδεμένος
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // Αν είναι, κανουμε ανακατεύθυνση στην αρχική
            return "redirect:/dashboard";
        }
        // Αν δεν είναι, κάνουμε ανακατεύθυνση στην σελίδα σύνδεσης
        return "redirect:/users/login";
    }

    @GetMapping("dashboard")
    public String dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));

        boolean isOrganization = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ORGANIZATION"));

        if (isAdmin) {
            // Εάν είναι διαχειριστής, τότε του δείχνουμε αυτή την σελίδα
            return "admin/dashboard";
        } else if (isOrganization) {
            // Εάν είναι οργανισμός του δείχνουμε την σελίδα όλων τον οργανισμών
            return "redirect:/organizations/all";
        } else {
            // Εάν είναι εθελοντής του δείχνουμε την σελίδα των εκδηλώσεων
            return "redirect:/events/all";
        }
    }
}
