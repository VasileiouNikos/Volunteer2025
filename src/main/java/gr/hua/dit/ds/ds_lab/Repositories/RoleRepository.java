package gr.hua.dit.ds.ds_lab.Repositories;


import gr.hua.dit.ds.ds_lab.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); // Επιστρέφει έναν ρόλο με βάση το όνομα
}
