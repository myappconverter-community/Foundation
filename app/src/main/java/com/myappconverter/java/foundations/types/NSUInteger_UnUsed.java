package com.myappconverter.java.foundations.types;

import java.math.BigDecimal;

public class NSUInteger_UnUsed extends BigDecimal {

	private static final long serialVersionUID = 5773424805639683160L;

	public NSUInteger_UnUsed(int arg) {
		super(arg);
	}

	public NSUInteger_UnUsed(long arg) {
		super(arg);
	}

	public NSUInteger_UnUsed() {
		super(0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intValue();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);

	};

	@Override
	public String toString() {
		return "[" + toString() + "]";
	}

}
