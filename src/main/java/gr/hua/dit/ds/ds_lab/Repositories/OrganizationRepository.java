package gr.hua.dit.ds.ds_lab.Repositories;

import gr.hua.dit.ds.ds_lab.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Αναζήτηση οργανισμού με βάση το όνομα
    Optional<Organization> findByName(String name);
}
