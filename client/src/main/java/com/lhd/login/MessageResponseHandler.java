package com.lhd.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.MessageResponsePacket;

import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/12 16:08
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String fromUserId = msg.getFromUserId();
        String fromUsername = msg.getFromUsername();
        System.out.println(fromUserId + ":" + fromUsername + " -> " + msg .getMessage());
    }
}
