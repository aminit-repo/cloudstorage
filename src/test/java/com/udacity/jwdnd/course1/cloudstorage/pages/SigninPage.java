package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

public class SigninPage {
    private  WebDriver driver;
    private WebDriverWait webDriverWait;

    private Integer port;
    @FindBy(id = "inputUsername")
    private WebElement loginUserName;

    @FindBy(id = "inputPassword")
    private WebElement loginPassword;

    @FindBy(id = "login-button")
    private  WebElement loginButton;

    public SigninPage(WebDriver driver, Integer port) {
        PageFactory.initElements(driver, this);
        this.driver= driver;
        this.port= port;
        webDriverWait=new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    public void doLogIn(String userName, String password)
    {

        waitForElement("inputUsername");
        loginUserName.click();
        loginUserName.sendKeys(userName);

        waitForElement("inputPassword");
        loginPassword.click();
        loginPassword.sendKeys(password);

        waitForElement("login-button");
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void visitPage(){
        driver.get("http://localhost:"+port+"/login");
    }

    public void waitForElement(String id){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public void waitForPageLoad(){
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }



}
