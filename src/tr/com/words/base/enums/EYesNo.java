package tr.com.words.base.enums;

import tr.com.words.base.IEnumParamType;

/**
 * Created by AKKOYUNLU_YUNUS on 02.03.2018.
 */
public enum EYesNo implements IEnumParamType{

    YES("Y", "YES"),
    NO("N", "NO");

    private String value;
    private String text;

    private EYesNo(String value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }

}
