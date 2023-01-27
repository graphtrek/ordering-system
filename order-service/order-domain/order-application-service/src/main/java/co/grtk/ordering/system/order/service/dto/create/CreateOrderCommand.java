package co.grtk.ordering.system.order.service.dto.create;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.NotNull;
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
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress address;
    @NotNull
    private final BigDecimal price;
}
