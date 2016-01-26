
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSURLSessionDownloadTask.NSURLSessionDownloadTaskBlock.cancelByProducingResumeDataBlock;


public class NSURLSessionDownloadTask extends NSURLSessionTask {

	public static interface NSURLSessionDownloadTaskBlock {
		static interface cancelByProducingResumeDataBlock {
			void perform(NSData resumeData);
		}
	}

	// 5916
	/**
	 * @Signature: cancelByProducingResumeData:
	 * @Declaration : - (void)cancelByProducingResumeData:(void (^)(NSData *resumeData))completionHandler
	 * @Description : Cancels a download and calls a callback with resume data for later use.
	 * @Discussion A download can be resumed only if the following conditions are met: The resource has not changed since you first
	 *             requested it The task is an HTTP or HTTPS GET request The server provides either the ETag or Last-Modified header (or
	 *             both) in its response The server supports byte-range requests The temporary file hasn’t been deleted by the system in
	 *             response to disk space pressure
	 **/

	public void cancelByProducingResumeData(cancelByProducingResumeDataBlock completionHandler) {
	}

	@Override
	public Object copyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void suspend() {
	}

}