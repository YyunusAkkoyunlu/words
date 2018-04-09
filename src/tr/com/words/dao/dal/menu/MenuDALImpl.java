package tr.com.words.dao.dal.menu;

import tr.com.words.common.WordsConstants;
import tr.com.words.dao.dal.books.BooksDALImpl;
import tr.com.words.dao.dal.user.UserDALImpl;
import tr.com.words.dao.dal.word.WordDALImpl;
import tr.com.words.dao.definitions.menu.IMenuDAO;
import tr.com.words.dao.entity.word.Word;
import tr.com.words.utility.DataUtility;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public class MenuDALImpl implements IMenuDAO{

    UserDALImpl userDALImpl = new UserDALImpl();
    BooksDALImpl booksDALImpl = new BooksDALImpl();

    Scanner scanner = new Scanner(System.in);
    Scanner scannerForDeleteWord = new Scanner(System.in);

    @Override
    public void prepareLoginMenu() throws IOException {
        System.out.println("\n");
        System.out.println("---- !..WORD LOGIN MENU..! ----");
        System.out.println();
        System.out.println("1.) Sign Up..!");
        System.out.println("2.) Log In..!");
        System.out.println("3.) Info..!");
        System.out.println("4.) Exit..!");
        System.out.println("");
        System.out.println("---- !..WORD  LOGIN MENU..! ----");
        System.out.println("");
        System.out.print("Make Choise : ");

        makeChoiceFromLoginMenu();

    }

    @Override
    public void makeChoiceFromLoginMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        MenuDALImpl menuDALImpl = new MenuDALImpl();

        String userChoice = scanner.next();
        if(!userChoice.equals("1") && !userChoice.equals("2") && !userChoice.equals("3") && !userChoice.equals("4")) {
            System.out.println("\n*** Wrong choice. Please make choice.. ! ***");
            menuDALImpl.prepareLoginMenu();
        }

        if(userChoice.equals("1")) {
            userDALImpl.getUserDataForSave();
        }
        if(userChoice.equals("2")) {
            userDALImpl.getUserDataForLogin();
        }
        if(userChoice.equals("3")) {
            System.out.println("This program was made to memorize the word..!");
            menuDALImpl.prepareLoginMenu();
        }
        if(userChoice.equals("4")) {
            UserDALImpl.exitApp();
        }

    }

    @Override
    public void prepareChoiceProgramMenu() throws IOException {
        System.out.println("\n");
        System.out.println("---- !..WORD CHOICE PROGRAM MENU..! ----");
        System.out.println();
        System.out.println("1.) From Books..!");
        System.out.println("2.) Course..!");
        System.out.println("3.) General..!");
        System.out.println("4.) Log Out..!");
        System.out.println("");
        System.out.println("---- !..WORD CHOICE PROGRAM MENU..! ----");
        System.out.println("");
        System.out.print("Make Choise : ");

        makeChoiceFromChoiceProgramMenu();
    }

    @Override
    public void makeChoiceFromChoiceProgramMenu() throws IOException {
        MenuDALImpl menuDALImpl = new MenuDALImpl();
        WordDALImpl wordDALImpl = new WordDALImpl();

        String userChoice = scanner.next();
        if(!userChoice.equals("1") && !userChoice.equals("2") && !userChoice.equals("3") && !userChoice.equals("4")) {
            System.out.println("\n*** Wrong choice. Please make choice.. ! ***");
            menuDALImpl.prepareLoginMenu();
        }

        //bookChoiceBlock:
        if (userChoice.equals("1")) {

            List<String > listOfBooks = booksDALImpl.listOfBooks(WordsConstants.PROJECT_PATH);
            if (listOfBooks.size() > 0) {
                prepareChoiceBooksDetailMenu(listOfBooks);

                Scanner scannerBookName = new Scanner(System.in);
                String userAnswerBookName = scannerBookName.nextLine().trim();
                if (!booksDALImpl.isBookNameTrue(userAnswerBookName, listOfBooks)) {
                    System.out.println("The book name of your write -> " + userAnswerBookName + " <- is not exist. Please check books name again and write coorect book name..!");
                    //break bookChoiceBlock;
                    prepareChoiceProgramMenu();
                }

                //wordDALImpl.startAskQuestions(booksDALImpl.formatfileName(userAnswerBookName));
                prepareMainMenuForBooks(userAnswerBookName);
            } else {
                System.out.println("There is no book you add. Please add a book for adding words..!");
                prepareMainMenuForBooks(null);
            }

        }
        if (userChoice.equals("2")) {
            prepareMainMenu(WordsConstants.COURSE_WORD_FILE_NAME);
        }
        if (userChoice.equals("3")) {
            prepareMainMenu(WordsConstants.GENERAL_WORD_FILE_NAME);
        }
        if (userChoice.equals("4")) {
            //Do Something
            prepareLoginMenu();
        }

    }

    @Override
    public void prepareChoiceBooksDetailMenu(List listOfBooks) throws IOException {
        System.out.println("\n");
        System.out.println("---- !..BOOK LIST..! ----");
        System.out.println();
        for (int i = 0; i < listOfBooks.size(); i ++) {
            System.out.println(i + 1 + ".) " + listOfBooks.get(i));
        }
        System.out.println("");
        System.out.println("---- !..WORD  MAIN MENU..! ----");
        System.out.println("");
        System.out.print("Write book name what you want to learn words : ");


        /*WordDALImpl wordDALImpl = new WordDALImpl();
        wordDALImpl.startAskQuestions(userAnswerBookName + WordsConstants.WORD_FILE_EXTENSION);*/

    }

    @Override
    public void prepareMainMenu(String fileName) throws IOException {
        System.out.println("\n");
        String bookName = DataUtility.isNullOrEmpty(fileName) ? "There is no book. Please add a book." : fileName;
        System.out.println("---- !..WORD MAIN MENU [ " + bookName.substring(0, 1).toUpperCase() + bookName.substring(1, bookName.length()) + " ] ..! ----");
        System.out.println();
        System.out.println("1.) Add New Word..!");
        System.out.println("2.) Delete Exist Word..!");
        System.out.println("3.) Get Practise..!");
        System.out.println("4.) Learn Word Means..!");
        System.out.println("5.) Back to Top Menu..!");
        System.out.println("7.) Exit..!");
        System.out.println("");
        System.out.println("---- !..WORD  MAIN MENU..! ----");
        System.out.println("");
        System.out.print("Make Choise : ");

        makeChoiceFromMainMenu(fileName);
    }

    @Override
    public void makeChoiceFromMainMenu(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        MenuDALImpl menuDALImpl = new MenuDALImpl();
        WordDALImpl wordDALImpl = new WordDALImpl();

        String userChoice = scanner.next();
        if(!userChoice.equals("1")
                && !userChoice.equals("2")
                && !userChoice.equals("3")
                && !userChoice.equals("4")
                && !userChoice.equals("5")
                && !userChoice.equals("6")
                && !userChoice.equals("7")) {
            System.out.println("\n*** Wrong choice. Please make choice.. ! ***");
            menuDALImpl.prepareLoginMenu();
        }

        if(userChoice.equals("1")) {
            wordDALImpl.addNewWord(fileName);
        }
        if(userChoice.equals("2")) {
            System.out.println("This menu under the maintenance. Please try again later..!");
            prepareMainMenu(fileName);
        }
        if(userChoice.equals("3")) {
            wordDALImpl.startAskQuestions(fileName);
        }
        if(userChoice.equals("4")) {
            wordDALImpl.learnWordMeans(fileName);
        }
        if(userChoice.equals("5")) {
            prepareChoiceProgramMenu();
        }
        if(userChoice.equals("6")) {
        UserDALImpl.exitApp();
        }


    }

    @Override
    public void prepareMainMenuForBooks(String fileName) throws IOException {
        System.out.println("\n");

        String bookName = DataUtility.isNullOrEmpty(fileName) ? "There is no book. Please add a book." : fileName;
        System.out.println("---- !..WORD MAIN MENU [ " + bookName + " ] ..! ----");
        System.out.println();
        System.out.println("1.) Add New Book..!");
        System.out.println("2.) Add New Word..!");
        System.out.println("3.) Delete Exist Word..!");
        System.out.println("4.) Get Practise..!");
        System.out.println("5.) Learn Word Means..!");
        System.out.println("6.) Back to Top Menu..!");
        System.out.println("7.) Exit..!");
        System.out.println("");
        System.out.println("---- !..WORD  MAIN MENU..! ----");
        System.out.println("");
        System.out.print("Make Choise : ");

        makeChoiceFromMainMenuForBooks(fileName);
    }

    @Override
    public void makeChoiceFromMainMenuForBooks(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        MenuDALImpl menuDALImpl = new MenuDALImpl();
        WordDALImpl wordDALImpl = new WordDALImpl();
        BooksDALImpl booksDALImpl = new BooksDALImpl();

        String userChoice = scanner.next();
        if(!userChoice.equals("1")
                && !userChoice.equals("2")
                && !userChoice.equals("3")
                && !userChoice.equals("4")
                && !userChoice.equals("5")
                && !userChoice.equals("6")
                && !userChoice.equals("7")
                && !userChoice.equals("8")) {
            System.out.println("\n*** Wrong choice. Please make choice.. ! ***");
            menuDALImpl.prepareLoginMenu();
        }

        if(userChoice.equals("1")) {
            booksDALImpl.createBookFile();
        }
        if(userChoice.equals("2")) {
            if (DataUtility.isNullOrEmpty(fileName)) {
                System.out.println("\nThere is no book. Please add a book..!");
                booksDALImpl.createBookFile();
            }
            wordDALImpl.addNewWord(fileName);
        }
        if(userChoice.equals("3")) {
            System.out.println("This menu under the maintenance. Please try again later..!");
            prepareMainMenuForBooks(fileName);
        }
        if(userChoice.equals("4")) {
            if (DataUtility.isNullOrEmpty(fileName)) {
                System.out.println("\nThere is no book. Please add a book..!");
                booksDALImpl.createBookFile();
            }
            wordDALImpl.startAskQuestions(fileName);
        }
        if(userChoice.equals("5")) {
            if (DataUtility.isNullOrEmpty(fileName)) {
                System.out.println("\nThere is no book. Please add a book..!");
                booksDALImpl.createBookFile();
            }
            wordDALImpl.learnWordMeans(fileName);
        }
        if(userChoice.equals("6")) {
            prepareChoiceProgramMenu();
        }
        if(userChoice.equals("7")) {
            UserDALImpl.exitApp();
        }

    }

}
