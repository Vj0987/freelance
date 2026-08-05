package org.cdac.freelance.services.contract;

import org.cdac.freelance.dto.contract.ContractResponseDTO;
import org.cdac.freelance.dto.contract.CreateContractRequestDTO;
import org.cdac.freelance.entity.Contract;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.enums.ContractStatus;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.ContractRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean createContract(CreateContractRequestDTO createContractRequestDTO) {

        Users provider = usersRepository.findById(createContractRequestDTO.getProviderId())
                .orElseThrow(() ->
                        new UserNotFoundException("Provider not found"));
        Users client = usersRepository.findById(createContractRequestDTO.getClientId())
                .orElseThrow(() ->
                        new UserNotFoundException("Client not found"));

        if(contractRepository.existsByProviderIdAndClientIdAndEscrowId(
                createContractRequestDTO.getProviderId(),
                createContractRequestDTO.getClientId(),
                createContractRequestDTO.getEscrowId()))
            throw new RuntimeException("Contract Already Exist");

        Contract contract = new Contract();

        contract.setProviderId(createContractRequestDTO.getProviderId());
        contract.setClientId(createContractRequestDTO.getClientId());
        contract.setEscrowId(createContractRequestDTO.getEscrowId());
        contract.setAmount(createContractRequestDTO.getAmount());
        contract.setStatus(ContractStatus.STARTED);
        contract.setCompletionDate(createContractRequestDTO.getCompletionDate());

        contractRepository.save(contract);

        return true;
    }

    @Override
    public List<ContractResponseDTO> getAllContractByProviderId(int providerId) {
        Users provider = usersRepository.findById(providerId).orElseThrow(()->new UserNotFoundException("Provider Not Found"));
        return contractRepository.findByProviderId(providerId).stream().map(contract -> {

            Users client = usersRepository.findById(contract.getClientId())
                    .orElseThrow(()->new UserNotFoundException("Client not found"));

            ContractResponseDTO responseDTO = new ContractResponseDTO();
            responseDTO.setContractId(contract.getContractId());
            responseDTO.setProviderId(contract.getProviderId());
            responseDTO.setProviderName(provider.getFullName());
            responseDTO.setClientId(contract.getClientId());
            responseDTO.setClientName(client.getFullName());
            responseDTO.setEscrowId(contract.getEscrowId());
            responseDTO.setAmount(contract.getAmount());
            responseDTO.setStatus(contract.getStatus());
            responseDTO.setCompletionDate(contract.getCompletionDate());

            return responseDTO;
        }).toList();
    }

    @Override
    public List<ContractResponseDTO> getAllContractByClientId(int clientId) {
        Users client = usersRepository.findById(clientId).orElseThrow(()->new UserNotFoundException("Client Not Found"));
        return contractRepository.findByClientId(clientId).stream().map(contract -> {

            Users provider = usersRepository.findById(contract.getProviderId())
                    .orElseThrow(()->new UserNotFoundException("Provider not found"));

            ContractResponseDTO responseDTO = new ContractResponseDTO();
            responseDTO.setContractId(contract.getContractId());
            responseDTO.setProviderId(contract.getProviderId());
            responseDTO.setProviderName(provider.getFullName());
            responseDTO.setClientId(contract.getClientId());
            responseDTO.setClientName(client.getFullName());
            responseDTO.setEscrowId(contract.getEscrowId());
            responseDTO.setAmount(contract.getAmount());
            responseDTO.setCompletionDate(contract.getCompletionDate());

            return responseDTO;
        }).toList();
    }
}
