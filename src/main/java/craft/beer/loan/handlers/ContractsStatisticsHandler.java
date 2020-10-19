package craft.beer.loan.handlers;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.controller.responses.ContractsStatisticsResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import craft.beer.loan.services.ILoanStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractsStatisticsHandler implements Command.Handler<
        ContractsStatisticsRequest, ContractsStatisticsResponse> {

    @Autowired
    private ILoanRepository repository;

    @Autowired
    private ILoanStatisticsService statisticsService;

    @Override
    public ContractsStatisticsResponse handle(ContractsStatisticsRequest request) {
        var defaultInterval = 60; // seconds
        var approvedRequests = repository.getApprovedRequestsByInterval(defaultInterval);
        return new ContractsStatisticsResponse(statisticsService.computeStatistic(approvedRequests));
    }


}
