
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.AndroidVersionUtils;
import com.myappconverter.mapping.utils.InstanceTypeFactory;

import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;



public class NSURL extends NSObject implements NSCopying, NSSecureCoding {


    // Enumeration


    public static enum NSURLBookmarkCreationOptions {
        NSURLBookmarkCreationPreferFileIDResolution(1L << 8), //
        NSURLBookmarkCreationMinimalBookmark(1L << 9), //
        NSURLBookmarkCreationSuitableForBookmarkFile(1L << 10);
        long value;

        NSURLBookmarkCreationOptions(long l) {
            value = l;
        }
    }

    ;


    public static enum NSURLBookmarkResolutionOptions {
        NSURLBookmarkResolutionWithoutUI(1L << 8), //
        NSURLBookmarkResolutionWithoutMounting(1L << 9);
        long value;

        NSURLBookmarkResolutionOptions(long l) {
            value = l;
        }
    }

    ;

    // File Property Keys
    public static final NSString NSURLFileSizeKey = new NSString("NSURLFileSizeKey");
    public static final NSString NSURLFileAllocatedSizeKey = new NSString("NSURLFileAllocatedSizeKey");
    public static final NSString NSURLIsAliasFileKey = new NSString("NSURLIsAliasFileKey");
    public static final NSString NSURLTotalFileAllocatedSizeKey = new NSString("NSURLTotalFileAllocatedSizeKey");
    public static final NSString NSURLTotalFileSizeKey = new NSString("NSURLTotalFileSizeKey");
    public static final NSString NSURLIsExcludedFromBackupKey = new NSString("NSURLIsExcludedFromBackupKey");

    public NSNumber fileSizeKey;
    public NSNumber fileAllocatedSizeKey;
    public boolean isAliasFileKey;
    public NSNumber totalFileAllocatedSizeKey;
    public NSNumber totalFileSizeKey;

    public NSNumber NSURLFileSizeKey() {
        return fileSizeKey;
    }

    public NSNumber NSURLFileAllocatedSizeKey() {
        return fileAllocatedSizeKey;
    }

    public boolean NSURLIsAliasFileKey() {
        return isAliasFileKey;
    }

    public NSNumber NSURLTotalFileAllocatedSizeKey() {
        return totalFileAllocatedSizeKey;
    }

    public NSNumber NSURLTotalFileSizeKey() {
        return totalFileSizeKey;
    }

    // File Resource Types
    public static final NSString NSURLFileResourceTypeNamedPipe = new NSString("NSURLFileResourceTypeNamedPipe");
    public static final NSString NSURLFileResourceTypeCharacterSpecial = new NSString("NSURLFileResourceTypeCharacterSpecial");
    public static final NSString NSURLFileResourceTypeDirectory = new NSString("NSURLFileResourceTypeDirectory");
    public static final NSString NSURLFileResourceTypeBlockSpecial = new NSString("NSURLFileResourceTypeBlockSpecial");
    public static final NSString NSURLFileResourceTypeRegular = new NSString("NSURLFileResourceTypeRegular");
    public static final NSString NSURLFileResourceTypeSymbolicLink = new NSString("NSURLFileResourceTypeSymbolicLink");
    public static final NSString NSURLFileResourceTypeSocket = new NSString("NSURLFileResourceTypeSocket");
    public static final NSString NSURLFileResourceTypeUnknown = new NSString("NSURLFileResourceTypeUnknown");

    // Volume Property Keys
    // Keys that apply to volumes

    public static final NSString NSURLVolumeLocalizedFormatDescriptionKey = new NSString("NSURLVolumeLocalizedFormatDescriptionKey");
    public static final NSString NSURLVolumeTotalCapacityKey = new NSString("NSURLVolumeTotalCapacityKey");
    public static final NSString NSURLVolumeAvailableCapacityKey = new NSString("NSURLVolumeAvailableCapacityKey");
    public static final NSString NSURLVolumeResourceCountKey = new NSString("NSURLVolumeResourceCountKey");
    public static final NSString NSURLVolumeSupportsPersistentIDsKey = new NSString("NSURLVolumeSupportsPersistentIDsKey");
    public static final NSString NSURLVolumeSupportsSymbolicLinksKey = new NSString("NSURLVolumeSupportsSymbolicLinksKey");
    public static final NSString NSURLVolumeSupportsHardLinksKey = new NSString("NSURLVolumeSupportsHardLinksKey");
    public static final NSString NSURLVolumeSupportsJournalingKey = new NSString("NSURLVolumeSupportsJournalingKey");
    public static final NSString NSURLVolumeIsJournalingKey = new NSString("NSURLVolumeIsJournalingKey");
    public static final NSString NSURLVolumeSupportsSparseFilesKey = new NSString("NSURLVolumeSupportsSparseFilesKey");
    public static final NSString NSURLVolumeSupportsZeroRunsKey = new NSString("NSURLVolumeSupportsZeroRunsKey");
    public static final NSString NSURLVolumeSupportsCaseSensitiveNamesKey = new NSString("NSURLVolumeSupportsCaseSensitiveNamesKey");
    public static final NSString NSURLVolumeSupportsCasePreservedNamesKey = new NSString("NSURLVolumeSupportsCasePreservedNamesKey");
    public static final NSString NSURLVolumeSupportsRootDirectoryDatesKey = new NSString("NSURLVolumeSupportsRootDirectoryDatesKey");
    public static final NSString NSURLVolumeSupportsVolumeSizesKey = new NSString("NSURLVolumeSupportsVolumeSizesKey");
    public static final NSString NSURLVolumeSupportsRenamingKey = new NSString("NSURLVolumeSupportsRenamingKey");
    public static final NSString NSURLVolumeSupportsAdvisoryFileLockingKey = new NSString("NSURLVolumeSupportsAdvisoryFileLockingKey");
    public static final NSString NSURLVolumeSupportsExtendedSecurityKey = new NSString("NSURLVolumeSupportsExtendedSecurityKey");
    public static final NSString NSURLVolumeIsBrowsableKey = new NSString("NSURLVolumeIsBrowsableKey");
    public static final NSString NSURLVolumeMaximumFileSizeKey = new NSString("NSURLVolumeMaximumFileSizeKey");
    public static final NSString NSURLVolumeIsEjectableKey = new NSString("NSURLVolumeIsEjectableKey");
    public static final NSString NSURLVolumeIsRemovableKey = new NSString("NSURLVolumeIsRemovableKey");
    public static final NSString NSURLVolumeIsInternalKey = new NSString("NSURLVolumeIsInternalKey");
    public static final NSString NSURLVolumeIsAutomountedKey = new NSString("NSURLVolumeIsAutomountedKey");
    public static final NSString NSURLVolumeIsLocalKey = new NSString("NSURLVolumeIsLocalKey");
    public static final NSString NSURLVolumeIsReadOnlyKey = new NSString("NSURLVolumeIsReadOnlyKey");
    public static final NSString NSURLVolumeCreationDateKey = new NSString("NSURLVolumeCreationDateKey");
    public static final NSString NSURLVolumeURLForRemountingKey = new NSString("NSURLVolumeURLForRemountingKey");
    public static final NSString NSURLVolumeUUIDStringKey = new NSString("NSURLVolumeUUIDStringKey");
    public static final NSString NSURLVolumeNameKey = new NSString("NSURLVolumeNameKey");
    public static final NSString NSURLVolumeLocalizedNameKey = new NSString("NSURLVolumeLocalizedNameKey");


    private URL wrappedURL;
    private NSString baseUrl;
    private NSString host;
    private NSString path;
    private int port;
    private NSString scheme;
    private NSString urlString;

    public NSURL(String url) {
        initWithString(new NSString(url));
    }

    public NSURL() {
        super();
    }

    public URL getWrappedURL() {
        return wrappedURL;
    }

    public void setWrappedURL(URL wrappedUrl) {
        this.wrappedURL = wrappedUrl;
    }

    public NSURL(NSString scheme, NSString host, NSString path) {

        try {
            wrappedURL = new URL(scheme.getWrappedString(), host.getWrappedString(), path.getWrappedString());

            this.baseUrl = new NSString(scheme.getWrappedString() + host.getWrappedString());
            this.host = host;
            this.path = path;
            this.scheme = scheme;
            urlString = new NSString(wrappedURL.toString());

        } catch (MalformedURLException e) {

            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }

    }

    public NSURL(NSString path) {
        initWithString(path);
    }

    public NSString getUrlString() {
        return urlString;
    }

    public void setUrlString(NSString urlString) {
        this.urlString = urlString;
    }

    /**
     * @return Return Value The host of the URL. If the receiver does not conform to RFC 1808,
     * returns nil. For example, in the URL
     * http://www.example.com/index.html, the host is www.example.com.
     * @Declaration : - (NSString *)host
     * @Description : Returns the host of a URL conforming to RFC 1808.
     * @Discussion If the URL conforms to RFC 1808 (the most common form of URL), this accessor
     * returns the specified URL component;
     * otherwise it returns nil. The litmus test for conformance to RFC 1808 is as recommended in
     * RFC 1808â€”specifically, whether
     * the first two characters of resourceSpecifier are slashes (//).
     **/

    public NSString host() {
        return new NSString(wrappedURL.getHost());
    }

    public void setHost(NSString host) {
        this.host = host;
    }

    /**
     * @return Return Value The path of the URL, unescaped with the stringByReplacingPercentEscapesUsingEncoding:
     * method. If the receiver
     * does not conform to RFC 1808, returns nil. If the URL object contains a file or file
     * reference URL (as determined with
     * isFileURL), the return value of this method is suitable for input into methods of
     * NSFileManager or NSPathUtilities. If the
     * path has a trailing slash, it is stripped. If the URL object contains a file reference URL,
     * the return value provides the
     * current path for the referenced resource, which may be nil if it no longer exists. Per RFC
     * 3986, the leading slash after the
     * authority (host name and port) portion is treated as part of the path. For example, in the
     * URL
     * http://www.example.com/index.html, the path is /index.html.
     * @Declaration : - (NSString *)path
     * @Description : Returns the path of a URL conforming to RFC 1808.
     * @Discussion If the parameterString method returns a non-nil value, the returned path may be
     * incomplete. In a URL whose path contains
     * an unencoded semicolon, the returned path ends at the character before the semicolon. The
     * remainder of the URL is
     * provided in the parameterString method. To obtain the complete path, if parameterString
     * returns a non-nil value, append a
     * semicolon, followed by the parameter string.
     **/

    public NSString path() {
        return path;
    }

    public void setPath(NSString path) {
        this.path = path;
    }

    /**
     * @return Return Value The port number of the URL. If the receiver does not conform to RFC
     * 1808, returns nil. For example, in the URL
     * http://www.example.com:8080/index.php, the port number is 8080.
     * @Declaration : - (NSNumber *)port
     * @Description : Returns the port number of a URL conforming to RFC 1808.
     * @Discussion If the URL conforms to RFC 1808 (the most common form of URL), this accessor
     * returns the specified URL component;
     * otherwise it returns nil. The litmus test for conformance to RFC 1808 is as recommended in
     * RFC 1808â€”specifically, whether
     * the first two characters of resourceSpecifier are slashes (//).
     **/

    public NSNumber port() {
        return NSNumber.numberWithInt(this.port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return Return Value The scheme of the URL. For example, in the URL
     * http://www.example.com/index.html, the scheme is http.
     * @Declaration : - (NSString *)scheme
     * @Description : Returns the scheme of the URL.
     * @Discussion The full URL is the concatenation of the scheme, a colon (:), and the value of
     * resourceSpecifier. Note:Â The term
     * â€œprotocolâ€? is also sometimes used when talking about network-based URL schemes. However, not
     * all URL schemes are
     * networking protocolsâ€”data:// URLs, for example.
     **/

    public NSString scheme() {
        return new NSString(wrappedURL.getProtocol());
    }

    public void setScheme(NSString scheme) {
        this.scheme = scheme;
    }

    /**
     * @param scheme The scheme for the NSURL object. For example, in the URL
     *               http://www.example.com/index.html, the scheme is http.
     * @param host   The host for the NSURL object (for example, www.example.com). May be the empty
     *               string.
     * @param path   The path for the NSURL object (for example, /index.html). If the path begins
     *               with a tilde, you must first expand it by
     *               calling stringByExpandingTildeInPath.
     * @return Return Value The newly initialized NSURL object.
     * @Declaration : - (id)initWithScheme:(NSString *)scheme host:(NSString *)host path:(NSString
     * *)path
     * @Description : Initializes a newly created NSURL with a specified scheme, host, and path.
     * @Discussion This method automatically uses percent encoding to escape the path and host
     * parameters.
     **/


    public Object initWithScheme(NSString scheme, NSString host, NSString path) {
        return new NSURL(scheme, host, path);
    }

    /**
     * @param URLString The URL string with which to initialize the NSURL object. Must be a URL
     *                  that
     *                  conforms to RFC 2396. This method
     *                  parses URLString according to RFCs 1738 and 1808.
     * @return Return Value An NSURL object initialized with URLString. If the URL string was
     * malformed or nil, returns nil.
     * @Declaration : + (id)URLWithString:(NSString *)URLString
     * @Description : Creates and returns an NSURL object initialized with a provided URL string.
     * @Discussion This method expects URLString to contain only characters that are allowed in a
     * properly formed URL. All other characters
     * must be properly percent escaped. Any percent-escaped characters are interpreted using UTF-8
     * encoding. Important:Â To
     * create NSURL objects for file system paths, use fileURLWithPath:isDirectory: instead.
     * Note:Â In OS X v10.7 and later or
     * iOS 5 and later, this method returns nil if the URL string is nil. In earlier versions, this
     * method throws an exception
     * if the URL string is nil.
     **/


    public static NSURL URLWithString(Class clazz, NSString URLString) {
        NSURL url = (NSURL) InstanceTypeFactory.getInstance(clazz);
        url.initWithString(URLString);
        return url;
    }

    /**
     * @param URLString The URL string with which to initialize the NSURL object. This URL string
     *                  must conform to URL format as described in
     *                  RFC 2396, and must not be nil. This method parses URLString according to
     *                  RFCs 1738 and 1808.
     * @return Return Value An NSURL object initialized with URLString. If the URL string was
     * malformed, returns nil.
     * @Declaration : - (id)initWithString:(NSString *)URLString
     * @Description : Initializes an NSURL object with a provided URL string.
     * @Discussion This method expects URLString to contain only characters that are allowed in a
     * properly formed URL. All other characters
     * must be properly percent escaped. Any percent-escaped characters are interpreted using UTF-8
     * encoding. Note:Â This method
     * throws an exception if the URL string is nil.
     **/


    public Object initWithString(NSString urlString) {
        this.urlString = urlString;
        this.path = urlString;
        try {
            this.wrappedURL = new URL(urlString.getWrappedString());
        } catch (MalformedURLException e) {
            this.urlString = urlString;// string referring to an asset file or a drawable
        }
        return this;

    }

    /**
     * @param URLString The URL string with which to initialize the NSURL object. May not be nil.
     *                  Must conform to RFC 2396. URLString is
     *                  interpreted relative to baseURL.
     * @param baseURL   The base URL for the NSURL object.
     * @return Return Value An NSURL object initialized with URLString and baseURL. If URLString was
     * malformed or nil, returns nil.
     * @Declaration : + (id)URLWithString:(NSString *)URLString relativeToURL:(NSURL *)baseURL
     * @Description : Creates and returns an NSURL object initialized with a base URL and a
     * relative
     * string.
     * @Discussion This method allows you to create a URL relative to a base path or URL. For
     * example, if you have the URL for a folder on
     * disk and the name of a file within that folder, you can construct a URL for the file by
     * providing the folderâ€™s URL as the
     * base path (with a trailing slash) and the filename as the string part. This method expects
     * URLString to contain only
     * characters that are allowed in a properly formed URL. All other characters must be properly
     * percent escaped. Any
     * percent-escaped characters are interpreted using UTF-8 encoding. Note:Â In OS X v10.7 and
     * later or iOS 5 and later, this
     * method returns nil if the URL string is nil. In earlier versions, this method throws an
     * exception if the URL string is
     * nil.
     **/


    public static Object URLWithString(NSString URLString, NSURL baseURL) {
        String urlString = URLString.getWrappedString();
        String basePathUrl = baseURL.path().getWrappedString();

        return new NSURL(baseURL.scheme(), baseURL.host(), new NSString(basePathUrl + urlString));
    }

    /**
     * @param URLString The URL string with which to initialize the NSURL object. Must conform to
     *                  RFC 2396. URLString is interpreted
     *                  relative to baseURL.
     * @param baseURL   The base URL for the NSURL object.
     * @return Return Value An NSURL object initialized with URLString and baseURL. If URLString was
     * malformed, returns nil.
     * @Declaration : - (id)initWithString:(NSString *)URLString relativeToURL:(NSURL *)baseURL
     * @Description : Initializes an NSURL object with a base URL and a relative string.
     * @Discussion This method allows you to create a URL relative to a base path or URL. For
     * example, if you have the URL for a folder on
     * disk and the name of a file within that folder, you can construct a URL for the file by
     * providing the folderâ€™s URL as the
     * base path (with a trailing slash) and the filename as the string part. This method expects
     * URLString to contain only
     * characters that are allowed in a properly formed URL. All other characters must be properly
     * percent escaped. Any
     * percent-escaped characters are interpreted using UTF-8 encoding. Note:Â This method throws an
     * exception if the URL string
     * is nil. initWithString:relativeToURL: is the designated initializer for NSURL.
     **/


    public Object initWithString(NSString URLString, NSURL baseURL) {
        String urlString = URLString.getWrappedString();
        String basePathUrl = baseURL.path().getWrappedString();

        return new NSURL(baseURL.scheme(), baseURL.host(), new NSString(basePathUrl + urlString));
    }

    /**
     * @param path  The path that the NSURL object will represent. path should be a valid system
     *              path. If path begins with a tilde, it must
     *              first be expanded with stringByExpandingTildeInPath. If path is a relative
     *              path,
     *              it is treated as being relative to the
     *              current working directory. Passing nil for this parameter produces an
     *              exception.
     * @param isDir A Boolean value that specifies whether path is treated as a directory path when
     *              resolving against relative path
     *              components. Pass YES if the path indicates a directory, NO otherwise.
     * @return Return Value An NSURL object initialized with path.
     * @Declaration : + (id)fileURLWithPath:(NSString *)path isDirectory:(BOOL)isDir
     * @Description : Initializes and returns a newly created NSURL object as a file URL with a
     * specified path.
     **/

    public static NSURL fileURLWithPath(NSString path, boolean isDir) {

        try {
            File f = new File(path.getWrappedString());
            if (f.isDirectory() && f.mkdir()) {
                return new NSURL(path);
            }
        } catch (Exception e) {
            Logger.getLogger(NSURL.class.getName(), String.valueOf(e));
            return null;
        }

        return null;
    }

    /**
     * @param path  The path that the NSURL object will represent. path should be a valid system
     *              path. If path begins with a tilde, it must
     *              first be expanded with stringByExpandingTildeInPath. If path is a relative
     *              path,
     *              it is treated as being relative to the
     *              current working directory. Passing nil for this parameter produces an
     *              exception.
     * @param isDir A Boolean value that specifies whether path is treated as a directory path when
     *              resolving against relative path
     *              components. Pass YES if the path indicates a directory, NO otherwise
     * @return Return Value An NSURL object initialized with path.
     * @Declaration : - (id)initFileURLWithPath:(NSString *)path isDirectory:(BOOL)isDir
     * @Description : Initializes a newly created NSURL referencing the local file or directory at
     * path.
     * @Discussion Invoking this method is equivalent to invoking initWithScheme:host:path: with
     * scheme NSURLFileScheme, a nil host, and
     * path.
     **/

    public NSURL initFileURLWithPathIsDirectory(NSString path, boolean isDir) {
        try {
            NSURL mUrl = new NSURL();
            mUrl.urlString = path;
            mUrl.setUrl(new URL("file://" + path.getWrappedString()));
            return mUrl;
        } catch (MalformedURLException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @param path The path that the NSURL object will represent. path should be a valid system
     *             path. If path begins with a tilde, it must
     *             first be expanded with stringByExpandingTildeInPath. If path is a relative path,
     *             it is treated as being relative to the
     *             current working directory. Passing nil for this parameter produces an exception.
     * @return Return Value An NSURL object initialized with path.
     * @Declaration : + (id)fileURLWithPath:(NSString *)path
     * @Description : Initializes and returns a newly created NSURL object as a file URL with a
     * specified path.
     * @Discussion This method assumes that path is a directory if it ends with a slash. If path
     * does not end with a slash, the method
     * examines the file system to determine if path is a file or a directory. If path exists in
     * the
     * file system and is a
     * directory, the method appends a trailing slash. If path does not exist in the file system,
     * the method assumes that it
     * represents a file and does not append a trailing slash. As an alternative, consider using
     * fileURLWithPath:isDirectory:,
     * which allows you to explicitly specify whether the returned NSURL object represents a file
     * or
     * directory.
     **/


    public static NSURL fileURLWithPath(NSString path) {
        if (path != null) {
            if (path.getWrappedString() == null || !AndroidVersionUtils.isInteger(path.getWrappedString())) {
                path.setWrappedString(path.getWrappedString().replace("assets ", ""));
                String[] bits = path.getWrappedString().split("/");
                String lastOne = bits[bits.length - 1];
                return NSBundle.mainBundle().URLForResourceWithExtension(new NSString(lastOne), null);
            } else {
                NSURL mUrl = new NSURL(path.getWrappedString());
                return mUrl;
            }
        }
        return null;

    }

    /**
     * @param path The path that the NSURL object will represent. path should be a valid system
     *             path. If path begins with a tilde, it must
     *             first be expanded with stringByExpandingTildeInPath. If path is a relative path,
     *             it is treated as being relative to the
     *             current working directory. Passing nil for this parameter produces an exception.
     * @return Return Value An NSURL object initialized with path.
     * @Declaration : - (id)initFileURLWithPath:(NSString *)path
     * @Description : Initializes a newly created NSURL referencing the local file or directory at
     * path.
     * @Discussion Invoking this method is equivalent to invoking initWithScheme:host:path: with
     * scheme NSURLFileScheme, a nil host, and
     * path. This method assumes that path is a directory if it ends with a slash. If path does not
     * end with a slash, the method
     * examines the file system to determine if path is a file or a directory. If path exists in
     * the
     * file system and is a
     * directory, the method appends a trailing slash. If path does not exist in the file system,
     * the method assumes that it
     * represents a file and does not append a trailing slash. As an alternative, consider using
     * initFileURLWithPath:isDirectory:, which allows you to explicitly specify whether the
     * returned
     * NSURL object represents a
     * file or directory.
     **/


    public NSURL initFileURLWithPath(NSString path) {
        try {
            NSURL mUrl = new NSURL();
            mUrl.urlString = path;
            mUrl.setUrl(new URL("file://" + path.getWrappedString()));
            return mUrl;
        } catch (MalformedURLException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @param components An array of path components. Passing nil for this parameter produces an
     *                   exception.
     * @return Return Value An NSURL object initialized with components.
     * @Declaration : + (NSURL *)fileURLWithPathComponents:(NSArray *)components
     * @Description : Initializes and returns a newly created NSURL object as a file URL with
     * specified path components.
     * @Discussion The path components are separated by a forward slash in the returned URL.
     **/


    public URL fileURLWithPathComponents(NSArray<NSString> component) {
        return null;
    }

    /**
     * @param bookmarkData The bookmark data the URL is derived from.
     * @param options      Options taken into account when resolving the bookmark data. To resolve
     *                     a
     *                     security-scoped bookmark to support App
     *                     Sandbox, you must include (by way of bitwise OR operators with any other
     *                     options in this parameter) the
     *                     NSURLBookmarkResolutionWithSecurityScope option.
     * @param relativeURL  The base URL that the bookmark data is relative to. If you are resolving
     *                     a security-scoped bookmark to obtain a
     *                     security-scoped URL, use this parameter as follows: To resolve an
     *                     app-scoped bookmark, use a value of nil. To resolve a
     *                     document-scoped bookmark, use the absolute path (despite this
     *                     parameterâ€™s
     *                     name) to the document from which you retrieved
     *                     the bookmark.
     * @param isStale      On return, if YES, the bookmark data is stale. Your app should create a
     *                     new bookmark using the returned URL and use it
     *                     in place of any stored copies of the existing bookmark.
     * @param error        The error that occurred in the case that the URL cannot be created.
     * @return Return Value A new URL made by resolving bookmarkData.
     * @Declaration : + (id)URLByResolvingBookmarkData:(NSData *)bookmarkData
     * options:(NSURLBookmarkResolutionOptions)options
     * relativeToURL:(NSURL *)relativeURL bookmarkDataIsStale:(BOOL *)isStale error:(NSError
     * **)error
     * @Description : Returns a new URL made by resolving bookmark data.
     * @Discussion This method fails if the original file or directory could not be located or is
     * on
     * a volume that could not be mounted. If
     * this method fails, you can use the resourceValuesForKeys:fromBookmarkData: method to obtain
     * information about the
     * bookmark, such as the last known path (NSURLPathKey) to help the user decide how to proceed.
     * To obtain a security-scoped
     * URL from a security-scoped bookmark, call this method using the
     * NSURLBookmarkResolutionWithSecurityScope option. In
     * addition, to use security scope, you must first have enabled the appropriate entitlements
     * for
     * your app, as described in
     * â€œEnabling Security-Scoped Bookmark and URL Accessâ€? in Entitlement Key Reference. To then
     * obtain access to the file-system
     * resource pointed to by a security-scoped URL (in other words, to bring the resource into
     * your
     * appâ€™s sandbox), call the
     * startAccessingSecurityScopedResource method (or its Core Foundation equivalent) on the URL.
     * For an app-scoped bookmark,
     * no sandboxed app other than the one that created the bookmark can obtain access to the
     * file-system resource that the URL
     * (obtained from the bookmark) points to. For a document-scoped bookmark, any sandboxed app
     * that has access to the bookmark
     * data itself, and has access to the document that owns the bookmark, can obtain access to the
     * resource. Version
     * note:Â Security-scoped bookmarks are not available in versions of OS X prior to OS X v10.7.3.
     **/

    public static URL URLByResolvingBookmarkData(NSData bookmarkData, NSURLBookmarkResolutionOptions options, URL relativeURL,
                                                 boolean isStale, NSError[] error) {
        return null;// TODO

    }

    /**
     * @param bookmarkData The bookmark data the URL is derived from.
     * @param options      Options taken into account when resolving the bookmark data.
     * @param relativeURL  The base URL that the bookmark data is relative to.
     * @param isStale      If YES, the bookmark data is stale.
     * @param error        The error that occurred in the case that the URL cannot be created.
     * @return Return Value An NSURL initialized by resolving bookmarkData.
     * @Declaration : - (id)initByResolvingBookmarkData:(NSData *)bookmarkData
     * options:(NSURLBookmarkResolutionOptions)options
     * relativeToURL:(NSURL *)relativeURL bookmarkDataIsStale:(BOOL *)isStale error:(NSError
     * **)error
     * @Description : Initializes a newly created NSURL that points to a location specified by
     * resolving bookmark data.
     **/


    public URL initByResolvingBookmarkData(NSData bookmarkData, NSURLBookmarkResolutionOptions options, URL relativeURL, boolean isStale,
                                           NSError[] error) {
        return null;// TODO

    }

    /**
     * @param path    A null-terminated C string in file system representation containing the path
     *                to represent as a URL. If this path is a
     *                relative path, it is treated as being relative to the current working
     *                directory.
     * @param isDir   YES if the last path part is a directory, otherwise NO.
     * @param baseURL The base URL for the new URL object. This must be a file URL. If path is
     *                absolute, this URL is ignored.
     * @return Return Value Returns the new object or nil if an error occurred.
     * @Declaration : + (id)fileURLWithFileSystemRepresentation:(const char *)path
     * isDirectory:(BOOL)isDir relativeToURL:(NSURL *)baseURL
     * @Description : Returns a new URL object initialized with a C string representing a local
     * file
     * system path.
     * @Discussion The file system representation format is described in â€œFile Encodings and
     * Fontsâ€?.
     **/


    public static URL fileURLWithFileSystemRepresentation(char path, boolean isDir, URL baseURL) {
        return null;

    }

    /**
     * @param buffer          A buffer large enough to hold the path. On return, contains a
     *                        null-terminated C string in file system representation.
     * @param maxBufferLength The size of buffer in bytes (typically MAXPATHLEN or PATH_MAX).
     * @return Return Value Returns YES if the URL could be converted into a file system
     * representation, otherwise NO.
     * @Declaration : - (BOOL)getFileSystemRepresentation:(char *)buffer
     * maxLength:(NSUInteger)maxBufferLength
     * @Description : Fills the provided buffer with a C string representing a local file system
     * path.
     * @Discussion The file system representation format is described in â€œFile Encodings and
     * Fontsâ€?.
     **/


    public boolean getFileSystemRepresentation(char buffer, Integer maxBufferLength) {
        return false;

    }

    /**
     * @param path    A null-terminated C string in file system representation containing the path
     *                to represent as a URL. If this path is a
     *                relative path, it is treated as being relative to the current working
     *                directory.
     * @param isDir   YES if the last path part is a directory, otherwise NO.
     * @param baseURL The base URL for the new URL object. This must be a file URL. If path is
     *                absolute, this URL is ignored.
     * @return Return Value Returns the initialized object or nil if an error occurred.
     * @Declaration : - (id)initFileURLWithFileSystemRepresentation:(const char *)path
     * isDirectory:(BOOL)isDir relativeToURL:(NSURL
     * *)baseURL
     * @Description : Initializes a URL object with a C string representing a local file system
     * path.
     * @Discussion The file system representation format is described in â€œFile Encodings and
     * Fontsâ€?.
     **/


    public URL initFileURLWithFileSystemRepresentation(char path, boolean isDir, URL baseURL) {
        return null;

    }

    /**
     * @param anObject The object to be compared to the receiver.
     * @return Return Value YES if the receiver and anObject are equal, otherwise NO.
     * @Declaration : -Â (BOOL)isEqual:(id)anObject
     * @Description : Returns a Boolean value that indicates whether the receiver and a given
     * object
     * have identical URL strings and base
     * URLs.
     * @Discussion This method defines what it means for instances to be equal. Two NSURLs are
     * considered equal if and only if they return
     * identical values for both baseURL and relativeString.
     **/


    public boolean isEqual(URL anObject) {
        return false;
    }

    /**
     * @param value The location where the value for the resource property identified by key should
     *              be stored.
     * @param key   The name of one of the URLâ€™s resource properties.
     * @param error The error that occurred in the case that the resource value cannot be
     *              retrieved.
     * @return Return Value YES if value is successfully populated; otherwise, NO.
     * @Declaration : - (BOOL)getResourceValue:(out id *)value forKey:(NSString *)key error:(out
     * NSError **)error
     * @Description : Returns the value of the resource property for the specified key.
     * @Discussion value is set to nil if the requested resource value is not defined for the URL.
     * In this case, the method still returns
     * YES. This method first checks if the URL object already caches the resource value. If so, it
     * returns the cached resource
     * value to the caller. If not, then this method synchronously obtains the resource value from
     * the backing store, adds the
     * resource value to the URL object's cache, and returns the resource value to the caller. The
     * type of the returned resource
     * value varies by resource property; for details, see the documentation for the key you want
     * to
     * access. If this method
     * returns YES and the value is populated with nil, it means that the resource property is not
     * available for the specified
     * resource, and that no errors occurred when determining that the resource property was
     * unavailable. If this method returns
     * NO, the object pointer referenced by error is populated with additional information.
     * Note:Â This method applies only to
     * URLs that represent file system resources.
     **/

    public boolean getResourceValue(Object value, String key, NSError[] error) {
        return true;// TODO
    }

    /**
     * @param keys  An array of names of URL resource properties.
     * @param error The error that occurred in the case that one or more resource values cannot be
     *              retrieved.
     * @return Return Value A dictionary of resource values indexed by key.
     * @Declaration : - (NSDictionary *)resourceValuesForKeys:(NSArray *)keys error:(NSError
     * **)error
     * @Description : Returns the resource values for the properties identified by specified array
     * of keys.
     * @Discussion This method first checks if the URL object already caches the specified resource
     * values. If so, it returns the cached
     * resource values to the caller. If not, then this method synchronously obtains the resource
     * values from the backing store,
     * adds the resource values to the URL object's cache, and returns the resource values to the
     * caller. The type of the
     * returned resource value varies by resource property; for details, see the documentation for
     * the key you want to access.
     * If the result dictionary does not contain a resource value for one or more of the requested
     * resource keys, it means those
     * resource properties are not available for the specified resource and no errors occurred when
     * determining those resource
     * properties were not available. If an error occurs, this method returns nil and populates the
     * object pointer referenced by
     * error with additional information.
     **/


    public Map<String, Object> resourceValuesForKeys(List<String> keys, NSError[] error) {
        // TODO
        return null;
    }

    /**
     * @param value The value for the resource property defined by key.
     * @param key   The name of one of the URLâ€™s resource properties.
     * @param error The error that occurred in the case that the resource value cannot be set.
     * @return Return Value YES if the resource property named key is successfully set to value;
     * otherwise, NO.
     * @Declaration : - (BOOL)setResourceValue:(id)value forKey:(NSString *)key error:(NSError
     * **)error
     * @Description : Sets the resource property of the URL specified by a given key to a given
     * value.
     * @Discussion This method synchronously writes the new resource value out to disk. Attempts to
     * set a read-only resource property or to
     * set a resource property that is not supported by the resource are ignored and are not
     * considered errors. Note:Â This
     * method applies only to URLs for file system resources.
     **/


    public boolean setResourceValue(Object value, NSString key, NSError[] error) {
        return false;// TODO
    }

    /**
     * @param keyedValues A dictionary of resource values to be set.
     * @param error       The error that occurred in the case that one or more resource values
     *                    cannot be set.
     * @return Return Value YES if all resource values in keyedValues are successfully set;
     * otherwise, NO.
     * @Declaration : - (BOOL)setResourceValues:(NSDictionary *)keyedValues error:(NSError **)error
     * @Description : Sets resource properties of the URL specified by a given set of keys to a
     * given set of values.
     * @Discussion This method synchronously writes the new resource value out to disk. If an error
     * occurs after some resource properties
     * have been successfully changed, the userInfo dictionary in the returned error object
     * contains
     * a
     * kCFURLKeysOfUnsetValuesKey key whose value is an array of the resource values that were not
     * successfully set. Attempts to
     * set a read-only resource property or to set a resource property that is not supported by the
     * resource are ignored and are
     * not considered errors. The order in which the resource values are set is not defined. If you
     * need to guarantee the order
     * in which resource values are set, you should make multiple requests to this method or
     * setResourceValue:forKey:error:.
     * Note:Â This method applies only to URLs for file system resources.
     **/


    public boolean setResourceValues(HashMap<String, Object> keyedValues, NSError[] error) {
        return false;// TODO
    }

    /**
     * @Declaration : - (void)removeAllCachedResourceValues
     * @Description : Removes all cached resource values and temporary resource values from the URL
     * object.
     * @Discussion This method is applicable only to URLs that represent file system resources.
     * Note:Â At the NSURL level, all cached values
     * (not temporary values) are automatically removed every time the run loop runs. This method
     * is
     * only necessary if you need
     * to clear the cache during a single run of the run loop.
     **/

    public void removeAllCachedResourceValues() {

    }

    /**
     * @param key The resource value key whose cached values you wish to remove.
     * @Declaration : - (void)removeCachedResourceValueForKey:(NSString *)key
     * @Description : Removes the cached resource value identified by a given resource value key
     * from the URL object.
     * @Discussion Removing a cached resource value may remove other cached resource values because
     * some resource values are cached as a set
     * of values, and because some resource values depend on other resource values. (Temporary
     * resource values have no
     * dependencies.) This method is currently applicable only to URLs for file system resources.
     * Note:Â At the NSURL level, all
     * cached values (not temporary values) are automatically removed every time the run loop runs.
     * This method is only
     * necessary if you need to clear the cache during a single run of the run loop.
     **/

    public void removeCachedResourceValueForKey(NSString key) {

    }

    /**
     * @param value The value to store.
     * @param key   The key where the value should be stored. This key must be unique and must not
     *              conflict with any system-defined keys.
     *              Reverse-domain-name notation is recommended.
     * @Declaration : - (void)setTemporaryResourceValue:(id)value forKey:(NSString *)key
     * @Description : Sets a temporary resource value on the URL object.
     * @Discussion Your app can use a temporary resource value to temporarily store a value for an
     * app-defined resource value key in memory
     * without modifying the actual resource that the URL represents. Once set, you can copy the
     * temporary resource value from
     * the URL object just as you would copy system-defined keysâ€”by calling
     * getResourceValue:forKey:error: or
     * resourceValuesForKeys:error:. Your app can remove a temporary resource value from the URL
     * object by calling
     * removeCachedResourceValueForKey: or removeAllCachedResourceValues (to remove all temporary
     * values). This method is
     * applicable only to URLs for file system resources.
     **/


    public void setTemporaryResourceValue(Object value, String key) {
    }

    // Modifying and Converting a File URL


    public NSURL filePathURL() {
        // FIXME
        NSURL tmp = (NSURL) new NSURL().initWithString(new NSString("file://pathtofile"));
        return tmp;
    }


    public NSURL fileReferenceURL() {
        // FIXME
        NSURL tmp = (NSURL) new NSURL().initWithString(new NSString("file://pathtofile"));
        return tmp;
    }

    /**
     * @param pathComponent The path component to add to the URL, in its original form (not URL
     *                      encoded).
     * @return Return Value A new URL with pathComponent appended.
     * @Declaration : - (NSURL *)URLByAppendingPathComponent:(NSString *)pathComponent
     * @Description : Returns a new URL made by appending a path component to the original URL.
     * @Discussion If the original URL does not end with a forward slash and pathComponent does not
     * begin with a forward slash, a forward
     * slash is inserted between the two parts of the returned URL, unless the original URL is the
     * empty string.
     **/

    public NSURL URLByAppendingPathComponent(NSString pathComponent) {
        Uri.Builder builder = Uri.parse(baseUrl.getWrappedString()).buildUpon();
        builder.path(pathComponent.getWrappedString());
        NSURL res = new NSURL(baseUrl);
        return res;
    }

    /**
     * @param pathComponent The path component to add to the URL.
     * @param isDirectory   If YES, a trailing slash is appended after pathComponent.
     * @return Return Value A new URL with pathComponent appended.
     * @Declaration : - (NSURL *)URLByAppendingPathComponent:(NSString *)pathComponentisDirectory
     * isDirectory:(BOOL)isDirectory
     * @Description : Returns a new URL made by appending a path component to the original URL,
     * along with a trailing slash if the component
     * is designated a directory.
     * @Discussion If the original URL does not end with a forward slash and pathComponent does not
     * begin with a forward slash, a forward
     * slash is inserted between the two parts of the returned URL, unless the original URL is the
     * empty string.
     **/


    public NSURL URLByAppendingPathComponent(NSString pathComponent, boolean isDirectory) {
        Uri.Builder builder = Uri.parse(baseUrl.getWrappedString()).buildUpon();
        builder.path(pathComponent.getWrappedString());
        if (isDirectory)
            builder.appendPath("/");
        NSURL res = new NSURL(baseUrl);
        return res;
    }

    /**
     * @param pathExtension The path extension to add to the URL.
     * @return Return Value A new URL with pathExtension appended.
     * @Declaration : - (NSURL *)URLByAppendingPathExtension:(NSString *)pathExtension
     * @Description : Returns a new URL made by appending a path extension to the original URL.
     * @Discussion If the original URL ends with one or more forward slashes, these are removed
     * from
     * the returned URL. A period is inserted
     * between the two parts of the new URL.
     **/


    public NSURL URLByAppendingPathExtension(NSString pathExtension) {
        Uri.Builder builder = Uri.parse(baseUrl.getWrappedString()).buildUpon();
        builder.appendPath(pathExtension.getWrappedString());
        NSURL res = new NSURL(baseUrl);
        return res;
    }

    /**
     * @return Return Value A new URL with the last path component of the original URL removed.
     * @Declaration : - (NSURL *)URLByDeletingLastPathComponent
     * @Description : Returns a new URL made by deleting the last path component from the original
     * URL.
     * @Discussion If the original URL represents the root path, the returned URL is identical.
     * Otherwise, if the original URL has only one
     * path component, the new URL is the empty string.
     **/

    public NSURL URLByDeletingLastPathComponent() {
        return null;
    }

    /**
     * @return Return Value A new URL with the path extension of the original URL removed.
     * @Declaration : - (NSURL *)URLByDeletingPathExtension
     * @Description : Returns a new URL made by deleting the path extension, if any, from the
     * original URL.
     * @Discussion If the original URL represents the root path, the returned URL is identical. If
     * the URL has multiple path extensions,
     * only the last one is removed.
     **/

    public NSURL URLByDeletingPathExtension() {
        return null;
    }

    /**
     * @return Return Value A new URL that points to the same resource as the original URL and
     * includes no symbolic links.
     * @Declaration : - (NSURL *)URLByResolvingSymlinksInPath
     * @Description : Returns a new URL that points to the same resource as the original URL and
     * includes no symbolic links.
     * @Discussion If the original URL has no symbolic links, the returned URL is identical to the
     * original URL. If some symbolic links
     * cannot be resolved, the returned URL contains those broken symbolic links. If the name of
     * the
     * receiving path begins with
     * /private, this method strips off the /private designator, provided the result is the name of
     * an existing file. This
     * method only works on URLs with the file: path scheme. This method will return an identical
     * URL for all other URLs.
     **/

    public NSURL URLByResolvingSymlinksInPath() {

        return null;
    }

    /**
     * @return Return Value A new URL that points to the same resource as the original URL and is an
     * absolute path.
     * @Declaration : - (NSURL *)URLByStandardizingPath
     * @Description : Returns a new URL that points to the same resource as the original URL and is
     * an absolute path.
     * @Discussion This method only works on URLs with the file: path scheme. This method will
     * return an identical URL for all other URLs.
     * Like stringByStandardizingPath, this method can make the following changes in the provided
     * URL: Expand an initial tilde
     * expression using stringByExpandingTildeInPath. Reduce empty components and references to the
     * current directory (that is,
     * the sequences â€œ//â€? and â€œ/./â€?) to single path separators. In absolute paths only, resolve
     * references to the parent
     * directory (that is, the component â€œ..â€?) to the real parent directory if possible using
     * stringByResolvingSymlinksInPath,
     * which consults the file system to resolve each potential symbolic link. In relative paths,
     * because symbolic links canâ€™t
     * be resolved, references to the parent directory are left in place. Remove an initial
     * component of â€œ/privateâ€? from the
     * path if the result still indicates an existing file or directory (checked by consulting the
     * file system). Note that the
     * path returned by this method may still have symbolic link components in it. Note also that
     * this method only works with
     * file paths (not, for example, string representations of URLs).
     **/

    public NSURL URLByStandardizingPath() {
        return null;
    }

    // Working with Bookmark Data

    /**
     * @param options     Options taken into account when creating the bookmark for the URL. The
     *                    possible flags (which can be combined with
     *                    bitwise OR operations) are described in â€œBookmark Data Creation Options.â€?
     *                    To create a security-scoped bookmark to support
     *                    App Sandbox, include the NSURLBookmarkCreationWithSecurityScope flag.
     *                    When
     *                    you later resolve the bookmark, you can use the
     *                    resulting security-scoped URL to obtain read/write access to the
     *                    file-system resource pointed to by the URL. If you
     *                    instead want to create a security-scoped bookmark that, when resolved,
     *                    enables you to obtain read-only access to a
     *                    file-system resource, bitwise OR this parameterâ€™s value with both the
     *                    NSURLBookmarkCreationWithSecurityScope option and
     *                    the NSURLBookmarkCreationSecurityScopeAllowOnlyReadAccess option.
     * @param keys        An array of names of URL resource properties to store as part of the
     *                    bookmark. You can later access these values (without
     *                    resolving the bookmark) by calling the resourceValuesForKeys:fromBookmarkData:
     *                    method. The values of these properties must
     *                    be of a type that the bookmark generation code can serialize.
     *                    Specifically, the values can contain any of the following
     *                    primitive types: NSString or CFString NSData or CFData NSDate or CFDate
     *                    NSNumber or CFNumber CFBoolean NSURL or CFURL
     *                    kCFNull or NSNull CFUUID In addition, the properties can contain the
     *                    following collection classes: NSArray or CFArray
     *                    containing only the above primitive types NSDictionary or CFDictionary
     *                    with NSString or CFString keys, in which all values
     *                    contain only the above primitive types
     * @param relativeURL The URL that the bookmark data will be relative to. If you are creating a
     *                    security-scoped bookmark to support App
     *                    Sandbox, use this parameter as follows: To create an app-scoped bookmark,
     *                    use a value of nil. To create a document-scoped
     *                    bookmark, use the absolute path (despite this parameterâ€™s name) to the
     *                    document file that is to own the new
     *                    security-scoped bookmark.
     * @param error       The error that occurred in the case that the bookmark data cannot be
     *                    created.
     * @return Return Value A bookmark for the URL.
     * @Declaration : - (NSData *)bookmarkDataWithOptions:(NSURLBookmarkCreationOptions)options
     * includingResourceValuesForKeys:(NSArray
     * *)keys relativeToURL:(NSURL *)relativeURL error:(NSError **)error
     * @Description : Returns a bookmark for the URL, created with specified options and resource
     * values.
     * @Discussion This method returns bookmark data that can later be resolved into a URL object
     * for a file even if the user moves or
     * renames it (if the volume format on which the file resides supports doing so). Note:Â If the
     * specified URL is not a file
     * URL, this method returns a bookmark containing only the URL, and the options and keys
     * parameters are ignored. You can
     * also use this method to create a security-scoped bookmark to support App Sandbox. Before you
     * do so, you must first enable
     * the appropriate entitlements for your app, as described in â€œEnabling Security-Scoped
     * Bookmark
     * and URL Accessâ€? in
     * Entitlement Key Reference. In addition, be sure to understand the behavior of the options
     * and
     * relativeURL parameters. For
     * an app-scoped bookmark, no sandboxed app other than the one that created the bookmark can
     * obtain access to the
     * file-system resource that the URL (obtained from the bookmark) points to. Specifically, a
     * bookmark created with security
     * scope fails to resolve if the caller does not have the same code signing identity as the
     * caller that created the
     * bookmark. For a document-scoped bookmark, any sandboxed app that has access to the bookmark
     * data itself, and has access
     * to the document that owns the bookmark, can obtain access to the resource. Version
     * note:Â Security-scoped bookmarks are
     * not available in versions of OS X prior to OS X v10.7.3.
     **/


    public NSData bookmarkDataWithOptionsIncludingResourceValuesForKeysRelativeToURLError(NSURLBookmarkCreationOptions options,
                                                                                          NSArray keys, NSURL relativeURL, NSError[] error) {
        return null;// TODO
    }

    /**
     * @param keys  An array of names of URL resource properties.
     * @param error The error that occurred in the case that one or more resource values cannot be
     *              retrieved.
     * @return Return Value A dictionary of resource values indexed by key.
     * @Declaration : - (NSDictionary *)resourceValuesForKeys:(NSArray *)keys error:(NSError
     * **)error
     * @Description : Returns the resource values for the properties identified by specified array
     * of keys.
     * @Discussion This method first checks if the URL object already caches the specified resource
     * values. If so, it returns the cached
     * resource values to the caller. If not, then this method synchronously obtains the resource
     * values from the backing store,
     * adds the resource values to the URL object's cache, and returns the resource values to the
     * caller. The type of the
     * returned resource value varies by resource property; for details, see the documentation for
     * the key you want to access.
     * If the result dictionary does not contain a resource value for one or more of the requested
     * resource keys, it means those
     * resource properties are not available for the specified resource and no errors occurred when
     * determining those resource
     * properties were not available. If an error occurs, this method returns nil and populates the
     * object pointer referenced by
     * error with additional information.
     **/


    public NSDictionary resourceValuesForKeys(NSArray keys, NSError error) {
        return null;
    }

    /**
     * @param bookmarkData    The bookmark data containing information for the alias file.
     * @param bookmarkFileURL The desired location of the alias file.
     * @param options         Options taken into account when creating the alias file.
     * @param error           The error that occurred in the case that the alias file cannot be
     *                        created.
     * @return Return Value YES if the alias file is successfully created; otherwise, NO.
     * @Declaration : + (BOOL)writeBookmarkData:(NSData *)bookmarkData toURL:(NSURL
     * *)bookmarkFileURL
     * options:(NSURLBookmarkFileCreationOptions)options error:(NSError **)error
     * @Description : Creates an alias file on disk at a specified location with specified bookmark
     * data.
     * @Discussion This method will produce an error if bookmarkData was not created with the
     * NSURLBookmarkCreationSuitableForBookmarkFile
     * option. If bookmarkFileURL points to a directory, the alias file will be created in that
     * directory with its name derived
     * from the information in bookmarkData. If bookmarkFileURL points to a file, the alias file
     * will be created with the
     * location and name indicated by bookmarkFileURL, and its extension will be changed to .alias
     * if it is not already.
     **/


    public boolean writeBookmarkDataToURLOptionsError() {
        return false;// TODO
    }

    /**
     * @return Return Value An absolute string for the URL. Creating by resolving the receiver's
     * string against its base according to the
     * algorithm given in RFC 1808.
     * @Declaration : - (NSString *)absoluteString
     * @Description : Returns the URL string for the receiver as if it were an absolute URL.
     **/

    public NSString absoluteString() {
        return null;
    }

    public URL getModel() {
        return null;
    }

    /**
     * @return Return Value The last path component of the URL. For example, in the URL
     * file:///path/to/file, the last path component is
     * file.
     * @Declaration : - (NSString *)lastPathComponent
     * @Description : Returns the last path component of a file URL.
     **/

    public NSString lastPathComponent() {
        try {
            String urlString = wrappedURL.toString();
            return new NSString(urlString.substring(urlString.lastIndexOf('/') + 1));
        } catch (Exception e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @return Return Value The parameter string of the URL. If the receiver does not conform to RFC
     * 1808, returns nil. For example, in the
     * URL file:///path/to/file;foo, the parameter string is foo.
     * @Declaration : - (NSString *)parameterString
     * @Description : Returns the parameter string of a URL conforming to RFC 1808.
     * @Discussion This method should not be confused with the query method, which also often
     * contains a string of parameters.
     **/


    public String parameterString() {
        try {
            String url = baseUrl.getWrappedString() + urlString.getWrappedString();
            return url.substring(url.lastIndexOf(';') + 1);
        } catch (Exception e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @return Return Value The password of the URL. If the receiver does not conform to RFC 1808,
     * returns nil. For example, in the URL
     * http://username:password@www.example.com/index.html, the password is password.
     * @Declaration : - (NSString *)password
     * @Description : Returns the password of a URL conforming to RFC 1808.
     **/


    public String password() {
        if (getUrl().getUserInfo().indexOf("@") > getUrl().getUserInfo().indexOf(":"))
            return getUrl().getUserInfo().substring(getUrl().toString().indexOf(":") + 1, getUrl().getUserInfo().indexOf("@"));
        return null;
    }

    /**
     * @return Return Value An array containing the individual path components of the URL. For
     * example, in the URL
     * file:///directory/directory2/file, the path components array contains directory, directory2,
     * and file.
     * @Declaration : - (NSArray *)pathComponents
     * @Description : Returns the individual path components of a file URL in an array.
     **/


    public NSArray<NSString> pathComponents() {
        try {
            String url = baseUrl.getWrappedString() + urlString.getWrappedString();
            NSArray<NSString> strings = NSArray.array(NSArray.class);
            if (url != null) {
                for (int i = 0; i < url.split("/").length; i++) {
                    strings.getWrappedList().add(new NSString(url.split("/")[i]));
                }
            }

            return strings;
        } catch (Exception e) {
            Logger.getLogger(NSURL.class.getName(), String.valueOf(e));
        }
        return null;
    }

    /**
     * @return Return Value The path extension of the URL. For example, in the URL
     * file:///path/to/file.txt, the path extension is txt.
     * @Declaration : - (NSString *)pathExtension
     * @Description : Returns the path extension of a file URL.
     **/


    public String pathExtension() {
        try {
            String url = baseUrl.getWrappedString() + urlString.getWrappedString();
            if (URLUtil.isFileUrl(url))
                return url.substring(url.lastIndexOf('.') + 1);
            return null;
        } catch (Exception e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @return Return Value The query string from the URL. If the receiver does not conform to RFC
     * 1808, returns nil. For example, in the
     * URL http://www.example.com/index.php?key1=value1&key2=value2, the query string is
     * key1=value1&key2=value2.
     * @Declaration : - (NSString *)query
     * @Description : Returns the query string from a URL conforming to RFC 1808.
     **/


    public NSString query() {
        try {
            String url = baseUrl.getWrappedString() + urlString.getWrappedString();
            String query = url.substring(url.lastIndexOf('?') + 1);
            return new NSString(query);
        } catch (Exception e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    /**
     * @return Return Value The resource specifier of the URL. For example, in the URL
     * http://www.example.com/index.html?key1=value1#jumplink, the resource specifier is
     * //www.example.com/index.html?key1=value1#jumplink (everything after the colon).
     * @Declaration : - (NSString *)resourceSpecifier
     * @Description : Returns the resource specifier of the URL.
     * @Discussion The full URL is the concatenation of the value of scheme, a colon (:), and this
     * resource specifier value.
     **/


    public String resourceSpecifier() {
        return "//" + baseUrl + urlString;
    }

    /**
     * Returns a new NSURL object with any instances of ".." or "." removed from its path. - (NSURL
     * *)standardizedURL
     */

    public NSURL standardizedURL() {
        (getUrl().toString() + urlString).replaceAll(".", "").replace("..", "");
        return this;
    }

    /**
     * @return Return Value The user portion of the URL. If the receiver does not conform to RFC
     * 1808, returns nil. For example, in the URL
     * http://username:password@www.example.com/index.html, the user is username.
     * @Declaration : - (NSString *)user
     * @Description : Returns the user portion of a URL conforming to RFC 1808.
     * @Discussion If the URL conforms to RFC 1808 (the most common form of URL), this accessor
     * returns the specified URL component;
     * otherwise it returns nil. The litmus test for conformance to RFC 1808 is as recommended in
     * RFC 1808â€”specifically, whether
     * the first two characters of resourceSpecifier are slashes (//).
     **/

    public String user() {
        if (getUrl().getUserInfo().indexOf("@") > getUrl().getUserInfo().indexOf(":"))
            return getUrl().getUserInfo().substring(getUrl().toString().indexOf("//") + 1, getUrl().getUserInfo().indexOf(":"));
        return null;
    }

    /**
     * @return Return Value The fragment of the URL. If the receiver does not conform to RFC 1808,
     * returns nil. For example, in the URL
     * http://www.example.com/index.html#jumpLocation, the fragment identifier is jumpLocation.
     * @Declaration : - (NSString *)fragment
     * @Description : Returns the fragment of a URL conforming to RFC 1808.
     **/


    public String fragment() {
        return baseUrl.getWrappedString().substring(baseUrl.getWrappedString().lastIndexOf(';') + 1);
    }

    /**
     * @return Return Value The base URL of the receiver. If the receiver is an absolute URL,
     * returns nil.
     * @Declaration : - (NSURL *)baseURL
     * @Description : Returns the base URL of the receiver.
     **/


    public URL baseURL() {
        // return initWithString(getUrl().getProtocol() + "://" + getUrl().getHost() + path);
        return null;
    }

    /**
     * @return Return Value The relative path of the URL without resolving against the base URL. If
     * the path has a trailing slash it is
     * stripped. If the receiver is an absolute URL, this method returns the same value as path. If
     * the receiver does not conform to
     * RFC 1808, returns nil.
     * @Declaration : - (NSString *)relativePath
     * @Description : Returns the path of a URL conforming to RFC 1808, without resolving against
     * the receiverâ€™s base URL.
     **/


    public String relativePath() {
        String url = this.baseURL().toString() + urlString;
        if (url.lastIndexOf('/') + 1 != url.length())
            return url;
        if (url.lastIndexOf('/') + 1 == url.length())
            return url.substring(0, url.length());
        return null;
    }

    /**
     * @return Return Value An absolute string for the URL. Creating by resolving the receiver's
     * string against its base according to the
     * algorithm given in RFC 1808.
     * @Declaration : - (NSString *)absoluteString
     * @Description : Returns the URL string for the receiver as if it were an absolute URL.
     **/

    public NSString relativeString() {
        return this.absoluteString();
    }

    /**
     * @return Return Value Returns YES if the receiver uses the file scheme, NO otherwise.
     * @Declaration : - (BOOL)isFileURL
     * @Description : Returns whether the receiver uses the file scheme.
     * @Discussion Both file path and file reference URLs are considered to be file URLs.
     **/


    public boolean isFileURL() {
        return URLUtil.isFileUrl(getUrl().toString());
    }

    /**
     * @return Return Value An absolute URL that refers to the same resource as the receiver. If the
     * receiver is already absolute, returns
     * self. Resolution is performed per RFC 1808.
     * @Declaration : - (NSURL *)absoluteURL
     * @Description : Returns an absolute URL that refers to the same resource as the receiver.
     **/


    public NSURL absoluteURL() {
        return null;
    }

    /**
     * @return Return Value YES if the URL is a file reference URL; otherwise, NO.
     * @Declaration : - (BOOL)isFileReferenceURL
     * @Description : Returns whether the URL is a file reference URL.
     **/


    public boolean isFileReferenceURL() {
        return URLUtil.isFileUrl(getUrl().toString());
    }

    /**
     * @return Return Value A null-terminated C string in file system representation.
     * @Declaration : - (const char *)fileSystemRepresentation
     * @Description : Returns a C string containing the file system path for a file URL.
     * @Discussion This string is automatically freed in the same way that a returned object would
     * be released. The caller must either copy
     * the string or use getFileSystemRepresentation:maxLength: if it needs to store the
     * representation outside of the
     * autorelease context in which the value was obtained. The file system representation format
     * is
     * described in â€œFile
     * Encodings and Fontsâ€?.
     **/


    public String fileSystemRepresentation() {
        String representation = "";
        try {
            representation = new String(getUrl().toString().getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return representation;
    }

    /**
     * @param error The error that occurred when the resource could not be reached.
     * @return Return Value YES if the resource is reachable; otherwise, NO.
     * @Declaration : - (BOOL)checkResourceIsReachableAndReturnError:(NSError **)error
     * @Description : Returns whether the resource pointed to by a file URL can be reached.
     * @Discussion This method synchronously checks if the file at the provided URL is reachable.
     * Checking reachability is appropriate when
     * making decisions that do not require other immediate operations on the resource, such as
     * periodic maintenance of user
     * interface state that depends on the existence of a specific document. For example, you might
     * remove an item from a
     * download list if the user deletes the file. If your app must perform operations on the file,
     * such as opening it or
     * copying resource properties, it is more efficient to attempt the operation and handle any
     * failure that may occur.
     * Note:Â This method is currently applicable only to URLs for file system resources. For other
     * URL types, this method always
     * returns NO.
     **/


    public boolean checkResourceIsReachableAndReturnError(NSError[] error) {
        if (error[0] != null)
            return false;
        return true;
    }

    public Uri getUri() {
        return null;
    }

    public void setUri(Uri _uri) {

    }

    public URL getUrl() {
        return wrappedURL;
    }

    public void setUrl(URL url) {
        this.wrappedURL = url;
    }

    public String getFileName() {
        return wrappedURL.getFile();
    }

    @Override
    public boolean supportsSecureCoding() {
        return false;
    }

    @Override
    public NSObject copyWithZone(NSZone zone) {
        return null;
    }

    @Override
    public void encodeWithCoder(NSCoder encoder) {
    }

    @Override
    public NSCoding initWithCoder(NSCoder decoder) {
        return null;
    }
}