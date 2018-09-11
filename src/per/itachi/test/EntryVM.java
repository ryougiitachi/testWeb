package per.itachi.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryVM {

	private static final Logger logger = LoggerFactory.getLogger(EntryVM.class);
	
	/**
	 * java内存堆分为新生代/新域(young generation)，老年代/旧域(old generation)，持久代/永久域(permanent generation)，
	 * 新建的对象都被存储在新域中，在历经了一定数量的垃圾收集循环后进入旧域。永久域存放class/method对象，他们不会因垃圾收集而被回收。
	 * 通常新旧域与永久域被看成是两种堆。<br/>
	 * <br/>
	 * 参考文章：http://wyj-study.iteye.com/blog/2254845<br/> 
	 * <br/>
	 * <br/>
	 * 根据JVM规范，java内存区分为五个部分：<b>虚拟机栈</b>、<b>程序寄存器</b>、<b>原生方法区</b>、<b>方法区</b>、<b>堆</b>，各区简介如下<br/>
	 * <b>虚拟机栈</b>：存放每个线程的私有栈，栈里面存放<i>栈帧</i>，每个方法都有自己的<i>栈帧</i>，里面存放局部变量表、
	 * 操作数栈、方法出口等信息。当栈的调用深度超过允许最大值就会报StackOverflowError，该最大值不定，可用无限递归测试。<br/>
	 * <b>程序寄存器</b>：jvm支持多线程同时运行，每个线程有自己的程序计数器，该区域存放当前执行线程指令地址，只存放jvm方法，原生方法不算<br/>
	 * <b>原生方法区</b>：存放原生方法，一般不用管<br/>
	 * <b>方法区</b>：所有线程共享，用于存放类信息、常量池、方法数据与代码，java1.7hotspot及其之前所谓永久代就是在这个区域发生的；
	 * 但在java1.8hotspot中取消了永久代，用<i>元空间</i>代替了，并且元数据并不是在jvm而在本地内存中；<br/>
	 * java1.8中默认情况下jvm将String存放到堆中，在设置了元空间参数后将放到元空间。<br/>
	 * 1.7之前永久代相关参数-XX:PermSize(初始)-XX:MaxPermSize(最大)<br/>
	 * 1.8元空间参数-XX:XX:MetaspaceSize(初始)-XX:MaxMetaspaceSize(最大)<br/>
	 * <b>堆</b>：所有线程共享，每次new一个对象都会放到这个区，分为新生代与老年代，如果申请不到空间将报OutOfMemoryError，
	 * 新生代又分为eden区和survivor区<br/>
	 * 堆区相关参数-Xms(初始)-Xmx(最大)-Xmn(?)<br/>
	 * Eden区相关参数-XX:NewSize(初始)-XX:MaxNewSize(最大)<br/>
	 * Survivor区相关参数-XX:SurvivorRatio(比率)<br/>
	 * <br/>
	 * 参考文章：http://www.cnblogs.com/paddix/p/5309550.html 关于java1.8的hotspot虚拟机与之前版本的区别<br/>
	 * 
	 * @param args
	 * */
	public static void main(String[] args) {
		int i = 1;
		try {
			i /= 0;
		} 
		catch (Exception e) {
			logger.error("The value of i is {}", i, e);
		}
	}

}
