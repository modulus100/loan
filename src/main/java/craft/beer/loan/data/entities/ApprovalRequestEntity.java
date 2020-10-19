package craft.beer.loan.data.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ApprovalRequestEntity {

    @Getter
    @Setter
    private String customerId;

    @Getter
    @Setter
    private int loanAmount;

    @Getter
    @Setter
    private Date approvedByAllDate;

    @Getter
    @Setter
    private Date createdDate = new Date();

    @Getter
    @Setter
    private boolean approvedByAll = false;

    @Getter
    @Setter
    private HashSet<String> whoMustApprove = new HashSet<>();
}
