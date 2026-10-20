package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerPersistenceMapper {

    // Convert Customer Domain Entity to MongoDB Document
    default CustomerDocument toDocument(Customer customer) {
        if (customer == null) {
            return null;
        }

        return CustomerDocument.builder()
                .id(customer.getId() == null
                        ? null
                        : customer.getId().value().toString())
                .username(customer.getUsername())
                .familyName(customer.getFamilyName())
                .givenName(customer.getGivenName())
                .email(customer.getEmail() == null
                        ? null
                        : customer.getEmail().value())
                .phoneNumber(customer.getPhoneNumber() == null
                        ? null
                        : customer.getPhoneNumber().value())
                .segmentId(customer.getSegmentId())
                .status(customer.getStatus())
                .build();
    }

    // Convert MongoDB Document to Customer Domain Entity
    default Customer toDomain(CustomerDocument document) {
        if (document == null) {
            return null;
        }

        CustomerId customerId = document.getId() == null
                ? null
                : new CustomerId(UUID.fromString(document.getId()));

        Email email = document.getEmail() == null
                ? null
                : new Email(document.getEmail());

        PhoneNumber phoneNumber = document.getPhoneNumber() == null
                ? null
                : new PhoneNumber(document.getPhoneNumber());

        CustomerStatus status = document.getStatus();

        return Customer.Builder.builder()
                .id(customerId)
                .username(document.getUsername())
                .familyName(document.getFamilyName())
                .givenName(document.getGivenName())
                .email(email)
                .phoneNumber(phoneNumber)
                .segmentId(document.getSegmentId())
                .status(status)
                .build();
    }
}



