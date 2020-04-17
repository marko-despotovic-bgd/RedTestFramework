package com.red.testframework.pageobjects;

import java.util.ArrayList;
import java.util.List;

import com.red.testframework.utils.XPathUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.red.testframework.testconfiguration.TestConfiguration;
import com.red.testframework.utils.Log;
import com.red.testframework.utils.TimeUtil;

public class BasePage {
    protected WebDriver driver;
    protected TestConfiguration testConfiguration;
    private static Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        testConfiguration = new TestConfiguration();
        log = Log.getLog(this.getClass());
        PageFactory.initElements(driver, this);
    }

    public void quitWebDriver() {
        log.debug("quitWebDriver");
        if (driver != null) {
            driver.quit();
        }
    }

    public void pressEnterKeyOnElement(WebElement element) {
        waitUntilElementIsVisible(element);
        element.sendKeys(Keys.RETURN);
    }

    public void fillInInputFieldAndPressEnter(WebElement element, String value) {
        fillInInputField(element, value);
        pressEnterKeyOnElement(element);
    }

    public void dragAndDropItem(WebElement from, WebElement to) {
        Actions action = new Actions(driver);
        action.dragAndDrop(from, to).build().perform();
    }

    public void fillInInputField(WebElement element, String value, int timeout) {
        waitUntilElementIsVisible(element, timeout);
        element.clear();
        element.sendKeys(value);
    }

    public void fillInInputField(WebElement element, String value) {
        fillInInputField(element, value, TimeUtil.ELEMENT_VISIBLE_TIME);
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
        clickOnElement(element, TimeUtil.ELEMENT_VISIBLE_TIME);
    }

    public void waitUntilElementIsClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementIsClickable(WebElement element) {
        waitUntilElementIsClickable(element, TimeUtil.ELEMENT_VISIBLE_TIME);
    }

    public void waitUntilElementIsVisible(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementIsVisible(WebElement element) {
        waitUntilElementIsVisible(element, TimeUtil.ELEMENT_VISIBLE_TIME);
    }

    public boolean isElementPresent(By by) {
        if (driver.findElements(by).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDisplayed(WebElement element, int timeout) {
        waitUntilElementIsVisible(element, timeout);
        return element.isDisplayed();
    }

    public boolean isDisplayed(String elementXpath) {
            List<WebElement> elements = driver.findElements(By.xpath(elementXpath));
            return elements.size()!=0;
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

    public boolean isChecked(WebElement element) {
        waitUntilElementIsVisible(element);
        return element.isSelected();
    }

    public String getDivElementContainingTextXpath(String text) {
        return "//div[contains(text(),'" + text + "')]";
    }

    public String getElementContainingTextXpath(String text) {
        return "//*[contains(text(),'" + text + "')]";
    }

    public boolean isAttributePresent(By by, String attribute) {
        return "true".equals(driver.findElement(by).getAttribute(attribute));
    }

    public boolean isAttributePresent(WebElement element, String attribute) {
        return "true".equals(element.getAttribute(attribute));
    }

    public boolean hasCssClass(WebElement element, String cssClass) {
        return element.getAttribute("class").contains(cssClass);
    }

    public WebDriver getCurrentDriver() {
        return this.driver;
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public void verticalPageScroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,40)");
    }

    public void verticalPageScroll(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ")");
    }


    public void wait(int timeoutInSeconds) {
        try {
            Thread.sleep(timeoutInSeconds * 1000);
            Log.debug("Waiting for " + timeoutInSeconds + " seconds");
        } catch (InterruptedException e) {
            Log.error("Error" + e.getMessage() + ";\nStack trace: " + e.getStackTrace());
        }
    }

    public void switchTab(int tabNumber) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));
    }

    public void closeLastTab() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int tabNumber = tabs.size() - 1;
        driver.switchTo().window(tabs.get(tabNumber));
        driver.close();

        driver.switchTo().window(tabs.get(tabNumber - 1));
    }

    public List<WebElement> findDisplayedElements(By locator) {
        List<WebElement> allElements = driver.findElements(locator);
        List<WebElement> displayedElements = new ArrayList<WebElement>();
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                displayedElements.add(element);
            }
        }
        return displayedElements;
    }

    public List<WebElement> findDisplayedElements(WebElement rootElement, By locator) {
        List<WebElement> allElements = rootElement.findElements(locator);
        List<WebElement> displayedElements = new ArrayList<WebElement>();
        for (WebElement element : allElements) {
            if (element.isDisplayed()) {
                displayedElements.add(element);
            }
        }
        return displayedElements;
    }

    public List<WebElement> findElementsWithMatchingText(String text) {
        String xpathExpression = "//*[contains(text(), '" + text + "')]";
        List<WebElement> allElements = findDisplayedElements(By.xpath(xpathExpression));
        List<WebElement> matchingElements = new ArrayList<WebElement>();
        for (WebElement element : allElements) {
            String elementText = getElementText(element);
            if (text.equalsIgnoreCase(elementText)) {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    public WebElement findFirstElementsWithMatchingText(String text) {
        List<WebElement> matchingElements = findElementsWithMatchingText(text);
        if (!matchingElements.isEmpty()) {
            return matchingElements.get(0);
        } else {
            return null;
        }
    }

    public List<WebElement> findElementsWithMatchingText(WebElement rootElement, String text) {
        String xpathExpression = "//*[contains(text(), '" + text + "')]";
        List<WebElement> allElements = findDisplayedElements(rootElement, By.xpath(xpathExpression));
        List<WebElement> matchingElements = new ArrayList<WebElement>();
        for (WebElement element : allElements) {
            String elementText = getElementText(element);
            if (text.equalsIgnoreCase(elementText)) {
                matchingElements.add(element);
            }
        }
        return matchingElements;
    }

    public WebElement findFirstElementsWithMatchingText(WebElement rootElement, String text) {
        List<WebElement> matchingElements = findElementsWithMatchingText(rootElement, text);
        if (!matchingElements.isEmpty()) {
            return matchingElements.get(0);
        } else {
            return null;
        }
    }

    public boolean verifyPageIsDisplayed(WebElement pageIdentifyingElement, String identifyingElementText) {
        System.out.println(getElementText(pageIdentifyingElement) + " - " + identifyingElementText);
        return getElementText(pageIdentifyingElement).equals(identifyingElementText);
    }
}