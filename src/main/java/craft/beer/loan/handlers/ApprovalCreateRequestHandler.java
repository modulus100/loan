package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import craft.beer.loan.mappers.ApprovalMapper;
import craft.beer.loan.services.ILoanValidatorService;
import org.springframework.stereotype.Component;

@Component
class ApprovalCreateRequestHandler implements Command.Handler<ApprovalCreateRequest, ApprovalCreateResponse> {

    final private ILoanValidatorService validationService;
    final private ILoanRepository repository;
    final private ApprovalMapper mapper;

    public ApprovalCreateRequestHandler(
            ILoanValidatorService validationService,
            ILoanRepository repository,
            ApprovalMapper mapper) {

        this.validationService = validationService;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ApprovalCreateResponse handle(ApprovalCreateRequest request) {
        ApprovalRequestEntity entityRequest = mapper.requestToEntity(request);
        validationService.validateApprovalCreateRequest(entityRequest);
        repository.createApprovalRequest(entityRequest);
        return mapper.entityToResponse(entityRequest);
    }
}
