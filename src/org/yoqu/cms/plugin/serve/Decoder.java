package org.yoqu.cms.plugin.serve;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author yoqu
 * @date 2016/6/20 0020
 * @description
 */
public class Decoder implements ProtocolDecoder {

    private final static Logger log= LoggerFactory.getLogger(Decoder.class);

    private final static Charset charset =Charset.forName("UTF-8");

    //可变的IoBuffer缓冲区
    private IoBuffer buff= IoBuffer.allocate(100).setAutoExpand(true);

    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        log.info("########decode#########");
        while(ioBuffer.hasRemaining()){
            byte b=ioBuffer.get();
            if(b=='\n'){
                buff.flip();
                byte[] bytes=new byte[buff.limit()];
                buff.get(bytes);
                String message =new String(bytes,charset);
                buff= IoBuffer.allocate(100).setAutoExpand(true);
                protocolDecoderOutput.write(message);
                log.info("message:  "+message);
            }else{
                buff.put(b);
            }
        }
    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        log.info("########finishDecode#########");

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {
        log.info("########dispose#########");
        log.info((String) ioSession.getCurrentWriteMessage());
    }
}
