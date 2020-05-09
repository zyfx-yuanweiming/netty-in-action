package nia.chapter2.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.4 Main class for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start()
        throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Bootstrap
            Bootstrap b = new Bootstrap();
            // 指定EventLoopGroup 以
            //处理客户端事件；需要适
            //用于NIO 的实现
            b.group(group)
                    // 适用于NIO 传输的
                    //Channel 类型
                .channel(NioSocketChannel.class)
                    // 设置服务器的
                    //InetSocketAddress
                .remoteAddress(new InetSocketAddress(host, port))
                    // 在创建Channel 时，
                    //向ChannelPipeline
                    //中添加一个Echo-
                    //ClientHandler 实例
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                        throws Exception {
                        ch.pipeline().addLast(
                             new EchoClientHandler());
                    }
                });
            // 连接到远程节点，阻
            //塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            // 阻塞，直到
            //Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池并且
            //释放所有的资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)
            throws Exception {

        new EchoClient("localhost", 9999).start();
    }
}

