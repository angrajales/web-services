package co.com.realistic.app.employee.employeems.application.config;

import co.com.realistic.app.employee.employeems.core.domain.employee.repository.EmployeeRepository;
import co.com.realistic.app.employee.employeems.infrastructure.entrypoints.handlers.EmployeeHandler;
import co.com.realistic.app.employee.employeems.infrastructure.soap.encoding.SoapDecoder;
import co.com.realistic.app.employee.employeems.infrastructure.soap.encoding.SoapEncoder;
import co.com.realistic.app.employee.employeems.infrastructure.soap.integration.SoapClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    private String SOAP_URL = "http://localhost:8080/ws";

    private WebClient webClient() {
        TcpClient tcpClient = TcpClient.create();
        tcpClient
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });
        ExchangeStrategies exchangeStrategies = ExchangeStrategies
                .builder()
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.customCodecs().register(new SoapDecoder());
                    clientCodecConfigurer.customCodecs().register(new SoapEncoder());
                }).build();
        return WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient).wiretap(true)))
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    @Bean
    public EmployeeHandler employeeHandler() {
        return new EmployeeHandler(employeeRepository());
    }

    @Bean
    public EmployeeRepository employeeRepository() {
        return new SoapClient(webClient(), SOAP_URL);
    }
}
