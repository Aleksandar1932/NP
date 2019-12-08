package mk.ukim.finki.np.lab1.zad2;

import java.text.DecimalFormat;

public class Bank {
    String name;
    Account accounts[];
    private double totalTransfers;
    private double totalProvisions;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = accounts.clone();
        totalTransfers = 0;
        totalProvisions = 0;
    }

    public boolean makeTransaction(Transaction t) {
        int fromIndex = findIndexById(t.fromId);
        int toIndex = findIndexById(t.toId);

        if (fromIndex == -1 || toIndex == -1) {
            return false; //Znaci nekoj od id-ata ne postoi pa transakcijata ne bi bila vozmozna;
        }

        double fromBalance = accounts[fromIndex].getDoubleBalance();
        double toBalance = accounts[toIndex].getDoubleBalance();

        double provision = t.getProvision();

        if (fromBalance < (t.getDoubleAmount() + provision)) {
            return false;
        }

        totalProvisions += provision;
        totalTransfers += t.getDoubleAmount();

        if (fromIndex == toIndex) {
            fromBalance -= provision;
            accounts[fromIndex].setBalance(decimalFormatter(fromBalance));
            return true;
        } else {
            fromBalance -= (t.getDoubleAmount() + provision);
            toBalance += t.getDoubleAmount();
        }

        accounts[fromIndex].setBalance(decimalFormatter(fromBalance));
        accounts[toIndex].setBalance(decimalFormatter(toBalance));

        return true;
    }

    public int findIndexById(long keyId) {
        int i = 0;
        int retValue = -1;
        boolean notFound = true;
        while (notFound) {
            if (accounts[i].getId() == keyId) {
                retValue = i;
                notFound = false;
            }
            i++;
        }
        return retValue;
    }

    public String totalTransfers() {
        return decimalFormatter(totalTransfers);
    }

    public String totalProvision() {
        return decimalFormatter(totalProvisions);
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public String decimalFormatter(double amountToFormat) {
        DecimalFormat formatDecimal = new DecimalFormat("#0.00");
        return formatDecimal.format(amountToFormat) + "$";
    }

    @Override
    public String toString() {
        String retString = "";
        retString += "Name: " + this.name + "\n\n";
        for (int i = 0; i < accounts.length; i++) {
            retString += accounts[i].toString();
        }

        return retString;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return account.equals(obj);
    }
}
