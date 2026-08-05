package org.cdac.freelance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ServiceProvidedId implements Serializable {
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "provider_id")
    private int providerId;

    public ServiceProvidedId(Integer serviceId, Integer providerId) {
        this.serviceId = serviceId;
        this.providerId = providerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
