package packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Haidong Liu
 * @date 2020/10/12 11:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet{

    private Boolean success;

    private String reason;

    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
