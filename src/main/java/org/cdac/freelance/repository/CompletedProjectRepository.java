package org.cdac.freelance.repository;

import org.cdac.freelance.entity.CompletedProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompletedProjectRepository extends JpaRepository<CompletedProjects, Integer> {
    List<CompletedProjects> findByProviderId(int providerId);
}
