package craft.beer.loan.controller;

import an.awesome.pipelinr.Pipeline;
import craft.beer.loan.handlers.async.AsyncPing;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class LoanControllerTests {

	@InjectMocks
	private LoanController loanController;

	@Mock
	private Pipeline pipeline;

	@Test
	public void testAsync() throws Exception {
		// arrange
		String response = "works async";
		when(pipeline.send(any(AsyncPing.class))).thenReturn(CompletableFuture.completedFuture(response));

		// act
		CompletableFuture<String> result = loanController.testAsync();

		//assert
		assertThat(result.get(), is(response));
		verify(pipeline, times(1)).send(any(AsyncPing.class));
	}
}
