package org.cdac.freelance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_provided")
public class ServiceProvided {

    @EmbeddedId
    private ServiceProvidedId id;

    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    public ServiceProvidedId getId() {
        return id;
    }

    public void setId(ServiceProvidedId id) {
        this.id = id;
    }

    public BigDecimal getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(BigDecimal estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
