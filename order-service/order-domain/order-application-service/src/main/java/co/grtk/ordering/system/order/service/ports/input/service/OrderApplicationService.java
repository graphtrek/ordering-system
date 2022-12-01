package co.grtk.ordering.system.order.service.ports.input.service;

import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderResponse;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderQuery;
import co.grtk.ordering.system.order.service.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
