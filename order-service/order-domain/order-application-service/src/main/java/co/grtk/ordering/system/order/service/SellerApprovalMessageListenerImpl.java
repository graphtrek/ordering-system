package co.grtk.ordering.system.order.service;

import co.grtk.ordering.system.order.service.dto.message.SellerApprovalResponse;
import co.grtk.ordering.system.order.service.ports.input.message.listener.sellerapproval.SellerApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class SellerApprovalMessageListenerImpl implements SellerApprovalResponseMessageListener {
    @Override
    public void orderApproved(SellerApprovalResponse sellerApprovalResponse) {

    }

    @Override
    public void orderRejected(SellerApprovalResponse sellerApprovalResponse) {

    }
}
