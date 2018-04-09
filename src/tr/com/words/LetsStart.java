package tr.com.words;

import tr.com.words.common.WordsConstants;
import tr.com.words.dao.dal.menu.MenuDALImpl;
import tr.com.words.dao.dal.word.WordDALImpl;

import java.io.IOException;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public class LetsStart {

    public static void main(String args[]) throws IOException {

        MenuDALImpl menuDALImpl = new MenuDALImpl();
        menuDALImpl.prepareLoginMenu();

    }
}
