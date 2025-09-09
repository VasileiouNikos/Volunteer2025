package gr.hua.dit.ds.ds_lab.Controllers;

import gr.hua.dit.ds.ds_lab.Models.Organization;
import gr.hua.dit.ds.ds_lab.Models.Role;
import gr.hua.dit.ds.ds_lab.Models.User;
import gr.hua.dit.ds.ds_lab.Models.LoginRequest;
import gr.hua.dit.ds.ds_lab.Repositories.RoleRepository;
import gr.hua.dit.ds.ds_lab.Service.OrganizationService;
import gr.hua.dit.ds.ds_lab.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users") // Βασική διαδρομή για τις λειτουργίες χρηστών
public class UserController {

    @Autowired
    private UserService userService; // Υπηρεσία για τη διαχείριση χρηστών

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizationService organizationService;

    // Λίστα χρηστών
    @GetMapping("/list")
    public String getAllUsers(Model model) {
        Iterable<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }

    // Φόρμα καταχώρησης νέου χρήστη
    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    // Φόρμα σύνδεσης χρήστη
    @GetMapping("/login")
    public String loginUserForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "user/login";
    }

    // Καταχώρηση νέου χρήστη
    // Προσθέτουμε ένα επιπλέον request parameter "selectedRole" για να καθορίσουμε αν ο χρήστης είναι ORGANIZATION ή USER
    @PostMapping("/api/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam(name = "selectedRole", required = false) String selectedRole) {
        if ("ORGANIZATION".equalsIgnoreCase(selectedRole)) {
            Role role = roleRepository.findByName("ORGANIZATION");
            user.setRoles(Set.of(role));
            // Δημιουργία οργανισμού για τον λογαριασμό οργανισμού
            Organization organization = new Organization();

            // Δημιουργούμε το όνομα του οργανισμού από το username του χρήστη
            String username = user.getUsername();
            String orgNameCapitalisedWithSpaces = Arrays.stream(username.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                    .collect(Collectors.joining(" "));
            String orgNameLowerNoSpaces = orgNameCapitalisedWithSpaces.toLowerCase().replace(" ", "");

            organization.setEmail("support@" + orgNameLowerNoSpaces + ".com");
            organization.setName(orgNameCapitalisedWithSpaces);
            organizationService.createOrganization(organization);
        } else {
            Role role = roleRepository.findByName("USER");
            user.setRoles(Set.of(role));
        }

        // Δημιουργία του χρήστη
        userService.registerUser(user);
        return "redirect:/users/login";
    }

    // Σύνδεση χρήστη
    @PostMapping("/api/login")
    public String loginUser(@ModelAttribute("loginRequest") LoginRequest loginRequest, Model model, HttpServletRequest request) {
        try {
            User authenticatedUser = userService.authenticateUser(loginRequest);
            // Εάν γίνει επιτυχής σύνδεση, τότε δημιουργούμε ένα token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // Δημιουργούμε το session
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/login";
        }
    }

    // Εύρεση χρήστη με βάση το ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Φόρμα Ενημέρωσης Στοιχείων Χρήστη
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    // Ενημέρωση στοιχείων χρήστη
    @PostMapping("update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/users/list";
    }

    // Διαγραφή χρήστη
    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/users/list";
    }
}
