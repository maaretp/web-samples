package se.thinkcode.selenium.actions.buy.currency;

import java.util.Currency;

public class Converter {
    public static final double EUR_TO_USD = 0.81;
    private final Action action;
    private final int amount;
    private final Currency to;
    private final Currency from;

    public Converter(Action action, int amount, Currency to, Currency from) {
        this.action = action;
        this.amount = amount;
        this.to = to;
        this.from = from;
    }

    public int getCost() {
        int cost = 0;

        if (action.isBuy()) {
            cost = getConversion();
            cost = addCommission(cost);
        }

        return cost;
    }

    public Currency getFromCurrency() {
        return from;
    }

    private int getConversion() {
        Currency USD = Currency.getInstance("USD");
        Currency EUR = Currency.getInstance("EUR");

        if (from.equals(EUR) && to.equals(USD)) {
            return (int) (amount * EUR_TO_USD);
        }

        throw new UnhandledConversionException();
    }

    private int addCommission(int cost) {
        return cost + 10;
    }
}
