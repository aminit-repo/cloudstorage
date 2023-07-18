package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SigninPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectCustomApplicationTest {
    private WebDriver driver;

    @LocalServerPort
    private int port;
    @BeforeAll
    public static void  beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        this.driver = new ChromeDriver(option);
    }



    /**
     * TEST 1:  Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
     *
     * This test verifies that an unauthorized user can only access the login and signup pages
     * */
 @Test
    public void unAuthorizeUser(){
     SigninPage signinPage= new SigninPage(driver, port);
     //visit the page
     signinPage.visitPage();
     //wait for the page to load
     signinPage.waitForPageLoad();

     // Check if we have been redirected to the log in page.
     Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

     SignupPage signupPage= new SignupPage(driver, port);
     //Access the signup page
     signupPage.visitPage();

     signupPage.waitForPageLoad();
     //Test if page opens
     Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

     HomePage homePage= new HomePage(driver,port);
     //Access the home page without authentication
     homePage.visitPage();

     //expect to be redirected to login page
     Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

    }


    /**
     *  TEST 1: Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
     *
     *  A test that signs up a new user, logs in, verifies that the home page is accessible, logs out,
     * and verifies that the home page is no longer accessible.
     */
    @Test
    public void loginAndLogout(){
        signupAndLogin();
        //Logout the user
        HomePage homePage= new HomePage(driver, port);
        homePage.clickLogoutBtn();
        SigninPage signinPage= new SigninPage(driver, port);
        //wait for login page to load
        signinPage.waitForPageLoad();

        //expect to be redirected to login page
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        // verifies that the home page is no longer accessible.
        unAuthorizeUser();
    }


    /**
     * TEST 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
     *
     *   A test that creates a note, and verifies it is displayed.
     */
    @Test
    public void createNote(){
        HomePage homePage= new HomePage(driver,port);
        //signup and login
        signupAndLogin();

        homePage.openNoteTab();
        homePage.clickOpenNoteModalBtn();
        homePage.fillNote("title", "Web Development");
        homePage.fillNote("description","Spring Boot enables developers focus on the Business Logic");
        homePage.clickSubmitNote();
        homePage.waitForPageLoad();
        homePage.openNoteTab();
        //verify it shows on the page
        homePage.waitForElement("note-table-body");
        Boolean condition=(driver.findElements(By.cssSelector("#note-table-body tr")).size() == 1) && (driver.findElements(By.cssSelector("#note-table-body .note-title-th")).get(0).getText().equals("Web Development"));
        Assertions.assertTrue(condition);
    }


    /**
     * TEST 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
     *  A test that edits an existing note and verifies that the changes are displayed.
     */
    @Test
    public void editNotes(){
        //execute create note test
        createNote();
        HomePage homePage= new HomePage(driver,port);
        homePage.clickEditNoteBtn(0);
        homePage.waitForPageLoad();
        homePage.waitForElement("noteModal");
        homePage.fillNote("title", null);
        homePage.fillNote("title", "Java WNDC");
        homePage.clickSubmitNote();
        homePage.waitForPageLoad();
        homePage.openNoteTab();
        homePage.waitForElement("note-table-body");
        Assertions.assertEquals("Java WNDC", driver.findElements(By.cssSelector("#note-table-body .note-title-th")).get(0).getText());
    }

    /**
     *  TEST 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
     *  A test that deletes a note and verifies that the note is no longer displayed.
     */
    @Test
    public void deleteNote(){
        createNote();
        HomePage homePage= new HomePage(driver,port);
        homePage.waitForPageLoad();
        homePage.openNoteTab();
        homePage.clickDeleteNoteBtn(0);
        homePage.waitForPageLoad();
        Assertions.assertTrue(driver.findElements(By.cssSelector("#delete-note-btn .tr")).size() == 0);
    }





    /**
     * TEST 3:  Write Tests for Credential Creation, Viewing, Editing, and Deletion.
     *
     *  A test that creates a set of credentials, verifies that they are displayed,
     *  and verifies that the displayed password is encrypted.
     */
    @Test
    public void createCredentials(){
        signupAndLogin();
        HomePage homePage= new HomePage(driver, port);
        homePage.visitPage();
        homePage.uploadACredential("https://udacity.com","samdavis","factors");
        homePage.waitForPageLoad();
        Assertions.assertTrue(driver.findElement(By.id("successMessage")).getText().contains("credentials added successfully"));

        //create a new set of credential
        homePage.uploadACredential("https://testdomain.com","sae","tors");
        homePage.waitForPageLoad();
        Assertions.assertTrue(driver.findElement(By.id("successMessage")).getText().contains("credentials added successfully"));
        //verifies that they are displayed
        homePage.verifyCredentialOnPage("https://testdomain.com");
    }


    /**
     * TEST 3: Write Tests for Credential Creation, Viewing, Editing, and Deletion.
     *
     * A test that views an existing set of credentials, verifies that the viewable password is unencrypted,
     * edits the credentials, and verifies that the changes are displayed.
     *
     */
    @Test
    public void viewCredential(){
        signupAndLogin();
        HomePage homePage= new HomePage(driver, port);
        homePage.visitPage();

        //upload a credentials
        homePage.uploadACredential("https://udacity.com","samdavis","factors");
        homePage.waitForPageLoad();
        Assertions.assertTrue(driver.findElement(By.id("successMessage")).getText().contains("credentials added successfully"));

        homePage.verifyCredentialOnPage("https://udacity.com");
        //edit the credentials
        homePage.clickEditCredentailBtn(0);
        //wait for the credential modal to show
        homePage.waitForElement("credential-password");

        //assert that the password is unencrypted
        Assertions.assertEquals("factors", driver.findElement(By.id("credential-password")).getDomProperty("value"));

        //update the username to Jwdnd and password to jwndpassword
        homePage.fillCredential(null, "username");
        homePage.fillCredential("jwdnd", "username");

        homePage.fillCredential(null, "password");
        homePage.fillCredential("jwndpassword", "password");

        homePage.clickCredentialUpateBtn();

        homePage.waitForPageLoad();
        homePage.openCredentialTab();
        homePage.waitForElement("credentialTable");
        //verify the changes that it was displayed
        Assertions.assertEquals("jwdnd",driver.findElements(By.className("credential-username-td")).get(0).getText());
    }

    /**
     *  TEST 3: Write Tests for Credential Creation, Viewing, Editing, and Deletion.
     *
     * A test that deletes an existing set of credentials
     * and verifies that the credentials are no longer displayed
     */
    @Test
    public void deleteCredential(){
        //create two credentials
        createCredentials();
        //delete the created credentials
        HomePage homePage= new HomePage(driver, port);

        homePage.deleteCredentialBtn(0);
        homePage.waitForPageLoad();
        homePage.openCredentialTab();

        //assert that the credential was deleted
        Assertions.assertEquals(1, driver.findElements(By.className("credential-username-td")).size());

    }


    public void signupAndLogin(){
        //Create Account
        SignupPage signupPage  = new SignupPage(driver, port);
        signupPage.visitPage();
        signupPage.doMockSignUp("Alex", "Donald", "alexd","donAlex");

        //Sign In User
        SigninPage signinPage= new SigninPage(driver, port);
        signinPage.visitPage();
        signinPage.doLogIn("alexd","donAlex");
    }






}
