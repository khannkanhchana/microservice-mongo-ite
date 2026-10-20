package kh.edu.istad.platform.customer.persistence.mapper;

import kh.edu.istad.platform.customer.domain.entity.CustomerSegment;
import kh.edu.istad.platform.customer.persistence.document.CustomerSegmentDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerSegmentPersistenceMapper {

    public CustomerSegmentDocument toDocument(
            CustomerSegment segment
    ) {
        return CustomerSegmentDocument.builder()
                .id(segment.getId() == null
                        ? null
                        : segment.getId().toString())
                .name(segment.getName())
                .code(segment.getCode())
                .description(segment.getDescription())
                .active(segment.isActive())
                .build();
    }

    public CustomerSegment toDomain(
            CustomerSegmentDocument document
    ) {
        return new CustomerSegment(
                document.getId() == null
                        ? null
                        : UUID.fromString(document.getId()),
                document.getName(),
                document.getCode(),
                document.getDescription(),
                document.isActive()
        );
    }
}
