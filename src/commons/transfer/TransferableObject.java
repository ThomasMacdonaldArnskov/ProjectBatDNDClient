package commons.transfer;

import java.io.Serializable;

public class TransferableObject implements Serializable {
	

	private final Transferables transfer;

	public TransferableObject(Transferables transfer) {
		this.transfer = transfer;
	}

	public Transferables getTransfer() {
		return transfer;
	}

}
