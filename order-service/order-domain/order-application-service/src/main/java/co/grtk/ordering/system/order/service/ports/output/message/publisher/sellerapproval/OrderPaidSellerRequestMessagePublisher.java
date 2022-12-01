package co.grtk.ordering.system.order.service.ports.output.message.publisher.sellerapproval;

import co.grtk.ordering.system.domain.event.publisher.DomainEventPublisher;
import co.grtk.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidSellerRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
