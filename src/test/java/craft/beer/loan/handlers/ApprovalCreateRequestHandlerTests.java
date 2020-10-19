package craft.beer.loan.handlers;

import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.mappers.ApprovalMapper;
import craft.beer.loan.services.ILoanValidatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ApprovalCreateRequestHandlerTests {

    @InjectMocks
    private ApprovalCreateRequestHandler handler;

    @Mock
    private ILoanValidatorService validationService;

    @Mock
    private ILoanRepository repository;

    @Mock
    private ApprovalMapper mapper;

    @Test
    public void createRequest() {

        //handler.handle(new ApprovalCreateRequest());

        assertTrue(true);
    }
}
