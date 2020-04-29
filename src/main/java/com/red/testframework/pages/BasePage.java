package com.red.testframework.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.red.testframework.utils.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red.testframework.utils.Log;

public class BasePage {
    public WebDriver driver;
    public static Logger log = LoggerFactory.getLogger(BasePage.class);
    public Properties properties = new Properties();

    public BasePage(WebDriver driver) {
        this.driver = driver;
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public void fillInInputField(WebElement element, String value) {
        waitUntilElementIsVisible(element);
        element.clear();
        element.sendKeys(value);
    }


    public void clickOnElement(WebElement element, int timeout) {
        try {
            // this should work most of the time
            waitUntilElementIsClickable(element, timeout);
            element.click();
        } catch (Exception e1) {
            // TODO: Maybe separate those in 3 methods and handle externally
            try {
                // if not, try this way
                element.sendKeys(Keys.SPACE);
            } catch (Exception e2) {
                // last resort
                String id = element.getAttribute("id");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("document.querySelector(\"*[id$='" + id + "'] span\").click()");
            }
        }
    }

    public void clickOnElement(WebElement element) {
        clickOnElement(element, Constants.ELEMENT_VISIBLE_TIME);
    }

    private void waitUntilElementIsClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.ELEMENT_VISIBLE_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUntilElementIsVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Constants.ELEMENT_VISIBLE_TIME); // Use Time util in class if longer wait is needed
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public String getElementText(WebElement element) {
        try {
            waitUntilElementIsVisible(element);
            return element.getText();
        } catch (NoSuchElementException e) {
            Log.error("Failed to locate element.");
            return null;
        }
    }

    public void wait(int timeoutInSeconds) {
        try {
            Thread.sleep(timeoutInSeconds * 1000);
            Log.debug("Waiting for " + timeoutInSeconds + " seconds");
        } catch (InterruptedException e) {
            Log.error("Error" + e.getMessage() + ";\nStack trace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public List<WebElement> findDisplayedElements(By locator) {
        List<WebElement> allElements = driver.findElements(locator);
        List<WebElement> displayedElements = new ArrayList<>();
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                displayedElements.add(element);
            }
        }
        return displayedElements;
    }

    private List<WebElement> findDisplayedElements(WebElement rootElement, By locator) {
        List<WebElement> allElements = rootElement.findElements(locator);
        List<WebElement> displayedElements = new ArrayList<>();
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                displayedElements.add(element);
            }
        }
        return displayedElements;
    }

    private List<WebElement> findElementsWithMatchingText(String text) {
        String xpathExpression = "//*[contains(text(), '" + text + "')]";
        List<WebElement> allElements = findDisplayedElements(By.xpath(xpathExpression));
        List<WebElement> matchingElements = new ArrayList<>();
        for (WebElement element : allElements) {
            String elementText = getElementText(element);
            if (text.equalsIgnoreCase(elementText)) {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    private List<WebElement> findElementsWithMatchingText(WebElement rootElement, String text) {
        String xpathExpression = "//*[contains(text(), '" + text + "')]";
        List<WebElement> allElements = findDisplayedElements(rootElement, By.xpath(xpathExpression));
        List<WebElement> matchingElements = new ArrayList<>();
        for (WebElement element : allElements) {
            String elementText = getElementText(element);
            if (text.equalsIgnoreCase(elementText)) {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    public boolean verifyPageIsDisplayed(By by, String identifyingElementText) {
        List<WebElement> elements = driver.findElements(by);
        if (elements.size() != 0)
            return getElementText(elements.get(0)).equals(identifyingElementText);
        else
             Log.error("Page element not found!");
        return false;
    }

    public void closeWebDriver() {
        Log.debug("closeWebDriver");
        driver.close();
    }

    public void quitWebDriver() {
        log.debug("quitWebDriver");
        if (driver != null) {
            driver.quit();
        }
    }


    public void dragAndDropItem(WebElement from, WebElement to) {
        Actions action = new Actions(driver);
        action.dragAndDrop(from, to).build().perform();
    }

    public void waitUntilElementIsClickable(WebElement element) {
        waitUntilElementIsClickable(element, Constants.ELEMENT_VISIBLE_TIME);
    }

    public boolean isChecked(WebElement element) {
        waitUntilElementIsVisible(element);
        return element.isSelected();
    }


    public boolean isAttributePresent(By by, String attribute) {
        return "true".equals(driver.findElement(by).getAttribute(attribute));
    }

    public boolean isAttributePresent(WebElement element, String attribute) {
        return "true".equals(element.getAttribute(attribute));
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    public boolean isDisplayed(WebElement element) {
        waitUntilElementIsVisible(element);
        return element.isDisplayed();
    }

    public boolean isDisplayed(String elementXpath) {
        List<WebElement> elements = driver.findElements(By.xpath(elementXpath));
        return elements.size() != 0;
    }

    public void switchTab(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));

    }
}