package org.yoqu.cms.core.config;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.admin.config.InjectManager;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;

/**
 * @author yoqu
 * @date 2016/5/11 0011
 * @description
 */
public class FinalCMS extends Controller {

    /**
     * 获取后台登录用户
     *
     * @return
     */
    public User getUser() {
        return getSessionAttr(Constant.ONLINE_USER);
    }

    /**
     * 设置网站当前页面的标题.
     *
     * @param siteTitle
     */
    public void setPageTitle(String siteTitle) {
        setAttr("site_title", siteTitle);
    }

    /**
     * 渲染引擎为页面404找不到..
     */
    public void renderNotFound() {
        render(Constant.RENDER_ACCESS_NOT_FOUND);
    }

    public void renderJSONError() {
        renderText("{result:'json error'}");
    }

    public void renderJSONSuccess() {
        try {
            renderJson(JSONUtil.writeSuccess().toString());
        } catch (JSONException e) {
            renderJSONError();
        }
    }

    public void renderJSONObject(String resultStatus, Object object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("result", resultStatus);
        JSONObject data=new JSONObject( JsonKit.toJson(object));
        result.put("data",data);
        renderJson(result.toString());
    }

    public void renderJSONFail(String information) throws JSONException {
        renderJson(JSONUtil.writeFailInformation(information).toString());
    }


    public void injectCommonVariable() {
        InjectManager.getInstance().injectCommonVariable(this);
    }

}
