
package com.myappconverter.java.foundations;



public abstract class NSURLProtocol extends NSObject {

	/**
	 * @Signature: canInitWithRequest:
	 * @Declaration : + (BOOL)canInitWithRequest:(NSURLRequest *)request
	 * @Description : Returns whether the protocol subclass can handle the specified request.
	 * @param request The request to be handled.
	 * @return Return Value YES if the protocol subclass can handle request, otherwise NO.
	 * @Discussion A subclass should inspect request and determine whether or not the implementation can perform a load with that request.
	 *             This is an abstract method and subclasses must provide an implementation.
	 **/

	public abstract boolean canInitWithRequest(NSURLRequest request);

	/**
	 * @Signature: canonicalRequestForRequest:
	 * @Declaration : + (NSURLRequest *)canonicalRequestForRequest:(NSURLRequest *)request
	 * @Description : Returns a canonical version of the specified request.
	 * @param request The request whose canonical version is desired.
	 * @return Return Value The canonical form of request.
	 * @Discussion It is up to each concrete protocol implementation to define what â€œcanonicalâ€? means. A protocol should guarantee that the
	 *             same input request always yields the same canonical form. Special consideration should be given when implementing this
	 *             method, because the canonical form of a request is used to lookup objects in the URL cache, a process which performs
	 *             equality checks between NSURLRequest objects. This is an abstract method and subclasses must provide an implementation.
	 **/

	public abstract NSURLRequest canonicalRequestForRequest(NSURLRequest request);

	/**
	 * @Signature: propertyForKey:inRequest:
	 * @Declaration : + (id)propertyForKey:(NSString *)key inRequest:(NSURLRequest *)request
	 * @Description : Returns the property associated with the specified key in the specified request.
	 * @param key The key of the desired property.
	 * @param request The request whose properties are to be queried.
	 * @return Return Value The property associated with key, or nil if no property has been stored for key.
	 * @Discussion This method provides an interface for protocol implementors to access protocol-specific information associated with
	 *             NSURLRequest objects.
	 **/

	public abstract Object propertyForKeyInRequest(NSString key, NSURLRequest request);

	/**
	 * @Signature: registerClass:
	 * @Declaration : + (BOOL)registerClass:(Class)protocolClass
	 * @Description : Attempts to register a subclass of NSURLProtocol, making it visible to the URL loading system.
	 * @param protocolClass The subclass of NSURLProtocol to register.
	 * @return Return Value YES if the registration is successful, NO otherwise. The only failure condition is if protocolClass is not a
	 *         subclass of NSURLProtocol.
	 * @Discussion When the URL loading system begins to load a request, each registered protocol class is consulted in turn to see if it
	 *             can be initialized with the specified request. The first NSURLProtocol subclass to return YES when sent a
	 *             canInitWithRequest: message is used to perform the URL load. There is no guarantee that all registered protocol classes
	 *             will be consulted. Classes are consulted in the reverse order of their registration. A similar design governs the process
	 *             to create the canonical form of a request with canonicalRequestForRequest:.
	 **/

	public abstract boolean registerClass(Class<? extends NSObject> protocolClass);

	/**
	 * @Signature: removePropertyForKey:inRequest:
	 * @Declaration : + (void)removePropertyForKey:(NSString *)key inRequest:(NSMutableURLRequest *)request
	 * @Description : Removes the property associated with the specified key in the specified request.
	 * @param key The key whose value should be removed.
	 * @param request The request from which to remove the property value.
	 * @Discussion This method is used to provide an interface for protocol implementors to customize protocol-specific information
	 *             associated with NSMutableURLRequest objects.
	 **/

	public abstract void removePropertyForKeyInRequest(NSString key, NSURLRequest request);

	/**
	 * @Signature: requestIsCacheEquivalent:toRequest:
	 * @Declaration : + (BOOL)requestIsCacheEquivalent:(NSURLRequest *)aRequest toRequest:(NSURLRequest *)bRequest
	 * @Description : Returns whether two requests are equivalent for cache purposes.
	 * @param aRequest The request to compare with bRequest.
	 * @param bRequest The request to compare with aRequest.
	 * @return Return Value YES if aRequest and bRequest are equivalent for cache purposes, NO otherwise. Requests are considered equivalent
	 *         for cache purposes if and only if they would be handled by the same protocol and that protocol declares them equivalent after
	 *         performing implementation-specific checks.
	 * @Discussion The NSURLProtocol implementation of this method compares the URLs of the requests to determine if the requests should be
	 *             considered equivalent. Subclasses can override this method to provide protocol-specific comparisons.
	 **/

	public abstract boolean requestIsCacheEquivalentToRequest(NSURLRequest aRequest, NSURLRequest bRequest);

	/**
	 * @Signature: setProperty:forKey:inRequest:
	 * @Declaration : + (void)setProperty:(id)value forKey:(NSString *)key inRequest:(NSMutableURLRequest *)request
	 * @Description : Sets the property associated with the specified key in the specified request.
	 * @param value The value to set for the specified property.
	 * @param key The key for the specified property.
	 * @param request The request for which to create the property.
	 * @Discussion This method is used to provide an interface for protocol implementors to customize protocol-specific information
	 *             associated with NSMutableURLRequest objects.
	 **/

	public abstract void setPropertyForKeyInRequest(Object value, NSString key, NSMutableURLRequest request);

	/**
	 * @Signature: unregisterClass:
	 * @Declaration : + (void)unregisterClass:(Class)protocolClass
	 * @Description : Unregisters the specified subclass of NSURLProtocol.
	 * @param protocolClass The subclass of NSURLProtocol to unregister.
	 * @Discussion After this method is invoked, protocolClass is no longer consulted by the URL loading system.
	 **/

	public abstract void unregisterClass(Class<? extends NSObject> protocolClass);

	/**
	 * @Signature: cachedResponse
	 * @Declaration : - (NSCachedURLResponse *)cachedResponse
	 * @Description : Returns the receiverâ€™s cached response.
	 * @return Return Value The receiver's cached response.
	 * @Discussion If not overridden in a subclass, this method returns the cached response stored at initialization time.
	 **/

	public abstract NSCachedURLResponse cachedResponse();

	/**
	 * @Signature: client
	 * @Declaration : - (id < NSURLProtocolClient >)client
	 * @Description : Returns the object the receiver uses to communicate with the URL loading system.
	 * @return Return Value The object the receiver uses to communicate with the URL loading system.
	 **/

	public abstract Object client();

	/**
	 * @Signature: initWithRequest:cachedResponse:client:
	 * @Declaration : - (id)initWithRequest:(NSURLRequest *)request cachedResponse:(NSCachedURLResponse *)cachedResponse client:(id <
	 *              NSURLProtocolClient >)client
	 * @Description : Initializes an NSURLProtocol object.
	 * @param request The URL request for the URL protocol object. This request is retained.
	 * @param cachedResponse A cached response for the request; may be nil if there is no existing cached response for the request.
	 * @param client An object that provides an implementation of the NSURLProtocolClient protocol that the receiver uses to communicate
	 *            with the URL loading system. This client object is retained.
	 * @Discussion Subclasses should override this method to do any custom initialization. An application should never explicitly call this
	 *             method. This is the designated intializer for NSURLProtocol.
	 **/

	public abstract Object initWithRequestCachedResponseClient(NSURLRequest request, NSCachedURLResponse cachedResponse, Object client);

	/**
	 * @Signature: request
	 * @Declaration : - (NSURLRequest *)request
	 * @Description : Returns the receiverâ€™s request.
	 * @return Return Value The receiver's request.
	 **/

	public abstract NSURLRequest request();

	/**
	 * @Signature: startLoading
	 * @Declaration : - (void)startLoading
	 * @Description : Starts protocol-specific loading of the request.
	 * @Discussion When this method is called, the subclass implementation should start loading the request, providing feedback to the URL
	 *             loading system via the NSURLProtocolClient protocol. Subclasses must implement this method.
	 **/

	public abstract void startLoading();

	/**
	 * @Signature: stopLoading
	 * @Declaration : - (void)stopLoading
	 * @Description : Stops protocol-specific loading of the request.
	 * @Discussion When this method is called, the subclass implementation should stop loading a request. This could be in response to a
	 *             cancel operation, so protocol implementations must be able to handle this call while a load is in progress. When your
	 *             protocol receives a call to this method, it should also stop sending notifications to the client. Subclasses must
	 *             implement this method.
	 **/

	public abstract void stopLoading();

}