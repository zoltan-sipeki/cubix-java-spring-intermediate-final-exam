package hu.cubix.zoltan_sipeki.order_service.jms;

import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        try {
            configuration.addAcceptorConfiguration("remote", "tcp://localhost:61616");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
