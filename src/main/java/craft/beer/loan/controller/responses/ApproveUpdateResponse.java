package craft.beer.loan.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ApproveUpdateResponse {

    @Getter
    @Setter
    private String customerId;

    @Getter
    @Setter
    private boolean approvedByAll;
}
