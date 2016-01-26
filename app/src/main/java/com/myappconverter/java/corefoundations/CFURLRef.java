package com.myappconverter.java.corefoundations;

import java.net.URL;

/**
 * @Overview : The CFURL opaque type provides facilities for creating, parsing, and dereferencing URL strings. CFURL is useful to
 * applications that need to use URLs to access resources, including local files.
 * <p>
 * A CFURL object is composed of two parts—a base URL, which can be NULL, and a string that is resolved relative to the base URL.
 * A CFURL object whose string is fully resolved without a base URL is considered absolute; all others are considered relative.
 * </p>
 * <p>
 * Starting in OS X v10.6, the CFURL opaque type provides a facility for creating and using bookmarks. A bookmark provides a
 * persistent reference to a file-system resource. When you resolve a bookmark, you obtain a URL to the resource’s current
 * location. A bookmark’s association with a file-system resource (typically a file or folder) usually continues to work if the
 * user moves or renames the resource, or if the user relaunches your app or restarts the system.
 * </p>
 * // bookmark is an opaque data structure, enclosed in an NSData (CFData) object, that describes the location of a file
 * {@link https ://developer.apple.com/library/ios/documentation/FileManagement /Conceptual/FileSystemProgrammingGuide/
 * AccessingFilesandDirectories/AccessingFilesandDirectories.html}
 */
public class CFURLRef extends CFTypeRef {

    private URL wrappedUrl;
    public URL getWrappedUrl() {
        return wrappedUrl;
    }


}
