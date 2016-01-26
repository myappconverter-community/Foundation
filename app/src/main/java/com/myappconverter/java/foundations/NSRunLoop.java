
package com.myappconverter.java.foundations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;



public class NSRunLoop extends NSObject {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	public static final NSString NSDefaultRunLoopMode = new NSString("NSDefaultRunLoopMode");
	public static final NSString NSRunLoopCommonModes = new NSString("NSRunLoopCommonModes");;

	private Map<SEL, Thread> mapPerforme = new HashMap<SEL, Thread>();
	private List<SEL> listePerforme = new ArrayList<SEL>();
	private NSString currentmode;
	private Map<String, Integer> modePort = new HashMap<String, Integer>();
	private Map<Integer, Boolean> portEtat = new HashMap<Integer, Boolean>();
	private List<ModeDate> listeModeDate = new ArrayList<NSRunLoop.ModeDate>();
	private static Looper looper = Looper.getMainLooper();
	private Handler handler = new Handler();

	// Accessing Run Loops and Modes

	/**
	 * @Signature: currentRunLoop
	 * @Declaration : + (NSRunLoop *)currentRunLoop
	 * @Description : Returns the NSRunLoop object for the current thread.
	 * @return Return Value The NSRunLoop object for the current thread.
	 * @Discussion If a run loop does not yet exist for the thread, one is created and returned.
	 **/

	public static NSRunLoop currentRunLoop() {
		NSRunLoop nsrunloop = new NSRunLoop();
		nsrunloop.looper = Looper.myLooper();
		return nsrunloop;

	}

	/**
	 * @Signature: currentMode
	 * @Declaration : - (NSString *)currentMode
	 * @Description : Returns the receiver's current input mode.
	 * @return Return Value The receiver's current input mode. This method returns the current input mode only while the receiver is
	 *         running; otherwise, it returns nil.
	 * @Discussion The current mode is set by the methods that run the run loop, such as acceptInputForMode:beforeDate: and
	 *             runMode:beforeDate:.
	 **/

	public NSString currentMode() {
		return this.currentmode;

	}

	/**
	 * @Signature: limitDateForMode:
	 * @Declaration : - (NSDate *)limitDateForMode:(NSString *)mode
	 * @Description : Performs one pass through the run loop in the specified mode and returns the date at which the next timer is scheduled
	 *              to fire.
	 * @param mode The run loop mode to search. You may specify custom modes or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @return Return Value The date at which the next timer is scheduled to fire, or nil if there are no input sources for this mode.
	 * @Discussion The run loop is entered with an immediate timeout, so the run loop does not block, waiting for input, if no input sources
	 *             need processing.
	 **/

	public NSDate limitDateForMode(NSString mode) {
		List<Date> listeDate = new ArrayList<Date>();
		Date dateFire = null;
		/*
		 * Set keys = modeDate.keySet(); Iterator it = keys.iterator(); while (it.hasNext()) { String mod = (String) it.next(); if
		 * (mode.getString().equals(mod)) { dateFire = modeDate.get(mod); listeDate.add(dateFire); } }
		 */
		for (ModeDate modeDate : listeModeDate) {
			if (modeDate.getMode().equals(mode.getWrappedString())) {
				dateFire = modeDate.getDate();
				listeDate.add(dateFire);
			}

		}

		Collections.sort(listeDate);
		NSDate nsDate = new NSDate();
		nsDate.setWrappedDate(listeDate.get(0));
		return nsDate;
	}

	/**
	 * @Signature: mainRunLoop
	 * @Declaration : + (NSRunLoop *)mainRunLoop
	 * @Description : Returns the run loop of the main thread.
	 * @return Return Value An object representing the main threadâ€™s run loop.
	 **/

	public static NSRunLoop mainRunLoop() {
		NSRunLoop nsrunloop = new NSRunLoop();
		nsrunloop.looper = Looper.getMainLooper();
		return nsrunloop;
	}

	// Managing Timers
	/**
	 * @Signature: addTimer:forMode:
	 * @Declaration : - (void)addTimer:(NSTimer *)aTimer forMode:(NSString *)mode
	 * @Description : Registers a given timer with a given input mode.
	 * @param aTimer The timer to register with the receiver.
	 * @param mode The mode in which to add aTimer. You may specify a custom mode or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @Discussion You can add a timer to multiple input modes. While running in the designated mode, the receiver causes the timer to fire
	 *             on or after its scheduled fire date. Upon firing, the timer invokes its associated handler routine, which is a selector
	 *             on a designated object. The receiver retains aTimer. To remove a timer from all run loop modes on which it is installed,
	 *             send an invalidate message to the timer.
	 **/

	public void addTimerForMode(NSTimer aTimer, NSString mode) {
		// FIXME add mode
		final NSTimer timer = aTimer;
		if (handler == null)
			handler = new Handler(looper);

		final Runnable onEverySecond = new Runnable() {
			@Override
			public void run() {
				try {
					timer.getSelector().invokeMethodOnTarget(timer.getTarget(), timer.getSelector().getMethodName(), null);
					if (timer.getRepeats()) {
						long period = (long) (timer.getTimeInterval() * 1000);
						handler.postDelayed(this, period);
					}
				} catch (Exception e) {
					LOGGER.info(String.valueOf(e));
				}

			}
		};
		if (aTimer.getHandler() != null && aTimer.runnables != null) {
			for (Iterator iterator = aTimer.runnables.iterator(); iterator.hasNext();) {
				Runnable runnabl = (Runnable) iterator.next();
				aTimer.getHandler().removeCallbacks(runnabl);
			}
		}
		aTimer.setHandler(handler);
		if (aTimer.runnables == null) {
			aTimer.runnables = new ArrayList<Runnable>();
		}
		aTimer.runnables.add(onEverySecond);
		Date now = new Date();
		Date lunchDate = timer.getFireDate().getWrappedDate();
		handler.postAtTime(onEverySecond, lunchDate.getTime() - now.getTime());
	}

	// Managing Ports

	/**
	 * @Signature: addPort:forMode:
	 * @Declaration : - (void)addPort:(NSPort *)aPort forMode:(NSString *)mode
	 * @Description : Adds a port as an input source to the specified mode of the run loop.
	 * @param aPort The port to add to the receiver.
	 * @param mode The mode in which to add aPort. You may specify a custom mode or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @Discussion This method schedules the port with the receiver. You can add a port to multiple input modes. When the receiver is
	 *             running in the specified mode, it dispatches messages destined for that port to the portâ€™s designated handler routine.
	 **/

	public void addPortForMode(NSPort aPort, NSString mode) {

		if (modePort.containsKey(mode.getWrappedString())) {
			Log.w("NSRunLoop", "This port already exists");
		} else {
			portEtat.put(aPort.getPort(), true);
			modePort.put(mode.getWrappedString(), aPort.getPort());
		}
	}

	/**
	 * @Signature: removePort:forMode:
	 * @Declaration : - (void)removePort:(NSPort *)aPort forMode:(NSString *)mode
	 * @Description : Removes a port from the specified input mode of the run loop.
	 * @param aPort The port to remove from the receiver.
	 * @param mode The mode from which to remove aPort. You may specify a custom mode or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @Discussion If you added the port to multiple input modes, you must remove it from each mode separately.
	 **/

	public void removePortForMode(NSPort aPort, NSString mode) {

		Set keys = modePort.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			String mod = (String) it.next();
			if (mode.getWrappedString().equals(mod)) {
				modePort.remove(mod);
				Integer port = modePort.get(mod);
				portEtat.remove(port);
			}
		}
	}

	// Running a Loop

	/**
	 * @Signature: run
	 * @Declaration : - (void)run
	 * @Description : Puts the receiver into a permanent loop, during which time it processes data from all attached input sources.
	 * @Discussion If no input sources or timers are attached to the run loop, this method exits immediately; otherwise, it runs the
	 *             receiver in the NSDefaultRunLoopMode by repeatedly invoking runMode:beforeDate:. In other words, this method effectively
	 *             begins an infinite loop that processes data from the run loopâ€™s input sources and timers. Manually removing all known
	 *             input sources and timers from the run loop is not a guarantee that the run loop will exit. OS X can install and remove
	 *             additional input sources as needed to process requests targeted at the receiverâ€™s thread. Those sources could therefore
	 *             prevent the run loop from exiting. If you want the run loop to terminate, you shouldn't use this method. Instead, use one
	 *             of the other run methods and also check other arbitrary conditions of your own, in a loop. A simple example would be:
	 *             BOOL shouldKeepRunning = YES; // global NSRunLoop *theRL = [NSRunLoop currentRunLoop]; while (shouldKeepRunning && [theRL
	 *             runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]]); where shouldKeepRunning is set to NO somewhere else in
	 *             the program.
	 **/

	public void run() {

		/*
		 * Set keys = modeDate.keySet(); Iterator it = keys.iterator(); while (it.hasNext()) { String mod = (String) it.next();
		 */

		for (int i = 0; i < listeModeDate.size(); i++) {

			NSDate date = this.limitDateForMode(NSDefaultRunLoopMode);
			this.runModeBeforeDate(NSDefaultRunLoopMode, date);

		}

	}

	/**
	 * @Signature: runMode:beforeDate:
	 * @Declaration : - (BOOL)runMode:(NSString *)mode beforeDate:(NSDate *)limitDate
	 * @Description : Runs the loop once, blocking for input in the specified mode until a given date.
	 * @param mode The mode in which to run. You may specify custom modes or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @param limitDate The date until which to block.
	 * @return Return Value YES if the run loop ran and processed an input source or if the specified timeout value was reached; otherwise,
	 *         NO if the run loop could not be started.
	 * @Discussion If no input sources or timers are attached to the run loop, this method exits immediately and returns NO; otherwise, it
	 *             returns after either the first input source is processed or limitDate is reached. Manually removing all known input
	 *             sources and timers from the run loop does not guarantee that the run loop will exit immediately. OS X may install and
	 *             remove additional input sources as needed to process requests targeted at the receiverâ€™s thread. Those sources could
	 *             therefore prevent the run loop from exiting. Note:Â A timer is not considered an input source and may fire multiple times
	 *             while waiting for this method to return
	 **/

	public boolean runModeBeforeDate(NSString mode, NSDate limitDate) {

		boolean runDone = false;
		currentmode = null;
		List<ModeDate> modeExecute = new ArrayList<NSRunLoop.ModeDate>();
		for (ModeDate modeDate : listeModeDate) {
			if (mode.getWrappedString().equals(modeDate.getMode())) {
				Date dateout = modeDate.getDate();
				if (dateout.compareTo(limitDate.getWrappedDate()) == -1 || dateout.compareTo(limitDate.getWrappedDate()) == 0) {
					NSString nsString = new NSString();
					nsString.setWrappedString(mode.getWrappedString());
					currentmode = nsString;
					// looper.prepare();
					Handler mHandler = new Handler();
					// Thread thread = looper.getThread();
					Thread thread = new Thread();
					mHandler.post(thread);
					thread.start();
					// looper.loop();
					// looper.quit();
					runDone = true;
					modeExecute.add(modeDate);
				}
			}
		}
		for (ModeDate modeDate : modeExecute) {
			listeModeDate.remove(modeDate);

		}
		return runDone;

	}

	/**
	 * @Signature: runUntilDate:
	 * @Declaration : - (void)runUntilDate:(NSDate *)limitDate
	 * @Description : Runs the loop until the specified date, during which time it processes data from all attached input sources.
	 * @param limitDate The date up until which to run.
	 * @Discussion If no input sources or timers are attached to the run loop, this method exits immediately; otherwise, it runs the
	 *             receiver in the NSDefaultRunLoopMode by repeatedly invoking runMode:beforeDate: until the specified expiration date.
	 *             Manually removing all known input sources and timers from the run loop is not a guarantee that the run loop will exit. OS
	 *             X can install and remove additional input sources as needed to process requests targeted at the receiverâ€™s thread. Those
	 *             sources could therefore prevent the run loop from exiting.
	 **/

	public void runUntilDate(NSDate limitDate) {
		this.runModeBeforeDate(NSDefaultRunLoopMode, limitDate);
	}

	/**
	 * @Signature: acceptInputForMode:beforeDate:
	 * @Declaration : - (void)acceptInputForMode:(NSString *)mode beforeDate:(NSDate *)limitDate
	 * @Description : Runs the loop once or until the specified date, accepting input only for the specified mode.
	 * @param mode The mode in which to run. You may specify custom modes or use one of the modes listed in â€œRun Loop Modes.â€?
	 * @param limitDate The date up until which to run.
	 * @Discussion If no input sources or timers are attached to the run loop, this method exits immediately; otherwise, it runs the run
	 *             loop once, returning as soon as one input source processes a message or the specifed time elapses. Note:Â A timer is not
	 *             considered an input source and may fire multiple times while waiting for this method to return Manually removing all
	 *             known input sources and timers from the run loop is not a guarantee that the run loop will exit. OS X can install and
	 *             remove additional input sources as needed to process requests targeted at the receiverâ€™s thread. Those sources could
	 *             therefore prevent the run loop from exiting.
	 **/

	public void acceptInputForModeBeforeDate(NSString mode, NSDate limitDate) {

		Set keys = portEtat.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			Object cle = it.next();
			portEtat.put((Integer) cle, false);
		}
		this.runModeBeforeDate(mode, limitDate);

		Set keys1 = portEtat.keySet();
		Iterator it1 = keys1.iterator();
		while (it1.hasNext()) {
			Object cle = it1.next();
			portEtat.put((Integer) cle, true);
		}
	}

	// Scheduling and Canceling Messages

	/**
	 * @Signature: performSelector:target:argument:order:modes:
	 * @Declaration : - (void)performSelector:(SEL)aSelector target:(id)target argument:(id)anArgument order:(NSUInteger)order
	 *              modes:(NSArray *)modes
	 * @Description : Schedules the sending of a message on the current run loop.
	 * @param aSelector A selector that identifies the method to invoke. This method should not have a significant return value and should
	 *            take a single argument of type id.
	 * @param target The object that defines the selector in aSelector.
	 * @param anArgument The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param order The priority for the message. If multiple messages are scheduled, the messages with a lower order value are sent before
	 *            messages with a higher order value.
	 * @param modes An array of input modes for which the message may be sent. You may specify custom modes or use one of the modes listed
	 *            in â€œRun Loop Modes.â€?
	 * @Discussion This method sets up a timer to perform the aSelector message on the current threadâ€™s run loop at the start of the next
	 *             run loop iteration. The timer is configured to run in the modes specified by the modes parameter. When the timer fires,
	 *             the thread attempts to dequeue the message from the run loop and perform the selector. It succeeds if the run loop is
	 *             running and in one of the specified modes; otherwise, the timer waits until the run loop is in one of those modes. This
	 *             method returns before the aSelector message is sent. The receiver retains the target and anArgument objects until the
	 *             timer for the selector fires, and then releases them as part of its cleanup. Use this method if you want multiple
	 *             messages to be sent after the current event has been processed and you want to make sure these messages are sent in a
	 *             certain order.
	 **/

	public void performSelectorTargetArgumentOrderModes(SEL aSelector, final NSObject target, final NSObject anArgument, int order,
														NSArray modes) {

		final Method method = getMethodFromReceiver(aSelector, target, anArgument);

		Handler mHandler = new Handler();
		Thread threadToimplement = new Thread() {
			@Override
			public void run() {
				try {
					method.invoke(target, anArgument);
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
			}
		};

		threadToimplement.setPriority(order);
		mHandler.post(threadToimplement);

		SEL ps = aSelector;

		mapPerforme.put(ps, threadToimplement);
		listePerforme.add(ps);

	}

	/**
	 * @Signature: cancelPerformSelector:target:argument:
	 * @Declaration : - (void)cancelPerformSelector:(SEL)aSelector target:(id)target argument:(id)anArgument
	 * @Description : Cancels the sending of a previously scheduled message.
	 * @param aSelector The previously-specified selector.
	 * @param target The previously-specified target.
	 * @param anArgument The previously-specified argument.
	 * @Discussion You can use this method to cancel a message previously scheduled using the performSelector:target:argument:order:modes:
	 *             method. The parameters identify the message you want to cancel and must match those originally specified when the
	 *             selector was scheduled. This method removes the perform request from all modes of the run loop.
	 **/

	public void cancelPerformSelectorTargetArgument(SEL aSelector, Object target, NSObject anArgument) {
		Thread thread = mapPerforme.get(aSelector);
		handler.removeCallbacks(thread);
	}

	/**
	 * @Signature: cancelPerformSelectorsWithTarget:
	 * @Declaration : - (void)cancelPerformSelectorsWithTarget:(id)target
	 * @Description : Cancels all outstanding ordered performs scheduled with a given target.
	 * @param target The previously-specified target.
	 * @Discussion This method cancels the previously scheduled messages associated with the target, ignoring the selector and argument of
	 *             the scheduled operation. This is in contrast to cancelPerformSelector:target:argument:, which requires you to match the
	 *             selector and argument as well as the target. This method removes the perform requests for the object from all modes of
	 *             the run loop.
	 **/

	public void cancelPerformSelectorsWithTarget(NSObject target) {
		for (SEL performe : listePerforme) {
			if (performe.getMethodName().equals(target)) {
				Thread thread = mapPerforme.get(performe);
				handler.removeCallbacks(thread);
			}

		}

	}

	// added

	public Method getMethodFromReceiver(SEL aSelector, Object target, Object... anArgument) {
		// get methods
		Method methodToCall = null;
		Method[] methods = target.getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			// search method by name
			Class[] parameterTypes = method.getParameterTypes();
			if (method.getName().equalsIgnoreCase(aSelector.getMethodName())) {
				if (anArgument == null) {
					methodToCall = method;
				} else {
					if (method.getParameterTypes().length == anArgument.length) {
						for (int i = 0; i < anArgument.length; i++) {

							if (anArgument[i].getClass().isAssignableFrom(parameterTypes[i])) {
								methodToCall = method;
							}
						}

					}
				}
			}

		}
		return methodToCall;

	}

	public class ModeDate {
		String mode;
		Date date;

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}

}