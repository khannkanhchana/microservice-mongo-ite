package kh.edu.istad.platform.customer.persistence.dto;

public record CustomerCountBySegment(
        String segment,
        long count
) {
}
