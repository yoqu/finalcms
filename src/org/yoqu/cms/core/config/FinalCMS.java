package org.yoqu.cms.core.config;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.admin.config.InjectManager;
import org.yoqu.cms.core.model.File;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/11 0011
 * @description 系统核心类，包含了系统的常规页面渲染等等等方法
 */
public abstract class FinalCMS extends Controller {

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
        if (object instanceof List) {
            JSONArray data = new JSONArray(JsonKit.toJson(object));
            result.put("data", data);
        } else {
            JSONObject data = new JSONObject(JsonKit.toJson(object));
            result.put("data", data);
        }
        renderJson(result.toString());
    }

    public void renderJSONFail(String information) throws JSONException {
        renderJson(JSONUtil.writeFailInformation(information).toString());
    }

    public void injectCommonVariable() {
        InjectManager.getInstance().injectCommonVariable(this);
    }

    public boolean deleteUploadFile(int id) {
        File f = File.dao.findById(id);
        if (f != null) {
            String baseUploadPath;
            if (PathKit.isAbsolutelyPath(JFinal.me().getConstants().getBaseUploadPath())) {
                baseUploadPath = JFinal.me().getConstants().getBaseUploadPath();
            } else {
                baseUploadPath = PathKit.getWebRootPath() + java.io.File.separator + JFinal.me().getConstants().getBaseUploadPath();
            }
            baseUploadPath += java.io.File.separator + f.getName();
            java.io.File removeFile = new java.io.File(baseUploadPath);
            if (!removeFile.delete()) {
                //删除失败处理....
                return false;
            }
        }
        return File.dao.deleteById(id);
    }
}
