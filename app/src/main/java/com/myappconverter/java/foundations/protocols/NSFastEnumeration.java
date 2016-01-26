package com.myappconverter.java.foundations.protocols;

public interface NSFastEnumeration {

	// 1
	/**
	 * @Signature: countByEnumeratingWithState:objects:count:
	 * @Declaration : - (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state objects:(id *)stackbuf count:(NSUInteger)len
	 * @Description : Returns by reference a C array of objects over which the sender should iterate, and as the return value the number of
	 *              objects in the array. (required)
	 * @return Return Value The number of objects returned in stackbuf. Returns 0 when the iteration is finished.
	 * @Discussion The state structure is assumed to be of stack local memory, so you can recast the passed in state structure to one more
	 *             suitable for your iteration.
	 **/

	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state, Object[] stackbuf, int len);

	// to check

	public static class NSFastEnumerationState {
		long state;
		NSObject[] itemsPtr;
		long mutationsPtr;
		long[] extra = new long[5];
	}

}
