package org.yoqu.cms.plugin.serve;

import com.jfinal.kit.PropKit;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yoqu.cms.plugin.serve.parser.CommandParser;

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
    private SessionManager sessionManager;
    private static SocketService service;
    private ServiceHandler handler;


    private ServiceHandler getHandler() throws NullPointerException{
        if(handler==null)
            throw new NullPointerException("请确认是否执行开始方法");
        else{
            return this.handler;
        }
    }

    public static SocketService getInstance(){
        if(service==null){
            synchronized (SocketService.class){
                log.info("start construct SocketService.");
                String hostname= PropKit.use("socket_config.txt").get("hostname",null);
                log.info("hostName:{}"+hostname);
                int port=PropKit.use("socket_config.txt").getInt("port",8888);
                log.info("port:{}"+port);
                service=new SocketService(port,hostname);
            }
        }
        return service;
    }

    private SocketService() {
        handler=new ServiceHandler(new CommandParser(new MessageHandlerCenter()));
        acceptor = new NioSocketAcceptor();
        sessionManager = SessionManager.getInstance();
    }
    private SocketService(int port) {
        this();
        this.port = port;
    }

    private SocketService(int port, String hostname) {
        this(port);
        this.hostname = hostname;
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

    public SocketAcceptor getAcceptor() {
        return this.acceptor;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
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
