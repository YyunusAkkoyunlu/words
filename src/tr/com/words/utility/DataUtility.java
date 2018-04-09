package tr.com.words.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * nesnelerle ilgili utility kontrol metodları bu sınıfta yer alır.
 *
 * Created by BAHAR_AYSE on 05.10.2015.
 */
public final class DataUtility {

    /**
     *
     * @param o1
     * @param o2
     * @return
     * @throws
     */
    public static final boolean isEqual(Object o1, Object o2) {
        boolean result = o1.equals(o2);
        return result;
    }

    /**
     * @param o
     * @return
     */
    /*public static final boolean isNull(Object o) {
        try {
            Assert.notNull(o);
        } catch (Exception e) {
            return true;
        }
        return false;
    }*/

    /**
     *
     * @param text
     * @return
     */
    public static final boolean isNullOrEmpty(String text){
        if(text == null || "".equals(text) || text.length() == 0 || text.equals("undefined")){
            return true;
        }
        return false;
    }

    /**
     * @param collection
     * @return
     */
    public static final boolean isNullOrEmpty(Collection<?> collection) {
        boolean result = collection == null || collection.size() == 0;
        return result;
    }

    /**
     * @param array
     * @return
     */
    public static final <T> boolean isNullOrEmpty(T[] array) {

        boolean result = array == null || array.length == 0;
        return result;
    }


    /*public static JSONObject toJsonFromStr(String str) {
        JSONParser parser = new JSONParser();

        try {
            Object e = parser.parse(str);
            if(e instanceof Map) {
                return (JSONObject)e;
            }
            return null;

        } catch (Throwable var4) {
            throw new SystemError("Can not parse json string", var4);
        }
    }*/

    public static byte[] getByteArray(InputStream is) {
        int len;
        int size = 1024;
        byte[] buf;
        try {
            if (is instanceof ByteArrayInputStream) {

                size = is.available();
                buf = new byte[size];
                len = is.read(buf, 0, size);
            } else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                buf = new byte[size];
                while ((len = is.read(buf, 0, size)) != -1)
                    bos.write(buf, 0, len);
                buf = bos.toByteArray();
            }
            return buf;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DataUtility() {

    }
}
