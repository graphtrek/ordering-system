package co.grtk.ordering.system.order.service.ports.output.message.publisher.payment;

import co.grtk.ordering.system.domain.event.publisher.DomainEventPublisher;
import co.grtk.ordering.system.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
