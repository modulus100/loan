package craft.beer.loan.controller.requests;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;

public class ApprovalCreateRequest implements Command<ApprovalCreateResponse> {

    @Getter
    @Setter
    @Pattern(regexp = "^[A-Z0-9]{2}-[A-Z0-9]{4}-[A-Z0-9]{3}")
    private String customerId;

    @Getter
    @Setter
    @Min(value = 1)
    private int loanAmount;

    @Getter
    @Setter
    @NotEmpty
    @Size(min=1, max=3)
    private HashSet<String> approvers;
}
