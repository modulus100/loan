package craft.beer.loan.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
public class ApproveUpdateResponse {

    @Getter
    final private String customerId;

    @Getter
    final private boolean approvedByAll;
}
