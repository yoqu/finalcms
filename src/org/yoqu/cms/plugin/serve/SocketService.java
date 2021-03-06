package org.yoqu.cms.plugin.serve;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.core.CodeFactory;
import org.yoqu.cms.plugin.serve.core.ServiceHandler;
import org.yoqu.cms.plugin.serve.core.config.Constant;
import org.yoqu.cms.plugin.serve.core.config.Routes;
import org.yoqu.cms.plugin.serve.core.parser.CommandParser;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author yoqu
 * @date 2016/6/21 0021
 * @description
 */
public class SocketService {
    private static final Logger log= LoggerFactory.getLogger(SocketService.class);
    private int port = 8888;
    private String hostname;
    private SocketAcceptor acceptor;
    private ServiceHandler handler;

    private ServiceHandler getHandler() throws NullPointerException{
        if(handler==null)
            throw new NullPointerException("请确认是否执行开始方法");
        else{
            return this.handler;
        }
    }

  public SocketService(Constant constant, Routes routes) {
        hostname=constant.hostname;
        port=constant.port;
        handler=new ServiceHandler(new CommandParser(routes));
        acceptor = new NioSocketAcceptor();
    }

    public boolean start() {
        log.info("SocketService start");
        DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
        filterChain.addLast("codec", new ProtocolCodecFilter(new CodeFactory()));
        acceptor.setHandler(handler);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        try {
            if (hostname != null) {
                acceptor.bind(new InetSocketAddress(hostname, port));
            } else {
                acceptor.bind(new InetSocketAddress(port));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean stop(){
        try {
        acceptor.unbind();
        }catch (Exception ex){
            log.error("stop SocketService error. {}"+ex.getMessage());
            return false;
        }
        return true;
    }
}
