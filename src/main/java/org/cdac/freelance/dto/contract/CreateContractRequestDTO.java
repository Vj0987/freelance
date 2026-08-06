package org.cdac.freelance.dto.contract;



import jakarta.validation.constraints.Min;

public class CreateContractRequestDTO {

    @Min(value = 1, message = "Offer ID must be greater than 0")
    private int offerId;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
}