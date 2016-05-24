package org.yoqu.cms.admin.modules.node;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import org.json.JSONException;
import org.json.JSONObject;
import org.yoqu.cms.core.aop.SiteTitle;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalBaseController;
import org.yoqu.cms.core.model.File;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.core.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/6 0006
 * @description
 */
public class NodeController extends FinalBaseController {

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
                    File.dao.updateFile(getPara("files"), "node", node.getId());
                }
                redirect("/admin/node");
            } else {
                renderText("save fail.");
                deleteUploadFiles();
            }
        } catch (Exception ex) {
            node.delete();
            deleteUploadFiles();
            renderJSONError();
        }
    }

    public void doUpdate() {
        Node node = getModel(Node.class);
        if (node.update()) {
            if (isParaExists("files")) {
                //需要新增文件的
                File.dao.updateFile(getPara("files"), "node", node.getId());
                //需要删除的文件.
                List<File> oldFiles = File.dao.findDeleteFile(getPara("files"), node.getId());
                if (oldFiles.size() > 0) {
                    for (File f : oldFiles) {
                        deleteUploadFile(f.getId());
                    }
                }
            } else {
                List<File> files = node.getListFile();
                for (File file : files) {
                    deleteUploadFile(file.getId());
                }
            }
            redirect("/admin/node");
        } else {
            renderText("save fail.");
        }
    }

    /**
     * 删除上传的文件
     */
    private void deleteUploadFiles() {
        if (isParaExists("files")) {
            String[] ids = getPara("files").split(",");
            for (int i = 0; i < ids.length; i++) {
                deleteUploadFile(Integer.parseInt(ids[i]));
            }
        }
    }

    /**
     * 上传文件
     */
    public void doUploadFile() {
        UploadFile uploadFile = getFile("file");
        File file = new File();
        file.setPath(uploadFile.getUploadPath());
        file.setName(uploadFile.getFileName());
        file.setSize(String.valueOf(uploadFile.getFile().length()));
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
        if (deleteUploadFile(id)) {
            renderJSONSuccess();

        } else {
            renderJSONError();
        }
    }

    public void loadFiles() {
        if (isParaExists("id")) {
            List<File> files = File.dao.find("select * from file where module='node' and fid=?", getPara("id"));
            try {
                renderJSONObject(Constant.SUCCESS, files);
            } catch (JSONException e) {
                renderJSONError();
            }
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
