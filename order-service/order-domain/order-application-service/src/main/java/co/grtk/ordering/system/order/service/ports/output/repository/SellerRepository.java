package co.grtk.ordering.system.order.service.ports.output.repository;

import co.grtk.ordering.system.order.service.domain.entity.Seller;

import java.util.Optional;

public interface SellerRepository {

    Optional<Seller> findSellerInformation(Seller seller);
}
