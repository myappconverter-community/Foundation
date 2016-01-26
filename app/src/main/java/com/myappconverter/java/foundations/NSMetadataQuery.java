
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.myappconverter.java.foundations.protocols.NSMetadataQueryDelegate;


public class NSMetadataQuery extends NSObject {

	private NSArray<NSObject> searchScopes;
	private MediaMetadataRetriever mediaMetadataRetriever;
	private NSArray<NSSortDescriptor> sortDescriptors;
	private NSArray<NSString> valueListAttributes;
	private NSArray<NSString> groupAttributes;

	NSPredicate predicate;
	CursorLoader cLoader;
	Cursor cursor;
	String[] projection;
	private Context context;
	private NSMetadataQueryDelegate delegate;

	private static interface NSMetadataQueryBlock {

		void performAction(Object result, int idx, boolean[] stop);
	}

	// Creating Metadata Queries

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Initializes an allocated NSMetadataQuery object.
	 * @return Return Value An initialized NSMetadataQuery object.
	 **/
	@Override
	
	public NSMetadataQuery init() {
		NSMetadataQuery metadataQuery = new NSMetadataQuery();
		metadataQuery.cLoader = new CursorLoader(context);
		return metadataQuery;
	}

	// Configuring Queries
	/**
	 * @Signature: searchScopes
	 * @Declaration : - (NSArray *)searchScopes
	 * @Description : Returns an array containing the receiver’s search scopes.
	 * @return Return Value An array containing the receiver’s search scopes.
	 * @Discussion The array can contain NSString or NSURL objects that represent file system directories or the search scopes specified in
	 *             “Constants. An empty array indicates that there is no limitation on where the receiver searches.
	 **/
	
	public NSArray<NSObject> searchScopes() {// is projection
		return searchScopes;
	}

	/**
	 * @Signature: setSearchScopes:
	 * @Declaration : - (void)setSearchScopes:(NSArray *)scopes
	 * @Description : Restrict the search scope of the receiver.
	 * @param scopes Array of NSString or NSURL objects that specify file system directories. You can also include the predefined search
	 *            scopes specified in “Constants. An empty array removes search scope limitations.
	 **/
	
	public void setSearchScopes(NSArray<NSObject> scopes) {// is projection
		searchScopes = scopes;
		// get projection
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < searchScopes.count(); i++) {
			NSObject obj = searchScopes.objectAtIndex(i);
			if (obj instanceof NSString) {
				NSString str = (NSString) obj;
				list.add(str.getWrappedString());
			} else if (obj instanceof NSURL) {
				NSURL url = (NSURL) obj;
				list.add(url.getUri().toString());
			}
		}
		projection = (String[]) list.toArray();
	}

	/**
	 * @Declaration : - (NSPredicate *)predicate
	 * @Description : Returns the predicate the receiver uses to filter query results.
	 * @return Return Value The predicate used to filter query results.
	 */
	
	public NSPredicate predicate() {
		return predicate;
	}

	/**
	 * @Declaration : - (void)setPredicate:(NSPredicate *)predicate
	 * @Description : Sets the predicate used by the receiver to filter the query results.
	 * @param predicate A predicate to be used to filter query results.
	 * @Discussion Invoking this method on a receiver running a query causes the existing query to stop, all current results are discarded,
	 *             and a new query is started immediately.
	 */
	
	public void setPredicate(NSPredicate predicate) {
		this.predicate = predicate;
	}

	/**
	 * @Declaration : - (NSArray *)sortDescriptors
	 * @Description : Returns an array containing the receiver’s sort descriptors.
	 * @return Return Value An array containing sort descriptors.
	 */
	
	public NSArray<NSSortDescriptor> sortDescriptors() {
		return sortDescriptors;
	}

	/**
	 * @Declaration : - (void)setSortDescriptors:(NSArray *)descriptors
	 * @Description : Sets the sort descriptors to be used by the receiver.
	 * @param descriptors Array of sort descriptors.
	 * @Discussion Invoking this method on the receiver running a query causes the existing query to stop, all current results are
	 *             discarded, and a new query is started immediately.
	 */
	
	public void setSortDescriptors(NSArray<NSSortDescriptor> descriptors) {
		sortDescriptors = descriptors;
		if (this.isStarted()) {
			this.stopQuery();
			this.startQuery();
		}
	}

	/**
	 * @Declaration : - (NSArray *)valueListAttributes
	 * @Description : Returns an array containing the value list attributes the receiver generates.
	 * @return Return Value Array containing value list attributes.
	 */
	
	public NSArray<NSString> valueListAttributes() {
		return valueListAttributes;
	}

	/**
	 * @Declaration : - (void)setValueListAttributes:(NSArray *)attributes
	 * @Description : Sets the value list attributes for the receiver to the specific attribute names.
	 * @param attributes Array of value list attributes.
	 * @Discussion The query collects the values of these attributes into uniqued lists that can be used to summarize the results of the
	 *             query. If attributes is nil, the query generates no value lists. Note that value list collection increases CPU usage and
	 *             significantly increases the memory usage of an NSMetadataQuery object. Invoking this method on the receiver while it’s
	 *             running a query, stops the query and discards current results, and immediately starts a new query.
	 */
	
	public void setValueListAttributes(NSArray<NSString> attributes) {
		valueListAttributes = attributes;
		if (this.isStarted()) {
			this.stopQuery();
			this.startQuery();
		}
	}

	/**
	 * @Declaration : - (NSArray *)groupingAttributes
	 * @Description : Returns the receiver’s grouping attributes.
	 * @return Return Value Array containing grouping attributes.
	 */
	
	public NSArray<NSString> groupingAttributes() {
		return groupAttributes;
	}

	/**
	 * @Declaration : - (void)setGroupingAttributes:(NSArray *)attributes
	 * @Description : Sets the receiver’s grouping attributes to specific attribute names.
	 * @param attributes Array containing attribute names.
	 * @Discussion Invoking this method on a receiver while it’s running a query, stops the query and discards current results, and
	 *             immediately starts a new query.
	 */
	
	public void setGroupingAttributes(NSArray<NSString> attributes) {
		groupAttributes = attributes;
		if (this.isStarted()) {
			this.stopQuery();
			this.startQuery();
		}
	}

	/**
	 * @Declaration : - (NSTimeInterval)notificationBatchingInterval
	 * @Description : Returns the interval that the receiver provides notification of updated query results.
	 * @return Return Value The interval at which notification of updated results occurs.
	 */
	
	public double notificationBatchingInterval() {
		return 0;
	}

	/**
	 * @Declaration : - (void)setNotificationBatchingInterval:(NSTimeInterval)timeInterval
	 * @Description : Sets the interval between update notifications sent by the receiver.
	 * @param Term The Interval at which notification of updated results is to occur.
	 */
	
	public void setNotificationBatchingInterval(double timeInterval) {
		// not yet covered
	}

	/**
	 * @Signature: delegate
	 * @Declaration : - (id<NSMetadataQueryDelegate>)delegate
	 * @Description : Returns the receiver’s delegate.
	 * @return Return Value The receiver’s delegate, or nil if there is none.
	 **/
	
	public NSMetadataQueryDelegate delegate() {
		return null;
	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id<NSMetadataQueryDelegate>)delegate
	 * @Description : Sets the receiver’s delegate
	 * @param delegate An object to serve as the receiver’s delegate. The delegate must implement the NSMetadataQueryDelegate Protocol
	 *            protocol. Pass nil to remove the current delegate.
	 **/
	
	public void setDelegate(NSMetadataQueryDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * @Declaration : - (NSArray *)searchItems
	 * @Description : The array of items to which a search is scoped.
	 * @return Return Value An array of NSURL and/or NSMetadataItem objects to which the search is scoped.
	 * @Discussion For more information, see setSearchItems:.
	 */
	
	public NSArray searchItems() {
		return null;
	}

	/**
	 * @Declaration : - (void)setSearchItems:(NSArray *)items
	 * @Description : Scopes the search to the given array of items.
	 * @param items An array of NSURL and/or NSMetadataItem objects to which to scope the search.
	 * @Discussion You use this method to scope the metadata query to a given collection existing URLs and/or metadata items.
	 */
	
	public void setSearchItems(NSArray items) {
		// not yet covered
	}

	// Running Queries

	/**
	 * @Declaration : - (BOOL)isStarted
	 * @Description : Returns a Boolean value that indicates whether the receiver has started the query.
	 * @return Return Value YES when the receiver has executed the startQuery method; NO otherwise.
	 */
	
	public boolean isStarted() {
		NSNotificationCenter notif = new NSNotificationCenter();
		notif.postNotificationNameObject(new NSString("NSMetadataQueryDidStartGatheringNotification"), this);
		return cLoader.isStarted();
	}

	/**
	 * @Declaration : - (BOOL)startQuery
	 * @Description : Attempts to start the query.
	 * @return Return Value YES when successful; NO otherwise.
	 * @Discussion A query can’t be started if the receiver is already running a query or no predicate has been specified. This method must
	 *             be called on the main thread,
	 */
	
	public boolean startQuery() {
		if (this.predicate == null) {
			return false;
		}
		// get projection from searchScopes
		final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");

		cLoader = new CursorLoader(context, BOOKMARKS_URI, projection, null, null, sortDescriptors
				.objectAtIndex(0).getProperty());

		cursor = cLoader.loadInBackground();
		if (cursor != null) {
			return true;
		}
		return false;
		// TODO change URI
	}

	/**
	 * @Declaration : - (BOOL)isGathering
	 * @Description : Returns a Boolean value that indicates whether the receiver is in the initial gathering phase of the query.
	 * @return Return Value YES when the query is in the initial gathering phase; NO otherwise.
	 */
	
	public boolean isGathering() {
		return cursor.isFirst();
	}

	/**
	 * @Declaration : - (BOOL)isStopped
	 * @Description : Returns a Boolean value that indicates whether the receiver has stopped the query.
	 * @return Return Value YES when the receiver has stopped the query, NO otherwise.
	 */
	
	public boolean isStopped() {
		return cLoader.isLoadInBackgroundCanceled();
	}

	/**
	 * @Declaration : - (void)stopQuery
	 * @Description : Stops the receiver’s current query from gathering any further results.
	 * @Discussion The receiver first completes gathering any unprocessed results. If a query is stopped before the gathering phase
	 *             finishes, it will not post an NSMetadataQueryDidStartGatheringNotification notification. You would call this function to
	 *             stop a query that is generating too many results to be useful but still want to access the available results. If the
	 *             receiver is sent a startQuery message after performing this method, the existing results are discarded.
	 */
	
	public void stopQuery() {
		cLoader.cancelLoadInBackground();
	}

	// Getting Query Results
	/**
	 * @Declaration : - (NSArray *)results
	 * @Description : Returns an array containing the result objects for the receiver.
	 * @return Return Value Proxy array containing query result objects.
	 * @Discussion The results array is a proxy object that is primarily intended for use with Cocoa bindings. While it is possible to copy
	 *             the proxy array and receive a “snapshot of the complete current query results, it is generally not recommended due to
	 *             performance and memory issues. To access individual result array elements you should instead use the resultCount and
	 *             resultAtIndex: methods. The results array supports key-value observing, which can be used to be notified when items are
	 *             added, removed, or updated in the array of results.
	 */
	
	public NSArray<NSMetadataItem<NSObject>> results() {
		NSMutableArray<NSMetadataItem<NSObject>> resultArray = new NSMutableArray<NSMetadataItem<NSObject>>();
		cursor.moveToFirst();
		if (cursor.moveToFirst() && cursor.getCount() > 0) {
			while (cursor.isAfterLast() == false) {
				NSMetadataItem<NSObject> meta = new NSMetadataItem<NSObject>();
				for (String element : cursor.getColumnNames()) {
					meta.getFileMetadata().put(NSString.stringWithString(new NSString("/*Fixe Type*/")),
							NSString.stringWithString(new NSString(element)));
				}
				resultArray.addObject(meta);
			}
		}
		return resultArray;
	}

	/**
	 * @Signature: resultCount
	 * @Declaration : - (NSUInteger)resultCount
	 * @Description : Returns the number of results returned by the receiver.
	 * @return Return Value The number of objects the query produced.
	 * @Discussion For performance reasons, you should use this method, rather than invoking count on results.
	 **/
	
	public int resultCount() {
		return results().count();
	}

	/**
	 * @Signature: resultAtIndex:
	 * @Declaration : - (id)resultAtIndex:(NSUInteger)index
	 * @Description : Returns the query result at a specific index.
	 * @param index Index of the desired result in the query result array.
	 * @return Return Value Query result at the position specified by index.
	 * @Discussion For performance reasons, you should use this method when retrieving a specific result, rather than the array returned by
	 *             results.
	 **/
	
	public NSObject resultAtIndex(int index) {
		return results().objectAtIndex(index);
	}

	/**
	 * @Signature: indexOfResult:
	 * @Declaration : - (NSUInteger)indexOfResult:(id)result
	 * @Description : Returns the index of a query result object in the receiver’s results array.
	 * @param result Query result object being inquired about.
	 * @return Return Value Index of result in the query result array.
	 **/
	
	public int indexOfResult(NSMetadataItem<NSObject> result) {
		NSArray<NSMetadataItem<NSObject>> itemsList = results();
		if (itemsList.count() > 0)
			return itemsList.indexOfObject(result);
		return 0;
	}

	/**
	 * @Declaration : - (NSArray *)groupedResults
	 * @Description : Returns an array containing hierarchical groups of query results based on the receiver’s grouping attributes.
	 * @return Return Value Array containing hierarchical groups of query results.
	 */
	
	public NSArray groupedResults() {
		return results();
	}

	/**
	 * @Signature: enumerateResultsUsingBlock:
	 * @Declaration : - (void)enumerateResultsUsingBlock:(void (^)(id result, NSUInteger idx, BOOL *stop))block
	 * @Description : Enumerates the current set of results using the given block.
	 * @param block The block to execute for each current result.
	 * @Discussion This method disables the query at the start of the iteration and re-enables it upon completion. Use
	 *             enumerateResultsWithOptions:usingBlock: if you want to use concurrent or reverse iteration.
	 **/
	
	public void enumerateResultsUsingBlock(NSMetadataQueryBlock block) {
		// not yet covered
	}

	/**
	 * @Signature: enumerateResultsWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateResultsWithOptions:(NSEnumerationOptions)opts usingBlock:(void (^)(id result, NSUInteger idx, BOOL
	 *              *stop))block
	 * @Description : Enumerates the current set of results using the given options and block.
	 * @param opts Options for the enumeration. You can
	 * @param block The block to execute for each current result.
	 * @Discussion This method disables the query at the start of the iteration and re-enables it upon completion.
	 **/
	
	public void enumerateResultsWithOptionsUsingBlock(int opts, NSMetadataQueryBlock block) {
		enumerateResultsUsingBlock(block);
	}

	/**
	 * @Signature: valueLists
	 * @Declaration : - (NSDictionary *)valueLists
	 * @Description : Returns a dictionary containing the value lists generated by the receiver.
	 * @return Return Value Dictionary of NSMetadataQueryAttributeValueTuple objects.
	 **/
	
	public NSDictionary valueLists() {
		return null;
	}

	/**
	 * @Signature: valueOfAttribute:forResultAtIndex:
	 * @Declaration : - (id)valueOfAttribute:(NSString *)attributeName forResultAtIndex:(NSUInteger)index
	 * @Description : Returns the value for the attribute name attrName at the index in the results specified by idx.
	 * @param attributeName The attribute of the result object at index being inquired about. The attribute must be specified in
	 *            setValueListAttributes:, as a sorting key in a specified sort descriptor, or as one of the grouping attributes specified
	 *            set for the query.
	 * @param index Index of the desired return object in the query results array.
	 * @return Return Value Value for attributeName in the result object at index in the query result array.
	 **/
	
	public NSMetadataQuery valueOfAttributeForResultAtIndex(NSString attributeName, int index) {
		return null;
	}

	/**
	 * @Declaration : - (void)enableUpdates
	 * @Description : Enables updates to the query results.
	 * @Discussion Unless you use enumerateResultsUsingBlock: or enumerateResultsWithOptions:usingBlock:, you should invoke this method
	 *             after you’re done iterating over the query results.
	 */
	
	public void enableUpdates() {
        // not yet covered
	}

	/**
	 * @Declaration : - (void)disableUpdates
	 * @Description : Disables updates to the query results.
	 * @Discussion Unless you use enumerateResultsUsingBlock: or enumerateResultsWithOptions:usingBlock:, you should invoke this method
	 *             before iterating over query results that could change due to live updates.
	 */
	
	public void disableUpdates() {
        // not yet covered
	}

	/**
	 * @Declaration : - (NSOperationQueue *)operationQueue
	 * @Description : The queue on which query result notifications will be posted.
	 * @return Return Value The queue on which query result notifications will be posted.
	 * @Discussion For more information, see setOperationQueue:.
	 */
	
	public NSOperationQueue operationQueue() {
		return null;
	}

	/**
	 * @Declaration : - (void)setOperationQueue:(NSOperationQueue *)operationQueue
	 * @Description : Sets the queue on which query result notifications will be posted to the given queue.
	 * @param operationQueue The queue on which query result notifications should be posted.
	 * @Discussion You use this method to decouple processing of results from the thread used to execute the query. This makes it easier to
	 *             synchronize query result processing with other related operations—such as updating the data model or user interface—which
	 *             you might want to perform on the main queue.
	 */
	
	public void setOperationQueue(NSOperationQueue operationQueue) {
        // not yet covered
	}

}