package org.cdac.freelance.repository;

import org.cdac.freelance.dto.offers.OfferResponseDTO;
import org.cdac.freelance.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffersRepository extends JpaRepository<Offers, Integer> {
    boolean existsByClientIdAndProviderIdAndServiceId(int clientId, int providerId, int serviceId);
    List<Offers> findByClientId(int clientId);
    List<Offers> findByProviderId(int providerId);
}
