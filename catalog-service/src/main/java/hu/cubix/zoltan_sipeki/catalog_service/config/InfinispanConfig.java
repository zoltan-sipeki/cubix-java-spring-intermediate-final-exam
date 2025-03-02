package hu.cubix.zoltan_sipeki.catalog_service.config;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class InfinispanConfig {

    @Bean(name = "productPriceHistoryCache")
    public org.infinispan.configuration.cache.Configuration productPriceHistoryCache() {
        return new ConfigurationBuilder()
            .simpleCache(true)
            .memory()
                .maxCount(1000)
                .whenFull(EvictionStrategy.REMOVE)
            .expiration()
                .lifespan(60000)
                .wakeUpInterval(60000)
        .build();
    }
}
