
package com.myappconverter.java.foundations;

import java.util.Map;
import java.util.Map.Entry;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.os.SystemClock;

import com.myappconverter.mapping.utils.PerformBlock;



public class NSProcessInfo extends NSObject {

	// Enumeration

	public enum NSActivityOptions {

		NSActivityIdleDisplaySleepDisabled(1L << 40), //
		NSActivityIdleSystemSleepDisabled(1L << 20), //
		NSActivitySuddenTerminationDisabled(1L << 14), //
		NSActivityAutomaticTerminationDisabled(1L << 15), //
		NSActivityUserInitiated(0x00FFFFFFL | (1L << 20)), //
		NSActivityUserInitiatedAllowingIdleSystemSleep((0x00FFFFFFL | (1L << 20)) & ~(1L << 20)), //
		NSActivityBackground(0x000000FFL), //
		NSActivityLatencyCritical(0xFF00000000L);

		Long activity;
		NSActivityOptions option;

		NSActivityOptions(Long activity) {
			this.activity = activity;
		}

		NSActivityOptions(Long activity, NSActivityOptions option) {
			this.activity = activity;
			this.option = option;
		}

	}

	private WakeLock wakeLock = null;
	private Context context;
	private static final String TAG = "NSProcessInfo";

	// Returns the process information agent for the process.

	/**
	 * @Signature: processInfo
	 * @Declaration : + (NSProcessInfo *)processInfo
	 * @Description : Returns the process information agent for the process.
	 * @return Return Value Shared process information agent for the process.
	 * @Discussion An NSProcessInfo object is created the first time this method is invoked, and that same object is returned on each
	 *             subsequent invocation.
	 **/


	public static NSProcessInfo processInfo() {
		// not yet covered
		return null;

	}

	// Returns the command-line arguments for the process.

	/**
	 * @Signature: arguments
	 * @Declaration : - (NSArray *)arguments
	 * @Description : Returns the command-line arguments for the process.
	 * @return Return Value Array of strings with the processâ€™s command-line arguments.
	 **/

	public NSArray<NSString> arguments() {
		NSArray<NSString> nsArray = new NSArray<NSString>();
		ProcessBuilder processBuilder = new ProcessBuilder();
		for (String cmd : processBuilder.command()) {
			nsArray.getWrappedList().add(new NSString(cmd));
		}
		return nsArray;

	}

	// Returns the variable names and their values in the environment from which the process was launched.

	/**
	 * @Signature: environment
	 * @Declaration : - (NSDictionary *)environment
	 * @Description : Returns the variable names and their values in the environment from which the process was launched.
	 * @return Return Value Dictionary of environment-variable names (keys) and their values.
	 **/

	public NSDictionary<NSString, NSString> environment() {
		NSDictionary<NSString, NSString> nsDictionary = new NSDictionary<NSString, NSString>();
		ProcessBuilder processBuilder = new ProcessBuilder();
		Map<String, String> procEnv = processBuilder.environment();
		while (procEnv.entrySet().iterator().hasNext()) {
			Entry<String, String> elmnt = procEnv.entrySet().iterator().next();
			NSString nsKey = new NSString(elmnt.getKey());
			NSString nsValue = new NSString(elmnt.getValue());
			nsDictionary.getWrappedDictionary().put(nsKey, nsValue);
		}
		return nsDictionary;

	}

	/**
	 * @Signature: processIdentifier
	 * @Declaration : - (int)processIdentifier
	 * @Description : Returns the identifier of the process.
	 * @return Return Value Process ID of the process.
	 **/

	public int processIdentifier() {
		return Process.myPid();
	}

	/**
	 * @Signature: globallyUniqueString
	 * @Declaration : - (NSString *)globallyUniqueString
	 * @Description : Returns a global unique identifier for the process.
	 * @return Return Value Global ID for the process. The ID includes the host name, process ID, and a time stamp, which ensures that the
	 *         ID is unique for the network.
	 * @Discussion This method generates a new string each time it is invoked, so it also uses a counter to guarantee that strings created
	 *             from the same process are unique.
	 **/

	public NSString globallyUniqueString() {
		String name = System.getProperty("java.vm.name");

		int id = Process.myPid();
		Long timeStamp = Process.getElapsedCpuTime();
		NSString retour = new NSString();
		retour.setWrappedString(name + " : " + id + " : " + " : " + timeStamp);

		return retour;

	}

	// Returns the name of the process.

	/**
	 * @Signature: processName
	 * @Declaration : - (NSString *)processName
	 * @Description : Returns the name of the process.
	 * @return Return Value Name of the process.
	 * @Discussion The process name is used to register application defaults and is used in error messages. It does not uniquely identify
	 *             the process.
	 **/

	public NSString processName() {

		NSString currentProcName = new NSString();
		int pid = Process.myPid();
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
			if (processInfo.pid == pid) {
				currentProcName = new NSString(processInfo.processName);
			}
		}
		return currentProcName;
	}

	// Sets the name of the process.

	/**
	 * @Signature: setProcessName:
	 * @Declaration : - (void)setProcessName:(NSString *)name
	 * @Description : Sets the name of the process.
	 * @param name New name for the process.
	 * @Discussion Warning:Â  User defaults and other aspects of the environment might depend on the process name, so be very careful if you
	 *             change it. Setting the process name in this manner is not thread safe.
	 **/

	public void setProcessName(NSString name) {
		// not yet covered
	}

	// Returns the name of the host computer.

	/**
	 * @Signature: hostName
	 * @Declaration : - (NSString *)hostName
	 * @Description : Returns the name of the host computer.
	 * @return Return Value Host name of the computer.
	 **/

	public NSString hostName() {
		NSString nshost = new NSString();
		String host = System.getProperty("user.name");
		nshost.setWrappedString(host);
		return nshost;

	}

	/**
	 * @Signature: operatingSystem
	 * @Declaration : - (NSUInteger)operatingSystem
	 * @Description : Returns a constant to indicate the operating system on which the process is executing.
	 * @return Return Value Operating system identifier. See â€œConstantsâ€? for a list of possible values. In OS X, itâ€™s NSMACHOperatingSystem.
	 **/

	public long operatingSystem() {
		int retour = 0;
		String osname = System.getProperty("os.name", "generic").toLowerCase();
		if ("windows nt".equals(osname)) {
			retour = 1;
		} else if ("windows 95".equals(osname)) {
			retour = 2;
		} else if (osname.startsWith("solaris")) {
			retour = 3;
		} else if (osname.startsWith("hp-ux")) {
			retour = 4;
		} else if (osname.startsWith("mac")) {
			retour = 5;
		} else if (osname.startsWith("sunos")) {
			retour = 6;
		}

		else if (osname.startsWith("os")) {
			retour = 7;
		}

		return retour;

	}

	// Returns a string containing the name of the operating system on which the process is executing.

	/**
	 * @Signature: operatingSystemName
	 * @Declaration : - (NSString *)operatingSystemName
	 * @Description : Returns a string containing the name of the operating system on which the process is executing.
	 * @return Return Value Operating system name. In OS X, itâ€™s @"NSMACHOperatingSystem"
	 **/

	public NSString operatingSystemName() {
		NSString nsname = new NSString();
		String name = System.getProperty("os.name", "generic");
		nsname.setWrappedString(name);
		return nsname;
	}

	// Returns a string containing the version of the operating system on which the process is executing.

	/**
	 * @Signature: operatingSystemVersionString
	 * @Declaration : - (NSString *)operatingSystemVersionString
	 * @Description : Returns a string containing the version of the operating system on which the process is executing.
	 * @return Return Value Operating system version. This string is human readable, localized, and is appropriate for displaying to the
	 *         user. This string is not appropriate for parsing.
	 **/

	public NSString operatingSystemVersionString() {
		NSString nsversion = new NSString();
		String version = System.getProperty("os.version", "generic");
		nsversion.setWrappedString(version);
		return nsversion;
	}

	/**
	 * @Signature: physicalMemory
	 * @Declaration : - (unsigned long long)physicalMemory
	 * @Description : Provides the amount of physical memory on the computer.
	 * @return Return Value Amount of physical memory in bytes.
	 **/

	public long physicalMemory() {
		long totalSize;
		Runtime info = Runtime.getRuntime();
		totalSize = info.totalMemory();
		return totalSize;
	}

	/**
	 * @Signature: activeProcessorCount
	 * @Declaration : - (NSUInteger)activeProcessorCount
	 * @Description : Provides the number of active processing cores available on the computer.
	 * @return Return Value Number of active processing cores.
	 **/

	public long processorCount() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * @Signature: activeProcessorCount
	 * @Declaration : - (NSUInteger)activeProcessorCount
	 * @Description : Provides the number of active processing cores available on the computer.
	 * @return Return Value Number of active processing cores.
	 **/

	public long activeProcessorCount() {
		// not yet covered
		return 1;
	}

	// Returns how long it has been since the computer has been restarted.

	/**
	 * @Signature: systemUptime
	 * @Declaration : - (NSTimeInterval)systemUptime
	 * @Description : Returns how long it has been since the computer has been restarted.
	 * @return Return Value An NSTimeInterval indicating how long since the computer has been restarted.
	 **/

	public double systemUptime() {
		double nstime = SystemClock.uptimeMillis();
		return nstime;
	}

	/**
	 * @Signature: beginActivityWithOptions:reason:
	 * @Declaration : - (id<NSObject>)beginActivityWithOptions:(NSActivityOptions)options reason:(NSString *)reason
	 * @Description : Begin an activity using the given options and reason.
	 * @param options Options for the activity. See â€œActivity Optionsâ€? for possible values.
	 * @param reason A string used in debugging to indicate the reason the activity began.
	 * @return Return Value An object token representing the activity.
	 * @Discussion Indicate completion of the activity by calling endActivity: passing the returned object as the argument.
	 **/

	public Object beginActivityWithOptionsReason(NSActivityOptions options, NSString reason) {

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

		switch (options) {
			case NSActivityIdleDisplaySleepDisabled: {
				wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
						reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityIdleSystemSleepDisabled: {
				wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
						reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivitySuddenTerminationDisabled: {
				wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityAutomaticTerminationDisabled: {
				wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityUserInitiated: {
				wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityUserInitiatedAllowingIdleSystemSleep: {
				wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityBackground: {
				wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			case NSActivityLatencyCritical: {
				wakeLock = pm.newWakeLock(PowerManager.ON_AFTER_RELEASE, reason.getWrappedString());
				wakeLock.acquire();
				break;
			}
			default:
				break;
		}
		return wakeLock;
	}

	/**
	 * @Signature: endActivity:
	 * @Declaration : - (void)endActivity:(id<NSObject>)activity
	 * @Description : Ends the given activity.
	 * @param activity An activity object returned by beginActivityWithOptions:reason:.
	 **/


	public void endActivity(NSObject activity) {
		this.wakeLock.release();
	}

	/**
	 * @Signature: performActivityWithOptions:reason:usingBlock:
	 * @Declaration : - (void)performActivityWithOptions:(NSActivityOptions)options reason:(NSString *)reason usingBlock:(void
	 *              (^)(void))block
	 * @Description : Synchronously perform an activity defined by a given block using the given options.
	 * @param options Options for the activity. See â€œActivity Optionsâ€? for possible values.
	 * @param reason A string used in debugging to indicate the reason the activity began.
	 * @param block A block containing the work to be performed by the activity.
	 * @Discussion The activity will be automatically ended after block returns.
	 **/

	public void performActivityWithOptionsReasonUsingBlock(NSActivityOptions options,
														   NSString reason, PerformBlock.VoidBlockVoid block) {
		// not yet covered
	}

}