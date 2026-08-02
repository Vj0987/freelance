package org.cdac.freelance.repository;

import org.cdac.freelance.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByProviderId(int providerId);

    List<Contract> findByClientId(int clientId);

    boolean existsByProviderIdAndClientIdAndEscrowId(int providerId, int clientId, int escrowId);
}
