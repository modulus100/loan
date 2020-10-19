package craft.beer.loan.mappers;

import craft.beer.loan.controller.requests.ApprovalCreateRequest;
import craft.beer.loan.controller.responses.ApprovalCreateResponse;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApprovalMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "approvedByAll", ignore = true)
    @Mapping(target = "approvedByAllDate", ignore = true)
    @Mapping(target = "whoMustApprove", source = "approvers")
    ApprovalRequestEntity requestToEntity(ApprovalCreateRequest request);

    @Mapping(target = "created", ignore = true)
    ApprovalCreateResponse entityToResponse(ApprovalRequestEntity request);
}
