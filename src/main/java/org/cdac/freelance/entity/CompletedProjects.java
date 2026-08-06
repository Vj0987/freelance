package org.cdac.freelance.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "completed_projects")
public class CompletedProjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int projectId;

    @Column(name = "clientId")
    private int clientid;

    @Column(name = "provider_id")
    private int providerId;

    @Column(name = "amount")
    private BigDecimal amount;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }


    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
