package co.com.realistic.app.employee.employeems.infrastructure.entrypoints;

import co.com.realistic.app.employee.employeems.infrastructure.entrypoints.handlers.EmployeeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class EmployeeEntryPoint implements WebFluxConfigurer {
    private final EmployeeHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routeEmployee() {
        return route()
                .GET("/employees/create",
                        handler::addEmployee)
                .GET("/employees/get", handler::getEmployee)
                .build();
    }
}
