package com.lhd.login;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.MessageRequestPacket;
import packet.MessageResponsePacket;
import packet.Session;
import packet.SessionUtil;

import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/12 15:41
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext chx, MessageRequestPacket messageRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(chx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUsername(session.getUsername());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }

    private MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
        System.out.println(new Date() + ": 服务端接收到的信息 -> " + messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();

        String message = messageRequestPacket.getMessage();
        messageResponsePacket.setMessage("服务端接收到的信息：【" + message + "】");
        return messageResponsePacket;
    }
}
