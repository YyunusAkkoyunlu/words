package tr.com.words.dao.definitions.menu;

import java.io.IOException;
import java.util.List;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public interface IMenuDAO {

    public void prepareLoginMenu() throws IOException;

    public void makeChoiceFromLoginMenu() throws IOException;

    public void prepareChoiceProgramMenu() throws IOException;

    public void makeChoiceFromChoiceProgramMenu() throws IOException;

    public void prepareChoiceBooksDetailMenu(List listOfBooks) throws IOException;

    public void prepareMainMenu(String fileName) throws IOException;

    public void makeChoiceFromMainMenu(String fileName) throws IOException;

    public void prepareMainMenuForBooks(String fileName) throws IOException;

    public void makeChoiceFromMainMenuForBooks(String fileName) throws IOException;

}
