import com.lhd.login.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import packet.LoginUtil;
import packet.MessageRequestPacket;
import packet.PacketCodec;

import java.util.Scanner;

/**
 * @author Haidong Liu
 * @date 2020/10/12 10:10
 */
public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        bootstrap.connect("127.0.0.1", 8000).addListener((ChannelFutureListener) channelFuture -> {
            if(channelFuture.isSuccess()){
                Channel channel = ((ChannelFuture) channelFuture).channel();
                startConsoleThread(channel);
            } else {
                System.out.println("客户端连接失败");
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = PacketCodec.DEFAULT.encode(packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
