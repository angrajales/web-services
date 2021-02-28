package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Errors {
    private List<ErrorsElement> errors;
}
