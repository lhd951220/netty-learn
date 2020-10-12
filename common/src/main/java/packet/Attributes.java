package packet;

import io.netty.util.AttributeKey;

/**
 * @author Haidong Liu
 * @date 2020/10/12 12:28
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
