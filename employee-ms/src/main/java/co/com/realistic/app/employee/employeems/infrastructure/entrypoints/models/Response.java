package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Response<T> {
    private T employee;
}
