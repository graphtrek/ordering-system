package co.grtk.ordering.system.order.service.domain.entity;

import co.grtk.ordering.system.domain.entity.AggregateRoot;
import co.grtk.ordering.system.domain.valueobject.*;
import co.grtk.ordering.system.order.service.domain.exception.OrderDomainException;
import co.grtk.ordering.system.order.service.domain.valueobject.OrderItemId;
import co.grtk.ordering.system.order.service.domain.valueobject.StreetAddress;
import co.grtk.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

/*
    Do not use Lombok in core

    Builder is generated with Intellij innerBuilder plugin

    No need to overwrite hashCode and equals
    as BaseEntity has the id and overwrites

    Entity objects identified only by id and nothing else
 */
public class Order extends AggregateRoot<OrderId> {
    // fields set when instantiate Order
    private final BuyerId buyerId;
    private final SellerId sellerId;
    private final List<OrderItem> items;
    private final StreetAddress deliveryAddress;
    private final Money price;

    // fields sets by business logic after created the entity
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Order(Builder builder) {
        super.setId(builder.orderId);
        buyerId = builder.buyerId;
        sellerId = builder.sellerId;
        items = builder.items;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING)
            throw new OrderDomainException("Order not in correct state for pay operation");
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if (orderStatus != OrderStatus.PAID)
            throw new OrderDomainException("Order not in correct state for approve operation!");
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID)
            throw new OrderDomainException("Order not in correct state for initCncelL operation!");
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.PAID || orderStatus == OrderStatus.CANCELLING))
            throw new OrderDomainException("Order not in the correct state for cancel operation!");
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(
                    failureMessages.stream().filter(failureMessage -> !failureMessage.isEmpty()).toList());
        }

        if (this.failureMessages == null)
            this.failureMessages = failureMessages;
    }

    void validateItemsPrice() {
        Money itemsTotalPrice = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!itemsTotalPrice.equals(price))
            throw new OrderDomainException(
                    "Order price " + price.getAmount() +
                            " is not equals with items price " + itemsTotalPrice);
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid())
            throw new OrderDomainException(
                    "Order item price " +
                            orderItem.getPrice().getAmount() +
                            " is not valid for product price " +
                            orderItem.getProduct().getPrice().getAmount());
    }

    private void validateTotalPrice() {
        if(price == null || !price.isGreaterThanZero())
            throw new OrderDomainException("Price must be grater than zero!");
    }

    private void validateInitialOrder() {
        if(orderStatus != null || getId() != null)
            throw new OrderDomainException("Order is not in correct state for initialization!");
    }

    public void initializeOrder() {
        super.setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initilaizeOrderItems();
    }

    private void initilaizeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem : items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public BuyerId getBuyerId() {
        return buyerId;
    }

    public SellerId getSellerId() {
        return sellerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }


    public static final class Builder {
        private OrderId orderId;
        private BuyerId buyerId;
        private SellerId sellerId;
        private List<OrderItem> items;
        private StreetAddress deliveryAddress;
        private Money price;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder buyerId(BuyerId val) {
            buyerId = val;
            return this;
        }

        public Builder sellerId(SellerId val) {
            sellerId = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
