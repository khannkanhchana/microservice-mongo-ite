package kh.edu.istad.platform.customer.domain.dto;

import kh.edu.istad.platform.customer.persistence.dto.CustomerCountBySegment;
import kh.edu.istad.platform.customer.persistence.dto.CustomerCountByStatus;

import java.util.List;

public record CustomerStatistics(
        long totalCustomers,
        List<CustomerCountByStatus> byStatus,
        List<CustomerCountBySegment> bySegment
) {
}
