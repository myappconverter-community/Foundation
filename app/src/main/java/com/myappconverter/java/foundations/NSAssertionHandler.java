
package com.myappconverter.java.foundations;

import java.util.logging.Logger;




public class NSAssertionHandler extends NSObject {

	public final static Logger LOGGER = Logger.getLogger(NSAssertionHandler.class.getName());
	AssertionError assertionError;

	// Handling Assertion Failures

	/**
	 * Returns the NSAssertionHandler object associated with the current thread. + (NSAssertionHandler *)currentHandler
	 * 
	 * @return The NSAssertionHandler object associated with the current thread.
	 * @Discussion If no assertion handler is associated with the current thread, this method creates one and assigns it to the thread.
	 */

	/**
	 * @Declaration : + (NSAssertionHandler *)currentHandler
	 * @Description : Returns the NSAssertionHandler object associated with the current thread.
	 * @return Return Value The NSAssertionHandler object associated with the current thread.
	 * @Discussion If no assertion handler is associated with the current thread, this method creates one and assigns it to the thread.
	 **/
	
	public static NSAssertionHandler currentHandler() {
		return new NSAssertionHandler();
	}

	/**
	 * @Declaration : - (void)handleFailureInFunction:(NSString *)functionName file:(NSString *)fileName lineNumber:(NSInteger)line
	 *              description:(NSString *)format, ...
	 * @Description : Logs (using NSLog) an error message that includes the name of the function, the name of the file, and the line number.
	 * @param functionName The function that failed.
	 * @param object The object that failed.
	 * @param fileName The name of the source file.
	 * @param line The line in which the failure occurred.
	 * @param format,... A format string followed by a comma-separated list of arguments to substitute into the format string. See
	 *            Formatting String Objects for more information.
	 * @Discussion Raises NSInternalInconsistencyException.
	 **/
	
	public void handleFailureInFunctionFileLineNumberDescription(NSString functionName,
			NSString fileName, int line, NSString format, NSString... args) {
		String[] strings = null;
		if (args != null && args.length > 0) {
			strings = new String[args.length];
			for (int i = 0; i < args.length; i++) {
				strings[0] = args[0].getWrappedString();
			}
		}
		String msg = null;
		if (strings != null) {
			msg = String.format(format.getWrappedString(), strings);

		}
		if (msg == null) {
			msg = "";
		}
		LOGGER.info(String.format(
				"Stack trace Error in method :%s in file : %s and The line in which the failure occurred is %d with message %s",
				functionName, fileName, line, msg));

	}

	/**
	 * @Declaration : - (void)handleFailureInMethod:(SEL)selector object:(id)object file:(NSString *)fileName lineNumber:(NSInteger)line
	 *              description:(NSString *)format, ...
	 * @Description : Logs (using NSLog) an error message that includes the name of the method that failed, the class name of the object,
	 *              the name of the source file, and the line number.
	 * @param selector The selector for the method that failed
	 * @param object The object that failed.
	 * @param fileName The name of the source file.
	 * @param line The line in which the failure occurred.
	 * @param format,... A format string followed by a comma-separated list of arguments to substitute into the format string. See
	 *            Formatting String Objects for more information.
	 * @Discussion Raises NSInternalInconsistencyException.
	 **/
	
	public void handleFailureInMethodObjectFileLineNumberDescription(SEL selector, Object object,
			NSString fileName, Integer line, NSString format, Object... argument) {

		int i = 0;
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			i++;
			if (ste instanceof Object) {
				break;
			}
		}
		LOGGER.info(String.format(
				"Stack trace Error in method :%s and object: %s in file : %s and The line in which the failure occurred is %d ",
				Thread.currentThread().getStackTrace()[i].getMethodName(),
				Thread.currentThread().getStackTrace()[i].getClassName(),
				Thread.currentThread().getStackTrace()[i].getFileName(),
				Thread.currentThread().getStackTrace()[i].getLineNumber()));

		if (format.getWrappedString().toString().contains("@")) {
			format.getWrappedString().toString().replace("@", "s");
		}

		LOGGER.info(String.format(format.getWrappedString().toString(), argument));

	}

}