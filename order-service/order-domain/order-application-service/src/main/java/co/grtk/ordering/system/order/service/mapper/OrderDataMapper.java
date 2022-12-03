package co.grtk.ordering.system.order.service.mapper;

import co.grtk.ordering.system.domain.valueobject.BuyerId;
import co.grtk.ordering.system.domain.valueobject.Money;
import co.grtk.ordering.system.domain.valueobject.ProductId;
import co.grtk.ordering.system.domain.valueobject.SellerId;
import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.entity.OrderItem;
import co.grtk.ordering.system.order.service.domain.entity.Product;
import co.grtk.ordering.system.order.service.domain.entity.Seller;
import co.grtk.ordering.system.order.service.domain.valueobject.StreetAddress;
import co.grtk.ordering.system.order.service.domain.valueobject.TrackingId;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Seller createOrderCommandToSeller(CreateOrderCommand createOrderCommand) {
        return Seller.builder()
                .sellerId(new SellerId(createOrderCommand.getSellerId()))
                .products(createOrderCommand.getOrderItems().stream().map(orderItem ->
                                new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .orderTrackingId(order.getTrackingId().getValue())
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .buyerId(new BuyerId(createOrderCommand.getBuyerId()))
                .price(new Money(createOrderCommand.getPrice()))
                .sellerId(new SellerId(createOrderCommand.getSellerId()))
                .deliveryAddress(orderAddressToDeliveyAddress(createOrderCommand.getAddress()))
                .items(orderItemsToOrderItemsEntities(createOrderCommand.getOrderItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntities(
            List<co.grtk.ordering.system.order.service.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> OrderItem.builder()
                .price(new Money(orderItem.getPrice()))
                .product(new Product(new ProductId(orderItem.getProductId())))
                .quantity(orderItem.getQuantity())
                .subTotal(new Money(orderItem.getSubTotal()))
                .build()
        ).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToDeliveyAddress(StreetAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }
}
