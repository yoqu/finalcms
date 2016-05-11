package org.yoqu.cms.core.admin.modules.node;

import com.jfinal.core.Controller;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.core.util.StringUtils;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/6 0006
 * @description
 */
public class NodeController extends Controller {

    public void index() {
        if (getPara() != null) {
            if (!StringUtils.isNumbervalue(getPara())) {
                render(Constant.RENDER_ACCESS_NOT_FOUND);
                return;
            } else {
                int id = getParaToInt();
                Node node = Node.dao.findById(id);
                if (node == null) {
                    render(Constant.RENDER_ACCESS_NOT_FOUND);
                    return;
                }
                setAttr("node", node);
                setAttr("site_title", node.getTitle());
                render("/admin/node/edit.html");
            }
        } else {
            List<Node> nodeList = Node.dao.find("select * from node where is_delete=0");
            setAttr("nodeList", nodeList);
            setAttr("site_title", "内容管理");
        }
    }


}
