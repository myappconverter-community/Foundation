package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSURL;
import com.myappconverter.java.foundations.NSURLConnection;


public abstract class NSURLConnectionDownloadDelegate {
	/**
	 * @Signature: connection:didWriteData:totalBytesWritten:expectedTotalBytes:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didWriteData:(long long)bytesWritten totalBytesWritten:(long
	 *              long)totalBytesWritten expectedTotalBytes:(long long)expectedTotalBytes
	 * @Description : Sent to the delegate to deliver progress information for a download of a URL asset to a destination file.
	 * @param connection The URL connection object downloading the asset.
	 * @param bytesWritten The number of bytes written since the last call of this method.
	 * @param totalBytesWritten The total number of bytes of the downloading asset that have been written to the file.
	 * @param expectedTotalBytes The total number of bytes of the URL asset once it is completely downloaded and written to a file. This
	 *            parameter can be zero if the total number of bytes is not known.
	 * @Discussion This method is invoked repeatedly during the download of a URL asset to the destination file. The delegate typically uses
	 *             the values of the three “bytes” parameters to update a progress indicator in the application’s user interface.
	 **/

	public abstract void connectionDidWriteDataTotalBytesWrittenExpectedTotalBytes(NSURLConnection connection, long bytesWritten,
			long totalBytesWritten, long expectedTotalBytes);

	/**
	 * @Signature: connectionDidFinishDownloading:destinationURL:
	 * @Declaration : - (void)connectionDidFinishDownloading:(NSURLConnection *)connection destinationURL:(NSURL *)destinationURL
	 * @Description : Sent to the delegate when the URL connection has successfully downloaded the URL asset to a destination file.
	 * @param connection The URL connection object that downloaded the asset.
	 * @param destinationURL A file URL specifying a destination in the file system. For iOS applications, this is a location in the
	 *            application sandbox.
	 * @Discussion This method will be called once after a successful download. The file downloaded to destinationURL is guaranteed to exist
	 *             there only for the duration of this method implementation; the delegate should copy or move the file to a more persistent
	 *             and appropriate location.
	 **/

	public abstract void connectionDidFinishDownloadingDestinationURL(NSURLConnection connection, NSURL destinationURL);

	/**
	 * @Signature: connectionDidResumeDownloading:totalBytesWritten:expectedTotalBytes:
	 * @Declaration : - (void)connectionDidResumeDownloading:(NSURLConnection *)connection totalBytesWritten:(long long)totalBytesWritten
	 *              expectedTotalBytes:(long long)expectedTotalBytes
	 * @Description : Sent to the delegate when an URL connection resumes downloading a URL asset that was earlier suspended.
	 * @param connection The URL connection object downloading the asset.
	 * @param totalBytesWritten The total number of bytes of the downloading asset that have been written to the destination file.
	 * @param expectedTotalBytes The total number of bytes of the URL asset once it is completely downloaded and written to a file.
	 * @Discussion This method is invoked once a suspended download of a URL asset resumes downloading. In response, the delegate can
	 *             display a progress indicator, setting the initial value of the indicator to where it was when downloading was suspended.
	 *             After the URL-connection object sends this message, it sends one or more
	 *             connection:didWriteData:totalBytesWritten:expectedTotalBytes: to the delegate until the download concludes.
	 **/

	public abstract void connectionDidResumeDownloadingTotalBytesWrittenExpectedTotalBytes(NSURLConnection connection,
			long totalBytesWritten, long expectedTotalBytes);

}
