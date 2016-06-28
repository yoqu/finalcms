package org.yoqu.cms.plugin.serve.core;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author yoqu
 * @date 2016/6/21 0021
 * @description
 */
public class Encoder implements ProtocolEncoder {
    private final static Logger log= LoggerFactory.getLogger(Encoder.class);
//    private final static Logger log= LoggerFactory.getLogger(Encoder.class);
    private final static Charset charset =Charset.forName("UTF-8");

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        log.info("#########encode..######");
        IoBuffer buffer= IoBuffer.allocate(100).setAutoExpand(true);
        buffer.putString(o.toString(),charset.newEncoder());
        buffer.putString(LineDelimiter.DEFAULT.getValue(),charset.newEncoder());
        buffer.flip();
        protocolEncoderOutput.write(buffer);
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {
        log.info("##########dispose##########");
    }
}
