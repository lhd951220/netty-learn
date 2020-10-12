package packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Haidong Liu
 * @date 2020/10/12 10:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet{

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
