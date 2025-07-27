//package com.huysor.saas.api.product.config;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MeterRegistryConfig {
//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
//        return registry -> registry.config().commonTags("application", "api-product");
//    }
//}
