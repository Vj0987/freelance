package org.cdac.freelance.services.service_provided;

import org.cdac.freelance.dto.service_provided.CreateRequestDTO;
import org.cdac.freelance.dto.service_provided.ServiceProvidedResponseDTO;
import org.cdac.freelance.entity.ServiceProvided;
import org.cdac.freelance.entity.ServiceProvidedId;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.ServiceProvidedRepository;
import org.cdac.freelance.repository.ServiceRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProvideServiceImpl implements ServiceProvideService{

    @Autowired
    ServiceProvidedRepository serviceProvidedRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UsersRepository usersRepository;



    @Override
    public boolean createServiceProvided(CreateRequestDTO requestDTO) {

        ServiceProvidedId id = new ServiceProvidedId(
                requestDTO.getServiceId(),
                requestDTO.getProviderId());

        if(serviceProvidedRepository.existsById(id))
            throw new RuntimeException("This provider has already added this service.");

        ServiceProvided serviceProvided = new ServiceProvided();
        serviceProvided.setId(id);
        serviceProvided.setEstimatedCost(requestDTO.getEstimatedCost());

        serviceProvidedRepository.save(serviceProvided);
        return true;
    }

    @Override
    public List<ServiceProvidedResponseDTO> getAllByServiceId(int serviceId) {
        return serviceProvidedRepository
                .findByIdServiceId(serviceId)
                .stream()
                .map(
                    serviceProvided -> {

                        org.cdac.freelance.entity.Service service = serviceRepository
                                .findById(serviceProvided.getId().getServiceId())
                                .orElseThrow(() ->new RuntimeException("Service Not Found"));

                        Users user = usersRepository
                                .findById(serviceProvided.getId().getProviderId())
                                .orElseThrow(()-> new UserNotFoundException("Provider Not found"));


                        ServiceProvidedResponseDTO responseDTO = new ServiceProvidedResponseDTO();
                        responseDTO.setProviderId(serviceProvided.getId().getProviderId());
                        responseDTO.setServiceId(serviceProvided.getId().getServiceId());
                        responseDTO.setServiceName(service.getTitle());
                        responseDTO.setProviderName(user.getFullName());
                        responseDTO.setEstimatedCost(serviceProvided.getEstimatedCost());

                        return responseDTO;
                    }
        ).toList();
    }

    @Override
    public List<ServiceProvidedResponseDTO> getAllByProviderId(int providerId) {
        return serviceProvidedRepository
                .findByIdProviderId(providerId)
                .stream()
                .map(
                        serviceProvided -> {
                            org.cdac.freelance.entity.Service service = serviceRepository
                                    .findById(serviceProvided.getId().getServiceId())
                                    .orElseThrow(() ->new RuntimeException("Service Not Found"));

                            Users user = usersRepository
                                    .findById(serviceProvided.getId().getProviderId())
                                    .orElseThrow(()-> new UserNotFoundException("Provider Not found"));

                            ServiceProvidedResponseDTO responseDTO = new ServiceProvidedResponseDTO();
                            responseDTO.setProviderId(serviceProvided.getId().getProviderId());
                            responseDTO.setServiceId(serviceProvided.getId().getServiceId());
                            responseDTO.setServiceName(service.getTitle());
                            responseDTO.setProviderName(user.getFullName());
                            responseDTO.setEstimatedCost(serviceProvided.getEstimatedCost());

                            return responseDTO;
                        }
                ).toList();
    }
}
