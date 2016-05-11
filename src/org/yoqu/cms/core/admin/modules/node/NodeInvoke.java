package org.yoqu.cms.core.admin.modules.node;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.yoqu.cms.core.admin.Constant.SystemVariable;
import org.yoqu.cms.core.aop.InvokeBefore;
import org.yoqu.cms.core.config.Constant;
import org.yoqu.cms.core.model.Node;
import org.yoqu.cms.core.util.FinalProxy;

/**
 * @author yoqu
 * @date 2016/5/11 0011
 * @description
 */

public class NodeInvoke {

    private volatile static NodeInvoke userHook;

    public static NodeInvoke getInstance() {
        if (userHook == null) {
            synchronized (NodeInvoke.class) {
                userHook = (NodeInvoke) new FinalProxy().createProxy(NodeInvoke.class);
            }
        }
        return userHook;
    }

    @InvokeBefore("FindNodeByPage_Before")
    public Page<Node> findNodeByPage(int pageNumber) {
        return Node.dao.paginate(pageNumber, Integer.parseInt(SystemVariable.get(Constant.PAGE_SIZE).trim()), "select *", "from node where is_delete=0 order by create_date desc");
    }

    public boolean softDelete(int id) {
        if (Db.update("update node set is_delete=1 where id=?", id) > 0) {
            return true;
        } else {
            return false;
        }
    }

}
