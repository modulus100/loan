package craft.beer.loan.controller.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ApprovalCreateResponse {

    @Getter
    @Setter
    private String customerId;

    @Getter
    @Setter
    private int loanAmount;

    @Getter
    @Setter
    private Date created = new Date();
}
