package listeners;

import java.util.EventListener;
// 1、定义事件监听接口
public interface MethodMonitorEventListener extends EventListener{

    // 处理方法执行之前发布的事件
    public void onMethodBegin(MethodMonitorEvent methodMonitorEvent) ;
    // 处理方法结束时发布的事件
    public void onMethodEnd(MethodMonitorEvent methodMonitorEvent);
}
