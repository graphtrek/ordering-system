package co.grtk.ordering.system.domain.entity;

/*
    Marker class to distinguish root object from base entities

    Do not use Lombok in core
 */
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {
}
