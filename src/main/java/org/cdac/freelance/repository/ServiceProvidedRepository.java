package org.cdac.freelance.repository;

import org.cdac.freelance.entity.ServiceProvided;
import org.cdac.freelance.entity.ServiceProvidedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, ServiceProvidedId> {
    List<ServiceProvided> findByIdServiceId(int serviceId);
    List<ServiceProvided> findByIdProviderId(int providerId);
    Optional<ServiceProvided> findByIdServiceIdAndIdProviderId(
            Integer serviceId,
            Integer providerId);
}
