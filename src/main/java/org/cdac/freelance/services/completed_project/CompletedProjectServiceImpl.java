package org.cdac.freelance.services.completed_project;

import feign.FeignException;
import org.cdac.freelance.client.EscrowClient;
import org.cdac.freelance.dto.completed_project.CompletedProjectResponseDTO;
import org.cdac.freelance.dto.completed_project.CreateCompleteProjectDTO;
import org.cdac.freelance.entity.CompletedProjects;
import org.cdac.freelance.entity.Contract;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.enums.ContractStatus;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.CompletedProjectRepository;
import org.cdac.freelance.repository.ContractRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedProjectServiceImpl implements CompletedProjectService{

    @Autowired
    CompletedProjectRepository completedProjectRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    EscrowClient escrowClient;


    @Override
    public boolean createCompletedProject(int contractId) {

        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new RuntimeException("Contract not found"));

        try{
            if(escrowClient.terminateEscrowRecord(contract.getEscrowId())) {

                contract.setStatus(ContractStatus.FINISHED);

                CompletedProjects project = new CompletedProjects();

                project.setClientid(contract.getClientId());
                project.setProviderId(contract.getProviderId());
                project.setAmount(contract.getAmount());

                completedProjectRepository.save(project);
            }
            return true;

        }catch (FeignException.NotFound ex) {

            throw new RuntimeException("Escrow service endpoint not found.");

        } catch (FeignException.ServiceUnavailable ex) {

            throw new RuntimeException("Escrow service is currently unavailable.");

        } catch (FeignException ex) {

            throw new RuntimeException("Error while communicating with Escrow Service: " + ex.getMessage());

        }

    }

    @Override
    public List<CompletedProjectResponseDTO> getByProviderId(int providerId) {

        return completedProjectRepository.findByProviderId(providerId).stream().map(
                completedProjects -> {
                    Users client = usersRepository
                            .findById(completedProjects.getClientid())
                            .orElseThrow(() -> new UserNotFoundException("Client not found"));

                    CompletedProjectResponseDTO responseDTO = new CompletedProjectResponseDTO();
                    responseDTO.setProjectId(completedProjects.getProjectId());
                    responseDTO.setClientId(completedProjects.getClientid());
                    responseDTO.setClientName(client.getFullName());
                    responseDTO.setAmount(completedProjects.getAmount());

                    return responseDTO;
                }
        ).toList();
    }
}
