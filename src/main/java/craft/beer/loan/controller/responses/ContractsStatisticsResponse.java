package craft.beer.loan.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.IntSummaryStatistics;

@AllArgsConstructor
public class ContractsStatisticsResponse {

    @Getter
    final private IntSummaryStatistics approvedContracts;
}
