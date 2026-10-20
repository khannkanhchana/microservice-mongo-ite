package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    public Mono<Void> execute(UUID customerId) {
        CustomerId id = new CustomerId(customerId);

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                "Customer not found: " + customerId
                        )
                ))
                .flatMap(customer ->
                        customerRepository.deleteById(id)
                );
    }
}