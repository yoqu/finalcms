package org.yoqu.cms.core.admin.modules.node;

import com.jfinal.aop.Before;
import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.admin.modules.user.UserInvoke;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalCMS;
import org.yoqu.cms.core.model.File;
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

        Node node = getModel(Node.class);
        node.setCreateDate(new Date());
        node.setUpdateDate(new Date());
        node.setUid(getUser().getId());
        node.setIsDelete(0);
        try {
            if (node.save()) {
                if (isParaExists("files")) {
                    String[] ids = getPara("files").split(",");
                    for (int i = 0; i < ids.length; i++) {
                        File f = File.dao.findById(ids[i]);
                        f.setFid(node.getId());
                        f.setModule("node");
                        f.save();
                    }
                }
                redirect("/admin/node");
            } else {
                renderText("save fail.");
                deleteUploadFile();
            }
        } catch (Exception ex) {
            deleteUploadFile();
            renderJSONError();
        }


    }

    /**
     * 删除上传的文件
     */
    private void deleteUploadFile() {
        if (isParaExists("files")) {
            String[] ids = getPara("files").split(",");
            for (int i = 1; i < ids.length; i++) {
                File f = File.dao.findById(ids[i]);
                if (f != null) {
                    String baseUploadPath;
                    if (PathKit.isAbsolutelyPath(JFinal.me().getConstants().getBaseUploadPath())) {
                        baseUploadPath = JFinal.me().getConstants().getBaseUploadPath();
                    } else {
                        baseUploadPath = PathKit.getWebRootPath() + java.io.File.separator + JFinal.me().getConstants().getBaseUploadPath();
                    }
                    baseUploadPath += java.io.File.separator + f.getPath();
                    java.io.File removeFile = new java.io.File(baseUploadPath);
                    if (!removeFile.delete()) {
                        //删除失败处理....
                        continue;
                    }
                }
                File.dao.deleteById(ids[i]);
            }
        }
    }

    public void doUploadFile() {
        UploadFile uploadFile = getFile("file");
        File file = new File();
        file.setPath(uploadFile.getFileName());
        file.setUploadTime(new Date());
        file.setFid(-1);
        file.save();
        JSONObject jsonfile = new JSONObject();
        try {
            renderJSONObject(Constant.SUCCESS, file);
        } catch (JSONException e) {
            renderJSONError();
        }
    }

    public void doDeleteFile() {
        if (isParaBlank("id")) {
            renderJSONError();
            return;
        }
        int id = getParaToInt("id");
        if (File.dao.deleteById(id)) {
            renderJSONSuccess();

        } else {
            renderJSONError();
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
