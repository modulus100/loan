package craft.beer.loan.controller;

import com.github.javafaker.Faker;
import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ILoanRepository repository;

    private final Faker faker = new Faker();

    @Test
    public void integrationTestAsync() {
        // arrange
        String response = "works async";

        // act
        String result = restTemplate.getForObject("http://localhost:" + port + "/async", String.class);

        // assert
        assertEquals(response, result);
    }

    @Test
    public void createApprovalRequestSuccess() {
        // arrange
        reset();
        var approvalRequest = new ApprovalCreateRequest();
        approvalRequest.setCustomerId("XX-XXXX-XXX");
        approvalRequest.setLoanAmount(faker.number().randomDigit());
        approvalRequest.setApprovers(repository.getAllApprovers());

        var request = new HttpEntity<>(approvalRequest, new HttpHeaders());

        // act
        var url = "http://localhost:" + port + "/approval-request";
        var response = restTemplate.postForEntity(
                url,
                request,
                ApprovalCreateResponse.class);

        // assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("XX-XXXX-XXX", response.getBody().getCustomerId());
    }

    @Test
    public void checkApprovedByAll() {
        // arrange
        reset();
        var customerId = "XX-XXXX-XXX";
        var requestEntity = new ApprovalRequestEntity();
        requestEntity.setCustomerId(customerId);
        requestEntity.setLoanAmount(faker.number().randomDigit());
        requestEntity.setWhoMustApprove(repository.getAllApprovers());
        repository.createApprovalRequest(requestEntity);

        ResponseEntity<ApproveUpdateResponse> response = null;
        var url = "http://localhost:" + port + "/approval-request";

        for (String approver : repository.getAllApprovers()) {
            var updateRequest = new ApprovalUpdateRequest();
            updateRequest.setCustomerId(customerId);
            updateRequest.setApprover(approver);
            updateRequest.setApproved(true);

            var request = new HttpEntity<>(updateRequest, new HttpHeaders());

            // act
            response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    ApproveUpdateResponse.class);
        }

        // assert
        assert response != null;
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isApprovedByAll());
    }

    private void generateApprovers() {
        int approversCount = 3;
        Faker faker = new Faker();
        List<String> approvers = new ArrayList<>();
        HashMap<String, HashSet<String>> approvedRequests = new HashMap<>();

        for (int i = 0; i < approversCount; i++) {
            String approverUsername = faker.name().username();
            approvers.add(approverUsername);
            approvedRequests.put(approverUsername, new HashSet<>());
        }

        repository.addApprovers(approvers);
        repository.initApprovedRequests(approvedRequests);
    }

    private void reset() {
        repository.clearAll();
        generateApprovers();
    }
}
