package com.lhd.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author Haidong Liu
 * @date 2020/10/12 11:01
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆...");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("lhd");
        packet.setPassword("pass");

        ByteBuf encode = PacketCodec.DEFAULT.encode(packet);

        ctx.channel().writeAndFlush(encode);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf bytebuf = (ByteBuf) msg;

        Packet packet = PacketCodec.DEFAULT.decode(bytebuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.getSuccess()){
                // 在通道中标记用户已经登录
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登陆成功");
            } else {
                System.out.println(new Date() + ": 客户端登陆失败，原因: " + loginResponsePacket.getReason());
            }
        } else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;

            System.out.println(new Date() + ": 客户端接收到信息 -> " + messageResponsePacket.getMessage());
        }
    }
}
