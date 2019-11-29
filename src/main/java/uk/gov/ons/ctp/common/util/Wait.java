package uk.gov.ons.ctp.common.util;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class facilitates the ability for Cucumber to wait for certain events to happen within the
 * UI Waits until an expected condition occurs Waits for a specific element to be displayed Waits
 * for the presence of a specific element Waits for a page to fully load
 */
public class Wait {

  private WebDriver driver;

  public Wait(WebDriver driver) {
    this.driver = driver;
  }

  private void waitUntilCondition(ExpectedCondition condition, String timeoutMessage, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.withMessage(timeoutMessage);
    wait.until(condition);
  }

  public void forLoading(int timeout) {
    ExpectedCondition<Object> condition =
        ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
    String timeoutMessage = "Page didn't load after " + timeout + " seconds.";
    waitUntilCondition(condition, timeoutMessage, timeout);
  }

  public void forElementToBeDisplayed(int timeout, WebElement webElement, String webElementName) {
    ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOf(webElement);
    String timeoutMessage =
        webElementName + " wasn't displayed after " + Integer.toString(timeout) + " seconds.";
    waitUntilCondition(condition, timeoutMessage, timeout);
  }

  public void forPresenceOfElements(int timeout, By elementLocator, String elementName) {
    ExpectedCondition<List<WebElement>> condition =
        ExpectedConditions.presenceOfAllElementsLocatedBy(elementLocator);
    String timeoutMessage =
        elementName
            + " elements were not displayed after "
            + Integer.toString(timeout)
            + " seconds.";
    waitUntilCondition(condition, timeoutMessage, timeout);
  }
}
