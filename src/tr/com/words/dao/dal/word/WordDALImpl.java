package tr.com.words.dao.dal.word;

import tr.com.words.base.enums.EYesNo;
import tr.com.words.common.WordsConstants;
import tr.com.words.dao.dal.books.BooksDALImpl;
import tr.com.words.dao.dal.menu.MenuDALImpl;
import tr.com.words.dao.definitions.word.IWordDAO;
import tr.com.words.dao.entity.word.Word;
import tr.com.words.utility.DataUtility;

import java.io.*;
import java.util.*;

/**
 * Created by AKKOYUNLU_YUNUS on 05.03.2018.
 */
public class WordDALImpl implements IWordDAO {

    Scanner scanner = new Scanner(System.in);
    Scanner scannerYesNoForWord = new Scanner(System.in);
    Scanner scannerYesNoForMean = new Scanner(System.in);
    Scanner scannerYesNoForLearnMeans = new Scanner(System.in);

    @Override
    public void createWordFile(String fileName) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName + WordsConstants.WORD_FILE_EXTENSION, "UTF-8");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

    @Override
    public HashMap readWordFile(String fileName) throws IOException {

        if (!DataUtility.isNullOrEmpty(fileName)) {
            BufferedReader br = null;
            String strLine = "";
            Map<String, String> readedWordFileData = new HashMap<>();
            try {
                File fileExist = new File(BooksDALImpl.formatfileName(fileName));
                if (!fileExist.exists() && !fileExist.isDirectory()) {
                    createWordFile(fileName);
                }

                fileName += WordsConstants.WORD_FILE_EXTENSION;
                br = new BufferedReader(new FileReader(fileName));
                while ((strLine = br.readLine()) != null) {
                    String[] splittedWordData = strLine.split("-");

                    String keyWordName = splittedWordData[0].trim();
                    String valueWordMeans = splittedWordData[1].trim();

                    readedWordFileData.put(keyWordName, valueWordMeans);
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                System.out.println("The file which you write couldn' t find. Please write file book name on the list.");
            } finally {
                br.close();
            }

            return (HashMap) readedWordFileData;
        } else {
            System.out.println("Invalid file name. Please write correct file name");
            return null;
        }


    }

    /*@Override
    public void addNewWord(String fileName) throws IOException {
        addNewWord(fileName, null);
    }*/

    @Override
    public void addNewWord(String fileName) throws IOException { // String inputWordFromLearnMenu. Comment was added on learnWordMeans method. I' ll check.

        String inputWordMean, wordMeans;
        String bufferWordMeans = "", wordName; // You can add means repeatedly without out of the mean while loop ( bufferWordMeans ).

        System.out.print("\nDo yu want to add new word ..! [ Y / N ] : ");
        String answerWantToAdd = scannerYesNoForWord.next().trim().toUpperCase();
        while (answerWantToAdd.equals(EYesNo.YES.getValue())) {
            Word word = new Word();
            HashMap<String, String> mapWordsForControl = readWordFile(fileName);

            System.out.print("\nPlease Write a Word to Want to Add : ");
            wordName = scanner.nextLine().trim();
            word.setName(wordName);
            if (isWordExistInFile(mapWordsForControl, wordName)) {
                System.out.print("\nThe word which you want to add is already exist. Do you want to add that word' s another means ? That word' s means is / are : " + mapWordsForControl.get(wordName) + ".");
                System.out.print(" Do you want to add new mean for '" + wordName + "' 's ? [ Y / N ] : ");
                String answerWantToAddMean = scannerYesNoForMean.nextLine().trim().toUpperCase();
                while (answerWantToAddMean.equals(EYesNo.YES.getValue())) { // Mean while loop.
                    System.out.print("Please Type Word Mean : ");
                    inputWordMean = scanner.nextLine().trim();
                    if (DataUtility.isNullOrEmpty(bufferWordMeans) && isWordMeanExistInFile(mapWordsForControl, wordName, inputWordMean)) { // if the word already exist and you want to add new first mean.
                        System.out.println("Mean which you write " + inputWordMean + " is already exist. Check the word' s mean to add.");
                    } else if(!DataUtility.isNullOrEmpty(bufferWordMeans) && isMeanExistBufferWordMeans(bufferWordMeans, inputWordMean)) { // if the mean you want to add, is already added.
                        System.out.println("Mean which you write '" + inputWordMean + "' is exist in the means you write. Please check the means. The mean' s you write is / are : " + bufferWordMeans);
                    } else {
                        wordMeans = mapWordsForControl.get(wordName);
                        wordMeans = addMeanToMeansWithComma(mapWordsForControl.get(wordName), bufferWordMeans, inputWordMean);
                        bufferWordMeans = wordMeans;
                    }
                    System.out.print("\nDo you want to add new mean for " + wordName + "'s ? [ Y / N ] : ");
                    answerWantToAddMean = scannerYesNoForMean.nextLine().trim().toUpperCase();
                }
                word.setMeans(bufferWordMeans);
                bufferWordMeans = "";
            } else {
                System.out.print("Write mean of word which you want to add : ");
                wordMeans = scanner.nextLine().trim();
                if (!DataUtility.isNullOrEmpty(wordMeans)) {
                    word.setMeans(wordMeans);
                } else {
                    System.out.println("The mean which you write is empty. Please check your write..!");
                }
            }

            if (!DataUtility.isNullOrEmpty(word.getMeans())) {
                removeWord(word, BooksDALImpl.formatfileName(fileName), mapWordsForControl);
                saveUpdate(word, BooksDALImpl.formatfileName(fileName));
            }

            System.out.print("\nDo yu want to add new word ..! [ Y / N ] : ");
            answerWantToAdd = scannerYesNoForWord.next().trim().toUpperCase();
        }

        MenuDALImpl menuDALImpl = new MenuDALImpl();

        if (fileName.trim().equals(WordsConstants.COURSE_WORD_FILE_NAME) || fileName.trim().equals(WordsConstants.GENERAL_WORD_FILE_NAME)) {
            menuDALImpl.prepareMainMenu(fileName);
        } else {
            menuDALImpl.prepareMainMenuForBooks(fileName);
        }

    }

    @Override
    public void removeWord(Word word, String fileName, HashMap<String, String> mapReadedWords) {
        try {
            File currentFile = new File(fileName);
            if (!currentFile.isFile()) {
                System.out.println(fileName + " File Not Exist..!");
            }

            File tempFile = new File(currentFile.getAbsolutePath() + ".tmp");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            PrintWriter printWriter = new PrintWriter(new FileWriter(tempFile));

            String lineToRemove = word.getName() + " - " + mapReadedWords.get(word.getName());
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    printWriter.println(line);
                    printWriter.flush();
                }
            }

            bufferedReader.close();
            printWriter.close();

            if (!currentFile.delete()) {
                System.out.println("Could not delete -> " + fileName + " <- file");
                return;
            }
            if (!tempFile.renameTo(currentFile)) {
                System.out.println("Could not rename -> " + fileName + " <- file");
            }

        } catch (FileNotFoundException fiEx) {
            fiEx.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap saveUpdate(Word word, String fileName) throws IOException {
        String wordToSaveFile = word.getName() + " - " + word.getMeans() + "\n";
        Writer output = null;

        try {
            output = new BufferedWriter(new FileWriter(fileName, true));
            output.append(wordToSaveFile);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            System.out.println("*** Error : WordDALImpl_saveUpdate ***");
        } finally {
            output.close();
        }

        return null;
    }

    @Override
    public void startAskQuestions(String fileName) throws IOException {

        MenuDALImpl menuDALImpl = new MenuDALImpl();

        ArrayList<String> arrayWrongAnsweredWords = new ArrayList<>();

        HashMap mapWords = readWordFile(fileName);
        if (mapWords.size() == 0) {
            if (fileName.trim().equals(WordsConstants.GENERAL_WORD_FILE_NAME) || fileName.trim().equals(WordsConstants.COURSE_WORD_FILE_NAME)) {
                System.out.println("The file is emty. Please add words for practise.");
                menuDALImpl.prepareMainMenu(fileName);
            } else {
                System.out.println("The file which '" + fileName + "' is empty. Please add words for practise.");
                menuDALImpl.prepareMainMenuForBooks(fileName);
            }
        }

        System.out.println("You can write -1 for answer when you want to go program menu..!");

        ArrayList listWordNames = listWordNames(mapWords);
        String userAnswer;

        ArrayList listRandomNumbersByFileName = listRandomNumber(mapWords);
        for (int i = 0; i < listRandomNumbersByFileName.size(); i++) {

            String askedWord = (String) listWordNames.get((Integer) listRandomNumbersByFileName.get(i));
            //String askedWord = listWordNames.get(listRandomNumbersByFileName.get(i));
            String[] askedWordMeans = (String[]) mapWords.get(askedWord).toString().split(",");

            System.out.print("\n" + askedWord + " : ");
            userAnswer = scanner.nextLine().trim();
            if (userAnswer.trim().equals(WordsConstants.EXIT_THE_EXERCISE)) {
                if (fileName.trim().equals(WordsConstants.GENERAL_WORD_FILE_NAME) || fileName.trim().equals(WordsConstants.COURSE_WORD_FILE_NAME)) {
                    menuDALImpl.prepareMainMenu(fileName);
                } else {
                    menuDALImpl.prepareMainMenuForBooks(fileName);
                }
            }
            if (isAnswerTrue(askedWordMeans, userAnswer)) {
                System.out.println("Your Answer is correct ..!\n");
            } else {
                System.out.println("Wrong answer ..!");
                System.out.println("The word " + askedWord + " 's means : " + mapWords.get(askedWord));
                arrayWrongAnsweredWords.add(askedWord);
            }

            if (i == listRandomNumbersByFileName.size() - 1) { // if the listRandomNumbersByFileName ends, list reloads again
                if (arrayWrongAnsweredWords.size() != 0) {
                    System.out.println("\nThe words which you answered wrong are : \n");
                    for (String word : arrayWrongAnsweredWords) {
                        System.out.println("    * " + word);
                    }

                    System.out.println("\n*** The list have reloaded..! ***");
                }

                i = -1; // when i goes for loop' s start, it' s variable value will start at 0. Therefore i started at -1. So we didn' t lose any elements of list.
                listRandomNumbersByFileName = listRandomNumber(mapWords); // Reload the list.
            }


        }
    }

    @Override
    public ArrayList listWordNames(Map mapWords) {

        ArrayList listWords = new ArrayList();
        for (Object key : mapWords.keySet()) {
            listWords.add((String) key);
        }

        return listWords;
    }

    @Override
    public boolean isWordExistInFile(HashMap<String, String> mapWordsForControl, String wordName) {
        return mapWordsForControl.containsKey(wordName);
    }

    @Override
    public boolean isWordMeanExistInFile(HashMap<String, String> mapWordsForControl, String wordName, String wordMean) {

        String[] strOfWordMean = mapWordsForControl.get(wordName).trim().split(",");
        for (String mean : strOfWordMean) {
            if (mean.trim().equals(wordMean.trim())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String addMeanToMeansWithComma(String wordMeans, String bufferMeans, String wordMeanToAdd) {
        if (!DataUtility.isNullOrEmpty(wordMeanToAdd) && DataUtility.isNullOrEmpty(bufferMeans)) {
            wordMeanToAdd = ", " + wordMeanToAdd;
            wordMeans += wordMeanToAdd;

            return wordMeans;
        } else if(!DataUtility.isNullOrEmpty(bufferMeans)) {
            bufferMeans += ", " + wordMeanToAdd;
            return bufferMeans;
        } else{
            System.out.println("Word mean couldn' t add..!");
            return null;
        }
    }

    @Override
    public ArrayList<Integer> listRandomNumber(HashMap mapWords) {

        ArrayList listNumber = new ArrayList();
        for (int i = 0; i < mapWords.size(); i++) {
            listNumber.add(i);
        }

        Collections.shuffle(listNumber);
        return listNumber;
    }

    @Override
    public boolean isAnswerTrue(String[] askedWordMeans, String userAnswer) {
        for (String str : askedWordMeans) {
            if (!DataUtility.isNullOrEmpty(str) && userAnswer.equals(str.trim())) {
                //System.out.println("Your Answer is correct ..!\n");
                return true;
            }
        }
        //System.out.println("Wrong answer ..!\n");
        return false;
    }

    @Override
    public boolean isMeanExistBufferWordMeans(String bufferWordMeans, String inputWordMean) {
        String[] splittedMeans = bufferWordMeans.trim().split(",");
        for (String mean : splittedMeans) {
            if (inputWordMean.trim().equals(mean.trim())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void learnWordMeans(String fileName) throws IOException {

        Map<String, String> mapReadedWords = readWordFile(fileName);
        MenuDALImpl menuDALImpl = new MenuDALImpl();
        String wantToLearnWord;

        if (mapReadedWords.size() == 0) {
            if (fileName.trim().equals(WordsConstants.GENERAL_WORD_FILE_NAME) || fileName.trim().equals(WordsConstants.COURSE_WORD_FILE_NAME)) {
                System.out.println("The file is emty. Please add words for practise.");
                menuDALImpl.prepareMainMenu(fileName);
            } else {
                System.out.println("The file which '" + fileName + "' is empty. Please add words for practise.");
                menuDALImpl.prepareMainMenuForBooks(fileName);
            }
        }

        System.out.print("\nDo you want to learn word means ? [ Y / N ] : ");

        String userAnswerYesNo = scannerYesNoForMean.nextLine().toUpperCase();
        while (userAnswerYesNo.trim().equals(EYesNo.YES.getValue())) {
            System.out.print("\nWrite a word which you want to learn means : ");

            wantToLearnWord = scanner.nextLine();
            if (!DataUtility.isNullOrEmpty(wantToLearnWord) && mapReadedWords.containsKey(wantToLearnWord)) {
                System.out.println(wantToLearnWord + " 's means : " + mapReadedWords.get(wantToLearnWord));
            } else {
                System.out.println("'" + wantToLearnWord + "'" + " word is not exist.");
                menuDALImpl.prepareMainMenu(fileName);
                //Later, the word if does not exist in the file, the program will ask wheter ask to add it.
            }

            System.out.print("\nDo you want to learn word means ? [ Y / N ] : ");
            userAnswerYesNo = scannerYesNoForMean.nextLine().toUpperCase();
        }

        if (fileName.trim().equals(WordsConstants.GENERAL_WORD_FILE_NAME) || fileName.trim().equals(WordsConstants.COURSE_WORD_FILE_NAME)) {
            menuDALImpl.prepareMainMenu(fileName);
        } else {
            menuDALImpl.prepareMainMenuForBooks(fileName);
        }
    }
}

