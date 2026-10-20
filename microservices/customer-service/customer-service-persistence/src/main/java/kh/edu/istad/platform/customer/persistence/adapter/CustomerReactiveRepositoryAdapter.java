package kh.edu.istad.platform.customer.persistence.adapter;

import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.port.out.CustomerRepository;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import kh.edu.istad.platform.customer.persistence.dto.CustomerCountBySegment;
import kh.edu.istad.platform.customer.persistence.dto.CustomerCountByStatus;
import kh.edu.istad.platform.customer.persistence.dto.CustomerStatisticsData;
import kh.edu.istad.platform.customer.persistence.mapper.CustomerPersistenceMapper;
import kh.edu.istad.platform.customer.persistence.query.DynamicMongoQueryBuilder;
import kh.edu.istad.platform.customer.persistence.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@Repository
public class CustomerReactiveRepositoryAdapter implements CustomerRepository {

    private final CustomerMongoRepository customerMongoRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;


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

    @Override
    public Mono<Customer> findById(CustomerId id) {
        return customerMongoRepository.findById(id.value().toString()).map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(CustomerId id) {
        return customerMongoRepository.deleteById(id.value().toString());
    }

    @Override
    public Mono<Customer> findByUsername(String username) {
        return customerMongoRepository.findByUsername(username).map(customerPersistenceMapper::toDomain);
    }
//
//    @Override
//    public Flux<Customer> query() {
//        Query query = Query.query(
//                Criteria.where("familyName").is("kanhchana").andOperator()
//        );
//        return reactiveMongoTemplate.find(query, CustomerDocument.class).map(
//                customerPersistenceMapper::toDomain
//        );
//
//    }
    @Override
    public Flux<Customer> query() {
        Query query = Query.query(
            Criteria.where("familyName").is("kanhchana")
        );

        return reactiveMongoTemplate
            .find(query, CustomerDocument.class)
            .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Flux<Customer> filter(Map<String, String> filters) {

        Set<String> allowedFields = Set.of(
                "username",
                "familyName",
                "givenName",
                "email",
                "phoneNumber",
                "status"
        );

        Set<String> containsFields = Set.of(
                "username",
                "familyName",
                "givenName",
                "email"
        );

        Query query = DynamicMongoQueryBuilder.build(
                filters,
                allowedFields,
                containsFields
        );

        return reactiveMongoTemplate
                .find(query, CustomerDocument.class)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public Mono<CustomerStatisticsData> getStatistics() {

        // 1. Count customers by status
        Aggregation statusAggregation = Aggregation.newAggregation(
                Aggregation.group("status")
                        .count().as("count"),
                Aggregation.project("count")
                        .and("_id").as("status")
        );

        // 2. Count customers by segment
        Aggregation segmentAggregation = Aggregation.newAggregation(
                Aggregation.lookup(
                        "customer_segments",
                        "segmentId",
                        "_id",
                        "segmentDetails"
                ),
                Aggregation.unwind("segmentDetails"),
                Aggregation.group("segmentDetails.code")
                        .count().as("count"),
                Aggregation.project("count")
                        .and("_id").as("segment")
        );

        // 3. Count all customers
        Mono<Long> totalCustomersMono =
                reactiveMongoTemplate.count(
                        new Query(),
                        CustomerDocument.class
                );

        // 4. Execute status aggregation
        Mono<List<CustomerCountByStatus>> statusListMono =
                reactiveMongoTemplate.aggregate(
                        statusAggregation,
                        CustomerDocument.class,
                        CustomerCountByStatus.class
                ).collectList();

        // 5. Execute segment aggregation
        Mono<List<CustomerCountBySegment>> segmentListMono =
                reactiveMongoTemplate.aggregate(
                        segmentAggregation,
                        CustomerDocument.class,
                        CustomerCountBySegment.class
                ).collectList();

        // 6. Combine all results
        return Mono.zip(
                totalCustomersMono,
                statusListMono,
                segmentListMono
        ).map(result -> new CustomerStatisticsData(
                result.getT1(),
                result.getT2(),
                result.getT3()
        ));
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
