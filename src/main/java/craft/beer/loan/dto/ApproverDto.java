package craft.beer.loan.dto;

import lombok.Getter;
import lombok.Setter;

public class ApproverDto {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean approved;
}
