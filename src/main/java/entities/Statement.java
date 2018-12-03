package entities;

public class Statement {
    private double change;
    private double balance;

    public Statement() {

    }

    public Statement(double change, double balance) {
        this.change = change;
        this.balance = balance;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
