package kh.edu.istad.platform.customer.domain.usecase;


import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCustomerSegmentUseCase {

    private final CustomerSegmentRepository repository;

    public Mono<Void> execute(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                "Customer Segment not found: " + id
                        )
                ))
                .flatMap(segment -> repository.deleteById(id));
    }
}
