package org.yoqu.cms.core.front.modules.index;

import com.jfinal.plugin.activerecord.Page;
import org.yoqu.cms.core.admin.modules.node.NodeInvoke;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.FinalCMS;
import org.yoqu.cms.core.model.Node;

/**
 * @author yoqu
 * @date 2016/5/12 0012
 * @description
 */
public class IndexController extends FinalCMS {

    @SiteTitle("首页")
    public void index() {
        int page = getPara("page") != null ? getParaToInt("page") : 1;
        Page<Node> nodeList = NodeInvoke.getInstance().findNodeByPage(page);
        setAttr("nodeList", nodeList);
        render("/front/index.html");
    }

    @SiteTitle("yoqu &mdash; 一个简单的javaweb开发者")
    public void about() {

        render("/front/about.html");
    }
}
