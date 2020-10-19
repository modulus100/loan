package craft.beer.loan.services;

import com.github.javafaker.Faker;
import craft.beer.loan.controller.exceptions.LoanException;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoanValidatorServiceTests {

    @InjectMocks
    private LoanValidatorService validatorService;

    @Mock
    private ILoanRepository repository;

    private final Faker faker = new Faker();

    @Test
    public void validateApprovalCreateRequest_approvalRequestAlreadyCreatedLoanException() {
        // arrange
        var customerId = faker.name().username();
        var approvalEntity = new ApprovalRequestEntity();
        approvalEntity.setCustomerId(customerId);

        when(repository.isLoanRequestCreated(customerId)).thenReturn(true);

        // act
        Throwable exceptionThatWasThrown = Assertions.assertThrows(
                LoanException.class,
                () -> validatorService.validateApprovalCreateRequest(approvalEntity));

        // assert
        assertEquals(exceptionThatWasThrown.getMessage(), "Loan request already created");
    }

    @Test
    public void validateApprovalUpdateRequest_approvalRequestNotCreatedLoanException() {
        // arrange
        var customerId = faker.name().username();
        var approvalEntity = new ApprovalUpdateRequest();
        approvalEntity.setCustomerId(customerId);

        when(repository.isLoanRequestCreated(customerId)).thenReturn(false);

        // act
        Throwable exceptionThatWasThrown = Assertions.assertThrows(
                LoanException.class,
                () -> validatorService.validateApprovalUpdateRequest(approvalEntity));

        // assert
        assertEquals(exceptionThatWasThrown.getMessage(), "Loan request is not created");
    }
}
