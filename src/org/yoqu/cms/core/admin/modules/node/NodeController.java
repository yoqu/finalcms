package org.yoqu.cms.core.admin.modules.node;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import org.json.JSONException;
import org.yoqu.cms.core.admin.modules.user.UserInvoke;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalCMS;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.core.model.User;
import org.yoqu.cms.core.util.JSONUtil;
import org.yoqu.cms.core.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/6 0006
 * @description
 */
public class NodeController extends FinalCMS {

    public void index() {
        if (getPara() != null) {
            if (!StringUtils.isNumbervalue(getPara())) {
                renderNotFound();
                return;
            } else {
                int id = getParaToInt();
                Node node = Node.dao.findById(id);
                if (node == null) {
                    renderNotFound();
                    return;
                }
                setAttr("node", node);
                setAttr("site_title", node.getTitle());
                render("/admin/node/edit.html");
            }
        } else {
            int page = getPara("page") != null ? getParaToInt("page") : 1;
            Page<Node> nodeList = NodeInvoke.getInstance().findNodeByPage(page);
            setAttr("nodeList", nodeList);
            setAttr("site_title", "内容管理");
            render("/admin/node/nodeList.html");
        }
    }

    @SiteTitle("创建内容")
    public void create() {
        render("/admin/node/create.html");
    }

    @Before(POST.class)
    public void doCreate() {
        UploadFile file = getFile("file");
        Node node = getModel(Node.class);
        node.setCreateDate(new Date());
        node.setUpdateDate(new Date());
        node.setUid(getUser().getId());
        node.setIsDelete(0);
        if (node.save()) {
            redirect("/admin/node");
        } else {
            renderText("save fail.");
        }

    }

    @SiteTitle("内容编辑")
    public void edit() {
        if (getPara() == null) {
            renderNotFound();
            return;
        }
        if (!StringUtils.isNumbervalue(getPara())) {
            renderNotFound();
            ;
            return;
        }
        Node node = Node.dao.findById(getParaToInt());
        if (node == null) {
            renderNotFound();
            return;
        }
        setAttr("node", node);
        render("/admin/node/edit.html");
    }

    public void doUpdate() {
        Node node = getModel(Node.class);
        if (node.update()) {
            redirect("/admin/node");
        } else {
            renderText("save fail.");
        }
    }

    public void doDelete() {
        Integer nid = getParaToInt("id");
        try {
            NodeInvoke.getInstance().softDelete(nid);
            renderJSONSuccess();
        } catch (Exception ex) {
            try {
                renderJSONFail("删除内容出错");
            } catch (JSONException e) {
                renderJSONError();
            }
        }
    }
}
