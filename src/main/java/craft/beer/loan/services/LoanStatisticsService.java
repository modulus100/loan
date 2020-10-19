package craft.beer.loan.services;

import craft.beer.loan.controller.responses.ContractsStatisticsResponse;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanStatisticsService implements ILoanStatisticsService {

    public static class Statistic {
        int count;
        int sum;
        double avg;
        int max;
        int min;
    }

    @Override
    public Statistic computeStatistic(List<ApprovalRequestEntity> requests) {
        var firstFlag = true;
        var statistic = new Statistic();


        for (ApprovalRequestEntity approval : requests) {
            if (approval.getLoanAmount() > statistic.max) {
                statistic.max = approval.getLoanAmount();
            }

            if (approval.getLoanAmount() < statistic.min || firstFlag) {
                statistic.min = approval.getLoanAmount();
                firstFlag = false;
            }

            statistic.sum += approval.getLoanAmount();
        }

        statistic.count = requests.size();
        statistic.avg = (double) statistic.sum / statistic.count;

        return statistic;
    }
}
