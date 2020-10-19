package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import craft.beer.loan.mappers.ApprovalMapper;
import craft.beer.loan.services.ILoanValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ApprovalCreateRequestHandler implements Command.Handler<ApprovalCreateRequest, ApprovalCreateResponse> {

    @Autowired
    private ILoanValidatorService validationService;

    @Autowired
    private ILoanRepository repository;

    @Autowired
    private ApprovalMapper mapper;

    @Override
    public ApprovalCreateResponse handle(ApprovalCreateRequest request) {
        ApprovalRequestEntity entityRequest = mapper.requestToEntity(request);
        validationService.validateApprovalCreateRequest(entityRequest);
        repository.createApprovalRequest(entityRequest);
        return mapper.entityToResponse(entityRequest);
    }
}
