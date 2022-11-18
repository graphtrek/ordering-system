package co.grtk.ordering.system.domain.entity;

import lombok.Data;

@Data
public abstract class BaseEntity<ID> {
    private ID id;
}
