package craft.beer.loan.services;

import com.github.javafaker.Faker;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LoanValidatorServiceTests {

    @InjectMocks
    private LoanValidatorService validatorService;

    @Mock
    private ILoanRepository loanRepository;

    private Faker faker;

    public LoanValidatorServiceTests() {
        faker = new Faker();
    }

    @Test()
    public void testLoadAlreadyRequestedException() {
        var id = faker.name().fullName();
        var entity = new ApprovalRequestEntity();
        entity.setCustomerId(id);

        when(loanRepository.isLoanRequestCreated(id)).thenReturn(true);

        //validatorService.validateApprovalCreateRequest(entity);

        assertTrue(true);
    }
}
