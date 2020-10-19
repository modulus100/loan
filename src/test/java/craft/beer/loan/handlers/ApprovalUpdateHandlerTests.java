package craft.beer.loan.handlers;

import com.github.javafaker.Faker;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import craft.beer.loan.services.ILoanValidatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApprovalUpdateHandlerTests {

    @InjectMocks
    private ApprovalUpdateHandler handler;

    @Mock
    private ILoanValidatorService validationService;

    @Mock
    private ILoanRepository repository;

    final Faker faker = new Faker();

    @Test
    public void handle_NoApprovers() {
        // arrange
        var customerId = faker.name().username();
        var response = new ApproveUpdateResponse(customerId, false);
        var request = new ApprovalUpdateRequest();
        var requestEntity = new ApprovalRequestEntity();
        request.setCustomerId(customerId);

        when(repository.getApprovalRequestById(customerId)).thenReturn(requestEntity);
        when(repository.getApprovedApprovals(requestEntity.getWhoMustApprove())).thenReturn(new HashMap<>());

        // act
        var result = handler.handle(request);

        assertEquals(response.getCustomerId(), result.getCustomerId());
        assertEquals(response.isApprovedByAll(), result.isApprovedByAll());
    }

    @Test
    public void handle_approverHasNoApprovals() {
        // arrange
        var customerId = faker.name().username();
        var response = new ApproveUpdateResponse(customerId, false);
        var request = new ApprovalUpdateRequest();
        var whoMustApprove = new HashSet<String>();
        var requestEntity = new ApprovalRequestEntity();

        whoMustApprove.add(faker.name().username());
        request.setCustomerId(customerId);
        requestEntity.setWhoMustApprove(whoMustApprove);

        when(repository.getApprovalRequestById(customerId)).thenReturn(requestEntity);
        when(repository.getApprovedApprovals(requestEntity.getWhoMustApprove())).thenReturn(new HashMap<>());

        // act
        var result = handler.handle(request);

        assertEquals(response.getCustomerId(), result.getCustomerId());
        assertEquals(response.isApprovedByAll(), result.isApprovedByAll());
    }

    @Test
    public void handle_approverDidNotApprove() {
        // arrange
        var customerId = faker.name().username();
        var response = new ApproveUpdateResponse(customerId, false);
        var request = new ApprovalUpdateRequest();
        var approvers = new HashSet<String>();
        var requestEntity = new ApprovalRequestEntity();
        var whoMustApproveUserName = faker.name().username();
        var approvals = new HashMap<String, HashSet<String>>();

        approvals.put(whoMustApproveUserName, new HashSet<>());
        approvers.add(whoMustApproveUserName);
        request.setCustomerId(customerId);
        requestEntity.setWhoMustApprove(approvers);

        when(repository.getApprovalRequestById(customerId)).thenReturn(requestEntity);
        when(repository.getApprovedApprovals(requestEntity.getWhoMustApprove())).thenReturn(approvals);

        // act
        var result = handler.handle(request);

        assertEquals(response.getCustomerId(), result.getCustomerId());
        assertEquals(response.isApprovedByAll(), result.isApprovedByAll());
    }

    @Test
    public void handle_approvedByAllApprovers() {
        // arrange
        var customerId = faker.name().username();
        var response = new ApproveUpdateResponse(customerId, true);
        var request = new ApprovalUpdateRequest();
        var approvers = new HashSet<String>();
        var requestEntity = new ApprovalRequestEntity();
        var whoMustApproveUserName = faker.name().username();
        var approvals = new HashMap<String, HashSet<String>>();
        var approvedCustomers = new HashSet<String>();
        approvedCustomers.add(customerId);

        approvals.put(whoMustApproveUserName, approvedCustomers);
        approvers.add(whoMustApproveUserName);
        request.setCustomerId(customerId);
        requestEntity.setWhoMustApprove(approvers);

        when(repository.getApprovalRequestById(customerId)).thenReturn(requestEntity);
        when(repository.getApprovedApprovals(requestEntity.getWhoMustApprove())).thenReturn(approvals);

        // act
        var result = handler.handle(request);

        assertEquals(response.getCustomerId(), result.getCustomerId());
        assertEquals(response.isApprovedByAll(), result.isApprovedByAll());
    }
}
