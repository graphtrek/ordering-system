package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderResponse;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderQuery;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderResponse;
import co.grtk.ordering.system.order.service.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final TrackOrderCommandHandler trackOrderCommandHandler;

    OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, TrackOrderCommandHandler trackOrderCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.trackOrderCommandHandler = trackOrderCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return trackOrderCommandHandler.trackOrder(trackOrderQuery);
    }
}
