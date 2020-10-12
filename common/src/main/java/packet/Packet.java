package packet;

import lombok.Data;

/**
 * @author Haidong Liu
 * @date 2020/10/12 10:36
 */
@Data
public abstract class Packet {
    private Byte version = 1;

    public abstract Byte getCommand();
}
