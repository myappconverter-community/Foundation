
package com.myappconverter.java.foundations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myappconverter.java.foundations.constants.NSStringEncoding;
import com.myappconverter.java.foundations.protocols.NSURLConnectionDataDelegate;
import com.myappconverter.java.foundations.protocols.NSURLConnectionDelegate;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InvokableHelper;
import com.myappconverter.mapping.utils.PerformBlock;


public class NSURLConnection extends NSObject {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	JsonObjectRequest jsonRequest;
	private NSURLRequest request;

	public void addListener(String url, final Object delegate) {
		RequestQueue queue = Volley.newRequestQueue(GenericMainContext.sharedContext);
		this.jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if (delegate instanceof NSURLConnectionDataDelegate && delegate != null)
							Log.d("NSURLConnection", "OnResponseListener");
						try {
							((NSURLConnectionDataDelegate) delegate).connectionDidReceiveData(null,
									new NSData(response.toString().getBytes("UTF-8")));
						} catch (UnsupportedEncodingException e) {
							LOGGER.info(String.valueOf(e));
						}
						((NSURLConnectionDataDelegate) delegate).connectionDidFinishLoading(null);
						((NSURLConnectionDataDelegate) delegate).connectionDidReceiveResponse(null,
								new NSURLResponse().initWithJSONResponse(response));
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("NSURLConnection", "Response Error", error);
			}
		});
		queue.add(jsonRequest);
	}

	/**
	 * @Signature: canHandleRequest:
	 * @Declaration : + (BOOL)canHandleRequest:(NSURLRequest *)request
	 * @Description : Returns whether a request can be handled based on a preflight evaluation.
	 * @param request The request to evaluate.
	 * @return Return Value YES if a preflight operation determines that a connection with request can be created and the associated I/O can
	 *         be started, NO otherwise.
	 * @Discussion The result of this method is valid as long as no NSURLProtocol classes are registered or unregistered, and request
	 *             remains unchanged. Applications should be prepared to handle failures even if they have performed request preflighting by
	 *             calling this method.
	 **/

	public static boolean canHandleRequest(NSURLRequest request) {
		// not yet covered
		return false;
	}

	// 2
	/**
	 * @Signature: connectionWithRequest:delegate:
	 * @Declaration : + (NSURLConnection *)connectionWithRequest:(NSURLRequest *)request delegate:(id < NSURLConnectionDelegate >)delegate
	 * @Description : Creates and returns an initialized URL connection and begins to load the data for the URL request.
	 * @param request The URL request to load. The request object is deep-copied as part of the initialization process. Changes made to
	 *            request after this method returns do not affect the request that is used for the loading process.
	 * @param delegate The delegate object for the connection. The connection calls methods on this delegate as the load progresses.
	 *            Delegate methods are called on the same thread that called this method. For the connection to work correctly, the calling
	 *            thread’s run loop must be operating in the default run loop mode.
	 * @return Return Value The URL connection for the URL request. Returns nil if a connection can't be created.
	 **/


	public static NSURLConnection connectionWithRequestDelegate(NSURLRequest request,
																Object delegate) {
		NSURLConnection conn = new NSURLConnection();
		if (request.HTTPMethod != null && "POST".equals(request.HTTPMethod.getWrappedString())) {
			postData(request, delegate);
		} else
			conn.addListener(request.URL.getUrlString().getWrappedString(), delegate);
		return conn;
	}

	// 3
	/**
	 * @Signature: sendAsynchronousRequest:queue:completionHandler:
	 * @Declaration : + (void)sendAsynchronousRequest:(NSURLRequest *)request queue:(NSOperationQueue *)queue completionHandler:(void
	 *              (^)(NSURLResponse*, NSData*, NSError*))handler
	 * @Description : Loads the data for a URL request and executes a handler block on an operation queue when the request completes or
	 *              fails.
	 * @param request The URL request to load. The request object is deep-copied as part of the initialization process. Changes made to
	 *            request after this method returns do not affect the request that is used for the loading process.
	 * @param queue The operation queue to which the handler block is dispatched when the request completes or failed.
	 * @param handler The handler block to execute.
	 * @Discussion If the request completes successfully, the data parameter of the handler block contains the resource data, and the error
	 *             parameter is nil. If the request fails, the data parameter is nil and the error parameter contain information about the
	 *             failure. If authentication is required in order to download the request, the required credentials must be specified as
	 *             part of the URL. If authentication fails, or credentials are missing, the connection will attempt to continue without
	 *             credentials.
	 **/


	public static void sendAsynchronousRequestQueueCompletionHandler(NSURLRequest request,
																	 NSOperationQueue queue, PerformBlock.VoidBlockNSURLResponseNSDataNSError block) {
		String line = "";
		StringBuilder sb = new StringBuilder();
		try {
			HttpURLConnection connection = (HttpURLConnection) request.URL().getWrappedURL()
					.openConnection();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonObject = new JSONObject(sb.toString());
			block.perform(new NSURLResponse().initWithJSONResponse(jsonObject),
					new NSData(jsonObject.toString().getBytes("UTF-8")), new NSError());

		} catch (IOException e) {
			LOGGER.info(String.valueOf(e));
		} catch (JSONException e) {
			LOGGER.info(String.valueOf(e));
		}

	}

	// 4
	/**
	 * @Signature: sendSynchronousRequest:returningResponse:error:
	 * @Declaration : + (NSData *)sendSynchronousRequest:(NSURLRequest *)request returningResponse:(NSURLResponse **)response error:(NSError
	 *              **)error
	 * @Description : Performs a synchronous load of the specified URL request.
	 * @param request The URL request to load. The request object is deep-copied as part of the initialization process. Changes made to
	 *            request after this method returns do not affect the request that is used for the loading process.
	 * @param response Out parameter for the URL response returned by the server.
	 * @param error Out parameter used if an error occurs while processing the request. May be NULL.
	 * @return Return Value The downloaded data for the URL request. Returns nil if a connection could not be created or if the download
	 *         fails.
	 * @Discussion A synchronous load is built on top of the asynchronous loading code made available by the class. The calling thread is
	 *             blocked while the asynchronous loading system performs the URL load on a thread spawned specifically for this load
	 *             request. No special threading or run loop configuration is necessary in the calling thread in order to perform a
	 *             synchronous load. Important: Because this call can potentially take several minutes to fail (particularly when using a
	 *             cellular network in iOS), you should never call this function from the main thread of a GUI application. If
	 *             authentication is required in order to download the request, the required credentials must be specified as part of the
	 *             URL. If authentication fails, or credentials are missing, the connection will attempt to continue without credentials.
	 **/


	public static NSData sendSynchronousRequestReturningResponseError(NSURLRequest request,
																	  NSURLResponse[] response, NSError[] error) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: cancel
	 * @Declaration : - (void)cancel
	 * @Description : Cancels an asynchronous load of a request.
	 * @Discussion After this method is called, the connection makes no further delegate method calls. If you want to reattempt the
	 *             connection, you should create a new connection object.
	 **/

	public void cancel() {
		// not yet covered
	}

	/**
	 * @Signature: currentRequest
	 * @Declaration : - (NSURLRequest *)currentRequest
	 * @Description : Returns the current connection request.
	 * @return Return Value Returns the current—possibly modified—connection request.
	 * @Discussion As the connection performs the load, the request may change as a result of protocol canonicalization or due to following
	 *             redirects. This method is be used to retrieve the current value.
	 **/

	public NSURLRequest currentRequest() {
		// not yet covered
		return null;
	}

	// 7
	/**
	 * @Signature: initWithRequest:delegate:
	 * @Declaration : - (id)initWithRequest:(NSURLRequest *)request delegate:(id < NSURLConnectionDelegate >)delegate
	 * @Description : Returns an initialized URL connection and begins to load the data for the URL request.
	 * @param request The URL request to load. The request object is deep-copied as part of the initialization process. Changes made to
	 *            request after this method returns do not affect the request that is used for the loading process.
	 * @param delegate The delegate object for the connection. The connection calls methods on this delegate as the load progresses.
	 *            Delegate methods are called on the same thread that called this method. By default, for the connection to work correctly,
	 *            the calling thread’s run loop must be operating in the default run loop mode. See scheduleInRunLoop:forMode: to change the
	 *            run loop and mode.
	 * @return Return Value The URL connection for the URL request. Returns nil if a connection can't be initialized.
	 * @Discussion This is equivalent to calling initWithRequest:delegate:startImmediately: and passing YES for startImmediately.
	 **/

	public NSURLConnection initWithRequestDelegate(final NSURLRequest request, final NSURLConnectionDelegate delegate) {
		this.request = request;
		AsyncTask<Void, Void, String> startRequets = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {

				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(request.URL().getWrappedURL().toString());

				try {
					// Execute HTTP Post Request
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httppost, responseHandler);
					if (responseBody != null && delegate!=null) {
						final NSURLResponse response = new NSURLResponse();
						response.url=request.URL;
						GenericMainContext.sharedContext.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								delegate.connectionDidReceiveResponse(NSURLConnection.this, response);
							}
						});
					}
					return responseBody;

				} catch (Exception e) {
					LOGGER.info(String.valueOf(e));
				}
				return null;
			}

			@Override
			protected void onPostExecute(String response) {
				// run on UI Thread
				if (delegate != null)
					try {
						delegate.connectionDidReceiveData(
								new NSURLConnection(), new NSData(response.getBytes()));
						delegate.connectionDidFinishLoading(
								new NSURLConnection());
					} catch (Exception e) {
						Log.d("NSURLConnection", "", e);
					}
				if (response == null && delegate!=null) {
					delegate.connectionDidFailWithError(NSURLConnection.this,new NSError());
				}
			}

			@Override
			protected void onPreExecute() {
			}
		};
		try {
			startRequets.execute();
			return this;
		}catch(Exception ex){
			LOGGER.info(String.valueOf(ex));
			return  null;
		}
	}

	/**
	 * @Signature: initWithRequest:delegate:startImmediately:
	 * @Declaration : - (id)initWithRequest:(NSURLRequest *)request delegate:(id < NSURLConnectionDelegate >)delegate
	 *              startImmediately:(BOOL)startImmediately
	 * @Description : Returns an initialized URL connection and begins to load the data for the URL request, if specified.
	 * @param request The URL request to load. The request object is deep-copied as part of the initialization process. Changes made to
	 *            request after this method returns do not affect the request that is used for the loading process.
	 * @param delegate The delegate object for the connection. The connection calls methods on this delegate as the load progresses.
	 * @param startImmediately YES if the connection should begin loading data immediately, otherwise NO. If you pass NO, the connection is
	 *            not scheduled with a run loop. You can then schedule the connection in the run loop and mode of your choice by calling
	 *            scheduleInRunLoop:forMode:.
	 * @return Return Value The URL connection for the URL request. Returns nil if a connection can't be initialized.
	 **/

	public Object initWithRequestDelegateStartImmediately(NSURLRequest request,
														  NSURLConnectionDelegate delegate, boolean startImmediately) {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: originalRequest
	 * @Declaration : - (NSURLRequest *)originalRequest
	 * @Description : Returns a deep copy of the original connection request.
	 * @return Return Value Returns a deep copy of the original connection request.
	 * @Discussion As the connection performs the load, this request may change as a result of protocol canonicalization or due to following
	 *             redirects. The currentRequest method can be used to retrieve this value.
	 **/

	public NSURLRequest originalRequest() {
		// not yet covered
		return null;
	}

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Determines the run loop and mode that the connection uses to call methods on its delegate.
	 * @param aRunLoop The NSRunLoop instance to use when calling delegate methods.
	 * @param mode The mode in which to call delegate methods.
	 * @Discussion By default, a connection is scheduled on the current thread in the default mode when it is created. If you create a
	 *             connection with the initWithRequest:delegate:startImmediately: method and provide NO for the startImmediately parameter,
	 *             you can schedule the connection on a different run loop or mode before starting it with the start method. You can
	 *             schedule a connection on multiple run loops and modes, or on the same run loop in multiple modes. You cannot reschedule a
	 *             connection after it has started. It is an error to schedule delegate method calls with both this method and the
	 *             setDelegateQueue: method.
	 **/

	public void scheduleInRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {
		// not yet covered
	}

	/**
	 * @Signature: setDelegateQueue:
	 * @Declaration : - (void)setDelegateQueue:(NSOperationQueue *)queue
	 * @Description : Determines the operation queue that is used to call methods on the connection’s delegate.
	 * @param queue The operation queue to use when calling delegate methods.
	 * @Discussion By default, a connection is scheduled on the current thread in the default mode when it is created. If you create a
	 *             connection with the initWithRequest:delegate:startImmediately: method and provide NO for the startImmediately parameter,
	 *             you can instead schedule the connection on an operation queue before starting it with the start method. You cannot
	 *             reschedule a connection after it has started. It is an error to schedule delegate method calls with both this method and
	 *             the scheduleInRunLoop:forMode: method.
	 **/

	public void setDelegateQueue(NSOperationQueue queue) {
		// not yet covered
	}

	/**
	 * @Signature: start
	 * @Declaration : - (void)start
	 * @Description : Causes the connection to begin loading data, if it has not already.
	 * @Discussion Calling this method is necessary only if you create a connection with the initWithRequest:delegate:startImmediately:
	 *             method and provide NO for the startImmediately parameter. If you don’t schedule the connection in a run loop or an
	 *             operation queue before calling this method, the connection is scheduled in the current run loop in the default mode.
	 **/


	public void start() {
		// not yet covered
	}

	/**
	 * @Signature: unscheduleFromRunLoop:forMode:
	 * @Declaration : - (void)unscheduleFromRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Causes the connection to stop calling delegate methods in the specified run loop and mode.
	 * @param aRunLoop The run loop instance to unschedule.
	 * @param mode The mode to unschedule.
	 * @Discussion By default, a connection is scheduled on the current thread in the default mode when it is created. If you create a
	 *             connection with the initWithRequest:delegate:startImmediately: method and provide NO for the startImmediately parameter,
	 *             you can instead schedule connection on a different run loop or mode before starting it with the start method. You can
	 *             schedule a connection on multiple run loops and modes, or on the same run loop in multiple modes. Use this method to
	 *             unschedule the connection from an undesired run loop and mode before starting the connection. You cannot reschedule a
	 *             connection after it has started. It is not necessary to unschedule a connection after it has finished.
	 **/

	public void unscheduleFromRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {
		// not yet covered
	}

	private final static ProgressDialog dialog = new ProgressDialog(
			GenericMainContext.sharedContext);

	public static void postData(final NSURLRequest request, final Object delegate) {

		AsyncTask<Void, Void, String> postDataTask = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {

				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(request.URL().getWrappedURL().toString());
				NSData bodyData = request.HTTPBody();
				NSString body = (NSString) new NSString().initWithDataEncoding(bodyData,
						NSStringEncoding.NSUTF8StringEncoding);
				String msg = body.getWrappedString();
				String[] keyvals = msg.split("&");
				try {
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

					for (String elmt : keyvals) {
						String[] keyval = elmt.split("=");
						nameValuePairs.add(new BasicNameValuePair(keyval[0], keyval[1]));
					}
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					// Execute HTTP Post Request
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String responseBody = httpclient.execute(httppost, responseHandler);
					if (responseBody != null) {
						InvokableHelper.invoke(delegate, "connectionDidReceiveResponse",
								new NSURLConnection(), new NSURLResponse());
					}
					return responseBody;

				} catch (Exception e) {
					LOGGER.info(String.valueOf(e));
					NSError error = new NSError();
					InvokableHelper.invoke(delegate, "connectionDidFailWithError",
							new NSURLConnection(), error);
				}
				return null;
			}

			@Override
			protected void onPostExecute(String response) {
				// run on UI Thread
				if (delegate != null)
					try {
						InvokableHelper.invoke(delegate, "connectionDidReceiveData",
								new NSURLConnection(), new NSData(response.getBytes()));
						InvokableHelper.invoke(delegate, "connectionDidFinishLoading",
								new NSURLConnection());
					} catch (Exception e) {
						Log.d("NSURLConnection", "", e);
					}
				if (response == null) {
					NSError error = new NSError();
					InvokableHelper.invoke(delegate, "connectionDidFailWithError",
							new NSURLConnection(), error);
				}
				dialog.dismiss();
			}

			@Override
			protected void onPreExecute() {
				dialog.setMessage("Processing...");
				dialog.show();
			}
		};
		postDataTask.execute();

	}
}