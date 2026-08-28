package com.example.persona.infrastructure.config;

import com.example.persona.domain.api.IPersonaServicePort;
import com.example.persona.domain.spi.PersonaRepositoryPort;
import com.example.persona.domain.usecase.PersonaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    IPersonaServicePort personaUseCase(PersonaRepositoryPort repository) {
        return new PersonaUseCase(repository);
    }
}
