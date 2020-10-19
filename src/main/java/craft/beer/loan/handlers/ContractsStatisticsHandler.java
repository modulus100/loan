package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.IntSummaryStatistics;


@Component
public class ContractsStatisticsHandler implements Command.Handler<
        ContractsStatisticsRequest, IntSummaryStatistics> {

    @Autowired
    private ILoanRepository repository;

    @Override
    public IntSummaryStatistics handle(ContractsStatisticsRequest request) {
        var defaultInterval = 60; // seconds default
        return repository.getApprovedRequestsByInterval(defaultInterval).stream()
                .mapToInt(ApprovalRequestEntity::getLoanAmount)
                .summaryStatistics();
    }
}
