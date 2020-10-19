package craft.beer.loan.controller.requests;

import an.awesome.pipelinr.Command;
import craft.beer.loan.controller.responses.ApproveUpdateResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ApprovalUpdateRequest implements Command<ApproveUpdateResponse> {

    @Getter
    @Setter
    @NotBlank
    @NotNull
    private String customerId;

    @Getter
    @Setter
    private boolean approved;

    @Getter
    @Setter
    @NotBlank
    @NotNull
    private String approver;
}
