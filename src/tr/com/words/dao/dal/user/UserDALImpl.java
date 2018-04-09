package tr.com.words.dao.dal.user;

import tr.com.words.base.dao.BaseDALImpl;
import tr.com.words.base.enums.EYesNo;
import tr.com.words.common.WordsConstants;
import tr.com.words.dao.dal.books.BooksDALImpl;
import tr.com.words.dao.dal.menu.MenuDALImpl;
import tr.com.words.dao.definitions.user.IUserDAO;
import tr.com.words.dao.entity.user.User;

import java.io.*;
import java.util.*;

/**
 * Created by AKKOYUNLU_YUNUS on 01.03.2018.
 */
public class UserDALImpl extends BaseDALImpl implements IUserDAO {

    Scanner scanner = new Scanner(System.in);
    Scanner scannerYesNo = new Scanner(System.in);

    public static void createUsersFile() {
        try {
            PrintWriter printWriter = new PrintWriter(BooksDALImpl.formatfileName(WordsConstants.USER_FILE_NAME), "UTF-8");
            printWriter.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public HashMap readUserFileData() {
        File isFileExist = new File(BooksDALImpl.formatfileName(WordsConstants.USER_FILE_NAME));
        if (!isFileExist.exists() && !isFileExist.isDirectory()) {
            createUsersFile();
        }

        BufferedReader br = null;
        String strLine = "";
        Map<String, String> userDataMap = new HashMap<>();
        try {

            br = new BufferedReader(new FileReader(BooksDALImpl.formatfileName(WordsConstants.USER_FILE_NAME)));
            while ((strLine = br.readLine()) != null) {
                String[] splittedUserData = strLine.split("-");

                String keyUserName = splittedUserData[0].trim();
                String valueUserPassword = splittedUserData[1].trim();

                userDataMap.put(keyUserName, valueUserPassword);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (HashMap)userDataMap;

    }

    @Override
    public void getUserDataForSave() throws IOException {

        User user = new User();

        /*System.out.print("\nPlease Type Your Name : ");
        String name = scanner.nextLine().trim();
        user.setName(name);

        System.out.print("\nPlease Type Your Surname : ");
        String surName = scanner.nextLine().trim();
        user.setSurname(surName);*/

        System.out.print("\nDo You Want to Create New Account..! [ Y / N ] : ");
        String wantToAddNewUser = scannerYesNo.next().trim().toUpperCase();
        if (!wantToAddNewUser.equals(EYesNo.NO.getValue())) {


            System.out.print("\nPlease Type a User Name : ");
            String userName = scanner.nextLine().trim();
            user.setUserName(userName);

            System.out.print("\nPlease Type a Password : ");
            String password = scanner.nextLine().trim();
            user.setPassword(password);

        /*System.out.print("\nPlease Type Your eMail : ");
        String eMail = scanner.nextLine().trim();
        user.seteMail(eMail);

        System.out.print("\nPlease Type Your Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();
        user.setPhoneNumber(phoneNumber);

        System.out.print("\nPlease Type Your Adress : ");
        String adress = scanner.nextLine().trim();
        user.setAdress(adress);*/

            saveUser(user);
        }

        MenuDALImpl menuDALImpl = new MenuDALImpl();
        menuDALImpl.prepareLoginMenu();
    }

    @Override
    public void saveUser(User user) throws IOException {

        File fileExist = new File(BooksDALImpl.formatfileName(WordsConstants.USER_FILE_NAME));
        if (!fileExist.exists() && !fileExist.isDirectory()) {
            createUsersFile();
        }

        String userName = user.getUserName().trim();
        if (!isUserNameExist(userName)) {
            try {
                Writer output = new BufferedWriter(new FileWriter(BooksDALImpl.formatfileName(WordsConstants.USER_FILE_NAME), true));
                output.append(addUserClassFieldWithComma(user));
                output.close();

                System.out.println("\n!.. " + user.getUserName() + " Saved Succesfully..!");

                MenuDALImpl menuDALImpl = new MenuDALImpl();
                menuDALImpl.prepareLoginMenu();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }

        } else {
            System.out.println("\nUser Name Which You Picked is Already Exist. Please Pick Unused User Name..!");
            getUserDataForSave();
        }

    }

    public static String addUserClassFieldWithComma(User user) {
        return user.getUserName() + " - " + user.getPassword() + "\n";
    }

    public static boolean isUserNameExist(String userName) {

        UserDALImpl userDALImpl = new UserDALImpl();
        HashMap<String, String> userDataFileMap = userDALImpl.readUserFileData();

        return userDataFileMap.containsKey(userName.trim());
    }

    @Override
    public void getUserDataForLogin() throws IOException {

        User user = new User();

        System.out.print("\nPlease Type Your User Name : ");
        String userName = scanner.nextLine().trim();
        user.setUserName(userName);

        System.out.print("\nPlease Type Your Password : ");
        String password = scanner.nextLine().trim();
        user.setPassword(password);

        isUserRegistered(user);
    }

    public boolean isUserRegistered(User user) throws IOException {

        if (!isUserNameExist(user.getUserName().trim())) {
            System.out.println("You Don' t Have Account. Please Sign In..!");
            getUserDataForSave();
        }

        UserDALImpl userDALImpl = new UserDALImpl();
        HashMap userDataMap = userDALImpl.readUserFileData();

        if (user.getPassword().equals(userDataMap.get(user.getUserName()))) {
            System.out.println("\nWellcome " + user.getUserName());
            MenuDALImpl menuDALImpl = new MenuDALImpl();
            menuDALImpl.prepareChoiceProgramMenu();
        }

        return false;
    }

    public boolean isAnyActiveUser(User user) { // If is there any active user in the system, it won't allow login again. Maybe one day.

        return false;
    }

    public static void exitApp() {
        System.exit(0);
    }
}
