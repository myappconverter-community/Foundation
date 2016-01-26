
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.functions.NSPathUtilities;
import com.myappconverter.java.foundations.protocols.NSCoding;

import java.io.File;
import java.util.List;


public class NSFileWrapper extends NSObject implements NSCoding {

    // NSFileWrapperReadingOptions
    public static final int NSFileWrapperReadingImmediate = 1 << 0; //
    public static final int NSFileWrapperReadingWithoutMapping = 1 << 1;//

    // NSFileWrapperWritingOptions
    public static final int NSFileWrapperWritingAtomic = 1 << 0; //
    public static final int NSFileWrapperWritingWithNameUpdating = 1 << 1;

    
    public static enum GSFileWrapperType {
        GSFileWrapperDirectoryType, //
        GSFileWrapperRegularFileType, //
        GSFileWrapperSymbolicLinkType
    }

    protected List<File> filesWrapper;
    protected NSString _filename;
    protected NSString _preferredFilename;
    protected NSMutableDictionary<NSObject, NSObject> _fileAttributes;
    protected GSFileWrapperType _wrapperType;
    protected NSObject wrapperData;
    protected NSObject iconImage;

    private NSString preferredFilename() {
        return _preferredFilename;
    }

    // Creating File Wrappers
    // This class has several designated initializers.

    /**
     * @param url      URL of the file-system node the file wrapper is to represent.
     * @param options  Option flags for reading the node located at url. See “File Wrapper Reading
     *                 Options for possible values.
     * @param outError If an error occurs, upon return contains an NSError object that describes
     *                 the
     *                 problem. Pass NULL if you do not want
     *                 error information.
     * @return Return Value File wrapper for the file-system node at url. May be a directory, file,
     * or symbolic link, depending on what is
     * located at the URL. Returns NO (0) if reading is not successful.
     * @Signature: initWithURL:options:error:
     * @Declaration : - (id)initWithURL:(NSURL *)url options:(NSFileWrapperReadingOptions)options
     * error:(NSError **)outError
     * @Description : Initializes a file wrapper instance whose kind is determined by the type of
     * file-system node located by the URL.
     * @Discussion If url is a directory, this method recursively creates file wrappers for each
     * node within that directory. Use the
     * fileWrappers method to get the file wrappers of the nodes contained by the directory.
     **/
    
    public NSFileWrapper initWithURLOptionsError(NSURL url, int options, NSError[] outError) {
        return initWithPath(url.path());
    }

    /**
     * @param childrenByPreferredName Key-value dictionary of file wrappers with which to
     *                                initialize
     *                                the receiver. The dictionary must
     *                                contain entries whose values are the file wrappers that are
     *                                to
     *                                become children and whose keys are filenames. See “Working
     *                                with Directory Wrappers in File System Programming Guide for
     *                                more information about the file-wrapper list structure.
     * @return Return Value Initialized file wrapper for fileWrappers.
     * @Signature: initDirectoryWithFileWrappers:
     * @Declaration : - (id)initDirectoryWithFileWrappers:(NSDictionary *)childrenByPreferredName
     * @Description : Initializes the receiver as a directory file wrapper, with a given
     * file-wrapper list.
     * @Discussion After initialization, the file wrapper is not associated with a file-system node
     * until you save it using
     * writeToURL:options:originalContentsURL:error:. The receiver is initialized with open
     * permissions: anyone can read, write,
     * or modify the directory on disk. If any file wrapper in the directory doesn’t have a
     * preferred filename, its preferred
     * name is automatically set to its corresponding key in the childrenByPreferredName
     * dictionary.
     **/
    
    public NSFileWrapper initDirectoryWithFileWrappers(NSDictionary childrenByPreferredName) {
        if (childrenByPreferredName != null) {
            NSEnumerator<NSObject> enumerator;
            NSObject key;
            NSFileWrapper wrapper;

            _wrapperType = GSFileWrapperType.GSFileWrapperDirectoryType;
            NSMutableDictionary nsMutableDictionary = new NSMutableDictionary<NSObject, NSObject>();
            wrapperData = nsMutableDictionary.initWithCapacity(childrenByPreferredName.count());

            enumerator = childrenByPreferredName.keyEnumerator();
            while ((key = (NSObject) enumerator.nextObject()) != null) {
                wrapper = (NSFileWrapper) childrenByPreferredName.objectForKey(key);

                if (wrapper.preferredFilename() == null) {
                    wrapper.setPreferredFilename((NSString) key);
                }
                ((NSMutableDictionary) wrapperData).setObjectForKey(wrapper, key);
            }
        }
        return this;

    }

    /**
     * @param contents Contents of the file.
     * @return Return Value Initialized regular-file file wrapper containing contents.
     * @Signature: initRegularFileWithContents:
     * @Declaration : - (id)initRegularFileWithContents:(NSData *)contents
     * @Description : Initializes the receiver as a regular-file file wrapper.
     * @Discussion After initialization, the file wrapper is not associated with a file-system node
     * until you save it using
     * writeToURL:options:originalContentsURL:error:. The file wrapper is initialized with open
     * permissions: anyone can write to
     * or read the file wrapper.
     **/
    
    public NSFileWrapper initRegularFileWithContents(NSData contents) {
        if (contents != null) {
            wrapperData = contents.copy();
            _wrapperType = GSFileWrapperType.GSFileWrapperRegularFileType;
        }
        return this;
    }

    /**
     * @param url URL of the file the file wrapper is to reference.
     * @return Return Value Initialized symbolic-link file wrapper referencing url.
     * @Signature: initSymbolicLinkWithDestinationURL:
     * @Declaration : - (id)initSymbolicLinkWithDestinationURL:(NSURL *)url
     * @Description : Initializes the receiver as a symbolic-link file wrapper that links to a
     * specified file.
     * @Discussion The file wrapper is not associated with a file-system node until you save it
     * using
     * writeToURL:options:originalContentsURL:error:. The file wrapper is initialized with open
     * permissions: anyone can modify
     * or read the file reference. .
     **/
    
    public NSFileWrapper initSymbolicLinkWithDestinationURL(NSURL url) {
        NSFileWrapper nsFileWrapper = new NSFileWrapper();
        NSData nsData = new NSData();
        nsData.initWithContentsOfURL(url);
        if (nsData != null) {
            nsFileWrapper.wrapperData = nsData;
            nsFileWrapper._wrapperType = GSFileWrapperType.GSFileWrapperSymbolicLinkType;
        }
        return nsFileWrapper;
    }

    /**
     * @param serializedRepresentation Serialized representation of a file wrapper in the format
     *                                 used for the NSFileContentsPboardType
     *                                 pasteboard type. Data of this format is returned by such
     *                                 methods as serializedRepresentation and
     *                                 RTFDFromRange:documentAttributes: (NSAttributedString).
     * @return Return Value Regular-file file wrapper initialized from serializedRepresentation.
     * @Signature: initWithSerializedRepresentation:
     * @Declaration : - (id)initWithSerializedRepresentation:(NSData *)serializedRepresentation
     * @Description : Initializes the receiver as a regular-file file wrapper from given serialized
     * data.
     * @Discussion The file wrapper is not associated with a file-system node until you save it
     * using
     * writeToURL:options:originalContentsURL:error:.
     **/
    
    public NSFileWrapper initWithSerializedRepresentation(NSData serializedRepresentation) {
        return initRegularFileWithContents(serializedRepresentation);
    }

    // Available in iOS 4.0 through iOS 4.3

    /**
     * @param : node Pathname the receiver is to represent.
     * @return : Initialized symbolic-link file wrapper referencing node.
     * @Description :Initializes the receiver as a symbolic-link file wrapper. (Available in iOS
     * 4.0
     * through iOS 4.3. Use
     * initSymbolicLinkWithDestinationURL: instead.)
     * @Declaration: - (id)initSymbolicLinkWithDestination:(NSString *)node
     * @Discussion The receiver is not associated to a file-system node until you save it using
     * writeToFile:atomically:updateFilenames:.
     * It’s also initialized with open permissions; anyone can read or write the disk
     * representations it saves.
     */
    
    
    public NSFileWrapper initSymbolicLinkWithDestination(NSString node) {
        return initSymbolicLinkWithDestinationURL(new NSURL(node.getWrappedString()));
    }

    /**
     * Initializes a file wrapper instance whose kind is determined by the type of file-system node
     * located by the path. (Available in iOS
     * 4.0 through iOS 4.3. Use initWithURL:options:error: instead.) <br>
     * - (id)initWithPath:(NSString *)node
     *
     * @param node Pathname of the file-system node the file wrapper is to represent.
     * @return File wrapper for node.
     * @Discussion If node is a directory, this method recursively creates file wrappers for each
     * node within that directory.
     */
    
    
    public NSFileWrapper initWithPath(NSString path) {
        NSFileWrapper nsFileWrapper = new NSFileWrapper();
        NSFileManager fm = NSFileManager.defaultManager();
        NSString fileType;

        // Store the full path in filename, the specification is unclear in this point

        setFilename(path);
        setPreferredFilename(path.lastPathComponent());
        setFileAttributes(fm.attributesOfItemAtPathError(path, null));
        fileType = (NSString) fileAttributes().getWrappedDictionary().get("fileType");
        if ("NSFileTypeDirectory".equals(fileType)) {
            NSString filename;
            NSMutableArray fileWrappers = new NSMutableArray();
            NSArray<NSString> filenames = fm.contentsOfDirectoryAtPathError(path, null);
            NSEnumerator<NSString> enumerator = filenames.objectEnumerator();
            // for each element in directory , we will create an NSFileWrapper
            while ((filename = (NSString) enumerator.nextObject()) != null) {
                NSFileWrapper nfw = new NSFileWrapper();
                new NSPathUtilities().stringByAppendingPathComponent(filename);
                nfw.initWithPath(path);
            }
            nsFileWrapper = initDirectoryWithFileWrappers(NSDictionary.dictionaryWithObjectForKey(NSDictionary.class, fileWrappers, filenames));
            fileWrappers = null;
        } else if ("NSFileTypeRegular".equals(fileType.getWrappedString())) {
            NSData nsData = new NSData();
            nsData.initWithContentsOfFile(path);
            nsFileWrapper = initRegularFileWithContents(nsData);

        } else if ("NSFileTypeSymbolicLink".equals(fileType.getWrappedString())) {
            nsFileWrapper = initSymbolicLinkWithDestination(fm.pathContentOfSymbolicLinkAtPath(path));
        }

        return nsFileWrapper;
    }

    // Querying File Wrappers

    /**
     * @return Return Value YES when the receiver is a regular-file wrapper, NO otherwise.
     * @Signature: isRegularFile
     * @Declaration : - (BOOL)isRegularFile
     * @Description : Indicates whether the receiver is a regular-file file wrapper.
     * @Discussion Invocations of readFromURL:options:error: may change what is returned by
     * subsequent invocations of this method if the
     * type of the file on disk has changed.
     **/
    
    public boolean isRegularFile() {
        if (_wrapperType == GSFileWrapperType.GSFileWrapperRegularFileType) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return Return Value YES when the receiver is a directory file wrapper, NO otherwise.
     * @Signature: isDirectory
     * @Declaration : - (BOOL)isDirectory
     * @Description : Indicates whether the receiver is a directory file wrapper.
     * @Discussion Invocations of readFromURL:options:error: may change what is returned by
     * subsequent invocations of this method if the
     * type of the file on disk has changed.
     **/
    
    public boolean isDirectory() {
        return _wrapperType.equals(GSFileWrapperType.GSFileWrapperDirectoryType);
    }

    /**
     * @return Return Value YES when the receiver is a symbolic-link file wrapper, NO otherwise.
     * @Signature: isSymbolicLink
     * @Declaration : - (BOOL)isSymbolicLink
     * @Description : Indicates whether the receiver is a symbolic-link file wrapper.
     * @Discussion Invocations of readFromURL:options:error: may change what is returned by
     * subsequent invocations of this method if the
     * type of the file on disk has changed.
     **/
    
    public boolean isSymbolicLink() {
        return _wrapperType.equals(GSFileWrapperType.GSFileWrapperSymbolicLinkType);
    }

    // Accessing File-Wrapper Information

    /**
     * @return Return Value A key-value dictionary of the file wrappers contained in the directory.
     * The dictionary contains entries whose
     * values are the file wrappers and whose keys are the unique filenames that have been assigned
     * to each one. See “Working with
     * Directory Wrappers in File System Programming Guide for more information about the
     * file-wrapper list structure.
     * @Signature: fileWrappers
     * @Declaration : - (NSDictionary *)fileWrappers
     * @Description : Returns the file wrappers contained by a directory file wrapper.
     * @Discussion Returns a dictionary whose values are the file wrapper's children and whose keys
     * are the unique filenames that have been
     * assigned to each one. This method may return nil if the user modifies the directory after
     * you
     * call
     * readFromURL:options:error: or initWithURL:options:error: but before NSFileWrapper has read
     * the contents of the directory.
     * Use the NSFileWrapperReadingImmediate reading option to reduce the likelihood of that
     * problem.
     **/
    
    public NSDictionary<NSObject, NSObject> fileWrappers() {
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInternalInconsistencyException, new NSString(
                    "Can't invoke %@ on a file wrapper that does not wrap a directory!"));
        return (NSDictionary<NSObject, NSObject>) wrapperData;
    }

    /**
     * @param child File wrapper to add to the directory.
     * @return Return Value Dictionary key used to store fileWrapper in the directory’s list of file
     * wrappers. The dictionary key is a
     * unique filename, which is the same as the passed-in file wrapper's preferred filename unless
     * that name is already in use as a
     * key in the directory’s dictionary of children. See “Working with Directory Wrappers in File
     * System Programming Guide for
     * more information about the file-wrapper list structure.
     * @Signature: addFileWrapper:
     * @Declaration : - (NSString *)addFileWrapper:(NSFileWrapper *)child
     * @Description : Adds a child file wrapper to the receiver, which must be a directory file
     * wrapper.
     * @Discussion Use this method to add an existing file wrapper as a child of a directory file
     * wrapper. If the file wrapper does not have
     * a preferred filename, use the setPreferredFilename: method to give it one before calling
     * addFileWrapper:. To create a new
     * file wrapper and add it to a directory, use the addRegularFileWithContents:preferredFilename:
     * method.
     **/
    
    public NSString addFileWrapper(NSFileWrapper child) {
        NSString key;
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInternalInconsistencyException, new NSString(
                    "Can't invoke %@ on a file wrapper that does not wrap a directory!"));

        key = child.preferredFilename();
        if (key == null || "".equals(key)) {
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString("Adding file wrapper with no preferred filename"));
            return null;
        }
        if (((NSDictionary<NSObject, NSObject>) wrapperData).objectForKey(key) != null) {
            // TODO handle duplicate names
        }
        ((NSDictionary<NSObject, NSObject>) wrapperData).getWrappedDictionary().put(key, child);

        return key;
    }

    /**
     * @param child File wrapper to remove from the directory.
     * @Signature: removeFileWrapper:
     * @Declaration : - (void)removeFileWrapper:(NSFileWrapper *)child
     * @Description : Removes a child file wrapper from the receiver, which must be a directory
     * file
     * wrapper.
     **/
    
    public void removeFileWrapper(NSFileWrapper child) {
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString(
                    "Can't invoke this method on a file wrapper that does not wrap a directory!"));
        ((NSMutableDictionary) wrapperData).removeObjectsForKeys(((NSDictionary) wrapperData).allKeysForObject(child));
    }

    /**
     * @param data     Contents for the new regular-file file wrapper.
     * @param filename Preferred filename for the new regular-file file wrapper.
     * @return Return Value Dictionary key used to store the new file wrapper in the directory’s
     * list of file wrappers. The dictionary key
     * is a unique filename, which is the same as the passed-in file wrapper's preferred filename
     * unless that name is already in use
     * as a key in the directory's dictionary of children. See “Working with Directory Wrappers in
     * File System Programming Guide
     * for more information about the file-wrapper list structure.
     * @Signature: addRegularFileWithContents:preferredFilename:
     * @Declaration : - (NSString *)addRegularFileWithContents:(NSData *)data
     * preferredFilename:(NSString *)filename
     * @Description : Creates a regular-file file wrapper with the given contents and adds it to
     * the
     * receiver, which must be a directory
     * file wrapper.
     * @Discussion This is a convenience method. The default implementation allocates a new file
     * wrapper, initializes it with
     * initRegularFileWithContents:, sends it setPreferredFilename:, adds it to the directory with
     * addFileWrapper:, and returns
     * what addFileWrapper: returned.
     **/
    
    public NSString addRegularFileWithContentsPreferredFilename(NSData data, NSString filename) {
        NSFileWrapper wrapper;
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString(
                    "Can't invoke this method on a file wrapper that does not wrap a directory!"));
        wrapper = new NSFileWrapper();
        wrapper = initRegularFileWithContents(data);
        if (wrapper != null) {
            wrapper.setPreferredFilename(filename);
            return addFileWrapper(wrapper);
        } else {
            return null;
        }
    }

    /**
     * @param child The child file wrapper for which you want the key.
     * @return Return Value Dictionary key used to store the file wrapper in the directory’s list of
     * file wrappers. The dictionary key is a
     * unique filename, which may not be the same as the passed-in file wrapper's preferred
     * filename
     * if more than one file wrapper
     * in the directory's dictionary of children has the same preferred filename. See “Working with
     * Directory Wrappers in File
     * System Programming Guide for more information about the file-wrapper list structure. Returns
     * nil if the file wrapper
     * specified in child is not a child of the directory.
     * @Signature: keyForFileWrapper:
     * @Declaration : - (NSString *)keyForFileWrapper:(NSFileWrapper *)child
     * @Description : Returns the dictionary key used by a directory to identify a given file
     * wrapper.
     **/
    
    public NSString keyForFileWrapper(NSFileWrapper child) {
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString(
                    "Can't invoke this method on a file wrapper that does not wrap a directory!"));
        NSArray keyArray = ((NSDictionary) wrapperData).allKeysForObject(child);
        return keyArray.description();
    }

    /**
     * @return Return Value Pathname the file wrapper references (that is, the destination of the
     * symbolic link the file wrapper
     * represents).
     * @Signature: symbolicLinkDestinationURL
     * @Declaration : - (NSURL *)symbolicLinkDestinationURL
     * @Description : Provides the URL referenced by the receiver, which must be a symbolic-link
     * file wrapper.
     * @Discussion This method may return nil if the user modifies the symbolic link after you call
     * readFromURL:options:error: or
     * initWithURL:options:error: but before NSFileWrapper has read the contents of the link. Use
     * the
     * NSFileWrapperReadingImmediate reading option to reduce the likelihood of that problem.
     **/
    
    public NSURL symbolicLinkDestinationURL() {
        if (_wrapperType == GSFileWrapperType.GSFileWrapperSymbolicLinkType) {
            return null;
        } else {
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString("File wrapper does not wrap symbolic link."));
        }
        return null;
    }

    /**
     * @param node : file-system node from which to create the file wrapper to add to the
     *             directory.
     * @return Dictionary key used to store the new file wrapper in the di
     * @Description :Creates a file wrapper from a given file-system node and adds it to the
     * receiver, which must be a directory file
     * wrapper. (Available in iOS 4.0 through iOS 4.3. Use addFileWrapper: instead.)
     * @Declaration :- (NSString *)addFileWithPath:(NSString *)node
     */
    
    
    public NSString addFileWithPath(NSString node) {
        NSFileWrapper wrapper;
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString(
                    "Can't invoke this method on a file wrapper that does not wrap a directory!"));
        wrapper = new NSFileWrapper();
        wrapper = initWithPath(node);
        if (wrapper != null) {
            return addFileWrapper(wrapper);
        } else {
            return null;
        }
    }

    /**
     * @param node              Pathname the new symbolic-link file wrapper is to reference.
     * @param preferredFilename Preferred filename for the new symbolic-link file wrapper.
     * @return Dictionary key used to store the new file wrapper in the directory’s list of file
     * wrappers. See “Working with Directory
     * Wrappers in File System Programming Guide for more information.
     * @Description : Creates a symbolic-link file wrapper pointing to a given file-system node and
     * adds it to the receiver, which must be a
     * directory file wrapper. (Available in iOS 4.0 through iOS 4.3. Use addFileWrapper: instead.)
     * @Declaration :- (NSString *)addSymbolicLinkWithDestination:(NSString *)node
     * preferredFilename:(NSString *)preferredFilename
     */
    
    
    public NSString addSymbolicLinkWithDestinationPreferredFilename(NSString node, NSString preferredFilename) {
        NSFileWrapper wrapper;
        if (_wrapperType != GSFileWrapperType.GSFileWrapperDirectoryType)
            NSException.raiseFormat(NSException.NSInvalidArgumentException, new NSString(
                    "Can't invoke this method on a file wrapper that does not wrap a directory!"));

        wrapper = new NSFileWrapper();
        wrapper = initSymbolicLinkWithDestination(node);
        if (wrapper != null) {
            wrapper.setPreferredFilename(preferredFilename);
            return addFileWrapper(wrapper);
        } else {
            return null;
        }
    }

    /**
     * provides the pathname referenced by the receiver, which must be a symbolic-link file
     * wrapper.
     * <br>
     * (Available in iOS 4.0 through iOS 4.3. Use symbolicLinkDestinationURL instead.)
     *
     * @Declaration : - (NSString *)symbolicLinkDestination
     */
    
    
    public NSString symbolicLinkDestination() {
        if (_wrapperType.equals(GSFileWrapperType.GSFileWrapperSymbolicLinkType)) {
            return wrapperData.description();
        } else {
            NSException
                    .raiseFormat(NSException.NSInternalInconsistencyException, new NSString("File wrapper does not wrap symbolic link."));
        }
        return null;
    }

    // Updating File Wrappers

    /**
     * @param url URL of the file-system node with which to compare the file wrapper.
     * @return Return Value YES when the contents of the file wrapper match the contents of url, NO
     * otherwise.
     * @Signature: matchesContentsOfURL:
     * @Declaration : - (BOOL)matchesContentsOfURL:(NSURL *)url
     * @Description : Indicates whether the contents of a file wrapper matches a directory, regular
     * file, or symbolic link on disk.
     * @Discussion The contents of files are not compared; matching of regular files is based on
     * file modification dates. For a directory,
     * children are compared against the files in the directory, recursively. Because children of
     * directory file wrappers are
     * not read immediately by the initWithURL:options:error: method unless the
     * NSFileWrapperReadingImmediate reading option is
     * used, even a newly-created directory file wrapper might not have the same contents as the
     * directory on disk. You can use
     * this method to determine whether the file wrapper's contents in memory need to be updated.
     * If
     * the file wrapper needs
     * updating, use the readFromURL:options:error: method with the NSFileWrapperReadingImmediate
     * reading option. This table
     * describes which attributes of the file wrapper and file-system node are compared to
     * determine
     * whether the file wrapper
     * matches the node on disk: File-wrapper type Comparison determinants Regular file
     * Modification
     * date and access
     * permissions. Directory Children (recursive). Symbolic link Destination pathname.
     **/
    
    public boolean matchesContentsOfURL(NSURL url) {
        NSFileManager fm = NSFileManager.defaultManager();

        switch (_wrapperType) {
            case GSFileWrapperRegularFileType:
                if (fileAttributes().isEqualToDictionary(fm.attributesOfItemAtPathError(url.path(), null)))
                    return false;
                break;
            case GSFileWrapperSymbolicLinkType:
                if (wrapperData.equals(fm.pathContentOfSymbolicLinkAtPath(url.path())))
                    return false;
                break;
            case GSFileWrapperDirectoryType:
                // Has the directory itself changed?
                if (!fileAttributes().isEqualToDictionary(fm.attributesOfItemAtPathError(url.path(), null)))
                    return true;
                return false;
            default:
                break;

        }

        return true;
    }

    /**
     * @param url      URL of the file-system node corresponding to the file wrapper.
     * @param options  Option flags for reading the node located at url. See “File Wrapper Reading
     *                 Options for possible values.
     * @param outError If an error occurs, upon return contains an NSError object that describes
     *                 the
     *                 problem. Pass NULL if you do not want
     *                 error information.
     * @return Return Value YES if successful. If not successful, returns NO after setting outError
     * to an NSError object that describes the
     * reason why the file wrapper could not be reread.
     * @Signature: readFromURL:options:error:
     * @Declaration : - (BOOL)readFromURL:(NSURL *)url options:(NSFileWrapperReadingOptions)options
     * error:(NSError **)outError
     * @Description : Recursively rereads the entire contents of a file wrapper from the specified
     * location on disk.
     * @Discussion When reading a directory, children are added and removed as necessary to match
     * the file system.
     **/
    
    public boolean readFromURLOptionsError(NSURL url, int options, NSError[] outError) {
        return true;
        // TODO
    }

    /**
     * @param node file-system node with which to compare the file wrapper.
     * @return YES when the file wrapper needs to be updated to match node, NO otherwise.
     * @Description: Indicates whether the file wrapper needs to be updated to match a given
     * file-system node. (Available in iOS 4.0 through
     * iOS 4.3. Use matchesContentsOfURL: instead.)
     * @Declaration:- (BOOL)needsToBeUpdatedFromPath:(NSString *)node
     */
    
    
    public boolean needsToBeUpdatedFromPath(NSString node) {
        return matchesContentsOfURL(new NSURL(node.getWrappedString()));

    }

    
    
    /**
     * @Description: Updates the file wrapper to match a given file-system node. (Available in iOS 4.0 through iOS 4.3.
     *               Use readFromURL:options:error: instead.)
     * @Declaration: - (BOOL)updateFromPath:(NSString *)path
     * @return YES if the update is carried out, NO if it isn’t needed.
     */
    public boolean updateFromPath(NSString path) {
        return readFromURLOptionsError(new NSURL(path.getWrappedString()), 0, null);
    }

    // Serializing

    /**
     * @return Return Value The file wrapper’s contents in the format used for the pasteboard type
     * NSFileContentsPboardType.
     * @Signature: serializedRepresentation
     * @Declaration : - (NSData *)serializedRepresentation
     * @Description : Returns the contents of the file wrapper as an opaque collection of data.
     * @Discussion Returns an NSData object suitable for passing to initWithSerializedRepresentation:.
     * This method may return nil if the
     * user modifies the contents of the file-system node after you call readFromURL:options:error:
     * or
     * initWithURL:options:error: but before NSFileWrapper has read the contents of the file. Use
     * the
     * NSFileWrapperReadingImmediate reading option to reduce the likelihood of that problem.
     **/
    
    public NSData serializedRepresentation() {
        return NSKeyedArchiver.archivedDataWithRootObject(this);
    }

    // Accessing Files

    /**
     * @return Return Value The file wrapper’s filename; nil when the file wrapper has no
     * corresponding file-system node.
     * @Signature: filename
     * @Declaration : - (NSString *)filename
     * @Description : Provides the filename of a file wrapper.
     * @Discussion The filename is used for record-keeping purposes only and is set automatically
     * when the file wrapper is created from the
     * file system using initWithURL:options:error: and when it’s saved to the file system using
     * writeToURL:options:originalContentsURL:error: (although this method allows you to request
     * that the filename not be
     * updated). The filename is usually the same as the preferred filename, but might instead be a
     * name derived from the
     * preferred filename. You can use this method to get the name of a child that's just been
     * read.
     * Don’t use this method to
     * get the name of a child that's about to be written, because the name might be about to
     * change; send keyForFileWrapper: to
     * the parent instead.
     **/
    
    public NSString filename() {
        return _filename;
    }

    /**
     * @param filename Filename of the file wrapper.
     * @Signature: setFilename:
     * @Declaration : - (void)setFilename:(NSString *)filename
     * @Description : Specifies the filename of a file wrapper.
     * @Discussion The file name is a dictionary key used to store fileWrapper in a directory’s
     * list
     * of child file wrappers. The dictionary
     * key is a unique filename, which is the same as the child file wrapper's preferred filename
     * unless that name is already in
     * use as a key in the directory’s dictionary of children. See “Working with Directory
     * Wrappers
     * in File System Programming
     * Guide for more information about the file-wrapper list structure. In general, the filename
     * is
     * set for you by the
     * initWithURL:options:error:, initDirectoryWithFileWrappers:, or writeToURL:options:originalContentsURL:error:
     * methods; you
     * do not normally have to call this method directly.
     **/
    
    public void setFilename(NSString filename) {
        if (filename == null || "".equals(filename)) {
            NSException.raiseFormat(NSException.NSInternalInconsistencyException, new NSString("Empty or nil argument to setFilename: "));
        } else
            _filename = filename;
    }

    /**
     * @return Return Value The file wrapper’s preferred filename.
     * @Signature: preferredFilename
     * @Declaration : - (NSString *)preferredFilename
     * @Description : Provides the preferred filename for a file wrapper.
     * @Discussion This name is normally used as the dictionary key when a child file wrapper is
     * added to a directory file wrapper. However,
     * if another file wrapper with the same preferred name already exists in the directory file
     * wrapper when the receiver is
     * added, the filename assigned as the dictionary key may differ from the preferred filename.
     **/
    
    public NSString preferredFilename(NSString filename) {
        return _preferredFilename;
    }

    /**
     * @param filename Preferred filename for the receiver.
     * @Signature: setPreferredFilename:
     * @Declaration : - (void)setPreferredFilename:(NSString *)filename
     * @Description : Specifies the receiver’s preferred filename.
     * @Discussion When a file wrapper is added to a parent directory file wrapper, the parent
     * attempts to use the child’s preferred
     * filename as the key in its dictionary of children. If that key is already in use, then the
     * parent derives a unique
     * filename from the preferred filename and uses that for the key. When you change the
     * preferred
     * filename of a file wrapper,
     * the default implementation of this method causes existing parent directory file wrappers to
     * remove and re-add the child
     * to accommodate the change. Preferred filenames of children are not preserved when you write
     * a
     * file wrapper to disk and
     * then later instantiate another file wrapper by reading the file from disk. If you need to
     * preserve the user-visible names
     * of attachments, you have to store the names yourself.
     **/
    
    public void setPreferredFilename(NSString filename) {
        if (filename == null || "".equals(filename)) {
            NSException.raiseFormat(NSException.NSInternalInconsistencyException, new NSString(
                    "Empty or nil argument to setPreferredFilename:  "));
        } else
            _preferredFilename = filename;
    }

    /**
     * @return Return Value File attributes, in a dictionary of the same sort as that returned by
     * attributesOfItemAtPath:error:
     * (NSFileManager).
     * @Signature: fileAttributes
     * @Declaration : - (NSDictionary *)fileAttributes
     * @Description : Returns a file wrapper’s file attributes.
     **/
    
    public NSDictionary fileAttributes() {
        return _fileAttributes;
    }

    /**
     * @param fileAttributes File attributes for the file wrapper, in a dictionary of the same sort
     *                       as that used by
     *                       setAttributes:ofItemAtPath:error: (NSFileManager).
     * @Signature: setFileAttributes:
     * @Declaration : - (void)setFileAttributes:(NSDictionary *)fileAttributes
     * @Description : Specifies a file wrapper’s file attributes.
     **/
    
    public void setFileAttributes(NSDictionary<NSObject, NSObject> fileAttributes) {
        if (_fileAttributes == null) {
            _fileAttributes = new NSMutableDictionary<NSObject, NSObject>();
        }
        _fileAttributes.addEntriesFromDictionary(fileAttributes);
    }

    /**
     * @return Return Value Contents of the file-system node the file wrapper represents.
     * @Signature: regularFileContents
     * @Declaration : - (NSData *)regularFileContents
     * @Description : Returns the contents of the file-system node associated with a regular-file
     * file wrapper.
     * @Discussion This method may return nil if the user modifies the file after you call
     * readFromURL:options:error: or
     * initWithURL:options:error: but before NSFileWrapper has read the contents of the file. Use
     * the
     * NSFileWrapperReadingImmediate reading option to reduce the likelihood of that problem.
     **/
    
    public NSData regularFileContents() {
        if (_wrapperType == GSFileWrapperType.GSFileWrapperRegularFileType) {
            return ((NSDictionary) wrapperData).getTxtNSData();
        } else {
            NSException.raiseFormat(NSException.NSInternalInconsistencyException, new NSString("File wrapper does not wrap regular file."));
            return null;
        }
    }

    // Writing Files

    /**
     * @param url                 URL of the file-system node to which the file wrapper’s contents
     *                            are written.
     * @param options             Option flags for writing to the node located at url. See “File
     *                            Wrapper Writing Options for possible values.
     * @param originalContentsURL The location of a previous revision of the contents being
     *                            written.
     *                            The default implementation of this
     *                            method attempts to avoid unnecessary I/O by writing hard links to
     *                            regular files instead of actually writing out their
     *                            contents when the contents have not changed. The child file
     *                            wrappers must return accurate values when sent the filename
     *                            method for this to work. Use the NSFileWrapperWritingWithNameUpdating
     *                            writing option to increase the likelihood of that.
     *                            Specify nil for this parameter if there is no earlier version of
     *                            the contents or if you want to ensure that all the
     *                            contents are written to files.
     * @param updateNames         YES to update the receiver’s filenames (its filename and—for
     *                            directory file wrappers—the filenames of its sub–file
     *                            wrappers) be changed to the filenames of the corresponding nodes
     *                            in the file system, after a successful write operation.
     *                            Use this in Save or Save As operations. NO to specify that the
     *                            receiver’s filenames not be updated. Use this in Save To
     *                            operations.
     * @param outError            If an error occurs, upon return contains an NSError object that
     *                            describes the problem. Pass NULL if you do not want
     *                            error information.
     * @return Return Value YES when the write operation is successful. If not successful, returns
     * NO after setting outError to an NSError
     * object that describes the reason why the file wrapper’s contents could not be written.
     * @Signature: writeToURL:options:originalContentsURL:error:
     * @Declaration : - (BOOL)writeToURL:(NSURL *)url options:(NSFileWrapperWritingOptions)options
     * originalContentsURL:(NSURL
     * *)originalContentsURL error:(NSError **)outError
     * @Description : Recursively writes the entire contents of a file wrapper to a given
     * file-system URL.
     **/
    
    public boolean writeToURLOptionsOriginalContentsURLError(NSURL url, int options, NSURL originalContentsURL, NSError[] outError) {
        return false;
    }

    /**
     * Writes a file wrapper’s contents to a given file-system node. (Available in iOS 4.0 through
     * iOS 4.3. Use
     * writeToURL:options:originalContentsURL:error: instead.)
     *
     * @param node        Pathname of the file-system node to which the receiver’s contents are
     *                    written.
     * @param atomically  YES to write the file safely so that: An existing file is not overwritten
     *                    The method fails if the file cannot be
     *                    written in its entirety NO to overwrite an existing file and ignore
     *                    incomplete writes.
     * @param updateNames YES to update the receiver’s filenames (its filename and—for directory
     *                    file wrappers—the filenames of its sub–file
     *                    wrappers) be changed to the filenames of the corresponding nodes in the
     *                    file system, after a successful write operation.
     *                    Use this in Save or Save As operations. NO to specify that the receiver’s
     *                    filenames not be updated. Use this in Save To
     *                    operations.
     * @return YES when the write operation is successful, NO otherwise.
     * @Declaration: - (BOOL)writeToFile:(NSString *)node atomically:(BOOL)atomically
     * updateFilenames:(BOOL)updateNames
     */
    
    
    public boolean writeToFileAtomicallyUpdateFilenames(NSString node, boolean atomically, boolean updateNames) {

        NSFileManager fm = NSFileManager.defaultManager();
        boolean success = false;
        switch (_wrapperType) {
            case GSFileWrapperDirectoryType: {
                NSEnumerator enumerator = ((NSDictionary) wrapperData).keyEnumerator();
                NSString key;

                fm.createDirectoryAtPathAttributes(node, _fileAttributes);
                while ((key = (NSString) (enumerator.nextObject())) != null) {
                }
                success = true;
                break;
            }
            case GSFileWrapperRegularFileType: {
                if (((NSDictionary) wrapperData).writeToFileAtomically(node, atomically))
                    success = fm.changeFileAttributesAtPath(_fileAttributes, node);
                break;
            }
            case GSFileWrapperSymbolicLinkType: {
                break;
            }
            default:
                break;
        }
        if (success && updateNames) {
            setFilename(node.lastPathComponent());
        }
        return success;
    }

    @Override
    public void encodeWithCoder(NSCoder encoder) {
        // Dont store the file name
        encoder.encodeObject(_preferredFilename);
        encoder.encodeObject(_fileAttributes);
        encoder.encodeObject(wrapperData);
        encoder.encodeObject(iconImage);

    }

    @Override
    public NSCoding initWithCoder(NSCoder decoder) {
        if (decoder.getAllowsKeyedCoding()) {
            NSData data = (NSData) decoder.decodeObjectForKey(new NSString("NSFileWrapperData"));
            return initWithSerializedRepresentation(data);
        } else {
            NSString preferredFilename;
            NSDictionary fileAttributes;
            NSObject _wrapperData;
            // Dont restore the file name
            preferredFilename = (NSString) decoder.decodeObject();
            fileAttributes = (NSDictionary) decoder.decodeObject();
            _wrapperData = decoder.decodeObject();

            switch (_wrapperType) {
                case GSFileWrapperRegularFileType: {
                    initRegularFileWithContents((NSData) wrapperData);
                    break;
                }
                case GSFileWrapperSymbolicLinkType: {
                    initSymbolicLinkWithDestination((NSString) wrapperData);
                    break;
                }
                case GSFileWrapperDirectoryType: {
                    initDirectoryWithFileWrappers((NSDictionary) wrapperData);
                    break;
                }
                default:
                    break;
            }

            if (preferredFilename != null) {
                setPreferredFilename(preferredFilename);
            }
            if (fileAttributes != null) {
                setFileAttributes(fileAttributes);
            }

        }
        return this;
    }

}