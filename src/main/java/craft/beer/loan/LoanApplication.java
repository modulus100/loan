package craft.beer.loan;

import com.github.javafaker.Faker;
import craft.beer.loan.data.ILoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class LoanApplication {

	@Autowired
	private ILoanRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			generateApprovers();
		};
	}

	public void generateApprovers() {
		int approversCount = 3;
		Faker faker = new Faker();
		List<String> approvers = new ArrayList<>();
		HashMap<String, HashSet<String>> approvedRequests = new HashMap<>();

		for (int i = 0; i < approversCount; i++) {
			String approverUsername = faker.name().username();
			approvers.add(approverUsername);
			approvedRequests.put(approverUsername, new HashSet<>());
		}

		repository.addApprovers(approvers);
		repository.initApprovedRequests(approvedRequests);
	}
}
