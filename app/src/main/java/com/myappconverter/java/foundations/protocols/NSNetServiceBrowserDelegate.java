package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSNetService;
import com.myappconverter.java.foundations.NSNetServiceBrowser;
import com.myappconverter.java.foundations.NSString;


public abstract class NSNetServiceBrowserDelegate {

	// 1
	/**
	 * @Signature: netServiceBrowser:didFindDomain:moreComing:
	 * @Declaration : - (void)netServiceBrowser:(NSNetServiceBrowser *)netServiceBrowser didFindDomain:(NSString *)domainName
	 *              moreComing:(BOOL)moreDomainsComing
	 * @Description : Tells the delegate the sender found a domain.
	 * @Discussion The delegate uses this message to compile a list of available domains. It should wait until moreDomainsComing is NO to do
	 *             a bulk update of user interface elements.
	 **/

	public abstract void netServiceBrowserDidFindDomainMoreComing(NSNetServiceBrowser netServiceBrowser, NSString domainName,
			boolean moreDomainsComing);

	// 2
	/**
	 * @Signature: netServiceBrowser:didFindService:moreComing:
	 * @Declaration : - (void)netServiceBrowser:(NSNetServiceBrowser *)netServiceBrowser didFindService:(NSNetService *)netService
	 *              moreComing:(BOOL)moreServicesComing
	 * @Description : Tells the delegate the sender found a service.
	 * @Discussion The delegate uses this message to compile a list of available services. It should wait until moreServicesComing is NO to
	 *             do a bulk update of user interface elements.
	 **/

	public abstract void netServiceBrowserDidFindServiceMoreComing(NSNetServiceBrowser netServiceBrowser, NSNetService netService,
			boolean moreServicesComing);

	// 3
	/**
	 * @Signature: netServiceBrowser:didNotSearch:
	 * @Declaration : - (void)netServiceBrowser:(NSNetServiceBrowser *)netServiceBrowser didNotSearch:(NSDictionary *)errorInfo
	 * @Description : Tells the delegate that a search was not successful.
	 **/

	public abstract void netServiceBrowserDidNotSearch(NSNetServiceBrowser netServiceBrowser, NSDictionary<?, ?> errorInfo);

	// 4
	/**
	 * @Signature: netServiceBrowser:didRemoveDomain:moreComing:
	 * @Declaration : - (void)netServiceBrowser:(NSNetServiceBrowser *)netServiceBrowser didRemoveDomain:(NSString *)domainName
	 *              moreComing:(BOOL)moreDomainsComing
	 * @Description : Tells the delegate the a domain has disappeared or has become unavailable.
	 * @Discussion The delegate uses this message to compile a list of unavailable domains. It should wait until moreDomainsComing is NO to
	 *             do a bulk update of user interface elements.
	 **/

	public abstract void netServiceBrowserDidRemoveDomainMoreComing(NSNetServiceBrowser netServiceBrowser, NSString domainName,
			boolean moreDomainsComing);

	// 5
	/**
	 * @Signature: netServiceBrowser:didRemoveService:moreComing:
	 * @Declaration : - (void)netServiceBrowser:(NSNetServiceBrowser *)netServiceBrowser didRemoveService:(NSNetService *)netService
	 *              moreComing:(BOOL)moreServicesComing
	 * @Description : Tells the delegate a service has disappeared or has become unavailable.
	 * @Discussion The delegate uses this message to compile a list of unavailable services. It should wait until moreServicesComing is NO
	 *             to do a bulk update of user interface elements.
	 **/

	public abstract void netServiceBrowserDidRemoveServiceMoreComing(NSNetServiceBrowser netServiceBrowser, NSNetService netService,
			boolean moreServicesComing);

	// 6
	/**
	 * @Signature: netServiceBrowserDidStopSearch:
	 * @Declaration : - (void)netServiceBrowserDidStopSearch:(NSNetServiceBrowser *)netServiceBrowser
	 * @Description : Tells the delegate that a search was stopped.
	 * @Discussion When netServiceBrowser receives a stop message from its client, netServiceBrowser sends a netServiceBrowserDidStopSearch:
	 *             message to its delegate. The delegate then performs any necessary cleanup.
	 **/

	public abstract void netServiceBrowserDidStopSearch(NSNetServiceBrowser netServiceBrowser);

	// 7
	/**
	 * @Signature: netServiceBrowserWillSearch:
	 * @Declaration : - (void)netServiceBrowserWillSearch:(NSNetServiceBrowser *)netServiceBrowser
	 * @Description : Tells the delegate that a search is commencing.
	 * @Discussion This message is sent to the delegate only if the underlying network layer is ready to begin a search. The delegate can
	 *             use this notification to prepare its data structures to receive data.
	 **/

	public abstract void netServiceBrowserWillSearch(NSNetServiceBrowser netServiceBrowser);

}