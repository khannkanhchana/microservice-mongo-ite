//package kh.edu.istad.platform.customer.restapi.mapper;
//
//import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
//import kh.edu.istad.platform.customer.domain.entity.Customer;
//import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
//import kh.edu.istad.platform.customer.domain.event.CustomerUpdatedEvent;
//import kh.edu.istad.platform.customer.domain.valueobject.Email;
//import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
//import kh.edu.istad.platform.customer.restapi.dto.*;
//import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
//import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.UUID;
//
//@Mapper(componentModel = "spring")
//public interface CustomerWebMapper {
//    InitiateCustomerCommand toCommand(CustomerInitiateRequest request);
//
//    CustomerInitiateResponse toResponse(InitiateCustomerResult result);
//
//    Customer toDomain(CreateCustomerRequest request);
//
//
//    default Email toEmail(String email) {
//        return new Email(email);
//    }
//
//    default  PhoneNumber toPhoneNumber(String phoneNumber) {
//        return new PhoneNumber(phoneNumber);
//    }
//
////    @Mapping(target = "customerId", source = "id.value")
////    @Mapping(target = "email", source = "email.email")
////    @Mapping(target = "phoneNumber", source = "phoneNumber.phoneNumber")
////    CustomerResponse toResponse(Customer customer);
////
////    @Mapping(target = "customerId", source = "customer.id.value")
////    CustomerUpdateResponse toUpdateResponse(CustomerUpdatedEvent customerUpdatedEvent);
////
////    @Mapping(target = "customerId", source = "customerId.value")
////    CustomerDeactivateResponse toDeactivateResponse(CustomerDeactivatedEvent customerDeactivatedEvent);
////
////    @Mapping(target = "customerId", source = "customerId")
////    UpdateCustomerCommand toUpdateCommand(CustomerUpdateRequest request, UUID customerId);
//
//
//
//
//
//}




package kh.edu.istad.platform.customer.restapi.mapper;

import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerCommand;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.restapi.dto.CreateCustomerRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateRequest;
import kh.edu.istad.platform.customer.restapi.dto.CustomerInitiateResponse;
import kh.edu.istad.platform.customer.restapi.dto.CustomerResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {

    InitiateCustomerCommand toCommand(CustomerInitiateRequest request);

    CustomerInitiateResponse toResponse(InitiateCustomerResult result);

    default CustomerResponse toResponse(Customer customer) {
        return toCustomerResponse(customer);
    }

    default Customer toDomain(CreateCustomerRequest request) {
        if (request == null) {
            return null;
        }

        return Customer.Builder.builder()
                .username(request.username())
                .familyName(request.familyName())
                .givenName(request.givenName())
                .email(toEmail(request.email()))
                .phoneNumber(toPhoneNumber(request.phoneNumber()))
                .build();
    }

    default Email toEmail(String email) {
        return email == null ? null : new Email(email);
    }

    default PhoneNumber toPhoneNumber(String phoneNumber) {
        return phoneNumber == null ? null : new PhoneNumber(phoneNumber);
    }

    default CustomerResponse toCustomerResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        UUID customerId = customer.getId() == null
                ? null
                : customer.getId().value();

        return new CustomerResponse(
                customerId,
                customer.getUsername(),
                customer.getFamilyName(),
                customer.getGivenName(),
                customer.getEmail() == null ? null : customer.getEmail().value(),
                customer.getPhoneNumber() == null ? null : customer.getPhoneNumber().value(),
                customer.getStatus() == null ? null : customer.getStatus().name()
        );
    }
}
