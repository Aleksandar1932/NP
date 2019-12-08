package mk.ukim.finki.np.lab1.zad2;

import java.util.Random;

public class Account {
    protected String name;
    protected long id;
    protected String balance;

    public Account(String name, String balance) {
        this.name = name;
        this.id = new Random().nextLong(); //Generate random ID;
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public double getDoubleBalance() {
        return Double.parseDouble(balance.substring(0, balance.length() - 1));
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n"
                + "Balance: " + this.balance + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        Account toCheck = (Account) obj;
        return ((this.name == toCheck.name) && (this.id == toCheck.id) && (this.balance == toCheck.balance));
    }
}
