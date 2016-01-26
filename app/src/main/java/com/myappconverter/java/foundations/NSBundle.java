
package com.myappconverter.java.foundations;

import com.myappconverter.mapping.utils.AndroidRessourcesUtils;
import com.myappconverter.mapping.utils.GenericMainContext;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class NSBundle extends NSObject {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private File ressourceBunble;
	private Uri initialPath;

	public static final NSString BundleDidLoadNotification = new NSString(
			"NSBundleDidLoadNotification");
	public static final NSString LoadedClassesNotification = new NSString(
			"NSLoadedClassesNotification");
	private static final NSMutableArray<NSBundle> _allBundles = new NSMutableArray<NSBundle>();
	private static final NSMutableArray _allFrameworks = new NSMutableArray();

	private NSURL appStoreReceiptURL = new NSURL();

	public NSURL getAppStoreReceiptURL() {
		return appStoreReceiptURL;
	}

	public NSURL appStoreReceiptURL() {
		return getAppStoreReceiptURL();
	}

	public Context getContext() {
		return GenericMainContext.sharedContext;
	}

	public File getRessourceBunble() {
		return ressourceBunble;
	}

	public void setRessourceBunble(File ressourceBunble) {
		this.ressourceBunble = ressourceBunble;
	}

	/* Instance variables */

	protected NSString name;

	protected NSDictionary info = null;

	protected NSString path;

	protected NSMutableArray classNames = new NSMutableArray();

	protected NSMutableArray packages = new NSMutableArray();

	protected Properties properties;

	protected boolean isFramework = false;

	/*
	 * Static methods
	 */

	/**
	 * @Signature: mainBundle
	 * @Declaration : + (NSBundle *)mainBundle
	 * @Description : Returns the NSBundle object that corresponds to the directory where the current application executable is located.
	 * @return Return Value The NSBundle object that corresponds to the directory where the application executable is located, or nil if a
	 *         bundle object could not be created.
	 * @Discussion This method allocates and initializes a bundle object if one doesn t already exist. The new object corresponds to the
	 *             directory where the application executable is located. Be sure to check the return value to make sure you have a valid
	 *             bundle. This method may return a valid bundle object even for unbundled applications. In general, the main bundle
	 *             corresponds to an application file package or application wrapper: a directory that bears the name of the application and
	 *             is marked by a .app extension.
	 **/
	
	public static NSBundle mainBundle() {
		String mainDir = GenericMainContext.sharedContext.getFilesDir().getAbsolutePath();
		return bundleWithPath(new NSString(mainDir));

	}

	/**
	 * @Signature: bundleWithPath:
	 * @Declaration : + (NSBundle *)bundleWithPath:(NSString *)fullPath
	 * @Description : Returns an NSBundle object that corresponds to the specified directory.
	 * @param fullPath The path to a directory. This must be a full pathname for a directory; if it contains any symbolic links, they must
	 *            be resolvable.
	 * @return Return Value The NSBundle object that corresponds to fullPath, or nil if fullPath does not identify an accessible bundle
	 *         directory.
	 * @Discussion This method allocates and initializes the returned object if there is no existing NSBundle associated with fullPath, in
	 *             which case it returns the existing object.
	 **/
	
	public static NSBundle bundleWithPath(NSString path) {
		NSBundle result = new NSBundle();
		File f = new File(path.getWrappedString());
		result.ressourceBunble = f;
		result.initialPath = Uri.parse(f.getAbsolutePath());
		return result;
	}

	/**
	 * @Signature: bundleWithURL:
	 * @Declaration : + (NSBundle *)bundleWithURL:(NSURL *)url
	 * @Description : Returns an NSBundle object that corresponds to the specified file URL.
	 * @param url The URL to a directory. This must be a URL for a directory; if it contains any symbolic links, they must be resolvable.
	 * @return Return Value The NSBundle object that corresponds to url, or nil if url does not identify an accessible bundle directory.
	 * @Discussion This method allocates and initializes the returned object if there is no existing NSBundle associated with url, in which
	 *             case it returns the existing object.
	 **/
	
	public static NSBundle bundleWithURL(NSURL url) {
		NSBundle result = new NSBundle();
		File f = new File(url.getUri().toString());
		result.ressourceBunble = f;
		result.initialPath = Uri.parse(f.getAbsolutePath());
		return result;
	}

	/**
	 * @Signature: bundleForClass:
	 * @Declaration : + (NSBundle *)bundleForClass:(Class)aClass
	 * @Description : Returns the NSBundle object with which the specified class is associated.
	 * @param aClass A class.
	 * @return Return Value The NSBundle object that dynamically loaded aClass (a loadable bundle), the NSBundle object for the framework in
	 *         which aClass is defined, or the main bundle object if aClass was not dynamically loaded or is not defined in a framework.
	 **/
	
	public static NSBundle bundleForClass(Class<?> aClass) {
		NSBundle result = new NSBundle();
		try {
			File f = new File(GenericMainContext.sharedContext.getClassLoader()
					.getResource(aClass.getName()).toURI().toString());
			result.ressourceBunble = f;
			result.initialPath = Uri.parse(f.getAbsolutePath());
			return result;
		} catch (URISyntaxException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;

	}

	/**
	 * @Signature: bundleWithIdentifier:
	 * @Declaration : + (NSBundle *)bundleWithIdentifier:(NSString *)identifier
	 * @Description : Returns the previously created NSBundle instance that has the specified bundle identifier.
	 * @param identifier The identifier for an existing NSBundle instance.
	 * @return Return Value The previously created NSBundle instance that has the bundle identifier identifier. Returns nil if the requested
	 *         bundle is not found.
	 * @Discussion This method is typically used by frameworks and plug-ins to locate their own bundle at runtime. This method may be
	 *             somewhat more efficient than trying to locate the bundle using the bundleForClass: method. However, if the initial lookup
	 *             of an already loaded and cached bundle with the specified identifier fails, this method uses potentially time-consuming
	 *             heuristics to attempt to locate the bundle.
	 **/
	
	public static NSBundle bundleWithIdentifier(String identifier)
			throws NameNotFoundException, IOException, URISyntaxException {
		Context cont = GenericMainContext.sharedContext.createPackageContext(identifier,
				Context.CONTEXT_INCLUDE_CODE);
		return bundleWithPath(new NSString(cont.getPackageCodePath()));

	}

	/**
	 * @Signature: allBundles
	 * @Declaration : + (NSArray *)allBundles
	 * @Description : Returns an array of all the application s non-framework bundles.
	 * @return Return Value An array of all the application s non-framework bundles.
	 * @Discussion The returned array includes the main bundle and all bundles that have been dynamically created but doesn t contain any
	 *             bundles that represent frameworks.
	 **/
	
	public static NSArray<NSBundle> allBundles() {
		return _allBundles.immutableClone();
	}

	/**
	 * @Signature: allFrameworks
	 * @Declaration : + (NSArray *)allFrameworks
	 * @Description : Returns an array of all of the application s bundles that represent frameworks.
	 * @return Return Value An array of all of the application s bundles that represent frameworks. Only frameworks with one or more
	 *         Objective-C classes in them are included.
	 * @Discussion The returned array includes frameworks that are linked into an application when the application is built and bundles for
	 *             frameworks that have been dynamically created.
	 **/
	
	public static NSArray allFrameworks() {
		return frameworkBundles();
	}

	public static NSArray frameworkBundles() {
		return _allFrameworks.immutableClone();
	}

	/**
	 * @Signature: URLForResource:withExtension:subdirectory:inBundleWithURL:
	 * @Declaration : + (NSURL *)URLForResource:(NSString *)name withExtension:(NSString *)ext subdirectory:(NSString *)subpath
	 *              inBundleWithURL:(NSURL *)bundleURL
	 * @Description : Creates and returns a file URL for the resource with the specified name and extension in the specified bundle.
	 * @param name The name of the resource file.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file URL is the first file
	 *            encountered that exactly matches name.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param bundleURL The file URL of the bundle to search.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located.
	 **/
	
	public static NSURL URLForResourceWithExtensionSubdirectoryInBundleWithURL(NSString name,
																			   NSString ext, NSString subpath, NSURL bundleURL) {
		try {
			NSURL result = new NSURL();
			result.setUri(Uri.parse(GenericMainContext.sharedContext.getClassLoader()
					.getResource(name.getWrappedString()).toURI().toString()));
			return result;
		} catch (URISyntaxException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;

	}

	/**
	 * @Signature: URLsForResourcesWithExtension:subdirectory:inBundleWithURL:
	 * @Declaration : + (NSArray *)URLsForResourcesWithExtension:(NSString *)ext subdirectory:(NSString *)subpath inBundleWithURL:(NSURL
	 *              *)bundleURL
	 * @Description : Returns an array containing the file URLs for all bundle resources having the specified filename extension, residing
	 *              in the specified resource subdirectory, within the specified bundle.
	 * @param ext The file extension of the files to retrieve.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param bundleURL The file URL of the bundle to search.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located.
	 **/
	
	public static NSArray URLsForResourcesWithExtensionSubdirectoryInBundleWithURL(NSString ext,
																				   NSString subpath, NSURL bundleURL) {

		List<Object> list = new ArrayList<Object>();

		File f = new File(subpath.getWrappedString());
		File file[] = f.listFiles();

		for (int i = 0; i < file.length; i++) {

			int extensionIndex = file[i].getName().lastIndexOf(".");
			String extension = file[i].getName().substring(extensionIndex + 1,
					file[i].getName().length());
			if (extension.equals(ext)) {
				NSURL url = new NSURL();
				url.setUri(Uri.parse(file[i].getAbsolutePath()));
				list.add(url);
			}

		}

		NSArray result = new NSArray(list.size());

		return result;
	}

	/**
	 * @Signature: pathForResource:ofType:inDirectory:
	 * @Declaration : + (NSString *)pathForResource:(NSString *)name ofType:(NSString *)extension inDirectory:(NSString *)bundlePath
	 * @Description : Returns the full pathname for the resource file identified by the specified name and extension and residing in a given
	 *              bundle directory.
	 * @param name The name of a resource file contained in the directory specified by bundlePath.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file is the first file
	 *            encountered that exactly matches name.
	 * @param bundlePath The path of a top-level bundle directory. This must be a valid path. For example, to specify the bundle directory
	 *            for a Mac app, you might specify the path /Applications/MyApp.app.
	 * @return Return Value The full pathname for the resource file or nil if the file could not be located. This method also returns nil if
	 *         the bundle specified by the bundlePath parameter does not exist or is not a readable directory.
	 **/
	
	public static NSString _pathForResourceOfTypeInDirectory(NSString name, NSString ext,
															 NSString bundlePath) {

		File file[] = null;
		if (bundlePath != null) {
			File f = new File(bundlePath.getWrappedString());
			file = f.listFiles();
			for (int i = 0; i < file.length; i++) {

				int extensionIndex = file[i].getName().lastIndexOf(".");
				String extension = file[i].getName().substring(extensionIndex + 1,
						file[i].getName().length());
				if (extension.equals(ext) && file[i].getName().equals(name)) {

					return new NSString(file[i].getAbsolutePath());
				}

			}
		} else {
			try {
				List<String> list = Arrays
						.asList(GenericMainContext.sharedContext.getAssets().list(""));
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					if (string.endsWith(name.getWrappedString() + "." + ext.getWrappedString())) {
						return new NSString(string);
					}
				}

			} catch (IOException e) {
				LOGGER.info(e.getMessage());
			}
		}

		return null;
	}

	private boolean listAssetFiles(String path, List<String> listFiles) {

		if (listFiles != null) {
			String[] list;
			try {
				list = GenericMainContext.sharedContext.getAssets().list(path);
				if (list.length > 0) {
					// This is a folder
					for (String file : list) {
						if (!listAssetFiles(path + "/" + file, listFiles)) {
							return false;
						}
					}
				} else {
					// This is a file
					// TODO: add file name to an array list
				}
			} catch (IOException e) {
				LOGGER.info(e.getMessage());
				return false;
			}
		}

		return true;
	}

	/**
	 * @Signature: pathsForResourcesOfType:inDirectory:
	 * @Declaration : + (NSArray *)pathsForResourcesOfType:(NSString *)extension inDirectory:(NSString *)bundlePath
	 * @Description : Returns an array containing the pathnames for all bundle resources having the specified extension and residing in the
	 *              bundle directory at the specified path.
	 * @param extension The file extension. If extension is an empty string or nil, the extension is assumed not to exist, all the files in
	 *            bundlePath are returned.
	 * @param bundlePath The top-level directory of a bundle. This must represent a valid path.
	 * @return Return Value An array containing the full pathnames for all bundle resources with the specified extension. This method
	 *         returns an empty array if no matching resource files are found. It also returns an empty array if the bundle specified by the
	 *         bundlePath parameter does not exist or is not a readable directory.
	 **/
	
	public static NSArray _pathsForResourcesOfTypeInDirectory(NSString extension,
															  NSString bundlePath) {
		if (bundlePath != null && !bundlePath.getWrappedString().isEmpty()) {
			List<String> allDirFiles = new ArrayList<String>();
			NSArray<NSString> result = new NSArray<NSString>();
			if (AndroidRessourcesUtils.listAssetFiles(bundlePath.getWrappedString(), allDirFiles)) {
				if (!allDirFiles.isEmpty()) {
					for (Iterator iterator = allDirFiles.iterator(); iterator.hasNext();) {
						String file = (String) iterator.next();
						if (file.endsWith(extension.getWrappedString())) {
							result.arrayByAddingObject(new NSString("assets " + file));
						}
					}
				}
				return result;
			}
		}
		return URLsForResourcesWithExtensionSubdirectoryInBundleWithURL(extension, bundlePath,
				new NSURL());
	}

	/**
	 * @Signature: preferredLocalizationsFromArray:
	 * @Declaration : + (NSArray *)preferredLocalizationsFromArray:(NSArray *)localizationsArray
	 * @Description : Returns one or more localizations from the specified list that a bundle object would use to locate resources for the
	 *              current user.
	 * @param localizationsArray An array of NSString objects, each of which specifies the name of a localization that the bundle supports.
	 * @return Return Value An array of NSString objects containing the preferred localizations. These strings are ordered in the array
	 *         according to the current user's language preferences and are taken from the strings in the localizationsArray parameter.
	 * @Discussion This method does not return all localizations in preference order but only those from which NSBundle would get localized
	 *             content, typically either a single non-region-specific localization or a region-specific localization followed by a
	 *             corresponding non-region-specific localization as a fallback. However, clients who want all localizations in preference
	 *             order can make repeated calls, each time taking the top localizations out of the list of localizations passed in.
	 **/
	
	public static NSArray<NSString> preferredLocalizationsFromArray(
			NSArray<NSString> localizationsArray) {
		NSBundle bundle = NSBundle.mainBundle();
		return bundle.localizations();
	}

	/**
	 * @Signature: preferredLocalizationsFromArray:
	 * @Declaration : + (NSArray *)preferredLocalizationsFromArray:(NSArray *)localizationsArray
	 * @Description : Returns one or more localizations from the specified list that a bundle object would use to locate resources for the
	 *              current user.
	 * @param localizationsArray An array of NSString objects, each of which specifies the name of a localization that the bundle supports.
	 * @return Return Value An array of NSString objects containing the preferred localizations. These strings are ordered in the array
	 *         according to the current user's language preferences and are taken from the strings in the localizationsArray parameter.
	 * @Discussion This method does not return all localizations in preference order but only those from which NSBundle would get localized
	 *             content, typically either a single non-region-specific localization or a region-specific localization followed by a
	 *             corresponding non-region-specific localization as a fallback. However, clients who want all localizations in preference
	 *             order can make repeated calls, each time taking the top localizations out of the list of localizations passed in.
	 **/
	
	public static NSArray preferredLocalizationsFromArray(NSArray<NSString> localizationsArray,
														  NSArray<NSString> preferencesArray) {
		return preferredLocalizationsFromArray(localizationsArray);
	}

	/**
	 * @Signature: initWithPath:
	 * @Declaration : - (id)initWithPath:(NSString *)fullPath
	 * @Description : Returns an NSBundle object initialized to correspond to the specified directory.
	 * @param fullPath The path to a directory. This must be a full pathname for a directory; if it contains any symbolic links, they must
	 *            be resolvable.
	 * @return Return Value An NSBundle object initialized to correspond to fullPath. This method initializes and returns a new instance
	 *         only if there is no existing bundle associated with fullPath, otherwise it deallocates self and returns the existing object.
	 *         If fullPath doesn t exist or the user doesn t have access to it, returns nil.
	 * @Discussion It s not necessary to allocate and initialize an instance for the main bundle; use the mainBundle class method to get
	 *             this instance. You can also use the bundleWithPath: class method to obtain a bundle identified by its directory path.
	 **/
	
	public NSBundle initWithPath(NSString path) {
		NSBundle bdl = bundleWithPath(path);
		return bdl;
	}

	/**
	 * @Signature: initWithURL:
	 * @Declaration : - (id)initWithURL:(NSURL *)url
	 * @Description : Returns an NSBundle object initialized to correspond to the specified file URL.
	 * @param url The file URL to a directory. This must be a full URL for a directory; if it contains any symbolic links, they must be
	 *            resolvable.
	 * @return Return Value An NSBundle object initialized to correspond to url. This method initializes and returns a new instance only if
	 *         there is no existing bundle associated with url, otherwise it deallocates self and returns the existing object. If url doesn
	 *         t exist or the user doesn t have access to it, returns nil.
	 * @Discussion It s not necessary to allocate and initialize an instance for the main bundle; use the mainBundle class method to get
	 *             this instance. You can also use the bundleWithURL: class method to obtain a bundle identified by its file URL.
	 **/
	
	public NSBundle initWithURL(NSURL url) {
		NSBundle bdl = bundleWithURL(url);
		return bdl;
	}

	public NSBundle() {
	}

	/**
	 * @Signature: load
	 * @Declaration : - (BOOL)load
	 * @Description : Dynamically loads the bundle s executable code into a running program, if the code has not already been loaded.
	 * @return Return Value YES if the method successfully loads the bundle s code or if the code has already been loaded, otherwise NO.
	 * @Discussion You can use this method to load the code associated with a dynamically loaded bundle, such as a plug-in or framework.
	 *             Prior to OS X version 10.5, a bundle would attempt to load its code if it had any only once. Once loaded, you could not
	 *             unload that code. In OS X version 10.5 and later, you can unload a bundle s executable code using the unload method. You
	 *             don t need to load a bundle s executable code to search the bundle s resources.
	 **/
	

	public static boolean load() {
		try {
			System.load(GenericMainContext.sharedContext.getPackageCodePath());
		} catch (Exception e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}
		return true;

	}

	/**
	 * @Signature: isLoaded
	 * @Declaration : - (BOOL)isLoaded
	 * @Description : Obtains information about the load status of a bundle.
	 * @return Return Value YES if the bundle s code is currently loaded, otherwise NO.
	 **/
	
	public boolean isLoaded() {

		return load();// FIXME
	}

	private boolean loaded;

	public boolean getLoaded() {
		return load();
	}

	/**
	 * @Signature: unload
	 * @Declaration : - (BOOL)unload
	 * @Description : Unloads the code associated with the receiver.
	 * @return Return Value YES if the bundle was successfully unloaded or was not already loaded; otherwise, NO if the bundle could not be
	 *         unloaded.
	 * @Discussion This method attempts to unload a bundle s executable code using the underlying dynamic loader (typically dyld). You may
	 *             use this method to unload plug-in and framework bundles when you no longer need the code they contain. You should use
	 *             this method to unload bundles that were loaded using the methods of the NSBundle class only. Do not use this method to
	 *             unload bundles that were originally loaded using the bundle-manipulation functions in Core Foundation. It is the
	 *             responsibility of the caller to ensure that no in-memory objects or data structures refer to the code being unloaded. For
	 *             example, if you have an object whose class is defined in a bundle, you must release that object prior to unloading the
	 *             bundle. Similarly, your code should not attempt to access any symbols defined in an unloaded bundle.
	 **/
	
	public boolean unload() {
		try {
			// before invoking garbage collector maybe set all receiver element to null
			this.classNames = null;
			this.info = null;
			this.initialPath = null;
			this.name = null;
			this.packages = null;
			this.path = null;
			this.principalClass = null;
			this.properties = null;

		} catch (Exception e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return false;
		}

		return true;
	}

	/**
	 * @Signature: preflightAndReturnError:
	 * @Declaration : - (BOOL)preflightAndReturnError:(NSError **)error
	 * @Description : Returns a Boolean value indicating whether the bundle s executable code could be loaded successfully.
	 * @param error On input, a pointer to an error object variable. On output, this variable may contain an error object indicating why the
	 *            bundle s executable could not be loaded. If no error would occur, this parameter is left unmodified. You may specify nil
	 *            for this parameter if you are not interested in the error information.
	 * @return Return Value YES if the bundle s executable code could be loaded successfully or is already loaded; otherwise, NO if the code
	 *         could not be loaded.
	 * @Discussion This method does not actually load the bundle s executable code. Instead, it performs several checks to see if the code
	 *             could be loaded and with one exception returns the same errors that would occur during an actual load operation. The one
	 *             exception is the NSExecutableLinkError error, which requires the actual loading of the code to verify link errors. For a
	 *             list of possible load errors, see the discussion for the loadAndReturnError: method.
	 **/
	
	public boolean preflightAndReturnError(NSError[] error) {
		return loadAndReturnError(error);
	}

	/**
	 * @Signature: loadAndReturnError:
	 * @Declaration : - (BOOL)loadAndReturnError:(NSError **)error
	 * @Description : Loads the bundle s executable code and returns any errors.
	 * @param error On input, a pointer to an error object variable. On output, this variable may contain an error object indicating why the
	 *            bundle s executable could not be loaded. If no error occurred, this parameter is left unmodified. You may specify nil for
	 *            this parameter if you are not interested in the error information.
	 * @return Return Value YES if the bundle s executable code was loaded successfully or was already loaded; otherwise, NO if the code
	 *         could not be loaded.
	 * @Discussion If this method returns NO and you pass a value for the error parameter, a suitable error object is returned in that
	 *             parameter. Potential errors returned are in the Cocoa error domain and include the types that follow.
	 **/
	
	public boolean loadAndReturnError(NSError[] error) {

		boolean result = true;
		Error err = null;
		try {
			result = load();
		} catch (Exception e) {
			err = new Error(e.getLocalizedMessage(), e.getCause());
			LOGGER.info(e.getMessage());
		}
		error[0] = NSError.errorWithDomainCodeUserInfo(new NSString(err.getMessage()),
				err.hashCode(), null);
		return result;

	}

	/**
	 * @Signature: bundleURL
	 * @Declaration : - (NSURL *)bundleURL
	 * @Description : Returns the full URL of the receiver s bundle directory.
	 * @return Return Value The full URL of the receiver s bundle directory.
	 **/
	
	public NSURL bundleURL() {
		NSURL result = new NSURL(initialPath.toString());
		return result;
	}

	private NSURL bundleURL;

	public NSURL getBundleURL() {
		return bundleURL();
	}

	/**
	 * @Signature: resourceURL
	 * @Declaration : - (NSURL *)resourceURL
	 * @Description : Returns the file URL of the receiver's subdirectory containing resource files.
	 * @return Return Value The file URL of the receiver's subdirectory containing resource files.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL resourceURL() {
		NSURL result = new NSURL();
		File file = GenericMainContext.sharedContext.getApplicationContext().getDir("res",
				Context.MODE_PRIVATE);
		result.setUri(Uri.parse(file.getAbsolutePath()));
		return result;
	}

	private NSURL resourceURL;

	public NSURL getResourceURL() {
		return resourceURL();
	}

	/**
	 * @Signature: executableURL
	 * @Declaration : - (NSURL *)executableURL
	 * @Description : Returns the file URL of the receiver's executable file.
	 * @return Return Value The file URL of the receiving bundle s executable file.
	 **/
	
	public NSURL executableURL() {
		NSURL result = new NSURL();
		// Fixed why using: MODE_WORLD_READABLE , use MODE_PRIVATE instead
		File file = GenericMainContext.sharedContext.getApplicationContext().getDir("bin",
				Context.MODE_PRIVATE);
		result.setUri(Uri.parse(file.getAbsolutePath()));
		return result;
	}

	private NSURL executableURL;

	public NSURL getExecutableURL() {
		return executableURL();
	}

	/**
	 * @Signature: URLForAuxiliaryExecutable:
	 * @Declaration : - (NSURL *)URLForAuxiliaryExecutable:(NSString *)executableName
	 * @Description : Returns the file URL of the executable with the specified name in the receiver s bundle.
	 * @param executableName The name of an executable file.
	 * @return Return Value The file URL of the executable executableName in the receiver s bundle.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a URL
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL URLForAuxiliaryExecutable(NSString executableName) {
		NSURL result = new NSURL();
		URL resourceLink = GenericMainContext.sharedContext.getClassLoader()
				.getResource(executableName.getWrappedString());
		result.setUri(Uri.parse(resourceLink.toString()));
		return result;
	}

	/**
	 * @Signature: privateFrameworksURL
	 * @Declaration : - (NSURL *)privateFrameworksURL
	 * @Description : Returns the file URL of the receiver's subdirectory containing private frameworks.
	 * @return Return Value The file URL of the receiver's subdirectory containing private frameworks.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a URL
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL privateFrameworksURL() {
		NSURL result = new NSURL();
		result.setUri(
				Uri.parse(GenericMainContext.sharedContext.getApplicationInfo().nativeLibraryDir));
		return result;
	}

	private NSURL privateFrameworksURL;

	public NSURL getPrivateFrameworksURL() {
		return privateFrameworksURL();
	}

	/**
	 * @Signature: sharedFrameworksURL
	 * @Declaration : - (NSURL *)sharedFrameworksURL
	 * @Description : Returns the file URL of the receiver's subdirectory containing shared frameworks.
	 * @return Return Value The file URL of the receiver's subdirectory containing shared frameworks.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a URL
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL sharedFrameworksURL() {
		int index = GenericMainContext.sharedContext.getApplicationInfo().sharedLibraryFiles.length;
		NSURL[] result = new NSURL[index];

		for (int i = 0; i < index; i++) {
			result[i] = new NSURL(
					GenericMainContext.sharedContext.getApplicationInfo().sharedLibraryFiles[i]);
		}

		return result[0];// FIXME ??!!
	}

	private NSURL sharedFrameworksURL;

	public NSURL getSharedFrameworksURL() {
		return sharedFrameworksURL();
	}

	/**
	 * @Signature: sharedSupportURL
	 * @Declaration : - (NSURL *)sharedSupportURL
	 * @Description : Returns the file URL of the receiver's subdirectory containing shared support files.
	 * @return Return Value The file URL of the receiver's subdirectory containing shared support files.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL sharedSupportURL() {
		NSURL result = new NSURL();
		result.setUri(Uri.parse(sharedSupportPath().getWrappedString()));
		return result;
	}

	private NSURL sharedSupportURL;

	public NSURL getSharedSupportURL() {
		return sharedSupportURL();
	}

	/**
	 * @Signature: builtInPlugInsURL
	 * @Declaration : - (NSURL *)builtInPlugInsURL
	 * @Description : Returns the file URL of the receiver's subdirectory containing plug-ins.
	 * @return Return Value The file URL of the receiving bundle s subdirectory containing plug-ins.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a URL
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSURL builtInPlugInsURL() {
		return sharedFrameworksURL();
	}

	public NSURL getBuiltInPlugInsURL() {
		return builtInPlugInsURL();
	}

	private NSURL builtInPlugInsURL;

	/**
	 * @Signature: bundlePath
	 * @Declaration : - (NSString *)bundlePath
	 * @Description : Returns the full pathname of the receiver s bundle directory.
	 * @return Return Value The full pathname of the receiver s bundle directory.
	 **/
	
	public NSString bundlePath() {
		return this.path;
	}

	private NSString bundlePath;

	public NSString getBundlePath() {
		return bundlePath();
	}

	/**
	 * @Signature: resourcePath
	 * @Declaration : - (NSString *)resourcePath
	 * @Description : Returns the full pathname of the receiving bundle s subdirectory containing resources.
	 * @return Return Value The full pathname of the receiving bundle s subdirectory containing resources.
	 **/
	
	public NSString resourcePath() {
		File file = GenericMainContext.sharedContext.getApplicationContext().getDir("res",
				Context.MODE_PRIVATE);
		return new NSString(file.getAbsolutePath());
	}

	private NSString resourcePath;

	public NSString getResourcePath() {
		return resourcePath();
	}

	/**
	 * @Signature: executablePath
	 * @Declaration : - (NSString *)executablePath
	 * @Description : Returns the full pathname of the receiver's executable file.
	 * @return Return Value The full pathname of the receiving bundle s executable file.
	 **/
	
	public NSString executablePath() {

		File file = GenericMainContext.sharedContext.getApplicationContext().getDir("bin",
				Context.MODE_PRIVATE);// FIXME ??? return bin dir ?!!
		return new NSString(file.getAbsolutePath());
	}

	private NSString executablePath;

	public NSString getExecutablePath() {
		return executablePath();
	}

	/**
	 * @Signature: pathForAuxiliaryExecutable:
	 * @Declaration : - (NSString *)pathForAuxiliaryExecutable:(NSString *)executableName
	 * @Description : Returns the full pathname of the executable with the specified name in the receiver s bundle.
	 * @param executableName The name of an executable file.
	 * @return Return Value The full pathname of the executable executableName in the receiver s bundle.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSString pathForAuxiliaryExecutable(String executableName) {
		try {
			return new NSString(GenericMainContext.sharedContext.getClassLoader()
					.getResource(executableName).toURI().toString());
		} catch (URISyntaxException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: privateFrameworksPath
	 * @Declaration : - (NSString *)privateFrameworksPath
	 * @Description : Returns the full pathname of the receiver's subdirectory containing private frameworks.
	 * @return Return Value The full pathname of the receiver's subdirectory containing private frameworks.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSString privateFrameworksPath() {
		return new NSString(GenericMainContext.sharedContext.getApplicationInfo().nativeLibraryDir);
	}

	private NSString privateFrameworksPath;

	public NSString getPrivateFrameworksPath() {
		return privateFrameworksPath();
	}

	/**
	 * @Signature: sharedFrameworksPath
	 * @Declaration : - (NSString *)sharedFrameworksPath
	 * @Description : Returns the full pathname of the receiver's subdirectory containing shared frameworks.
	 * @return Return Value The full pathname of the receiver's subdirectory containing shared frameworks.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSString sharedFrameworksPath() {
		String[] sharedLibFiles = GenericMainContext.sharedContext
				.getApplicationInfo().sharedLibraryFiles.clone();
		return new NSString(Arrays.toString(sharedLibFiles));
	}

	private NSString sharedFrameworksPath;

	public NSString getSharedFrameworksPath() {
		return sharedFrameworksPath();
	}

	/**
	 * @Signature: sharedSupportPath
	 * @Declaration : - (NSString *)sharedSupportPath
	 * @Description : Returns the full pathname of the receiver's subdirectory containing shared support files.
	 * @return Return Value The full pathname of the receiver's subdirectory containing shared support files.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSString sharedSupportPath() {
		String dataDir = GenericMainContext.sharedContext.getApplicationInfo().dataDir;
		return new NSString(dataDir);
	}

	private NSString sharedSupportPath;

	public NSString getSharedSupportPath() {
		return sharedSupportPath();
	}

	/**
	 * @Signature: builtInPlugInsPath
	 * @Declaration : - (NSString *)builtInPlugInsPath
	 * @Description : Returns the full pathname of the receiver's subdirectory containing plug-ins.
	 * @return Return Value The full pathname of the receiving bundle s subdirectory containing plug-ins.
	 * @Discussion This method returns the appropriate path for modern application and framework bundles. This method may not return a path
	 *             for non-standard bundle formats or for some older bundle formats.
	 **/
	
	public NSString builtInPlugInsPath() {
		return privateFrameworksPath();
	}

	public NSString getBuiltInPlugInsPath() {
		return builtInPlugInsPath;
	}

	private NSString builtInPlugInsPath;

	/**
	 * @Signature: URLForResource:withExtension:
	 * @Declaration : - (NSURL *)URLForResource:(NSString *)name withExtension:(NSString *)extension
	 * @Description : Returns the file URL for the resource identified by the specified name and file extension.
	 * @param name The name of the resource file.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file URL is the first file
	 *            encountered that exactly matches name.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located.
	 * @Discussion If extension is an empty string or nil, the returned pathname is the first one encountered where the file name exactly
	 *             matches name. The method first looks for a matching resource file in the nonlocalized resource directory of the specified
	 *             bundle. (In OS X, this directory is typically called Resources but in iOS, it is the main bundle directory.) If a
	 *             matching resource file is not found, it then looks in the top level of any available language-specific .lproj
	 *             directories. (The search order for the language-specific directories corresponds to the user s preferences.) It does not
	 *             recurse through other subdirectories at any of these locations. For more details see Internationalization Programming
	 *             Topics.
	 **/
	
	public NSURL URLForResourceWithExtension(NSString name, NSString extension) {
		String nameExtension = null;
		if (extension == null && name.getWrappedString().lastIndexOf(".") != -1) {
			nameExtension = name.getWrappedString().substring(
					name.getWrappedString().lastIndexOf(".") + 1, name.getWrappedString().length());
			if (nameExtension.length() > 0) {
				int end = name.getWrappedString().length() - nameExtension.length() - 1;
				name.setWrappedString(name.getWrappedString().substring(0, end));
			}

		} else {
			nameExtension = extension.getWrappedString();
		}

		String originalName = name.getWrappedString();
		name.setWrappedString(name.getWrappedString().toLowerCase());
		boolean res = name.getWrappedString().matches("^([a-z]+[a-z0-9_.]*)");
		if (!res) {
			if (nameExtension != null && !nameExtension.isEmpty()) {
				return new NSURL("assets " + originalName + "." + nameExtension);
			} else {
				return new NSURL("assets " + originalName);
			}
		}
		int resId = AndroidRessourcesUtils.getResID(name.getWrappedString(), "raw");
		if (resId == 0) {
			resId = AndroidRessourcesUtils.getResID(name.getWrappedString(), "drawable");
			if (resId == 0) {
				if (nameExtension.isEmpty() || nameExtension == null) {
					return new NSURL(originalName);
				}
				return new NSURL(originalName + "." + nameExtension);
			}
		}
		return new NSURL("" + resId + "");
	}

	/**
	 * @Signature: URLForResource:withExtension:subdirectory:
	 * @Declaration : - (NSURL *)URLForResource:(NSString *)name withExtension:(NSString *)extension subdirectory:(NSString *)subpath
	 * @Description : Returns the file URL for the resource file identified by the specified name and extension and residing in a given
	 *              bundle directory.
	 * @param name The name of a resource file contained in the directory specified by bundleURL.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file URL is the first file
	 *            encountered that exactly matches name.
	 * @param subpath The path of a top-level bundle directory. This must be a valid path. For example, to specify the bundle directory for
	 *            a Mac app, you might specify the path /Applications/MyApp.app.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located. This method also returns nil if the
	 *         bundle specified by the bundlePath parameter does not exist or is not a readable directory.
	 * @Discussion The method first looks for a matching resource file in the non-localized resource directory of the specified bundle. (In
	 *             OS X, this directory is typically called Resources but in iOS, it is the main bundle directory.) If a matching resource
	 *             file is not found, it then looks in the top level of any available language-specific .lproj directories. (The search
	 *             order for the language-specific directories corresponds to the user s preferences.) It does not recurse through other
	 *             subdirectories at any of these locations. For more details see Internationalization Programming Topics.
	 **/
	
	public NSURL URLForResourceWithExtensionSubdirectory(String name, String extension,
														 String subpath) {

		return null;
	}

	/**
	 * @Signature: URLForResource:withExtension:subdirectory:localization:
	 * @Declaration : - (NSURL *)URLForResource:(NSString *)name withExtension:(NSString *)extension subdirectory:(NSString *)subpath
	 *              localization:(NSString *)localizationName
	 * @Description : Returns the file URL for the resource identified by the specified name and file extension, located in the specified
	 *              bundle subdirectory, and limited to global resources and those associated with the specified localization.
	 * @param name The name of the resource file.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file URL is the first file
	 *            encountered that exactly matches name.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param localizationName The name of the localization. This parameter should correspond to the name of one of the bundle's
	 *            language-specific resource directories without the .lproj extension.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located.
	 * @Discussion This method is equivalent to URLsForResourcesWithExtension:subdirectory:, except that only nonlocalized resources and
	 *             those in the language-specific .lproj directory specified by localizationName are searched. There should typically be
	 *             little reason to use this method see Getting the Current Language and Locale . See also
	 *             preferredLocalizationsFromArray:forPreferences: for how to determine what localizations are available.
	 **/
	
	public NSArray<NSURL> URLForResourceWithExtensionSubdirectoryLocalization(NSString name,
																			  NSString ext, NSString subpath, NSString localizationName) {

		return null;
	}

	/**
	 * @Signature: URLsForResourcesWithExtension:subdirectory:
	 * @Declaration : - (NSArray *)URLsForResourcesWithExtension:(NSString *)extension subdirectory:(NSString *)subpath
	 * @Description : Returns the file URL for the resource identified by the specified name and file extension and located in the specified
	 *              bundle subdirectory.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file URL is the first file
	 *            encountered that exactly matches name.
	 * @param subpath The name of the bundle subdirectory.
	 * @return Return Value The file URL for the resource file or nil if the file could not be located.
	 * @Discussion If subpath is nil, this method searches the top-level non-localized resource directory and the top-level of any
	 *             language-specific directories. (In OS X, the top-level non-localized resource directory is typically called Resources but
	 *             in iOS, it is the main bundle directory.) For example, suppose you have a Mac app with a modern bundle and you
	 *             specify @"Documentation" for the subpath parameter. This method would first look in the Contents/Resources/Documentation
	 *             directory of the bundle, followed by the Documentation subdirectories of each language-specific .lproj directory. (The
	 *             search order for the language-specific directories corresponds to the user s preferences.) This method does not recurse
	 *             through any other subdirectories at any of these locations. For more details see Internationalization Programming Topics.
	 **/
	
	public NSArray<NSURL> URLsForResourcesWithExtensionSubdirectory(NSString ext,
																	NSString subpath) {
		return null;
	}

	/**
	 * @Signature: URLsForResourcesWithExtension:subdirectory:localization:
	 * @Declaration : - (NSArray *)URLsForResourcesWithExtension:(NSString *)extensions subdirectory:(NSString *)subpath
	 *              localization:(NSString *)localizationName
	 * @Description : Returns an array containing the file URLs for all bundle resources having the specified filename extension, residing
	 *              in the specified resource subdirectory, and limited to global resources and those associated with the specified
	 *              localization.
	 * @param ext The file extension of the files to retrieve.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param localizationName The name of the localization. This parameter should correspond to the name of one of the bundle's
	 *            language-specific resource directories without the .lproj extension.
	 * @return Return Value An array containing the file URLs for all bundle resources matching the specified criteria. This method returns
	 *         an empty array if no matching resource files are found.
	 * @Discussion This method is equivalent to URLsForResourcesWithExtension:subdirectory:, except that only nonlocalized resources and
	 *             those in the language-specific .lproj directory specified by localizationName are searched.
	 **/
	
	public NSArray<NSURL> URLsForResourcesWithExtensionSubdirectoryLocalization(NSString extensions,
																				NSString subpath, NSString localizationName) {
		return null;
	}

	/**
	 * @Signature: pathForResource:ofType:
	 * @Declaration : - (NSString *)pathForResource:(NSString *)name ofType:(NSString *)extension
	 * @Description : Returns the full pathname for the resource identified by the specified name and file extension.
	 * @param name The name of the resource file. If name is an empty string or nil, returns the first file encountered of the supplied
	 *            type.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file is the first file
	 *            encountered that exactly matches name.
	 * @return Return Value The full pathname for the resource file or nil if the file could not be located.
	 * @Discussion The method first looks for a matching resource file in the non-localized resource directory of the specified bundle. (In
	 *             OS X, this directory is typically called Resources but in iOS, it is the main bundle directory.) If a matching resource
	 *             file is not found, it then looks in the top level of any available language-specific .lproj directories. (The search
	 *             order for the language-specific directories corresponds to the user s preferences.) It does not recurse through other
	 *             subdirectories at any of these locations. For more details see Internationalization Programming Topics.
	 **/
	
	public NSString pathForResourceOfType(NSString name, NSString extension) {
		String nameExtension = null;
		if (extension == null && name.getWrappedString().lastIndexOf(".") != -1) {

			nameExtension = name.getWrappedString().substring(
					name.getWrappedString().lastIndexOf(".") + 1, name.getWrappedString().length());
			if (nameExtension.length() > 0) {
				int end = name.getWrappedString().length() - nameExtension.length() - 1;
				name.setWrappedString(name.getWrappedString().substring(0, end));
			}

		} else {
			nameExtension = extension.getWrappedString();
		}

		NSString orginaleName = new NSString(name.getWrappedString());
		name.setWrappedString(name.getWrappedString().toLowerCase());
		name.setWrappedString(
				AndroidRessourcesUtils.formatStringResourceName(name.getWrappedString()));
		boolean res = name.getWrappedString().matches("^([a-z]+[a-z0-9_.]*)");// ("^[a-z]0-9_.");
		if (!res) {
			if (nameExtension.charAt(0) == '.')
				return new NSString("assets " + name.getWrappedString() + "" + nameExtension);
			return new NSString("assets " + name.getWrappedString() + "." + nameExtension);
		}
		int resId = AndroidRessourcesUtils.getResID(name.getWrappedString(), "raw");
		if (resId == 0) {
			resId = AndroidRessourcesUtils.getResID(name.getWrappedString(), "drawable");
			if (resId == 0) {
				if (nameExtension.isEmpty() || nameExtension == null) {
					return new NSString("assets " + name.getWrappedString());
				}
				if (nameExtension.charAt(0) == '.')
					return new NSString(
							"assets " + orginaleName.getWrappedString() + "" + nameExtension);
				return new NSString(
						"assets " + orginaleName.getWrappedString() + "." + nameExtension);
			}
		}
		return new NSString("" + resId + "");
	}

	/**
	 * @Signature: pathForResource:ofType:inDirectory:
	 * @Declaration : - (NSString *)pathForResource:(NSString *)name ofType:(NSString *)extension inDirectory:(NSString *)subpath
	 * @Description : Returns the full pathname for the resource identified by the specified name and file extension and located in the
	 *              specified bundle subdirectory.
	 * @param name The name of the resource file.
	 * @param extension If extension is an empty string or nil, all the files in subpath and its subdirectories are returned. If an
	 *            extension is provided the subdirectories are not searched.
	 * @param subpath The name of the bundle subdirectory. Can be nil.
	 * @return Return Value An array of full pathnames for the subpath or nil if no files are located.
	 * @Discussion If subpath is nil, this method searches the top-level nonlocalized resource directory and the top-level of any
	 *             language-specific directories. (In OS X, the top-level nonlocalized resource directory is typically called Resources but
	 *             in iOS, it is the main bundle directory.) For example, suppose you have a Mac app with a modern bundle and you
	 *             specify @"Documentation" for the subpath parameter. This method would first look in the Contents/Resources/Documentation
	 *             directory of the bundle, followed by the Documentation subdirectories of each language-specific .lproj directory. Whether
	 *             this method recurses through subdirectories is dependent on the extension parameter. If nil or an empty string it will
	 *             recurse, otherwise, it does not. (The search order for the language-specific directories corresponds to the user s
	 *             preferences.) For more details see Internationalization Programming Topics.
	 **/
	
	public NSString pathForResourceOfTypeInDirectory(NSString name, NSString ext,
													 NSString subpath) {
		return null;
	}

	/**
	 * @Signature: pathForResource:ofType:inDirectory:forLocalization:
	 * @Declaration : - (NSString *)pathForResource:(NSString *)name ofType:(NSString *)extension inDirectory:(NSString *)subpath
	 *              forLocalization:(NSString *)localizationName
	 * @Description : Returns the full pathname for the resource identified by the specified name and file extension, located in the
	 *              specified bundle subdirectory, and limited to global resources and those associated with the specified localization.
	 * @param name The name of the resource file.
	 * @param extension If extension is an empty string or nil, the extension is assumed not to exist and the file is the first file
	 *            encountered that exactly matches name.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param localizationName The name of the localization. This parameter should correspond to the name of one of the bundle's
	 *            language-specific resource directories without the .lproj extension.
	 * @return Return Value The full pathname for the resource file or nil if the file could not be located.
	 * @Discussion This method is equivalent to pathForResource:ofType:inDirectory:, except that only nonlocalized resources and those in
	 *             the language-specific .lproj directory specified by localizationName are searched. There should typically be little
	 *             reason to use this method see Getting the Current Language and Locale . See also
	 *             preferredLocalizationsFromArray:forPreferences: for how to determine what localizations are available.
	 **/
	
	public NSString pathForResourceOfTypeInDirectoryForLocalization(NSString name, NSString ext,
																	NSString subpath, NSString localizationName) {

		return null;
	}

	/**
	 * @Signature: pathsForResourcesOfType:inDirectory:
	 * @Declaration : - (NSArray *)pathsForResourcesOfType:(NSString *)extension inDirectory:(NSString *)subpath
	 * @Description : Returns an array containing the pathnames for all bundle resources having the specified filename extension and
	 *              residing in the resource subdirectory.
	 * @param extension The file extension. If extension is an empty string or nil, the extension is assumed not to exist, all the files in
	 *            subpath are returned.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @return Return Value An array containing the full pathnames for all bundle resources matching the specified criteria. This method
	 *         returns an empty array if no matching resource files are found.
	 * @Discussion This method provides a means for dynamically discovering multiple bundle resources of the same type. If extension is an
	 *             empty string or nil, all bundle resources in the specified resource directory are returned. The argument subpath
	 *             specifies the name of a specific subdirectory to search within the current bundle s resource directory hierarchy. If
	 *             subpath is nil, this method searches the top-level nonlocalized resource directory and the top-level of any
	 *             language-specific directories. (In OS X, the top-level nonlocalized resource directory is typically called Resources but
	 *             in iOS, it is the main bundle directory.) For example, suppose you have a Mac app with a modern bundle and you
	 *             specify @"Documentation" for the subpath parameter. This method would first look in the Contents/Resources/Documentation
	 *             directory of the bundle, followed by the Documentation subdirectories of each language-specific .lproj directory. (The
	 *             search order for the language-specific directories corresponds to the user s preferences.) This method does not recurse
	 *             through any other subdirectories at any of these locations. For more details see Internationalization Programming Topics.
	 **/
	
	public NSArray pathsForResourcesOfTypeInDirectory(NSString ext, NSString subpath) {

		return null;
	}

	/**
	 * @Signature: pathsForResourcesOfType:inDirectory:forLocalization:
	 * @Declaration : - (NSArray *)pathsForResourcesOfType:(NSString *)extension inDirectory:(NSString *)subpath forLocalization:(NSString
	 *              *)localizationName
	 * @Description : Returns an array containing the file for all bundle resources having the specified filename extension, residing in the
	 *              specified resource subdirectory, and limited to global resources and those associated with the specified localization.
	 * @param extension The file extension of the files to retrieve.
	 * @param subpath The name of the bundle subdirectory to search.
	 * @param localizationName The name of the localization. This parameter should correspond to the name of one of the bundle's
	 *            language-specific resource directories without the .lproj extension.
	 * @return Return Value An array containing the full pathnames for all bundle resources matching the specified criteria. This method
	 *         returns an empty array if no matching resource files are found.
	 * @Discussion This method is equivalent to pathsForResourcesOfType:inDirectory:, except that only nonlocalized resources and those in
	 *             the language-specific .lproj directory specified by localizationName are searched.
	 **/
	
	public NSArray pathsForResourcesOfTypeInDirectoryForLocalization(NSString ext, NSString subpath,
																	 NSString localizationName) {

		return null;
	}

	/**
	 * @Signature: localizedStringForKey:value:table:
	 * @Declaration : - (NSString *)localizedStringForKey:(NSString *)key value:(NSString *)value table:(NSString *)tableName
	 * @Description : Returns a localized version of the string designated by the specified key and residing in the specified table.
	 * @param key The key for a string in the table identified by tableName.
	 * @param value The value to return if key is nil or if a localized string for key can t be found in the table.
	 * @param tableName The receiver s string table to search. If tableName is nil or is an empty string, the method attempts to use the
	 *            table in Localizable.strings.
	 * @return Return Value A localized version of the string designated by key in table tableName. If value is nil or an empty string, and
	 *         a localized string is not found in the table, returns key. If key and value are both nil, returns the empty string.
	 * @Discussion For more details about string localization and the specification of a .strings file, see String Resources . Using the
	 *             user default NSShowNonLocalizedStrings, you can alter the behavior of localizedStringForKey:value:table: to log a message
	 *             when the method can t find a localized string. If you set this default to YES (in the global domain or in the application
	 *             s domain), then when the method can t find a localized string in the table, it logs a message to the console and
	 *             capitalizes key before returning it.
	 **/
	
	public NSString localizedStringForKeyValueTable(NSString key, NSString value,
													NSString tableName) {

		if (key == null || key.getWrappedString() == null
				|| (key.getWrappedString() != null && key.getWrappedString().isEmpty())) {
			return value;
		}
		String parsedVal = AndroidRessourcesUtils.formatStringResourceName(key.getWrappedString());
		int idString = AndroidRessourcesUtils.getResID(parsedVal, "string");
		try {
			return new NSString(getContext().getResources().getString(idString));
		} catch (NotFoundException e) {
			LOGGER.info(e.getMessage());
			return value;

		}
	}

	/**
	 * @Signature: bundleIdentifier
	 * @Declaration : - (NSString *)bundleIdentifier
	 * @Description : Returns the receiver s bundle identifier.
	 * @return Return Value The receiver s bundle identifier, which is defined by the CFBundleIdentifier key in the bundle s information
	 *         property list.
	 **/
	
	public NSString bundleIdentifier() {
		return new NSString(GenericMainContext.sharedContext.getApplicationInfo().packageName);
	}

	private NSString bundleIdentifier;

	public NSString getBundleIdentifier() {
		return bundleIdentifier();
	}

	/**
	 * @Signature: infoDictionary
	 * @Declaration : - (NSDictionary *)infoDictionary
	 * @Description : Returns a dictionary that contains information about the receiver.
	 * @return Return Value A dictionary, constructed from the bundle's Info.plist file, that contains information about the receiver. If
	 *         the bundle does not contain an Info.plist file, a valid dictionary is returned but this dictionary contains only private keys
	 *         that are used internally by the NSBundle class. The NSBundle class may add extra keys to this dictionary for its own use.
	 * @Discussion Common keys for accessing the values of the dictionary are CFBundleIdentifier, NSMainNibFile, and NSPrincipalClass.
	 **/
	
	public NSDictionary infoDictionary() {
		NSDictionary dic = NSDictionary.dictionaryWithContentsOfFile(
				NSBundle._pathForResourceOfTypeInDirectory(new NSString("Info"),
						new NSString("plist"), null));
		return dic;
	}

	private NSDictionary infoDictionary;

	public NSDictionary getInfoDictionary() {
		return infoDictionary();
	}

	/**
	 * @Signature: localizedInfoDictionary
	 * @Declaration : - (NSDictionary *)localizedInfoDictionary
	 * @Description : Returns a dictionary with the keys from the bundle s localized property list.
	 * @return Return Value A dictionary with the keys from the bundle s localized property list (InfoPlist.strings).
	 * @Discussion This method uses the preferred localization for the current user when determining which resources to return. If the
	 *             preferred localization is not available, this method chooses the most appropriate localization found in the bundle.
	 **/
	
	public NSDictionary localizedInfoDictionary() {
		NSDictionary dic = NSDictionary.dictionaryWithContentsOfFile(
				NSBundle._pathForResourceOfTypeInDirectory(new NSString("infoPlist"),
						new NSString("string"), null));
		return dic;
	}

	private NSDictionary localizedInfoDictionary;

	public NSDictionary getLocalizedInfoDictionary() {
		return localizedInfoDictionary();
	}

	/**
	 * @Signature: objectForInfoDictionaryKey:
	 * @Declaration : - (id)objectForInfoDictionaryKey:(NSString *)key
	 * @Description : Returns the value associated with the specified key in the receiver's information property list.
	 * @param key A key in the receiver's property list.
	 * @return Return Value The value associated with key in the receiver's property list (Info.plist). The localized value of a key is
	 *         returned when one is available.
	 * @Discussion Use of this method is preferred over other access methods because it returns the localized value of a key when one is
	 *             available.
	 **/
	
	public Object objectForInfoDictionaryKey(NSString key) {
		NSDictionary dic = this.infoDictionary();
		return dic.objectForKey(key);
	}

	/**
	 * @Signature: classNamed:
	 * @Declaration : - (Class)classNamed:(NSString *)className
	 * @Description : Returns the Class object for the specified name.
	 * @param className The name of a class.
	 * @return Return Value The Class object for className. Returns nil if className is not one of the classes associated with the receiver
	 *         or if there is an error loading the executable code containing the class implementation.
	 * @Discussion If the bundle s executable code is not yet loaded, this method dynamically loads it into memory. Classes (and categories)
	 *             are loaded from just one file within the bundle directory; this code file has the same name as the directory, but without
	 *             the extension ( .bundle , .app , .framework ). As a side effect of code loading, the receiver posts
	 *             NSBundleDidLoadNotification after all classes and categories have been loaded; see Notifications for details.
	 **/
	
	public Class<?> classNamed(String className) {
		try {
			Class<?> act = Class.forName(className);
			return act;
		} catch (ClassNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: principalClass
	 * @Declaration : - (Class)principalClass
	 * @Description : Returns the receiver s principal class.
	 * @return Return Value The receiver s principal class after ensuring that the code containing the definition of that class is
	 *         dynamically loaded. If the receiver encounters errors in loading or if it can t find the executable code file in the bundle
	 *         directory, returns nil.
	 * @Discussion The principal class typically controls all the other classes in the bundle; it should mediate between those classes and
	 *             classes external to the bundle. Classes (and categories) are loaded from just one file within the bundle directory.
	 *             NSBundle obtains the name of the code file to load from the dictionary returned from infoDictionary, using NSExecutable
	 *             as the key. The bundle determines its principal class in one of two ways: It first looks in its own information
	 *             dictionary, which extracts the information encoded in the bundle s property list (Info.plist). NSBundle obtains the
	 *             principal class from the dictionary using the key NSPrincipalClass. For non-loadable bundles (applications and
	 *             frameworks), if the principal class is not specified in the property list, the method returns nil. If the principal class
	 *             is not specified in the information dictionary, NSBundle identifies the first class loaded as the principal class.
	 **/
	
	public Class principalClass() {

		try {
			return Class.forName(GenericMainContext.sharedContext.getApplicationInfo().className);
		} catch (ClassNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	private Class principalClass;

	public Class getPrincipalClass() {
		return principalClass();
	}

	/**
	 * @Signature: localizations
	 * @Declaration : - (NSArray *)localizations
	 * @Description : Returns a list of all the localizations contained within the receiver s bundle.
	 * @return Return Value An array, containing NSString objects, that specifies all the localizations contained within the receiver s
	 *         bundle.
	 **/
	
	public NSArray<NSString> localizations() {
		return preferredLocalizations();
	}

	private NSArray<NSString> localizations;

	public NSArray<NSString> getLocalizations() {
		return localizations();
	}

	/**
	 * @Signature: preferredLocalizations
	 * @Declaration : - (NSArray *)preferredLocalizations
	 * @Description : Returns an array of strings indicating the actual localizations contained in the receiver s bundle.
	 * @return Return Value An array of NSString objects, each of which identifies the a localization in the receiver s bundle. The
	 *         languages are in the preferred order.
	 **/
	
	public NSArray<NSString> preferredLocalizations() {
		AssetManager asset = GenericMainContext.sharedContext.getAssets();
		String[] locales = asset.getLocales();
		NSArray<NSString> result = new NSArray<NSString>(locales.length);
		for (String string : locales) {
			result.getWrappedList().add(new NSString(string));
		}
		return result;
	}

	private NSArray<NSString> preferredLocalizations;

	public NSArray<NSString> getPreferredLocalizations() {
		return preferredLocalizations();
	}

	/**
	 * @Signature: developmentLocalization
	 * @Declaration : - (NSString *)developmentLocalization
	 * @Description : Returns the localization used to create the bundle.
	 * @return Return Value The localization used to create the bundle.
	 * @Discussion The returned localization corresponds to the value in the CFBundleDevelopmentRegion key of the bundle s property list
	 *             (Info.plist).
	 **/
	
	public NSString developmentLocalization() {
		return (NSString) objectForInfoDictionaryKey(new NSString("CFBundleDevelopmentRegion"));
	}

	private NSString developmentLocalization;

	public NSString getDevelopmentLocalization() {
		return developmentLocalization();
	}

	/**
	 * @Signature: executableArchitectures
	 * @Declaration : - (NSArray *)executableArchitectures
	 * @Description : Returns an array of numbers indicating the architecture types supported by the bundle s executable.
	 * @return Return Value An array of NSNumber objects, each of which contains an integer value corresponding to a supported processor
	 *         architecture. For a list of common architecture types, see the constants in Mach-O Architecture. If the bundle does not
	 *         contain a Mach-O executable, this method returns nil.
	 * @Discussion This method scans the bundle s Mach-O executable and returns all of the architecture types it finds. Because they are
	 *             taken directly from the executable, the returned values may not always correspond to one of the well-known CPU types
	 *             defined in Mach-O Architecture.
	 **/
	
	public NSArray<NSNumber> executableArchitectures() {
		NSArray<NSNumber> result = new NSArray<NSNumber>();
		result.getWrappedList().add(new NSNumber(Build.VERSION.SDK_INT));
		return result;
	}

	private NSArray<NSNumber> executableArchitectures;

	public NSArray<NSNumber> getExecutableArchitectures() {
		return executableArchitectures();
	}

	// Functions declarations

	/**
	 * @Description : Returns a localized version of a string.
	 * @param key : The key for a string in the specified table.
	 * @param tableName : The name of the table containing the key-value pairs. Also, the suffix for the strings file (a file with the
	 *            .strings extension) to store the localized string.
	 * @param bundle : The bundle containing the strings file.
	 * @param value : The value to return if key is nil or if a localized string for key can t be found in the table.
	 * @param comment : The comment to place above the key-value pair in the strings file.
	 * @return : Return Value The result of sending localizedStringForKey:value:table: to bundle, passing the specified key, value, and
	 *         tableName.
	 **/

	
	public static NSString NSLocalizedStringWithDefaultValue(NSString key, NSString tableName,
															 NSBundle bundle, NSString value, NSString comment) {
		try {
			return bundle.localizedStringForKeyValueTable(key, value, tableName);
		} catch (NotFoundException e) {
			LOGGER.info(e.getMessage());
		}
		return value;
	}

	/**
	 * @Description : Returns a localized version of a string.
	 * @param key : The key for a string in the specified table.
	 * @param tableName : The name of the table containing the key-value pairs. Also, the suffix for the strings file (a file with the
	 *            .strings extension) to store the localized string.
	 * @param bundle : The bundle containing the strings file.
	 * @param comment : The comment to place above the key-value pair in the strings file.
	 * @return : Return Value The result of sending localizedStringForKey:value:table: to bundle, passing the specified key and tableName
	 *         where the value parameter is nil.
	 **/

	
	public static NSString NSLocalizedStringFromTableInBundle(NSString key, NSString tableName,
															  NSBundle bundle, NSString comment) {
		try {
			return bundle.localizedStringForKeyValueTable(key, null, tableName);
		} catch (NotFoundException e) {
			LOGGER.info(e.getMessage());
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return null;
	}

	/**
	 * @Description : Returns a localized version of a string from the specified table.
	 * @param key : The key for a string in the specified table.
	 * @param tableName : The name of the table containing the key-value pairs. Also, the suffix for the strings file (a file with the
	 *            .strings extension) to store the localized string.
	 * @param comment : The comment to place above the key-value pair in the strings file.
	 * @return : Return Value The result of invoking localizedStringForKey:value:table: on the main bundle, passing it the specified key and
	 *         tableName.
	 **/

	
	public static NSString NSLocalizedStringFromTable(NSString key, NSString tableName,
													  NSString comment) {
		return NSBundle.mainBundle().localizedStringForKeyValueTable(key, null, tableName);
	}

	/**
	 * @Description : Returns a localized version of a string.
	 * @param key : The key for a string in the default table.
	 * @param comment : The comment to place above the key-value pair in the strings file.
	 * @return : Return Value The result of invoking localizedStringForKey:value:table: on the main bundle passing nil as the table.
	 **/

	
	public static NSString NSLocalizedString(NSString key, NSString comment) {
		return NSBundle.mainBundle().localizedStringForKeyValueTable(key, null, null);
	}

	// UIKit Additions

	
	public static final NSString UINibProxiedObjectsKey = new NSString("UINibProxiedObjectsKey");
	
	public static NSString UINibExternalObjects = new NSString("UINibExternalObjects");

	private static LayoutInflater mInflater;

	/**
	 * @Signature: loadNibNamed:owner:options:
	 * @Declaration: - (NSArray *)loadNibNamed:(NSString *)name owner:(id)owner options:(NSDictionary *)options
	 * @paaram : name The name of the nib file, which need not include the .nib extension.
	 * @paaram: owner The object to assign as the nib’s File's Owner object.
	 * @paaram: options A dictionary containing the options to use when opening the nib file. For a list of available keys for this
	 *          dictionary, see “Nib File Loading Options.�?
	 * @return : An array containing the top-level objects in the nib file. The array does not contain references to the File’s Owner or any
	 *         proxy objects; it contains only those objects that were instantiated when the nib file was unarchived. You should retain
	 *         either the returned array or the objects it contains manually to prevent the nib file objects from being released
	 *         prematurely.
	 * @Discussion : You can use this method to load user interfaces and make the objects available to your code. During the loading
	 *             process, this method unarchives each object, initializes it, sets its properties to their configured values, and
	 *             reestablishes any connections to other objects. (To establish outlet connections, this method uses the setValue:forKey:
	 *             method, which may cause the object in the outlet to be retained automatically.) For detailed information about the
	 *             nib-loading process
	 **/

	
	public static NSArray loadNibNamedOwnerOptions(NSString name, Object owner,
												   NSDictionary options) {
		try {
			ViewGroup parent = null;
			if (owner != null && owner instanceof ViewGroup) {
				parent = (ViewGroup) owner;
			}
			mInflater = LayoutInflater.from(GenericMainContext.sharedContext);
			int idRes = AndroidRessourcesUtils.getResID(name.getWrappedString().toLowerCase(),
					"layout");
			ViewGroup mView = (ViewGroup) mInflater.inflate(idRes, parent, false);
			NSArray mArray = new NSArray();
			mArray.getWrappedList().add(mView);
			return mArray;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return null;
	}

}