
package com.myappconverter.java.foundations;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class NSMetadataItem<T extends NSObject> extends NSObject implements Iterable<NSString>, Iterator<NSString> {

    Map<NSString, T> fileMetadata = new HashMap<NSString, T>();

    // Attribute Keys
    // Attribute keys that may be associated with an item.

    public Map<NSString, T> getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(HashMap<NSString, T> fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    // NSMetadataItemFSNameKey
    // The value is an NSString object with the name of the item as seen in the file system.
    public static final NSString NSMetadataItemFSNameKey = new NSString("NSMetadataItemFSNameKey");
    // NSMetadataItemDisplayNameKey
    // The value is an NSString object with the display name of the item, which may be different then the file system
    // name.
    public static final NSString NSMetadataItemDisplayNameKey = new NSString("NSMetadataItemDisplayNameKey");
    // NSMetadataItemURLKey
    // The value is an NSURL object that you can use to open the file.
    public static final NSString NSMetadataItemURLKey = new NSString("NSMetadataItemURLKey");
    // NSMetadataItemPathKey
    // The value is an NSString object that contains the full path to the file
    public static final NSString NSMetadataItemPathKey = new NSString("NSMetadataItemPathKey");
    // NSMetadataItemFSSizeKey
    // The value is an NSNumber object that indicates the size (in bytes) of the file on disk.
    public static final NSString NSMetadataItemFSSizeKey = new NSString("NSMetadataItemFSSizeKey");
    // NSMetadataItemFSCreationDateKey
    // The value is an NSDate object that contains the date and time that the file was created.
    public static final NSString NSMetadataItemFSCreationDateKey = new NSString("NSMetadataItemFSCreationDateKey");
    // NSMetadataItemFSContentChangeDateKey
    // The value is an NSDate object that contains the date and time that the file contents last changed.
    public static final NSString NSMetadataItemFSContentChangeDateKey = new NSString("NSMetadataItemFSContentChangeDateKey");

    // Getting Item Attributes

    /**
     * @return Return Value An array containing the attribute names of the receiver’s values. See
     * the “Constants section for a list of
     * possible keys.
     * @Signature: attributes
     * @Declaration : - (NSArray *)attributes
     * @Description : Returns an array containing the attribute names of the receiver’s values.
     **/
    
    public NSArray<T> attributes() {
        NSArray<T> nsArray = new NSArray<T>();
        nsArray.wrappedList.addAll(fileMetadata.values());
        return nsArray;
    }

    /**
     * @param key The name of a metadata attribute. See the “Constants section for a list of
     *            possible keys.
     * @return Return Value The receiver’s metadata attribute name specified by key.
     * @Signature: valueForAttribute:
     * @Declaration : - (id)valueForAttribute:(NSString *)key
     * @Description : Returns the receiver’s metadata attribute name specified by a given key.
     **/
    
    public T valueForAttribute(NSString key) {
        return fileMetadata.get(key);
    }

    /**
     * @param keys An array containing NSString objects that specify the names of a metadata
     *             attributes. See the “Constants section for a
     *             list of possible keys.
     * @return Return Value A dictionary containing the key-value pairs for the attribute names
     * specified by keys.
     * @Signature: valuesForAttributes:
     * @Declaration : - (NSDictionary *)valuesForAttributes:(NSArray *)keys
     * @Description : Returns a dictionary containing the key-value pairs for the attribute names
     * specified by a given array of keys.
     **/
    
    public NSDictionary<NSString, T> valuesForAttributes(NSArray<NSString> keys) {
        NSDictionary<NSString, T> fileAttributes = new NSDictionary<NSString, T>();
        for (NSString key : keys.getWrappedList()) {
            if (fileMetadata.containsKey(key)) {
                fileAttributes.getWrappedDictionary().put(key, fileMetadata.get(key));
            }
        }
        return fileAttributes;
    }

    // Constants

    public static final NSString NSMetadataItemIsUbiquitousKey = new NSString("NSMetadataItemIsUbiquitousKey");
    public static final NSString NSMetadataUbiquitousItemHasUnresolvedConflictsKey = new NSString("NSMetadataUbiquitousItemHasUnresolvedConflictsKey");
    public static final NSString NSMetadataUbiquitousItemIsDownloadedKey = new NSString("NSMetadataUbiquitousItemIsDownloadedKey");
    public static final NSString NSMetadataUbiquitousItemIsDownloadingKey = new NSString("NSMetadataUbiquitousItemIsDownloadingKey");
    public static final NSString NSMetadataUbiquitousItemIsUploadedKey = new NSString("NSMetadataUbiquitousItemIsUploadedKey");
    public static final NSString NSMetadataUbiquitousItemIsUploadingKey = new NSString("NSMetadataUbiquitousItemIsUploadingKey");
    public static final NSString NSMetadataUbiquitousItemPercentDownloadedKey = new NSString("NSMetadataUbiquitousItemPercentDownloadedKey");
    public static final NSString NSMetadataUbiquitousItemPercentUploadedKey = new NSString("NSMetadataUbiquitousItemPercentUploadedKey");
    public static final NSString NSMetadataUbiquitousItemDownloadingStatusKey = new NSString("NSMetadataUbiquitousItemDownloadingStatusKey");
    public static final NSString NSMetadataUbiquitousItemDownloadingErrorKey = new NSString("NSMetadataUbiquitousItemDownloadingErrorKey");
    public static final NSString NSMetadataUbiquitousItemUploadingErrorKey = new NSString("NSMetadataUbiquitousItemUploadingErrorKey");

    public static final NSString NSMetadataUbiquitousItemDownloadingStatusCurrent = new NSString("NSMetadataUbiquitousItemDownloadingStatusCurrent");
    public static final NSString NSMetadataUbiquitousItemDownloadingStatusDownloaded = new NSString("NSMetadataUbiquitousItemDownloadingStatusDownloaded");
    public static final NSString NSMetadataUbiquitousItemDownloadingStatusNotDownloaded = new NSString("NSMetadataUbiquitousItemDownloadingStatusNotDownloaded");

    @Override
    public boolean hasNext() {
        return fileMetadata.keySet().iterator().hasNext();
    }

    @Override
    public NSString next() {
        return fileMetadata.keySet().iterator().next();
    }

    @Override
    public void remove() {
        fileMetadata.keySet().iterator().remove();

    }

    @Override
    public Iterator<NSString> iterator() {
        return fileMetadata.keySet().iterator();
    }
}