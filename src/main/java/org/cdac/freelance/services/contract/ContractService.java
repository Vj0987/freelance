package org.cdac.freelance.services.contract;

import org.cdac.freelance.dto.contract.ContractResponseDTO;
import org.cdac.freelance.dto.contract.CreateContractRequestDTO;

import java.util.List;

public interface ContractService {

    boolean createContract(CreateContractRequestDTO createContractRequestDTO);

    List<ContractResponseDTO> getAllContractByProviderId(int providerId);

    List<ContractResponseDTO> getAllContractByClientId(int clientId);
}
