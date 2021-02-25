package co.com.realistic.app.employee.employeems.core.domain.employee;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EmployeeInterestingAttributes {
    private int birthDateDay;
    private int birthDateMonth;
    private int birthDateYear;
    private int entailmentDateDay;
    private int entailmentDateMonth;
    private int entailmentDateYear;
}
