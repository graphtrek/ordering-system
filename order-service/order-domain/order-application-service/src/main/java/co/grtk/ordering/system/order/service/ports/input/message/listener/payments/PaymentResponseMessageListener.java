package co.grtk.ordering.system.order.service.ports.input.message.listener.payments;

import co.grtk.ordering.system.order.service.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
