package co.com.realistic.app.employee.employeems.infrastructure.entrypoints.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Data {
    private List<DataElement> data;
}
