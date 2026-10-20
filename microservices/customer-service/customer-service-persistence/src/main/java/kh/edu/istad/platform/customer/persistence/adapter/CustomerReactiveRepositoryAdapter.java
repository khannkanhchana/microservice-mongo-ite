package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Repository
public class CustomerReactiveRepositoryAdapter implements CustomerRepository {

    private final CustomerMongoRepository customerMongoRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;


    @Override
    public Mono<Customer> save(Customer customer) {

      CustomerDocument customerDocument = customerPersistenceMapper.toDocument(customer);
      Mono<CustomerDocument> result = customerMongoRepository.save(customerDocument);
      return result.map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Customer> findAll() {
        return customerMongoRepository.findAll().map(customerPersistenceMapper::toDomain);
    }

//    @Override
//    public java.util.Optional<Customer> findById(CustomerId customerId) {
//        // TODO: Implement according to the CustomerRepository contract.
//        return java.util.Optional.empty();
//    }
//
//    @Override
//    public org.springframework.data.domain.Page<Customer> findAll(
//            org.springframework.data.domain.Pageable pageable) {
//        // TODO: Reactive MongoDB does not return Page<Customer> directly.
//        throw new UnsupportedOperationException(
//                "findAll requires a repository contract redesign for reactive MongoDB");
//    }
}
