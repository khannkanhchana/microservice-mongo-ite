//
//package kh.edu.istad.platform.customer.persistence.mapper;
//
//import kh.edu.istad.platform.customer.domain.entity.Customer;
//import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
//import org.mapstruct.InheritInverseConfiguration;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingConstants;
//
//
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//public interface CustomerPersistenceMapper {
//
//
//    @Mapping(source = "id.value", target = "id")
//    @Mapping(source = "email.value", target = "email")
//    @Mapping(source = "phoneNumber.value", target = "phoneNumber")
//    CustomerDocument  toDocument(Customer customer);
//
//
////    @Mapping(source = "id", target = "id.value")
////    @Mapping(source = "email", target = "email.value")
////    @Mapping(source = "phoneNumber", target = "phoneNumber.value")
//
//    @InheritInverseConfiguration
//    Customer toDomain(CustomerDocument customerDocument);
//
//
//
////    @Mapping(target = "customerId", source = "id.id")
////    @Mapping(target = "email", source = "email.email")
////    @Mapping(target = "phoneNumber", source = "phoneNumber.phoneNumber")
////    @Mapping(target = "createdBy", ignore = true)
////    @Mapping(target = "createdAt", ignore = true)
////    @Mapping(target = "modifiedBy", ignore = true)
////    @Mapping(target = "modifiedAt", ignore = true)
//////    CustomerEntity toEntity(Customer customer);
////
////    @Mapping(target = "id", source = "customerId")
////    void mapToBuilder(
////            CustomerEntity entity,
////            @MappingTarget Customer.Builder builder
////    );
////
////    default Customer toDomain(CustomerEntity entity) {
////        Customer.Builder builder = Customer.Builder.builder();
////        mapToBuilder(entity, builder);
////        return builder.build();
////    }
////
////    default CustomerId toCustomerId(UUID id) {
////        return id == null ? null : new CustomerId(id);
////    }
////
////    default Email toEmail(String value) {
////        return value == null ? null : new Email(value);
////    }
////
////    default PhoneNumber toPhoneNumber(String value) {
////        return value == null ? null : new PhoneNumber(value);
////    }
//}
//
////@Mapper(componentModel = "spring")
////public interface CustomerPersistenceMapper {
////        CustomerDocument toDocument(Customer customer);
////
////        Customer toDomain(CustomerDocument customerDocument);
////
////}
//



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
                .status(customer.getStatus())
                .build();
    }

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
                .status(status)
                .build();
    }
}
