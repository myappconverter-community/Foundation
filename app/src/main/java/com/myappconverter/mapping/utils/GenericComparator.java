package com.myappconverter.mapping.utils;

import com.myappconverter.java.foundations.NSCalendar;
import com.myappconverter.java.foundations.NSDate;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSMutableString;
import com.myappconverter.java.foundations.NSNumber;
import com.myappconverter.java.foundations.NSObjCRuntime;
import com.myappconverter.java.foundations.NSObject;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.SEL;
import com.myappconverter.java.foundations.constants.NSComparator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;

public final class GenericComparator implements Comparator, Serializable {

	private static final long serialVersionUID = -2293914106471884607L;

	private static final int LESSER = -1;
	private static final int EQUAL = 0;
	private static final int GREATER = 1;
	private static final String METHOD_GET_PREFIX = "get";
	private static final String DATATYPE_STRING = "java.lang.String";
	private static final String DATATYPE_DATE = "java.util.Date";
	private static final String DATATYPE_INTEGER = "java.lang.Integer";
	private static final String DATATYPE_LONG = "java.lang.Long";
	private static final String DATATYPE_FLOAT = "java.lang.Float";
	private static final String DATATYPE_DOUBLE = "java.lang.Double";
	private static final String DATATYPE_INT = "int";
	private static final String DATATYPE_fLOAT = "float";
	private static final String DATATYPE_dOUBLE = "double";
	private static final String DATATYPE_lONG = "long";
	private static final String DATATYPE_sTRING = "string";

	private enum CompareMode {
		EQUAL, LESS_THAN, GREATER_THAN, DEFAULT
	}

	// generic comparator attributes
	private String targetMethod;// get of the property
	private boolean sortAscending = true;
	private String sortingFiled;// using for sorting the dictionary
	private SEL selector;
	private NSComparator nsComparator;

	public GenericComparator(boolean sortAscending) {
		super();
		this.targetMethod = null;
		this.sortAscending = sortAscending;
	}

	public GenericComparator(String sortField) {
		super();
		this.sortingFiled = sortField;
		this.targetMethod = prepareTargetMethod(sortField);
		this.sortAscending = true;
	}

	public GenericComparator(String sortField, boolean sortAscending) {
		super();
		this.sortingFiled = sortField;
		this.targetMethod = prepareTargetMethod(sortField);
		this.sortAscending = sortAscending;
	}

	public GenericComparator(String sortField, boolean sortAscending, SEL selector) {
		super();
		this.sortingFiled = sortField;
		this.targetMethod = prepareTargetMethod(sortField);
		this.sortAscending = sortAscending;
		this.selector = selector;
	}

	public GenericComparator(String sortField, boolean sortAscending, NSComparator comparator) {
		super();
		this.sortingFiled = sortField;
		this.targetMethod = prepareTargetMethod(sortField);
		this.sortAscending = sortAscending;
		this.nsComparator = comparator;
	}

	@Override
	public int compare(Object o1, Object o2) {
		int response = LESSER;
		if (o1 instanceof NSDictionary && o2 instanceof NSDictionary) {
			NSDictionary dic1 = (NSDictionary) o1;
			NSDictionary dic2 = (NSDictionary) o2;
			Object obj1 = (NSObject) dic1.objectForKey(new NSString(this.sortingFiled));
			Object obj2 = (NSObject) dic2.objectForKey(new NSString(this.sortingFiled));
			if ((this.selector != null && !selector.getMethodName().equals("compare")) || nsComparator != null) {
				return compareObjectsWithSelectorOrNSComparator(obj1, obj2);
			}
			return compareObjects(obj1, obj2);
		}
		Object objLhs = null, objRhs = null;
		try {
			objLhs = (null == this.targetMethod) ? o1
					: o1.getClass().getMethod(targetMethod).invoke(o1);// getValue(o1);
			objRhs = (null == this.targetMethod) ? o2
					: o2.getClass().getMethod(targetMethod).invoke(o2);// getValue(o2);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		if ((this.selector != null && !selector.getMethodName().equals("compare")) || nsComparator != null) {
			NSObjCRuntime.NSComparisonResult indice = NSObjCRuntime.NSComparisonResult.NSOrderedSame;
			if (objLhs != null && objRhs != null) {
				return compareObjectsWithSelectorOrNSComparator(objLhs, objRhs);
			}
		}
			response = compareObjects(objLhs, objRhs);
		return response;
	}

	private int compareObjects(Object obj1, Object obj2){
		CompareMode cm = findCompareMode(obj1, obj2);
		if (!cm.equals(CompareMode.DEFAULT)) {
			return compareAlternate(cm);
		}
		return compareActual(obj1, obj2);
	}

	private int compareObjectsWithSelectorOrNSComparator(Object obj1, Object obj2){
		if(selector != null){
			Method m = InvokableHelper.getMethod(obj1, selector.getMethodName(),
					obj2);
			if (m != null) {
				try {
					NSObjCRuntime.NSComparisonResult indice = (NSObjCRuntime.NSComparisonResult) m.invoke(obj1, obj2);
					if (indice == NSObjCRuntime.NSComparisonResult.NSOrderedSame) {
						return 0;
					} else if (indice == NSObjCRuntime.NSComparisonResult.NSOrderedDescending) {
						return 1 * determinePosition();
					} else {
						return -1 * determinePosition();
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		else if(nsComparator != null){
			return nsComparator.compare(obj1, obj2) * determinePosition();
		}
		return 0;
	}

	private int compareAlternate(CompareMode cm) {
		int compareState = LESSER;
		switch (cm) {
		case LESS_THAN:
			compareState = LESSER * determinePosition();
			break;
		case GREATER_THAN:
			compareState = GREATER * determinePosition();
			break;
		case EQUAL:
			compareState = EQUAL * determinePosition();
			break;

		}
		return compareState;
	}

	private int compareActual(Object v1, Object v2) {
		if (!v1.getClass().getSimpleName().equals(v2.getClass().getSimpleName()))
			return 0;
		switch (v1.getClass().getSimpleName()) {
		case "NSString":
			return ((NSString) v1).getWrappedString().compareTo(((NSString) v2).getWrappedString())
					* determinePosition();
		case "NSMutableString":
			return ((NSMutableString) v1).getWrappedString()
					.compareTo(((NSMutableString) v2).getWrappedString()) * determinePosition();
		case "NSDate":
			return ((NSDate) v1).getWrappedDate().compareTo(((NSDate) v2).getWrappedDate())
					* determinePosition();
		case "NSCalendar":
			return ((NSCalendar) v1).getWrappedCalendar()
					.compareTo(((NSCalendar) v2).getWrappedCalendar()) * determinePosition();
		case "NSValue":
			return 0;
		case "NSNumber":
			return ((NSNumber) v1).compareTo(((NSNumber) v2)) * determinePosition();
		}
		return compareNumber((Number) v1, (Number) v2);
	}

	private int compareNumber(Number n1, Number n2) {
		int coef = 1;
		if (n1.intValue() < 0 && n2.intValue() < 0)
			coef = -1;
		if (n1.intValue() < 0)
			if (n2.intValue() >= 0)
				return -1;
		if (n2.intValue() < 0)
			if (n1.intValue() >= 0)
				return 1;
		if (n1.intValue() < n2.intValue())
			return -1 * coef * determinePosition();
		if (n1.intValue() > n2.intValue())
			return coef * determinePosition();
		// the number is not a int type
		// the part of is some ...
		String type1 = n1.getClass().getName();
		String type2 = n2.getClass().getName();
		if ((type1.equals(DATATYPE_INTEGER) || type1.equals(DATATYPE_INT)
				|| type1.equals(DATATYPE_lONG) || type1.equals(DATATYPE_LONG))) {
			if ((type2.equals(DATATYPE_INTEGER) || type2.equals(DATATYPE_INT)
					|| type2.equals(DATATYPE_lONG) || type2.equals(DATATYPE_LONG)))
				return 0;
			else
				return 1 * coef * determinePosition();
		}
		if ((type2.equals(DATATYPE_INTEGER) || type2.equals(DATATYPE_INT)
				|| type2.equals(DATATYPE_lONG) || type2.equals(DATATYPE_LONG))) {
			return -1 * coef * determinePosition();
		}
		Double d1 = (double) n1;
		Double d2 = (double) n2;
		return (d1).compareTo((d2)) * determinePosition();

	}

	private int compareActual(Object v1, Object v2, String returnType) {
		int acutal = LESSER;
		if (returnType.equals(DATATYPE_INTEGER) || returnType.equals("")) {
			acutal = (((Integer) v1).compareTo((Integer) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_LONG)) {
			acutal = (((Long) v1).compareTo((Long) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_STRING)) {
			acutal = (((String) v1).compareTo((String) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_DATE)) {
			acutal = (((Date) v1).compareTo((Date) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_FLOAT)) {
			acutal = (((Float) v1).compareTo((Float) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_DOUBLE)) {
			acutal = (((Double) v1).compareTo((Double) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_INT)) {
			acutal = (((Integer) v1).compareTo((Integer) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_dOUBLE)) {
			acutal = (((Double) v1).compareTo((Double) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_fLOAT)) {
			acutal = (((Float) v1).compareTo((Float) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_sTRING)) {
			acutal = (((String) v1).compareTo((String) v2) * determinePosition());
		} else if (returnType.equals(DATATYPE_lONG)) {
			acutal = (((Long) v1).compareTo((Long) v2) * determinePosition());
		} else {
			NSObject obj1 = (NSObject) v1;
			NSObject obj2 = (NSObject) v2;
			acutal = obj1.compareTo(obj2);
		}

		return acutal;
	}

	/**
	 * preparing target name of getter method for given sort field
	 * 
	 * @param name a {@link String}
	 * @return methodName a {@link String}
	 */
	private final static String prepareTargetMethod(String name) {
		StringBuffer fieldName = new StringBuffer(METHOD_GET_PREFIX);
		fieldName.append(name.substring(0, 1).toUpperCase());
		fieldName.append(name.substring(1));
		return fieldName.toString();
	}

	/**
	 * fetching method from <code>Class</code> object through reflect
	 *
	 * @param obj - a {@link Object} - input object
	 * @return method - a {@link Method}
	 * @throws NoSuchMethodException
	 */
	// private final Method getMethod(Object obj) throws NoSuchMethodException {
	// return obj.getClass().getMethod(targetMethod);
	// }
	//
	// /**
	// * dynamically invoking given method with given object through reflect
	// *
	// * @param method - a {@link java.lang.reflect.Method}
	// * @param obj - a {@link java.lang.Object}
	// * @return object - a {@link java.lang.Object} - return of given method
	// * @throws InvocationTargetException
	// * @throws IllegalAccessException
	// */
	// private final static Object invoke(Method method, Object obj) throws InvocationTargetException, IllegalAccessException {
	// return method.invoke(obj);
	// }
	//
	// /**
	// * fetching a value from given object
	// *
	// * @param obj - a {@link java.lang.Object}
	// * @return object - a {@link java.lang.Object} - return of given method
	// * @throws InvocationTargetException
	// * @throws IllegalAccessException
	// * @throws NoSuchMethodException
	// */
	// private Object getValue(Object obj) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
	// return invoke(getMethod(obj), obj);
	// }

	/**
	 * identifying the comparison mode for given value
	 *
	 * @param o1 - a {@link Object}
	 * @param o2 - a {@link Object}
	 * @return compareMode - a {@link com.myjeeva.comparator.GenericComparator.CompareMode}
	 */
	private CompareMode findCompareMode(Object o1, Object o2) {
		CompareMode cm = CompareMode.LESS_THAN;

		if (null != o1 & null != o2) {
			cm = CompareMode.DEFAULT;
		} else if (null == o1 & null != o2) {
			cm = CompareMode.LESS_THAN;
		} else if (null != o1 & null == o2) {
			cm = CompareMode.GREATER_THAN;
		} else if (null == o1 & null == o2) {
			cm = CompareMode.EQUAL;
		}

		return cm;
	}

	/**
	 * Determining positing for sorting
	 * 
	 * @return -1 to change the sort order if appropriate.
	 */
	private int determinePosition() {
		return sortAscending ? GREATER : LESSER;
	}
}
