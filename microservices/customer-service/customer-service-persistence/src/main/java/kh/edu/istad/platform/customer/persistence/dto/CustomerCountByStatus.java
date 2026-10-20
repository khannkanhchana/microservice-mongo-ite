package kh.edu.istad.platform.customer.persistence.dto;

public record CustomerCountByStatus(
        String status,
        long count
) {
}
