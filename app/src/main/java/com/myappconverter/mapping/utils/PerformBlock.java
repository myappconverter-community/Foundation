package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSCachedURLResponse;
import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSInputStream;
import com.myappconverter.java.foundations.NSMutableDictionary;
import com.myappconverter.java.foundations.NSObject;
import com.myappconverter.java.foundations.NSRange;
import com.myappconverter.java.foundations.NSRegularExpression;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.NSTextCheckingResult;
import com.myappconverter.java.foundations.NSURL;
import com.myappconverter.java.foundations.NSURLCredential;
import com.myappconverter.java.foundations.NSURLRequest;
import com.myappconverter.java.foundations.NSURLResponse;
import com.myappconverter.java.foundations.protocols.NSURLSessionDataDelegate;
import com.myappconverter.java.foundations.protocols.NSURLSessionDelegate;

public class PerformBlock {

	public static interface VoidBlockIdNSUIntegerBOOL<E> {
		void perform(E obj, int idx, boolean[] stop);
	}

	public static interface BOOLBlockIdNSUIntegerBOOL<E> {
		boolean perform(E obj, int idx, boolean[] stop);
	}

	public static interface VoidBlockIdNSRangeBOOL {
		void perform(Object value, NSRange range, boolean stop);
	}

	public static interface VoidBlockNSDictionaryNSRangeBOOL {
		void perform(Object value, NSRange range, boolean stop);
	}

	public static interface VoidBlockNSDictionary {
		void perform(NSDictionary value);
	}

	public static interface VoidBlockBOOL {
		public void perform(boolean finished);
	}

	public static interface VoidBlockVoidNSUInteger {
		public NSData perform(byte[] bytes, int length);
	}

	public static interface VoidBlockNSStringNSRangeNSRangeBOOL {
		void perform(NSString tag, NSRange tokenRange, NSRange sentenceRange, boolean[] stop);

		void perform(NSString line, boolean[] stop);
	}

	public static interface VoidBlockNSURLResponseNSDataNSError {
		void perform(NSURLResponse nsurlResponse, NSData nsData, NSError nsError);
	}

	public static interface VoidBlockIdBOOL {
		boolean perform(Object anObject, String string);
	}

	public static interface VoidBlockIdIdBOOL {
		public void perform(Object id, Object obj, boolean[] stop);

		public boolean perform(Object id, String block);
	}

	public static interface BOOLBlockIdIdBOOL {
		public boolean perform(Object key, Object obj, boolean[] stop);
	}

	public static interface VoidBlockNSURL {
		public void perform(NSURL newURL);
	}

	public static interface VoidBlockNSURLNSURL {
		public void perform(NSURL newReadingURL, NSURL newWritingURL);
	}

	public static interface BOOLBlockIdNSDictionary {
		public boolean perform(NSObject evaluatedObject, NSDictionary bindings);
	}

	public static interface VoidBlockVoid {
		public void perform();
	}

	public static interface VoidBlockNSCachedURLResponse {
		public void perform(NSCachedURLResponse cachedResponse);
	}

	public static interface VoidBlockNSURLSessionResponseDisposition {
		public void perform(NSURLSessionDataDelegate.NSURLSessionResponseDisposition disposition);
	}

	public static interface VoidBlockNSURLRequest {
		public void perform(NSURLRequest completionHandler);
	}

	public static interface VoidBlockVoidNSURLSessionAuthChallengeDispositionNSURLCredential {
		public void perform(NSURLSessionDelegate.NSURLSessionAuthChallengeDisposition disposition,
							NSURLCredential credential);
	}

	public static interface VoidBlockNSInputStream {
		public void perform(NSInputStream bodyStream);
	}

	public static interface VoidBlockNSURLSessionAuthChallengeDispositionNSURLCredential {
		public void perform(NSURLSessionDelegate.NSURLSessionAuthChallengeDisposition disposition,
							NSURLCredential credential);
	}

	public static interface VoidBlockNSData {
		public void perform(NSData resumeData);
	}

	public static interface VoidBlockNSURLNSURLResponseNSError {
		public void perform(NSURL location, NSURLResponse response, NSError error);
	}

	public static interface VoidBlockNSDataNSURLResponseNSError {
		public void perform(NSData data, NSURLResponse response, NSError error);
	}

	public static interface VoidBlockNSArrayNSArrayNSArray {
		public void perform(NSArray dataTasks, NSArray uploadTasks, NSArray downloadTasks);
	}

	public static interface VoidBlockNSTextCheckingResultNSMatchingFlagsBOOL {
		public void perform(NSTextCheckingResult result, NSRegularExpression.NSMatchingFlags flags,
							boolean stop);
	}

	public static interface BOOLBlockNSURLNSError {

		public boolean perform(NSURL url, NSError nsError);
	}

	public static interface IdBlockIdNSArrayNSMutableDictionary {
		public NSObject perform(NSObject evaluatedObject, NSArray<?> expressions,
								NSMutableDictionary<?, ?> context);
	}

	public static interface VoidBlockNSUIntegerBOOL {
		public boolean perform(Object obj, int idx, boolean[] stop);
	}

	public static interface BOOLBlockNSUIntegerBOOL {
		public boolean perform(Object obj, int idx, boolean[] stop);
	}

	public static interface VoidBlockNSRangeBOOL {
		public boolean perform(NSRange range, boolean[] stop);
	}

	public static interface VoidBlockVoidNSRangeBOOL {
		public void perform(byte[] bytes, NSRange byteRange, boolean stop);
	}

	public static interface VoidBlock {
		public void perform();
	}

	public static interface VoidBlockNsarrayNserror {
		public void perform(NSArray placemark, NSError error);
	}

	public static interface VoidBlockNsdataNserror {
		public void perform(NSData data, NSError error);
	}

}
