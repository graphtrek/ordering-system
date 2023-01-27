package co.grtk.ordering.system.order.service.domain;

import co.grtk.ordering.system.order.service.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import co.grtk.ordering.system.order.service.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import co.grtk.ordering.system.order.service.ports.output.message.publisher.sellerapproval.OrderPaidSellerRequestMessagePublisher;
import co.grtk.ordering.system.order.service.ports.output.repository.BuyerRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.OrderRepository;
import co.grtk.ordering.system.order.service.ports.output.repository.SellerRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "co.grtk.ordering.system")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidSellerRequestMessagePublisher orderPaidSellerRequestMessagePublisher() {
        return Mockito.mock(OrderPaidSellerRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public SellerRepository sellerRepository() {
        return Mockito.mock(SellerRepository.class);
    }

    @Bean
    public BuyerRepository buyerRepository() {
        return Mockito.mock(BuyerRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
