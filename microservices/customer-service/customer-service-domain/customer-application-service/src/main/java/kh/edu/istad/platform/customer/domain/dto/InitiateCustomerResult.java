package kh.edu.istad.platform.customer.domain.dto;

import java.util.UUID;


// Response in RestAPI
public record InitiateCustomerResult(
        UUID customerId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber

) {

}
