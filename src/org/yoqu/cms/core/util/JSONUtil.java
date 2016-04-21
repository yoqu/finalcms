package org.yoqu.cms.core.util;

import com.jfinal.core.Controller;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.config.Constant;

/**
 * Created by yoqu on 2016/4/18 0018.
 */
public class JSONUtil {

    public static JSONObject writeSuccessInformation(String info) throws JSONException {
        return new JSONObject().put(Constant.INFO, info).put(Constant.RESULT, Constant.SUCCESS);
    }

    public static String writeSuccess() throws JSONException {
        return new JSONObject().put(Constant.RESULT, Constant.SUCCESS).toString();
    }

    public static JSONObject writeFailInformation(String info) throws JSONException {
        return new JSONObject().put(Constant.INFO, info).put(Constant.RESULT, Constant.FAIL);
    }

    public static String writeFail() throws JSONException {
        return new JSONObject().put(Constant.RESULT, Constant.FAIL).toString();
    }
    public static void writeJSONError(Controller c){
        c.renderJson("json error");
    }
}
