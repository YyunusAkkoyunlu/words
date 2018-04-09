package tr.com.words.dao.definitions.books;

import java.io.IOException;
import java.util.List;

/**
 * Created by AKKOYUNLU_YUNUS on 06.03.2018.
 */
public interface IBooksDAO {

    public void createBookFile() throws IOException;

    public List<String> listOfBooks(String filePath);

    public boolean isBookNameTrue(String inputBookName, List<String> listOfBooks);

}
