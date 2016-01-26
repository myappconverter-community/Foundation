
package com.myappconverter.java.foundations;




public class NSUndoManager extends NSObject {

	public NSUndoManager() {
		super();
		_redoStack = new NSMutableArray<NSUndoManager.PrivateUndoGroup>();
		_undoStack = new NSMutableArray<NSUndoManager.PrivateUndoGroup>();
		_groupsByEvent = true;
	}

	// Registering Undo Operations

	/**
	 * @Signature: registerUndoWithTarget:selector:object:
	 * @Declaration : - (void)registerUndoWithTarget:(id)target selector:(SEL)aSelector object:(id)anObject
	 * @Description : Records a single undo operation for a given target, so that when an undo is performed it is sent a specified selector
	 *              with a given object as the sole argument.
	 * @param target The target of the undo operation.
	 * @param aSelector The selector for the undo operation.
	 * @param anObject The argument sent with the selector.
	 * @Discussion Also clears the redo stack. Does not retain target, but does retain anObject. See â€œRegistering Undo Operationsâ€? for more
	 *             information. Raises an NSInternalInconsistencyException if invoked when no undo group has been established using
	 *             beginUndoGrouping. Undo groups are normally set by default, so you should rarely need to begin a top-level undo group
	 *             explicitly.
	 **/

	public void registerUndoWithTargetSelectorObject(Object target, SEL aSelector, Object anObject) {
		if (_disableCount == 0) {
			NSMethodSignature sig;
			NSInvocation inv;
			PrivateUndoGroup g;

			if (_group == null) {
				if (this.groupsByEvent()) {
					this._begin();
				} else {
					NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
							"registerUndo without beginUndoGrouping"));
				}
			}
			g = (PrivateUndoGroup) _group;
			sig = new NSMethodSignature(target.getClass().getName(), "funTest");
			inv = NSInvocation.invocationWithMethodSignature(sig);
			inv.setTarget(target);
			inv.setSelector(aSelector);
			inv.setArgumentAtIndex(anObject, Integer.valueOf(2));
			g.addInvocation(inv);
			if (_isUndoing == false && _isRedoing == false) {
				_redoStack.removeAllObjects();
			}
			if ((_runLoopGroupingPending == false) && (this.groupsByEvent() == true)) {
				this._loop(null);
				_runLoopGroupingPending = true;

			}
		}

	}

	/**
	 * @Signature: prepareWithInvocationTarget:
	 * @Declaration : - (id)prepareWithInvocationTarget:(id)target
	 * @Description : Prepares the receiver for invocation-based undo with the given target as the subject of the next undo operation and
	 *              returns self.
	 * @param target The target of the undo operation. The undo manager maintains a weak reference to the target.
	 * @return Return Value self.
	 * @Discussion See â€œRegistering Undo Operationsâ€? for more information.
	 **/

	public Object prepareWithInvocationTarget(NSObject target) {
		_nextTarget = target;
		return this;
	}

	// Checking Undo Ability
	/**
	 * @Signature: canUndo
	 * @Declaration : - (BOOL)canUndo
	 * @Description : Returns a Boolean value that indicates whether the receiver has any actions to undo.
	 * @return Return Value YES if the receiver has any actions to undo, otherwise NO.
	 * @Discussion The return value does not mean you can safely invoke undo or undoNestedGroupâ€”you may have to close open undo groups
	 *             first.
	 **/

	public boolean canUndo() {
		if (_undoStack.count() > 0) {
			return true;
		}
		PrivateUndoGroup gr = (PrivateUndoGroup) _group;
		if (_group != null && gr.actions.count() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @Signature: canRedo
	 * @Declaration : - (BOOL)canRedo
	 * @Description : Returns a Boolean value that indicates whether the receiver has any actions to redo.
	 * @return Return Value YES if the receiver has any actions to redo, otherwise NO.
	 * @Discussion Because any undo operation registered clears the redo stack, this method posts an NSUndoManagerCheckpointNotification to
	 *             allow clients to apply their pending operations before testing the redo stack.
	 **/

	public boolean canRedo() {
		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerCheckpointNotification"), this);
		if (_redoStack.count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Performing Undo and Redo

	/**
	 * @Signature: undo
	 * @Declaration : - (void)undo
	 * @Description : Closes the top-level undo group if necessary and invokes undoNestedGroup.
	 * @Discussion This method also invokes endUndoGrouping if the nesting level is 1. Raises an NSInternalInconsistencyException if more
	 *             than one undo group is open (that is, if the last group isnâ€™t at the top level). This method posts an
	 *             NSUndoManagerCheckpointNotification.
	 **/

	public void undo() {
		if (groupingLevel() == 1) {
			this.endUndoGrouping();
		}
		if (_group != null) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString("undo with nested groups"));
		}
		this.undoNestedGroup();
	}

	/**
	 * @Signature: undoNestedGroup
	 * @Declaration : - (void)undoNestedGroup
	 * @Description : Performs the undo operations in the last undo group (whether top-level or nested), recording the operations on the
	 *              redo stack as a single group.
	 * @Discussion Raises an NSInternalInconsistencyException if any undo operations have been registered since the last
	 *             enableUndoRegistration message. This method posts an NSUndoManagerCheckpointNotification and
	 *             NSUndoManagerWillUndoChangeNotification before it performs the undo operation, and it posts an
	 *             NSUndoManagerDidUndoChangeNotification after it performs the undo operation.
	 **/

	public void undoNestedGroup() {

		NSString name = null;
		PrivateUndoGroup oldGroup;
		PrivateUndoGroup groupToUndo;
		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerCheckpointNotification"), this);

		if (_group != null) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"),
					new NSString("undoNestedGroup before endUndoGrouping"));
		}

		if (_isUndoing || _isRedoing) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
					"undoNestedGroup while undoing or redoing"));
		}

		if (_undoStack.count() == 0) {
			return;
		}

		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerWillUndoChangeNotification"), this);

		oldGroup = (PrivateUndoGroup) _group;
		_group = null;
		_isUndoing = true;

		if (oldGroup != null) {
			groupToUndo = oldGroup;
			oldGroup = oldGroup.parent;
			groupToUndo.orphan();
			_redoStack.addObject(groupToUndo);
		} else {
			int index = _undoStack.count() - 1;
			groupToUndo = _undoStack.objectAtIndex(index);
			_undoStack.removeLastObject();
		}

		name = new NSString(groupToUndo.actionName.getWrappedString());

		this._begin();
		groupToUndo.perform();
		this.endUndoGrouping();

		_isUndoing = false;
		_group = oldGroup;
		int index = _redoStack.count() - 1;
		PrivateUndoGroup lastObject = _redoStack.objectAtIndex(index);
		lastObject.actionName = name;

		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerDidUndoChangeNotification"), this);
	}

	/**
	 * @Signature: redo
	 * @Declaration : - (void)redo
	 * @Description : Performs the operations in the last group on the redo stack, if there are any, recording them on the undo stack as a
	 *              single group.
	 * @Discussion Raises an NSInternalInconsistencyException if the method is invoked during an undo operation. This method posts an
	 *             NSUndoManagerCheckpointNotification and NSUndoManagerWillRedoChangeNotification before it performs the redo operation,
	 *             and it posts the NSUndoManagerDidRedoChangeNotification after it performs the redo operation.
	 **/

	public void redo() {
		NSString name;

		if (_isUndoing || _isRedoing) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString("redo while undoing or redoing"));
		}
		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerCheckpointNotification"), this);

		if (_redoStack.count() > 0) {
			PrivateUndoGroup oldGroup;
			PrivateUndoGroup groupToRedo;

			NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerWillRedoChangeNotification"), this);
			int index = _redoStack.count() - 1;
			groupToRedo = _redoStack.objectAtIndex(index);
			_redoStack.removeLastObject();

			name = new NSString(groupToRedo.actionName.getWrappedString());

			oldGroup = (PrivateUndoGroup) _group;
			_group = null;
			_isRedoing = true;

			this._begin();
			groupToRedo.perform();
			this.endUndoGrouping();

			_isRedoing = false;
			_group = oldGroup;
			index = _undoStack.count() - 1;
			PrivateUndoGroup pv = _undoStack.objectAtIndex(index);
			pv.actionName = name;

			NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerDidRedoChangeNotification"), this);
		}

	}

	// Limiting the Undo Stack
	/**
	 * @Signature: setLevelsOfUndo:
	 * @Declaration : - (void)setLevelsOfUndo:(NSUInteger)anInt
	 * @Description : Sets the maximum number of top-level undo groups the receiver holds.
	 * @param anInt The maximum number of undo groups. A limit of 0 indicates no limit, so that old undo groups are never dropped.
	 * @Discussion When ending an undo group results in the number of groups exceeding this limit, the oldest groups are dropped from the
	 *             stack. The default is 0. If invoked with a limit below the prior limit, old undo groups are immediately dropped.
	 **/

	public void setLevelsOfUndo(int anInt) {
		_levelsOfUndo = anInt;
		if (anInt > 0) {
			while (_undoStack.count() > anInt) {
				_undoStack.removeObjectAtIndex(0);
			}
			while (_redoStack.count() > anInt) {
				_redoStack.removeObjectAtIndex(0);
			}
		}
	}

	/**
	 * @Signature: levelsOfUndo
	 * @Declaration : - (NSUInteger)levelsOfUndo
	 * @Description : Returns the maximum number of top-level undo groups the receiver holds.
	 * @return Return Value An integer specifying the number of undo groups. A limit of 0 indicates no limit, so old undo groups are never
	 *         dropped.
	 * @Discussion When ending an undo group results in the number of groups exceeding this limit, the oldest groups are dropped from the
	 *             stack. The default is 0.
	 **/

	public int levelsOfUndo() {
		return _levelsOfUndo;
	}

	// Creating Undo Groups
	/**
	 * @Signature: beginUndoGrouping
	 * @Declaration : - (void)beginUndoGrouping
	 * @Description : Marks the beginning of an undo group.
	 * @Discussion All individual undo operations before a subsequent endUndoGrouping message are grouped together and reversed by a later
	 *             undo message. By default undo groups are begun automatically at the start of the event loop, but you can begin your own
	 *             undo groups with this method, and nest them within other groups. This method posts an NSUndoManagerCheckpointNotification
	 *             unless a top-level undo is in progress. It posts an NSUndoManagerDidOpenUndoGroupNotification if a new group was
	 *             successfully created.
	 **/

	public void beginUndoGrouping() {

		if (_group == null && this.groupsByEvent()) {
			this._begin();
		}

		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerCheckpointNotification"), this);
		this._begin();
	}

	/**
	 * @Signature: endUndoGrouping
	 * @Declaration : - (void)endUndoGrouping
	 * @Description : Marks the end of an undo group.
	 * @Discussion All individual undo operations back to the matching beginUndoGrouping message are grouped together and reversed by a
	 *             later undo or undoNestedGroup message. Undo groups can be nested, thus providing functionality similar to nested
	 *             transactions. Raises an NSInternalInconsistencyException if thereâ€™s no beginUndoGrouping message in effect. This method
	 *             posts an NSUndoManagerCheckpointNotification and an NSUndoManagerDidCloseUndoGroupNotification just before the group is
	 *             closed.
	 **/

	public void endUndoGrouping() {

		PrivateUndoGroup g;
		PrivateUndoGroup p;

		if (_group == null) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
					"endUndoGrouping without beginUndoGrouping"));
		}
		NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerCheckpointNotification"), this);

		if (_isUndoing == false && _isRedoing == false)
			NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerWillCloseUndoGroupNotification"),
					this);

		g = (PrivateUndoGroup) _group;
		p = g.parent;
		_group = p;
		g.orphan();
		if (p == null) {
			if (_isUndoing) {
				if (_levelsOfUndo > 0 && _redoStack.count() == _levelsOfUndo && g.actions.count() > 0) {
					_redoStack.removeObjectAtIndex(0);
				}

				if (g != null && g.actions != null) {
					if (g.actions.count() > 0) // FIXME
						_redoStack.addObject(g);
				}
			} else {
				if (_levelsOfUndo > 0 && _undoStack.count() == _levelsOfUndo && g.actions.count() > 0) {
					_undoStack.removeObjectAtIndex(0);
				}

				if (g != null && g.actions != null) {
					if (g.actions.count() > 0) // FIXME
						_undoStack.addObject(g);
				}
			}
		} else if (g.actions != null) {
			NSArray<NSInvocation> a = g.actions;
			int i;
			for (i = 0; i < a.count(); i++) {
				p.addInvocation(a.objectAtIndex(i));
			}
		}
	}

	@Override
	public void forwardInvocation(NSInvocation anInvocation) {
		if (_disableCount == 0 && _nextTarget == null) {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
					"forwardInvocation without perparation"));
			if (_group == null) {
				if (this.groupsByEvent()) {
					this._begin();
				} else {
					NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
							"forwardInvocation without beginUndoGrouping"));
				}
			}
			anInvocation.retainArguments();
			anInvocation.setTarget(_nextTarget);
			_nextTarget = null;
			((PrivateUndoGroup) _group).addInvocation(anInvocation);
			if (_isUndoing == false && _isRedoing == false && ((PrivateUndoGroup) _group).actions.count() > 0) {
				_redoStack.removeAllObjects();
			}
			if ((_runLoopGroupingPending == false) && (this.groupsByEvent() == true)) {
				this._loop(this);
				// _runLoopGroupingPending = true;
			}
		}
	}

	/**
	 * @Signature: groupsByEvent
	 * @Declaration : - (BOOL)groupsByEvent
	 * @Description : Returns a Boolean value that indicates whether the receiver automatically creates undo groups around each pass of the
	 *              run loop.
	 * @return Return Value YES if the receiver automatically creates undo groups around each pass of the run loop, otherwise NO.
	 * @Discussion The default is YES.
	 **/

	public boolean groupsByEvent() {
		return _groupsByEvent;
	}

	/**
	 * @Signature: setGroupsByEvent:
	 * @Declaration : - (void)setGroupsByEvent:(BOOL)flag
	 * @Description : Sets a Boolean value that specifies whether the receiver automatically groups undo operations during the run loop.
	 * @param flag If YES, the receiver creates undo groups around each pass through the run loop; if NO it doesnâ€™t.
	 * @Discussion The default is YES. If you turn automatic grouping off, you must close groups explicitly before invoking either undo or
	 *             undoNestedGroup.
	 **/

	public void setGroupsByEvent(Boolean flag) {
		if (_groupsByEvent != flag) {
			_groupsByEvent = flag;
		}
	}

	/**
	 * @Signature: groupingLevel
	 * @Declaration : - (NSInteger)groupingLevel
	 * @Description : Returns the number of nested undo groups (or redo groups, if Redo was invoked last) in the current event loop.
	 * @return Return Value An integer indicating the number of nested groups. If 0 is returned, there is no open undo or redo group.
	 **/

	public int groupingLevel() {
		PrivateUndoGroup g = (PrivateUndoGroup) _group;
		int level = 0;

		while (g != null) {
			level++;
			g = g.parent;
		}
		return level;
	}

	// Enabling and Disabling Undo

	/**
	 * @Signature: disableUndoRegistration
	 * @Declaration : - (void)disableUndoRegistration
	 * @Description : Disables the recording of undo operations, whether by registerUndoWithTarget:selector:object: or by invocation-based
	 *              undo.
	 * @Discussion This method can be invoked multiple times by multiple clients. The enableUndoRegistration method must be invoked an equal
	 *             number of times to re-enable undo registration.
	 **/

	public void disableUndoRegistration() {
		_disableCount++;
	}

	/**
	 * @Signature: enableUndoRegistration
	 * @Declaration : - (void)enableUndoRegistration
	 * @Description : Enables the recording of undo operations.
	 * @Discussion Because undo registration is enabled by default, it is often used to balance a prior disableUndoRegistration message.
	 *             Undo registration isnâ€™t actually re-enabled until an enable message balances the last disable message in effect. Raises
	 *             an NSInternalInconsistencyException if invoked while no disableUndoRegistration message is in effect.
	 **/

	public void enableUndoRegistration() {
		if (_disableCount > 0) {
			_disableCount--;
		} else {
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"),
					new NSString("enableUndoRegistration without disable"));
		}
	}

	/**
	 * @Signature: isUndoRegistrationEnabled
	 * @Declaration : - (BOOL)isUndoRegistrationEnabled
	 * @Description : Returns a Boolean value that indicates whether the recording of undo operations is enabled.
	 * @return Return Value YES if registration is enabled; otherwise, NO.
	 * @Discussion Undo registration is enabled by default.
	 **/

	public boolean isUndoRegistrationEnabled() {
		if (_disableCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Checking Whether Undo or Redo Is Being Performed
	/**
	 * @Signature: isUndoing
	 * @Declaration : - (BOOL)isUndoing
	 * @Description : Returns a Boolean value that indicates whether the receiver is in the process of performing its undo or
	 *              undoNestedGroup method.
	 * @return Return Value YES if the method is being performed, otherwise NO.
	 **/

	public boolean isUndoing() {
		return _isUndoing;
	}

	/**
	 * @Signature: isRedoing
	 * @Declaration : - (BOOL)isRedoing
	 * @Description : Returns a Boolean value that indicates whether the receiver is in the process of performing its redo method.
	 * @return Return Value YES if the method is being performed, otherwise NO.
	 **/

	public boolean isRedoing() {
		return _isRedoing;
	}

	// Clearing Undo Operations
	/**
	 * @Signature: removeAllActions
	 * @Declaration : - (void)removeAllActions
	 * @Description : Clears the undo and redo stacks and re-enables the receiver.
	 **/

	public void removeAllActions() {
		while (_group != null) {
			this.endUndoGrouping();
		}
		_redoStack.removeAllObjects();
		_undoStack.removeAllObjects();
		_isRedoing = false;
		_isUndoing = false;
		_disableCount = 0;
	}

	/**
	 * @Signature: removeAllActionsWithTarget:
	 * @Declaration : - (void)removeAllActionsWithTarget:(id)target
	 * @Description : Clears the undo and redo stacks of all operations involving the specified target as the recipient of the undo message.
	 * @param target The recipient of the undo messages to be removed.
	 * @Discussion Doesnâ€™t re-enable the receiver if itâ€™s disabled.
	 **/

	public void removeAllActionsWithTarget(NSObject target) {
		int i;

		i = _redoStack.count();
		while (i-- > 0) {
			PrivateUndoGroup g;
			g = _redoStack.objectAtIndex(i);
			if (g.removeActionsForTarget(target) == false) {
				_redoStack.removeObjectAtIndex(i);
			}
		}
		i = _undoStack.count();
		while (i-- > 0) {
			PrivateUndoGroup g;
			g = _undoStack.objectAtIndex(i);
			if (g.removeActionsForTarget(target) == true) {
				_undoStack.removeObjectAtIndex(i);
			}
		}

	}

	// Managing the Action Name
	/**
	 * @Signature: setActionName:
	 * @Declaration : - (void)setActionName:(NSString *)actionName
	 * @Description : Sets the name of the action associated with the Undo or Redo command.
	 * @param actionName The name of the action.
	 * @Discussion If actionName is an empty string, the action name currently associated with the menu command is removed. There is no
	 *             effect if actionName is nil.
	 **/

	public void setActionName(NSString actionName) {
		if ((actionName != null) && (_group != null)) {
			PrivateUndoGroup gr = (PrivateUndoGroup) _group;
			gr.actionName = actionName;
		}
	}

	/**
	 * @Signature: redoActionName
	 * @Declaration : - (NSString *)redoActionName
	 * @Description : Returns the name identifying the redo action.
	 * @return Return Value The redo action name. Returns an empty string (@"") if no action name has been assigned or if there is nothing
	 *         to redo.
	 * @Discussion For example, if the menu title is â€œRedo Delete,â€? the string returned is â€œDelete.â€?
	 **/

	public NSString redoActionName() {
		if (this.canRedo() == false) {
			return null;
		}
		int index = _redoStack.count() - 1;
		return _redoStack.objectAtIndex(index).actionName;
	}

	/**
	 * @Signature: undoActionName
	 * @Declaration : - (NSString *)undoActionName
	 * @Description : Returns the name identifying the undo action.
	 * @return Return Value The undo action name. Returns an empty string (@"") if no action name has been assigned or if there is nothing
	 *         to undo.
	 * @Discussion For example, if the menu title is â€œUndo Delete,â€? the string returned is â€œDelete.â€?
	 **/

	public NSString undoActionName() {
		if (this.canUndo() == false) {
			return null;
		}

		if (_group != null) {
			return ((PrivateUndoGroup) _group).actionName;
		} else {
			int index = _undoStack.count() - 1;
			return _undoStack.objectAtIndex(index).actionName;
		}

	}

	// Getting and Localizing the Menu Item Title

	/**
	 * @Signature: redoMenuItemTitle
	 * @Declaration : - (NSString *)redoMenuItemTitle
	 * @Description : Returns the complete title of the Redo menu command, for example, â€œRedo Paste.â€?
	 * @return Return Value The menu item title.
	 * @Discussion Returns â€œRedoâ€? if no action name has been assigned or nil if there is nothing to redo.
	 **/

	public NSString redoMenuItemTitle() {
		return this.redoMenuTitleForUndoActionName(this.redoActionName());
	}

	/**
	 * @Signature: undoMenuItemTitle
	 * @Declaration : - (NSString *)undoMenuItemTitle
	 * @Description : Returns the complete title of the Undo menu command, for example, â€œUndo Paste.â€?
	 * @return Return Value The menu item title.
	 * @Discussion Returns â€œUndoâ€? if no action name has been assigned or nil if there is nothing to undo.
	 **/

	public NSString undoMenuItemTitle() {
		return this.undoMenuTitleForUndoActionName(this.undoActionName());
	}

	/**
	 * @Signature: redoMenuTitleForUndoActionName:
	 * @Declaration : - (NSString *)redoMenuTitleForUndoActionName:(NSString *)actionName
	 * @Description : Returns the complete, localized title of the Redo menu command for the action identified by the given name.
	 * @param actionName The name of the undo action.
	 * @return Return Value The localized title of the redo menu item.
	 * @Discussion Override this method if you want to customize the localization behavior. This method is invoked by redoMenuItemTitle.
	 **/

	public NSString redoMenuTitleForUndoActionName(NSString actionName) {
		if (actionName != null) {
			if ("".equalsIgnoreCase(actionName.getWrappedString())) {
				return new NSString("Redo");
			} else {
				return NSString.stringWithFormat(NSString.class, new NSString(actionName.getWrappedString()), "Redo");
			}
		}
		return new NSString("Redo");
	}

	/**
	 * @Signature: undoMenuTitleForUndoActionName:
	 * @Declaration : - (NSString *)undoMenuTitleForUndoActionName:(NSString *)actionName
	 * @Description : Returns the complete, localized title of the Undo menu command for the action identified by the given name.
	 * @param actionName The name of the undo action.
	 * @return Return Value The localized title of the undo menu item.
	 * @Discussion Override this method if you want to customize the localization behavior. This method is invoked by undoMenuItemTitle.
	 **/

	public NSString undoMenuTitleForUndoActionName(NSString actionName) {
		if (actionName != null) {
			if ("".equalsIgnoreCase(actionName.getWrappedString())) {
				return new NSString("Undo");
			} else {
				return NSString.stringWithFormat(NSString.class, new NSString(actionName.getWrappedString()), "Undo");
			}
		}
		return new NSString("Undo");
	}

	// Working with Run Loops

	/**
	 * @Signature: runLoopModes
	 * @Declaration : - (NSArray *)runLoopModes
	 * @Description : Returns the modes governing the types of input handled during a cycle of the run loop.
	 * @return Return Value An array of string constants specifying the current run-loop modes.
	 * @Discussion By default, the sole run-loop mode is NSDefaultRunLoopMode (which excludes data from NSConnection objects).
	 **/

	public NSArray<NSObject> runLoopModes() {
		return _modes;
	}

	/**
	 * @Signature: setRunLoopModes:
	 * @Declaration : - (void)setRunLoopModes:(NSArray *)modes
	 * @Description : Sets the modes that determine the types of input handled during a cycle of the run loop.
	 * @param modes An array of string constants specifying the run-loop modes to set.
	 * @Discussion By default, the sole run-loop mode is NSDefaultRunLoopMode (which excludes data from NSConnection objects). With this
	 *             method, you could limit the input to data received during a mouse-tracking session by setting the mode to
	 *             NSEventTrackingRunLoopMode, or you could limit it to data received from a modal panel with NSModalPanelRunLoopMode.
	 **/

	public void setRunLoopModes(NSArray<NSObject> newModes) {
		// not mapped
		if (_modes != newModes) {
			_modes = newModes;
			if (_runLoopGroupingPending) {
				NSRunLoop runLoop = NSRunLoop.currentRunLoop();
				// runLoop.cancelPerformSelector(selector(_loop), target self, argument: nil);
				// runLoop.performSelector(aSelector(_loop), target self, null, NSUndoCloseGroupingRunLoopOrdering,
				// modes);
			}
		}
	}

	// Discardable Undo and Redo Actions

	/**
	 * @Signature: setActionIsDiscardable:
	 * @Declaration : - (void)setActionIsDiscardable:(BOOL)discardable
	 * @Description : Sets whether the next undo or redo action is discardable.
	 * @param discardable Specifies if the action is discardable. YES if the next undo or redo action can be discarded; NO otherwise.
	 * @Discussion Specifies that the latest undo action may be safely discarded when a document can not be saved for any reason. An example
	 *             might be an undo action that changes the viewable area of a document. To find out if an undo group contains only
	 *             discardable actions, look for the NSUndoManagerGroupIsDiscardableKey in the userInfo dictionary of the
	 *             NSUndoManagerWillCloseUndoGroupNotification.
	 **/

	public void setActionIsDiscardable(boolean discardable) {
		this.isdiscardable = discardable;
	}

	/**
	 * @Signature: undoActionIsDiscardable
	 * @Declaration : - (BOOL)undoActionIsDiscardable
	 * @Description : Returns whether the next undo action is discardable.
	 * @return Return Value YES if the action is discardable; NO otherwise.
	 * @Discussion Specifies that the latest undo action may be safely discarded when a document can not be saved for any reason. These are
	 *             typically actions that donâ€™t effect persistent state. An example might be an undo action that changes the viewable area
	 *             of a document.
	 **/

	public boolean undoActionIsDiscardable() {
		return isdiscardable;
	}

	/**
	 * @Signature: redoActionIsDiscardable
	 * @Declaration : - (BOOL)redoActionIsDiscardable
	 * @Description : Returns whether the next redo action is discardable.
	 * @return Return Value YES if the action is discardable; NO otherwise.
	 * @Discussion Specifies that the latest redo action may be safely discarded when a document can not be saved for any reason. These are
	 *             typically actions that donâ€™t effect persistent state. An example might be an redo action that changes the viewable area
	 *             of a document.
	 **/

	public boolean redoActionIsDiscardable() {
		return isdiscardable;
	}

	// Added

	NSMutableArray<PrivateUndoGroup> _redoStack;
	NSMutableArray<PrivateUndoGroup> _undoStack;
	NSObject _group;
	NSObject _nextTarget;
	NSArray<NSObject> _modes;
	boolean _isRedoing;
	boolean _isUndoing;
	boolean _groupsByEvent;
	boolean _runLoopGroupingPending;
	int _disableCount;
	int _levelsOfUndo;
	boolean isdiscardable;

	class PrivateUndoGroup extends NSObject {

		private PrivateUndoGroup parent;
		private NSMutableArray<NSInvocation> actions;
		private NSString actionName;

		public void addInvocation(NSInvocation inv) {
			if (actions == null) {
				actions = new NSMutableArray<NSInvocation>();
				actions = (NSMutableArray<NSInvocation>) actions.initWithCapacity(2);
			}
			actions.addObject(inv);
		}

		public NSObject initWithParent(PrivateUndoGroup p) {
			parent = p;
			actions = null;
			actionName = new NSString("");
			return this;
		}

		public void orphan() {
			parent = null;
		}

		public void perform() {
			if (actions != null) {
				int i = actions.count();

				while (i-- > 0) {
					NSInvocation invoc = actions.objectAtIndex(i);
					invoc.invoke();
				}
			}
		}

		public boolean removeActionsForTarget(Object target) {
			if (actions != null) {
				int i = actions.count();

				while (i-- > 0) {
					NSInvocation inv = actions.objectAtIndex(i);

					if (inv.target() == target) {
						actions.removeObjectAtIndex(i);
					}
				}
				if (actions.count() > 0) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * This method is used to begin undo grouping internally. It's necessary to have a different mechanism from the -beginUndoGrouping
	 * because it seems that in MacOS X a call to -beginUndoGrouping when at the top level will actually create two undo groups.
	 */
	public void _begin() {
		PrivateUndoGroup parent;

		parent = (PrivateUndoGroup) _group;
		PrivateUndoGroup pg = new PrivateUndoGroup();
		pg.initWithParent(parent);
		_group = pg;
		if (_group == null) {
			_group = parent;
			NSException.raiseFormat(new NSString("NSInternalInconsistencyException"), new NSString(
					"beginUndoGrouping failed to greate group"));
		} else {
			parent = null;
			if (_isUndoing == false && _isRedoing == false)
				NSNotificationCenter.defaultCenter().postNotificationNameObject(new NSString("NSUndoManagerDidOpenUndoGroupNotification"),
						this);
		}
	}

	public void _loop(NSObject arg) {
		if (_groupsByEvent && _group != null) {
			this.endUndoGrouping();
		}
		_runLoopGroupingPending = false;
	}

}