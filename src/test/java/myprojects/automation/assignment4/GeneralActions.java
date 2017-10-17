package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private By emailInput = By.id("email");
    private By passwordInput = By.id("passwd");
    private By loginBtn = By.name("submitLogin");
    private By refreshIcon = By.xpath("//*[@id='ajax_running']/i");
    private By catalogTab = By.xpath("//*[@id='subtab-AdminCatalog']/a");
    private By productsTab = By.xpath("//*[@id='subtab-AdminProducts']/a");
    private By addProductBtn = By.id("page-header-desc-configuration-add");
    private By checkBtn = By.className("switch-input");
   // private By categoryNameField = By.cssSelector("#name_1");
    private By saveButton = By.xpath("//*[@id='submit']");
    public String productName;
    public int productQuantity;
    public String productPrice;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(emailInput).sendKeys(login);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginBtn).click();

    }

    public void navigateToProductTab() {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(catalogTab)).build().perform();
        WebElement products = driver.findElement(productsTab);
        wait.until(ExpectedConditions.elementToBeClickable(products));
        actions.moveToElement(products).build().perform();
        products.click();
        wait.until(ExpectedConditions.elementToBeClickable(addProductBtn));
    }

    public void createProduct(ProductData newProduct) {
        driver.findElement(addProductBtn).click();
        productName = newProduct.getName();
        driver.findElement(By.id("form_step1_name_1")).sendKeys(newProduct.getName());
        productQuantity = newProduct.getQty();
        driver.findElement(By.id("form_step1_qty_0_shortcut")).sendKeys(newProduct.getQty().toString());
        productPrice = newProduct.getPrice();
        driver.findElement(By.id("form_step1_price_ttc_shortcut")).sendKeys(newProduct.getPrice());
        if (!driver.findElement(checkBtn).isSelected()) {
            driver.findElement(checkBtn).click();
        }
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("growls"))));
        driver.findElement(By.xpath("//*[@id='growls']/div/div[1]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        driver.findElement(saveButton).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("growls"))));
        driver.findElement(By.xpath("//*[@id='growls']/div/div[1]")).click();

    }

/*    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean scrollPageDown(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        boolean scrollResult = (boolean) executor.executeScript(
                "var scrollBefore = $(window).scrollTop();" +
                        "window.scrollTo(scrollBefore, document.body.scrollHeight);" +
                        "return $(window).scrollTop() > scrollBefore;");
        return scrollResult;
    }*/

    public void allProductsPage() {

        driver.get(Properties.getBaseUrl());
        WebElement allProductsLink = driver.findElement(By.xpath("//*[@id='content']/section/a"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", allProductsLink);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='main']/div[1]/h1"))));
    }

    public void ElementPage() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", driver.findElement(By.xpath(
                    "//*[contains(text(),'"+productName+"')]")));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(
                ".btn.btn-primary.add-to-cart"))));

    }

    public String SplitString(String to_split) {
        String[] parts = to_split.split(" ");
        return parts[0];
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

        wait.until(ExpectedConditions.invisibilityOfElementLocated(refreshIcon));

    }
}
