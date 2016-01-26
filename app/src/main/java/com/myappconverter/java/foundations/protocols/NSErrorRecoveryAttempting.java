package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.SEL;

public interface NSErrorRecoveryAttempting {

	// 1
	/**
	 * @Signature: attemptRecoveryFromError:optionIndex:
	 * @Declaration : - (BOOL)attemptRecoveryFromError:(NSError *)error optionIndex:(NSUInteger)recoveryOptionIndex
	 * @Description : Implemented to attempt a recovery from an error noted in an application-modal dialog.
	 * @return Return Value YES if the error recovery was completed successfully, NO otherwise.
	 * @Discussion Invoked when an error alert is been presented to the user in an application-modal dialog, and the user has selected an
	 *             error recovery option specified by error.
	 **/

	public boolean attemptRecoveryFromErrorOptionIndex(NSError error, long recoveryOptionIndex);

	// 2
	/**
	 * @Signature: attemptRecoveryFromError:optionIndex:delegate:didRecoverSelector:contextInfo:
	 * @Declaration : - (void)attemptRecoveryFromError:(NSError *)error optionIndex:(NSUInteger)recoveryOptionIndex delegate:(id)delegate
	 *              didRecoverSelector:(SEL)didRecoverSelector contextInfo:(void *)contextInfo
	 * @Description : Implemented to attempt a recovery from an error noted in an document-modal sheet.
	 * @Discussion Invoked when an error alert is presented to the user in a document-modal sheet, and the user has selected an error
	 *             recovery option specified by error. After recovery is attempted, your implementation should send delegate the message
	 *             specified in didRecoverSelector, passing the provided contextInfo. The didRecoverSelector should have the following
	 *             signature: - (void)didPresentErrorWithRecovery:(BOOL)didRecover contextInfo:(void *)contextInfo; where didRecover is YES
	 *             if the error recovery attempt was successful; otherwise it is NO.
	 **/

	public void attemptRecoveryFromErrorOptionIndexDelegateDidRecoverSelectorContextInfo(NSError error, long recoveryOptionIndex,
																						 Object delegate, SEL didRecoverSelector, Object contextInfo);
}
