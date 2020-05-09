package nia.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by kerr.
 *
 * Listing 1.3 Asynchronous connect
 *
 * Listing 1.4 Callback in action
 */
public class ConnectExample {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 1.3 Asynchronous connect
     *
     * Listing 1.4 Callback in action
     * */
    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE; //reference form somewhere
        // Does not block
        // 异步地连接到远程节点
        ChannelFuture future = channel.connect(new InetSocketAddress("192.168.0.1", 25));
        // 注册一个ChannelFutureListener， 以便在操作完成时获得通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                // 检查操作 的状态
                if (future.isSuccess()) {
                    // 如果操作是成功的，则创建 一个ByteBuf 以持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    // 将数据异步地发送到远程节点。 返回一个ChannelFuture
                    ChannelFuture wf = future.channel().writeAndFlush(buffer);
                    // ...
                } else {
                    // 如果发生错误，则访问描述原因 的Throwable
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });

    }
}