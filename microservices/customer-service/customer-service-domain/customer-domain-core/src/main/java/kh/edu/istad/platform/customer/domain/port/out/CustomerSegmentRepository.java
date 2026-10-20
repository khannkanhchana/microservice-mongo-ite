package kh.edu.istad.platform.customer.domain.port.out;


import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerSegmentRepository {

    Mono<CustomerSegment> save(CustomerSegment segment);

    Mono<CustomerSegment> findById(UUID id);

    Flux<CustomerSegment> findAll();

    Mono<Void> deleteById(UUID id);

    Mono<Boolean> existsByCode(String code);

    Mono<Boolean> existsByCodeAndIdNot(
            String code,
            UUID id
    );
}
