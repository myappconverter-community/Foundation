
package com.myappconverter.java.foundations;

import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.SerializationUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;


public class NSUserDefaults extends NSObject {

    private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
    private static final String TAG = NSUserDefaults.class.getSimpleName();
    private Set<String> persistentPreferencesList = new HashSet<String>();
    private Set<String> volatilePreferencesList = new HashSet<String>();
    Properties prop=new Properties();
    private PreferenceManager preferenceManager;
    private SharedPreferences sharedPreferences;
    private static int idFilePreference;

    public NSUserDefaults() {
    }

    public NSUserDefaults(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

    }

    /**
     * Returns the shared defaults object.
     */

    /**
     * @return Return Value The shared defaults object.
     * @Signature: standardUserDefaults
     * @Declaration : + (NSUserDefaults *)standardUserDefaults
     * @Description : Returns the shared defaults object.
     * @Discussion If the shared defaults object does not exist yet, it is created with a search list containing the names of the following
     * domains, in this order: NSArgumentDomain, consisting of defaults parsed from the applicationâ€™s arguments A domain
     * identified by the applicationâ€™s bundle identifier NSGlobalDomain, consisting of defaults meant to be seen by all
     * applications Separate domains for each of the userâ€™s preferred languages NSRegistrationDomain, a set of temporary
     * defaults whose values can be set by the application to ensure that searches will always be successful The defaults are
     * initialized for the current user. Subsequent modifications to the standard search list remain in effect even when this
     * method is invoked againâ€”the search list is guaranteed to be standard only the first time this method is invoked.
     **/

    public static NSUserDefaults standardUserDefaults() {
        return new NSUserDefaults(PreferenceManager.getDefaultSharedPreferences(GenericMainContext.sharedContext));
    }

    /**
     * @Signature: resetStandardUserDefaults
     * @Declaration : + (void)resetStandardUserDefaults
     * @Description : Synchronizes any changes made to the shared user defaults object and releases it from memory.
     * @Discussion A subsequent invocation of standardUserDefaults creates a new shared user defaults object with the standard search list.
     **/

    public static void resetStandardUserDefaults() {
        PreferenceManager.setDefaultValues(GenericMainContext.sharedContext, idFilePreference, false);
    }

    /**
     * @return Return Value An initialized NSUserDefaults object whose argument and registration domains are already set up.
     * @Signature: init
     * @Declaration : - (id)init
     * @Description : Returns an NSUserDefaults object initialized with the defaults for the current user account.
     * @Discussion This method does not put anything in the search list. Invoke it only if youâ€™ve allocated your own NSUserDefaults instance
     * instead of using the shared one.
     **/
    @Override

    public NSObject init() {
        Context context = null;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // FIXME
        return null;
    }

    /**
     * @param username The name of the user account.
     * @return Return Value An initialized NSUserDefaults object whose argument and registration domains are already set up. If the current
     * user does not have access to the specified user account, this method returns nil.
     * @Signature: initWithUser:
     * @Declaration : - (id)initWithUser:(NSString *)username
     * @Description : Returns an NSUserDefaults object initialized with the defaults for the specified user account. (Deprecated in iOS 7.0.
     * This method was never implemented to return anything but the defaults for the current user. Use standardUserDefaults
     * instead.)
     * @Discussion This method does not put anything in the search list. Invoke it only if youâ€™ve allocated your own NSUserDefaults instance
     * instead of using the shared one. You do not normally use this method to initialize an instance of NSUserDefaults.
     * Applications used by a superuser might use this method to update the defaults databases for a number of users. The user
     * who started the application must have appropriate access (read, write, or both) to the defaults database of the new user,
     * or this method returns nil.
     **/


    public Object initWithUser(NSString username) {
        return init();
    }

    /**
     * Adds the contents of the specified dictionary to the registration domain.
     */

    /**
     * - (void)registerDefaults:(NSDictionary *)dictionary
     *
     * @param dictionary
     */
    /**
     * Parameters dictionary The dictionary of keys and values you want to register. Discussion If there is no registration domain, one is
     * created using the specified dictionary, and NSRegistrationDomain is added to the end of the search list. The contents of the
     * registration domain are not written to disk; you need to call this method each time your application starts. You can place a plist
     * file in the application's Resources directory and call registerDefaults: with the contents that you read in from that file.
     */

    public void registerDefaults(NSDictionary dictionary) {
        Iterator it = dictionary.getWrappedDictionary().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            setObjectForKey(pair.getValue(), (NSString) pair.getKey());
        }
    }

    /**
     * @param defaultName A key in the current user's defaults database.
     * @return Return Value The array associated with the specified key, or nil if the key does not exist or its value is not an NSArray
     * object.
     * @Signature: arrayForKey:
     * @Declaration : - (NSArray *)arrayForKey:(NSString *)defaultName
     * @Description : Returns the array associated with the specified key.
     **/

    public NSArray arrayForKey(NSString defaultName) {
        Set<String> defaultsValues = null;
        Set<String> preferencesValues = sharedPreferences.getStringSet(defaultName.getWrappedString(), defaultsValues);
        String[] preferencesValuesInArray = preferencesValues.toArray(new String[0]);

        return new NSArray(Arrays.asList(preferencesValuesInArray));
    }

    /**
     * @param defaultName A key in the current user's defaults database.
     * @return Return Value If a boolean value is associated with defaultName in the user defaults, that value is returned. Otherwise, NO is
     * returned.
     * @Signature: boolForKey:
     * @Declaration : - (BOOL)boolForKey:(NSString *)defaultName
     * @Description : Returns the Boolean value associated with the specified key.
     **/

    public boolean boolForKey(NSString defaultName) {
        boolean defValue = false;

        Object obj = objectForKey(defaultName);
        if (obj != null){
            if (obj instanceof Boolean){
                return (boolean) obj;
            }else if (obj instanceof Integer){
                if (obj==new Integer(1)){
                    return true;
                }else if (obj == new Integer(0)){
                    return false;
                }
            }
        }
        return defValue;
    }

    /**
     * @param defaultName A key in the current user's defaults database.
     * @return Return Value The data object associated with the specified key, or nil if the key does not exist or its value is not an
     * NSData object.
     * @Signature: dataForKey:
     * @Declaration : - (NSData *)dataForKey:(NSString *)defaultName
     * @Description : Returns the data object associated with the specified key.
     **/

    public NSData dataForKey(NSString defaultName) {
        long defValue = 0;
        Long mlong = sharedPreferences.getLong(defaultName.getWrappedString(), defValue);
        byte[] arr = new byte[1024];
        arr[0] = mlong.byteValue();
        return new NSData(arr);
    }

    /**
     * Returns the dictionary object associated with the specified key.
     */

    /**
     * @param defaultName A key in the current user's defaults database.
     * @return Return Value The dictionary object associated with the specified key, or nil if the key does not exist or its value is not an
     * NSDictionary object.
     * @Signature: dictionaryForKey:
     * @Declaration : - (NSDictionary *)dictionaryForKey:(NSString *)defaultName
     * @Description : Returns the dictionary object associated with the specified key.
     **/

    public NSDictionary dictionaryForKey(NSString defaultName) {
        Map<String, ?> keyValuesPreferences = sharedPreferences.getAll();
        Map<String, Object> keyValue = (Map<String, Object>) keyValuesPreferences.get(defaultName.getWrappedString());

        return new NSDictionary(keyValue);
    }

    /**
     * Returns the floating-point value associated with the specified key.
     */

    /**
     * - (float)floatForKey:(NSString *)defaultName
     *
     * @param defaultName
     * @return
     */


    public float floatForKey(NSString defaultName) {
        float defValue = 0;
        return sharedPreferences.getFloat(defaultName.getWrappedString(), defValue);
    }

    /**
     * @param defaultName A key in the current user's defaults database.
     * @return Return Value The integer value associated with the specified key. If the specified key does not exist, this method returns 0.
     * @Signature: integerForKey:
     * @Declaration : - (NSInteger)integerForKey:(NSString *)defaultName
     * @Description : Returns the integer value associated with the specified key..
     **/

    public Integer integerForKey(NSString defaultName) {
        int defValue = 0;
        return sharedPreferences.getInt(defaultName.getWrappedString(), defValue);
    }

    /**
     * @param key A key in the current user's defaults database.
     * @return Return Value The object associated with the specified key, or nil if the key was not found.
     * @Signature: objectForKey:
     * @Declaration : - (id)objectForKey:(NSString *)defaultName
     * @Description : Returns the object associated with the first occurrence of the specified default.
     * @Discussion This method searches the domains included in the search list in the order they are listed.
     **/

    public Object objectForKey(NSString key) {
        NSString x;
        try {

            // ************get primitive data from sharedPreferences******//
            Set<? extends Entry<String, ?>> entreys = sharedPreferences.getAll().entrySet();
            for (Entry<String, ?> entry : entreys) {
                String keymap = (String) entry.getKey();
                Object value = entry.getValue();
                if (key != null && key.getWrappedString().equals(keymap)) {
                    if (value != null && value instanceof String) {
                        String y = (String) value;
                        if (y != null) {
                            x = new NSString(y);
                            return x;
                        }
                    } else if (value != null && value instanceof Integer) {
                        return (Integer) (sharedPreferences.getInt(key.getWrappedString(),
                                (int) 0));
                    } else if (value != null && value instanceof Long) {
                        return (Long) (sharedPreferences.getLong(key.getWrappedString(),
                                (Long) 0L));
                    } else if (value != null && value instanceof Float) {
                        return (Float) (sharedPreferences.getFloat(key.getWrappedString(),
                                (Float) 0f));
                    }else if (value != null && value instanceof Boolean) {
                        return (Boolean) (sharedPreferences.getBoolean(key.getWrappedString(),
                                (Boolean) false));
                    }
                }

            }

            // ***********get data from internal storage********************//

            Properties property = new Properties();
            property.load(GenericMainContext.sharedContext
                    .openFileInput("internal_preferences.properties"));
            String filePath = property.getProperty(key.getWrappedString());
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
            return SerializationUtil.retrieveObject(fileName);

        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
        }
        return null;
    }
    /**
     * Returns the array of strings associated with the specified key.
     */

    /**
     * - (NSArray *)stringArrayForKey:(NSString *)defaultName
     *
     * @param defaultName
     * @return
     */
    /**
     * Parameters defaultName A key in the current user's defaults database. Return Value The array of NSString objects, or nil if the
     * specified default does not exist, the default does not contain an array, or the array does not contain NSString objects. Special
     * Considerations The returned array and its contents are immutable, even if the values you originally set were mutable.
     */

    public NSArray stringArrayForKey(NSString defaultName) {
        Set<String> defaultsValues = null;

        Set<String> preferencesValues = sharedPreferences.getStringSet(defaultName.getWrappedString(), defaultsValues);
        String[] preferencesValuesInArray = preferencesValues.toArray(new String[0]);

        return new NSArray(Arrays.asList(preferencesValuesInArray));
    }

    /**
     * Returns the string associated with the specified key.
     */

    /**
     * - (NSString *)stringForKey:(NSString *)defaultName
     *
     * @param defaultName
     * @return
     */
    /**
     * Parameters defaultName A key in the current user's defaults database. Return Value For string values, the string associated with the
     * specified key. For number values, the string value of the number. Returns nil if the default does not exist or is not a string or
     * number value. Special Considerations The returned string is immutable, even if the value you originally set was a mutable string.
     */

    public NSString stringForKey(NSString defaultName) {

        try{
            return (NSString)objectForKey(defaultName);
        }catch (Exception ex){
            LOGGER.info(String.valueOf(ex));
            return null;
        }

    }

    /**
     * Returns the double value associated with the specified key.
     */

    /**
     * - (double)doubleForKey:(NSString *)defaultName
     *
     * @param defaultName
     * @return
     */

    /**
     * Parameters defaultName A key in the current user's defaults database. Return Value The double value associated with the specified
     * key. If the key does not exist, this method returns 0.
     */

    public double doubleForKey(NSString defaultName) {
        float defValue = 0;
        return sharedPreferences.getFloat(defaultName.getWrappedString(), defValue);
    }

    /**
     * Returns the NSURL instance associated with the specified key.
     */

    /**
     * - (NSURL *)URLForKey:(NSString *)defaultName
     *
     * @param defaultName
     * @return
     */

    /**
     * Parameters defaultName A key in the current user's defaults database. Return Value The NSURL instance value associated with the
     * specified key. If the key does not exist, this method returns nil. Discussion When an NSURL is read using -[NSUserDefaults
     * URLForKey:], the following logic is used: If the value for the key is an NSData, the NSData is used as the argument to
     * +[NSKeyedUnarchiver unarchiveObjectWithData:]. If the NSData can be unarchived as an NSURL, the NSURL is returned otherwise nil is
     * returned. If the value for this key was a file reference URL, the file reference URL will be created but its bookmark data will not
     * be resolved until the NSURL instance is later used (e.g. at -[NSData initWithContentsOfURL:]). If the value for the key is an
     * NSString which begins with a ~, the NSString will be expanded using -[NSString stringByExpandingTildeInPath] and a file: scheme NSURL
     * will be created from that.
     */

    public NSURL URLForKey(NSString defaultName) {
        NSString string = stringForKey(defaultName);
        return NSURL.URLWithString(NSURL.class, string);
    }

    /**
     * @param value       The Boolean value to store in the defaults database.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setBool:forKey:
     * @Declaration : - (void)setBool:(BOOL)value forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key to the specified Boolean value.
     * @Discussion Invokes setObject:forKey: as part of its implementation.
     **/

    public void setBoolForKey(boolean value, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(defaultName.getWrappedString(), value);
        editor.commit();
    }

    /**
     * @param value       The floating-point value to store in the defaults database.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setFloat:forKey:
     * @Declaration : - (void)setFloat:(float)value forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key to the specified floating-point value.
     * @Discussion Invokes setObject:forKey: as part of its implementation.
     **/

    public void setFloatForKey(float value, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(defaultName.getWrappedString(), value);

        editor.commit();
    }

    /**
     * @param value       The integer value to store in the defaults database.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setInteger:forKey:
     * @Declaration : - (void)setInteger:(NSInteger)value forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key to the specified integer value.
     * @Discussion Invokes setObject:forKey: as part of its implementation.
     **/

    public void setIntegerForKey(Integer value, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(defaultName.getWrappedString(), value);
        editor.commit();
    }

    /**
     * @param value       The object to store in the defaults database.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setObject:forKey:
     * @Declaration : - (void)setObject:(id)value forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key in the standard application domain.
     * @Discussion The value parameter can be only property list objects: NSData, NSString, NSNumber, NSDate, NSArray, or NSDictionary. For
     * NSArray and NSDictionary objects, their contents must be property list objects. See â€œWhat is a Property List?â€? in
     * Property List Programming Guide. Setting a default has no effect on the value returned by the objectForKey: method if the
     * same key exists in a domain that precedes the application domain in the search list.
     **/

    public void setObjectForKey(Object value, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        FileOutputStream output = null;

        if (value instanceof String || value instanceof Integer || value instanceof Float
                || value instanceof Double || value instanceof Boolean || value instanceof Long
                || value instanceof Byte) {
            if (value instanceof String) {
                editor.putString(defaultName.getWrappedString(), (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(defaultName.getWrappedString(), (Integer) value);
            } else if (value instanceof Float) {
                editor.putFloat(defaultName.getWrappedString(), (Float) value);
            } else if (value instanceof Double) {
                editor.putFloat(defaultName.getWrappedString(), new Float((Double) value));
            } else if (value instanceof Boolean) {
                editor.putBoolean(defaultName.getWrappedString(), (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(defaultName.getWrappedString(), (Long) value);
            } else if (value instanceof Byte) {
                editor.putLong(defaultName.getWrappedString(), (Long) value);
            }

            editor.commit();

        } else if(value instanceof NSString){
            editor.putString(defaultName.getWrappedString(),  ((NSString) value).getWrappedString());
        } else{

            try {
                SerializationUtil.storeObject(value, defaultName.getWrappedString());
                output = GenericMainContext.sharedContext
                        .openFileOutput("internal_preferences.properties", Context.MODE_PRIVATE);

                prop.setProperty(defaultName.getWrappedString(),
                        GenericMainContext.sharedContext.getFilesDir() + "/"
                                + defaultName.getWrappedString());
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    public void setValueForKey(Object value, NSString defaultName){
        setObjectForKey(value, defaultName);

    }

    /**
     * @param value       The double value.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setDouble:forKey:
     * @Declaration : - (void)setDouble:(double)value forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key to the double value.
     **/

    public void setDoubleForKey(double value, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(defaultName.getWrappedString(), (float) value);

        editor.commit();
    }

    /**
     * @param url         The NSURL to store in the defaults database.
     * @param defaultName The key with which to associate with the value.
     * @Signature: setURL:forKey:
     * @Declaration : - (void)setURL:(NSURL *)url forKey:(NSString *)defaultName
     * @Description : Sets the value of the specified default key to the specified URL.
     * @Discussion When an NSURL is stored using -[NSUserDefaults setURL:forKey:], some adjustments are made: Any non-file URL is written by
     * calling +[NSKeyedArchiver archivedDataWithRootObject:] using the NSURL instance as the root object. Any file reference
     * file: scheme URL will be treated as a non-file URL, and information which makes this URL compatible with 10.5 systems
     * will also be written as part of the archive as well as its minimal bookmark data. Any path-based file: scheme URL is
     * written by first taking the absolute URL, getting the path from that and then determining if the path can be made
     * relative to the user's home directory. If it can, the string is abbreviated by using stringByAbbreviatingWithTildeInPath
     * and written out. This allows pre-10.6 clients to read the default and use -[NSString stringByExpandingTildeInPath] to use
     * this information.
     **/

    public void setURLForKey(NSURL url, NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(defaultName.getWrappedString(), url.toString());
        editor.commit();
    }

    /**
     * Removes the value of the specified default key in the standard application domain.
     */

    /**
     * - (void)removeObjectForKey:(NSString *)defaultName
     *
     * @param defaultName
     */
    /**
     * Parameters defaultName The key whose value you want to remove. Discussion Removing a default has no effect on the value returned by
     * the objectForKey: method if the same key exists in a domain that precedes the standard application domain in the search list.
     */

    public void removeObjectForKey(NSString defaultName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(defaultName.getWrappedString());
        editor.commit();
    }

    /**
     * @return Return Value YES if the data was saved successfully to disk, otherwise NO.
     * @Signature: synchronize
     * @Declaration : - (BOOL)synchronize
     * @Description : Writes any modifications to the persistent domains to disk and updates all unmodified persistent domains to what is on
     * disk.
     * @Discussion Because this method is automatically invoked at periodic intervals, use this method only if you cannot wait for the
     * automatic synchronization (for example, if your application is about to exit) or if you want to update the user defaults
     * to what is on disk even though you have not made any changes.
     **/

    public boolean synchronize() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.commit();
    }

    /**
     * @param domainName The domain whose keys and values you want.
     * @return Return Value A dictionary containing the keys. The keys are names of defaults and the value corresponding to each key is a
     * property list object (NSData, NSString, NSNumber, NSDate, NSArray, or NSDictionary).
     * @Signature: persistentDomainForName:
     * @Declaration : - (NSDictionary *)persistentDomainForName:(NSString *)domainName
     * @Description : Returns a dictionary containing the keys and values in the specified persistent domain.
     **/

    public NSDictionary persistentDomainForName(NSString domainName) {

        NSDictionary dictionnaory = new NSDictionary();
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(domainName.getWrappedString(), 0);
        Map<String, ?> mPref = pref.getAll();
        Collection<?> values = mPref.values();
        List valuesList = new ArrayList<Object>(values);
        Set keys = mPref.keySet();
        List keyList = new ArrayList<Object>(keys);
        NSArray nsArrayKeys = new NSArray<Object>(keyList);
        NSArray nsArrayValues = new NSArray<Object>(valuesList);

        dictionnaory.dictionaryWithObjectsForKeys(NSDictionary.class, nsArrayValues, nsArrayKeys);
        return dictionnaory;
    }

    /**
     * Removes the contents of the specified persistent domain from the userâ€™s defaults.
     */

    /**
     * - (void)removePersistentDomainForName:(NSString *)domainName
     *
     * @param domainName
     */

    /**
     * Parameters domainName The domain whose keys and values you want. Discussion When a persistent domain is changed, an
     * NSUserDefaultsDidChangeNotification is posted.
     */

    public void removePersistentDomainForName(NSString domainName) {
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(domainName.getWrappedString(), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        persistentPreferencesList.remove(domainName);
    }

    /**
     * @param domain     The dictionary of keys and values you want to assign to the domain.
     * @param domainName The domain whose keys and values you want to set. This value should be equal to your application's bundle
     *                   identifier.
     * @Signature: setPersistentDomain:forName:
     * @Declaration : - (void)setPersistentDomain:(NSDictionary *)domain forName:(NSString *)domainName
     * @Description : Sets the dictionary for the specified persistent domain.
     * @Discussion When a persistent domain is changed, an NSUserDefaultsDidChangeNotification is posted.
     **/

    public void setPersistentDomainForName(NSDictionary domain, NSString domainName) {

        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(domainName.getWrappedString(), 0);
        SharedPreferences.Editor editor = pref.edit();
        NSEnumerator it = domain.keyEnumerator();
        while (it.mIterator.hasNext()) {
            // todo
            // if (domain.objectForKey(key) instanceof String) {
            // editor.putString(key, (String) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof Integer) {
            // editor.putInt(key, (Integer) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof Float) {
            // editor.putFloat(key, (Float) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof Double) {
            // editor.putFloat(key, ((Double) domain.objectForKey(key)).floatValue());
            // }
            // else if (domain.objectForKey(key) instanceof Boolean) {
            // editor.putBoolean(key, (Boolean) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof Long) {
            // editor.putLong(key, (Long) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof Byte) {
            //
            // // NSDATA
            // ByteBuffer buf = ByteBuffer.allocate(5000);
            // editor.putLong(key, buf.getLong());
            // }
            // else if (domain.objectForKey(key) instanceof Set<?>) {
            // editor.putStringSet(key, (Set<String>) domain.objectForKey(key));
            // }
            // else if (domain.objectForKey(key) instanceof URL) {
            // editor.putString(key, (String) domain.objectForKey(key).toString());
            // }

        }
        editor.commit();
        persistentPreferencesList.add(domainName.getWrappedString());
    }

    /**
     * Returns an array of the current persistent domain names. (Deprecated in iOS 7.0. Instead of using this method, you should track the
     * domains you add if you want to later retrieve them with persistentDomainForName:.)
     */

    /**
     * - (NSArray *)persistentDomainNames
     *
     * @return
     */
    /**
     * Return Value An array of NSString objects containing the domain names. Discussion You can get the keys and values for each domain by
     * passing the returned domain names to the persistentDomainForName: method.
     */

    public NSArray persistentDomainNames() {
        return new NSArray(Arrays.asList(persistentPreferencesList.toArray()));
    }

    /**
     * @param key The key whose status you want to check.
     * @return Return Value YES if the value of the specified key is managed by an administrator, otherwise NO.
     * @Signature: objectIsForcedForKey:
     * @Declaration : - (BOOL)objectIsForcedForKey:(NSString *)key
     * @Description : Returns a Boolean value indicating whether the specified key is managed by an administrator.
     * @Discussion This method assumes that the key is a preference associated with the current user and application. For managed keys, the
     * application should disable any user interface that allows the user to modify the value of key.
     **/

    public boolean objectIsForcedForKey(NSString key) {
        return !sharedPreferences.contains(key.getWrappedString());
    }

    /**
     * @param key    The key whose status you want to check.
     * @param domain The domain of the key.
     * @return Return Value YES if the key is managed by an administrator in the specified domain, otherwise NO.
     * @Signature: objectIsForcedForKey:inDomain:
     * @Declaration : - (BOOL)objectIsForcedForKey:(NSString *)key inDomain:(NSString *)domain
     * @Description : Returns a Boolean value indicating whether the key in the specified domain is managed by an administrator.
     * @Discussion This method assumes that the key is a preference associated with the current user. For managed keys, the application
     * should disable any user interface that allows the user to modify the value of key.
     **/

    public boolean objectIsForcedForKeyInDomain(NSString key, NSString domain) {
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(domain.getWrappedString(), 0);
        return !pref.contains(key.getWrappedString());
    }

    /**
     * @return Return Value A dictionary containing the keys. The keys are names of defaults and the value corresponding to each key is a
     * property list object (NSData, NSString, NSNumber, NSDate, NSArray, or NSDictionary).
     * @Signature: dictionaryRepresentation
     * @Declaration : - (NSDictionary *)dictionaryRepresentation
     * @Description : Returns a dictionary that contains a union of all key-value pairs in the domains in the search list.
     * @Discussion As with objectForKey:, key-value pairs in domains that are earlier in the search list take precedence. The combined
     * result does not preserve information about which domain each entry came from.
     **/

    public NSDictionary dictionaryRepresentation() {
        NSDictionary nsd = new NSDictionary();

        Map<String, ?> mPref = null;
        Collection<?> values = null;
        Set<String> keys = mPref.keySet();
        nsd = null;
        for (String domain : persistentPreferencesList) {
        }
        // TODO test

        return nsd;

    }

    /**
     * Removes the specified volatile domain from the userâ€™s defaults.
     */

    /**
     * - (void)removeVolatileDomainForName:(NSString *)domainName
     *
     * @param domainName
     */
    /**
     * Parameters domainName The volatile domain you want to remove.
     */

    public void removeVolatileDomainForName(NSString domainName) {
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(domainName.getWrappedString(), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        volatilePreferencesList.remove(domainName);
    }

    /**
     * @param domain     The dictionary of keys and values you want to assign to the domain.
     * @param domainName The domain whose keys and values you want to set.
     * @Signature: setVolatileDomain:forName:
     * @Declaration : - (void)setVolatileDomain:(NSDictionary *)domain forName:(NSString *)domainName
     * @Description : Sets the dictionary for the specified volatile domain.
     * @Discussion This method raises an NSInvalidArgumentException if a volatile domain with the specified name already exists.
     **/

    public void setVolatileDomain(NSDictionary domain, NSString volatiledomainName) {
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(volatiledomainName.getWrappedString(), 0);
        //Not Mapped
        volatilePreferencesList.add(volatiledomainName.getWrappedString());
    }

    /**
     * Returns the dictionary for the specified volatile domain.
     */

    /**
     * - (NSDictionary *)volatileDomainForName:(NSString *)domainName
     *
     * @param domainName
     * @return
     */
    /**
     * Parameters domainName The domain whose keys and values you want. Return Value The dictionary of keys and values belonging to the
     * domain. The keys in the dictionary are names of defaults, and the value corresponding to each key is a property list object (NSData,
     * NSString, NSNumber, NSDate, NSArray, or NSDictionary).
     */

    public NSDictionary volatileDomainForName(NSString volatiledomainName) {
        NSDictionary dictionnary = new NSDictionary();
        SharedPreferences pref = GenericMainContext.sharedContext.getSharedPreferences(volatiledomainName.getWrappedString(), 0);
        Map<String, ?> mPref = pref.getAll();
        return dictionnary;
    }

    /**
     * Returns an array of the current volatile domain names.
     */

    /**
     * - (NSArray *)volatileDomainNames
     *
     * @return Value An array of NSString objects with the volatile domain names. Discussion You can get the contents of each domain by
     * passing the returned domain names to the volatileDomainForName: method.
     */

    public NSArray volatileDomainNames() {
        return new NSArray(Arrays.asList(volatilePreferencesList.toArray()));
    }

    /**
     * Inserts the specified domain name into the receiverâ€™s search list.
     */

    /**
     * - (void)addSuiteNamed:(NSString *)suiteName
     *
     * @param suiteName The domain name to insert. This domain is inserted after the application domain. Discussion The suiteName domain is
     *                  similar to a bundle identifier string, but is not necessarily tied to a particular application or bundle. A suite can be
     *                  used to hold preferences that are shared between multiple applications. Searches of preferences tied to a suite follow the
     *                  normal pattern, searching first for current user, current host, then
     */

    public void addSuiteNamed(NSString suiteName) {
        // TODO Chech later
    }


    public void removeSuiteNamed(NSString suiteName) {
        // TODO Chech later
    }

    public Set<String> getPersistentPreferencesList() {
        return persistentPreferencesList;
    }

    public void setPersistentPreferencesList(Set<String> persistentPreferencesList) {
        this.persistentPreferencesList = persistentPreferencesList;
    }

    public Set<String> getVolatilePreferencesList() {
        return volatilePreferencesList;
    }

    public void setVolatilePreferencesList(Set<String> volatilePreferencesList) {
        this.volatilePreferencesList = volatilePreferencesList;
    }

    // TODO variable valueForKey and setter/getter
    public Object valueForKey(NSString key) {
        return null;
    }

}