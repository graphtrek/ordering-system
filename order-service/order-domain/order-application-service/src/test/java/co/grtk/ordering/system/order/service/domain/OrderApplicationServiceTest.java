package co.grtk.ordering.system.order.service.domain;


import co.grtk.ordering.system.domain.valueobject.BuyerId;
import co.grtk.ordering.system.domain.valueobject.OrderId;
import co.grtk.ordering.system.order.service.domain.entity.Buyer;
import co.grtk.ordering.system.order.service.domain.entity.Order;
import co.grtk.ordering.system.order.service.domain.entity.Seller;
import co.grtk.ordering.system.order.service.dto.create.CreateOrderCommand;
import co.grtk.ordering.system.order.service.dto.create.OrderAddress;
import co.grtk.ordering.system.order.service.dto.create.OrderItem;
import co.grtk.ordering.system.order.service.mapper.OrderDataMapper;
import co.grtk.ordering.system.order.service.ports.input.service.OrderApplicationService;
import co.grtk.ordering.system.order.service.ports.output.repository.BuyerRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.OrderRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.SellerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {OrderTestConfiguration.class})
public class OrderApplicationServiceTest {

    @Autowired
    public OrderApplicationService orderApplicationService;

    @Autowired
    public OrderDataMapper orderDataMapper;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public SellerRepository sellerRepository;

    @Autowired
    public BuyerRepository buyerRepository;

    public CreateOrderCommand createOrderCommand;
    public CreateOrderCommand createOrderCommandWrongPrice;
    public CreateOrderCommand createOrderCommandWrongProductPrice;
    public final UUID BUYER_ID = UUID.fromString("8d40bca9-8ebf-4d85-a1a4-a5df9ad25dd7");
    public final UUID SELLER_ID = UUID.fromString("5290b1a6-4707-4e99-bc5d-fb5b9f441bbe");
    public final UUID ORDER_ID = UUID.fromString("f693d9d9-5d4d-4c95-b4fc-f72e3e203f3a");
    public final UUID PRODUCT_ID = UUID.fromString("cfd725da-e9de-47a1-967c-19c5448bb9af");
    public final BigDecimal PRICE = new BigDecimal("200");

    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .buyerId(BUYER_ID)
                .sellerId(SELLER_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .buyerId(BUYER_ID)
                .sellerId(SELLER_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .buyerId(BUYER_ID)
                .sellerId(SELLER_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

/*
        Restaurant restaurantResponse = Restaurant.Builder
                .builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1",
                                new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2",
                                new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();
*/

        Buyer buyer = new Buyer();
        buyer.setId(new BuyerId(BUYER_ID));

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(UUID.randomUUID()));

        Seller seller = orderDataMapper.createOrderCommandToSeller(createOrderCommand);

        when(buyerRepository.findBuyer(BUYER_ID)).thenReturn(Optional.of(buyer));
        when(sellerRepository.findSellerInformation(seller)).thenReturn(Optional.of(seller));
    }
}
