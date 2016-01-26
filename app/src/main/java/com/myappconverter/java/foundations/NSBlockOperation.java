
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSBlockOperation.NSBlockOperationBlock.addExecutionBlockBlock;
import com.myappconverter.java.foundations.NSBlockOperation.NSBlockOperationBlock.blockOperationWithBlockBlock;


public class NSBlockOperation extends NSOperation {

	NSArray<Object> listBlock;

	public NSBlockOperation() {
		listBlock = new NSMutableArray<Object>();
	}

	public static interface NSBlockOperationBlock {

		public static interface blockOperationWithBlockBlock {
			public void performAction();
		}

		public static interface addExecutionBlockBlock {
			public void performAction();
		}
	}

	/**
	 * Creates and returns an NSBlockOperation object and adds the specified block to it.
	 */

	/**
	 * @Signature: blockOperationWithBlock:
	 * @Declaration : + (id)blockOperationWithBlock:(void (^)(void))block
	 * @Description : Creates and returns an NSBlockOperation object and adds the specified block to it.
	 * @param block The block to add to the new block operation object’s list. The block should take no parameters and have no return value.
	 * @return Return Value A new block operation object.
	 **/
	
	public static NSObject blockOperationWithBlock(blockOperationWithBlockBlock block) {
		NSBlockOperation nsBlockOperation = new NSBlockOperation();
		nsBlockOperation.listBlock.getWrappedList().add(block);
		return nsBlockOperation;
	}

	/**
	 * @Signature: addExecutionBlock:
	 * @Declaration : - (void)addExecutionBlock:(void (^)(void))block
	 * @Description : Adds the specified block to the receiver’s list of blocks to perform.
	 * @param block The block to add to the receiver’s list. The block should take no parameters and have no return value.
	 * @Discussion The specified block should not make any assumptions about its execution environment. Calling this method while the
	 *             receiver is executing or has already finished causes an NSInvalidArgumentException exception to be thrown.
	 **/
	
	public void addExecutionBlock(addExecutionBlockBlock block) {

		listBlock.getWrappedList().add(block);
	}

	/**
	 * @Signature: executionBlocks
	 * @Declaration : - (NSArray *)executionBlocks
	 * @Description : Returns an array containing the blocks associated with the receiver.
	 * @return Return Value An array of blocks. The blocks in this array are copies of the ones originally added using the
	 *         addExecutionBlock: method.
	 **/
	
	NSArray<Object> executionBlocks() {
		return listBlock;
	}

	
	@Override
	public void main() {
		// implemented method
	}

	
	@Override
	public Object init() {
		return null;
	}

}