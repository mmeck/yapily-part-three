package base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Comic;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.Base64.getDecoder;

public class TestBase {
    public static WebDriver driver;
    public static Properties prop;
    public static long PAGE_LOAD_TIMEOUT = 5;
    public static long IMPLICIT_WAIT = 5;

    //load properties file
    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream("./src/main/resources/application.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //initialise browser/webdriver
    public static Comic initialization(String url) {
        String browserName = prop.getProperty("browser");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("enable-automation");
//        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver");
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(url);
        return new Comic();
    }

    public static void explicitWait(WebElement element) {
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static List<String> readFromFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public String getPrivateKey() {
        return prop.getProperty("privateKey");
    }

    public String getPublicKey() {
        return prop.getProperty("publicKey");
    }

    public  static String decryptPassword(String text) {
        byte[] decodedBytes = getDecoder().decode(text);
        return new String(decodedBytes);
    }
}
