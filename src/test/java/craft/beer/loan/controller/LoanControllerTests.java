package craft.beer.loan.controller;

import an.awesome.pipelinr.Pipeline;
import com.github.javafaker.Faker;
import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import craft.beer.loan.controller.responses.ContractsStatisticsResponse;
import craft.beer.loan.handlers.async.AsyncPing;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.IntSummaryStatistics;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoanControllerTests {

	@InjectMocks
	private LoanController loanController;

	@Mock
	private Pipeline pipeline;

	final private Faker faker = new Faker();

	@Test
	public void testAsyncSuccess() throws Exception {
		// arrange
		String response = "works async";
		when(pipeline.send(any(AsyncPing.class))).thenReturn(CompletableFuture.completedFuture(response));

		// act
		CompletableFuture<String> result = loanController.testAsync();

		//assert
		assertEquals(result.get(), response);
		verify(pipeline, times(1)).send(any(AsyncPing.class));
	}

	@Test
	public void getStatistics_StatisticsHandled() {
		var stats = new IntSummaryStatistics();
		var response = new ContractsStatisticsResponse(stats);

		when(pipeline.send(any(ContractsStatisticsRequest.class))).thenReturn(response);

		var result = loanController.getStatistics();

		assertEquals(response, result);
		verify(pipeline, times(1)).send(any(ContractsStatisticsRequest.class));
	}

	@Test
	public void createApprovalRequest_requestCreated() {
		// arrange
		var response = new ApprovalCreateResponse();
		var request = new ApprovalCreateRequest();

		when(pipeline.send(any(ApprovalCreateRequest.class))).thenReturn(response);

		// act
		var result = loanController.createApprovalRequest(request);

		// assert
		assertEquals(response, result);
	}

	@Test
	public void updateApprovalRequest_requestUpdated() {
		// arrange
		var response = new ApproveUpdateResponse("test", true);
		var request = new ApprovalUpdateRequest();

		when(pipeline.send(any(ApprovalUpdateRequest.class))).thenReturn(response);

		// act
		var result = loanController.updateApprovalRequest(request);

		// assert
		assertEquals(response, result);
	}
}
