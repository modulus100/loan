package craft.beer.loan.controller.responses;

import craft.beer.loan.handlers.ContractsStatisticsHandler;
import craft.beer.loan.services.LoanStatisticsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ContractsStatisticsResponse {

    @Getter
    @Setter
    private LoanStatisticsService.Statistic approvedContracts;
}
