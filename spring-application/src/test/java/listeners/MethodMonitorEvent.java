package listeners;

import java.util.EventObject;

/**
 * 事件类型
 */
public class MethodMonitorEvent extends EventObject{

    public long timestamp;

    public MethodMonitorEvent(Object o) {
        super(o);
    }
}
