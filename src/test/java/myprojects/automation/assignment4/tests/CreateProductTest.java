package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateProductTest extends BaseTest {

    @DataProvider
    public Object[][] LoginInfo() {
        return new String[][] {
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }


    @Test(dataProvider = "LoginInfo")
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        actions.login(login, password);
        actions.waitForContentLoad();
        WebElement adminPageTitle = driver.findElement(By.cssSelector(".breadcrumb-current>a"));
        Assert.assertTrue(adminPageTitle.getText().toString().equals("Dashboard"));
        actions.navigateToProductTab();
        actions.createProduct(ProductData.generate());
    }

    @Test(dependsOnMethods = "createNewProduct")
    public void verifyProduct() {
        // TODO implement logic to check product visibility on website
        SoftAssert softAssert = new SoftAssert();
        actions.allProductsPage();
        softAssert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'"+actions.productName+"')]")
        ).isDisplayed(),"Element is not displayed on the page");
        actions.ElementPage();
        softAssert.assertEquals(driver.findElement(By.xpath("//*[@id='main']/div[1]/div[2]/h1")).getText().
                toLowerCase(),actions.productName.toLowerCase());
        String price = driver.findElement(By.cssSelector(".current-price>span")).getText();
        softAssert.assertEquals(actions.SplitString(price), actions.productPrice);
        String productNumber = driver.findElement(By.cssSelector(".product-quantities>span")).getText();
        softAssert.assertEquals(actions.SplitString(productNumber), String.valueOf(actions.productQuantity));
        softAssert.assertAll();
    }

}
