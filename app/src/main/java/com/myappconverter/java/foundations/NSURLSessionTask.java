
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSURLSession.NSURLSessionTaskState;
import com.myappconverter.java.foundations.protocols.NSCopying;


public abstract class NSURLSessionTask extends NSObject implements NSCopying {

	NSURL mURL;

	public NSURLSessionTask(){
		super();
	}

	public NSURLSessionTask(NSURL url){
		super();
		this.mURL = url;
	}

	// Properties

	protected NSURLSessionTaskState state;


	public NSURLSessionTaskState getState() {
		return state;
	}


	protected NSURLRequest currentRequest;

	public NSURLRequest getCurrentRequest() {
		return currentRequest;
	}


	protected int taskIdentifier;


	public int getTaskIdentifier() {
		return taskIdentifier;
	}


	public void setTaskIdentifier(int taskIdentifier) {
		this.taskIdentifier = taskIdentifier;
	}


	protected NSString taskDescription;


	public NSString getTaskDescription() {
		return taskDescription;
	}


	public void setTaskDescription(NSString taskDescription) {
		this.taskDescription = taskDescription;
	}


	protected NSURLResponse response;


	public NSURLResponse getResponse() {
		return response;
	}


	protected NSURLRequest originalRequest;


	public NSURLRequest getOriginalRequest() {
		return originalRequest;
	}


	protected NSError error;


	public NSError getError() {
		return error;
	}


	public void setError(NSError error) {
		this.error = error;
	}

	//

	protected int countOfBytesExpectedToReceive;


	public int getCountOfBytesExpectedToReceive() {
		return countOfBytesExpectedToReceive;
	}


	protected int countOfBytesSent;


	public int getCountOfBytesSent() {
		return countOfBytesSent;
	}


	protected int countOfBytesReceived;


	public int getCountOfBytesReceived() {
		return countOfBytesReceived;
	}


	protected int countOfBytesExpectedToSend;


	public int getCountOfBytesExpectedToSend() {
		return countOfBytesExpectedToSend;
	}

	// Controlling the Task State
	/**
	 * @Declaration : - (void)cancel
	 * @Description : Cancels the task.
	 **/

	public abstract void cancel();

	/**
	 * @Declaration : - (void)resume
	 * @Description : Resumes the task, if it is suspended.
	 **/

	public abstract void resume();

	/**
	 * @Declaration : - (void)suspend
	 * @Description : Temporarily suspends a task.
	 * @Discussion A task, while suspended, produces no network traffic and is not subject to timeouts. A download task can continue
	 *             transferring data at a later time. All other tasks must start over when resumed.
	 **/

	public abstract void suspend();

	/**
	 * state The current state of the task—active, suspended, in the process of being canceled, or completed. (read-only)
	 *
	 *  NSURLSessionTaskState state
	 */
	public NSURLSessionTaskState state() {
		return state;
	}

	// Obtaining Task Progress

	/**
	 * countOfBytesExpectedToReceive The number of bytes that the task expects to receive in the response body. (read-only)
	 *
	 *  int64_t countOfBytesExpectedToReceive
	 * @Discussion This value is determined based on the Content-Length header received from the server. If that header is absent, the value
	 *             is NSURLSessionTransferSizeUnknown.
	 */

	public int countOfBytesExpectedToReceive() {
		return countOfBytesExpectedToReceive;
	}

	/**
	 * countOfBytesExpectedToSend The number of bytes that the task expects to send in the request body. (read-only)
	 *
	 *  int64_t countOfBytesExpectedToSend
	 * @Discussion The URL loading system can determine the length of the upload data in three ways: From the length of the NSData object
	 *             provided as the upload body. From the length of the file on disk provided as the upload body of an upload task (not a
	 *             download task). From the Content-Length in the request object, if you explicitly set it. Otherwise, the value is
	 *             NSURLSessionTransferSizeUnknown (-1) if you provided a stream or body data object, or zero (0) if you did not.
	 */

	public int countOfBytesExpectedToSend() {
		return countOfBytesExpectedToSend;
	}

	/**
	 * countOfBytesReceived The number of bytes that the task has received from the server in the response body. (read-only)
	 *
	 *  int64_t countOfBytesReceived
	 * @Discussion To be notified when this value changes, implement the URLSession:dataTask:didReceiveData: delegate method (for data and
	 *             upload tasks) or the URLSession:downloadTask:didWriteData:totalBytesWritten:totalBytesExpectedToWrite: method (for
	 *             download tasks).
	 */

	public int countOfBytesReceived() {
		return countOfBytesReceived;
	}

	/**
	 * countOfBytesSent The number of bytes that the task has sent to the server in the request body. (read-only)
	 *
	 *  int64_t countOfBytesSent Discussion This byte count includes only the length of the request body itself, not the
	 *                     request headers. To be notified when this value changes, implement the
	 *                     URLSession:task:didSendBodyData:totalBytesSent:totalBytesExpectedToSend: delegate method.
	 */

	public int countOfBytesSent() {
		return countOfBytesSent;
	}

	// Obtaining General Task Information

	/**
	 * currentRequest The URL request object currently being handled by the task. (read-only)
	 *
	 *  NSURLRequest *currentRequest Discussion This value is typically the same as the initial request
	 *                     (originalRequest) except when the server has responded to the initial request with a redirect to a different URL.
	 */

	public NSURLRequest currentRequest() {
		return (NSURLRequest) currentRequest.copy();
	}

	/**
	 * error An error object that indicates why the task failed. (read-only)
	 *
	 *  NSError *error Discussion This value is NULL if the task is still active or if the transfer completed
	 *                     successfully.
	 */

	public NSError error() {
		return (NSError) error.copy();
	}

	/**
	 * originalRequest The original request object passed when the task was created. (read-only)
	 *
	 *  NSURLRequest *originalRequest Discussion This value is typically the same as the currently active request
	 *                     (currentRequest) except when the server has responded to the initial request with a redirect to a different URL.
	 */

	public NSURLRequest originalRequest() {
		return (NSURLRequest) originalRequest.copy();
	}

	/**
	 * response The server’s response to the currently active request. (read-only)
	 *
	 *  NSURLResponse *response Discussion This object provides information about the request as provided by the
	 *                     server. This information always includes the original URL. It may also include an expected length, MIME type
	 *                     information, encoding information, a suggested filename, or a combination of these.
	 */

	public NSURLResponse response() {
		return (NSURLResponse) response.copy();
	}

	/**
	 * taskDescription An app-provided description of the current task.
	 *
	 *  NSString *taskDescription
	 * @Discussion This value may be nil. It is intended to contain human-readable strings that you can then display to the user as part of
	 *             your app’s user interface.
	 */

	public NSString taskDescription() {
		return new NSString(taskDescription.getWrappedString());
	}

	/**
	 * taskIdentifier An identifier uniquely identifies the task within a given session. (read-only)
	 *
	 *  NSUInteger taskIdentifier
	 * @Discussion This value is unique only within the context of a single session; tasks in other sessions may have the same
	 *             taskIdentifier value.
	 */

	public int taskIdentifier() {
		return taskIdentifier;
	}

}