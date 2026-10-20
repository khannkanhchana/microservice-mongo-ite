package kh.edu.istad.platform.customer.domain.usecase;


import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateCustomerSegmentUseCase {

    private final CustomerSegmentRepository repository;

    public Mono<CustomerSegment> execute(CustomerSegment segment) {
        segment.create();

        return repository.existsByCode(segment.getCode())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new IllegalArgumentException(
                                        "Segment code already exists"
                                )
                        );
                    }

                    return repository.save(segment);
                });
    }
}
