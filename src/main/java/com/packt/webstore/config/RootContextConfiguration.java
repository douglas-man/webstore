package com.packt.webstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ SecurityConfig.class })
public class RootContextConfiguration {
}
