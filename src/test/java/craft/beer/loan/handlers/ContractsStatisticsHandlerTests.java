package craft.beer.loan.handlers;

import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        var approved = statistics.getApprovedContracts();

        // assert
        assertEquals(10, approved.getAverage());
        assertEquals(10, approved.getMin());
        assertEquals(10, approved.getMax());
        assertEquals(20, approved.getSum());
        assertEquals(requests.size(), approved.getCount());
    }
}
