package co.grtk.ordering.system.order.service.dto.create;

import co.grtk.ordering.system.order.service.domain.entity.OrderItem;
import co.grtk.ordering.system.order.service.domain.valueobject.StreetAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {
    @NotNull
    private final UUID sellerId;
    @NotNull
    private final UUID buyerId;
    @NotNull
    private final List<OrderItem> orderItems;
    @NotNull
    private final StreetAddress address;
    @NotNull
    private final BigDecimal price;
}
