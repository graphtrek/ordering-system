package co.grtk.ordering.system.order.service.domain.event;

import co.grtk.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {

        super(order, createdAt);
    }
}
