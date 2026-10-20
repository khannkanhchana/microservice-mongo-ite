//package kh.edu.istad.platform.customer.restapi.exception;
//
//import kh.edu.istad.common.domain.exception.CustomerDomainException;
//import kh.edu.istad.platform.customer.domain.exception.CustomerNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.*;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Slf4j
//@RestControllerAdvice
//public class  RestAdvisor extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(CustomerNotFoundException.class)
//    public ProblemDetail handleCustomerNotFound(
//            CustomerNotFoundException e
//    ){
//        return ProblemDetail.forStatusAndDetail(
//                HttpStatus.NOT_FOUND,
//                e.getMessage()
//        );
//    }
//
//    @ExceptionHandler(CustomerDomainException.class)
//    public ProblemDetail handleCustomerDomain(
//            CustomerDomainException exception
//    ) {
//        return ProblemDetail.forStatusAndDetail(
//                HttpStatus.BAD_REQUEST,
//                exception.getMessage()
//        );
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException exception,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request
//    ) {
//        Map<String, String> errors = new LinkedHashMap<>();
//
//        exception.getBindingResult()
//                .getFieldErrors()
//                .forEach(error -> errors.putIfAbsent(
//                        error.getField(),
//                        error.getDefaultMessage() == null
//                                ? "Invalid value"
//                                : error.getDefaultMessage()
//                ));
//
//        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
//                status,
//                "Please correct the invalid request fields."
//        );
//
//        problem.setProperty("errors", errors);
//
//        return handleExceptionInternal(
//                exception,
//                problem,
//                headers,
//                status,
//                request
//        );
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException exception,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request
//    ) {
//        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
//                status,
//                "The request body is missing or contains invalid JSON."
//        );
//
//        return handleExceptionInternal(
//                exception,
//                problem,
//                headers,
//                status,
//                request
//        );
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleUnexpectedException(Exception exception) {
//        log.error("Unexpected error while processing a request", exception);
//
//        return ProblemDetail.forStatusAndDetail(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred. Please try again later."
//        );
//    }
//}
//

package kh.edu.istad.platform.customer.restapi.exception;

import kh.edu.istad.common.domain.exception.CustomerDomainException;
import kh.edu.istad.platform.customer.domain.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestAdvisor {

    @ExceptionHandler(CustomerNotFoundException.class)
    public Mono<ProblemDetail> handleCustomerNotFound(
            CustomerNotFoundException exception
    ) {
        return Mono.just(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        ));
    }

    @ExceptionHandler(CustomerDomainException.class)
    public Mono<ProblemDetail> handleCustomerDomain(
            CustomerDomainException exception
    ) {
        return Mono.just(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        ));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ProblemDetail> handleValidation(
            WebExchangeBindException exception
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage() == null
                                ? "Invalid value"
                                : error.getDefaultMessage()
                ));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Please correct the invalid request fields."
        );

        problem.setProperty("errors", errors);

        return Mono.just(problem);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ProblemDetail> handleInvalidRequest(
            ServerWebInputException exception
    ) {
        return Mono.just(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "The request body is missing or contains invalid JSON."
        ));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ProblemDetail> handleUnexpectedException(
            Exception exception
    ) {
        log.error("Unexpected error while processing a request", exception);

        return Mono.just(ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later."
        ));
    }
}
//
