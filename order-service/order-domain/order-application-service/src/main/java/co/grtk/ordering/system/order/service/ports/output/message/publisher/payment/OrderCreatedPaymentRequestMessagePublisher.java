package co.grtk.ordering.system.order.service.ports.output.message.publisher.payment;

import co.grtk.ordering.system.domain.event.publisher.DomainEventPublisher;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
