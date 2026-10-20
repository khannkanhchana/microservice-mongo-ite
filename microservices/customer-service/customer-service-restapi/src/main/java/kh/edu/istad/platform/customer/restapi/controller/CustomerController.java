package kh.edu.istad.platform.customer.restapi.controller;


import jakarta.validation.Valid;
import kh.edu.istad.platform.customer.domain.dto.UpdateCustomerCommand;
import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.event.CustomerDeactivatedEvent;
import kh.edu.istad.platform.customer.domain.usecase.*;
import kh.edu.istad.platform.customer.restapi.dto.*;
import kh.edu.istad.platform.customer.restapi.mapper.CustomerWebMapper;
import kh.edu.istad.platform.customer.domain.dto.InitiateCustomerResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.server.ResponseStatusException;
import org.springdoc.core.annotations.ParameterObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final InitiateCustomerUseCase initiateCustomerUseCase;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CustomerWebMapper customerWebMapper;
//    private final UpdateCustomerUseCase updateCustomerUseCase;
//    private final DeactivateCustomerUseCase deactivateCustomerUseCase;
//    private final GetAllCustomersUseCase getAllCustomersUseCase;

    private final QueryCustomerUseCase queryCustomerUseCase;

//    @GetMapping
//    public PagedModel<CustomerResponse> getAllCustomers(
//            @ParameterObject
//            @PageableDefault(page = 0, size = 15, sort = "customerId")
//            Pageable pageable
//    ){
//        var customers = getAllCustomersUseCase.execute(pageable);
//        var responses = customers.map(customerWebMapper::toResponse);
//
//        return new PagedModel<>(responses);
//    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public CustomerInitiateResponse initiateResponse(@Valid @RequestBody CustomerInitiateRequest customerInitiateRequest){
//        InitiateCustomerResult result = initiateCustomerUseCase.execute(customerWebMapper.toCommand(customerInitiateRequest));
//
//        return customerWebMapper.toResponse(result);
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerResponse> create(
        @RequestBody CreateCustomerRequest request
    ) {
        Customer customer = customerWebMapper.toDomain(request);

        return createCustomerUseCase.create(customer)
            .map(customerWebMapper::toCustomerResponse);
    }

    @GetMapping
    public Flux<Customer> findAll() {
        return queryCustomerUseCase.findAll();
    }







//    @PatchMapping("/{customerId}")
//    public CustomerUpdateResponse updateCustomer(
//            @PathVariable ("customerId") UUID customerId,
//            @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest
//    ) {
//        var command = customerWebMapper.toUpdateCommand(
//                customerUpdateRequest,
//                customerId
//        );
//
//        var event = updateCustomerUseCase.execute(command);
//
//        return customerWebMapper.toUpdateResponse(event);
//    }
//
//    @PatchMapping("/{customerId}/deactivate")
//    public CustomerDeactivateResponse deactivateResponse(
//            @PathVariable("customerId") UUID customerId
//    ){
//        var event = deactivateCustomerUseCase.execute(customerId);
//
//        return customerWebMapper.toDeactivateResponse(event);
//    }

}
