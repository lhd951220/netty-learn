import com.lhd.login.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import packet.PacketDecoder;
import packet.PacketEncoder;

/**
 * @author Haidong Liu
 * @date 2020/10/12 10:10
 */
public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ssc) throws Exception {
                        ssc.pipeline()
                                .addLast(new LifeCyCleTestHandler())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                })
                .bind(8000)
                .addListener((ChannelFutureListener) channelFuture -> {
                    if(channelFuture.isSuccess()){
                        System.out.println("服务器启动成功，端口号: " + 8000);
                    } else {
                        Throwable cause = channelFuture.cause().getCause();
                        System.out.println("服务器启动失败，原因: ");
                        cause.printStackTrace();
                    }
                });
    }
}
