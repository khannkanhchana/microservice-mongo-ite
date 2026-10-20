package kh.edu.istad.platform.customer.persistence.adapter;


import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.port.out.CustomerSegmentRepository;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerSegmentPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerSegmentMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerSegmentRepositoryAdapter
        implements CustomerSegmentRepository {

    private final CustomerSegmentMongoRepository mongoRepository;
    private final CustomerSegmentPersistenceMapper mapper;

    @Override
    public Mono<CustomerSegment> save(CustomerSegment segment) {
        return mongoRepository.save(mapper.toDocument(segment))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<CustomerSegment> findById(UUID id) {
        return mongoRepository.findById(id.toString())
                .map(mapper::toDomain);
    }

    @Override
    public Flux<CustomerSegment> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return mongoRepository.deleteById(id.toString());
    }

    @Override
    public Mono<Boolean> existsByCode(String code) {
        return mongoRepository.existsByCode(code);
    }

    @Override
    public Mono<Boolean> existsByCodeAndIdNot(
            String code,
            UUID id
    ) {
        return mongoRepository.existsByCodeAndIdNot(
                code,
                id.toString()
        );
    }
}