package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.events.Event;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private Integer port;
    private WebDriverWait webDriverWait;
    @FindBy(id="credential-url")
    private WebElement credentialUrl;
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;
    @FindBy(id = "credential-submit")
    private WebElement credentialSubmitBtn;

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;
    @FindBy(id = "successMessage")
    private WebElement successMessage;
    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    @FindBy(id = "addCredentialModel")
    private WebElement addCredentialModelBtn;
    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(css = "#credentialTable .edit-btn")
    private List<WebElement> editCredentialBtns;

    @FindBy (css = "#credentialTable .delete-btn")
    private List<WebElement> deleteCredentialBtn;


    //Note Elements
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-submit-btn")
    private WebElement submitNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;
    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(css = ".edit-note-btn")
    private List<WebElement> editNoteBtn;
    @FindBy(css = "#note-table-body .delete-note-btn")
    private List<WebElement> deleteNoteBtn;

    public HomePage(WebDriver driver,Integer port){
        PageFactory.initElements(driver, this);
        this.driver= driver;
        this.port=port;
        webDriverWait=new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void clickLogoutBtn(){
        logoutBtn.click();
    }

    public void uploadACredential(String url, String username, String password){

        waitForPageLoad();
        navCredentialsTab.click();
        waitForPageLoad();

        /* *use javascript executor to solve  "ElementNotIntractableException"
       JavascriptExecutor jse= (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", addCredentialModelBtn); */

        //Use "webDriverWait.until(ExpectedConditions.elementToBeClickable(element id))"
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredentialModelBtn));
        addCredentialModelBtn.click();
       //wait for the visibility of the modal
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));


        waitForElement("credential-url");
        credentialUrl.click();
        credentialUrl.sendKeys(url);

        waitForElement("credential-username");
        credentialUsername.click();
        credentialUsername.sendKeys(username);

        waitForElement("credential-password");
        credentialPassword.click();
        credentialPassword.sendKeys(password);

        credentialSubmitBtn.click();
    }

    public void visitPage(){
        driver.get("http://localhost:"+port+"/home");
    }

    public void waitForElement(String id){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public void waitForPageLoad(){
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void verifyCredentialOnPage(String url){
        waitForPageLoad();
        navCredentialsTab.click();
        Boolean check=false;
        List<WebElement> list= driver.findElements(By.className("credential-record"));
        waitForElement("credentialTable");
        for (int i=0;i< list.size(); i++){
            if(list.get(i).getText().contains(url)){
                check=true;
            }
        }
        Assertions.assertTrue(check);
    }

    public void clickEditCredentailBtn(Integer i){
        editCredentialBtns.get(i).click();
    }

    public void fillCredential(String value, String field){
        if(field == "url"){
            if(value== null){
                JavascriptExecutor js= (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('credential-url').setAttribute('value', '')");

            }else {
                credentialUrl.sendKeys(value);
            }
        }

        if(field == "username"){
            if(value== null){
                JavascriptExecutor js= (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('credential-username').setAttribute('value', '')");
            }else {
                credentialUsername.sendKeys(value);
            }
        }

        if(field == "password"){
            if(value== null){
                JavascriptExecutor js= (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('credential-password').setAttribute('value', '')");
            }else {
                credentialPassword.sendKeys(value);
            }
        }

    }

    public String getInputContent(String id){
        return driver.findElement(By.id(id)).getText();
    }

    public void clickCredentialUpateBtn(){
        credentialSubmitBtn.click();
    }

    public void openCredentialTab(){
        navCredentialsTab.click();
    }

    public void deleteCredentialBtn(Integer i){
        deleteCredentialBtn.get(i).click();
    }


    public void openNoteTab(){
        noteTab.click();
    }

    public void clickSubmitNote(){
        submitNoteBtn.click();
    }

    public void clickOpenNoteModalBtn(){
        waitForElement("add-note-btn");
        addNoteBtn.click();
    }


    public void fillNote(String field, String value){
        //wait for the element title
        waitForElement("note-title");
        if(field=="title"){
            if(value== null){
                JavascriptExecutor js= (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('note-title').setAttribute('value', '')");

            }else {
                noteTitleField.click();
                noteTitleField.sendKeys(value);
            }
        }
        waitForElement("note-description");
        if(field== "description"){
            if(value== null){
                JavascriptExecutor js= (JavascriptExecutor) driver;
                js.executeScript("document.getElementById('note-description').setAttribute('value','')");
                noteDescriptionField.sendKeys("");
            }else {
                noteDescriptionField.click();
                noteDescriptionField.sendKeys(value);
            }
        }

    }

    public void clickEditNoteBtn(Integer i){
        waitForElement("note-table-body");
        editNoteBtn.get(i).click();
    }

    public void clickDeleteNoteBtn(Integer i){
        waitForElement("note-table-body");
        deleteNoteBtn.get(i).click();
    }








}
