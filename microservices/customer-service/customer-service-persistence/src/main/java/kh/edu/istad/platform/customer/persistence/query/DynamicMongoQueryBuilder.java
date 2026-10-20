package kh.edu.istad.platform.customer.persistence.query;



import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DynamicMongoQueryBuilder {

    private DynamicMongoQueryBuilder() {
    }

    public static Query build(
            Map<String, String> filters,
            Set<String> allowedFields,
            Set<String> containsFields
    ) {
        List<Criteria> criteriaList = new ArrayList<>();

        filters.forEach((field, value) -> {
            if (value == null || value.isBlank()) {
                return;
            }

            // Only allow explicitly approved fields.
            if (!allowedFields.contains(field)) {
                throw new IllegalArgumentException(
                        "Unsupported filter field: " + field
                );
            }

            String trimmedValue = value.trim();

            if (containsFields.contains(field)) {
                criteriaList.add(
                        Criteria.where(field)
                                .regex(
                                        java.util.regex.Pattern.quote(trimmedValue),
                                        "i"
                                )
                );
            } else {
                criteriaList.add(
                        Criteria.where(field).is(trimmedValue)
                );
            }
        });

        Query query = new Query();

        if (!criteriaList.isEmpty()) {
            query.addCriteria(
                    new Criteria().andOperator(
                            criteriaList.toArray(new Criteria[0])
                    )
            );
        }

        return query;
    }
}