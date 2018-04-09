package tr.com.words.dao.dal.books;

import tr.com.words.base.enums.EYesNo;
import tr.com.words.common.WordsConstants;
import tr.com.words.dao.dal.menu.MenuDALImpl;
import tr.com.words.dao.dal.word.WordDALImpl;
import tr.com.words.dao.definitions.books.IBooksDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by AKKOYUNLU_YUNUS on 06.03.2018.
 */
public class BooksDALImpl implements IBooksDAO {

    Scanner userAnswerYesNo = new Scanner(System.in);
    Scanner userAnswerBookName = new Scanner(System.in);

    @Override
    public void createBookFile() throws IOException {
        System.out.print("Do you want to add new book ? [ Y / N ] : ");

        String inputUserAnswerYesNo = userAnswerYesNo.next().trim().toUpperCase();
        if (inputUserAnswerYesNo.equals(EYesNo.YES.getValue())) {
            System.out.print("Please type the name of book you want to add : ");
            String bookName = userAnswerBookName.nextLine().trim();

            WordDALImpl wordDALImpl = new WordDALImpl();
            wordDALImpl.createWordFile(bookName);

            MenuDALImpl menuDALImpl = new MenuDALImpl();
            menuDALImpl.prepareMainMenuForBooks(bookName);
        } else {
            MenuDALImpl menuDALImpl = new MenuDALImpl();
            menuDALImpl.prepareChoiceProgramMenu();
        }
    }

    @Override
    public List<String> listOfBooks(String filePath) {
        File folder = new File(WordsConstants.PROJECT_PATH);
        File[] listOfFiles = folder.listFiles();
        List listOfBooks = new ArrayList();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(WordsConstants.WORD_FILE_EXTENSION)
                    && !BooksDALImpl.unFormatfileName(listOfFiles[i].getName()).equals(WordsConstants.COURSE_WORD_FILE_NAME)
                    && !BooksDALImpl.unFormatfileName(listOfFiles[i].getName()).equals(WordsConstants.GENERAL_WORD_FILE_NAME)
                    && !BooksDALImpl.unFormatfileName(listOfFiles[i].getName()).equals(WordsConstants.USER_FILE_NAME)) {
                listOfBooks.add(listOfFiles[i].getName().substring(0, (int) (listOfFiles[i].getName().length() - WordsConstants.WORD_FILE_EXTENSION.length())));
            }
        }

        return listOfBooks;
    }

    @Override
    public boolean isBookNameTrue(String inputBookName, List<String> listOfBooks) {
        for (String bookName : listOfBooks) {
            if (bookName.trim().equals(inputBookName.trim())) {
                return true;
            }
        }

        return false;
    }

    public static String formatfileName(String fileName) {
            return fileName + WordsConstants.WORD_FILE_EXTENSION;
    }

    public static String unFormatfileName(String fileName) {
            return fileName.substring(0, fileName.length() - WordsConstants.WORD_FILE_EXTENSION.length());
    }

}
