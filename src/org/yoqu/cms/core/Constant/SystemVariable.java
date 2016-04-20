package org.yoqu.cms.core.Constant;

import org.yoqu.cms.core.model.Dictionary;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yoqu on 2016/4/19 0019.
 */
public class SystemVariable {
    public static HashMap<String, String> systemConstant = null;

    public static void use() {
        systemConstant = new HashMap<>();
        List<Dictionary> dictionaries = Dictionary.dao.getDictionaryList();
        for (Dictionary dictionary : dictionaries) {
            systemConstant.put(dictionary.getName(), dictionary.getValue());
        }
    }

    public static String get(String key) {
        return systemConstant.get(key);
    }
}
