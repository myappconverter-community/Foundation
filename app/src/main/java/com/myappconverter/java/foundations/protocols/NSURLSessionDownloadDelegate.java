package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSURL;
import com.myappconverter.java.foundations.NSURLSession;
import com.myappconverter.java.foundations.NSURLSessionDownloadTask;


public abstract class NSURLSessionDownloadDelegate {

	// 1
	/**
	 * @Signature: URLSession:downloadTask:didFinishDownloadingToURL:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session downloadTask:(NSURLSessionDownloadTask *)downloadTask
	 *              didFinishDownloadingToURL:(NSURL *)location
	 * @Description : Tells the delegate that a download task has finished downloading. (required)
	 **/

	public abstract void URLSessionDownloadTaskDidFinishDownloadingToURL(NSURLSession session, NSURLSessionDownloadTask downloadTask,
			NSURL location);

	// 2
	/**
	 * @Signature: URLSession:downloadTask:didResumeAtOffset:expectedTotalBytes:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session downloadTask:(NSURLSessionDownloadTask *)downloadTask
	 *              didResumeAtOffset:(int64_t)fileOffset expectedTotalBytes:(int64_t)expectedTotalBytes
	 * @Description : Tells the delegate that the download task has resumed downloading. (required)
	 * @Discussion If a resumable download task is canceled or fails, you can request a resumeData object that provides enough information
	 *             to restart the download in the future. Later, you can call downloadTaskWithResumeData: or
	 *             downloadTaskWithResumeData:completionHandler: with that data. When you call those methods, you get a new download task.
	 *             As soon as you resume that task, the session calls its delegate’s
	 *             URLSession:downloadTask:didResumeAtOffset:expectedTotalBytes: method with that new task to indicate that the download is
	 *             resumed.
	 **/

	public abstract void URLSessionDownloadTaskDidResumeAtOffsetExpectedTotalBytes(NSURLSession session,
			NSURLSessionDownloadTask downloadTask, long fileOffset, long expectedTotalBytes);

	// 3
	/**
	 * @Signature: URLSession:downloadTask:didWriteData:totalBytesWritten:totalBytesExpectedToWrite:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session downloadTask:(NSURLSessionDownloadTask *)downloadTask
	 *              didWriteData:(int64_t)bytesWritten totalBytesWritten:(int64_t)totalBytesWritten
	 *              totalBytesExpectedToWrite:(int64_t)totalBytesExpectedToWrite
	 * @Description : Periodically informs the delegate about the download’s progress. (required)
	 **/
	public abstract void URLSessionDownloadTaskDidWriteDataTotalBytesWrittenTotalBytesExpectedToWrite(NSURLSession session,
			NSURLSessionDownloadTask downloadTask, long bytesWritten, long totalBytesWritten, long totalBytesExpectedToWrite);

}
