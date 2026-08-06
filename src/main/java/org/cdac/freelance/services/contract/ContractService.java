package org.cdac.freelance.services.contract;

import org.cdac.freelance.dto.contract.ContractResponseDTO;

import java.util.List;

public interface ContractService {

    boolean createContract(int offerId);

    List<ContractResponseDTO> getAllContractByProviderId(int providerId);

    List<ContractResponseDTO> getAllContractByClientId(int clientId);

    boolean statusCompleted(int contractId);

    boolean statusConfirmed(int contractId);

    boolean statusCancelled(int contractId);

}
