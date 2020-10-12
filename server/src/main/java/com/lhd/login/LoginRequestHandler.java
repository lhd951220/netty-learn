package com.lhd.login;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.LoginRequestPacket;
import packet.LoginResponsePacket;
import packet.LoginUtil;

/**
 * @author Haidong Liu
 * @date 2020/10/12 15:40
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext chx, LoginRequestPacket loginRequestPacket) throws Exception {
        if(valid(loginRequestPacket)){
            LoginUtil.markAsLogin(chx.channel());
        } else {
            chx.channel().close();
            return;
        }
        chx.channel().writeAndFlush(login(loginRequestPacket));
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
