package mk.ukim.finki.np.lab1.zad2;

public class FlatPercentProvisionTransaction extends Transaction {
    private int percent;

    public FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDollar) {
        super(fromId, toId, "FlatPercent", amount);
        this.percent = centsPerDollar;
    }

    public int getPercent() {
        return percent;
    }

    public double getProvision() {
        return percent / 100.0 * (int) super.getDoubleAmount();
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
        FlatPercentProvisionTransaction toCheck = (FlatPercentProvisionTransaction) obj;
        return this.percent == toCheck.percent;
    }
}
