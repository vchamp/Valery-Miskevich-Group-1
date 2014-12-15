package org.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ShopRepositoryConfig.class, ShopServiceConfig.class, ShopInitializerConfig.class, FactoryConfig.class})
public class ShopConfig {

}
