package com.lhd.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.LoginResponsePacket;
import packet.Session;
import packet.SessionUtil;

import java.util.Date;

/**
 * @author Haidong Liu
 * @date 2020/10/12 16:03
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if(msg.getSuccess()){
            SessionUtil.bindSession(new Session(msg.getUserId(), msg.getUserId()), ctx.channel());
            System.out.println(new Date() + ": 登录成功");
        } else {
            System.out.println(new Date() + ": 登录失败，原因 -> " + msg.getReason());
        }
    }
}
