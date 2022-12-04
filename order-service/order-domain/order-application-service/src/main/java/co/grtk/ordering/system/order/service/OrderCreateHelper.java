package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.order.service.domain.OrderDomainService;
import co.grtk.ordering.system.order.service.domain.entity.Buyer;
import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.entity.Seller;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;
import co.grtk.ordering.system.order.service.domain.exception.OrderDomainException;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.mapper.OrderDataMapper;
import co.grtk.ordering.system.order.service.ports.output.repository.BuyerRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.OrderRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final OrderDomainService orderDomainService;

    public OrderCreateHelper(SellerRepository sellerRepository,
                             BuyerRepository buyerRepository,
                             OrderRepository orderRepository,
                             OrderDataMapper orderDataMapper,
                             OrderDomainService orderDomainService) {
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
        this.orderDomainService = orderDomainService;
    }
    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkBuyer(createOrderCommand.getBuyerId());
        Seller seller = checkSeller(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order,seller);
        saveOrder(order);
        log.info("Order created with id:{}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }
    private Seller checkSeller(CreateOrderCommand createOrderCommand) {
        Seller seller = orderDataMapper.createOrderCommandToSeller(createOrderCommand);
        Optional<Seller> optionalSeller = sellerRepository.findSellerInformation(seller);
        if(optionalSeller.isEmpty()) {
            log.warn("Seller with id:{} does not exists", seller.getId().getValue());
            throw new OrderDomainException("Seller with id: " + seller.getId().getValue() + " does not exists");
        }
        return optionalSeller.get();
    }

    private void checkBuyer(UUID buyerId) {
        Optional<Buyer> optionalBuyer = buyerRepository.findBuyer(buyerId);
        if(optionalBuyer.isEmpty()) {
            log.warn("Buyer with id:{} does not exists", buyerId);
            throw new OrderDomainException("Buyer with id:" + buyerId + " does not exists");
        }
    }

    private void saveOrder(Order order) {
        Order savedOrder = orderRepository.saveOrder(order);
        if(savedOrder == null) {
            log.error("Can not save order!");
            throw new OrderDomainException("Can not save order!");
        }
        log.info("Order saved id:{}", savedOrder.getId().getValue());
    }

}
