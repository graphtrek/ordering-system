package co.grtk.ordering.system.order.service.domain.valueobject;

import co.grtk.ordering.system.domain.valueobject.BaseId;

import java.util.UUID;

/*
    Do not use Lombok in core
    Value objects are immutable and only holds data,
    so identifier is not important for them.
    That means 2 value objects with same data,
    but different id’s considered to be the same value object.
 */
public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
