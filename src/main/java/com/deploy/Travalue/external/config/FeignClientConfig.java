package com.deploy.Travalue.external.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.deploy.Travalue")
@Configuration
public class FeignClientConfig {
}
