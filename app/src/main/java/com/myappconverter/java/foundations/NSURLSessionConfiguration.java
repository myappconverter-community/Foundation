
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSHTTPCookieStorage.NSHTTPCookieAcceptPolicy;
import com.myappconverter.java.foundations.NSURLRequest.NSURLRequestNetworkServiceType;
import com.myappconverter.java.foundations.protocols.NSCopying;


public class NSURLSessionConfiguration extends NSObject implements NSCopying {

    /**
     * @param identifier An identifier for the new session configuration that is unique for your
     *                   app. Your app can retrieve the download or
     *                   the upload response later by creating a new background session with the
     *                   same identifier.
     * @Signature: backgroundSessionConfiguration:
     * @Declaration : + (NSURLSessionConfiguration *)backgroundSessionConfiguration:(NSString
     * *)identifier
     * @Description : Returns a preconfigured session configuration object that causes the upload or
     * download to be performed in the
     * background.
     * @Discussion Sessions created with configuration objects returned by this method are called
     * background sessions. These sessions differ
     * from other sessions in the following ways: Upload and download tasks in background sessions
     * are performed by an external
     * daemon instead of by the app itself. As a result, the transfers continue in the background
     * even if the app is suspended,
     * exits, or crashes. In iOS, if your app creates a background session, the app is automatically
     * relaunched in the
     * background whenever tasks complete. In OS X, the results of background tasks are available
     * when your app restarts.
     * Background transfers have a few additional constraints, such as the need to provide a
     * delegate. For full details, see
     * NSURLSession Class Reference.
     **/

    public static NSURLSessionConfiguration backgroundSessionConfiguration(NSString identifier) {
        return null;
    }

    /**
     * @Signature: defaultSessionConfiguration
     * @Declaration : + (NSURLSessionConfiguration *)defaultSessionConfiguration
     * @Description : Returns a copy of the default session configuration.
     * @Discussion The default session configuration causes the session to behave similarly to an
     * NSURLConnection object in its standard
     * configuration. If you are porting code from NSURLConnection, use this method to obtain an
     * initial configuration and then
     * customize the returned object as needed. Note:Â Modifying this session configuration does not
     * change the default behavior
     * for existing or future sessions. It is therefore always safe to use the returned object as a
     * starting point for
     * additional customization.
     **/

    public static NSURLSessionConfiguration defaultSessionConfiguration() {
        return new NSURLSessionConfiguration();
    }

    /**
     * @Signature: ephemeralSessionConfiguration
     * @Declaration : + (NSURLSessionConfiguration *)ephemeralSessionConfiguration
     * @Description : Returns a session configuration that uses no persistent storage for caches,
     * cookies, or credentials.
     **/

    public static NSURLSessionConfiguration ephemeralSessionConfiguration() {
        return null;
    }

    /**
     * @Declaration : @property BOOL allowsCellularAccess
     * @Description : A Boolean value that determines whether connections should be made over a
     * cellular network.
     * @Discussion For more information, read â€œRestrict Cellular Networking Correctlyâ€? in Networking
     * Overview.
     **/
    private boolean allowsCellularAccess;

    public boolean isAllowsCellularAccess() {
        return allowsCellularAccess;
    }

    public void setAllowsCellularAccess(boolean allowsCellularAccess) {
        this.allowsCellularAccess = allowsCellularAccess;
    }

    /**
     * @Declaration :  NSDictionary *connectionProxyDictionary
     * @Description : A dictionary containing information about the HTTP proxy to use within this
     * session.
     * @Discussion See CFProxySupport Reference for more information about these dictionaries.
     **/
    private NSDictionary connectionProxyDictionary;

    public NSDictionary getConnectionProxyDictionary() {
        return connectionProxyDictionary;
    }

    public void setConnectionProxyDictionary(NSDictionary connectionProxyDictionary) {
        this.connectionProxyDictionary = connectionProxyDictionary;
    }

    /**
     * @Declaration :  BOOL discretionary
     * @Description : A Boolean value that determines whether background tasks can be scheduled at
     * the discretion of the system for optimal
     * performance.
     * @Discussion When this flag is set, transfers are more likely to occur when plugged into power
     * and on Wi-Fi. This value is false by
     * default. This property is used only if a sessionâ€™s configuration object was originally
     * constructed by calling the
     * backgroundSessionConfiguration: method, and only for tasks started while the app is in the
     * foreground. If a task is
     * started while the app is in the background, that task is treated as though discretionary were
     * true, regardless of the
     * actual value of this property. For sessions created based on other configurations, this
     * property is ignored.
     **/
    private boolean discretionary;

    public boolean isDiscretionary() {
        return discretionary;
    }

    public void setDiscretionary(boolean discretionary) {
        this.discretionary = discretionary;
    }

    /**
     * @Declaration :  NSDictionary *HTTPAdditionalHeaders
     * @Description : A dictionary of additional headers to send with requests.
     **/
    private NSDictionary HTTPAdditionalHeaders;

    public NSDictionary getHTTPAdditionalHeaders() {
        return HTTPAdditionalHeaders;
    }

    public void setHTTPAdditionalHeaders(NSDictionary HTTPAdditionalHeaders) {
        this.HTTPAdditionalHeaders = HTTPAdditionalHeaders;
    }

    /**
     * @Declaration : @property NSHTTPCookieAcceptPolicy HTTPCookieAcceptPolicy
     * @Description : A policy constant that determines when cookies should be accepted.
     **/
    private NSHTTPCookieAcceptPolicy HTTPCookieAcceptPolicy;

    public NSHTTPCookieAcceptPolicy getHTTPCookieAcceptPolicy() {
        return HTTPCookieAcceptPolicy;
    }

    public void setHTTPCookieAcceptPolicy(NSHTTPCookieAcceptPolicy HTTPCookieAcceptPolicy) {
        this.HTTPCookieAcceptPolicy = HTTPCookieAcceptPolicy;
    }

    /**
     * @Declaration :  NSHTTPCookieStorage *HTTPCookieStorage
     * @Description : The pool for storing cookies within this session.
     * @Discussion To disable cookie storage, set this property to NULL.
     **/
    private NSHTTPCookieStorage HTTPCookieStorage;

    public NSHTTPCookieStorage getHTTPCookieStorage() {
        return HTTPCookieStorage;
    }

    public void setHTTPCookieStorage(NSHTTPCookieStorage HTTPCookieStorage) {
        this.HTTPCookieStorage = HTTPCookieStorage;
    }

    /**
     * @Declaration : @property NSInteger HTTPMaximumConnectionsPerHost
     * @Description : The maximum number of simultaneous connections to make to a given host.
     **/
    private int HTTPMaximumConnectionsPerHost;

    public int getHTTPMaximumConnectionsPerHost() {
        return HTTPMaximumConnectionsPerHost;
    }

    public void setHTTPMaximumConnectionsPerHost(int HTTPMaximumConnectionsPerHost) {
        this.HTTPMaximumConnectionsPerHost = HTTPMaximumConnectionsPerHost;
    }

    /**
     * @Declaration : @property BOOL HTTPShouldSetCookies
     * @Description : A Boolean value that determines whether requests should contain cookies from
     * the cookie storage pool.
     **/
    private boolean HTTPShouldSetCookies;

    public boolean isHTTPShouldSetCookies() {
        return HTTPShouldSetCookies;
    }

    public void setHTTPShouldSetCookies(boolean HTTPShouldSetCookies) {
        this.HTTPShouldSetCookies = HTTPShouldSetCookies;
    }

    /**
     * @Declaration : @property BOOL HTTPShouldUsePipelining
     * @Description : A Boolean value that determines whether HTTP pipelining should be used.
     **/
    private boolean HTTPShouldUsePipelining;

    public boolean isHTTPShouldUsePipelining() {
        return HTTPShouldUsePipelining;
    }

    public void setHTTPShouldUsePipelining(boolean HTTPShouldUsePipelining) {
        this.HTTPShouldUsePipelining = HTTPShouldUsePipelining;
    }

    /**
     * @Declaration :  NSString *identifier
     * @Description : The background session identifier specified when creating the configuration
     * object. (read-only)
     * @Discussion If no value was specified when the object was created, this propertyâ€™s value is
     * NULL.
     **/
    private NSString identifier;

    public NSString getIdentifier() {
        return identifier;
    }

    public void setIdentifier(NSString identifier) {
        this.identifier = identifier;
    }

    /**
     * @Declaration : @property NSURLRequestNetworkServiceType networkServiceType
     * @Description : The type of network service.
     * @Discussion The network service type provides a hint to the operating system about what the
     * underlying traffic is used for. This hint
     * enhances the system's ability to prioritize traffic, determine how quickly it needs to wake
     * up the cellular or Wi-Fi
     * radio, and so on. By providing accurate information, you improve the ability of the system to
     * optimally balance battery
     * life, performance, and other considerations. For example, you should specify the
     * NSURLNetworkServiceTypeBackground type
     * if your app is performing a download that was not requested by the user, such as prefetching
     * content so that it will be
     * available when the user chooses to view it.
     **/
    private NSURLRequestNetworkServiceType networkServiceType;

    public NSURLRequestNetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(NSURLRequestNetworkServiceType networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    /**
     * @Declaration :  NSArray *protocolClasses
     * @Description : An optional array of class objects that subclass NSURLProtocol.
     * @Discussion Each object in this array is a class object obtained by calling the built-in
     * class class method on the class itself. For
     * example: @interface myClass ... @end ... id classObject = [myClass class]; When the
     * NSURLSession class needs to determine
     * whether the protocol can be used for a given URL scheme, it sends each class object a
     * canInitWithRequest: message. Do not
     * use the registerClass: method with NSURLSession, because that method registers your class
     * with the default session rather
     * than with an instance of NSURLSession. Note:Â Custom NSURLProtocol subclasses are not
     * available in background sessions.
     **/
    private NSArray protocolClasses;

    public NSArray getProtocolClasses() {
        return protocolClasses;
    }

    public void setProtocolClasses(NSArray protocolClasses) {
        this.protocolClasses = protocolClasses;
    }

    /**
     * @Declaration : @property NSURLRequestCachePolicy requestCachePolicy
     * @Description : A predefined constant that determines when to return a response from the
     * cache.
     * @Discussion The value stored in this property should be one of the constants defined in
     * NSURLRequestCachePolicy. These constants let
     * you specify whether the cache policy should depend on expiration dates and age, the cache
     * should be disabled entirely, or
     * the server should be contacted to determine whether the content has changed since it was last
     * requested.
     **/
    private int requestCachePolicy;// NSURLRequestCachePolicy

    public int getRequestCachePolicy() {
        return requestCachePolicy;
    }

    public void setRequestCachePolicy(int requestCachePolicy) {
        this.requestCachePolicy = requestCachePolicy;
    }

    /**
     * @Declaration : @property BOOL sessionSendsLaunchEvents
     * @Description : Whether the app should be resumed or launched in the background when needed.
     * @Discussion In sessions created based on this configuration object, if this property is YES,
     * the app is automatically relaunched in
     * the background when tasks finish or require authentication. This property applies only to
     * configurations created by
     * calling backgroundSessionConfiguration:. The default value is YES.
     **/
    private boolean sessionSendsLaunchEvents;

    public boolean isSessionSendsLaunchEvents() {
        return sessionSendsLaunchEvents;
    }

    public void setSessionSendsLaunchEvents(boolean sessionSendsLaunchEvents) {
        this.sessionSendsLaunchEvents = sessionSendsLaunchEvents;
    }

    /**
     * @Declaration : @property NSTimeInterval timeoutIntervalForRequest
     * @Description : The timeout interval to use when waiting for additional data.
     * @Discussion The timer associated with this value is reset whenever new data arrives. When the
     * request timer reaches the specified
     * interval without receiving any new data, it triggers a timeout.
     **/
    private double timeoutIntervalForRequest;

    public double getTimeoutIntervalForRequest() {
        return timeoutIntervalForRequest;
    }

    public void setTimeoutIntervalForRequest(double timeoutIntervalForRequest) {
        this.timeoutIntervalForRequest = timeoutIntervalForRequest;
    }

    public double getTimeoutIntervalForResource() {
        return timeoutIntervalForResource;
    }

    public void setTimeoutIntervalForResource(double timeoutIntervalForResource) {
        this.timeoutIntervalForResource = timeoutIntervalForResource;
    }

    public Object getTLSMaximumSupportedProtocol() {
        return TLSMaximumSupportedProtocol;
    }

    public void setTLSMaximumSupportedProtocol(Object TLSMaximumSupportedProtocol) {
        this.TLSMaximumSupportedProtocol = TLSMaximumSupportedProtocol;
    }

    public Object getTLSMinimumSupportedProtocol() {
        return TLSMinimumSupportedProtocol;
    }

    public void setTLSMinimumSupportedProtocol(Object TLSMinimumSupportedProtocol) {
        this.TLSMinimumSupportedProtocol = TLSMinimumSupportedProtocol;
    }

    public NSURLCache getURLCache() {
        return URLCache;
    }

    public void setURLCache(NSURLCache URLCache) {
        this.URLCache = URLCache;
    }

    public NSURLCredentialStorage getURLCredentialStorage() {
        return URLCredentialStorage;
    }

    public void setURLCredentialStorage(NSURLCredentialStorage URLCredentialStorage) {
        this.URLCredentialStorage = URLCredentialStorage;
    }

    /**
     * @Declaration : @property NSTimeInterval timeoutIntervalForResource
     * @Description : The maximum amount of time that a resource request should be allowed to take.
     * @Discussion This value controls how long to wait for an entire resource to transfer before
     * giving up. The resource timer starts when
     * the request is initiated and counts until either the request completes or this timeout
     * interval is reached, whichever
     * comes first.
     **/
    private double timeoutIntervalForResource;

    /**
     * @Declaration : @property SSLProtocol TLSMaximumSupportedProtocol
     * @Description : The maximum TLS protocol version that the client should request when making
     * connections in this session.
     **/
    private Object TLSMaximumSupportedProtocol;

    /**
     * @Declaration : @property SSLProtocol TLSMinimumSupportedProtocol
     * @Description : The minimum TLS protocol that should be accepted during protocol negotiation.
     **/
    private Object TLSMinimumSupportedProtocol;

    /**
     * @Declaration :  NSURLCache *URLCache
     * @Description : The URL cache for providing cached responses to requests within the session.
     * @Discussion To disable caching, set this property to NULL.
     **/
    private NSURLCache URLCache;

    /**
     * @Declaration :  NSURLCredentialStorage *URLCredentialStorage
     * @Description : A credential storage pool that provides stored credentials for
     * authentication.
     * @Discussion To not use a credential storage pool, set this property to NULL.
     **/
    private NSURLCredentialStorage URLCredentialStorage;

    @Override
    public NSObject copyWithZone(NSZone zone) {

        return null;
    }

}