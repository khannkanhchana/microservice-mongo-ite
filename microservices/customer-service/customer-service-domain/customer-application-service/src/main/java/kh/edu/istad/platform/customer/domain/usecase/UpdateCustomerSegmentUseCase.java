package kh.edu.istad.platform.customer.domain.usecase;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCustomerSegmentUseCase {

    private final CustomerSegmentRepository repository;

    public Mono<CustomerSegment> execute(
            UUID id,
            String name,
            String code,
            String description
    ) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                "Customer Segment not found: " + id
                        )
                ))
                .flatMap(segment ->
                        repository.existsByCodeAndIdNot(code, id)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return Mono.error(
                                                new IllegalArgumentException(
                                                        "Segment code already exists"
                                                )
                                        );
                                    }

                                    segment.update(name, code, description);
                                    return repository.save(segment);
                                })
                );
    }
}