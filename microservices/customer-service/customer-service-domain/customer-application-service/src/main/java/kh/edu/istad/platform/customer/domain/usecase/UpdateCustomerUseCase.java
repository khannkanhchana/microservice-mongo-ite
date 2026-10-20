package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.exception.CustomerNotFoundException;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public Mono<Customer> execute(UpdateCustomerCommand command) {
        CustomerId id = new CustomerId(command.customerId());

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new CustomerNotFoundException(command.customerId())
                ))
                .flatMap(customer -> {
                    customer.updateCustomer(
                            command.familyName(),
                            command.givenName()
                    );

                    return customerRepository.save(customer);
                });
    }
}