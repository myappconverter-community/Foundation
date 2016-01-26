
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.functions.NSPathUtilities;
import com.myappconverter.java.foundations.functions.NSPathUtilities.NSSearchPathDirectory;
import com.myappconverter.java.foundations.functions.NSPathUtilities.NSSearchPathDomainMask;
import com.myappconverter.java.foundations.protocols.NSFileManagerDelegate;
import com.myappconverter.mapping.utils.AndroidFileUtils;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.PerformBlock;
import com.myappconverter.mapping.utils.SerializationUtil;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;



public class NSFileManager extends NSObject {

    private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
    // NSVolumeEnumerationOptions
    public static final int NSVolumeEnumerationSkipHiddenVolumes = (int) (1L << 1); //
    public static final int NSVolumeEnumerationProduceFileReferenceURLs = (int) (1L << 2);

    
    public static enum NSDirectoryEnumerationOptions {

        NSDirectoryEnumerationSkipsSubdirectoryDescendants(1L << 0), //
        NSDirectoryEnumerationSkipsPackageDescendants(1L << 1), //
        NSDirectoryEnumerationSkipsHiddenFiles(1L << 2);//

        int value;

        private NSDirectoryEnumerationOptions(long v) {
            value = (int) v;
        }
    }

    
    public static enum NSFileManagerItemReplacementOptions {
        NSFileManagerItemReplacementUsingNewMetadataOnly(1L << 0), //
        NSFileManagerItemReplacementWithoutDeletingBackupItem(1L << 1);

        int value;

        private NSFileManagerItemReplacementOptions(long v) {
            value = (int) v;
        }

    }

    public static final NSString NSFileType = new NSString("NSFileType");
    public static final NSString NSFileSize = new NSString("NSFileSize");
    public static final NSString NSFileModificationDate = new NSString("NSFileModificationDate");
    public static final NSString NSFileReferenceCount = new NSString("NSFileReferenceCount");
    public static final NSString NSFileDeviceIdentifier = new NSString("NSFileDeviceIdentifier");
    public static final NSString NSFileOwnerAccountName = new NSString("NSFileOwnerAccountName");
    public static final NSString NSFileGroupOwnerAccountName = new NSString("NSFileGroupOwnerAccountName");
    public static final NSString NSFilePosixPermissions = new NSString("NSFilePosixPermissions");
    public static final NSString NSFileSystemNumber = new NSString("NSFileSystemNumber");
    public static final NSString NSFileSystemFileNumber = new NSString("NSFileSystemFileNumber");
    public static final NSString NSFileExtensionHidden = new NSString("NSFileExtensionHidden");
    public static final NSString NSFileHFSCreatorCode = new NSString("NSFileHFSCreatorCode");
    public static final NSString NSFileHFSTypeCode = new NSString("NSFileHFSTypeCode");
    public static final NSString NSFileImmutable = new NSString("NSFileImmutable");
    public static final NSString NSFileAppendOnly = new NSString("NSFileAppendOnly");
    public static final NSString NSFileCreationDate = new NSString("NSFileCreationDate");
    public static final NSString NSFileOwnerAccountID = new NSString("NSFileOwnerAccountID");
    public static final NSString NSFileGroupOwnerAccountID = new NSString("NSFileGroupOwnerAccountID");
    public static final NSString NSFileBusy = new NSString("NSFileBusy");

    // File-System Attribute Keys

    public static final NSString NSFileSystemFreeSize = new NSString("NSFileSystemFreeSize");
    public static final NSString NSFileSystemNodes = new NSString("NSFileSystemNodes");
    public static final NSString NSFileSystemFreeNodes = new NSString("NSFileSystemFreeNodes");

    private NSFileManagerDelegate mDelegate;
    private static NSFileManager Instance;
    private NSFileManagerBlock fileManagerErrorBlock;
    private Context context;

    private interface NSFileManagerBlock {

        public boolean errorHandler(NSURL url, NSError nsError);
    }

    public NSFileManager() {
    }

    // Creating a File Manager

    /**
     * @return Return Value An initialized NSFileManager instance.
     * @Signature: init
     * @Declaration : - init
     * @Description : Returns an initialized NSFileManager instance.
     * @Discussion In iOS and in OS X 10.5 and later you can use this method to create a specific
     * file manager instance. You might do this
     * in situations where you want to associate a delegate with the file manager to receive
     * notifications about the status of
     * file-related operations.
     **/
    @Override
    
    public NSFileManager init() {
        return new NSFileManager();
    }

    /**
     * @return The default NSFileManager object for the file system.
     * @Description :returns the shared file manager object for the process.
     * @Discussion This method always returns the same file manager object. If you plan to use a
     * delegate with the file manager to receive
     * notifications about the completion of file-based operations, you should create a new
     * instance
     * of NSFileManager (using the
     * init method) rather than using the shared object.
     */
    
    public static synchronized NSFileManager defaultManager() {
        if (Instance == null)
            Instance = new NSFileManager();
        return Instance;
    }

    // Locating System Directories

    /**
     * @param directory    The search path directory. The supported values are described in
     *                     NSSearchPathDirectory.
     * @param domain       The file system domain to search. The value for this parameter is one of
     *                     the constants described in
     *                     NSSearchPathDomainMask. You should specify only one domain for your
     *                     search and you may not specify the NSAllDomainsMask
     *                     constant for this parameter.
     * @param url          The name of a directory inside of which you want to create a unique
     *                     temporary directory for autosaving documents or some
     *                     other use. This parameter is ignored unless the directory parameter
     *                     contains the value NSItemReplacementDirectory and the
     *                     domain parameter contains the value NSUserDomainMask. When creating a
     *                     temporary directory, the shouldCreate parameter is
     *                     ignored and the directory is always created.
     * @param shouldCreate Specify YES if you want the directory to be created if it does not
     *                     exist.
     * @param error        On input, a pointer to an error object. If an error occurs, this pointer
     *                     is set to an actual error object containing the
     *                     error information. You may specify nil for this parameter if you do not
     *                     want the error information.
     * @return Return Value The NSURL for the requested directory or nil if an error occurred.
     * @Signature: URLForDirectory:inDomain:appropriateForURL:create:error:
     * @Declaration : - (NSURL *)URLForDirectory:(NSSearchPathDirectory)directory
     * inDomain:(NSSearchPathDomainMask)domain
     * appropriateForURL:(NSURL *)url create:(BOOL)shouldCreate error:(NSError **)error
     * @Description : Locates and optionally creates the specified common directory in a domain.
     * @Discussion You typically use this method to locate one of the standard system directories,
     * such as the Documents, Application
     * Support or Caches directories. You can also use this method to create a new temporary
     * directory for storing things like
     * autosave files; to do so, specify NSItemReplacementDirectory for the directory parameter,
     * NSUserDomainMask for the domain
     * parameter, and a valid parent directory for the url parameter. After locating (or creating)
     * the desired directory, this
     * method returns the URL for that directory. If more than one appropriate directory exists in
     * the specified domain, this
     * method returns only the first one it finds. Passing a directory and domain pair that makes
     * no
     * sense (for example
     * NSDesktopDirectory and NSNetworkDomainMask) raises an exception. Important: If you use this
     * method to create a temporary
     * directory, your app is responsible for removing the directory when you no longer need it.
     **/
    
    public NSURL URLForDirectoryInDomainAppropriateForURLCreateError(NSSearchPathDirectory directory, NSSearchPathDomainMask domain,
                                                                     NSURL url, boolean shouldCreate, NSError[] error) {
        NSURL nsurl = new NSURL();
        if (domain == NSSearchPathDomainMask.NSAllDomainsMask)
            throw new IllegalArgumentException("you may not specify the NSAllDomainsMask parameter");
            // user domain
        else if (domain == NSSearchPathDomainMask.NSUserDomainMask) {
            // InternalStorage
            File cacheDirectory;
            String tmpPath = url.absoluteString().getWrappedString();
            if (directory == NSSearchPathDirectory.NSItemReplacementDirectory || directory == NSSearchPathDirectory.NSCachesDirectory) {
                cacheDirectory = context.getCacheDir();
                // create temporary directory
                try {
                    if (cacheDirectory.exists() && cacheDirectory.canWrite() && shouldCreate) {
                        File result = File.createTempFile(tmpPath, null, cacheDirectory);
                        nsurl.setPath(new NSString(result.getAbsolutePath()));
                        return nsurl;
                    }

                } catch (IOException e) {
                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                    error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
                            null);
                }
            }
        } else if (domain == NSSearchPathDomainMask.NSLocalDomainMask) {
            // check if External storage is available
            File file = null;
            if (directory == NSSearchPathDirectory.NSMoviesDirectory)
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            else if (directory == NSSearchPathDirectory.NSDownloadsDirectory)
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            else if (directory == NSSearchPathDirectory.NSMusicDirectory)
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            else if (directory == NSSearchPathDirectory.NSPicturesDirectory)
                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            else if (directory == NSSearchPathDirectory.NSSharedPublicDirectory)
                file = Environment.getExternalStorageDirectory();
                //
            else if (file != null) {
                nsurl.setPath(new NSString(file.getAbsolutePath()));
                return nsurl;
            }
        }

        return url;
    }

    /**
     * @param directory  The search path directory. The supported values are described in
     *                   NSSearchPathDirectory.
     * @param domainMask The file system domain to search. The value for this parameter is one or
     *                   more of the constants described in
     *                   NSSearchPathDomainMask.
     * @return Return Value An array of NSURL objects identifying the requested directories. The
     * directories are ordered according to the
     * order of the domain mask constants, with items in the user domain first and items in the
     * system domain last.
     * @Signature: URLsForDirectory:inDomains:
     * @Declaration : - (NSArray *)URLsForDirectory:(NSSearchPathDirectory)directory
     * inDomains:(NSSearchPathDomainMask)domainMask
     * @Description : Returns an array of URLs for the specified common directory in the requested
     * domains.
     * @Discussion This method is intended to locate known and common directories in the system.
     * For
     * example, setting the directory to
     * NSApplicationDirectory, will return the Applications directories in the requested domain.
     * There are a number of common
     * directories available in the NSSearchPathDirectory, including: NSDesktopDirectory,
     * NSApplicationSupportDirectory, and
     * many more.
     **/
    
    public NSArray<NSString> URLsForDirectoryInDomains(NSSearchPathDirectory directory, NSSearchPathDomainMask inDomains) {
        NSArray<NSString> nsArray = new NSArray<NSString>();
        if (inDomains == NSSearchPathDomainMask.NSLocalDomainMask) {
            // External Storage
            if (AndroidFileUtils.isExternalStorageReadable()) {
                File esd = Environment.getExternalStorageDirectory();
                File[] files = esd.listFiles();
                for (File mfile : files) {
                    nsArray.getWrappedList().add(new NSString(mfile.getAbsolutePath()));
                }
            }
            return nsArray;
        } else if ((inDomains == NSSearchPathDomainMask.NSUserDomainMask) && (directory == NSSearchPathDirectory.NSUserDirectory ||
                directory == NSSearchPathDirectory.NSApplicationDirectory)) {
            File[] files = context.getFilesDir().listFiles();
            for (File mfile : files) {
                nsArray.getWrappedList().add(new NSString(mfile.getAbsolutePath()));
            }
            return nsArray;
        }


        return null;
    }

    // Locating Application Group Container Directories

    /**
     * @Signature: containerURLForSecurityApplicationGroupIdentifier:
     * @Declaration : - (NSURL *)containerURLForSecurityApplicationGroupIdentifier:(NSString
     * *)groupIdentifier
     * @Description : Returns the container directory associated with the specified security
     * application group ID.
     **/
    
    public NSURL containerURLForSecurityApplicationGroupIdentifier(NSString groupIdentifier) {
        return null;
    }

    // Discovering Directory Contents

    /**
     * @param url   The URL for the directory whose contents you want to enumerate.
     * @param keys  An array of keys that identify the file properties that you want pre-fetched
     *              for
     *              each item in the directory. For each
     *              returned URL, the specified properties are fetched and cached in the NSURL
     *              object. For a list of keys you can specify, see
     *              Common File System Resource Keys.
     * @param mask  Options for the enumeration. Because this method performs only shallow
     *              enumerations, options that prevent descending into
     *              subdirectories or packages are not allowed; the only supported option is
     *              NSDirectoryEnumerationSkipsHiddenFiles.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value An array of NSURL objects, each of which identifies a file, directory,
     * or symbolic link contained in url. If the
     * directory contains no entries, this method returns an empty array. If an error occurs, this
     * method returns nil and assigns an
     * appropriate error object to the error parameter.
     * @Signature: contentsOfDirectoryAtURL:includingPropertiesForKeys:options:error:
     * @Declaration : - (NSArray *)contentsOfDirectoryAtURL:(NSURL *)url
     * includingPropertiesForKeys:(NSArray *)keys
     * options:(NSDirectoryEnumerationOptions)mask error:(NSError **)error
     * @Description : Performs a shallow search of the specified directory and returns URLs for the
     * contained items.
     * @Discussion This method performs a shallow search of the directory and therefore does not
     * traverse symbolic links or return the
     * contents of any subdirectories. This method also does not return URLs for the current
     * directory (“.�?), parent directory
     * (“..�?), or resource forks (files that begin with “._�?) but it does return other hidden files
     * (files that begin with a
     * period character). If you need to perform a deep enumeration, use the
     * enumeratorAtURL:includingPropertiesForKeys:options:errorHandler: method instead. The order
     * of
     * the files in the returned
     * array is undefined.
     **/
    
    public NSArray<NSURL> contentsOfDirectoryAtURLIncludingPropertiesForKeysOptionsError(NSURL url, NSArray<NSString> keys,
                                                                                         NSDirectoryEnumerationOptions mask, NSError[] error) {
        // result
        NSArray<NSURL> nsArray = new NSArray<NSURL>();

        if (!mask.equals(NSDirectoryEnumerationOptions.NSDirectoryEnumerationSkipsHiddenFiles))
            throw new IllegalArgumentException("mask are not allowed");

        String path = url.absoluteString().getWrappedString();
        File f = new File(path);
        if (!f.exists()) {
            try {
                throw new Exception("file not exists");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        } else if (!f.isDirectory()) {
            try {
                throw new Exception("is not a directory");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        }

        File[] dirContents = f.listFiles();
        for (File mfile : dirContents) {
            if (mfile.getName().startsWith("._"))
                continue;
            NSURL nsurl = new NSURL(mfile.getAbsolutePath());
            if (keys != null && !keys.getWrappedList().isEmpty()) {
                // for each key in keys fetch value from basicFileAttrib and set it in NSUrl
                for (NSString fileKey : keys.getWrappedList()) {
                    if (fileKey.equals(NSURL.NSURLFileSizeKey))
                        nsurl.fileSizeKey.numberValue = String.valueOf(mfile.length());
                    else if (fileKey.equals(NSURL.NSURLFileAllocatedSizeKey))
                        nsurl.fileAllocatedSizeKey.numberValue = String.valueOf(mfile.length());
                        // if (fileKey.equals(NSURL.NSURLIsAliasFileKey))
                        // nsurl.isAliasFileKey =
                    else if (fileKey.equals(NSURL.NSURLTotalFileAllocatedSizeKey))
                        nsurl.fileAllocatedSizeKey.numberValue = String.valueOf(mfile.length());
                    else if (fileKey.equals(NSURL.NSURLTotalFileSizeKey))
                        nsurl.totalFileSizeKey.numberValue = String.valueOf(mfile.length());
                }

            }
            nsArray.getWrappedList().add(nsurl);
        }
        return nsArray;
    }

    /**
     * @param path  The path to the directory whose contents you want to enumerate.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value An array of NSString objects, each of which identifies a file,
     * directory, or symbolic link contained in path.
     * Returns an empty array if the directory exists but has no contents. If an error occurs, this
     * method returns nil and assigns
     * an appropriate error object to the error parameter
     * @Signature: contentsOfDirectoryAtPath:error:
     * @Declaration : - (NSArray *)contentsOfDirectoryAtPath:(NSString *)path error:(NSError
     * **)error
     * @Description : Performs a shallow search of the specified directory and returns the paths of
     * any contained items.
     * @Discussion This method performs a shallow search of the directory and therefore does not
     * traverse symbolic links or return the
     * contents of any subdirectories. This method also does not return URLs for the current
     * directory (“.�?), parent directory
     * (“..�?), or resource forks (files that begin with “._�?) but it does return other hidden files
     * (files that begin with a
     * period character). If you need to perform a deep enumeration, use the
     * enumeratorAtURL:includingPropertiesForKeys:options:errorHandler: method instead. The order
     * of
     * the files in the returned
     * array is undefined.
     **/
    
    public NSArray<NSString> contentsOfDirectoryAtPathError(NSString path, NSError[] error) {
        NSArray<NSString> nsArray = new NSArray<NSString>();
        File f = new File(path.getWrappedString());
        if (!f.exists()) {
            try {
                throw new Exception("file not exists");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        }
        if (!f.isDirectory()) {
            try {
                throw new Exception("is not a directory");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        }
        List<String> foldersList = Arrays.asList(new File(path.getWrappedString()).list());
        for (String folderPath : foldersList) {
            nsArray.wrappedList.add(new NSString(folderPath));
        }

        return nsArray;
    }

    /**
     * @param url     The location of the directory for which you want an enumeration. This URL
     *                must
     *                not be a symbolic link that points to the
     *                desired directory. You can use the URLByResolvingSymlinksInPath method to
     *                resolve any symlinks in the URL.
     * @param keys    An array of keys that identify the properties that you want pre-fetched for
     *                each item in the enumeration. The values for
     *                these keys are cached in the corresponding NSURL objects. You may specify nil
     *                for this parameter. For a list of keys you
     *                can specify, see Common File System Resource Keys.
     * @param mask    Options for the enumeration. For a list of valid options, see “Directory
     *                Enumeration Options.�?
     * @param handler An optional 'errorHandler' block argument to call when an error occurs. The
     *                url parameter specifies the item for which
     *                the error occurred and the error parameter contains the error information.
     *                Your handler should return YES when it wants
     *                the enumeration to continue or NO when it wants the enumeration to stop.
     * @return Return Value An NSDirectoryEnumerator object that enumerates the contents of the
     * directory at url. If url is a filename, the
     * method returns an enumerator object that enumerates no files—the first call to nextObject
     * returns nil.
     * @Signature: enumeratorAtURL:includingPropertiesForKeys:options:errorHandler:
     * @Declaration : - (NSDirectoryEnumerator *)enumeratorAtURL:(NSURL *)url
     * includingPropertiesForKeys:(NSArray *)keys
     * options:(NSDirectoryEnumerationOptions)mask errorHandler:(BOOL (^)(NSURL *url, NSError
     * *error))handler
     * @Description : Returns a directory enumerator object that can be used to perform a deep
     * enumeration of the directory at the specified
     * URL.
     * @Discussion Because the enumeration is deep—that is, it lists the contents of all
     * subdirectories—this enumerator object is useful for
     * performing actions that involve large file-system subtrees. If the method is passed a
     * directory on which another file
     * system is mounted (a mount point), it traverses the mount point. This method does not
     * resolve
     * symbolic links or mount
     * points encountered in the enumeration process, nor does it recurse through them if they
     * point
     * to a directory.
     **/
    
    public NSDirectoryEnumerator enumeratorAtURLIncludingPropertiesForKeysOptionsErrorHandler(NSURL url, NSArray<NSString> keys,
                                                                                              NSDirectoryEnumerationOptions mask, PerformBlock.BOOLBlockNSURLNSError handlerBlock) {

        NSDirectoryEnumerator nsDirectoryEnumerator = new NSDirectoryEnumerator();

        File f = new File(url.getUri().toString());
        if (!f.exists()) {
            try {
                throw new Exception("file not exists");
            } catch (Exception e) {
                NSError nsError = new NSError();
                nsError.initWithDomainCodeUserInfo(new NSString("file not exists"), 1, null);
                fileManagerErrorBlock.errorHandler(url, nsError);
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
        if (!f.isDirectory()) {
            try {
                throw new Exception("is not a directory");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                NSError nsError = new NSError();
                nsError.initWithDomainCodeUserInfo(new NSString("is not a directory"), 1, null);
                fileManagerErrorBlock.errorHandler(url, nsError);
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
        // create iterator
        Iterator<File> dirIterator = AndroidFileUtils.iterateFilesAndDirs(f, null, null);
        nsDirectoryEnumerator.mIterator = dirIterator;
        return nsDirectoryEnumerator;
    }

    /**
     * @param path The path of the directory to enumerate.
     * @return Return Value An NSDirectoryEnumerator object that enumerates the contents of the
     * directory at path. If path is a filename,
     * the method returns an enumerator object that enumerates no files—the first call to
     * nextObject
     * will return nil.
     * @Signature: enumeratorAtPath:
     * @Declaration : - (NSDirectoryEnumerator *)enumeratorAtPath:(NSString *)path
     * @Description : Returns a directory enumerator object that can be used to perform a deep
     * enumeration of the directory at the specified
     * path.
     * @Discussion Because the enumeration is deep—that is, it lists the contents of all
     * subdirectories—this enumerator object is useful for
     * performing actions that involve large file-system subtrees. This method does not resolve
     * symbolic links encountered in
     * the traversal process, nor does it recurse through them if they point to a directory.
     **/
    
    public NSDirectoryEnumerator enumeratorAtPath(NSString path) {
        NSDirectoryEnumerator nsDirectoryEnumerator = new NSDirectoryEnumerator();
        File f = new File(path.getWrappedString());
        if (!f.exists()) {
            try {
                throw new Exception("file not exists");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
        if (!f.isDirectory()) {
            try {
                throw new Exception("is not a directory");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
        // create iterator
        Iterator<File> dirIterator = AndroidFileUtils.iterateFilesAndDirs(f, null, null);
        nsDirectoryEnumerator.mIterator = dirIterator;
        return nsDirectoryEnumerator;
    }

    /**
     * @param propertyKeys An array of keys that identify the file properties that you want
     *                     pre-fetched for each volume. For each returned
     *                     URL, the values for these keys are cached in the corresponding NSURL
     *                     objects. You may specify nil for this parameter. For
     *                     a list of keys you can specify, see Common File System Resource Keys.
     * @param options      Option flags for the enumeration. See “Mounted Volume Enumeration
     *                     Options.�?
     * @return Return Value An array of NSURL objects identifying the mounted volumes.
     * @Signature: mountedVolumeURLsIncludingResourceValuesForKeys:options:
     * @Declaration : - (NSArray *)mountedVolumeURLsIncludingResourceValuesForKeys:(NSArray
     * *)propertyKeys
     * options:(NSVolumeEnumerationOptions)options
     * @Description : Returns an array of URLs that identify the mounted volumes available on the
     * computer.
     * @Discussion This call may block if I/O is required to determine values for the requested
     * propertyKeys.
     **/
    
    public NSArray<NSURL> mountedVolumeURLsIncludingResourceValuesForKeysOptions(NSArray propertyKeys, int options) {
        NSArray<NSURL> nsArray = new NSArray<NSURL>();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File esd = Environment.getExternalStorageDirectory();
            // TODO : loop over propertyKeys and set apropiate value using File Attributes
            nsArray.getWrappedList().add(new NSURL(esd.getAbsolutePath()));
        }
        // TODO did we need mounted usb devices in android , no path is avaiable
        // HashMap<String, UsbDevice> attachedUsbDevices = manager.getDeviceList();

        return nsArray;
    }

    /**
     * @param path  The path of the directory to list.
     * @param error If an error occurs, upon return contains an NSError object that describes the
     *              problem. Pass NULL if you do not want
     *              error information.
     * @return Return Value An array of NSString objects, each of which contains the path of an item
     * in the directory specified by path. If
     * path is a symbolic link, this method traverses the link. This method returns nil if it
     * cannot
     * retrieve the device of the
     * linked-to file.
     * @Signature: subpathsOfDirectoryAtPath:error:
     * @Declaration : - (NSArray *)subpathsOfDirectoryAtPath:(NSString *)path error:(NSError
     * **)error
     * @Description : Performs a deep enumeration of the specified directory and returns the paths
     * of all of the contained subdirectories.
     * @Discussion This method recurses the specified directory and its subdirectories. The method
     * skips the “.�? and “..�? directories at
     * each level of the recursion. Because this method recurses the directory’s contents, you
     * might
     * not want to use it in
     * performance-critical code. Instead, consider using the enumeratorAtURL:includingPropertiesForKeys:options:errorHandler:
     * or enumeratorAtPath: method to enumerate the directory contents yourself. Doing so gives you
     * more control over the
     * retrieval of items and more opportunities to abort the enumeration or perform other tasks at
     * the same time.
     **/
    
    public NSArray<NSString> subpathsOfDirectoryAtPathError(NSString path, NSError[] error) {
        NSArray<NSString> nsArray = new NSArray<NSString>();
        File directory = new File(path.getWrappedString());
        if (directory.isDirectory()) {
            Collection<File> files = AndroidFileUtils.listFiles(directory);
            while (files.iterator().hasNext()) {
                File subFile = files.iterator().next();
                nsArray.getWrappedList().add(new NSString(subFile.getAbsolutePath()));
            }
        } else {
            try {
                throw new Exception("not a directory");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        }
        return null;
    }

    /**
     * @param path The path of the directory to list.
     * @return Return Value An array of NSString objects, each of which contains the path of an item
     * in the directory specified by path. If
     * path is a symbolic link, this method traverses the link. This method returns nil if it
     * cannot
     * retrieve the device of the
     * linked-to file.
     * @Signature: subpathsAtPath:
     * @Declaration : - (NSArray *)subpathsAtPath:(NSString *)path
     * @Description : Returns an array of strings identifying the paths for all items in the
     * specified directory.
     * @Discussion This method recurses the specified directory and its subdirectories. The method
     * skips the “.�? and “..�? directories at
     * each level of the recursion. This method reveals every element of the subtree at path,
     * including the contents of file
     * packages (such as apps, nib files, and RTFD files).
     **/
    
    public NSArray subpathsAtPath(NSString path) {
        NSArray<NSString> nsArray = new NSArray<NSString>();
        Collection<File> files = AndroidFileUtils.listFiles(new File(path.getWrappedString()));
        while (files.iterator().hasNext()) {
            File subFile = files.iterator().next();
            nsArray.getWrappedList().add(new NSString(subFile.getAbsolutePath()));
        }
        return nsArray;
    }

    // Creating and Deleting Items

    /**
     * @param url                 A file URL that specifies the directory to create. If you want to
     *                            specify a relative path, you must set the current
     *                            working directory before creating the corresponding NSURL object.
     *                            This parameter must not be nil.
     * @param createIntermediates If YES, this method creates any non-existent parent directories
     *                            as
     *                            part of creating the directory in url.
     *                            If NO, this method fails if any of the intermediate parent
     *                            directories does not exist.
     * @param attributes          The file attributes for the new directory. You can set the owner
     *                            and group numbers, file permissions, and
     *                            modification date. If you specify nil for this parameter, the
     *                            directory is created according to the umask(2) Mac OS X
     *                            Developer Tools Manual Page of the process. The “Constants�?
     *                            section lists the global constants used as keys in the
     *                            attributes dictionary. Some of the keys, such as
     *                            NSFileHFSCreatorCode and NSFileHFSTypeCode, do not apply to
     *                            directories.
     * @param error               On input, a pointer to an error object. If an error occurs, this
     *                            pointer is set to an actual error object containing the
     *                            error information. You may specify nil for this parameter if you
     *                            do not want the error information.
     * @return Return Value YES if the directory was created, YES if createIntermediates is set and
     * the directory already exists), or NO if
     * an error occurred.
     * @Signature: createDirectoryAtURL:withIntermediateDirectories:attributes:error:
     * @Declaration : - (BOOL)createDirectoryAtURL:(NSURL *)url withIntermediateDirectories:(BOOL)createIntermediates
     * attributes:(NSDictionary *)attributes error:(NSError **)error
     * @Description : Creates a directory with the given attributes at the specified URL.
     * @Discussion If you specify nil for the attributes parameter, this method uses a default set
     * of values for the owner, group, and
     * permissions of any newly created directories in the path. Similarly, if you omit a specific
     * attribute, the default value
     * is used. The default values for newly created directories are as follows: Permissions are
     * set
     * according to the umask of
     * the current process. For more information, see umask. The owner ID is set to the effective
     * user ID of the process. The
     * group ID is set to that of the parent directory. If you want to specify a relative path for
     * url, you must set the current
     * working directory before creating the corresponding NSURL object.
     **/
    
    public boolean createDirectoryAtURLWithIntermediateDirectoriesAttributesError(NSURL url, boolean createIntermediates,
                                                                                  NSDictionary attributes, NSError[] error) {
        return createDirectoryAtPathWithIntermediateDirectoriesAttributesError(url.path(), createIntermediates, attributes, error);
    }

    /**
     * @param path                A path string identifying the directory to create. You may
     *                            specify
     *                            a full path or a path that is relative to the current
     *                            working directory. This parameter must not be nil.
     * @param createIntermediates If YES, this method creates any non-existent parent directories
     *                            as
     *                            part of creating the directory in path.
     *                            If NO, this method fails if any of the intermediate parent
     *                            directories does not exist. This method also fails if any of
     *                            the intermediate path elements corresponds to a file and not a
     *                            directory.
     * @param attributes          The file attributes for the new directory and any newly created
     *                            intermediate directories. You can set the owner and
     *                            group numbers, file permissions, and modification date. If you
     *                            specify nil for this parameter or omit a particular value,
     *                            one or more default values are used as described in the
     *                            discussion. For a list of keys you can include in this
     *                            dictionary,
     *                            see “Constants�? section lists the global constants used as keys
     *                            in
     *                            the attributes dictionary. Some of the keys, such as
     *                            NSFileHFSCreatorCode and NSFileHFSTypeCode, do not apply to
     *                            directories.
     * @param error               On input, a pointer to an error object. If an error occurs, this
     *                            pointer is set to an actual error object containing the
     *                            error information. You may specify nil for this parameter if you
     *                            do not want the error information.
     * @return Return Value YES if the directory was created, YES if createIntermediates is set and
     * the directory already exists), or NO if
     * an error occurred.
     * @Signature: createDirectoryAtPath:withIntermediateDirectories:attributes:error:
     * @Declaration : - (BOOL)createDirectoryAtPath:(NSString *)path withIntermediateDirectories:(BOOL)createIntermediates
     * attributes:(NSDictionary *)attributes error:(NSError **)error
     * @Description : Creates a directory with given attributes at the specified path.
     * @Discussion If you specify nil for the attributes parameter, this method uses a default set
     * of values for the owner, group, and
     * permissions of any newly created directories in the path. Similarly, if you omit a specific
     * attribute, the default value
     * is used. The default values for newly created directories are as follows: Permissions are
     * set
     * according to the umask of
     * the current process. For more information, see umask. The owner ID is set to the effective
     * user ID of the process. The
     * group ID is set to that of the parent directory.
     **/
    
    public boolean createDirectoryAtPathWithIntermediateDirectoriesAttributesError(NSString path, boolean createIntermediates,
                                                                                   NSDictionary attributes, NSError[] error) {
        File mFile = new File(path.getWrappedString());
        // set Attributes
        if (attributes != null) {
            // TODO finish this later
        }
        if (createIntermediates) {

            return mFile.mkdirs();
        } else {
            return mFile.mkdir();
        }
    }

    /**
     * @param path       The path for the new file.
     * @param contents   A data object containing the contents of the new file.
     * @param attributes A dictionary containing the attributes to associate with the new file. You
     *                   can use these attributes to set the
     *                   owner and group numbers, file permissions, and modification date. For a
     *                   list of keys, see “File Attribute Keys.�? If you
     *                   specify nil for attributes, the file is created with a set of default
     *                   attributes.
     * @return Return Value YES if the operation was successful or if the item already exists,
     * otherwise NO.
     * @Signature: createFileAtPath:contents:attributes:
     * @Declaration : - (BOOL)createFileAtPath:(NSString *)path contents:(NSData *)contents
     * attributes:(NSDictionary *)attributes
     * @Description : Creates a file with the specified content and attributes at the given
     * location.
     * @Discussion If you specify nil for the attributes parameter, this method uses a default set
     * of values for the owner, group, and
     * permissions of any newly created directories in the path. Similarly, if you omit a specific
     * attribute, the default value
     * is used. The default values for newly created files are as follows: Permissions are set
     * according to the umask of the
     * current process. For more information, see umask. The owner ID is set to the effective user
     * ID of the process. The group
     * ID is set to that of the parent directory. If a file already exists at path, this method
     * overwrites the contents of that
     * file if the current process has the appropriate privileges to do so.
     **/
    
    public boolean createFileAtPathContentsAttributes(NSString path, NSData contents, NSDictionary attributes) {
        File file = new File(path.getWrappedString());
        if (file.canWrite()) {
            // set attributes
            // TODO finish
            try {
                return file.createNewFile();
            } catch (IOException e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            }
        }
        return false;
    }

    /**
     * @param URL   A file URL specifying the file or directory to remove. If the URL specifies a
     *              directory, the contents of that directory
     *              are recursively removed. You may specify nil for this parameter.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value YES if the item was removed successfully or if URL was nil. Returns NO
     * if an error occurred. If the delegate
     * aborts the operation for a file, this method returns YES. However, if the delegate aborts
     * the
     * operation for a directory, this
     * method returns NO.
     * @Signature: removeItemAtURL:error:
     * @Declaration : - (BOOL)removeItemAtURL:(NSURL *)URL error:(NSError **)error
     * @Description : Removes the file or directory at the specified URL.
     * @Discussion Prior to removing each item, the file manager asks its delegate if it should
     * actually do so. It does this by calling the
     * fileManager:shouldRemoveItemAtURL: method; if that method is not implemented (or the process
     * is running in OS X 10.5 or
     * earlier) it calls the fileManager:shouldRemoveItemAtPath: method instead. If the delegate
     * method returns YES, or if the
     * delegate does not implement the appropriate methods, the file manager proceeds to remove the
     * file or directory. If there
     * is an error removing an item, the file manager may also call the delegate’s
     * fileManager:shouldProceedAfterError:removingItemAtURL: or fileManager:shouldProceedAfterError:removingItemAtPath:
     * method
     * to determine how to proceed. Removing and item also removes all old versions of that item,
     * invalidating any URLs returned
     * by the URLForPublishingUbiquitousItemAtURL:expirationDate:error: method to old versions.
     **/
    
    public boolean removeItemAtURLError(NSURL aURL, NSError[] error) {
        return removeItemAtPathError(aURL.path(), error);
    }

    /**
     * @param path  A path string indicating the file or directory to remove. If the path specifies
     *              a directory, the contents of that
     *              directory are recursively removed. You may specify nil for this parameter.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value YES if the item was removed successfully or if path was nil. Returns NO
     * if an error occurred. If the delegate
     * aborts the operation for a file, this method returns YES. However, if the delegate aborts
     * the
     * operation for a directory, this
     * method returns NO.
     * @Signature: removeItemAtPath:error:
     * @Declaration : - (BOOL)removeItemAtPath:(NSString *)path error:(NSError **)error
     * @Description : Removes the file or directory at the specified path.
     * @Discussion Prior to removing each item, the file manager asks its delegate if it should
     * actually do so. It does this by calling the
     * fileManager:shouldRemoveItemAtURL: method; if that method is not implemented (or the process
     * is running in OS X 10.5 or
     * earlier) it calls the fileManager:shouldRemoveItemAtPath: method instead. If the delegate
     * method returns YES, or if the
     * delegate does not implement the appropriate methods, the file manager proceeds to remove the
     * file or directory. If there
     * is an error removing an item, the file manager may also call the delegate’s
     * fileManager:shouldProceedAfterError:removingItemAtURL: or fileManager:shouldProceedAfterError:removingItemAtPath:
     * method
     * to determine how to proceed. Removing and item also removes all old versions of that item,
     * invalidating any URLs returned
     * by the URLForPublishingUbiquitousItemAtURL:expirationDate:error: method to old versions.
     **/
    
    public boolean removeItemAtPathError(NSString path, NSError[] error) {
        File file = new File(path.getWrappedString());
        if (file.exists()) {
            try {
                AndroidFileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
                return false;
            }
        }
        return false;
    }

    /**
     * @param originalItemURL The item whose contents you want to replace.
     * @param newItemURL      The item containing the new content for originalItemURL. It is
     *                        recommended that you put this item in a temporary
     *                        directory as provided by the OS. If a temporary directory is not
     *                        available, put this item in a uniquely named directory
     *                        that is in the same directory as the original item.
     * @param backupItemName  Optional. If provided, this name is used to create a backup of the
     *                        original item. The backup is placed in the
     *                        same directory as the original item. If an error occurs during the
     *                        creation of the backup item, the operation will fail.
     *                        If there is already an item with the same name as the backup item,
     *                        that item will be removed. The backup item will be
     *                        removed in the event of success unless the NSFileManagerItemReplacementWithoutDeletingBackupItem
     *                        option is provided in
     *                        options.
     * @param options         Specifies the options to use during the replacement. Typically, you
     *                        pass
     *                        NSFileManagerItemReplacementUsingNewMetadataOnly for this parameter,
     *                        which uses only the metadata from the new item. You
     *                        can also combine the options described in “NSFileManagerItemReplacementOptions�?
     *                        using the C-bitwise OR operator.
     * @param resultingURL    On input, a pointer for a URL object. When the item is replaced, this
     *                        pointer is set to the URL of the new item.
     *                        If no new file system object is required, the URL object in this
     *                        parameter may be the same passed to the originalItemURL
     *                        parameter. However, if a new file system object is required, the URL
     *                        object may be different. For example, replacing an
     *                        RTF document with an RTFD document requires the creation of a new
     *                        file.
     * @param error           On input, a pointer to an error object. If an error occurs, this
     *                        pointer is set to an actual error object containing the
     *                        error information. You may specify nil for this parameter if you do
     *                        not want the error information.
     * @return Return Value YES if the replacement was successful or NO if an error occurred.
     * @Signature: replaceItemAtURL:withItemAtURL:backupItemName:options:resultingItemURL:error:
     * @Declaration : - (BOOL)replaceItemAtURL:(NSURL *)originalItemURL withItemAtURL:(NSURL
     * *)newItemURL backupItemName:(NSString
     * *)backupItemName options:(NSFileManagerItemReplacementOptions)options
     * resultingItemURL:(NSURL
     * **)resultingURL
     * error:(NSError **)error
     * @Description : Replaces the contents of the item at the specified URL in a manner that
     * insures no data loss occurs.
     * @Discussion By default, the creation date, permissions, Finder label and color, and
     * Spotlight
     * comments of the original item will be
     * preserved on the resulting item. If an error occurs and the original item is not in the
     * original location or a temporary
     * location, the returned error object contains a user info dictionary with the
     * NSFileOriginalItemLocationKey key. The value
     * assigned to that key is an NSURL object with the location of the item. The error code is one
     * of the file-related errors
     * described in NSError Codes.
     **/
    
    public boolean replaceItemAtURLWithItemAtURLBackupItemNameOptionsResultingItemURLError(NSURL originalItemURL, NSURL newItemURL,
                                                                                           NSString backupItemName, NSFileManagerItemReplacementOptions options, NSURL[] resultingURL, NSError[] error) {
        File file = new File(originalItemURL.path().getWrappedString());
        if ((file.exists()) && (backupItemName != null)) {
            // create a temporary item
            try {
                File tmpFile = File.createTempFile(backupItemName.getWrappedString(), null, file);
            } catch (IOException e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(),
                        null);
                return false;
            }
        }

        return false;
    }

    // Moving and Copying Items

    /**
     * @param srcURL The file URL that identifies the file you want to copy. The URL in this
     *               parameter must not be a file reference URL.
     *               This parameter must not be nil.
     * @param dstURL The URL at which to place the copy of srcURL. The URL in this parameter must
     *               not be a file reference URL and must
     *               include the name of the file in its new location. This parameter must not be
     *               nil.
     * @param error  On input, a pointer to an error object. If an error occurs, this pointer is
     *               set
     *               to an actual error object containing the
     *               error information. You may specify nil for this parameter if you do not want
     *               the error information.
     * @return Return Value YES if the item was copied successfully or the file manager’s delegate
     * aborted the operation deliberately.
     * Returns NO if an error occurred.
     * @Signature: copyItemAtURL:toURL:error:
     * @Declaration : - (BOOL)copyItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL error:(NSError
     * **)error
     * @Description : Copies the file at the specified URL to a new location synchronously.
     * @Discussion When copying items, the current process must have permission to read the file or
     * directory at srcURL and write the parent
     * directory of dstURL. If the item at srcURL is a directory, this method copies the directory
     * and all of its contents,
     * including any hidden files. If a file with the same name already exists at dstURL, this
     * method aborts the copy attempt
     * and returns an appropriate error. If the last component of srcURL is a symbolic link, only
     * the link is copied to the new
     * path. Prior to copying each item, the file manager asks its delegate if it should actually
     * do
     * so. It does this by calling
     * the fileManager:shouldCopyItemAtURL:toURL: method; if that method is not implemented (or the
     * process is running in OS X
     * 10.5 or earlier) it calls the fileManager:shouldCopyItemAtPath:toPath: method instead. If
     * the
     * delegate method returns
     * YES, or if the delegate does not implement the appropriate methods, the file manager
     * proceeds
     * to copy the file or
     * directory. If there is an error copying an item, the file manager may also call the
     * delegate’s
     * fileManager:shouldProceedAfterError:copyingItemAtURL:toURL: or
     * fileManager:shouldProceedAfterError:copyingItemAtPath:toPath: method to determine how to
     * proceed.
     **/
    
    public void copyItemAtURLToURLError(NSURL srcURL, NSURL dstURL, NSError[] error) {
        File fileSrc = new File(srcURL.absoluteString().getWrappedString());
        File fileDest = new File(dstURL.absoluteString().getWrappedString());
        try {
            if (fileSrc.canRead() && fileDest.canWrite())
                AndroidFileUtils.copyDirectory(fileSrc, fileDest);
        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
        }
    }

    /**
     * @param srcPath The path to the file or directory you want to move. This parameter must not
     *                be
     *                nil.
     * @param dstPath The path at which to place the copy of srcPath. This path must include the
     *                name of the file or directory in its new
     *                location. This parameter must not be nil.
     * @param error   On input, a pointer to an error object. If an error occurs, this pointer is
     *                set to an actual error object containing the
     *                error information. You may specify nil for this parameter if you do not want
     *                the error information.
     * @return Return Value YES if the item was copied successfully or the file manager’s delegate
     * aborted the operation deliberately.
     * Returns NO if an error occurred.
     * @Signature: copyItemAtPath:toPath:error:
     * @Declaration : - (BOOL)copyItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
     * error:(NSError **)error
     * @Description : Copies the item at the specified path to a new location synchronously.
     * @Discussion When copying items, the current process must have permission to read the file or
     * directory at srcPath and write the
     * parent directory of dstPath. If the item at srcPath is a directory, this method copies the
     * directory and all of its
     * contents, including any hidden files. If a file with the same name already exists at
     * dstPath,
     * this method aborts the copy
     * attempt and returns an appropriate error. If the last component of srcPath is a symbolic
     * link, only the link is copied to
     * the new path. Prior to copying an item, the file manager asks its delegate if it should
     * actually do so for each item. It
     * does this by calling the fileManager:shouldCopyItemAtURL:toURL: method; if that method is
     * not
     * implemented it calls the
     * fileManager:shouldCopyItemAtPath:toPath: method instead. If the delegate method returns YES,
     * or if the delegate does not
     * implement the appropriate methods, the file manager copies the given file or directory. If
     * there is an error copying an
     * item, the file manager may also call the delegate’s fileManager:shouldProceedAfterError:copyingItemAtURL:toURL:
     * or
     * fileManager:shouldProceedAfterError:copyingItemAtPath:toPath: method to determine how to
     * proceed.
     **/
    
    public boolean copyItemAtPathToPathError(NSString srcPath, NSString dstPath, NSError[] error) {
        boolean isDone = false;
        File sourceItem = null;
        File destItem = new File(dstPath.getWrappedString());
        if (!srcPath.getWrappedString().contains("assets")) {
            sourceItem = new File(srcPath.getWrappedString());
            if (!sourceItem.canRead() || !destItem.getParentFile().canWrite() || destItem.exists())
                try {
                    isDone = false;
                } catch (Exception e) {
                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                    isDone = false;
                    error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
                }
            try {
                if (sourceItem.isDirectory())
                    AndroidFileUtils.copyDirectory(sourceItem, destItem);
                else {
                    AndroidFileUtils.copyFile(sourceItem, destItem);
                }
                isDone = true;
            } catch (IOException e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                isDone = false;
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }
        } else {
            AssetManager am = GenericMainContext.sharedContext.getAssets();
            try {
                isDone = copyAsset(am,
                        srcPath.getWrappedString().split(" ")[1],
                        destItem.getPath());
            } catch (Exception e) {
                LOGGER.info(String.valueOf(e));
            }


        }


        return isDone;
    }

    private static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * @param srcURL The file URL that identifies the file or directory you want to move. The URL
     *               in
     *               this parameter must not be a file
     *               reference URL. This parameter must not be nil.
     * @param dstURL The new location for the item in srcURL. The URL in this parameter must not be
     *               a file reference URL and must include
     *               the name of the file or directory in its new location. This parameter must not
     *               be nil.
     * @param error  On input, a pointer to an error object. If an error occurs, this pointer is
     *               set
     *               to an actual error object containing the
     *               error information. You may specify nil for this parameter if you do not want
     *               the error information.
     * @return Return Value YES if the item was moved successfully or the file manager’s delegate
     * aborted the operation deliberately.
     * Returns NO if an error occurred.
     * @Signature: moveItemAtURL:toURL:error:
     * @Declaration : - (BOOL)moveItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL error:(NSError
     * **)error
     * @Description : Moves the file or directory at the specified URL to a new location
     * synchronously.
     * @Discussion When moving items, the current process must have permission to read the item at
     * srcURL and write the parent directory of
     * dstURL. If the item at srcURL is a directory, this method moves the directory and all of its
     * contents, including any
     * hidden files. If an item with the same name already exists at dstURL, this method aborts the
     * move attempt and returns an
     * appropriate error. If the last component of srcURL is a symbolic link, only the link is
     * moved
     * to the new path; the item
     * pointed to by the link remains at its current location. Prior to moving the item, the file
     * manager asks its delegate if
     * it should actually move it. It does this by calling the fileManager:shouldMoveItemAtURL:toURL:
     * method; if that method is
     * not implemented it calls the fileManager:shouldMoveItemAtPath:toPath: method instead. If the
     * item being moved is a
     * directory, the file manager notifies the delegate only for the directory itself and not for
     * any of its contents. If the
     * delegate method returns YES, or if the delegate does not implement the appropriate methods,
     * the file manager moves the
     * file. If there is an error moving one out of several items, the file manager may also call
     * the delegate’s
     * fileManager:shouldProceedAfterError:movingItemAtURL:toURL: or
     * fileManager:shouldProceedAfterError:movingItemAtPath:toPath: method to determine how to
     * proceed. If the source and
     * destination of the move operation are not on the same volume, this method copies the item
     * first and then removes it from
     * its current location. This behavior may trigger additional delegate notifications related to
     * copying and removing
     * individual items.
     **/
    
    public boolean moveItemAtURLToURLError(NSURL srcURL, NSURL dstURL, NSError[] error) {
        return moveItemAtPathToPathError(srcURL.path(), dstURL.path(), error);
    }

    /**
     * @param srcPath The path to the file or directory you want to move. This parameter must not
     *                be
     *                nil.
     * @param dstPath The new path for the item in srcPath. This path must include the name of the
     *                file or directory in its new location.
     *                This parameter must not be nil.
     * @param error   On input, a pointer to an error object. If an error occurs, this pointer is
     *                set to an actual error object containing the
     *                error information. You may specify nil for this parameter if you do not want
     *                the error information.
     * @return Return Value YES if the item was moved successfully or the file manager’s delegate
     * aborted the operation deliberately.
     * Returns NO if an error occurred.
     * @Signature: moveItemAtPath:toPath:error:
     * @Declaration : - (BOOL)moveItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
     * error:(NSError **)error
     * @Description : Moves the file or directory at the specified path to a new location
     * synchronously.
     * @Discussion When moving items, the current process must have permission to read the item at
     * srcPath and write the parent directory of
     * dstPath. If the item at srcPath is a directory, this method moves the directory and all of
     * its contents, including any
     * hidden files. If an item with the same name already exists at dstPath, this method aborts
     * the
     * move attempt and returns an
     * appropriate error. If the last component of srcPath is a symbolic link, only the link is
     * moved to the new path; the item
     * pointed to by the link remains at its current location. Prior to moving the item, the file
     * manager asks its delegate if
     * it should actually move it. It does this by calling the fileManager:shouldMoveItemAtURL:toURL:
     * method; if that method is
     * not implemented it calls the fileManager:shouldMoveItemAtPath:toPath: method instead. If the
     * item being moved is a
     * directory, the file manager notifies the delegate only for the directory itself and not for
     * any of its contents. If the
     * delegate method returns YES, or if the delegate does not implement the appropriate methods,
     * the file manager moves the
     * file. If there is an error moving one out of several items, the file manager may also call
     * the delegate’s
     * fileManager:shouldProceedAfterError:movingItemAtURL:toURL: or
     * fileManager:shouldProceedAfterError:movingItemAtPath:toPath: method to determine how to
     * proceed. If the source and
     * destination of the move operation are not on the same volume, this method copies the item
     * first and then removes it from
     * its current location. This behavior may trigger additional delegate notifications related to
     * copying and removing
     * individual items.
     **/
    
    public boolean moveItemAtPathToPathError(NSString srcPath, NSString dstPath, NSError[] error) {
        File sourceItem = new File(srcPath.getWrappedString());
        File destItem = new File(dstPath.getWrappedString());
        if (!sourceItem.canRead() || !destItem.getParentFile().canWrite() || destItem.exists())
            try {
                throw new Exception("you don't have permission to move to destination or item already exist , copy abored ...");
            } catch (Exception e) {
                Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
            }

        try {
            if (sourceItem.isDirectory())
                AndroidFileUtils.moveDirectory(sourceItem, destItem);
            else {
                AndroidFileUtils.moveFile(sourceItem, destItem);
            }
            return true;
        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            error[0] = NSError.errorWithDomainCodeUserInfo(NSString.stringWithString(new NSString(e.getMessage())), e.hashCode(), null);
        }
        return false;
    }

    // Managing ICloud-Based Items

    /**
     * @return Return Value An opaque object that identifies the current iCloud user. If iCloud is
     * unavailable for any reason or there is no
     * logged-in user, this method returns nil.
     * @Signature: ubiquityIdentityToken
     * @Declaration : - (id < NSObject, NSCopying, NSCoding >)ubiquityIdentityToken
     * @Description : Returns an opaque token that represents the current iCloud (“ubiquity�?)
     * identity.
     * @Discussion Use this method to determine if iCloud is currently available. Because this
     * method returns relatively quickly, you can
     * call it at launch time and you can call it from your app’s main thread. You can use the
     * token
     * returned by this method,
     * together with the NSUbiquityIdentityDidChangeNotification notification, to detect when the
     * user logs in or out of iCloud
     * and to detect changes to the active iCloud account. When the user logs in with a different
     * iCloud account, the returned
     * identity token changes and the system posts the notification. If you stored or archived the
     * previous token, you compare
     * that token to the newly obtained one using the isEqual: method to determine if the users are
     * the same or different.
     * Calling this method does not establish access to your app’s ubiquity containers. To
     * establish
     * access to a ubiquity
     * container, call the URLForUbiquityContainerIdentifier: method. In OS X, you can instead use
     * an NSDocument object, which
     * establishes access automatically.
     **/
    
    public void ubiquityIdentityToken() {
        // TODO WILL NOT BE IMPLEMENTED
    }

    /**
     * - (NSURL *)URLForUbiquityContainerIdentifier:(NSString *)containerID
     *
     * @param containerID The fully-qualified container identifier for a ubiquity container. The
     *                    string you specify must not contain
     *                    wildcards and must be of the form <TEAMID>.<CONTAINER>, where <TEAMID> is
     *                    your development team ID and <CONTAINER>
     *                    describes the bundle identifier of the container you want to access. The
     *                    container identifiers for your app must be
     *                    declared in the com.apple.developer.ubiquity-container-identifiers array
     *                    of the .entitlements property list file in your
     *                    Xcode project. If you specify nil in this parameter, this method returns
     *                    the first container listed in the
     *                    com.apple.developer.ubiquity-container-identifiers entitlement array.
     * @return Return Value A URL pointing to the specified ubiquity container, or nil if the
     * container could not be located or if iCloud
     * storage is unavailable for the current user or device.
     * @Description : Returns the URL for the ubiquity (iCloud) container associated with the
     * specified container identifier, and
     * establishes access to that container.
     * @Discussion You use this method to determine the location of your app’s ubiquity container
     * directories and to configure your app’s
     * initial iCloud access. The first time you call this method for a given ubiquity container,
     * the system extends your app’s
     * sandbox to include that container. In iOS, you must call this method at least once before
     * trying to search for
     * cloud-based files in the ubiquity container. If your app accesses multiple ubiquity
     * containers, call this method once for
     * each container. In OS X, you do not need to call this method if you use NSDocument-based
     * objects, because the system then
     * calls this method automatically. You can use the URL returned by this method to build paths
     * to files and directories
     * within your app’s ubiquity container. Each app that syncs documents to the cloud must have
     * at
     * least one associated
     * ubiquity container in which to put those files. This container can be unique to the app or
     * shared by multiple apps.
     * Important: Do not call this method from your app’s main thread. Because this method might
     * take a nontrivial amount of
     * time to set up iCloud and return the requested URL, you should always call it from a
     * secondary thread. To determine if
     * iCloud is available, especially at launch time, call the ubiquityIdentityToken method
     * instead. In addition to writing to
     * its own ubiquity container, an app can write to any container directory for which it has the
     * appropriate permission. Each
     * additional ubiquity container should be listed as an additional value in the
     * com.apple.developer.ubiquity-container-identifiers entitlement array. To learn how to view
     * your development team’s unique
     * <TEAM_ID> value, read “To view the team ID�? in Tools Workflow Guide for Mac. Note: The
     * development team ID that precedes
     * each container ID string is the unique identifier associated with your development team. To
     * learn how to view your
     * development team’s unique <TEAM_ID> value, read “To view the team ID�? in Tools Workflow
     * Guide
     * for Mac.
     */
    
    public void URLForUbiquityContainerIdentifier(NSString containerID) {
        // WILL NOT BE IMPLEMENTED
    }

    /**
     * - (BOOL)isUbiquitousItemAtURL:(NSURL *)url
     *
     * @param url Specify the URL for the file or directory whose status you want to check.
     * @return Return Value YES if the item is targeted for iCloud storage or NO if it is not. This
     * method also returns NO if no item exists
     * at url.
     * @Description : Returns a Boolean indicating whether the item is targeted for storage in
     * iCloud.
     * @Discussion This method reflects only whether the item should be stored in iCloud because a
     * call was made to the
     * setUbiquitous:itemAtURL:destinationURL:error: method with a value of YES for its flag
     * parameter. This method does not
     * reflect whether the file has actually been uploaded to any iCloud servers. To determine a
     * file’s upload status, check the
     * NSURLUbiquitousItemIsUploadedKey attribute of the corresponding NSURL object.
     */
    
    public void isUbiquitousItemAtURL(NSURL url) {
        // WILL NOT BE IMPLEMENTED
    }

    /**
     * @param flag           Specify YES to move the item to iCloud or NO to remove it from iCloud
     *                       (if it is there currently).
     * @param url            Specify the URL of the item (file or directory) that you want to store
     *                       in iCloud.
     * @param destinationURL Moving a file into iCloud Specify the location in iCloud at which to
     *                       store the file or directory. This URL must
     *                       be constructed from a URL returned by the URLForUbiquityContainerIdentifier:
     *                       method, which you use to retrieve the desired
     *                       iCloud container directory. The URL you specify may contain additional
     *                       subdirectories so that you can organize your files
     *                       hierarchically in iCloud. However, you are responsible for creating
     *                       those intermediate subdirectories (using the
     *                       NSFileManager class) in your iCloud container directory. Moving a file
     *                       out of iCloud Specify the location on the local
     *                       device.
     * @param errorOut       On input, a pointer to variable for an NSError object. If an error
     *                       occurs, this pointer is set to an NSError object
     *                       containing information about the error. You may specify nil to ignore
     *                       the error information.
     * @return Return Value YES if the item’s status was updated successfully or NO if an error
     * occurred. If this method returns NO and you
     * specified a value for the errorOut parameter, this method returns an error object in the
     * provided pointer.
     * @Signature: setUbiquitous:itemAtURL:destinationURL:error:
     * @Declaration : - (BOOL)setUbiquitous:(BOOL)flag itemAtURL:(NSURL *)url destinationURL:(NSURL
     * *)destinationURL error:(NSError
     * **)errorOut
     * @Description : Sets whether the item at the specified URL should be stored in the cloud.
     * @Discussion Use this method to move a file from its current location to iCloud. For files
     * located in an app’s sandbox, this involves
     * physically removing the file from the sandbox container. (The system extends your app’s
     * sandbox privileges to give it
     * access to files it moves to iCloud.) You can also use this method to move files out of
     * iCloud
     * and back into a local
     * directory. If the your app is presenting the file’s contents to the user, it must have an
     * active file presenter object
     * configured to monitor the specified file or directory before calling this method. When you
     * specify YES for the flag
     * parameter, this method attempts to move the file or directory to the cloud and returns YES
     * if
     * it is successful. Calling
     * this method also notifies your file presenter of the new location of the file so that your
     * app can continue to operate on
     * it. Important: Avoid calling this method from your app’s main thread. This method performs a
     * coordinated write operation
     * on the specified file, which can block for a long time. Additionally, if the file presenter
     * that is monitoring the file
     * is incorrectly configured so that it receives messages on the main operation queue, calling
     * this method on the main
     * thread can cause a deadlock. Instead, use a dispatch queue to call this method from
     * background thread. After the method
     * returns, message your main thread to update the rest of your app’s data structures.
     **/
    
    public void setUbiquitousItemAtURLDestinationURLError(boolean flag, NSURL url, NSURL destinationURL, NSError[] errorOut) {
        // WILL NOT BE IMPLEMENTED
    }

    /**
     * @param url      Specify the URL for the file or directory in the cloud that you want to
     *                 download.
     * @param errorOut On input, a pointer to variable for an NSError object. If an error occurs,
     *                 this pointer is set to an NSError object
     *                 containing information about the error. You may specify nil to ignore the
     *                 error information.
     * @return Return Value YES if the download started successfully or was not necessary, otherwise
     * NO. If NO is returned and errorOut is
     * not nil, an NSError object describing the error is returned in that parameter.
     * @Signature: startDownloadingUbiquitousItemAtURL:error:
     * @Declaration : - (BOOL)startDownloadingUbiquitousItemAtURL:(NSURL *)url error:(NSError
     * **)errorOut
     * @Description : Starts downloading (if necessary) the specified item to the local system.
     * @Discussion If a cloud-based file or directory has not been downloaded yet, calling this
     * method starts the download process. If the
     * item exists locally, calling this method synchronizes the local copy with the version in the
     * cloud. For a given URL, you
     * can determine if a file is downloaded by getting the value of the
     * NSMetadataUbiquitousItemIsDownloadedKey key. You can
     * also use related keys to determine the current progress in downloading the file.
     **/
    
    public void startDownloadingUbiquitousItemAtURLError(NSURL url, NSError[] errorOut) {
        // WILL NOT BE IMPLEMENTED
    }

    /**
     * @param url      Specify the URL to a file or directory in iCloud storage.
     * @param errorOut On input, a pointer to variable for an NSError object. If an error occurs,
     *                 this pointer is set to an NSError object
     *                 containing information about the error. You may specify nil to ignore the
     *                 error information.
     * @return Return Value YES if the local item was removed successfully or NO if it was not. If
     * NO is returned and errorOut is not nil,
     * an NSError object describing the error is returned in that parameter.
     * @Signature: evictUbiquitousItemAtURL:error:
     * @Declaration : - (BOOL)evictUbiquitousItemAtURL:(NSURL *)url error:(NSError **)errorOut
     * @Description : Removes the local copy of the specified cloud-based item.
     * @Discussion Do not use a coordinated write to perform this operation. On OS X v10.7 or
     * earlier, the framework takes a coordinated
     * write in its implemntation of this method, so taking one in your app causes a deadlock. On
     * OS
     * X v10.8 and later, the
     * framework detects coordination done by your app on the same thread as the call to this
     * method, to avoid deadlocking. This
     * method does not remove the item from the cloud. It removes only the local version. You can
     * use this method to force
     * iCloud to download a new version of the file or directory from the server. To delete a file
     * permanently from the user’s
     * iCloud storage, use the regular NSFileManager routines for deleting files and directories.
     * Remember that deleting items
     * from iCloud cannot be undone. Once deleted, the item is gone forever.
     **/
    
    public boolean evictUbiquitousItemAtURLError(NSURL url, NSError[] errorOut) {
        // WILL NOT BE IMPLEMENTED
        return false;
    }

    /**
     * @param url     Specify the URL of the item in the cloud that you want to share. The URL must
     *                be prefixed with the base URL returned from
     *                the URLForUbiquityContainerIdentifier: method that corresponds to the item’s
     *                location.
     * @param outDate On input, a pointer to a variable for a date object. On output, this
     *                parameter
     *                contains the date after which the item
     *                is no longer available at the returned URL. You may specify nil for this
     *                parameter if you are not interested in the date.
     * @param error   On input, a pointer to variable for an NSError object. If an error occurs,
     *                this pointer is set to an NSError object
     *                containing information about the error. You may specify nil for this
     *                parameter
     *                if you do not want the error information.
     * @return Return Value A URL with which users can download a copy of the item at url. Returns
     * nil if the URL could not be created for
     * any reason.
     * @Signature: URLForPublishingUbiquitousItemAtURL:expirationDate:error:
     * @Declaration : - (NSURL *)URLForPublishingUbiquitousItemAtURL:(NSURL *)url
     * expirationDate:(NSDate **)outDate error:(NSError **)error
     * @Description : Returns a URL that can be emailed to users to allow them to download a copy
     * of
     * a cloud-based item.
     * @Discussion This method creates a snapshot of the specified file and places that copy in a
     * temporary iCloud location where it can be
     * accessed by other users using the returned URL. The snapshot reflects the contents of the
     * file at the time the URL was
     * generated and is not updated when subsequent changes are made to the original file in the
     * user’s iCloud storage. The
     * snapshot file remains available at the specified URL until the date specified in the outDate
     * parameter, after which it is
     * automatically deleted. Explicitly deleting the item by calling the removeItemAtURL:error: or
     * removeItemAtPath:error:
     * method also deletes all old versions of the item, invalidating URLs to those versions
     * returned by this method. Your app
     * must have access to the network for this call to succeed.
     **/
    
    public NSURL URLForPublishingUbiquitousItemAtURLExpirationDateError(NSURL url, NSDate outDate, NSError[] error) {
        // WILL NOT BE IMPLEMENTED
        return null;
    }

    // Creating Symbolic and Hard Links

    /**
     * @param url     The file URL at which to create the new symbolic link. The last path
     *                component
     *                of the URL issued as the name of the link.
     * @param destURL The file URL that contains the item to be pointed to by the link. In other
     *                words, this is the destination of the link.
     * @param error   On input, a pointer to an error object. If an error occurs, this pointer is
     *                set to an actual error object containing the
     *                error information. You may specify nil for this parameter if you do not want
     *                the error information.
     * @return Return Value YES if the symbolic link was created or NO if an error occurred. This
     * method also returns NO if a file,
     * directory, or link already exists at url.
     * @Signature: createSymbolicLinkAtURL:withDestinationURL:error:
     * @Declaration : - (BOOL)createSymbolicLinkAtURL:(NSURL *)url withDestinationURL:(NSURL
     * *)destURL error:(NSError **)error
     * @Description : Creates a symbolic link at the specified URL that points to an item at the
     * given URL.
     * @Discussion This method does not traverse symbolic links contained in destURL, making it
     * possible to create symbolic links to
     * locations that do not yet exist. Also, if the final path component in url is a symbolic
     * link,
     * that link is not followed.
     **/
    
    public boolean createSymbolicLinkAtURLWithDestinationURLError(NSURL url, NSURL destURL, NSError[] error) {
        return createSymbolicLinkAtPathWithDestinationPathError(url.path(), destURL.path(), error);
    }

    /**
     * @param path     The path at which to create the new symbolic link. The last path component
     *                 is
     *                 used as the name of the link.
     * @param destPath The path that contains the item to be pointed to by the link. In other
     *                 words,
     *                 this is the destination of the link.
     * @param error    If an error occurs, upon return contains an NSError object that describes
     *                 the
     *                 problem. Pass NULL if you do not want
     *                 error information.
     * @return Return Value YES if the symbolic link was created or NO if an error occurred. This
     * method also returns NO if a file,
     * directory, or link already exists at path.
     * @Signature: createSymbolicLinkAtPath:withDestinationPath:error:
     * @Declaration : - (BOOL)createSymbolicLinkAtPath:(NSString *)path
     * withDestinationPath:(NSString *)destPath error:(NSError **)error
     * @Description : Creates a symbolic link that points to the specified destination.
     * @Discussion This method does not traverse symbolic links contained in destPath, making it
     * possible to create symbolic links to
     * locations that do not yet exist. Also, if the final path component in path is a symbolic
     * link, that link is not followed.
     **/
    
    public boolean createSymbolicLinkAtPathWithDestinationPathError(NSString path, NSString destPath, NSError[] error) {
        // TODO keep in mind that Android SD card use vfat filesystem (which can't do symlinks)
        return false;
    }

    /**
     * @param srcURL The file URL that identifies the source of the link. The URL in this parameter
     *               must not be a file reference URL; it
     *               must specify the actual path to the item. The value in this parameter must not
     *               be nil.
     * @param dstURL The file URL that specifies where you want to create the hard link. The URL in
     *               this parameter must not be a file
     *               reference URL; it must specify the actual path to the item. The value in this
     *               parameter must not be nil.
     * @param error  On input, a pointer to an error object. If an error occurs, this pointer is
     *               set
     *               to an actual error object containing the
     *               error information. You may specify nil for this parameter if you do not want
     *               the error information.
     * @return Return Value YES if the hard link was created or NO if an error occurred. This method
     * also returns NO if a file, directory,
     * or link already exists at dstURL.
     * @Signature: linkItemAtURL:toURL:error:
     * @Declaration : - (BOOL)linkItemAtURL:(NSURL *)srcURL toURL:(NSURL *)dstURL error:(NSError
     * **)error
     * @Description : Creates a hard link between the items at the specified URLs.
     * @Discussion Use this method to create hard links between files in the current file system.
     * If
     * srcURL is a directory, this method
     * creates a new directory at dstURL and then creates hard links for the items in that
     * directory. If srcURL is (or contains)
     * a symbolic link, the symbolic link is copied and not converted to a hard link at dstURL.
     * Prior to linking each item, the
     * file manager asks its delegate if it should actually create the link. It does this by
     * calling
     * the
     * fileManager:shouldLinkItemAtURL:toURL: method; if that method is not implemented it calls
     * the
     * fileManager:shouldLinkItemAtPath:toPath: method instead. If the delegate method returns YES,
     * or if the delegate does not
     * implement the appropriate methods, the file manager creates the hard link. If there is an
     * error moving one out of several
     * items, the file manager may also call the delegate’s fileManager:shouldProceedAfterError:linkingItemAtURL:toURL:
     * or
     * fileManager:shouldProceedAfterError:linkingItemAtPath:toPath: method to determine how to
     * proceed.
     **/
    
    public boolean linkItemAtURLToURLError(NSURL srcURL, NSURL dstURL, NSError[] error) {
        return linkItemAtPathToPathError(srcURL.path(), dstURL.path(), error);
    }

    /**
     * @param srcPath The path that specifies the item you wish to link to. The value in this
     *                parameter must not be nil.
     * @param dstPath The path that identifies the location where the link will be created. The
     *                value in this parameter must not be nil.
     * @param error   On input, a pointer to an error object. If an error occurs, this pointer is
     *                set to an actual error object containing the
     *                error information. You may specify nil for this parameter if you do not want
     *                the error information.
     * @return Return Value YES if the hard link was created or NO if an error occurred. This method
     * also returns NO if a file, directory,
     * or link already exists at dstPath.
     * @Signature: linkItemAtPath:toPath:error:
     * @Declaration : - (BOOL)linkItemAtPath:(NSString *)srcPath toPath:(NSString *)dstPath
     * error:(NSError **)error
     * @Description : Creates a hard link between the items at the specified paths.
     * @Discussion Use this method to create hard links between files in the current file system.
     * If
     * srcPath is a directory, this method
     * creates a new directory at dstPath and then creates hard links for the items in that
     * directory. If srcPath is (or
     * contains) a symbolic link, the symbolic link is copied to the new location and not converted
     * to a hard link. Prior to
     * linking each item, the file manager asks its delegate if it should actually create the link.
     * It does this by calling the
     * fileManager:shouldLinkItemAtURL:toURL: method; if that method is not implemented it calls
     * the
     * fileManager:shouldLinkItemAtPath:toPath: method instead. If the delegate method returns YES,
     * or if the delegate does not
     * implement the appropriate methods, the file manager creates the hard link. If there is an
     * error moving one out of several
     * items, the file manager may also call the delegate’s fileManager:shouldProceedAfterError:linkingItemAtURL:toURL:
     * or
     * fileManager:shouldProceedAfterError:linkingItemAtPath:toPath: method to determine how to
     * proceed.
     **/
    
    public boolean linkItemAtPathToPathError(NSString srcPath, NSString dstPath, NSError[] error) {
        return false;
        // TODO
    }

    /**
     * @param path  The path of a file or directory.
     * @param error If an error occurs, upon return contains an NSError object that describes the
     *              problem. Pass NULL if you do not want
     *              error information.
     * @return Return Value An NSString object containing the path of the directory or file to which
     * the symbolic link path refers, or nil
     * upon failure. If the symbolic link is specified as a relative path, that relative path is
     * returned.
     * @Signature: destinationOfSymbolicLinkAtPath:error:
     * @Declaration : - (NSString *)destinationOfSymbolicLinkAtPath:(NSString *)path error:(NSError
     * **)error
     * @Description : Returns the path of the item pointed to by a symbolic link.
     **/
    
    public NSString destinationOfSymbolicLinkAtPathError(NSString path, NSError[] error) {
        return null;
    }

    // Determining Access to Files

    /**
     * @param path The path of the file or directory. If path begins with a tilde (~), it must
     *             first
     *             be expanded with
     *             stringByExpandingTildeInPath, otherwise, this method returns NO.
     * @return Return Value YES if a file at the specified path exists or NO if the file does not
     * exist or its existence could not be
     * determined.
     * @Signature: fileExistsAtPath:
     * @Declaration : - (BOOL)fileExistsAtPath:(NSString *)path
     * @Description : Returns a Boolean value that indicates whether a file or directory exists at
     * a
     * specified path.
     * @Discussion If the file at path is inaccessible to your app, perhaps because one or more
     * parent directories are inaccessible, this
     * method returns NO. If the final element in path specifies a symbolic link, this method
     * traverses the link and returns YES
     * or NO based on the existence of the file at the link destination. Note: Attempting to
     * predicate behavior based on the
     * current state of the file system or a particular file on the file system is not recommended.
     * Doing so can cause odd
     * behavior or race conditions. It's far better to attempt an operation (such as loading a file
     * or creating a directory),
     * check for errors, and handle those errors gracefully than it is to try to figure out ahead
     * of
     * time whether the operation
     * will succeed. For more information on file system race conditions, see “Race Conditions and
     * Secure File Operations�? in
     * Secure Coding Guide.
     **/
    
    public boolean fileExistsAtPath(NSString path) {
        if (path.getWrappedString().contains("asset")) {
            NSString compentExt = path.lastPathComponent();
            try {
                GenericMainContext.sharedContext.getAssets().open(compentExt.getWrappedString());
                return true;
            } catch (IOException e) {
                LOGGER.info(String.valueOf(e));
                return false;
            }
        } else {
            NSString compentExt = path.lastPathComponent();
            Object obj = SerializationUtil.retrieveObject(compentExt.getWrappedString());
            return obj == null ? false : true;
        }
    }

    /**
     * @param path        The path of a file or directory. If path begins with a tilde (~), it must
     *                    first be expanded with
     *                    stringByExpandingTildeInPath, or this method will return NO.
     * @param isDirectory Upon return, contains YES if path is a directory or if the final path
     *                    element is a symbolic link that points to a
     *                    directory, otherwise contains NO. If path doesn’t exist, this value is
     *                    undefined upon return. Pass NULL if you do not need
     *                    this information.
     * @return Return Value YES if a file at the specified path exists or NO if the file’s does not
     * exist or its existence could not be
     * determined.
     * @Signature: fileExistsAtPath:isDirectory:
     * @Declaration : - (BOOL)fileExistsAtPath:(NSString *)path isDirectory:(BOOL *)isDirectory
     * @Description : Returns a Boolean value that indicates whether a file or directory exists at
     * a
     * specified path.
     * @Discussion If the file at path is inaccessible to your app, perhaps because one or more
     * parent directories are inaccessible, this
     * method returns NO. If the final element in path specifies a symbolic link, this method
     * traverses the link and returns YES
     * or NO based on the existence of the file at the link destination. If you need to further
     * determine if path is a package,
     * use the isFilePackageAtPath: method of NSWorkspace.
     **/
    
    public boolean fileExistsAtPathIsDirectory(NSString path, boolean[] isDirectory) {
        File file = new File(path.getWrappedString());
        isDirectory[0] = file.isDirectory();
        return file.exists();
    }

    /**
     * @param path A file path.
     * @return Return Value YES if the current process has read privileges for the file at path;
     * otherwise NO if the process does not have
     * read privileges or the existence of the file could not be determined.
     * @Signature: isReadableFileAtPath:
     * @Declaration : - (BOOL)isReadableFileAtPath:(NSString *)path
     * @Description : Returns a Boolean value that indicates whether the invoking object appears
     * able to read a specified file.
     * @Discussion If the file at path is inaccessible to your app, perhaps because it does not
     * have
     * search privileges for one or more
     * parent directories, this method returns NO. This method traverses symbolic links in the
     * path.
     * This method also uses the
     * real user ID and group ID, as opposed to the effective user and group IDs, to determine if
     * the file is readable.
     * Note: Attempting to predicate behavior based on the current state of the file system or a
     * particular file on the file
     * system is not recommended. Doing so can cause odd behavior or race conditions. It's far
     * better to attempt an operation
     * (such as loading a file or creating a directory), check for errors, and handle those errors
     * gracefully than it is to try
     * to figure out ahead of time whether the operation will succeed. For more information on file
     * system race conditions, see
     * “Race Conditions and Secure File Operations�? in Secure Coding Guide.
     **/
    
    public boolean isReadableFileAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        return file.canRead();
    }

    /**
     * @param path A file path.
     * @return Return Value YES if the current process has write privileges for the file at path;
     * otherwise NO if the process does not have
     * write privileges or the existence of the file could not be determined.
     * @Signature: isWritableFileAtPath:
     * @Declaration : - (BOOL)isWritableFileAtPath:(NSString *)path
     * @Description : Returns a Boolean value that indicates whether the invoking object appears
     * able to write to a specified file.
     * @Discussion If the file at path is inaccessible to your app, perhaps because it does not
     * have
     * search privileges for one or more
     * parent directories, this method returns NO. This method traverses symbolic links in the
     * path.
     * This method also uses the
     * real user ID and group ID, as opposed to the effective user and group IDs, to determine if
     * the file is writable.
     * Note: Attempting to predicate behavior based on the current state of the file system or a
     * particular file on the file
     * system is not recommended. Doing so can cause odd behavior or race conditions. It's far
     * better to attempt an operation
     * (such as loading a file or creating a directory), check for errors, and handle those errors
     * gracefully than it is to try
     * to figure out ahead of time whether the operation will succeed. For more information on file
     * system race conditions, see
     * “Race Conditions and Secure File Operations�? in Secure Coding Guide.
     **/
    
    public boolean isWritableFileAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        return file.canWrite();
    }

    /**
     * @param path A file path.
     * @return Return Value YES if the current process has execute privileges for the file at path;
     * otherwise NO if the process does not
     * have execute privileges or the existence of the file could not be determined.
     * @Signature: isExecutableFileAtPath:
     * @Declaration : - (BOOL)isExecutableFileAtPath:(NSString *)path
     * @Description : Returns a Boolean value that indicates whether the operating system appears
     * able to execute a specified file.
     * @Discussion If the file at path is inaccessible to your app, perhaps because it does not
     * have
     * search privileges for one or more
     * parent directories, this method returns NO. This method traverses symbolic links in the
     * path.
     * This method also uses the
     * real user ID and group ID, as opposed to the effective user and group IDs, to determine if
     * the file is executable.
     * Note: Attempting to predicate behavior based on the current state of the file system or a
     * particular file on the file
     * system is not recommended. Doing so can cause odd behavior or race conditions. It's far
     * better to attempt an operation
     * (such as loading a file or creating a directory), check for errors, and handle those errors
     * gracefully than it is to try
     * to figure out ahead of time whether the operation will succeed. For more information on file
     * system race conditions, see
     * “Race Conditions and Secure File Operations�? in Secure Coding Guide.
     **/
    
    public boolean isExecutableFileAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        return file.canExecute();
    }

    /**
     * @param path A file path.
     * @return Return Value YES if the current process has delete privileges for the file at path;
     * otherwise NO if the process does not have
     * delete privileges or the existence of the file could not be determined.
     * @Signature: isDeletableFileAtPath:
     * @Declaration : - (BOOL)isDeletableFileAtPath:(NSString *)path
     * @Description : Returns a Boolean value that indicates whether the invoking object appears
     * able to delete a specified file.
     * @Discussion For a directory or file to be deletable, the current process must either be able
     * to write to the parent directory of path
     * or it must have the same owner as the item at path. If path is a directory, every item
     * contained in path must be
     * deletable by the current process. If the file at path is inaccessible to your app, perhaps
     * because it does not have
     * search privileges for one or more parent directories, this method returns NO. If the item at
     * path is a symbolic link, it
     * is not traversed. Note: Attempting to predicate behavior based on the current state of the
     * file system or a particular
     * file on the file system is not recommended. Doing so can cause odd behavior or race
     * conditions. It's far better to
     * attempt an operation (such as loading a file or creating a directory), check for errors, and
     * handle those errors
     * gracefully than it is to try to figure out ahead of time whether the operation will succeed.
     * For more information on file
     * system race conditions, see “Race Conditions and Secure File Operations�? in Secure Coding
     * Guide.
     **/
    
    public boolean isDeletableFileAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        return isWritableFileAtPath(new NSString(file.getParent()));
    }

    // Getting and Setting Attributes

    /**
     * @param path A pathname.
     * @return Return Value An array of NSString objects representing the user-visible (for the
     * Finder, Open and Save panels, and so on)
     * components of path. Returns nil if path does not exist.
     * @Signature: componentsToDisplayForPath:
     * @Declaration : - (NSArray *)componentsToDisplayForPath:(NSString *)path
     * @Description : Returns an array of strings representing the user-visible components of a
     * given path.
     * @Discussion These components cannot be used for path operations and are only suitable for
     * display to the user.
     **/
    
    public NSArray<NSString> componentsToDisplayForPath(NSString path) {
        NSArray<NSString> nsArray = new NSArray<NSString>();
        File file = new File(path.getWrappedString());
        File[] mFileList = file.listFiles();
        for (File mFile : mFileList) {
            if (!mFile.isHidden() && !mFile.isAbsolute())
                nsArray.getWrappedList().add(new NSString(mFile.getAbsolutePath()));
        }
        return nsArray;
    }

    /**
     * @param path The path of a file or directory.
     * @return Return Value The name of the file or directory at path in a localized form
     * appropriate for presentation to the user. If there
     * is no file or directory at path, or if an error occurs, returns path as is.
     * @Signature: displayNameAtPath:
     * @Declaration : - (NSString *)displayNameAtPath:(NSString *)path
     * @Description : Returns the display name of the file or directory at a specified path.
     * @Discussion Display names are user-friendly names for files. They are typically used to
     * localize standard file and directory names
     * according to the user’s language settings. They may also reflect other modifications, such
     * as
     * the removal of filename
     * extensions. Such modifications are used only when displaying the file or directory to the
     * user and do not reflect the
     * actual path to the item in the file system. For example, if the current user’s preferred
     * language is French, the
     * following code fragment logs the name Bibliothèque and not the name Library, which is the
     * actual name of the directory.
     * NSArray *paths = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask,
     * YES); if ([paths count] > 0) {
     * NSString *documentsDirectory = [paths objectAtIndex:0]; NSFileManager *fileManager =
     * [[NSFileManager alloc] init];
     * NSString *displayNameAtPath = [fileManager displayNameAtPath:documentsDirectory];
     * NSLog(@"%@", displayNameAtPath); }
     **/
    
    public NSString displayNameAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        if (file.exists())
            return new NSString(file.getName());
        return null;
    }

    /**
     * @param path  The path of a file or directory.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value An NSDictionary object that describes the attributes (file, directory,
     * symlink, and so on) of the file specified
     * by path, or nil if an error occurred. The keys in the dictionary are described in “File
     * Attribute Keys�?.
     * @Signature: attributesOfItemAtPath:error:
     * @Declaration : - (NSDictionary *)attributesOfItemAtPath:(NSString *)path error:(NSError
     * **)error
     * @Description : Returns the attributes of the item at a given path.
     **/
    
    public NSDictionary attributesOfItemAtPathError(NSString path, NSError[] error) {
        return AndroidFileUtils.getNSDictionaryFromFileOrDirAttributes(path);
    }

    /**
     * @param path  Any pathname within the mounted file system.
     * @param error On input, a pointer to an error object. If an error occurs, this pointer is set
     *              to an actual error object containing the
     *              error information. You may specify nil for this parameter if you do not want
     *              the
     *              error information.
     * @return Return Value An NSDictionary object that describes the attributes of the mounted file
     * system on which path resides. See
     * “File-System Attribute Keys�? for a description of the keys available in the dictionary.
     * @Signature: attributesOfFileSystemForPath:error:
     * @Declaration : - (NSDictionary *)attributesOfFileSystemForPath:(NSString *)path
     * error:(NSError **)error
     * @Description : Returns a dictionary that describes the attributes of the mounted file system
     * on which a given path resides.
     * @Discussion This method does not traverse a terminal symbolic link.
     **/
    
    public NSDictionary attributesOfFileSystemForPathError(NSString path, NSError[] error) {
        return AndroidFileUtils.getNSDictionaryFromFileOrDirAttributes(path);
    }

    /**
     * @param attributes A dictionary containing as keys the attributes to set for path and as
     *                   values the corresponding value for the
     *                   attribute. You can set the following attributes: NSFileBusy,
     *                   NSFileCreationDate, NSFileExtensionHidden,
     *                   NSFileGroupOwnerAccountID, NSFileGroupOwnerAccountName,
     *                   NSFileHFSCreatorCode, NSFileHFSTypeCode, NSFileImmutable,
     *                   NSFileModificationDate, NSFileOwnerAccountID, NSFileOwnerAccountName,
     *                   NSFilePosixPermissions. You can change single
     *                   attributes or any combination of attributes; you need not specify keys for
     *                   all attributes.
     * @param path       The path of a file or directory.
     * @param error      On input, a pointer to an error object. If an error occurs, this pointer
     *                   is
     *                   set to an actual error object containing the
     *                   error information. You may specify nil for this parameter if you do not
     *                   want the error information.
     * @return Return Value YES if all changes succeed. If any change fails, returns NO, but it is
     * undefined whether any changes actually
     * occurred.
     * @Signature: setAttributes:ofItemAtPath:error:
     * @Declaration : - (BOOL)setAttributes:(NSDictionary *)attributes ofItemAtPath:(NSString
     * *)path
     * error:(NSError **)error
     * @Description : Sets the attributes of the specified file or directory.
     * @Discussion As in the POSIX standard, the app either must own the file or directory or must
     * be running as superuser for attribute
     * changes to take effect. The method attempts to make all changes specified in attributes and
     * ignores any rejection of an
     * attempted modification. If the last component of the path is a symbolic link it is
     * traversed.
     * The NSFilePosixPermissions
     * value must be initialized with the code representing the POSIX file-permissions bit pattern.
     * NSFileHFSCreatorCode and
     * NSFileHFSTypeCode will only be heeded when path specifies a file.
     **/
    
    public boolean setAttributesOfItemAtPathError(NSDictionary attributes, NSString path, NSError[] error) {
        return AndroidFileUtils.setFileOrDirAttributes(attributes, path);
    }

    // Getting and Comparing File Contents

    /**
     * @param path The path of the file whose contents you want.
     * @return Return Value An NSData object with the contents of the file. If path specifies a
     * directory, or if some other error occurs,
     * this method returns nil.
     * @Signature: contentsAtPath:
     * @Declaration : - (NSData *)contentsAtPath:(NSString *)path
     * @Description : Returns the contents of the file at the specified path.
     **/
    
    public NSData contentsAtPath(NSString path) {
        File file = new File(path.getWrappedString());
        if (!file.isDirectory())
            return NSData.dataWithContentsOfFile(NSData.class, path);
        return null;
    }

    /**
     * @param path1 The path of a file or directory to compare with the contents of path2.
     * @param path2 The path of a file or directory to compare with the contents of path1.
     * @return Return Value YES if file or directory specified in path1 has the same contents as
     * that specified in path2, otherwise NO.
     * @Signature: contentsEqualAtPath:andPath:
     * @Declaration : - (BOOL)contentsEqualAtPath:(NSString *)path1 andPath:(NSString *)path2
     * @Description : Returns a Boolean value that indicates whether the files or directories in
     * specified paths have the same contents.
     * @Discussion If path1 and path2 are directories, the contents are the list of files and
     * subdirectories each contains—contents of
     * subdirectories are also compared. For files, this method checks to see if they’re the same
     * file, then compares their
     * size, and finally compares their contents. This method does not traverse symbolic links, but
     * compares the links
     * themselves.
     **/
    
    public boolean contentsEqualAtPathAndPath(NSString path1, NSString path2) {
        File file1 = new File(path1.getWrappedString());
        File file2 = new File(path2.getWrappedString());
        try {
            return AndroidFileUtils.contentEquals(file1, file2);
        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
            return false;
        }

    }

    // Converting File Paths to Strings

    /**
     * @param path A string object containing a path to a file. This parameter must not be nil or
     *             contain the empty string.
     * @return Return Value A C-string representation of path that properly encodes Unicode strings
     * for use by the file system.
     * @Signature: fileSystemRepresentationWithPath:
     * @Declaration : - (const char *)fileSystemRepresentationWithPath:(NSString *)path
     * @Description : Returns a C-string representation of a given path that properly encodes
     * Unicode strings for use by the file system.
     * @Discussion Use this method if your code calls system routines that expect C-string path
     * arguments. If you use the C string beyond
     * the scope of the current autorelease pool, you must copy it. This method raises an exception
     * if path is nil or contains
     * the empty string. This method also throws an exception if the conversion of the string
     * fails.
     **/
    
    public char[] fileSystemRepresentationWithPath(NSString path) {
        File file = new File(path.getWrappedString());
        return file.getAbsolutePath().toCharArray();
    }

    /**
     * @param string A C string representation of a pathname.
     * @param len    The number of characters in string.
     * @return Return Value An NSString object converted from the C-string representation string
     * with length len of a pathname in the
     * current file system.
     * @Signature: stringWithFileSystemRepresentation:length:
     * @Declaration : - (NSString *)stringWithFileSystemRepresentation:(const char *)string
     * length:(NSUInteger)len
     * @Description : Returns an NSString object whose contents are derived from the specified
     * C-string path.
     * @Discussion Use this method if your code receives paths as C strings from system routines.
     **/
    
    public NSString stringWithFileSystemRepresentationLength(char[] string, int len) {
        String filePath = Arrays.toString(string);
        NSString mString = new NSString(filePath);
        return mString;
    }

    // Managing the Delegate

    /**
     * @param delegate The delegate for the receiver. The delegate must implement the
     *                 NSFileManagerDelegate protocol.
     * @Signature: setDelegate:
     * @Declaration : - (void)setDelegate:(id)delegate
     * @Description : Sets the delegate for the receiver.
     * @Discussion It is recommended that you assign a delegate only to file manager objects that
     * you create explicitly using the alloc/init
     * convention.
     **/
    
    public void setDelegate(NSFileManagerDelegate delegate) {
        mDelegate = delegate;
    }

    /**
     * @return Return Value The delegate for the receiver.
     * @Signature: delegate
     * @Declaration : - (id)delegate
     * @Description : Returns the delegate for the receiver.
     **/
    
    public NSFileManagerDelegate delegate() {
        return mDelegate;
    }

    public NSFileManagerDelegate getDelegate() {
        return delegate();
    }
    // Managing the Current Directory

    /**
     * @param path The path of the directory to which to change.
     * @return Return Value YES if successful, otherwise NO.
     * @Signature: changeCurrentDirectoryPath:
     * @Declaration : - (BOOL)changeCurrentDirectoryPath:(NSString *)path
     * @Description : Changes the path of the current working directory to the specified path.
     * @Discussion All relative pathnames refer implicitly to the current working directory.
     * Changing the current working directory affects
     * only paths created in the current process.
     **/
    
    public boolean changeCurrentDirectoryPath(NSString path) {
        // NOT USED IN JAVA AND ANDROID
        return false;
    }

    /**
     * @return Return Value The path of the program’s current directory. If the program’s current
     * working directory isn’t accessible,
     * returns nil.
     * @Signature: currentDirectoryPath
     * @Declaration : - (NSString *)currentDirectoryPath
     * @Description : Returns the path of the program’s current directory.
     * @Discussion The string returned by this method is initialized to the current working
     * directory; you can change the working directory
     * by invoking changeCurrentDirectoryPath:. Relative pathnames refer implicitly to the current
     * directory. For example, if
     * the current directory is /tmp, and the relative pathname reports/info.txt is specified, the
     * resulting full pathname is
     * /tmp/reports/info.txt.
     **/
    
    public NSString currentDirectoryPath() {

        String appPath;
        try {
            appPath = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
            return new NSString(appPath);
        } catch (NameNotFoundException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    public NSString getCurrentDirectoryPath() {
        return currentDirectoryPath();
    }


    // Deprecated Methods

    /**
     * @param attributes A dictionary containing as keys the attributes to set for path and as
     *                   values the corresponding value for the
     *                   attribute. You can set following: NSFileBusy, NSFileCreationDate,
     *                   NSFileExtensionHidden, NSFileGroupOwnerAccountID,
     *                   NSFileGroupOwnerAccountName, NSFileHFSCreatorCode, NSFileHFSTypeCode,
     *                   NSFileImmutable, NSFileModificationDate,
     *                   NSFileOwnerAccountID, NSFileOwnerAccountName, NSFilePosixPermissions. You
     *                   can change single attributes or any combination
     *                   of attributes; you need not specify keys for all attributes. For the
     *                   NSFilePosixPermissions value, specify a file mode
     *                   from the OR’d permission bit masks defined in sys/stat.h. See the man page
     *                   for the chmod function (man 2 chmod) for an
     *                   explanation.
     * @param path       A path to a file or directory.
     * @return Return Value YES if all changes succeed. If any change fails, returns NO, but it is
     * undefined whether any changes actually
     * occurred.
     * @Signature: changeFileAttributes:atPath:
     * @Declaration : - (BOOL)changeFileAttributes:(NSDictionary *)attributes atPath:(NSString
     * *)path
     * @Description : Changes the attributes of a given file or directory. (Deprecated in iOS 2.0.
     * Use setAttributes:ofItemAtPath:error:
     * instead.)
     * @Discussion As in the POSIX standard, the app either must own the file or directory or must
     * be running as superuser for attribute
     * changes to take effect. The method attempts to make all changes specified in attributes and
     * ignores any rejection of an
     * attempted modification. The NSFilePosixPermissions value must be initialized with the code
     * representing the POSIX
     * file-permissions bit pattern. NSFileHFSCreatorCode and NSFileHFSTypeCode will only be heeded
     * when path specifies a file.
     **/
    
    
    public boolean changeFileAttributesAtPath(NSDictionary attributes, NSString path) {
        return setAttributesOfItemAtPathError(attributes, path, null);
    }

    /**
     * @param path       The path at which to create the new directory. The directory to be created
     *                   must not yet exist, but its parent directory
     *                   must exist.
     * @param attributes The file attributes for the new directory. The attributes you can set are
     *                   owner and group numbers, file
     *                   permissions, and modification date. If you specify nil for attributes,
     *                   default values for these attributes are set
     *                   (particularly write access for the creator and read access for others).
     *                   The
     *                   “Constants�? section lists the global constants
     *                   used as keys in the attributes dictionary. Some of the keys, such as
     *                   NSFileHFSCreatorCode and NSFileHFSTypeCode, do not
     *                   apply to directories.
     * @return Return Value YES if the operation was successful, otherwise NO.
     * @Signature: createDirectoryAtPath:attributes:
     * @Declaration : - (BOOL)createDirectoryAtPath:(NSString *)path attributes:(NSDictionary
     * *)attributes
     * @Description : Creates a directory (without contents) at a given path with given attributes.
     * (Deprecated in iOS 2.0. Use
     * createDirectoryAtURL:withIntermediateDirectories:attributes:error: instead.)
     **/
    
    
    public boolean createDirectoryAtPathAttributes(NSString path, NSDictionary attributes) {
        NSURL url = new NSURL(path.getWrappedString());
        return createDirectoryAtURLWithIntermediateDirectoriesAttributesError(url, true, attributes, null);
    }

    /**
     * @param path      The path for a symbolic link.
     * @param otherPath The path to which path should refer.
     * @return Return Value YES if the operation is successful, otherwise NO. Returns NO if a file,
     * directory, or symbolic link identical to
     * path already exists.
     * @Signature: createSymbolicLinkAtPath:pathContent:
     * @Declaration : - (BOOL)createSymbolicLinkAtPath:(NSString *)path pathContent:(NSString
     * *)otherPath
     * @Description : Creates a symbolic link identified by a given path that refers to a given
     * location. (Deprecated in iOS 2.0. Use
     * createSymbolicLinkAtURL:withDestinationURL:error: instead.)
     * @Discussion Creates a symbolic link identified by path that refers to the location otherPath
     * in the file system.
     **/
    
    
    public boolean createSymbolicLinkAtPathPathContent(NSString path, NSString otherPath) {
        NSURL url = new NSURL(path.getWrappedString());
        NSURL destURL = new NSURL(otherPath.getWrappedString());
        return createSymbolicLinkAtURLWithDestinationURLError(url, destURL, null);
    }

    /**
     * @param path A path to a directory.
     * @return Return Value An array of NSString objects identifying the directories and files
     * (including symbolic links) contained in path.
     * Returns an empty array if the directory exists but has no contents. Returns nil if the
     * directory specified at path does not
     * exist or there is some other error accessing it.
     * @Signature: directoryContentsAtPath:
     * @Declaration : - (NSArray *)directoryContentsAtPath:(NSString *)path
     * @Description : Returns the directories and files (including symbolic links) contained in a
     * given directory. (Deprecated in iOS 2.0.
     * Use contentsOfDirectoryAtPath:error: instead.)
     * @Discussion The search is shallow, and therefore does not return the contents of any
     * subdirectories and does not traverse symbolic
     * links in the specified directory. This returned array does not contain strings for the
     * current directory (“.�?), parent
     * directory (“..�?), or resource forks (begin with “._�?).
     **/
    
    
    public NSArray directoryContentsAtPath(NSString path) {
        return contentsOfDirectoryAtPathError(path, null);
    }

    /**
     * @param path A file path.
     * @param flag If path is not a symbolic link, this parameter has no effect. If path is a
     *             symbolic link, then: If YES the attributes of
     *             the linked-to file are returned, or if the link points to a nonexistent file the
     *             method returns nil. If NO, the attributes
     *             of the symbolic link are returned.
     * @return Return Value An NSDictionary object that describes the POSIX attributes of the file
     * specified at path. The keys in the
     * dictionary are described in “File Attribute Keys�?. If there is no item at path, returns nil.
     * @Signature: fileAttributesAtPath:traverseLink:
     * @Declaration : - (NSDictionary *)fileAttributesAtPath:(NSString *)path
     * traverseLink:(BOOL)flag
     * @Description : Returns a dictionary that describes the POSIX attributes of the file
     * specified
     * at a given. (Deprecated in iOS 2.0. Use
     * attributesOfItemAtPath:error: instead.)
     **/
    
    
    public NSDictionary fileAttributesAtPathTraverseLink(NSString path, boolean flag) {
        return attributesOfItemAtPathError(path, null);
    }

    /**
     * @param path Any pathname within the mounted file system.
     * @return Return Value An NSDictionary object that describes the attributes of the mounted file
     * system on which path resides. See
     * “File-System Attribute Keys�? for a description of the keys available in the dictionary.
     * @Signature: fileSystemAttributesAtPath:
     * @Declaration : - (NSDictionary *)fileSystemAttributesAtPath:(NSString *)path
     * @Description : Returns a dictionary that describes the attributes of the mounted file system
     * on which a given path resides.
     * (Deprecated in iOS 2.0. Use attributesOfFileSystemForPath:error: instead.)
     **/
    
    
    public NSDictionary fileSystemAttributesAtPath(NSString path) {
        return attributesOfFileSystemForPathError(path, null);
    }

    /**
     * @param path The path of a symbolic link.
     * @return Return Value The path of the directory or file to which the symbolic link path
     * refers, or nil upon failure. If the symbolic
     * link is specified as a relative path, that relative path is returned.
     * @Signature: pathContentOfSymbolicLinkAtPath:
     * @Declaration : - (NSString *)pathContentOfSymbolicLinkAtPath:(NSString *)path
     * @Description : Returns the path of the directory or file that a symbolic link at a given
     * path
     * refers to. (Deprecated in iOS 2.0. Use
     * destinationOfSymbolicLinkAtPath:error: instead.)
     **/
    
    
    public NSString pathContentOfSymbolicLinkAtPath(NSString path) {
        return destinationOfSymbolicLinkAtPathError(path, null);

    }

    /**
     * @param manager   The file manager that sent this message.
     * @param errorInfo A dictionary that contains two or three pieces of information (all NSString
     *                  objects) related to the error: Key Value @"Path"
     *                  The path related to the error (usually the source path) @"Error" A
     *                  description of the error @"ToPath" The destination path
     *                  (not all errors)
     * @return Return Value YES if the operation (which is often continuous within a loop) should
     * proceed, otherwise NO.
     * @Signature: fileManager:shouldProceedAfterError:
     * @Declaration : - (BOOL)fileManager:(NSFileManager *)manager shouldProceedAfterError:(NSDictionary
     * *)errorInfo
     * @Description : An NSFileManager object sends this message to its handler for each error it
     * encounters when copying, moving, removing,
     * or linking files or directories. (Deprecated in iOS 2.0. See delegate methods for copy,
     * move,
     * remove, and link methods.)
     * @Discussion An NSFileManager object, manager, sends this message for each error it
     * encounters
     * when copying, moving, removing, or
     * linking files or directories. The return value is passed back to the invoker of
     * copyPath:toPath:handler:,
     * movePath:toPath:handler:, removeFileAtPath:handler:, or linkPath:toPath:handler:. If an
     * error
     * occurs and your handler has
     * not implemented this method, the invoking method automatically returns NO.
     **/
    
    
    public void fileManagerShouldProceedAfterError(NSFileManager manager, NSDictionary errorInfo) throws UnsupportedOperationException {
    }

    /**
     * @param manager The NSFileManager object that sent this message.
     * @param path    The path or a file or directory that manager is about to attempt to move,
     *                copy, rename, delete, or link to.
     * @Signature: fileManager:willProcessPath:
     * @Declaration : - (void)fileManager:(NSFileManager *)manager willProcessPath:(NSString *)path
     * @Description : An NSFileManager object sends this message to a handler immediately before
     * attempting to move, copy, rename, or
     * delete, or before attempting to link to a given path. (Deprecated in iOS 2.0. See delegate
     * methods for copy, move, link,
     * and remove methods.)
     * @Discussion You can implement this method in your handler to monitor file operations.
     **/
    
    
    public void fileManagerWillProcessPath(NSFileManager manager, NSString path) throws UnsupportedOperationException {
    }

}