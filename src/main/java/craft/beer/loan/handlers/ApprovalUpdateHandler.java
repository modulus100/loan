package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.services.ILoanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ApprovalUpdateHandler implements Command.Handler<ApprovalUpdateRequest, ApproveUpdateResponse> {

    @Autowired
    private ILoanRepository repository;

    @Autowired
    private ILoanValidatorService validatorService;

    @Override
    public ApproveUpdateResponse handle(ApprovalUpdateRequest request) {
        validatorService.validateApprovalUpdateRequest(request);
        repository.updateApprovalRequest(request);
        var approvedByAll = approvedByAll(request.getCustomerId());

        if (approvedByAll) {
            // notify and send to customer
            repository.updateApprovedByAll(request.getCustomerId(), true);
        }

        return new ApproveUpdateResponse(request.getCustomerId(), approvedByAll);
    }

    private boolean approvedByAll(String customerId) {
        var approvalRequest = repository.getApprovalRequestById(customerId);
        var approvedApprovals = repository.getApprovedApprovals(approvalRequest.getWhoMustApprove());

        for (String mustApprove : approvalRequest.getWhoMustApprove()) {
            if (!approvedApprovals.containsKey(mustApprove)) {
                return false;
            }

            var approvedCustomers = approvedApprovals.get(mustApprove);
            if (!approvedCustomers.contains(customerId)) {
                return false;
            }
        }

        return true;
    }
}

