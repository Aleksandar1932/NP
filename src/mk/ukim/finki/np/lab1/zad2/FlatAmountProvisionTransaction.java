package mk.ukim.finki.np.lab1.zad2;

public class FlatAmountProvisionTransaction extends Transaction {
    private String flatAmount;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatAmount = flatProvision;
    }

    public String getFlatAmount() {
        return flatAmount;
    }

    public double getProvision() {
        return Double.parseDouble(flatAmount.substring(0, flatAmount.length() - 1));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        FlatAmountProvisionTransaction toCheck = (FlatAmountProvisionTransaction) obj;
        return this.flatAmount.equals(toCheck.flatAmount);
    }
}
