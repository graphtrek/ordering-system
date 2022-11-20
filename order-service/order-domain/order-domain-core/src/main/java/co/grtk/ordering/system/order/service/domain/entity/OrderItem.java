package co.grtk.ordering.system.order.service.domain.entity;

import co.grtk.ordering.system.domain.entity.BaseEntity;
import co.grtk.ordering.system.domain.valueobject.Money;
import co.grtk.ordering.system.domain.valueobject.OrderId;
import co.grtk.ordering.system.order.service.domain.valueobject.OrderItemId;

/*
    Do not use Lombok in core

    Builder is generated with Intellij innerBuilder plugin

    No need to overwrite hashCode and equals
    as BaseEntity has the id and overwrites

    Entity objects identified only by id and nothing else
 */
public class OrderItem extends BaseEntity<OrderItemId> {
    // Reference to the Order
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    // subTotal = price * quantity
    private final Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public Builder id(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
