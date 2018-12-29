package listeners;

import java.util.EventObject;

public class MethodMonitorEvent extends EventObject{

    public long timestamp;

    public MethodMonitorEvent(Object o) {
        super(o);
    }
}
