package tr.com.words;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by AKKOYUNLU_YUNUS on 05.03.2018.
 */
public class DenemeRandom {

    public static ArrayList listOfNumbers(int limit) {

        ArrayList listNumber = new ArrayList();
        for(int i = 0; i < limit; i ++) {
            listNumber.add(i + 1);
        }

        Collections.shuffle(listNumber);
        return listNumber;
    }

    public static void main(String args[]) {
        ArrayList listRandomNumber = listOfNumbers(5);
        System.out.println(listRandomNumber + "\n");
        int j = 0;

        /*1.st for (int i = 0; i < listRandomNumber.size(); i++) {
            System.out.println("i : " + i + ", fromList : " + listRandomNumber.get(i));

            if(i == listRandomNumber.size() -1){
                if (j != 0)
                    System.out.println("i : " + i + ", fromList : " + listRandomNumber.get(i + 1));
                i = 0;
                listRandomNumber = listOfNumbers(5);
                System.out.println();
                j++;
            }
        }*/

        for (int i = 0; i < listRandomNumber.size(); i++) {
            System.out.println("i : " + i + ", fromList : " + listRandomNumber.get(i));

            if(i == listRandomNumber.size() -1){
                i = -1;
                listRandomNumber = listOfNumbers(5);
                System.out.println();
                //j++;
            }
        }
    }
}
