package craft.beer.loan.controller.exceptions;

public class LoanException extends Exception {

    public LoanException(String errorMessage) {
        super(errorMessage);
    }
}
