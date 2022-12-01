package co.grtk.ordering.system.order.service.ports.output.repository;

import co.grtk.ordering.system.order.service.domain.entity.Buyer;

import java.util.Optional;
import java.util.UUID;

public interface BuyerRepository {

    Optional<Buyer> findBuyer(UUID buyerId);
}
