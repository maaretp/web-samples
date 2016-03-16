package se.thinkcode.selenium.unit.datadriven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import se.thinkcode.selenium.unit.form.ConfirmPasswordSentPage;
import se.thinkcode.selenium.unit.form.RequestPasswordPage;

import java.util.Collection;
import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChangePasswordForLoopTest {
    private WebDriver browser;
    private String baseUrl;

    @Before
    public void setUp() {
        browser = new FirefoxDriver();
        baseUrl = "http://selenium.thinkcode.se/requestPassword";
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void request_new_password() {
        for (String account : accounts()) {
            String expected = "A new password has been sent to " + account;

            browser.get(baseUrl);
            RequestPasswordPage requestPasswordPage = new RequestPasswordPage(browser);
            ConfirmPasswordSentPage confirmPasswordSentPage = requestPasswordPage.requestNewPassword(account);

            String actual = confirmPasswordSentPage.getConfirmationMessage();

            assertThat(actual, is(expected));
        }
    }

    private Collection<String> accounts() {
        Collection<String> accounts = new LinkedList<>();
        accounts.add("Sune");
        accounts.add("Gretchen");

        return accounts;
    }
}
