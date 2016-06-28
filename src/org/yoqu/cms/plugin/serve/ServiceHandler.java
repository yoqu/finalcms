package org.yoqu.cms.plugin.serve;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.yoqu.cms.plugin.serve.parser.CommandParser;
import org.yoqu.cms.plugin.serve.parser.Parser;
import org.yoqu.cms.plugin.serve.parser.exception.MessageTypeException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceHandler extends IoHandlerAdapter {

    private final static Logger log = LoggerFactory.getLogger(ServiceHandler.class);

    private Parser parser;

    public ServiceHandler(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("服务器异常  ", cause.getCause());
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.info("服务器接收数据  " + message);
        try {
            Object result = parser.parserMessage(message.toString(), session);
            session.write(result);
        } catch (MessageTypeException ex) {
            session.write(ex.getMessage());
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info("当前连接{}处于空闲状态：{}" + session.getRemoteAddress() + status);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.info("创建一个新连接：{}" + session.getRemoteAddress());
        //session.write("welcome to the chat room !");
//        ClientSession clientSession= (ClientSession) session.getAttribute("client");
//        clientSession.setSession(session);
//          session.setAttribute("test","mytest");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("打开一个session：{}#{}" + session.getId() + session.getBothIdleCount());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("关闭当前session：{}#{}" + session.getId() + session.getRemoteAddress());
        CloseFuture closeFuture = session.close(true);
        closeFuture.addListener(new IoFutureListener<IoFuture>() {
            public void operationComplete(IoFuture future) {
                if (future instanceof CloseFuture) {
                    ((CloseFuture) future).setClosed();
                    log.info("sessionClosed CloseFuture setClosed-->{}," + future.getSession().getId());
                    SessionManager.getInstance().removeSession(SessionManager.getInstance().getSessionById(future.getSession().getId()));
                }
            }
        });
    }

//
//    @Override
//    public void messageSent(IoSession session, Object message) throws Exception {
//        //log.info("服务器发送消息： {}"+ message);
//        //session.write(message);
//    }
}
