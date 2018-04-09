package tr.com.words.dao.definitions.user;

import tr.com.words.dao.entity.user.User;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public interface IUserDAO {

    public HashMap readUserFileData();

    public void getUserDataForSave() throws IOException;

    public void saveUser(User user) throws IOException;

    public void getUserDataForLogin() throws IOException;

    public boolean isUserRegistered(User user) throws IOException;

    public boolean isAnyActiveUser(User user);

}
