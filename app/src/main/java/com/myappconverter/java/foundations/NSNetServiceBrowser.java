
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSNetServiceBrowserDelegate;



public class NSNetServiceBrowser extends NSObject {

	NetworkInfo[] listeNetwork;
	ConnectivityManager cm;
	NsdManager nsdManager;
	Context cn;
	boolean discoveryStarted;
	boolean isDiscoverRunning;
	private List<NsdServiceInfo> serviceFound;
	private List<NsdListener> listeners;
	private Set<String> solvedServiceName;
	private MyDiscoveryListener discoveryListener;
	private static final String TAG = NSNetServiceBrowser.class.getSimpleName();

	/**
	 * @Declaration : @property (assign) id <NSNetServiceBrowserDelegate> delegate;
	 * @Description : The delegate object for this instance.
	 **/
	private NSNetServiceBrowserDelegate delegate;

	public NSNetServiceBrowserDelegate delegate() {
		return delegate;
	}

	public void setDelegate(NSNetServiceBrowserDelegate delegate) {
		this.delegate = delegate;
	}

	// Initializes an allocated NSNetServiceBrowser object.

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Initializes an allocated NSNetServiceBrowser object.
	 * @return Return Value Initialized NSNetServiceBrowser object.
	 **/
	
	public NSObject init() {

		serviceFound = new ArrayList<NsdServiceInfo>();
		listeners = new ArrayList<NsdListener>();
		solvedServiceName = Collections.synchronizedSet(new HashSet<String>());
		cm = (ConnectivityManager) cn.getSystemService(Context.CONNECTIVITY_SERVICE);
		discoveryListener = new MyDiscoveryListener();
		// not yet covered
		return null;
	}

	// Initiates a search for domains visible to the host. This method returns immediately.

	/**
	 * @Signature: searchForBrowsableDomains
	 * @Declaration : - (void)searchForBrowsableDomains
	 * @Description : Initiates a search for domains visible to the host. This method returns immediately.
	 * @Discussion The delegate receives a netServiceBrowser:didFindDomain:moreComing: message for each domain discovered.
	 **/
	
	public void searchForBrowsableDomains() {
		listeNetwork = cm.getAllNetworkInfo();
	}

	// Initiates a search for domains in which the host may register services.

	/**
	 * @Signature: searchForRegistrationDomains
	 * @Declaration : - (void)searchForRegistrationDomains
	 * @Description : Initiates a search for domains in which the host may register services.
	 * @Discussion This method returns immediately, sending a netServiceBrowserWillSearch: message to the delegate if the network was ready
	 *             to initiate the search. The delegate receives a subsequent netServiceBrowser:didFindDomain:moreComing: message for each
	 *             domain discovered. Most network service browser clients do not have to use this method—it is sufficient to publish a
	 *             service with the empty string, which registers it in any available registration domains automatically.
	 **/
	
	public void searchForRegistrationDomains() {
		listeNetwork = cm.getAllNetworkInfo();
	}

	// Starts a search for services of a particular type within a specific domain.

	/**
	 * @Signature: searchForServicesOfType:inDomain:
	 * @Declaration : - (void)searchForServicesOfType:(NSString *)serviceType inDomain:(NSString *)domainName
	 * @Description : Starts a search for services of a particular type within a specific domain.
	 * @param serviceType Type of the service to search for.
	 * @param domainName Domain name in which to perform the search.
	 * @Discussion This method returns immediately, sending a netServiceBrowserWillSearch: message to the delegate if the network was ready
	 *             to initiate the search.The delegate receives subsequent netServiceBrowser:didFindService:moreComing: messages for each
	 *             service discovered. The serviceType argument must contain both the service type and transport layer information. To
	 *             ensure that the mDNS responder searches for services, rather than hosts, make sure to prefix both the service name and
	 *             transport layer name with an underscore character (“_�?). For example, to search for an HTTP service on TCP, you would use
	 *             the type string “_http._tcp.“. Note that the period character at the end is required. The domainName argument can be an
	 *             explicit domain name, the generic local domain @"local." (note trailing period, which indicates an absolute name), or the
	 *             empty string (@""), which indicates the default registration domains. Usually, you pass in an empty string. Note that it
	 *             is acceptable to use an empty string for the domainName argument when publishing or browsing a service, but do not rely
	 *             on this for resolution.
	 **/
	
	public void searchForServicesOfTypeInDomain(NSString serviceType, NSString domainName) {

		if (!discoveryStarted && !isDiscoverRunning) {
			Log.d(TAG, "Start discovery");
			nsdManager.discoverServices(serviceType.getWrappedString(), NsdManager.PROTOCOL_DNS_SD, discoveryListener);
			isDiscoverRunning = true;
		}

	}

	// Halts a currently running search or resolution.

	/**
	 * @Signature: stop
	 * @Declaration : - (void)stop
	 * @Description : Halts a currently running search or resolution.
	 * @Discussion This method sends a netServiceBrowserDidStopSearch: message to the delegate and causes the browser to discard any pending
	 *             search results.
	 **/
	
	public void stop() {
		if (discoveryStarted) {
			nsdManager.stopServiceDiscovery(discoveryListener);
			discoveryStarted = false;
		}

	}

	// Adds the receiver to the specified run loop.

	/**
	 * @Signature: scheduleInRunLoop:forMode:
	 * @Declaration : - (void)scheduleInRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)runLoopMode
	 * @Description : Adds the receiver to the specified run loop.
	 * @param runLoop Run loop in which to schedule the receiver.
	 * @param runLoopMode Run loop mode in which to perform this operation, such as NSDefaultRunLoopMode. See the Run Loop Modes section of
	 *            the NSRunLoop class for other run loop mode values.
	 * @Discussion You can use this method in conjunction with removeFromRunLoop:forMode: to transfer the receiver to a run loop other than
	 *             the default one. You should not attempt to run the receiver on multiple run loops.
	 **/
	
	public void scheduleInRunLoopForMode(NSRunLoop runLoop, NSString runLoopMode) {
		// runLoop.setMode(runLoopMode);
		// Set keys = runLoop.getModeDate().keySet();
		// Iterator it = keys.iterator();
		// while (it.hasNext()) {
		// String mod = (String) it.next();
		// if (runLoopMode.getString().equals(mod)) {
		// runLoop.getModeDate().remove(mod);
		// }
		// }

	}

	// Removes the receiver from the specified run loop.

	/**
	 * @Signature: removeFromRunLoop:forMode:
	 * @Declaration : - (void)removeFromRunLoop:(NSRunLoop *)runLoop forMode:(NSString *)runLoopMode
	 * @Description : Removes the receiver from the specified run loop.
	 * @param runLoop Run loop from which to remove the receiver.
	 * @param runLoopMode Run loop mode in which to perform this operation, such as NSDefaultRunLoopMode. See the Run Loop Modes section of
	 *            the NSRunLoop class for other run loop mode values.
	 * @Discussion You can use this method in conjunction with scheduleInRunLoop:forMode: to transfer the receiver to a run loop other than
	 *             the default one. Although it is possible to remove an NSNetService object completely from any run loop and then attempt
	 *             actions on it, you must not do it.
	 **/
	
	public void removeFromRunLoopForMode(NSRunLoop runLoop, NSString runLoopMode) {
		// Set keys = runLoop.getModeDate().keySet();
		// Iterator it = keys.iterator();
		// while (it.hasNext()) {
		// String mod = (String) it.next();
		// if (runLoopMode.getString().equals(mod)) {
		// runLoop.getModeDate().remove(mod);
		// }
		// }
	}

	private class MyDiscoveryListener implements DiscoveryListener {

		@Override
		public void onDiscoveryStarted(String serviceType) {
			Log.d(TAG, "Start discovery for " + serviceType);
			discoveryStarted = true;
		}

		@Override
		public void onDiscoveryStopped(String serviceType) {

			Log.d(TAG, "Stop discovery for " + serviceType);
			discoveryStarted = false;
			isDiscoverRunning = false;

		}

		@Override
		public void onServiceFound(NsdServiceInfo serviceInfo) {

			Log.d(TAG, "service found " + serviceInfo.getServiceName());
			fireServiceFound(serviceInfo);
		}

		@Override
		public void onServiceLost(NsdServiceInfo serviceInfo) {

			Log.d(TAG, "Service lost " + serviceInfo);
			solvedServiceName.remove(serviceInfo.getServiceName());
			serviceFound.remove(serviceInfo);
			fireServiceLost(serviceInfo);

		}

		@Override
		public void onStartDiscoveryFailed(String serviceType, int errorCode) {

			Log.d(TAG, "Failed to start discovery for " + serviceType + " error " + getNsdError(errorCode));
			nsdManager.stopServiceDiscovery(this);
			discoveryStarted = false;
			isDiscoverRunning = false;

		}

		@Override
		public void onStopDiscoveryFailed(String serviceType, int errorCode) {

			Log.d(TAG, "Failed to stop discovery for " + serviceType + " error " + getNsdError(errorCode));

		}

	}

	private void fireServiceFound(NsdServiceInfo info) {
		for (NsdListener l : listeners) {
			l.onServiceFound(info);
		}
	}

	private void fireServiceLost(NsdServiceInfo info) {
		for (NsdListener l : listeners) {
			l.onServiceLost(info);
		}
	}

	interface NsdListener {

		void onServiceFound(NsdServiceInfo info);

		void onServiceLost(NsdServiceInfo info);
	}

	private String getNsdError(int code) {
		if (code == NsdManager.FAILURE_ALREADY_ACTIVE) {
			return "NSD Already active";
		} else if (code == NsdManager.FAILURE_INTERNAL_ERROR) {
			return "NSD Internal error";
		} else if (code == NsdManager.FAILURE_MAX_LIMIT) {
			return "NSD Max request limit";
		} else
			return "NSD Unknown error";

	}

}