package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteTests {

    @LocalServerPort
    private int port;

    private static final String noteTitle = "Test Note Title";
    private static final String noteDescription = "Test Note Description";

    @Autowired
    private Helper helper;

    protected WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDelete() {
        HomePage homePage = helper.signUpAndLogin(driver, this.port);
        createNote(homePage);
        homePage.navToNotesTab();
        homePage = new HomePage(driver);
        Assertions.assertFalse(homePage.noNotes(driver));
        deleteNote(homePage);
        Assertions.assertTrue(homePage.noNotes(driver));
    }

    private void deleteNote(HomePage homePage) {
        homePage.deleteNote();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }

    @Test
    public void testCreateAndDisplay() {
        HomePage homePage = helper.signUpAndLogin(driver, this.port);
        createNote(homePage);
        homePage.navToNotesTab();

        homePage = new HomePage(driver);
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(noteTitle, note.getNoteTitle());
        Assertions.assertEquals(noteDescription, note.getNoteDescription());

        deleteNote(homePage);
        homePage.logout();
    }

    @Test
    public void testModify() {
        HomePage homePage = helper.signUpAndLogin(driver, this.port);
        createNote(homePage);
        homePage.navToNotesTab();

        homePage = new HomePage(driver);
        homePage.editNote();
        String modifiedNoteTitle = "Modified Note Title";
        homePage.modifyNoteTitle(modifiedNoteTitle);
        String modifiedNoteDescription = "Modified Note Description";
        homePage.modifyNoteDescription(modifiedNoteDescription);
        homePage.saveNoteChanges();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
        Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
    }

    private void createNote(HomePage homePage) {
        homePage.navToNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(noteTitle);
        homePage.setNoteDescription(noteDescription);
        homePage.saveNoteChanges();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToNotesTab();
        homePage.saveNoteChanges();
    }
}