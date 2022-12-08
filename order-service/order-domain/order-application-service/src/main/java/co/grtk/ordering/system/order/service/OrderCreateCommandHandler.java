package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.order.service.domain.OrderDomainService;
import co.grtk.ordering.system.order.service.domain.entity.Buyer;
import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.entity.Seller;
import co.grtk.ordering.system.order.service.domain.event.OrderCreatedEvent;
import co.grtk.ordering.system.order.service.domain.exception.OrderDomainException;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderResponse;
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
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final SellerRepository sellerRepository;

    private final BuyerRepository buyerRepository;

    private final OrderDataMapper orderDataMapper;

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    public OrderCreateCommandHandler(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            SellerRepository sellerRepository,
            BuyerRepository buyerRepository,
            OrderDataMapper orderDataMapper,
            ApplicationDomainEventPublisher applicationDomainEventPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkBuyer(createOrderCommand.getBuyerId());
        Seller seller = checkSeller(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order,seller);
        Order savedOrder = saveOrder(order);
        log.info("Order created id:{}", savedOrder.getId().getValue());
        applicationDomainEventPublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(savedOrder);
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

    private Order saveOrder(Order order) {
      Order savedOrder = orderRepository.saveOrder(order);
      if(savedOrder == null) {
          log.error("Can not save order!");
          throw new OrderDomainException("Can not save order!");
      }
      log.info("Order saved id:{}", savedOrder.getId().getValue());
      return savedOrder;
    }
}
