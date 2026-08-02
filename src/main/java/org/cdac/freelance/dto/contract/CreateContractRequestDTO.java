package org.cdac.freelance.dto.contract;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateContractRequestDTO {

    @Min(value = 1, message = "Provider ID must be greater than 0")
    private int providerId;

    @Min(value = 1, message = "Client ID must be greater than 0")
    private int clientId;

    @Min(value = 1, message = "Escrow ID must be greater than 0")
    private int escrowId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must have at most 10 digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Completion date is required")
    @Future(message = "Completion date must be a future date")
    private LocalDate completionDate;

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getEscrowId() {
        return escrowId;
    }

    public void setEscrowId(int escrowId) {
        this.escrowId = escrowId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}