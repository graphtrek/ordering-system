package co.grtk.ordering.system.order.service.domain.entity;

import co.grtk.ordering.system.domain.entity.AggregateRoot;
import co.grtk.ordering.system.domain.valueobject.SellerId;

import java.util.List;

public class Seller extends AggregateRoot<SellerId> {
    private final List<Product> products;
    private boolean active;

    private Seller(Builder builder) {
        super.setId(builder.sellerId);
        products = builder.products;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private SellerId sellerId;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public Builder sellerId(SellerId val) {
            sellerId = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Seller build() {
            return new Seller(this);
        }
    }
}
