package kh.edu.istad.platform.customer.domain.usecase;




import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.dto.CustomerStatisticsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetCustomerStatisticsUseCase {

    private final CustomerRepository customerRepository;

    public Mono<CustomerStatisticsData> execute() {
        return customerRepository.getStatistics();
    }
}
