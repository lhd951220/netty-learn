package packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Haidong Liu
 * @date 2020/10/12 12:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet{

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
