package craft.beer.loan.controller;

import an.awesome.pipelinr.Pipeline;
import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.controller.requests.ContractsStatisticsRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import craft.beer.loan.controller.responses.ContractsStatisticsResponse;
import craft.beer.loan.handlers.async.AsyncPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class LoanController {

    @Autowired
    private Pipeline pipeline;

    @GetMapping("/async")
    public CompletableFuture<String> testAsync() {
        return pipeline.send(new AsyncPing());
    }

    @PostMapping("/approval-request")
    @ResponseStatus(HttpStatus.CREATED)
    public ApprovalCreateResponse createApprovalRequest(@Valid @RequestBody ApprovalCreateRequest request) {
        return pipeline.send(request);
    }

    @PutMapping("/approval-request")
    public ApproveUpdateResponse updateApprovalRequest(@Valid @RequestBody ApprovalUpdateRequest request) {
        return pipeline.send(request);
    }

    @GetMapping("/statistics")
    public ContractsStatisticsResponse getStatistics() {
        return pipeline.send(new ContractsStatisticsRequest());
    }

    /**
     *  Handles request validation exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
