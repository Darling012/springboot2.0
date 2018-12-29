package listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MethodMonitorEventPublisher {
    private List<MethodMonitorEventListener> listeners = new ArrayList<>();
    public void methodMonitor() {
        MethodMonitorEvent event = new MethodMonitorEvent(this);
        publishEvent("begin",event);
        // 模拟方法执行：休眠5秒钟
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishEvent("end",event);
    }

    private void publishEvent(String methodName, MethodMonitorEvent event) {

        // 避免在事件处理期间，监听器被移除，这里为了安全做一个复制操作
        List<MethodMonitorEventListener> copyListeners =  new ArrayList<MethodMonitorEventListener>(listeners);
        for (MethodMonitorEventListener listener : copyListeners) {
            if ("begin".equals(methodName)) {
                listener.onMethodBegin(event);
            } else {
                listener.onMethodEnd(event);
            }
        }
    }
    public void addEventListener(MethodMonitorEventListener listener) {
        this.listeners.add(listener);
    }
    public void removeEventListener(MethodMonitorEventListener listener) {}
    public void removeAllListeners() {}
    public static void main (String[] args){

        MethodMonitorEventPublisher publisher = new MethodMonitorEventPublisher();
        publisher.addEventListener(new AbstractMethodMonitorEventListener());
        publisher.methodMonitor();

    }
}
