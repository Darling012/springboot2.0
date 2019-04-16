package listeners;

import java.util.EventListener;

/**
 * 事件监听器接口针对不同的事件发布实际提供相应的处理方法定义，最重要的是，其方法只接收MethodMonitorEvent参数，说明这个监听器类只负责监听器对应的事件并进行处理
 */
// 1、定义事件监听接口
public interface MethodMonitorEventListener extends EventListener{

    // 处理方法执行之前发布的事件
    public void onMethodBegin(MethodMonitorEvent methodMonitorEvent) ;
    // 处理方法结束时发布的事件
    public void onMethodEnd(MethodMonitorEvent methodMonitorEvent);
}
