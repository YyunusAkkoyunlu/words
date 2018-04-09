package tr.com.words.dao.entity.word;

import tr.com.words.base.entity.BaseModel;

/**
 * Created by AKKOYUNLU_YUNUS on 05.03.2018.
 */
public class Word extends BaseModel {

    private String name;

    private String means;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }


}
