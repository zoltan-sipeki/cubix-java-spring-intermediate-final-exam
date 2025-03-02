package hu.cubix.zoltan_sipeki.shipping_service.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;

import hu.cubix.zoltan_sipeki.shipping_service.service.ShippingSOAPService;
import jakarta.xml.ws.Endpoint;

@Configuration
public class SOAPConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint(JmsTemplate jmsTemplate, TaskScheduler taskScheduler) {
        var endpoint = new EndpointImpl(bus, new ShippingSOAPService(jmsTemplate, taskScheduler));
        endpoint.publish("/ShippingService");
        return endpoint;
    }
}
