package co.grtk.ordering.system.domain.valueobject;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {

    public ProductId(UUID id) {
        super(id);
    }
}
