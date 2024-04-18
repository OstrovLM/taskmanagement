package lev.ostrov.taskmanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
