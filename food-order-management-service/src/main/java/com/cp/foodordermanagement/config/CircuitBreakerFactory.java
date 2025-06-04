package com.cp.foodordermanagement.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class CircuitBreakerFactory {

	@Bean
	CircuitBreakerRegistry circuitBreakerRegistry() {

		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
				.slidingWindowSize(10).waitDurationInOpenState(Duration.ofMillis(10000))
				.permittedNumberOfCallsInHalfOpenState(3).build();

		return CircuitBreakerRegistry.of(circuitBreakerConfig);
	}

	@Bean
	CircuitBreaker myCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("myCircuitBreaker");
	}

}
