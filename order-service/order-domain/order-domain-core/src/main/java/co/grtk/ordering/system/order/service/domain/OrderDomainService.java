package co.grtk.ordering.system.order.service.domain;

import co.grtk.ordering.system.order.service.domain.entity.Buyer;
import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.event.OrderCancelledEvent;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;
import co.grtk.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Buyer buyer);

    OrderPaidEvent payOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessage);

    void approveOrder(Order order);


}
