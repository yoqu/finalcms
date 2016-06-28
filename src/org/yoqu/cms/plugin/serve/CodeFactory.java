package org.yoqu.cms.plugin.serve;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @author yoqu
 * @date 2016/6/20 0020
 * @description
 */
public class CodeFactory implements ProtocolCodecFactory {

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return new Encoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return new Decoder();
    }
}
