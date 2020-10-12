package com.lhd.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.*;

import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/12 11:03
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.DEFAULT.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账户或者密码错误");
            }

            ByteBuf responseByteBuf = PacketCodec.DEFAULT.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端信息 -> " + messageRequestPacket.getMessage());

            // 处理信息
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务器回复信息: 【" + messageRequestPacket.getMessage() + "】");
            ByteBuf data = PacketCodec.DEFAULT.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(data);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
