package packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Haidong Liu
 * @date 2020/10/12 12:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends Packet{

    private String message;

    private String fromUserId;

    private String fromUsername;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
