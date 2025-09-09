package gr.hua.dit.ds.ds_lab.SampleData;

import java.util.Set;

import gr.hua.dit.ds.ds_lab.Models.*;
import gr.hua.dit.ds.ds_lab.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private OrganizationRepository organizationRepository;


    @Override
    public void run(String... args) {

        // Διαγραφή προηγούμενων δεδομένων από τους πίνακες
        registrationRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        organizationRepository.deleteAll();

        // Δημιουργία ρόλων
        Role adminRole = roleRepository.save(new Role(null, "ADMIN")); // ρόλος διαχειριστή
        Role userRole = roleRepository.save(new Role(null, "USER")); // ρόλος χρήστη
        Role organizationRole = roleRepository.save(new Role(null, "ORGANIZATION")); // ρόλος οργάνωσης


        // Δημιουργία χρηστών
        User admin1 = new User(null, "admin", "admin@gmail.com", "admin123", true, Set.of(adminRole));
        User user1 = new User(null, "John Wilson", "johnW@gmail.com", "johnWil123", true, Set.of(userRole));
        User user2 = new User(null, "George Vas", "george@gmail.com", "george123", true, Set.of(userRole));
        User user3 = new User(null, "Nick Vas", "nick@gmail.com", "nick123", true, Set.of(userRole));
        userRepository.save(admin1);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // Δημιουργία χρηστών οργανισμών
        User orgUser1 = new User(null, "green_tomorrow", "info@greentomorrow.org", "greentom123", true, Set.of(organizationRole));
        User orgUser2 = new User(null, "eco_friendly", "contact@ecofriendly.com", "ecofriend123", true, Set.of(organizationRole));
        User orgUser3 = new User(null, "community_help", "support@communityhelp.com", "communityhelp123", true, Set.of(organizationRole));
        userRepository.save(orgUser1);
        userRepository.save(orgUser2);
        userRepository.save(orgUser3);

        //Δημιουργία Οργανισμών
        Organization org1=new Organization("Green Tomorrow", "info@greentomorrow.org");
        Organization org2=new Organization("Eco Friendly", "contact@ecofriendly.com");
        Organization org3=new Organization("Community Help", "support@communityhelp.com");
        organizationRepository.save(org1);
        organizationRepository.save(org2);
        organizationRepository.save(org3);

        // Δημιουργία εκδηλώσεων
        Event event1 = new Event("Beach Cleanup", "Cleaning the beach and picking up trash", LocalDate.of(2025, 10, 18), EventStatus.PENDING, org1);
        Event event2 = new Event("Tree Planting", "Planting trees in the burnt forest", LocalDate.of(2025, 8, 22), EventStatus.APPROVED, org2);
        Event event3 = new Event("Cooking Food", "Cooking food for the homeless", LocalDate.of(2025, 8, 17), EventStatus.APPROVED, org3);
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        // Δημιουργία εγγραφών εθελοντών
        Registration reg1 = new Registration(null, event1, "Vik Bale", "VikBale@gmail.com", RegistrationStatus.PENDING);
        Registration reg2 = new Registration(null, event1, "Christofer Nolan", "ChrisNolan@gmail.com", RegistrationStatus.APPROVED);
        Registration reg3 = new Registration(null, event2, "Mary Brown", "MaryBrown@yahoo.com", RegistrationStatus.APPROVED);
        Registration reg4 = new Registration(null, event3, "Christy Zac", "ChristyZ@gmail.com", RegistrationStatus.REJECTED);
        registrationRepository.save(reg1);
        registrationRepository.save(reg2);
        registrationRepository.save(reg3);
        registrationRepository.save(reg4);

    }

}


