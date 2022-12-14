package co.grtk.ordering.system.order.service.domain.entity;

import co.grtk.ordering.system.domain.entity.BaseEntity;
import co.grtk.ordering.system.domain.valueobject.Money;
import co.grtk.ordering.system.domain.valueobject.ProductId;

/*
    Do not use Lombok in core

    Builder is generated with Intellij innerBuilder plugin

    No need to overwrite hashCode and equals
    as BaseEntity has the id and overwrites

    Entity objects identified only by id and nothing else
 */
public class Product extends BaseEntity<ProductId> {
    private Money price;
    private String name;

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public Product(ProductId productId, Money price, String name) {
        super.setId(productId);
        this.price = price;
        this.name = name;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
