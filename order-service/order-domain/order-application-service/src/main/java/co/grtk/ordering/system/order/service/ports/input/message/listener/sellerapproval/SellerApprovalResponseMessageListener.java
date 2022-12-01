package co.grtk.ordering.system.order.service.ports.input.message.listener.sellerapproval;

import co.grtk.ordering.system.order.service.dto.message.SellerApprovalResponse;

public interface SellerApprovalResponseMessageListener {

    void orderApproved(SellerApprovalResponse sellerApprovalResponse);

    void orderRejected(SellerApprovalResponse sellerApprovalResponse);
}
