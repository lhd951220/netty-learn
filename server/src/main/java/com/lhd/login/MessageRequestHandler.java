package com.lhd.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.MessageRequestPacket;
import packet.MessageResponsePacket;

import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/12 15:41
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext chx, MessageRequestPacket messageRequestPacket) throws Exception {
        chx.channel().writeAndFlush(receiveMessage(messageRequestPacket));
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
        System.out.println(new Date() + ": 服务端接收到的信息 -> " + messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        String message = messageRequestPacket.getMessage();
        messageResponsePacket.setMessage("服务端接收到的信息：【" + message + "】");
        return messageResponsePacket;
    }
}
