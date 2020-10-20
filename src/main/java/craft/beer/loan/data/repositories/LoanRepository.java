package craft.beer.loan.data.repositories;

import craft.beer.loan.controller.requests.ApprovalUpdateRequest;
import craft.beer.loan.data.entities.ApprovalRequestEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LoanRepository implements ILoanRepository {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    private final HashSet<String> approvers = new HashSet<>();
    private final HashMap<String, ApprovalRequestEntity> approvalRequests = new HashMap<>();
    private final HashMap<String, HashSet<String>> approverApprovedRequests = new HashMap<>();

    @Override
    public void addApprovers(Collection<String> approvers) {
        writeLock.lock();
        try {
            this.approvers.addAll(approvers);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void initApprovedRequests(HashMap<String, HashSet<String>> request) {
        for (Map.Entry<String, HashSet<String>> entry : request.entrySet()) {
            approverApprovedRequests.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void createApprovalRequest(ApprovalRequestEntity request) {
        writeLock.lock();
        try {
            approvalRequests.put(request.getCustomerId(), request);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void updateApprovalRequest(ApprovalUpdateRequest request) {
        writeLock.lock();
        try {
            if (request.isApproved()) {
                approverApprovedRequests
                        .get(request.getApprover())
                        .add(request.getCustomerId());
            } else {
                approverApprovedRequests
                        .get(request.getApprover())
                        .remove(request.getCustomerId());
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean isLoanRequestCreated(String customerId) {
        readLock.lock();
        try {
            return approvalRequests.containsKey(customerId);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ApprovalRequestEntity getApprovalRequestById(String id) {
        readLock.lock();
        try {
            return approvalRequests.get(id);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean approverExists(String approver) {
        readLock.lock();
        try {
            return approvers.contains(approver);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Map<String, HashSet<String>> getApprovedApprovals(HashSet<String> approvers) {
        readLock.lock();
        try {
            return approverApprovedRequests.entrySet().stream()
                    .filter(requests -> approvers.contains(requests.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void updateApprovedByAll(String customerId, boolean approvedByAll) {
        writeLock.lock();
        try {
            var approvalRequest = approvalRequests.get(customerId);
            approvalRequest.setApprovedByAll(approvedByAll);
            approvalRequest.setApprovedByAllDate(new Date());
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<ApprovalRequestEntity> getApprovedRequestsByInterval(int interval) {
        readLock.lock();
        Function<ApprovalRequestEntity, Boolean> filterByInterval = request ->
                (new Date().getTime() - request.getApprovedByAllDate().getTime()) / 1000 > interval;
        try {
            return approvalRequests.values().stream()
                    .filter(request -> request.isApprovedByAll() && filterByInterval.apply(request))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public HashSet<String> getAllApprovers() {
        readLock.lock();
        try {
            return approvers;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void clearAll() {
        approvers.clear();
        approvalRequests.clear();
        approverApprovedRequests.clear();
    }
}
