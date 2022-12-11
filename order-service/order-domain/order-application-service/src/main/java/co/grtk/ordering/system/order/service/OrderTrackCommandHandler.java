package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.exception.OrderDomainException;
import co.grtk.ordering.system.order.service.domain.valueobject.TrackingId;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderQuery;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderResponse;
import co.grtk.ordering.system.order.service.mapper.OrderDataMapper;
import co.grtk.ordering.system.order.service.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if(optionalOrder.isEmpty()) {
            log.error("Could not find order with trackingId: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderDomainException("Could not find order with trackingId:" + trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
    }
}
