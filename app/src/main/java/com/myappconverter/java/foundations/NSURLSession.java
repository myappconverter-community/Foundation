
package com.myappconverter.java.foundations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myappconverter.java.foundations.protocols.NSURLSessionDelegate;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.PerformBlock;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;


public class NSURLSession extends NSObject {

    public NSURLSession() {
        super();
    }

    // Enumeration

    public static enum NSURLSessionTaskState {
        NSURLSessionTaskStateRunning, //
        NSURLSessionTaskStateSuspended, //
        NSURLSessionTaskStateCanceling, //
        NSURLSessionTaskStateCompleted
    };


    public static enum NSURLSessionResponseDisposition {
        NSURLSessionResponseCancel, //
        NSURLSessionResponseAllow, //
        NSURLSessionResponseBecomeDownload;
    }


    // FIXME must recode all this class

    NSURLSession urlSession;
    String taskType;

    DataTask dataTask;
    UploadTask uploadTask;
    DownloadTask downloadTask;

    List<DataTask> dataTasks;
    List<UploadTask> uploadTasks;
    List<DownloadTask> downloadTasks;

    public DataTask getDataTask() {
        return dataTask;
    }

    public void setDataTask(DataTask dataTask) {
        this.dataTask = dataTask;
    }

    public UploadTask getUploadTask() {
        return uploadTask;
    }

    public void setUploadTask(UploadTask uploadTask) {
        this.uploadTask = uploadTask;
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public void setDownloadTask(DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    /**
     * A copy of the configuration object for this session. (read-only)
     *
     *  NSURLSessionConfiguration *configuration
     * @Discussion Changing mutable values within the configuration object has no effect on the current session, but you can create a new
     *             session with the modified configuration object.
     */

    NSURLSessionConfiguration configuration;


    public NSURLSessionConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * The delegate assigned when this object was created. (read-only)
     *
     *  id<NSURLSessionDelegate> delegate
     * @Discussion This delegate object is responsible for handling authentication challenges, for making caching decisions, and for
     *             handling other session-related events.
     **/

    NSURLSessionDelegate delegate;


    public NSURLSessionDelegate getDelegate() {
        return delegate;
    }

    /**
     * The operation queue provided when this object was created. (read-only)
     *
     *  NSOperationQueue *delegateQueue
     * @Discussion All delegate method calls and completion handlers related to the session are performed on this queue.
     **/
    NSOperationQueue queue;


    public NSOperationQueue getQueue() {
        return queue;
    }

    /**
     * An app-defined descriptive label for the session.
     *
     *  NSString *sessionDescription
     * @Discussion This property contains human-readable strings that you can display to the user as part of your app’s user interface. This
     *             value may be nil.
     **/
    NSString sessionDescription;


    public NSString getSessionDescription() {
        return sessionDescription;
    }

    public NSURLSession(NSURLSessionConfiguration configuration) {
        super();
        this.configuration = configuration;
    }

    public NSURLSession(NSURLSessionConfiguration configuration, NSURLSessionDelegate delegate, NSOperationQueue queue) {
        super();
        this.configuration = configuration;
        this.delegate = delegate;
        this.queue = queue;
    }

    /**
     * @Declaration : + (NSURLSession *)sessionWithConfiguration:(NSURLSessionConfiguration *)configuration
     * @Description : Creates a session with the specified session configuration.
     * @param configuration A configuration object that specifies certain behaviors, such as caching policies, timeouts, proxies,
     *            pipelining, TLS versions to support, cookie policies, credential storage, and so on. For more information, see
     *            NSURLSessionConfiguration Class Reference.
     * @Discussion If this method is used, the session creates a serial NSOperationQueue object on which to perform all delegate method
     *             calls and completion handler calls.
     **/


    public static NSURLSession sessionWithConfiguration(NSURLSessionConfiguration configuration) {
        return new NSURLSession();
    }

    /**
     * @Declaration : + (NSURLSession *)sessionWithConfiguration:(NSURLSessionConfiguration *)configuration delegate:(id
     *              <NSURLSessionDelegate>)delegate delegateQueue:(NSOperationQueue *)queue
     * @Description : Creates a session with the specified session configuration, delegate, and operation queue.
     * @param configuration A configuration object that specifies certain behaviors, such as caching policies, timeouts, proxies,
     *            pipelining, TLS versions to support, cookie policies, and credential storage. Because the session copies the configuration
     *            object, it is safe to modify the configuration object and use it to construct additional sessions. For more information,
     *            see NSURLSessionConfiguration Class Reference.
     * @param delegate A session delegate object that handles requests for authentication and other session-related events. This delegate
     *            object is responsible for handling authentication challenges, for making caching decisions, and for handling other
     *            session-related events. If nil, the class uses a system-provided delegate and should be used only with methods that take
     *            completion handlers. Important: The session object keeps a strong reference to the delegate until your app explicitly
     *            invalidates the session. If you do not invalidate the session by calling the invalidateAndCancel or
     *            resetWithCompletionHandler: method, your app leaks memory.
     * @param queue A queue for scheduling the delegate calls and completion handlers. If nil, the session creates a serial operation queue
     *            for performing all delegate method calls and completion handler calls.
     **/


    public static NSURLSession sessionWithConfigurationDelegateDelegateQueue(NSURLSessionConfiguration configuration, Object delegate,
                                                                             NSOperationQueue queue) {
        return new NSURLSession();
    }

    /**
     * @Declaration : + (NSURLSession *)sharedSession
     * @Description : Returns a shared singleton session object.
     * @Discussion The shared session uses the currently set global NSURLCache, NSHTTPCookieStorage, and NSURLCredentialStorage objects and
     *             is based on the default configuration.
     **/

    public static NSURLSession sharedSession() {
        // return urlSession;
        return null;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithURL:(NSURL *)url
     * @Description : Creates a download task for the specified URL and saves the results to a file.
     * @param url An NSURL object that provides the URL to download.
     * @return Return Value The new session download task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public DataTask dataTaskWithURL(URL url) {
        DataTask dataTask = new DataTask(url);
        Thread thread = new Thread(dataTask);
        thread.start();
        dataTasks.add(dataTask);
        return dataTask;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithURL:(NSURL *)url completionHandler:(void (^)(NSURL *location,
     *              NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates a download task for the specified URL, saves the results to a file, and calls a handler upon completion.
     * @param url An NSURL object that provides the URL to download.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter must not be nil, because there is no other way to
     *            retrieve the response data.
     * @return Return Value The new session download task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public DataTask dataTaskWithURLCompletionHandler(URL url, PerformBlock.VoidBlockNSURLNSURLResponseNSError completionHandler) {
        DataTask dataTask = new DataTask(url);
        Thread thread = new Thread(dataTask);
        thread.start();
        dataTasks.add(dataTask);
        return dataTask;
    }

    /**
     * @Declaration : - (NSURLSessionDataTask *)dataTaskWithRequest:(NSURLRequest *)request
     * @Description : Creates an HTTP request based on the specified URL request object.
     * @param request An object that provides request-specific information such as the URL, cache policy, request type, and body data or
     *            body stream.
     * @return Return Value The new session data task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public NSURLSessionDataTask dataTaskWithRequest(NSURLRequest request) {
        return new NSURLSessionDataTask(request.URL);
    }

    /**
     * @Declaration : - (NSURLSessionDataTask *)dataTaskWithRequest:(NSURLRequest *)request completionHandler:(void (^)(NSData *data,
     *              NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates an HTTP request for the specified URL request object, and calls a handler upon completion.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, body data or body stream, and so on.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter must not be nil, because there is no other way to
     *            retrieve the response data.
     * @return Return Value The new session data task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public NSURLSessionDataTask dataTaskWithRequestCompletionHandler(NSURLRequest request, final PerformBlock.VoidBlockNSDataNSURLResponseNSError completionHandler) {
        RequestQueue queue = Volley.newRequestQueue(GenericMainContext.sharedContext);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, request.URL.getUrlString().getWrappedString(), new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            completionHandler.perform(new NSData(response.toString().getBytes("UTF-8")),
                                    new NSURLResponse().initWithJSONResponse(response), new NSError());
                        } catch (UnsupportedEncodingException e) {
                            Log.d(NSURLSession.class.getSimpleName(), String.valueOf(e));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonRequest);

        return new NSURLSessionDataTask(request.URL);
    }

    /**
     * @Declaration : - (NSURLSessionDataTask *)dataTaskWithURL:(NSURL *)url
     * @Description : Creates an HTTP GET request for the specified URL.
     * @param url The http or https URL to be retrieved.
     * @return Return Value The new session data task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public DownloadTask downloadTaskWithURL(URL url) {
        downloadTask = new DownloadTask(url);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionDataTask *)dataTaskWithURL:(NSURL *)url completionHandler:(void (^)(NSData *data, NSURLResponse
     *              *response, NSError *error))completionHandler
     * @Description : Creates an HTTP GET request for the specified URL, then calls a handler upon completion.
     * @param url The http or https URL to be retrieved.
     * @param completionHandler The completion handler to call when the load request is complete. If sent to a session created by calling
     *            sessionWithConfiguration:delegate:delegateQueue: with a non-nil value for the delegateQueue parameter, this handler is
     *            executed on that delegate queue. Unless you have provided a custom delegate, this parameter must not be nil, because there
     *            is no other way to retrieve the response data.
     * @return Return Value The new session data task.
     * @Discussion After you create the task, you must start it by calling its resume method. Note: This method is intended as an
     *             alternative to the sendAsynchronousRequest:queue:completionHandler: method of NSURLConnection, with the added ability to
     *             support custom authentication and cancellation.
     **/


    public DownloadTask downloadTaskWithURLCompletionHandler(URL url, PerformBlock.VoidBlockNSDataNSURLResponseNSError completionHandler) {
        downloadTask = new DownloadTask(url);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithRequest:(NSURLRequest *)request
     * @Description : Creates a download task for the specified URL request and saves the results to a file.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, body data or body stream, and so on.
     * @return Return Value The new session download task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public DownloadTask downloadTaskWithRequest(NSURLRequest request) {
        NSURL nsURL = request.URL();
        URL url = nsURL.getUrl();
        downloadTask = new DownloadTask(url);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithRequest:(NSURLRequest *)request completionHandler:(void (^)(NSURL
     *              *location, NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates a download task for the specified URL request, saves the results to a file, and calls a handler upon
     *              completion.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, body data or body stream, and so on.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter must not be nil, because there is no other way to
     *            retrieve the response data.
     * @return Return Value The new session download task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public DownloadTask downloadTaskWithRequestCompletionHandler(NSURLRequest request, PerformBlock.VoidBlockNSURLNSURLResponseNSError completionHandler) {
        NSURL nsURL = request.URL();
        URL url = nsURL.getUrl();
        downloadTask = new DownloadTask(url);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithResumeData:(NSData *)resumeData
     * @Description : Creates a download task to resume a previously canceled or failed download.
     * @param resumeData A data object that provides the data necessary to resume a download.
     * @return Return Value The new session download task.
     * @Discussion Your app can obtain a resumeData object in two ways: If your app cancels an existing transfer by calling
     *             cancelByProducingResumeData:, the session object passes a resumeData object to the completion handler that you provided
     *             in that call. If a transfer fails, the session object provides an NSError object either to your delegate or to the task’s
     *             completion handler. In that object, the NSURLSessionDownloadTaskResumeData key in the userInfo dictionary contains a
     *             resumeData object. After you create the task, you must start it by calling its resume method.
     **/


    public DownloadTask downloadTaskWithResumeData(NSData resumeData) {
        downloadTask = new DownloadTask(resumeData);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionDownloadTask *)downloadTaskWithResumeData:(NSData *)resumeData completionHandler:(void (^)(NSURL
     *              *location, NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates a download task to resume a previously canceled or failed download and calls a handler upon completion.
     * @param resumeData A data object that provides the data necessary to resume the download.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter must not be nil, because there is no other way to
     *            retrieve the response data.
     * @return Return Value The new session download task.
     * @Discussion Your app can obtained a resumeData object in two ways: If your app cancels the transfer explicitly by calling
     *             cancelByProducingResumeData:, the session object passes a resumeData object to the completion handler that you provided
     *             in that call. If a transfer fails, the session object provides an NSError object either to its delegate or to the task’s
     *             completion handler. In that object, the NSURLSessionDownloadTaskResumeData key in the userInfo dictionary contains a
     *             resumeData object. After you create the task, you must start it by calling its resume method.
     **/


    public DownloadTask downloadTaskWithResumeData(NSData resumeData, PerformBlock.VoidBlockNSURLNSURLResponseNSError completionHandler) {
        downloadTask = new DownloadTask(resumeData);
        downloadTasks.add(downloadTask);
        return downloadTask;
    }

    /**
     * @Declaration : - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromData:(NSData *)bodyData
     * @Description : Creates an HTTP request for the specified URL request object and uploads the provided data object.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, and so on. The body stream and body data in
     *            this request object are ignored.
     * @param bodyData The body data for the request.
     * @return Return Value The new session upload task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public UploadTask uploadTaskWithRequestFromData(NSURLRequest request, NSData bodyData) {
        URLConnection urlConn;
        try {
            urlConn = request.URL().getWrappedURL().openConnection();
            uploadTask = new UploadTask((HttpURLConnection) urlConn, bodyData);
            uploadTasks.add(uploadTask);
            return uploadTask;
        } catch (IOException e) {
            Logger.getLogger(NSURLSession.class.getName(),String.valueOf(e));
        }
        return null;
    }

    /**
     * @Declaration : - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromData:(NSData *)bodyData
     *              completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates an HTTP request for the specified URL request object, uploads the provided data object, and calls a handler
     *              upon completion.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, and so on. The body stream and body data in
     *            this request object are ignored.
     * @param bodyData The body data for the request.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter should not be nil, because there is no other way to
     *            retrieve the response data. If you do not need the response data, use key-value observing to watch for changes to the
     *            task’s status to determine when it completes.
     * @return Return Value The new session upload task.
     * @Discussion Unlike uploadTaskWithRequest:fromData:, this method returns the response body after it has been received in full, and
     *             does not require you to write a custom delegate to obtain the response body. After you create the task, you must start it
     *             by calling its resume method.
     **/


    public UploadTask uploadTaskWithRequestFromDataCompletionHandler(NSURLRequest request, NSData bodyData,
                                                                     PerformBlock.VoidBlockNSDataNSURLResponseNSError completionHandler) {
        URLConnection urlConn;
        try {
            urlConn = request.URL().getWrappedURL().openConnection();
            uploadTask = new UploadTask((HttpURLConnection) urlConn, bodyData);
            uploadTasks.add(uploadTask);
            return uploadTask;
        } catch (IOException e) {
            Logger.getLogger(NSURLSession.class.getName(), String.valueOf(e));
        }
        return null;
    }

    /**
     * @Declaration : - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromFile:(NSURL *)fileURL
     * @Description : Creates an HTTP request for uploading the specified file URL.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, and so on. The body stream and body data in
     *            this request object are ignored.
     * @param fileURL The URL of the file to upload.
     * @return Return Value The new session upload task.
     * @Discussion After you create the task, you must start it by calling its resume method.
     **/


    public UploadTask uploadTaskWithRequest(NSURLRequest request, NSURL fileURL) {
        URLConnection urlConn;
        try {
            urlConn = request.URL().getWrappedURL().openConnection();

            uploadTask = new UploadTask((HttpURLConnection) urlConn, fileURL);
            uploadTasks.add(uploadTask);
            return uploadTask;
        } catch (IOException e) {
            Logger.getLogger(NSURLSession.class.getName(), String.valueOf(e));
        }
        return null;
    }

    /**
     * @Declaration : - (NSURLSessionUploadTask *)uploadTaskWithRequest:(NSURLRequest *)request fromFile:(NSURL *)fileURL
     *              completionHandler:(void (^)(NSData *data, NSURLResponse *response, NSError *error))completionHandler
     * @Description : Creates an HTTP request for uploading the specified file URL, then calls a handler upon completion.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, and so on. The body stream and body data in
     *            this request object are ignored.
     * @param fileURL The URL of the file to upload.
     * @param completionHandler The completion handler to call when the load request is complete. This handler is executed on the delegate
     *            queue. Unless you have provided a custom delegate, this parameter must not be nil, because there is no other way to
     *            retrieve the response data.
     * @return Return Value The new session upload task.
     * @Discussion Unlike uploadTaskWithRequest:fromFile:, this method returns the response body after it has been received in full, and
     *             does not require you to write a custom delegate to obtain the response body. After you create the task, you must start it
     *             by calling its resume method.
     **/


    public UploadTask uploadTaskWithRequest(NSURLRequest request, NSURL fileURL, PerformBlock.VoidBlockNSDataNSURLResponseNSError completionHandler) {
        URLConnection urlConn;
        try {
            urlConn = request.URL().getWrappedURL().openConnection();
            uploadTask = new UploadTask((HttpURLConnection) urlConn, fileURL);
            uploadTasks.add(uploadTask);
            return uploadTask;
        } catch (IOException e) {
            Logger.getLogger(NSURLSession.class.getName(), String.valueOf(e));
        }
        return null;
    }

    /**
     * @Declaration : - (NSURLSessionUploadTask *)uploadTaskWithStreamedRequest:(NSURLRequest *)request
     * @Description : Creates an HTTP request for uploading data based on the specified URL request.
     * @param request An NSURLRequest object that provides the URL, cache policy, request type, and so on. The body stream and body data in
     *            this request object are ignored, and NSURLSession calls its delegate’s URLSession:task:needNewBodyStream: method to
     *            provide the body data.
     * @return Return Value The new session upload task.
     * @Discussion After you create the task, you must start it by calling its resume method. The upload task’s delegate must have a
     *             URLSession:task:needNewBodyStream: method that provides the body data to upload.
     **/


    public UploadTask uploadTaskWithStreamedRequest(NSURLRequest request) {
        return null;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String fileName;
        NSData data;
        private Context context;
        URL url;
        FileOutputStream fos = null;

        public DownloadTask(NSData data) {
            super();
            this.data = data;
        }

        public DownloadTask(Context context) {
            this.context = context;
        }

        public DownloadTask(URL url) {
            super();
            this.url = url;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            HttpURLConnection requestection = null;
            FileOutputStream fos = null;
            File file = new File(fileName);
            try {
                requestection = (HttpURLConnection) url.openConnection();
                requestection.connect();
                InputStream is = requestection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(1024);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
            } catch (IOException e) {

                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                }
            }

            return null;

        }

        public void download() {
            HttpURLConnection requestection = null;

            File file = new File(fileName);
            try {
                requestection = (HttpURLConnection) url.openConnection();
                requestection.setRequestProperty("method", "GET");

                if (this.isCancelled()) {
                    requestection.setRequestProperty("Range", "bytes=" + requestection.getInputStream().available() + "-" + data);
                }
                requestection.connect();
                InputStream is = requestection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(1024);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }
                fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());

            } catch (IOException e) {

                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                }
            }

        }

        public void flushData() {
            try {
                fos.flush();
            } catch (IOException e) {

                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
    }

    private class UploadTask extends AsyncTask<String, Integer, String> {

        String fileName;
        NSURL url;
        HttpURLConnection request;
        NSData data;

        public UploadTask(HttpURLConnection request, NSURL url) {
            this.request = request;
            this.url = url;
        }

        public UploadTask(HttpURLConnection request, NSData data) {
            this.request = request;
            this.data = data;

        }

        @Override
        protected String doInBackground(String... arg0) {

            return null;
        }

        public void uploadFile() throws IOException {
            String fileName = url.path().toString();

            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            FileInputStream fileInputStream = new FileInputStream(fileName);

            // Open a HTTP connection to the URL
            request = (HttpURLConnection) url.getUrl().openConnection();
            request.setDoInput(true); // Allow Inputs
            request.setDoOutput(true); // Allow Outputs
            request.setUseCaches(false); // Don't use a Cached Copy
            request.setChunkedStreamingMode(1024);
            request.setRequestMethod("POST");
            request.setRequestProperty("requestection", "Keep-Alive");
            request.setRequestProperty("ENCTYPE", "multipart/form-data");
            request.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            request.setRequestProperty("uploaded_file", fileName);

            dos = new DataOutputStream(request.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        }

        public void uploadData() throws IOException {
            DataOutputStream dos;

            request.setDoInput(true); // Allow Inputs
            request.setDoOutput(true); // Allow Outputs
            request.setUseCaches(false); // Don't use a Cached Copy
            request.setChunkedStreamingMode(1024);
            request.setRequestMethod("POST");
            request.setRequestProperty("connection", "Keep-Alive");
            request.setRequestProperty("ENCTYPE", "multipart/form-data");

            dos = new DataOutputStream(request.getOutputStream());
            byte[] databyte = data.bytes();

            dos.write(databyte);

            dos.flush();
            dos.close();

        }

    }

    private class DataTask implements Runnable {
        URL url;
        URLConnection URLrequestection = null;

        public DataTask(URL url) {
            this.url = url;
        }

        public void taskData() {
            if ("https".equals(url.getProtocol())) {
                HttpsURLConnection httpsUrlConnection;
                try {

                    URLrequestection = url.openConnection();
                    httpsUrlConnection = (HttpsURLConnection) URLrequestection;
                    httpsUrlConnection.setRequestMethod("GET");
                } catch (IOException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                }

            } else {
                URLConnection URLrequestection;
                HttpURLConnection httpUrlConnection;
                try {
                    URLrequestection = url.openConnection();
                    httpUrlConnection = (HttpURLConnection) URLrequestection;
                    httpUrlConnection.setRequestMethod("GET");

                } catch (IOException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                }

            }

        }

        @Override
        public void run() {
            taskData();
        }
    }

    public void invalidate() {

        if (urlSession != null)
            urlSession = null;

    }

    /**
     * @Declaration : - (void)finishTasksAndInvalidate
     * @Description : Invalidates the object, allowing any outstanding tasks to finish.
     * @Discussion This method returns immediately without waiting for tasks to finish. Once a session is invalidated, new tasks cannot be
     *             created in the session, but existing tasks continue until completion. After the last task finishes and the session makes
     *             the last delegate call, references to the delegate and callback objects are broken. Session objects cannot be reused. To
     *             cancel all outstanding tasks, call invalidateAndCancel instead.
     **/

    public void finishTasksAndInvalidate() {

        // if (taskType.equals("TaskData")) {
        // // FIXME ??
        // dataTask.cancel(false);
        // invalidate();
        // } else if (taskType.equals("UploadData")) {
        // uploadTask.cancel(false);
        // invalidate();
        // } else {
        // downloadTask.cancel(false);
        // invalidate();
        // }
    }

    /**
     * @Declaration : - (void)invalidateAndCancel
     * @Description : Cancels all outstanding tasks and then invalidates the session object.
     * @Discussion Once invalidated, references to the delegate and callback objects are broken. Session objects cannot be reused. To allow
     *             outstanding tasks to run until completion, call finishTasksAndInvalidate instead.
     **/

    public void invalidateAndCancel() {
        // FIXME ???
        // dataTask.cancel(false);
        // uploadTask.cancel(false);
        // downloadTask.cancel(false);
        // invalidate();
    }

    /**
     * @Declaration : - (void)flushWithCompletionHandler:(void (^)(void))completionHandler
     * @Description : Ensures that future requests occur on a new socket and that any in-transit download data is flushed to disk.
     * @param completionHandler The completion handler to call when the flush operation is complete. This handler is executed on the
     *            delegate queue.
     **/


    public void flushWithCompletionHandler(PerformBlock.VoidBlockVoid completionHandler) {
        this.downloadTask.flushData();
    }

    /**
     * @Declaration : - (void)getTasksWithCompletionHandler:(void (^)(NSArray *dataTasks, NSArray *uploadTasks, NSArray
     *              *downloadTasks))completionHandler
     * @Description : Asynchronously calls a completion callback with all outstanding data, upload, and download tasks in a session.
     * @param completionHandler The completion handler to call with the list of currently outstanding tasks. This handler is executed on the
     *            delegate queue.
     **/


    public void getTasksWithCompletionHandler(PerformBlock.VoidBlockNSArrayNSArrayNSArray completionHandler) {

        List<DataTask> dataTasks = new ArrayList<NSURLSession.DataTask>();
        List<DownloadTask> downloadTasks = new ArrayList<NSURLSession.DownloadTask>();
        List<UploadTask> uploadTasks = new ArrayList<NSURLSession.UploadTask>();

        dataTasks = this.dataTasks;
        downloadTasks = this.downloadTasks;
        uploadTasks = this.uploadTasks;

    }

    /**
     * @Declaration : - (void)resetWithCompletionHandler:(void (^)(void))completionHandler
     * @Description : Resets the session asynchronously.
     * @param completionHandler The completion handler to call when the reset operation is complete. This handler is executed on the
     *            delegate queue.
     * @Discussion This method empties all cookies, caches and credential stores, removes disk files, flushes in-progress downloads to disk,
     *             and ensures that future requests occur on a new socket.
     **/


    public void resetWithCompletionHandler(PerformBlock.VoidBlockVoid completionHandler) {
        urlSession.invalidate();
        urlSession = new NSURLSession(configuration);
    }

    public class NSURLSessionBlock implements PerformBlock.VoidBlockNSDataNSURLResponseNSError {
        @Override
        public void perform(NSData data, NSURLResponse response, NSError error) {

        }
    }
}