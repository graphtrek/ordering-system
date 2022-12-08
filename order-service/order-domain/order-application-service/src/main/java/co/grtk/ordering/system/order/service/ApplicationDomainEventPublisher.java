package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.domain.event.publisher.DomainEventPublisher;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class ApplicationDomainEventPublisher implements
        ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {

    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
        log.info("OrderCreatedEvent published id:{}", domainEvent.getOrder().getId().getValue());
    }
}
