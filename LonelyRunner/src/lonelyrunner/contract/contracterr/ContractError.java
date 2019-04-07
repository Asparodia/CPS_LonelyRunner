package lonelyrunner.contract.contracterr;

public class ContractError extends Error {
	private static final long serialVersionUID = 5070397146756819924L;

	public ContractError(String methode, String message) {
		super(methode+ " : "+message);
	}
}
