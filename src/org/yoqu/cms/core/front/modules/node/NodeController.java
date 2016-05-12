package org.yoqu.cms.core.front.modules.node;

import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.config.FinalCMS;
import org.yoqu.cms.core.model.Menu;
import org.yoqu.cms.core.model.MenuType;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.core.util.StringUtils;

import java.util.List;

/**
 * @author yoqu
 * @date 2016/5/12 0012
 * @description
 */
public class NodeController extends FinalCMS {

    public void index() {
        if (getPara() != null) {
            if (!StringUtils.isNumbervalue(getPara())) {
                renderNotFound();
            } else {
                Node node = Node.dao.findById(getParaToInt());
                if (node == null) {
                    render(Constant.RENDER_ACCESS_NOT_FOUND);
                    return;
                }
                setAttr("node", node);
                setAttr("site_title", node.getTitle());
                render("/front/node/node.html");
            }
        } else {
            renderNotFound();
        }
    }
}
