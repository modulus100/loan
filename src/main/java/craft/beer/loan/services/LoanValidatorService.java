package craft.beer.loan.services;

import craft.beer.loan.controller.exceptions.LoanException;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.data.repositories.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanValidatorService implements ILoanValidatorService {

    @Autowired
    private ILoanRepository repository;

    @SneakyThrows
    @Override
    public void validateApprovalCreateRequest(ApprovalRequestEntity request) {
        if (repository.isLoanRequestCreated(request.getCustomerId())) {
            throw new LoanException("Loan request already created");
        }
    }

    @SneakyThrows
    @Override
    public void validateApprovalUpdateRequest(ApprovalUpdateRequest request) {
        if (!repository.isLoanRequestCreated(request.getCustomerId())) {
            throw new LoanException("Loan request is not created");
        }

        if (!repository.approverExists(request.getApprover())) {
            throw new LoanException("Approver does not exist");
        }

        var approvalRequest = repository.getApprovalRequestById(request.getCustomerId());
        if (!approvalRequest.getWhoMustApprove().contains(request.getApprover())) {
            throw new LoanException("This approver can't approve");
        }
    }
}
