package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCopying;

public class NSURLSessionDataTask extends NSURLSessionTask implements NSCopying {

	public NSURLSessionDataTask() {
		super();
	}

	public NSURLSessionDataTask(NSURL url) {
		super(url);
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public void cancel() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void suspend() {

	}

}
