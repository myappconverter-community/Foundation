
package com.myappconverter.java.foundations;


import java.util.Iterator;


public class NSDirectoryEnumerator<E> extends NSEnumerator<E> {

	public NSDirectoryEnumerator(Iterator<E> iterator) {
		super(iterator);
	}

	public NSDirectoryEnumerator() {
	}

	/**
	 * @Signature: directoryAttributes
	 * @Declaration : - (NSDictionary *)directoryAttributes
	 * @Description : Returns an NSDictionary object that contains the attributes of the directory at which enumeration started.
	 * @return Return Value An NSDictionary object that contains the attributes of the directory at which enumeration started.
	 * @Discussion See the description of the fileAttributesAtPath:traverseLink: method of NSFileManager for details on obtaining the
	 *             attributes from the dictionary.
	 **/
	
	public NSDictionary<NSString, NSString> directoryAttributes() {
		return null;
	}

	public NSDictionary<NSString, NSString> getDirectoryAttributes() {
		return directoryAttributes();
	}
	/**
	 * @Signature: fileAttributes
	 * @Declaration : - (NSDictionary *)fileAttributes
	 * @Description : Returns an object that contains the attributes of the most recently returned file or subdirectory (as referenced by
	 *              the pathname).
	 * @return Return Value A dictionary that contains the attributes of the most recently returned file or subdirectory (as referenced by
	 *         the pathname).
	 * @Discussion See the description of the fileAttributesAtPath:traverseLink: method of NSFileManager for details on obtaining the
	 *             attributes from the dictionary.
	 **/
	
	public NSDictionary<NSString,Object> fileAttributes() {
		return null;
	}

	public NSDictionary<NSString,Object> getFileAttributes() {
		return fileAttributes();
	}
	/**
	 * @Signature: level
	 * @Declaration : - (NSUInteger)level
	 * @Description : Returns the number of levels deep the current object is in the directory hierarchy being enumerated.
	 * @return Return Value The number of levels, with the directory passed to
	 *         enumeratorAtURL:includingPropertiesForKeys:options:errorHandler: (NSFileManager) considered to be level 0.
	 **/
	
	public int level() {
		return 0;
	}

	public int getLevel() {
		return level();
	}

	// Skipping Subdirectories

	/**
	 * @Signature: skipDescendents
	 * @Declaration : - (void)skipDescendents
	 * @Description : Causes the receiver to skip recursion into the most recently obtained subdirectory.
	 **/
	
	public void skipDescendants() {

	}

	/**
	 * @Signature: skipDescendents
	 * @Declaration : - (void)skipDescendents
	 * @Description : Causes the receiver to skip recursion into the most recently obtained subdirectory.
	 **/
	
	public void skipDescendents() {

	}

}