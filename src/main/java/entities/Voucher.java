package entities;

public class Voucher {
    private float amount;
    private String currency;
    private String number;

    public Voucher() {

    }

    public Voucher(int amount, String number) {
        this(amount, number, null);
    }

    public Voucher(int amount, String number, String currency) {
        this.amount = amount;
        this.number = number;
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
