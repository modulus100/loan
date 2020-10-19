package craft.beer.loan.handlers;

import an.awesome.pipelinr.Pipeline;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ContractsStatisticsHandlerTests {

    @InjectMocks
    private ContractsStatisticsHandler handler;

    @Mock
    private ILoanRepository repository;

    @Test
    public void computeStatistics_allValuesValid() {
        // arrange
        var request1 = new ApprovalRequestEntity();
        request1.setLoanAmount(10);
        var request2 = new ApprovalRequestEntity();
        request2.setLoanAmount(10);
        var requests = new ArrayList<ApprovalRequestEntity>();
        requests.add(request1);
        requests.add(request2);

        when(repository.getApprovedRequestsByInterval(anyInt())).thenReturn(requests);

        // act
        var statistics = handler.handle(new ContractsStatisticsRequest());

        // assert
        assertEquals(10, statistics.getAverage());
        assertEquals(10, statistics.getMin());
        assertEquals(10, statistics.getMax());
        assertEquals(20, statistics.getSum());
        assertEquals(requests.size(), statistics.getCount());
    }
}
