package org.yoqu.cms.front.modules.index;

import com.jfinal.plugin.activerecord.Page;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.admin.modules.node.NodeInvoke;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.front.modules.message.Message;
import org.yoqu.cms.front.modules.message.StackList;

import java.util.HashMap;

/**
 * @author yoqu
 * @date 2016/5/12 0012
 * @description
 */
public class IndexController extends FinalBaseController {

    @SiteTitle("首页")
    public void index() {
        int page = getPara("page") != null ? getParaToInt("page") : 1;
        Page<Node> nodeList = NodeInvoke.getInstance().findNodeByPage(page);
        StackList.push(new Message("load index page",Constant.SUCCESS));
        setAttr("nodeList", nodeList);

        render("/front/index.html");
    }

    @SiteTitle("yoqu &mdash; 一个简单的javaweb开发者")
    public void about() {
        StackList.push(new Message("load about page",Constant.SUCCESS));
        render("/front/about.html");
    }

    public void message(){
        if(StackList.isNotify()){
            Message message =StackList.pop();
            try {
                renderJSONObject(Constant.SUCCESS,message);
                
            } catch (JSONException e) {
                renderJSONError(e.getMessage());
            }
        }
        else{
            JSONObject result=new JSONObject();
            try {
                result.put("status","empty");
                result.put("result", Constant.SUCCESS);
            } catch (JSONException e) {
                renderJSONError();
                return;
            }
            renderJson(result.toString());
        }
    }
}
