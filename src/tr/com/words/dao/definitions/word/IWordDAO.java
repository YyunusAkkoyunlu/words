package tr.com.words.dao.definitions.word;

import tr.com.words.dao.entity.word.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AKKOYUNLU_YUNUS on 05.03.2018.
 */
public interface IWordDAO {

    public void createWordFile(String fileName);

    public HashMap readWordFile(String fileName) throws IOException;

    //public void addNewWord(String fileName) throws IOException;

    public void addNewWord(String fileName) throws IOException; // , String inputWordFromLearnMenu

    public void removeWord(Word word, String fileName, HashMap<String, String> mapReadedWords);

    public HashMap saveUpdate(Word word, String fileName) throws IOException;

    public void startAskQuestions(String fileName) throws IOException;

    public ArrayList listWordNames(Map mapWords);

    public boolean isWordExistInFile(HashMap<String, String> mapWordsForControl, String wordName);

    public boolean isWordMeanExistInFile(HashMap<String, String> mapWordsForControl, String wordName, String wordMean);

    public String addMeanToMeansWithComma(String wordMeans, String bufferMeans, String wordMeanToAdd);

    public ArrayList listRandomNumber(HashMap mapWords);

    public boolean isAnswerTrue(String[] wordMeans, String userAnswer);

    public boolean isMeanExistBufferWordMeans(String bufferWordMeans, String inputWordMean);

    public void learnWordMeans(String fileName) throws IOException;
}
