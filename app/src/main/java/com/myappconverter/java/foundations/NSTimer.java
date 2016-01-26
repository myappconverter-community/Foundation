
package com.myappconverter.java.foundations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import android.os.Handler;
import android.util.Log;

import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InvokableHelper;



public class NSTimer extends NSObject {

	public NSTimer() {
		super();
		mTimer = new Timer();
	}

	protected List<Runnable> runnables;
	private double timeInterval;
	private Boolean repeats;
	private NSDate fireDate;
	private NSDictionary<NSObject, NSObject> userInfo;
	private Timer mTimer;
	private SEL selector;
	private Object target;
	private Handler handler;

	// Creating a Timer

	public double getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(double timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Boolean getRepeats() {
		return repeats;
	}

	public void setRepeats(Boolean repeats) {
		this.repeats = repeats;
	}

	public NSDictionary<NSObject, NSObject> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(NSDictionary<NSObject, NSObject> userInfo) {
		this.userInfo = userInfo;
	}

	public Timer getmTimer() {
		return mTimer;
	}

	public void setmTimer(Timer mTimer) {
		this.mTimer = mTimer;
	}

	public SEL getSelector() {
		return selector;
	}

	public void setSelector(SEL selector) {
		this.selector = selector;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public NSDate getFireDate() {
		return fireDate;
	}

	/**
	 * @Signature: scheduledTimerWithTimeInterval:invocation:repeats:
	 * @Declaration : + (NSTimer *)scheduledTimerWithTimeInterval:(NSTimeInterval)seconds invocation:(NSInvocation *)invocation
	 *              repeats:(BOOL)repeats
	 * @Description : Creates and returns a new NSTimer object and schedules it on the current run loop in the default mode.
	 * @param timeInterval The number of seconds between firings of the timer. If seconds is less than or equal to 0.0, this method chooses
	 *            the nonnegative value of 0.1 milliseconds instead.
	 * @param invocation The invocation to use when the timer fires. The invocation object maintains a strong reference to its arguments
	 *            until the timer is invalidated.
	 * @param repeats If YES, the timer will repeatedly reschedule itself until invalidated. If NO, the timer will be invalidated after it
	 *            fires.
	 * @return Return Value A new NSTimer object, configured according to the specified parameters.
	 * @Discussion After seconds seconds have elapsed, the timer fires, invoking invocation.
	 **/

	public static NSTimer scheduledTimerWithTimeIntervalInvocationRepeats(double sec,
																		  NSInvocation invoc, boolean repeats) {
		NSTimer timer = new NSTimer();
		timer.timeInterval = sec;
		timer.repeats = repeats;
		return timer;
	}

	/**
	 * @Signature: scheduledTimerWithTimeInterval:target:selector:userInfo:repeats:
	 * @Declaration : + (NSTimer *)scheduledTimerWithTimeInterval:(NSTimeInterval)seconds target:(id)target selector:(SEL)aSelector
	 *              userInfo:(id)userInfo repeats:(BOOL)repeats
	 * @Description : Creates and returns a new NSTimer object and schedules it on the current run loop in the default mode.
	 * @param timeInterval The number of seconds between firings of the timer. If seconds is less than or equal to 0.0, this method chooses
	 *            the nonnegative value of 0.1 milliseconds instead.
	 * @param target The object to which to send the message specified by aSelector when the timer fires. The timer maintains a strong
	 *            reference to target until it (the timer) is invalidated.
	 * @param aSelector The message to send to target when the timer fires. The selector should have the following signature:
	 *            timerFireMethod: (including a colon to indicate that the method takes an argument). The timer passes itself as the
	 *            argument, thus the method would adopt the following pattern: - (void)timerFireMethod:(NSTimer *)timer
	 * @param userInfo The user info for the timer. The timer maintains a strong reference to this object until it (the timer) is
	 *            invalidated. This parameter may be nil.
	 * @param repeats If YES, the timer will repeatedly reschedule itself until invalidated. If NO, the timer will be invalidated after it
	 *            fires.
	 * @return Return Value A new NSTimer object, configured according to the specified parameters.
	 * @Discussion After seconds seconds have elapsed, the timer fires, sending the message aSelector to target.
	 **/

	public static NSTimer scheduledTimerWithTimeIntervalTargetSelectorUserInfoRepeats(double sec,
																					  final NSObject target, final SEL aSelector, NSDictionary userInfo, boolean repeats) {

		// TODO check that userInfo= nil, repeats =
		final NSTimer timer = new NSTimer();
		// apres x Sec execute :
		if (!repeats) {
			timer.mTimer.schedule(new java.util.TimerTask() {

				@Override
				public void run() {

					GenericMainContext.sharedContext.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								InvokableHelper.invoke(target, aSelector.getMethodName(), timer);
							} catch (Exception e) {

								Log.d("Exception :", "Message :" + e.getMessage()
										+ "\n StackTrace: " + Log.getStackTraceString(e));

							}
						}

					});

				}
			}, (int) (sec * 1000));
		} else {
			timer.mTimer.schedule(new java.util.TimerTask() {

				@Override
				public void run() {

					GenericMainContext.sharedContext.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {
								InvokableHelper.invoke(target, aSelector.getMethodName(), timer);

							} catch (Exception e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
										+ Log.getStackTraceString(e));

							}
						}
					});
				}
			}, (int) (sec * 1000), (int) (sec * 1000));
		}

		return timer;
	}

	public static NSTimer scheduledTimerWithTimeIntervalTargetSelectorUserInfoRepeats(
			final double sec, final Object target, final SEL aSelector, NSDictionary userInfo,
			boolean repeats) {

		// TODO check that userInfo= nil, repeats =
		final NSTimer timer = new NSTimer();

		if (!repeats) {
			// apres x Sec execute :
			timer.mTimer.schedule(new java.util.TimerTask() {

				@Override
				public void run() {
					GenericMainContext.sharedContext.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								InvokableHelper.invoke(target, aSelector.getMethodName(), timer);
							} catch (IllegalArgumentException e) {

								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
										+ Log.getStackTraceString(e));
							}
						}
					});
				}
			}, (int) sec * 1000);
		} else {
			timer.mTimer.schedule(new java.util.TimerTask() {

				@Override
				public void run() {

					GenericMainContext.sharedContext.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {
								InvokableHelper.invoke(target, aSelector.getMethodName(), timer);

							} catch (Exception e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
										+ Log.getStackTraceString(e));

							}
						}
					});
				}
			}, (int) (sec * 1000), (int) (sec * 1000));
		}

		return timer;
	}

	/**
	 * @Signature: timerWithTimeInterval:invocation:repeats:
	 * @Declaration : + (NSTimer *)timerWithTimeInterval:(NSTimeInterval)seconds invocation:(NSInvocation *)invocation repeats:(BOOL)repeats
	 * @Description : Creates and returns a new NSTimer object initialized with the specified invocation object.
	 * @param timeInterval The number of seconds between firings of the timer. If seconds is less than or equal to 0.0, this method chooses
	 *            the nonnegative value of 0.1 milliseconds instead
	 * @param invocation The invocation to use when the timer fires. The timer instructs the invocation object to maintain a strong
	 *            reference to its arguments.
	 * @param repeats If YES, the timer will repeatedly reschedule itself until invalidated. If NO, the timer will be invalidated after it
	 *            fires.
	 * @return Return Value A new NSTimer object, configured according to the specified parameters.
	 * @Discussion You must add the new timer to a run loop, using addTimer:forMode:. Then, after seconds have elapsed, the timer fires,
	 *             invoking invocation. (If the timer is configured to repeat, there is no need to subsequently re-add the timer to the run
	 *             loop.)
	 **/

	public static NSTimer timerWithTimeIntervalInvocationRepeats(double sec,
																 NSInvocation invocation, boolean repeats) {
		NSTimer timer = new NSTimer();
		timer.timeInterval = sec;
		timer.repeats = repeats;
		return timer;
	}

	/**
	 * @Signature: timerWithTimeInterval:target:selector:userInfo:repeats:
	 * @Declaration : + (NSTimer *)timerWithTimeInterval:(NSTimeInterval)seconds target:(id)target selector:(SEL)aSelector
	 *              userInfo:(id)userInfo repeats:(BOOL)repeats
	 * @Description : Creates and returns a new NSTimer object initialized with the specified object and selector.
	 * @param timeInterval The number of seconds between firings of the timer. If seconds is less than or equal to 0.0, this method chooses
	 *            the nonnegative value of 0.1 milliseconds instead.
	 * @param target The object to which to send the message specified by aSelector when the timer fires. The timer maintains a strong
	 *            reference to this object until it (the timer) is invalidated.
	 * @param aSelector The message to send to target when the timer fires. The selector should have the following signature:
	 *            timerFireMethod: (including a colon to indicate that the method takes an argument). The timer passes itself as the
	 *            argument, thus the method would adopt the following pattern: - (void)timerFireMethod:(NSTimer *)timer
	 * @param userInfo Custom user info for the timer. The timer maintains a strong reference to this object until it (the timer) is
	 *            invalidated. This parameter may be nil.
	 * @param repeats If YES, the timer will repeatedly reschedule itself until invalidated. If NO, the timer will be invalidated after it
	 *            fires.
	 * @return Return Value A new NSTimer object, configured according to the specified parameters.
	 * @Discussion You must add the new timer to a run loop, using addTimer:forMode:. Then, after seconds seconds have elapsed, the timer
	 *             fires, sending the message aSelector to target. (If the timer is configured to repeat, there is no need to subsequently
	 *             re-add the timer to the run loop.)
	 **/

	public static NSTimer timerWithTimeIntervalTargetSelectorUserInfoRepeats(double sec,
																			 final NSObject target, final SEL aSelector, NSDictionary<NSObject, NSObject> userinf,
																			 boolean repeats) {
		// TODO check that userInfo= nil, repeats =
		final NSTimer timer = new NSTimer();
		// apres x Sec execute :
		timer.mTimer.schedule(new java.util.TimerTask() {

			@Override
			public void run() {
				GenericMainContext.sharedContext.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							InvokableHelper.invoke(target, aSelector.getMethodName(), timer);
						} catch (Exception e) {
							Log.d("Exception :", "Message :" + e.getMessage() + "\n StackTrace: "
									+ Log.getStackTraceString(e));
						}
					}
				});

			}
		}, (int) sec);
		return timer;
	}

	// Firing a Timer

	/**
	 * @Signature: initWithFireDate:interval:target:selector:userInfo:repeats:
	 * @Declaration : - (id)initWithFireDate:(NSDate *)date interval:(NSTimeInterval)seconds target:(id)target selector:(SEL)aSelector
	 *              userInfo:(id)userInfo repeats:(BOOL)repeats
	 * @Description : Initializes a new NSTimer object using the specified object and selector.
	 * @param date The time at which the timer should first fire.
	 * @param timeInterval For a repeating timer, this parameter contains the number of seconds between firings of the timer. If seconds is
	 *            less than or equal to 0.0, this method chooses the nonnegative value of 0.1 milliseconds instead.
	 * @param target The object to which to send the message specified by aSelector when the timer fires. The timer maintains a strong
	 *            reference to this object until it (the timer) is invalidated.
	 * @param aSelector The message to send to target when the timer fires. The selector should have the following signature:
	 *            timerFireMethod: (including a colon to indicate that the method takes an argument). The timer passes itself as the
	 *            argument, thus the method would adopt the following pattern: - (void)timerFireMethod:(NSTimer *)timer
	 * @param userInfo Custom user info for the timer. The timer maintains a strong reference to this object until it (the timer) is
	 *            invalidated. This parameter may be nil.
	 * @param repeats If YES, the timer will repeatedly reschedule itself until invalidated. If NO, the timer will be invalidated after it
	 *            fires.
	 * @return Return Value The receiver, initialized such that, when added to a run loop, it will fire at date and then, if repeats is YES,
	 *         every seconds after that.
	 * @Discussion You must add the new timer to a run loop, using addTimer:forMode:. Upon firing, the timer sends the message aSelector to
	 *             target. (If the timer is configured to repeat, there is no need to subsequently re-add the timer to the run loop.)
	 **/

	public NSTimer initWithFireDateIntervalTargetSelectorUserInfoRepeats(NSDate date,
																		 double intervale, final Object target, final SEL aSelector,
																		 NSDictionary<NSObject, NSObject> userInfo, Boolean repeats) {
		if (mTimer == null) {
			mTimer = new Timer();
		}
		this.userInfo = userInfo;
		this.repeats = repeats;
		this.timeInterval = intervale;
		this.fireDate = new NSDate(date.getWrappedDate());
		this.selector = aSelector;
		this.target = target;

		return this;

	}

	/**
	 * @Signature: fire
	 * @Declaration : - (void)fire
	 * @Description : Causes the receiver’s message to be sent to its target.
	 * @Discussion You can use this method to fire a repeating timer without interrupting its regular firing schedule. If the timer is
	 *             non-repeating, it is automatically invalidated after firing, even if its scheduled fire date has not arrived.
	 **/

	public void fire() {
		mTimer.cancel();
	}

	// Stopping a Timer
	/**
	 * @Signature: invalidate
	 * @Declaration : - (void)invalidate
	 * @Description : Stops the receiver from ever firing again and requests its removal from its run loop.
	 * @Discussion This method is the only way to remove a timer from an NSRunLoop object. The NSRunLoop object removes its strong reference
	 *             to the timer, either just before the invalidate method returns or at some later point. If it was configured with target
	 *             and user info objects, the receiver removes its strong references to those objects as well.
	 **/

	public void invalidate() {
		mTimer.cancel();
		if (getHandler() != null && runnables != null) {
			for (Iterator iterator = runnables.iterator(); iterator.hasNext();) {
				Runnable runnabl = (Runnable) iterator.next();
				getHandler().removeCallbacks(runnabl);
			}

		}
	}

	// Information About a Timer

	/**
	 * @Signature: isValid
	 * @Declaration : - (BOOL)isValid
	 * @Description : Returns a Boolean value that indicates whether the receiver is currently valid.
	 * @return Return Value YES if the receiver is still capable of firing or NO if the timer has been invalidated and is no longer capable
	 *         of firing.
	 **/

	public boolean isValid() {
		// TODO check that
		// Cancels the Timer and all scheduled tasks. If there is a currently running task it is not affected. No more
		// tasks may be scheduled on this Timer. Subsequent calls do nothing.
		try {
			mTimer.schedule(new java.util.TimerTask() {

				@Override
				public void run() {
				}
			}, 5000);
			return true;
		} catch (Exception e) {

			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));


			return false;
		}

	}

	/**
	 * @Signature: fireDate
	 * @Declaration : - (NSDate *)fireDate
	 * @Description : Returns the date at which the receiver will fire.
	 * @return Return Value The date at which the receiver will fire. If the timer is no longer valid, this method returns the last date at
	 *         which the timer fired.
	 * @Discussion Use the isValid method to verify that the timer is valid.
	 **/

	public NSDate fireDate() {
		if (this.isValid()) {
			NSDate firedate = new NSDate();
			firedate.setWrappedDate(fireDate.getDate());
			return firedate;
		}
		return null;
	}

	/**
	 * @Signature: setFireDate:
	 * @Declaration : - (void)setFireDate:(NSDate *)date
	 * @Description : Resets the firing time of the receiver to the specified date.
	 * @param date The new date at which to fire the receiver. If the new date is in the past, this method sets the fire time to the current
	 *            time.
	 * @Discussion You typically use this method to adjust the firing time of a repeating timer. Although resetting a timer’s next firing
	 *             time is a relatively expensive operation, it may be more efficient in some situations. For example, you could use it in
	 *             situations where you want to repeat an action multiple times in the future, but at irregular time intervals. Adjusting
	 *             the firing time of a single timer would likely incur less expense than creating multiple timer objects, scheduling each
	 *             one on a run loop, and then destroying them. You should not call this method on a timer that has been invalidated, which
	 *             includes non-repeating timers that have already fired. You could potentially call this method on a non-repeating timer
	 *             that had not yet fired, although you should always do so from the thread to which the timer is attached to avoid
	 *             potential race conditions.
	 **/

	public void setFireDate(NSDate date) {
		this.fireDate = date;
	}

	/**
	 * @Signature: timeInterval
	 * @Declaration : - (NSTimeInterval)timeInterval
	 * @Description : Returns the receiver’s time interval.
	 * @return Return Value The receiver’s time interval. If the receiver is a non-repeating timer, returns 0 (even if a time interval was
	 *         set).
	 **/

	public double timeInterval() {
		if (repeats)
			return 0;
		return timeInterval;
	}

	/**
	 * @Signature: userInfo
	 * @Declaration : - (id)userInfo
	 * @Description : Returns the receiver's userInfo object.
	 * @return Return Value The receiver's userInfo object.
	 * @Discussion Do not invoke this method after the timer is invalidated. Use isValid to test whether the timer is valid.
	 **/

	public NSDictionary<NSObject, NSObject> userInfo() {
		return this.userInfo;
	}

	// Firing Tolerance
	/**
	 * @Signature: setTolerance:
	 * @Declaration : - (void)setTolerance:(NSTimeInterval)tolerance
	 * @Description : Sets the amount of time after the scheduled fire date that the timer may fire to the given interval.
	 * @param tolerance The amount of time after the scheduled firing time that the timer may fire.
	 * @Discussion The default value is zero, which means no additional tolerance is applied. Setting a tolerance for a timer allows it to
	 *             fire later than the scheduled fire date. Allowing the system flexibility in when a timer fires increases the ability of
	 *             the system to optimize for increased power savings and responsiveness. The timer may fire at any time between its
	 *             scheduled fire date and the scheduled fire date plus the tolerance. The timer will not fire before the scheduled fire
	 *             date. For repeating timers, the next fire date is calculated from the original fire date regardless of tolerance applied
	 *             at individual fire times, to avoid drift. The system reserves the right to apply a small amount of tolerance to certain
	 *             timers regardless of the value of this property.
	 **/

	public void setTolerance(double tolerance) {
		this.timeInterval = tolerance;
	}

	/**
	 * @Signature: tolerance
	 * @Declaration : - (NSTimeInterval)tolerance
	 * @Description : Returns the amount of time after the scheduled fire date that the timer may fire.
	 * @return Return Value The amount of time after the scheduled firing time that the timer may fire.
	 * @Discussion The default value is zero, which means no additional tolerance is applied. For details, see setTolerance:.
	 **/

	public double tolerance() {
		return this.timeInterval;
	}

	public Timer getTimer() {
		return mTimer;
	}

	public void setTimer(Timer pTimer) {
		mTimer = pTimer;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}