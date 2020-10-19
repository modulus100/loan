package craft.beer.loan.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.IntSummaryStatistics;

@AllArgsConstructor
public class ContractsStatisticsResponse {

    @Getter
    @Setter
    private IntSummaryStatistics approvedContracts;
}
