package kh.edu.istad.platform.customer.restapi.controller;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.domain.usecase.CreateCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.domain.usecase.DeleteCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.domain.usecase.GetCustomerSegmentsUseCase;
import kh.edu.istad.platform.customer.domain.usecase.UpdateCustomerSegmentUseCase;
import kh.edu.istad.platform.customer.restapi.dto.CustomerSegmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/customer-segments")
@RequiredArgsConstructor
public class CustomerSegmentController {

    private final CreateCustomerSegmentUseCase createUseCase;
    private final GetCustomerSegmentsUseCase getUseCase;
    private final UpdateCustomerSegmentUseCase updateUseCase;
    private final DeleteCustomerSegmentUseCase deleteUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerSegment> create(
            @RequestBody CustomerSegmentRequest request
    ) {
        return createUseCase.execute(
                new CustomerSegment(
                        null,
                        request.name(),
                        request.code(),
                        request.description(),
                        true
                )
        );
    }

    @GetMapping
    public Flux<CustomerSegment> findAll() {
        return getUseCase.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CustomerSegment> findById(@PathVariable UUID id) {
        return getUseCase.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer Segment not found"
                        )
                ));
    }

    @PutMapping("/{id}")
    public Mono<CustomerSegment> update(
            @PathVariable UUID id,
            @RequestBody CustomerSegmentRequest request
    ) {
        return updateUseCase.execute(
                id,
                request.name(),
                request.code(),
                request.description()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return deleteUseCase.execute(id);
    }
}