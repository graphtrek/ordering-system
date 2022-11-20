package co.grtk.ordering.system.order.service.domain.valueobject;

import co.grtk.ordering.system.domain.valueobject.BaseId;

/*
    Do not use Lombok in core
    Value objects are immutable and only holds data,
    so identifier is not important for them.
    That means 2 value objects with same data,
    but different id’s considered to be the same value object.
 */
public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
