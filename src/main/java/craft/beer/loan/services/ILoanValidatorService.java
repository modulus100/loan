package craft.beer.loan.services;

import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.data.entities.ApprovalRequestEntity;

public interface ILoanValidatorService {

    void validateApprovalCreateRequest(ApprovalRequestEntity request);
    void validateApprovalUpdateRequest(ApprovalUpdateRequest request);
}
