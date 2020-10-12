package packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author Haidong Liu
 * @date 2020/10/12 10:44
 */
public class PacketCodec {
    private static final int MAGIC_NUMBER = 0x12345678;

    public static PacketCodec DEFAULT = new PacketCodec();

    public ByteBuf encode(Packet packet){
        // 创建 ByteBuf 对象
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        // 序列化 Java 对象
        byte[] data = Serializer.DEFAULT.serialize(packet);

        // 编码
        buffer.writeInt(MAGIC_NUMBER);
        buffer.writeByte(packet.getVersion());
        buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());
        buffer.writeInt(data.length);
        buffer.writeBytes(data);
        return buffer;
    }

    public Packet decode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本
        byteBuf.skipBytes(1);

        // 读取序列化算法
        byte sa = byteBuf.readByte();

        // 读取指令
        byte command = byteBuf.readByte();

        // 读取数据长度
        int len = byteBuf.readInt();

        // 读取数据
        byte[] data = new byte[len];
        byteBuf.readBytes(data);

        Class<? extends Packet> type = getRequestType(command);
        Serializer serializer = getSerializer(sa);

        if(type != null && serializer != null){
            return serializer.deserialize(type, data);
        }
        return null;
    }

    private Serializer getSerializer(byte sa) {
        if(sa == SerializerAlgorithm.JSON){
            return Serializer.DEFAULT;
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if(command == Command.LOGIN_REQUEST){
            return LoginRequestPacket.class;
        } else if(command == Command.LOGIN_RESPONSE){
            return LoginResponsePacket.class;
        } else if(command == Command.MESSAGE_REQUEST){
            return MessageRequestPacket.class;
        } else if(command == Command.MESSAGE_RESPONSE){
            return MessageResponsePacket.class;
        }
        return null;
    }
}
