package entities;

public class Statement {
    private float change;
    private float balance;

    public Statement() {

    }

    public Statement(float change, float balance) {
        this.change = change;
        this.balance = balance;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
