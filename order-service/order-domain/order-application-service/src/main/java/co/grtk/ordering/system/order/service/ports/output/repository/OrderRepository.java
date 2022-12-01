package co.grtk.ordering.system.order.service.ports.output.repository;

import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order saveOrder(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);

}
