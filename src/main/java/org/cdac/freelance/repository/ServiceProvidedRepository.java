package org.cdac.freelance.repository;

import org.cdac.freelance.entity.ServiceProvided;
import org.cdac.freelance.entity.ServiceProvidedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, ServiceProvidedId> {
    List<ServiceProvided> findByServiceId(int serviceId);
    List<ServiceProvided> findByProviderId(int providerId);
}
