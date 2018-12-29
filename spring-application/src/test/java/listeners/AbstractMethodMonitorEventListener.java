package listeners;

public class AbstractMethodMonitorEventListener implements MethodMonitorEventListener{
    @Override
    public void onMethodBegin(MethodMonitorEvent methodMonitorEvent) {
        methodMonitorEvent.timestamp = System.currentTimeMillis();
        System.out.println("开始");
    }

    @Override
    public void onMethodEnd(MethodMonitorEvent methodMonitorEvent) {
        // 计算方法耗时
        long duration = System.currentTimeMillis() - methodMonitorEvent.timestamp;
        System.out.println("耗时：" + duration);
    }
}
