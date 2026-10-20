package kh.edu.istad.platform.customer.domain.usecase;



import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilterCustomersUseCase {

    private final CustomerRepository customerRepository;

    public Flux<Customer> execute(Map<String, String> filters) {
        return customerRepository.filter(filters);
    }
}
