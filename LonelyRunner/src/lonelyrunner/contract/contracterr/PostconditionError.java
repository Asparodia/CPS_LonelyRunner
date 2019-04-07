package lonelyrunner.contract.contracterr;

public class PostconditionError extends ContractError {
	private static final long serialVersionUID = 5070397146756819924L;

	public PostconditionError(String m,String message) {
		super(">>>>>Postcondition failed : "+m, message);
	}
}
