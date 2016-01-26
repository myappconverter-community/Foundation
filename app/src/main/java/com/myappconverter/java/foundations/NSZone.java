
package com.myappconverter.java.foundations;

public class NSZone {

	public NSZone() {
	}

	/**
	 * @Description : Returns the number of bytes in a page.
	 * @return : Return Value The number of bytes in a page.
	 **/

	public static long intNSPageSize() {
		return 0;
	}

	/**
	 * @Description : Returns information about the user’s system.
	 * @return : Return Value The number of bytes available in RAM.
	 **/

	public static long intNSRealMemoryAvailable() {
		return 0;
	}

	/**
	 * @Description : Frees memory in a zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones in
	 *              current development.)
	 **/


	public static void NSRecycleZone(NSZone zone) {

	}

	/**
	 * @Description : Returns the specified number of bytes rounded down to a multiple of the page size.
	 * @return : Return Value In bytes, the multiple of the page size that is closest to, but not greater than, byteCount (that is, the
	 *         number of bytes rounded down to a multiple of the page size).
	 **/

	public static long intNSRoundDownToMultipleOfPageSize(long bytes) {
		return 0;
	}

	/**
	 * @Description : Returns the specified number of bytes rounded up to a multiple of the page size.
	 * @return : Return Value In bytes, the multiple of the page size that is closest to, but not less than, byteCount (that is, the number
	 *         of bytes rounded up to a multiple of the page size).
	 **/


	public static int NSRoundUpToMultipleOfPageSize(long bytes) {
		return 0;
	}

	/**
	 * @Description : Sets the name of the specified zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not
	 *              use zones in current development.)
	 **/


	public static void NSSetZoneName(NSZone zone, NSString name) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Allocates memory in a zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones
	 *              in current development.)
	 **/


	public static void NSZoneCalloc(NSZone zone, long numElems, long byteSize) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Deallocates a block of memory in the specified zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X.
	 *              You should not use zones in current development.)
	 **/


	public static void NSZoneFree(NSZone zone, Object ptr) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Gets the zone for a given block of memory. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You
	 *              should not use zones in current development.)
	 * @return : Return Value The zone for the block of memory indicated by pointer, or NULL if the block was not allocated from a zone.
	 **/


	public static NSZone NSZoneFromPointer(Object ptr) {
		return null;
	}

	/**
	 * @Description : Allocates memory in a zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones
	 *              in current development.)
	 **/


	public static void NSZoneMalloc(NSZone zone, long size) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Returns the name of the specified zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should
	 *              not use zones in current development.)
	 * @return : Return Value A string containing the name associated with zone. If zone is nil, the default zone is used. If no name is
	 *         associated with zone, the returned string is empty.
	 **/


	public static NSString NSZoneName(NSZone zone) {
		return null;
	}

	/**
	 * @Description : Allocates memory in a zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones
	 *              in current development.)
	 **/


	public static void NSZoneRealloc(NSZone zone, Object ptr, long size) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Returns the binary log of the page size.
	 * @return : Return Value The binary log of the page size.
	 **/


	public static long NSLogPageSize() {
		return 0;
	}

	/**
	 * @Description : Returns the default zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones
	 *              in current development.)
	 * @return : Return Value The default zone, which is created automatically at startup.
	 **/


	public static NSZone NSDefaultMallocZone() {
		return null;
	}

	/**
	 * @Description : Creates a new zone. (Deprecated. Zones are ignored on iOS and 64-bit runtime on OS X. You should not use zones in
	 *              current development.)
	 * @return : Return Value A pointer to a new zone of startSize bytes, which will grow and shrink by granularity bytes. If canFree is 0,
	 *         the allocator will never free memory, and malloc will be fast. Returns NULL if a new zone could not be created.
	 **/


	public static NSZone NSCreateZone(long startSize, long granularity, boolean canFree) {
		return null;
	}

	/**
	 * @Description : Deallocates the specified block of memory.
	 **/


	public static void NSDeallocateMemoryPages(Object ptr, long bytes) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Copies a block of memory.
	 **/


	public static void NSCopyMemoryPages(byte[] source, Object dest, long bytes) throws UnsupportedOperationException{

	}

	/**
	 * @Description : Allocates a new block of memory.
	 **/


	public static void NSAllocateMemoryPages(long bytes) throws UnsupportedOperationException{

	}

}