package craft.beer.loan.handlers;

import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.data.ILoanRepository;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import craft.beer.loan.handlers.async.AsyncPing;
import craft.beer.loan.mappers.ApprovalMapper;
import craft.beer.loan.services.ILoanValidatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    public void handle_createRequestSuccess() {
        var request = new ApprovalCreateRequest();
        var requestEntity = new ApprovalRequestEntity();
        var response = new ApprovalCreateResponse();

        when(mapper.requestToEntity(request)).thenReturn(requestEntity);
        when(mapper.entityToResponse(requestEntity)).thenReturn(response);

        var result = handler.handle(request);

        verify(mapper, times(1))
                .requestToEntity(request);
        verify(validationService, times(1))
                .validateApprovalCreateRequest(requestEntity);
        verify(repository, times(1))
                .createApprovalRequest(requestEntity);
        verify(mapper, times(1))
                .entityToResponse(requestEntity);

        assertEquals(response, result);
    }
}