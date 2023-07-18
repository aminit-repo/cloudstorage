package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.fasterxml.jackson.datatype.jdk8.WrappedIOException;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignupPage {
    private WebDriverWait webDriverWait;
    private Integer port;
    private WebDriver driver;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private  WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignUp;

    @FindBy(id = "successMsg")
    private WebElement successMsg;

    @FindBy(id = "login-link")
    private WebElement loginLink;




    public SignupPage(WebDriver driver, Integer port) {
        PageFactory.initElements(driver, this);
        webDriverWait=new WebDriverWait(driver, Duration.ofSeconds(2));
        this.port= port;
        this.driver= driver;
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    public void doMockSignUp(String firstName, String lastName, String userName, String password){
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        waitForPageLoad();

        // Fill out credentials
        waitForElement("inputFirstName");
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        waitForElement("inputLastName");
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        waitForElement("inputUsername");
        inputUsername.click();
        inputUsername.sendKeys(userName);

        waitForElement("inputPassword");
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        waitForElement("buttonSignUp");
        buttonSignUp.click();

		/* Check that the signup was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depending on the rest of your code.
		*/
        waitForElement("successMsg");
        Assertions.assertTrue(successMsg.getText().contains("You successfully signed up!"));
    }

    public void redirectToLogin(){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-link")));
        loginLink.click();
    }

    public void visitPage(){
        driver.get("http://localhost:"+port+"/signup");
    }

    public void waitForElement(String id){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public void waitForPageLoad(){
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
    }

}
