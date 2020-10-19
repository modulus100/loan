package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.controller.responses.ContractsStatisticsResponse;
import craft.beer.loan.data.repositories.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ContractsStatisticsHandler implements Command.Handler<
        ContractsStatisticsRequest, ContractsStatisticsResponse> {

    @Autowired
    private ILoanRepository repository;

    @Override
    public ContractsStatisticsResponse handle(ContractsStatisticsRequest request) {
        var defaultInterval = 60; // seconds default
        var stats = repository.getApprovedRequestsByInterval(defaultInterval).stream()
                .mapToInt(ApprovalRequestEntity::getLoanAmount)
                .summaryStatistics();

        return new ContractsStatisticsResponse(stats);
    }
}
