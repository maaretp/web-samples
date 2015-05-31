package se.thinkcode.selenium.unit.checkbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectColorPage {
    private final WebDriver browser;

    public SelectColorPage(WebDriver browser) {
        this.browser = browser;

        String page = browser.getCurrentUrl() + "selectColor.html";
        browser.get(page);

        String expectedTitle = "Select color";
        String actualTitle = browser.getTitle();

        assertThat(actualTitle, is(expectedTitle));
    }

    public void selectGreen() {
        List<WebElement> checkboxes = browser.findElements(By.name("color"));
        for (WebElement checkbox : checkboxes) {
            String color = checkbox.getAttribute("value");
            if (color.equals("green")) {
                checkbox.click();
                return;
            }
        }
    }

    public SelectedColorPage submit() {
        WebElement checkbox = browser.findElement(By.name("color"));
        ;
        checkbox.submit();
        return new SelectedColorPage(browser);
    }
}
