package kh.edu.istad.platform.customer.persistence.dto;

import java.util.List;

public record CustomerStatisticsData(
        long totalCustomers,
        List<CustomerCountByStatus> byStatus,
        List<CustomerCountBySegment> bySegment
) {
}
