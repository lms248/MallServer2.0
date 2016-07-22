package common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 时间处理器工具类
 */
public class TimerManagerUtils {
	/** 单线程定时器 */
	public static final ScheduledExecutorService single_t_scheduler = Executors.newScheduledThreadPool(1);
	/** 多线程定时器 */
	public static final ScheduledExecutorService many_t_scheduler = Executors.newScheduledThreadPool(4);

	/** 使用定时器线程模式 */
	public static enum TYPE {
		single, // 单线程运行模式
		many, // 多线程运行模式
		one_single, //定时器任务只执行一次,单线程运行模式
		one_many //定时器任务只执行一次,多线程运行模式
	};

	/**
	 * 创建并执行在给定延迟后启用的定期连续执行操作。
	 * 
	 * @param command 要执行的任务
	 * @param delay 从现在开始延迟执行的时间
	 * @param unit延迟参数的时间单位
	 * @return
	 */
	public static ScheduledFuture<?> schedule(Runnable command, long delay,
			TimeUnit unit) {
		return scheduleWithFixedDelay(TYPE.single, new TimerRunner(command), delay, delay, unit);
	}

	/**
	 * 创建并执行在给定延迟后启用的定期连续执行操作。(可以多线程执行)
	 * 
	 * @param command 要执行的任务
	 * @param delay 从现在开始延迟执行的时间
	 * @param unit 延迟参数的时间单位
	 * @return
	 */
	public static ScheduledFuture<?> scheduleMany(Runnable command, long delay,
			TimeUnit unit) {
		return scheduleWithFixedDelay(TYPE.many, new TimerRunner(command), delay, delay, unit);
	}

	/**
	 * 创建并执行在给定延迟后启用的一次性操作。
	 * 
	 * @param command 要执行的任务
	 * @param delay 从现在开始延迟执行的时间
	 * @param unit 延迟参数的时间单位
	 * @return
	 */
	public static ScheduledFuture<?> scheduleOne(Runnable command, long delay,
			TimeUnit unit) {
		return scheduleWithFixedDelay(TYPE.one_single, new TimerRunner(command), delay, 0, unit);
	}

	/**
	 * 创建并执行在给定延迟后启用的一次性操作。(多线程)
	 * 
	 * @param command 要执行的任务
	 * @param delay 从现在开始延迟执行的时间
	 * @param unit 延迟参数的时间单位
	 * @return
	 */
	public static ScheduledFuture<?> scheduleManyOne(Runnable command,
			long delay, TimeUnit unit) {
		return scheduleWithFixedDelay(TYPE.one_many,new TimerRunner( command), delay, 0, unit);
	}

	
	/**
	 * 连续运行定时任务
	 *
	 * @param command 要执行的任务
	 * @param initialDelay 首次执行的延迟时间
	 * @param period 连续执行之间的周期
	 * @param unit 时间单位
	 * @return
	 */
	public static ScheduledFuture<?> scheduleMany(Runnable command, long initialDelay, long period, TimeUnit unit) {
		return scheduleWithFixedDelay(TYPE.many,new TimerRunner(command), initialDelay, period, unit);
	}
	
	/**
	 * 启动一个定时调度的状态<br/>
	 * 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。如果任务的任一执行遇到异常，
	 * 就会取消后续执行。否则，只能通过执行程序的取消或终止方法来终止该任务。
	 * 
	 * @param command
	 *            - 要执行的任务
	 * @param initialDelay
	 *            - 首次执行的延迟时间
	 * @param period
	 *            - 连续执行之间的周期
	 * @param unit
	 *            - initialDelay 和 period 参数的时间单位
	 */
	private static ScheduledFuture<?> scheduleWithFixedDelay(TYPE t,
			Runnable command, long initialDelay, long period, TimeUnit unit) {
		switch (t) {
		case single:
			return single_t_scheduler.scheduleWithFixedDelay(new TimerRunner(command), initialDelay, period, unit);
		case many:
			return many_t_scheduler.scheduleWithFixedDelay(new TimerRunner(command), initialDelay, period, unit);
		case one_single:
			return single_t_scheduler.schedule(new TimerRunner(command), initialDelay, unit);
		case one_many:
			return many_t_scheduler.schedule(new TimerRunner(command), initialDelay, unit);
		}
		throw new RuntimeException("no such type" + t);
	}
	
	/**
	 * 清空定时器
	 */
	public static void destroyed(){
		TimerManagerUtils.many_t_scheduler.shutdown();
		TimerManagerUtils.single_t_scheduler.shutdown();
	}

}
class TimerRunner implements Runnable{
	private Runnable run=null;
	public TimerRunner(Runnable r){
		this.run=r;
	}
	@Override
	public void run() {
		try{
			run.run();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
