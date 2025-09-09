package gr.hua.dit.ds.ds_lab.Repositories;

import gr.hua.dit.ds.ds_lab.Models.Registration;
import gr.hua.dit.ds.ds_lab.Models.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEvent_IdAndStatus(Long eventId, RegistrationStatus status);

    List<Registration> findByEvent_Id(Long eventId);

    List<Registration> findByEvent_Organization_Id(Long organizationId);
}
