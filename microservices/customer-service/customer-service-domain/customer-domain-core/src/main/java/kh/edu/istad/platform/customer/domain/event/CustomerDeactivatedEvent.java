package kh.edu.istad.platform.customer.domain.event;

import kh.edu.istad.common.domain.event.DomainEvent;
import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;

//import java.time.ZonedDateTime;
//
//public class CustomerDeactivatedEvent implements DomainEvent<Customer> {
//    private final Customer customer;
//    private final ZonedDateTime initiatedAt;
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public ZonedDateTime getInitiatedAt() {
//        return initiatedAt;
//    }
//
//    public CustomerDeactivatedEvent(Customer customer, ZonedDateTime initiatedAt) {
//        this.customer = customer;
//        this.initiatedAt = initiatedAt;
//    }
//
//
//}

import java.time.ZonedDateTime;

public class CustomerDeactivatedEvent implements DomainEvent<Customer> {

    private final CustomerId customerId;
    private final ZonedDateTime deactivatedAt;


    public CustomerId getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getDeactivatedAt() {
        return deactivatedAt;
    }

    public CustomerDeactivatedEvent( CustomerId customerId, ZonedDateTime deactivatedAt) {
        this.customerId = customerId;
        this.deactivatedAt = deactivatedAt;
    }
}
