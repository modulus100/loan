package craft.beer.loan.data;

import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.data.entities.ApprovalRequestEntity;

import java.util.*;

public interface ILoanRepository {

    void addApprovers(Collection<String> approvers);
    HashSet<String> getAllApprovers();
    void initApprovedRequests(HashMap<String, HashSet<String>> request);
    void createApprovalRequest(ApprovalRequestEntity request);
    ApprovalRequestEntity getApprovalRequestById(String id);
    void updateApprovalRequest(ApprovalUpdateRequest request);
    boolean isLoanRequestCreated(String customerId);
    boolean approverExists(String approver);
    Map<String, HashSet<String>> getApprovedApprovals(HashSet<String> approvers);
    void updateApprovedByAll(String customerId, boolean approvedByAll);
    void clearAll();
    List<ApprovalRequestEntity> getApprovedRequestsByInterval(int interval);
}
