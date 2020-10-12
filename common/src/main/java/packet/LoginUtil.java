package packet;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author Haidong Liu
 * @date 2020/10/12 12:29
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);

        return attr != null;
    }
}
