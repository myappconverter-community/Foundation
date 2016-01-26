package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSInputStream;
import com.myappconverter.java.foundations.NSNetService;
import com.myappconverter.java.foundations.NSOutputStream;


public abstract class NSNetServiceDelegate {

	// 1
	/**
	 * @Signature: netService:didAcceptConnectionWithInputStream:outputStream:
	 * @Declaration : - (void)netService:(NSNetService *)sender didAcceptConnectionWithInputStream:(NSInputStream *)inputStream
	 *              outputStream:(NSOutputStream *)outputStream
	 * @Description : Called when a client connects to a service managed by Bonjour.
	 * @Discussion When you publish a service, if you set the NSNetServiceListenForConnections flag in the service options, the service
	 *             object accepts connections on behalf of your app. Later, when a client connects to that service, the service object calls
	 *             this method to provide the app with a pair of streams for communicating with that client.
	 **/

	public abstract void netServiceDidAcceptConnectionWithInputStreamOutputStream(NSNetService sender, NSInputStream inputStream,
			NSOutputStream outputStream);

	// 2
	/**
	 * @Signature: netService:didNotPublish:
	 * @Declaration : - (void)netService:(NSNetService *)sender didNotPublish:(NSDictionary *)errorDict
	 * @Description : Notifies the delegate that a service could not be published.
	 * @Discussion This method may be called long after a netServiceWillPublish: message has been delivered to the delegate.
	 **/

	public abstract void netServiceDidNotPublish(NSNetService sender, NSDictionary<?, ?> errorDict);

	// 3
	/**
	 * @Signature: netService:didNotResolve:
	 * @Declaration : - (void)netService:(NSNetService *)sender didNotResolve:(NSDictionary *)errorDict
	 * @Description : Informs the delegate that an error occurred during resolution of a given service.
	 * @Discussion Clients may try to resolve again upon receiving this error. For example, a DNS rotary may yield different IP addresses on
	 *             different resolution requests.
	 **/

	public abstract void netServiceDidNotResolve(NSNetService sender, NSDictionary<?, ?> errorDict);

	// 4
	/**
	 * @Signature: netService:didUpdateTXTRecordData:
	 * @Declaration : - (void)netService:(NSNetService *)sender didUpdateTXTRecordData:(NSData *)data
	 * @Description : Notifies the delegate that the TXT record for a given service has been updated.
	 **/

	public abstract void netServiceDidUpdateTXTRecordData(NSNetService sender, NSData data);

	// 5
	/**
	 * @Signature: netServiceDidPublish:
	 * @Declaration : - (void)netServiceDidPublish:(NSNetService *)sender
	 * @Description : Notifies the delegate that a service was successfully published.
	 **/

	public abstract void netServiceDidPublish(NSNetService sender);

	// 6
	/**
	 * @Signature: netServiceDidResolveAddress:
	 * @Declaration : - (void)netServiceDidResolveAddress:(NSNetService *)sender
	 * @Description : Informs the delegate that the address for a given service was resolved.
	 * @Discussion The delegate can use the addresses method to retrieve the service’s address.
	 **/

	public abstract void netServiceDidResolveAddress(NSNetService sender);

	// 7
	/**
	 * @Signature: netServiceDidStop:
	 * @Declaration : - (void)netServiceDidStop:(NSNetService *)sender
	 * @Description : Informs the delegate that a publish or resolveWithTimeout: request was stopped.
	 **/

	public abstract void netServiceDidStop(NSNetService sender);

	// 8
	/**
	 * @Signature: netServiceWillPublish:
	 * @Declaration : - (void)netServiceWillPublish:(NSNetService *)sender
	 * @Description : Notifies the delegate that the network is ready to publish the service.
	 * @Discussion Publication of the service proceeds asynchronously and may still generate a call to the delegate’s
	 *             netService:didNotPublish: method if an error occurs.
	 **/

	public abstract void netServiceWillPublish(NSNetService sender);

	// 9
	/**
	 * @Signature: netServiceWillResolve:
	 * @Declaration : - (void)netServiceWillResolve:(NSNetService *)sender
	 * @Description : Notifies the delegate that the network is ready to resolve the service.
	 * @Discussion Resolution of the service proceeds asynchronously and may still generate a call to the delegate’s
	 *             netService:didNotResolve: method if an error occurs.
	 **/

	public abstract void netServiceWillResolve(NSNetService sender);

}