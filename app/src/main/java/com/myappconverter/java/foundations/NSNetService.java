
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.RegistrationListener;
import android.net.nsd.NsdManager.ResolveListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSNetServiceDelegate;



public class NSNetService extends NSObject {

	NsdServiceInfo serviceInfo;
	NsdManager NsdManager;
	Context cn;
	RegistrationListener registrationListener;
	ResolveListener ResolveListener;
	private static final String TAG = NSNetServiceBrowser.class.getSimpleName();
	private Date current_date_resolve;
	// ////////////////////
	NSString hostName;
	NSArray<NSData> addresses;
	NSString domain;
	NSString name;
	NSString type;
	NSNetServiceDelegate delegate;
	int port;
	NSData TXTRecord;

	/**
	 * @Signature: resolveWithTimeout:
	 * @Declaration : - (void)resolveWithTimeout:(NSTimeInterval)timeout
	 * @Description : Starts a resolve process of a finite duration for the service.
	 * @param timeout The maximum number of seconds to attempt a resolve. A value of 0.0 indicates no timeout and a resolve process of
	 *            indefinite duration.
	 * @Discussion During the resolve period, the service sends netServiceDidResolveAddress: to the delegate for each address it discovers
	 *             that matches the service parameters. Once the timeout is hit, the service sends netServiceDidStop: to the delegate. If no
	 *             addresses resolve during the timeout period, the service sends netService:didNotResolve: to the delegate.
	 **/
	
	public void resolveWithTimeout(double timeout) {
		if ((float) timeout == 0.0) {
			NsdManager = (NsdManager) cn.getSystemService(Context.NSD_SERVICE);
			NsdManager.resolveService(serviceInfo, ResolveListener);

		} else {
			Date date_with_timeout = new Date();
			Calendar c = new GregorianCalendar();
			c.add(Calendar.SECOND, (int) timeout);
			date_with_timeout = c.getTime();
			initializeRegistrationListener();
			if (current_date_resolve.compareTo(date_with_timeout) < 0) {

				NsdManager = (NsdManager) cn.getSystemService(Context.NSD_SERVICE);
				NsdManager.resolveService(serviceInfo, ResolveListener);
				// TODO call the fonction : netServiceDidResolveAddress from NSNetSerciceDelegate
			} else {
				// TODO call the fonction : netService:didNotResolve from NSNetSerciceDelegate
			}
		}

	}

	// Initialise resolution listener

	public void initializeResolveListener() {
		current_date_resolve = new Date();
		Calendar c = new GregorianCalendar();
		current_date_resolve = c.getTime();
		ResolveListener = new ResolveListener() {

			@Override
			public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
				// Called when the resolve fails. Use the error code to debug.
				Log.w(TAG, "Resolve failed" + errorCode);
			}

			@Override
			public void onServiceResolved(NsdServiceInfo service) {
				// Called when the resolve is done.
				Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

				if (service.getServiceName().equals(serviceInfo.getServiceName())) {
					Log.d(TAG, "Same IP.");
					return;
				}
				serviceInfo = service;
				int nsport = serviceInfo.getPort();
				port = nsport;
				InetAddress host = serviceInfo.getHost();
				byte[] adresse = host.getAddress();
				NSData nsadresse = new NSData();
				nsadresse.setWrappedData(adresse);
				addresses.wrappedList.add(nsadresse);
				String hostname = host.getHostName();
				NSString nshost = new NSString();
				nshost.setWrappedString(hostname);
				hostName = nshost;

			}
		};
	}

	/**
	 * Stops the monitoring of TXT-record updates for the receiver. - (void)stopMonitoring
	 */
	
	public void stopMonitoring() {
		// TODO the API isnt available
	}

	/**
	 * Halts a currently running attempt to publish or resolve a service. - (void)stop
	 * 
	 * @Discussion This method results in the sending of a netServiceDidStop: message to the delegate.
	 */
	
	public void stop() {
		NsdManager = (NsdManager) cn.getSystemService(Context.NSD_SERVICE);
		NsdManager.unregisterService(registrationListener);
		NSNetServiceBrowser ns = new NSNetServiceBrowser();
		ns.stop();

	}

	/**
	 * @Signature: startMonitoring
	 * @Declaration : - (void)startMonitoring
	 * @Description : Starts the monitoring of TXT-record updates for the receiver.
	 * @Discussion The delegate must implement netService:didUpdateTXTRecordData:, which is called when the TXT record for the receiver is
	 *             updated.
	 **/
	
	public void startMonitoring() {

		// TODO the API isnt available
	}

	/**
	 * Deprecated in iOS 2.0 Starts a resolve process for the receiver. (Deprecated in iOS 2.0. Use resolveWithTimeout: instead.) -
	 * (void)resolve
	 * 
	 * @Discussion Attempts to determine at least one address for the receiver. This method returns immediately, with success or failure
	 *             indicated by the callbacks to the delegate. In Mac OS X v10.4, this method calls resolveWithTimeout: with a timeout value
	 *             of 5.
	 */
	
	public void resolve() {
		NsdManager = (NsdManager) cn.getSystemService(Context.NSD_SERVICE);
		NsdManager.resolveService(serviceInfo, ResolveListener);

	}

	/**
	 * @Signature: publishWithOptions:
	 * @Declaration : - (void)publishWithOptions:(NSNetServiceOptions)serviceOptions
	 * @Description : Attempts to advertise the receiver on the network, with the given options.
	 * @param serviceOptions Options for the receiver. The supported options are described in NSNetServiceOptions.
	 * @Discussion This method returns immediately, with success or failure indicated by the callbacks to the delegate.
	 **/
	
	public void publishWithOptions(NSNetServiceOptions serviceOptions) {

		switch (serviceOptions) {
		case NSNetServiceNoAutoRename:
			Log.w(TAG, "The service name automatically changes if two devices on the network both have the same service name ");
			this.publish();
			break;

		case NSNetServiceListenForConnections:
			String type = serviceInfo.getServiceType();
			String[] tabType = type.split(".");
			tabType[1] = "._tcp.";
			String newType = tabType[0] + tabType[1];
			serviceInfo.setServiceType(newType);
			this.publish();
			break;
		default:
			break;
		}

	}

	/**
	 * @Signature: removeFromRunLoop:forMode:
	 * @Declaration : - (void)removeFromRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Removes the service from the given run loop for a given mode.
	 * @param aRunLoop The run loop from which to remove the receiver.
	 * @param mode The run loop mode from which to remove the receiver. Possible values for mode are discussed in the "Constants" section of
	 *            NSRunLoop.
	 * @Discussion You can use this method in conjunction with scheduleInRunLoop:forMode: to transfer the service to a different run loop.
	 *             Although it is possible to remove an NSNetService object completely from any run loop and then attempt actions on it, it
	 *             is an error to do so.
	 **/
	
	public void removeFromRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {
		// TODO to be done in the class NSRunLoop
	}

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)aRunLoop forMode:(NSString *)mode
	 * @Description : Adds the service to the specified run loop.
	 * @param aRunLoop The run loop to which to add the receiver.
	 * @param mode The run loop mode to which to add the receiver. Possible values for mode are discussed in the "Constants" section of
	 *            NSRunLoop.
	 * @Discussion You can use this method in conjunction with removeFromRunLoop:forMode: to transfer a service to a different run loop. You
	 *             should not attempt to run a service on multiple run loops.
	 **/
	
	public void scheduleInRunLoopForMode(NSRunLoop aRunLoop, NSString mode) {
		// TODO verify the class NSRunLoop
		// not yet covered
	}

	/**************************************************************************/

	/**
	 * @Signature: getInputStream:outputStream:
	 * @Declaration : - (BOOL)getInputStream:(out NSInputStream **)inputStream outputStream:(out NSOutputStream **)outputStream
	 * @Description : Creates a pair of input and output streams for the receiver and returns a Boolean value that indicates whether they
	 *              were retrieved successfully.
	 * @param inputStream Upon return, the input stream for the receiver. Pass NULL if you do not need this stream.
	 * @param outputStream Upon return, the output stream for the receiver. Pass NULL if you do not need this stream.
	 * @return Return Value YES if the streams are created successfully, otherwise NO.
	 * @Discussion After this method is called, no delegate callbacks are called by the receiver. Note: If automatic reference counting is
	 *             not used, the input and output streams returned through the parameters are retained, which means that you are responsible
	 *             for releasing them to avoid memory leaks.
	 **/
	
	public boolean getInputStreamOutputStream(NSInputStream[] inputStream, NSOutputStream[] outputStream) throws UnknownHostException,
			IOException {
		// TODO check that
		Socket socket = new Socket(hostName.getWrappedString(), port);
		// Input Stream
		NSInputStream nsInput = new NSInputStream();
		InputStream inputstream = socket.getInputStream();
		nsInput.setWrappedInputStream(inputstream);
		inputStream[0] = nsInput;
		// Output Stream
		NSOutputStream nsOutput = new NSOutputStream();
		OutputStream output = socket.getOutputStream();
		nsOutput.setmOutputStream(output);
		outputStream[0] = nsOutput;

		return inputStream != null && outputStream != null;
	}

	/**
	 * @Signature: dictionaryFromTXTRecordData:
	 * @Declaration : + (NSDictionary *)dictionaryFromTXTRecordData:(NSData *)txtData
	 * @Description : Returns a dictionary representing a TXT record given as an NSData object.
	 * @param txtData A data object encoding a TXT record.
	 * @return Return Value A dictionary representing txtData. The dictionary’s keys are NSString objects using UTF8 encoding. The values
	 *         associated with all the dictionary’s keys are NSData objects that encapsulate strings or data. Fails an assertion if txtData
	 *         cannot be represented as an NSDictionary object.
	 **/
	
	public static NSDictionary dictionaryFromTXTRecordData(NSData txtData) {
		NSDictionary mDict = new NSDictionary();
		mDict.setTxtNSData(txtData);
		return mDict;
	}

	/**
	 * @Signature: dataFromTXTRecordDictionary:
	 * @Declaration : + (NSData *)dataFromTXTRecordDictionary:(NSDictionary *)txtDictionary
	 * @Description : Returns an NSData object representing a TXT record formed from a given dictionary.
	 * @param txtDictionary A dictionary containing a TXT record.
	 * @return Return Value An NSData object representing TXT data formed from txtDictionary. Fails an assertion if txtDictionary cannot be
	 *         represented as an NSData object.
	 **/
	
	public static NSData dataFromTXTRecordDictionary(NSDictionary txtDictionary) {
		NSData mData = new NSData();
		mData = txtDictionary.getTxtNSData();
		return mData;
	}

	/**
	 * @Signature: setTXTRecordData:
	 * @Declaration : - (BOOL)setTXTRecordData:(NSData *)recordData
	 * @Description : Sets the TXT record for the receiver, and returns a Boolean value that indicates whether the operation was successful.
	 * @param recordData The TXT record for the receiver.
	 * @return Return Value YES if recordData is successfully set as the TXT record, otherwise NO.
	 **/
	
	public boolean setTXTRecordData(NSData recordData) {
		TXTRecord = recordData;
		return true;
	}

	/**
	 * Returns the TXT record for the receiver. - (NSData *)TXTRecordData
	 */
	
	public NSData TXTRecordData() {
		return TXTRecord;
	}

	/**
	 * @Signature: initWithDomain:type:name:
	 * @Declaration : - (id)initWithDomain:(NSString *)domain type:(NSString *)type name:(NSString *)name
	 * @Description : Returns the receiver, initialized as a network service of a given type and sets the initial host information.
	 * @param domain The domain for the service. To resolve in the default domains, pass in an empty string (@""). To limit resolution to
	 *            the local domain, use @"local.". If you are creating this object to resolve a service whose information your app stored
	 *            previously, you should set this to the domain in which the service was originally discovered. You can also use a
	 *            NSNetServiceBrowser object to obtain a list of possible domains in which you can discover and resolve services.
	 * @param type The network service type. type must contain both the service type and transport layer information. To ensure that the
	 *            mDNS responder searches for services, as opposed to hosts, prefix both the service name and transport layer name with an
	 *            underscore character (“_�?). For example, to search for an HTTP service on TCP, you would use the type string
	 *            "_http._tcp.". Note that the period character at the end of the string, which indicates that the domain name is an
	 *            absolute name, is required.
	 * @param name The name of the service to resolve.
	 * @return Return Value The receiver, initialized as a network service named name of type type in the domain domain.
	 * @Discussion This method is the appropriate initializer to use to resolve a service—to publish a service, use
	 *             initWithDomain:type:name:port:. If you know the values for domain, type, and name of the service you wish to connect to,
	 *             you can create an NSNetService object using this initializer and call resolveWithTimeout: on the result. You cannot use
	 *             this initializer to publish a service. This initializer passes an invalid port number to the designated initializer,
	 *             which prevents the service from being registered. Calling publish on an NSNetService object initialized with this method
	 *             generates a call to your delegate’s netService:didNotPublish: method with an NSNetServicesBadArgumentError error.
	 **/
	
	public Object initWithDomainTypeName(NSString domain, NSString type, NSString name) {
		this.domain = domain;
		this.name = name;
		this.type = type;
		serviceInfo = new NsdServiceInfo();
		serviceInfo.setServiceType(type.getWrappedString());
		serviceInfo.setServiceName(name.getWrappedString());
		return serviceInfo;
	}

	/**
	 * @Signature: initWithDomain:type:name:port:
	 * @Declaration : - (id)initWithDomain:(NSString *)domain type:(NSString *)type name:(NSString *)name port:(int)port
	 * @Description : Initializes the receiver for publishing a network service of type type at the socket location specified by domain,
	 *              name, and port.
	 * @param domain The domain for the service. To use the default registration domains, pass in an empty string (@""). To limit
	 *            registration to the local domain, use @"local.". You can also use a NSNetServiceBrowser object to obtain a list of
	 *            possible domains in which you can publish your service.
	 * @param type The network service type. type must contain both the service type and transport layer information. To ensure that the
	 *            mDNS responder searches for services, as opposed to hosts, prefix both the service name and transport layer name with an
	 *            underscore character (“_�?). For example, to search for an HTTP service on TCP, you would use the type string
	 *            "_http._tcp.". Note that the period character at the end of the string, which indicates that the domain name is an
	 *            absolute name, is required.
	 * @param name The name by which the service is identified to the network. The name must be unique. If you pass the empty string (@""),
	 *            the system automatically advertises your service using the computer name as the service name.
	 * @param port The port on which the service is published. If you specify the NSNetServiceListenForConnections flag, you may pass zero
	 *            (0), in which case the service automatically allocates an arbitrary (ephemeral) port for your service. When the delegate’s
	 *            netServiceDidPublish: is called, you can determine the actual port chosen by calling the service object’s Backward
	 *            Compatibility Note method or accessing the corresponding property. If your app is listening for connections on its own,
	 *            the value of port must be a port number acquired by your application for the service.
	 * @Discussion You use this method to create a service that you wish to publish on the network. Although you can also use this method to
	 *             create a service you wish to resolve on the network, it is generally more appropriate to use the
	 *             initWithDomain:type:name: method instead. When publishing a service, you must provide valid arguments in order to
	 *             advertise your service correctly. If the host computer has access to multiple registration domains, you must create
	 *             separate NSNetService objects for each domain. If you attempt to publish in a domain for which you do not have
	 *             registration authority, your request may be denied. It is acceptable to use an empty string for the domain argument when
	 *             publishing or browsing a service, but do not rely on this for resolution. This method is the designated initializer.
	 **/
	
	public Object initWithDomainTypeNamePort(NSString domain, NSString type, NSString name, int port) {
		serviceInfo = new NsdServiceInfo();
		serviceInfo.setServiceType(type.getWrappedString());
		serviceInfo.setServiceName(name.getWrappedString());
		serviceInfo.setPort(port);
		return serviceInfo;
	}

	/**
	 * @Signature: publish
	 * @Declaration : - (void)publish
	 * @Description : Attempts to advertise the receiver’s on the network.
	 * @Discussion This method returns immediately, with success or failure indicated by the callbacks to the delegate. This is equivalent
	 *             to calling publishWithOptions: with the default options (0).
	 **/
	
	public void publish() {
		this.initializeRegistrationListener();
		NsdManager = (NsdManager) cn.getSystemService(Context.NSD_SERVICE);

		NsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener);

	}

	public void initializeRegistrationListener() {

		// TODO verify option(0) for android

		registrationListener = new RegistrationListener() {

			@Override
			public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
				Log.w(TAG, "RegstrationFailed serviceInfo= " + serviceInfo + " , errorCode= " + errorCode);
			}

			@Override
			public void onServiceRegistered(NsdServiceInfo serviceInfo) {
				Log.i(TAG, "ServiceRegisterd serviceInfo= " + serviceInfo);

			}

			@Override
			public void onServiceUnregistered(NsdServiceInfo serviceInfo) {

				Log.i(TAG, "ServiceUnregisterd serviceInfo= " + serviceInfo);

			}

			@Override
			public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
				Log.w(TAG, "UnregstrationFailed serviceInfo= " + serviceInfo + "errorCode= " + errorCode);

			}

		};

	}

	/**
	 * These constants specify options for a network service. enum { NSNetServiceNoAutoRename = 1 << 0 NSNetServiceListenForConnections =
	 * 1UL << 1 }; typedef NSUInteger NSNetServiceOptions;
	 */
	
	public enum NSNetServiceOptions {

		NSNetServiceOptions(0), //
		NSNetServiceNoAutoRename(1 << 0), //
		NSNetServiceListenForConnections(1L << 1);
		long value;

		NSNetServiceOptions(long val) {
			this.value = val;
		}

	}

	public NSString getHostName() {
		return hostName;
	}

	public NSArray<NSData> getAddresses() {
		return addresses;
	}

	public NSString getDomain() {
		return domain;
	}

	public NSString getName() {
		return name;
	}

	public NSString getType() {
		return type;
	}

	public int getPort() {
		return port;
	}

	public NSNetServiceDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(NSNetServiceDelegate delegate) {
		this.delegate = delegate;
	}

}