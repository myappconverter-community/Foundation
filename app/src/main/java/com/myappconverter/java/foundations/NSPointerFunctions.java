
package com.myappconverter.java.foundations;



public class NSPointerFunctions extends NSObject {

	// Enumeration

	public static enum NSPointerFunctionsOptions {
		NSPointerFunctionsStrongMemory(0L), //
		NSPointerFunctionsZeroingWeakMemory(1L << 0), //
		NSPointerFunctionsOpaqueMemory(2L << 0), //
		NSPointerFunctionsMallocMemory(3L << 0), //
		NSPointerFunctionsMachVirtualMemory(4L << 0), //
		NSPointerFunctionsWeakMemory(5L << 0), //
		NSPointerFunctionsObjectPersonality(0L << 8), //
		NSPointerFunctionsOpaquePersonality(1L << 8), //
		NSPointerFunctionsObjectPointerPersonality(2L << 8), //
		NSPointerFunctionsCStringPersonality(3L << 8), //
		NSPointerFunctionsStructPersonality(4L << 8), //
		NSPointerFunctionsIntegerPersonality(5L << 8), //
		NSPointerFunctionsCopyIn(1L << 16);//

		byte value;

		NSPointerFunctionsOptions(byte l) {
			value = l;
		}

		NSPointerFunctionsOptions(long l) {
			value = (byte) l;
		}

		public byte getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "" + value;
		}
	};

	// Block

	interface Size {
		public boolean size(Object[] item);
	}

	interface AcquireFunction {
		public boolean acquireFunction(Object[] src, Size size, boolean shouldCopy);
	}

	interface DescriptionFunction {
		public boolean descriptionFunction(Object[] item);
	}

	interface HashFunction {

		public boolean hashFunction(Object[] src, Size size);
	}

	interface IsEqualFunction {
		public boolean isEqualFunction(Object[] item1, Object[] item2, Size size);
	}

	interface RelinquishFunction {

		public boolean relinquishFunction(Object[] item, Size size);
	}

	interface SizeFunction {
		public boolean sizeFunction(Object[] item);
	}

	NSPointerFunctionsOptions option;

	public NSPointerFunctions(NSPointerFunctionsOptions option) {
		super();
		this.option = option;
	}

	public NSPointerFunctionsOptions getOption() {
		return option;
	}

	public void setOption(NSPointerFunctionsOptions options) {
		this.option = options;
	}

	/**
	 * acquireFunction The function used to acquire memory.
	 *
	 * @property void *(*acquireFunction)(const void *src, NSUInteger (*size)(const void *item), BOOL shouldCopy)
	 * @Discussion This specifies the function to use for copy-in operations.
	 */

	private AcquireFunction acquireFunction;

	public AcquireFunction acquireFunction() {
		return acquireFunction;
	}

	public void setAcquireFunction(AcquireFunction acquireFunction) {
		this.acquireFunction = acquireFunction;
	}

	/**
	 * descriptionFunction The function used to describe elements.
	 *
	 * @property NSString *(*descriptionFunction)(const void *item) Discussion This function is used by description methods for hash and map
	 *           tables.
	 */

	public DescriptionFunction descriptionFunction;

	public DescriptionFunction descriptionFunction() {
		return descriptionFunction;
	}

	public void setDescriptionFunction(DescriptionFunction descriptionFunction) {
		this.descriptionFunction = descriptionFunction;
	}

	// @property NSString *(*descriptionFunction)(const void *item)
	/**
	 * hashFunction The hash function.
	 *
	 * @property NSUInteger (*hashFunction)(const void *item, NSUInteger (*size)(const void *item))
	 */

	public HashFunction hashFunction;

	public HashFunction hashFunction() {
		return hashFunction;
	}

	public void setHashFunction(HashFunction hashFunction) {
		this.hashFunction = hashFunction;
	}

	/**
	 * isEqualFunction The function used to compare pointers.
	 *
	 * @property BOOL (*isEqualFunction)(const void *item1, const void*item2, NSUInteger (*size)(const void *item))
	 */

	private IsEqualFunction isEqualFunction;

	public IsEqualFunction isEqualFunction() {
		return isEqualFunction;
	}

	public void setIsEqualFunction(IsEqualFunction isEqualFunction) {
		this.isEqualFunction = isEqualFunction;
	}

	/**
	 * relinquishFunction The function used to relinquish memory.
	 *
	 * @property void (*relinquishFunction)(const void *item, NSUInteger (*size)(const void *item)) Discussion This specifies the function
	 *           to use when an item is removed from a table or pointer array.
	 */

	public RelinquishFunction relinquishFunction;

	public RelinquishFunction relinquishFunction() {
		return relinquishFunction;
	}

	public void setRelinquishFunction(RelinquishFunction relinquishFunction) {
		this.relinquishFunction = relinquishFunction;
	}

	/**
	 * sizeFunction The function used to determine the size of pointers.
	 *
	 * @property NSUInteger (*sizeFunction)(const void *item) Discussion This function is used for copy-in operations (unless the collection
	 *           has an object personality).
	 */

	public SizeFunction sizeFunction;

	public SizeFunction sizeFunction() {
		return sizeFunction;
	}

	public void setSizeFunction(SizeFunction sizeFunction) {
		this.sizeFunction = sizeFunction;
	}

	/**
	 * usesStrongWriteBarrier Specifies whether, in a garbage collected environment, pointers should be assigned using a strong write
	 * barrier.
	 *
	 * @property BOOL usesStrongWriteBarrier
	 * @Discussion If you use garbage collection, read and write barrier functions must be used when pointers are from memory scanned by the
	 *             collector.
	 */

	public boolean usesStrongWriteBarrier;

	public boolean usesStrongWriteBarrier() {
		return usesStrongWriteBarrier;
	}

	public void setUsesStrongWriteBarrier(boolean usesStrongWriteBarrier) {
		this.usesStrongWriteBarrier = usesStrongWriteBarrier;
	}

	/**
	 * usesWeakReadAndWriteBarriers Specifies whether, in a garbage collected environment, pointers should use weak read and write barriers.
	 *
	 * @property BOOL usesWeakReadAndWriteBarriers
	 * @Discussion If you use garbage collection, read and write barrier functions must be used when pointers are from memory scanned by the
	 *             collector.
	 */

	public boolean usesWeakReadAndWriteBarriers;

	public boolean usesWeakReadAndWriteBarriers() {
		return usesWeakReadAndWriteBarriers;
	}

	public void setUsesWeakReadAndWriteBarriers(boolean usesWeakReadAndWriteBarriers) {
		this.usesWeakReadAndWriteBarriers = usesWeakReadAndWriteBarriers;
	}

	/**
	 * @Signature: pointerFunctionsWithOptions:
	 * @Declaration : + (id)pointerFunctionsWithOptions:(NSPointerFunctionsOptions)options
	 * @Description : Returns a new NSPointerFunctions object initialized with the given options.
	 * @param options The options for the new NSPointerFunctions object.
	 * @return Return Value A new NSPointerFunctions object initialized with the given options.
	 **/

	static NSObject pointerFunctionsWithOptions(NSPointerFunctionsOptions options) {
		return new NSPointerFunctions(options);
	}

	/**
	 * @Signature: initWithOptions:
	 * @Declaration : - (id)initWithOptions:(NSPointerFunctionsOptions)options
	 * @Description : Returns an NSPointerFunctions object initialized with the given options.
	 * @param options The options for the new NSPointerFunctions object.
	 * @return Return Value The NSPointerFunctions object, initialized with the given options.
	 **/

	public NSObject initWithOptions(NSPointerFunctionsOptions options) {
		return new NSPointerFunctions(options);
	}

}