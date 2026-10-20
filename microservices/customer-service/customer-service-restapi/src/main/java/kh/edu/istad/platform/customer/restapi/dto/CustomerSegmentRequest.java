package kh.edu.istad.platform.customer.restapi.dto;

public record CustomerSegmentRequest(
        String name,
        String code,
        String description
) {}
