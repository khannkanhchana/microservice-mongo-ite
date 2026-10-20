package kh.edu.istad.platform.customer.persistence.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer_segments")
public class CustomerSegmentDocument {

    @Id
    private String id;

    private String name;
    private String code;
    private String description;
    private boolean active;
}
