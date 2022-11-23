package co.grtk.ordering.system.order.service.domain;

import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.entity.Product;
import co.grtk.ordering.system.order.service.domain.entity.Seller;
import co.grtk.ordering.system.order.service.domain.event.OrderCancelledEvent;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;
import co.grtk.ordering.system.order.service.domain.event.OrderPaidEvent;
import co.grtk.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Seller seller) {
        validateSeller(seller);
        setOrderProductInformation(order, seller);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id:{} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void setOrderProductInformation(Order order, Seller seller) {
        order.getItems().forEach(orderItem -> seller.getProducts().forEach(sellerProduct -> {
            Product currentProduct = orderItem.getProduct();
            if(currentProduct.equals(sellerProduct))
                currentProduct.updateWithConfirmedNameAndPrice(sellerProduct.getName(),sellerProduct.getPrice());
        }));
    }

    private void validateSeller(Seller seller) {
        if(!seller.isActive())
            throw new OrderDomainException(
                    "Seller with id:" + seller.getId().getValue() + " is inactive");
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id:{} is paid ", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id:{}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        cancelOrder(order,failureMessages);
        log.info("Order with id:{} is cancelled",order.getId().getValue());
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id:{} is approved", order.getId().getValue());
    }
}
