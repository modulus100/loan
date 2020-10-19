package craft.beer.loan.services;

import craft.beer.loan.data.entities.ApprovalRequestEntity;

import java.util.List;

public interface ILoanStatisticsService {

    LoanStatisticsService.Statistic computeStatistic(List<ApprovalRequestEntity> requests);
}
