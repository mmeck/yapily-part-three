package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Comic extends TestBase {

    public Comic() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(how = How.XPATH, using = "//h2")
    @CacheLookup
    private static WebElement pageHeader;

    @FindBy(how = How.XPATH, using = "//div[@class='cta-btn cta-btn--solid cta-btn--red active show-more']")
    @CacheLookup
    private static WebElement loadMoreButton;

    @FindBy(how = How.XPATH, using = "//div[@class='row-item comic-item']")
    private static List<WebElement> comicItemsLinks;

    @FindBy(how = How.XPATH, using = "//div[@class='JCMultiRow  JCMultiRow-comic_issue']")
    private static WebElement comicsDisplayArea;

    @FindBy(how = How.XPATH, using = "//span[@data-browse-fetchmore-limiter-trigger]")
    private static List<WebElement> dropDownMenuNew;

    @FindBy(how = How.XPATH, using = "//dl[@data-jcaccordion]//dt//span[1]")
    private static WebElement dropMenu;

    public int getComicListSizeOnWeb() {
        while (checkLoadMoreButton()) {
            sleep(3);
            scrollDown(loadMoreButton);
            hooverOverElement(loadMoreButton);
            dropMenu.click();
            sleep(3);
            if (dropDownMenuNew.size() > 0) {
                int lastIndex = dropDownMenuNew.size() - 1;
                String text = dropDownMenuNew.get(lastIndex).getText();

                dropDownMenuNew.get(lastIndex).click();
                sleep(3);
            }
        }

        explicitWait(comicsDisplayArea);

        return comicItemsLinks.size();

    }

    public boolean checkLoadMoreButton() {
        return loadMoreButton.isDisplayed();
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollDown(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", webElement);
    }

    public boolean isElementExist(By identifer) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(identifer));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    public void hooverOverElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public String verifyPageTitle() {
        return driver.getTitle();
    }

 public String verifyHeader() {

        return pageHeader.getText();
    }


}
