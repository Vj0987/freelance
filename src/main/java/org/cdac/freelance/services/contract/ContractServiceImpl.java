package org.cdac.freelance.services.contract;

import feign.FeignException;
import org.cdac.freelance.client.EscrowClient;
import org.cdac.freelance.dto.EscrowClient.CreateEscrowRecordDTO;
import org.cdac.freelance.dto.EscrowClient.EscrowRecordResponseDTO;
import org.cdac.freelance.dto.contract.ContractResponseDTO;
import org.cdac.freelance.entity.Contract;
import org.cdac.freelance.entity.Offers;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.enums.ContractStatus;
import org.cdac.freelance.enums.OfferStatus;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.ContractRepository;
import org.cdac.freelance.repository.OffersRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    OffersRepository offersRepository;

    @Autowired
    EscrowClient escrowClient;

    @Override
    public boolean createContract(int offerId) {
        Offers offers = offersRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer Not found"));

        CreateEscrowRecordDTO escrowRecordDTO = new CreateEscrowRecordDTO();
        escrowRecordDTO.setClientId(offers.getClientId());
        escrowRecordDTO.setProviderId(offers.getProviderId());
        escrowRecordDTO.setAmount(offers.getBudget());


        try {

            EscrowRecordResponseDTO escrowrecord = escrowClient.createEscrowRecord(escrowRecordDTO);

            Contract contract = new Contract();
            contract.setEscrowId(escrowrecord.getEscrowRecordId());
            contract.setClientId(escrowrecord.getClientId());
            contract.setProviderId(escrowrecord.getProviderId());
            contract.setAmount(escrowrecord.getAmount());
            contract.setStatus(ContractStatus.STARTED);

            contractRepository.save(contract);

            Offers offer = offersRepository
                    .findById(offerId)
                    .orElseThrow(() -> new RuntimeException("Offer Not Found"));

            if(!offer.getStatus().equals(OfferStatus.ACCEPTED))
                throw new RuntimeException("Cannot update Offer Status");

            offer.setStatus(OfferStatus.CONTRACTED);

            offersRepository.save(offer);

            return true;

        } catch (FeignException.NotFound ex) {

            throw new RuntimeException("Escrow service endpoint not found.");

        } catch (FeignException.ServiceUnavailable ex) {

            throw new RuntimeException("Escrow service is currently unavailable.");

        } catch (FeignException ex) {

            throw new RuntimeException("Error while communicating with Escrow Service: " + ex.getMessage());

        }

    }

    @Override
    public List<ContractResponseDTO> getAllContractByProviderId(int providerId) {
        Users provider = usersRepository.findById(providerId).orElseThrow(() -> new UserNotFoundException("Provider Not Found"));
        return contractRepository.findByProviderId(providerId).stream().map(contract -> {

            Users client = usersRepository.findById(contract.getClientId())
                    .orElseThrow(() -> new UserNotFoundException("Client not found"));

            ContractResponseDTO responseDTO = new ContractResponseDTO();
            responseDTO.setContractId(contract.getContractId());
            responseDTO.setProviderId(contract.getProviderId());
            responseDTO.setProviderName(provider.getFullName());
            responseDTO.setClientId(contract.getClientId());
            responseDTO.setClientName(client.getFullName());
            responseDTO.setEscrowId(contract.getEscrowId());
            responseDTO.setAmount(contract.getAmount());
            responseDTO.setStatus(contract.getStatus());

            return responseDTO;
        }).toList();
    }

    @Override
    public List<ContractResponseDTO> getAllContractByClientId(int clientId) {
        Users client = usersRepository.findById(clientId).orElseThrow(() -> new UserNotFoundException("Client Not Found"));
        return contractRepository.findByClientId(clientId).stream().map(contract -> {

            Users provider = usersRepository.findById(contract.getProviderId())
                    .orElseThrow(() -> new UserNotFoundException("Provider not found"));

            ContractResponseDTO responseDTO = new ContractResponseDTO();
            responseDTO.setContractId(contract.getContractId());
            responseDTO.setProviderId(contract.getProviderId());
            responseDTO.setProviderName(provider.getFullName());
            responseDTO.setClientId(contract.getClientId());
            responseDTO.setClientName(client.getFullName());
            responseDTO.setEscrowId(contract.getEscrowId());
            responseDTO.setAmount(contract.getAmount());

            return responseDTO;
        }).toList();
    }

    @Override
    public boolean statusCompleted(int contractId) {

        Contract contract = contractRepository
                .findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not Found"));

        contract.setStatus(ContractStatus.COMPLETED);
        contractRepository.save(contract);

        return true;
    }

    @Override
    public boolean statusConfirmed(int contractId) {
        Contract contract = contractRepository
                .findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not Found"));

        contract.setStatus(ContractStatus.CONFIRMED);
        contractRepository.save(contract);

        return true;
    }

    @Override
    public boolean statusCancelled(int contractId) {
        Contract contract = contractRepository
                .findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not Found"));

        contract.setStatus(ContractStatus.CANCELLED);
        contractRepository.save(contract);

        return true;
    }
}
