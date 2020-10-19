package craft.beer.loan.handlers;

import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.services.ILoanValidatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApprovalUpdateHandlerTests {

    @InjectMocks
    private ApprovalUpdateHandler handler;

    @Mock
    private ILoanValidatorService validationService;

    @Mock
    private ILoanRepository repository;

    @Test
    public void handle_approvedByAllIsFalse() {

    }
}
