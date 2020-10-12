package com.lhd.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.*;

import java.util.UUID;

/**
 * @author Haidong Liu
 * @date 2020/10/12 15:40
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext chx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket packet = new LoginResponsePacket();
        String userId = UUID.randomUUID().toString().substring(0, 6);
        packet.setUserId(userId);
        packet.setSuccess(true);
        SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), chx.channel());

        chx.channel().writeAndFlush(packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket){
        String username = loginRequestPacket.getUsername();

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setSuccess(true);
        loginResponsePacket.setReason("用户: " + username + " 登录成功");
        return loginResponsePacket;
    }
}
