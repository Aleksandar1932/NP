package mk.ukim.finki.np.lab1.zad2;

public abstract class Transaction {
    protected long fromId;
    protected long toId;
    String description;
    String amount;

    public Transaction() {

    }

    public Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public double getDoubleAmount() {
        return Double.parseDouble(amount.substring(0, amount.length() - 1)); //10.00$:String => 10.00:double
    }

    public String getDescription() {
        return description;
    }

    public abstract double getProvision();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Transaction toCheck = (Transaction) obj;
        return this.fromId == toCheck.fromId && this.toId == toCheck.toId
                && this.description.equals(toCheck.description)
                && this.amount.equals(toCheck.amount);
    }
}
