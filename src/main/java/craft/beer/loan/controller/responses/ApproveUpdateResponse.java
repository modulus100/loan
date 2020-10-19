package craft.beer.loan.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ApproveUpdateResponse {

    @Getter
    final private String customerId;

    @Getter
    final private boolean approvedByAll;
}
