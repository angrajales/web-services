package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ErrorsElement {
    private Metadata metadata;
    private ErrorModel error;
}
