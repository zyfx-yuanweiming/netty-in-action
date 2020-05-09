package nia.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kerr.
 *
 * Listing 1.2 ChannelHandler triggered by a callback
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        // 当一个新的连接已经被建立时， channelActive(ChannelHandler Context)将会被调用
        System.out.println(
                "Client " + ctx.channel().remoteAddress() + " connected");
    }
}