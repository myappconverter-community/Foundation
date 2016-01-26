
package com.myappconverter.java.foundations;




public class NSCompoundPredicate extends NSPredicate {

	private NSCompoundPredicateType _compoundPredicateType;
	private NSArray _subpredicates;

	
	public static enum NSCompoundPredicateType {
		NSNotPredicateType, //
		NSAndPredicateType, //
		NSOrPredicateType
	};

	// Constructors
	/**
	 * @Signature: andPredicateWithSubpredicates:
	 * @Declaration : + (NSPredicate *)andPredicateWithSubpredicates:(NSArray *)subpredicates
	 * @Description : Returns a new predicate formed by AND-ing the predicates in a given array.
	 * @param subpredicates An array of NSPredicate objects.
	 * @return Return Value A new predicate formed by AND-ing the predicates specified by subpredicates.
	 * @Discussion An AND predicate with no subpredicates evaluates to TRUE.
	 **/
	
	public static NSPredicate andPredicateWithSubpredicates(NSArray predicates) {
		NSCompoundPredicate myPredicate = new NSCompoundPredicate();
		return (NSPredicate) myPredicate
				.initWithTypeSubpredicates(NSCompoundPredicateType.NSAndPredicateType, predicates);
	}

	/**
	 * @Signature: notPredicateWithSubpredicate:
	 * @Declaration : + (NSPredicate *)notPredicateWithSubpredicate:(NSPredicate *)predicate
	 * @Description : Returns a new predicate formed by NOT-ing a given predicate.
	 * @param predicate A predicate.
	 * @return Return Value A new predicate formed by NOT-ing the predicate specified by predicate.
	 **/
	
	public static NSPredicate notPredicateWithSubpredicate(NSPredicate predicate) {
		NSCompoundPredicate myPredicate = new NSCompoundPredicate();
		return (NSPredicate) myPredicate.initWithTypeSubpredicates(
				NSCompoundPredicateType.NSNotPredicateType,
				NSArray.arrayWithObject(NSArray.class, predicate));

	}

	/**
	 * @Signature: orPredicateWithSubpredicates:
	 * @Declaration : + (NSPredicate *)orPredicateWithSubpredicates:(NSArray *)subpredicates
	 * @Description : Returns a new predicate formed by OR-ing the predicates in a given array.
	 * @param subpredicates An array of NSPredicate objects.
	 * @return Return Value A new predicate formed by OR-ing the predicates specified by subpredicates.
	 * @Discussion An OR predicate with no subpredicates evaluates to FALSE.
	 **/
	
	public static NSPredicate orPredicateWithSubpredicates(NSArray subpredicates) {
		NSCompoundPredicate myPredicate = new NSCompoundPredicate();
		return (NSPredicate) myPredicate.initWithTypeSubpredicates(
				NSCompoundPredicateType.NSOrPredicateType, subpredicates);
	}

	/**
	 * @Signature: initWithType:subpredicates:
	 * @Declaration : - (id)initWithType:(NSCompoundPredicateType)type subpredicates:(NSArray *)subpredicates
	 * @Description : Returns the receiver initialized to a given type using predicates from a given array.
	 * @param type The type of the new predicate.
	 * @param subpredicates An array of NSPredicate objects.
	 * @return Return Value The receiver initialized with its type set to type and subpredicates array to subpredicates.
	 **/
	
	public Object initWithTypeSubpredicates(NSCompoundPredicateType type, NSArray predicates) {
		_compoundPredicateType = type;
		_subpredicates = predicates;
		return this;
	}

	// Getting Information About a Compound Predicate
	/**
	 * @Signature: compoundPredicateType
	 * @Declaration : - (NSCompoundPredicateType)compoundPredicateType
	 * @Description : Returns the predicate type for the receiver.
	 * @return Return Value The predicate type for the receiver.
	 **/
	
	public NSCompoundPredicateType compoundPredicateType() {
		return _compoundPredicateType;
	}

	public NSCompoundPredicateType getCompoundPredicateType() {
		return _compoundPredicateType;
	}

	/**
	 * @Signature: subpredicates
	 * @Declaration : - (NSArray *)subpredicates
	 * @Description : Returns the array of the receiver’s subpredicates.
	 * @return Return Value The array of the receiver’s subpredicates.
	 **/
	
	public NSArray subpredicates() {
		return _subpredicates;
	}

	public NSArray getSubpredicates() {
		return _subpredicates;
	}

	@Override
	public NSString predicateFormat() {
		NSMutableString result = new NSMutableString();
		NSMutableArray args = new NSMutableArray();

		int i;
		int count = _subpredicates.count();

		for (i = 0; i < count; i++) {
			NSPredicate check = (NSPredicate) _subpredicates.objectAtIndex(i);
			NSString precedence = check.predicateFormat();

			if (check instanceof NSCompoundPredicate && ((NSCompoundPredicate) check)
					.compoundPredicateType() != _compoundPredicateType) {
				precedence.wrappedString = "(" + precedence.wrappedString + ")";
			}

			args.addObject(precedence);
		}

		switch (_compoundPredicateType) {
		case NSNotPredicateType:
			result.wrappedString = result.wrappedString + "NOT " + args.objectAtIndex(i);
			break;

		case NSAndPredicateType:
			result.wrappedString = result.wrappedString + " " + args.objectAtIndex(0) + " AND "
					+ args.objectAtIndex(1);
			break;

		case NSOrPredicateType:
			result.wrappedString = result.wrappedString + " " + args.objectAtIndex(0) + " OR "
					+ args.objectAtIndex(1);
			break;

		default:
			result.wrappedString = result.wrappedString + "NOT " + args.objectAtIndex(i);
			break;
		}

		return result;
	}

	public boolean evaluateWithObject(NSObject object) {
		boolean result = false;
		int i, count = _subpredicates.count();

		for (i = 0; i < count; i++) {
			NSPredicate predicate = (NSPredicate) _subpredicates.objectAtIndex(i);
			switch (_compoundPredicateType) {
			case NSNotPredicateType:
				return !predicate.evaluateWithObject(object);
			case NSAndPredicateType:
				result = setResult(i, object);
			case NSOrPredicateType:
				if (predicate.evaluateWithObject(object))
					return true;
				break;
			}
		}

		return result;
	}

	public boolean setResult(int index, NSObject object) {
		boolean result = false;
		NSPredicate predicate = (NSPredicate) _subpredicates.objectAtIndex(index);
		if (index == 0)
			result = predicate.evaluateWithObject(object);
		else
			result = result && predicate.evaluateWithObject(object);

		return result;
	}
}