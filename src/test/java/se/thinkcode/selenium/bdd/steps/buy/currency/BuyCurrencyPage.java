package se.thinkcode.selenium.bdd.steps.buy.currency;

import se.thinkcode.selenium.actions.buy.currency.Action;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Currency;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BuyCurrencyPage {
    private WebDriver browser;

    public BuyCurrencyPage(WebDriver browser) {
        this.browser = browser;

        String actualTitle = browser.getTitle();
        String expectedTitle = "Buy currency";

        assertThat(actualTitle, is(expectedTitle));
    }

    public void selectAction(Action action) {
        WebElement radioButton = browser.findElement(By.id(action.getAction()));
        radioButton.click();
    }

    public void selectWantedCurrency(Currency to) {
        WebElement selectElement = browser.findElement(By.id("toCurrency"));
        Select select = new Select(selectElement);

        select.selectByValue(to.getCurrencyCode());
    }

    public WebElement setWantedAmount(int amount) {
        WebElement amountField = browser.findElement(By.id("amount"));
        amountField.sendKeys("" + amount);

        return amountField;
    }

    public void selectCurrencyToPayWith(Currency from) {
        WebElement selectElement = browser.findElement(By.id("fromCurrency"));
        Select select = new Select(selectElement);

        select.selectByValue(from.getCurrencyCode());
    }

    public ResultPage submitForm() {
        WebElement form = browser.findElement(By.id("orderCurrency"));
        form.submit();

        return new ResultPage(browser);
    }
}
