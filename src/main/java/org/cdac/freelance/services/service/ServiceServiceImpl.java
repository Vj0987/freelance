package org.cdac.freelance.services.service;

import org.cdac.freelance.dto.service.ServiceResponseDTO;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.ServiceRepository;
import org.cdac.freelance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService{
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<ServiceResponseDTO> getAllServices() {

        return serviceRepository.findAll()
                .stream()
                .map(service -> {
                    ServiceResponseDTO response = new ServiceResponseDTO();
                    Users user = usersRepository.findById(service.getProviderId()).orElseThrow(()->new UserNotFoundException("Provider Not Found"));
                    response.setServiceId(service.getServiceId());
                    response.setProviderName(user.getFullName());
                    response.setTitle(service.getTitle());
                    response.setDescription(service.getDescription());

                    return response;
                })
                .toList();
    }
}
