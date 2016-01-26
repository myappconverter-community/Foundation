
package com.myappconverter.java.foundations;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class NSJSONSerialization extends NSObject {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private static NSMutableDictionary mutableContainer = new NSMutableDictionary();

	/**
	 * 
	 * Returns a Foundation object from given JSON data.
	 * 
	 * @Declaration OBJECTIVE-C + (id)JSONObjectWithData:(NSData *)data options:(NSJSONReadingOptions)opt error:(NSError **)error
	 * 
	 * @param data
	 * @param opt
	 * @param error
	 * @return
	 */
	
	public static Object JSONObjectWithDataOptionsError(NSData data, NSJSONReadingOptions opt, NSError error) {

		try {
			JSONObject objects = new JSONObject(new String(data.getWrappedData(), "UTF-8"));
			Iterator<String> x = objects.keys();
			JSONArray array = new JSONArray();

			while (x.hasNext()) {
				String key = x.next();
				array.put(objects.get(key));
			}

			if (opt == NSJSONReadingOptions.NSJSONReadingAllowFragments || opt == NSJSONReadingOptions.NSJSONReadingMutableLeaves
					|| opt == NSJSONReadingOptions.NSJSONReadingMutableContainers) {
				Map<NSString, NSString> pairs = new HashMap<NSString, NSString>();
				for (int i = 0; i < array.length(); i++) {
					JSONObject j = array.optJSONObject(i);
					Iterator<String> it = j.keys();
					while (it.hasNext()) {
						String n = it.next();
						pairs.put(new NSString(n), new NSString(j.getString(n)));
					}
				}
				mutableContainer.setWrappedDictionary(pairs);
				return mutableContainer;
			} else {
				Map pairs = new HashMap();
				for (int i = 0; i < objects.length(); i++) {
					Iterator<String> it = objects.keys();
					while (it.hasNext()) {
						String n = it.next();
						try {
							List<Object> list = new Gson().fromJson(objects.getJSONArray(n).toString(), new TypeToken<List<Object>>() {
							}.getType());
							pairs.put(new NSString(n), new NSArray(list));
						} catch (Exception e) {
                            LOGGER.info(String.valueOf(e));
							pairs.put(new NSString(n), objects.getString(n));
						}
					}

				}
				mutableContainer.setWrappedDictionary(pairs);
				return mutableContainer;
			}

		} catch (UnsupportedEncodingException e) {
            LOGGER.info(String.valueOf(e));
		} catch (JSONException e) {
            LOGGER.info(String.valueOf(e));
		}

		return mutableContainer;
	}

	public static void iterate(Map<NSString, NSObject> map) {

		for (Entry<NSString, NSObject> entry : map.entrySet()) {
			if (entry.getValue() instanceof Map) {
				iterate((Map<NSString, NSObject>) entry.getValue());
				NSDictionary<NSString, NSObject> dic = new NSDictionary<NSString, NSObject>();
				dic.setWrappedDictionary((Map<NSString, NSObject>) entry.getValue());
				entry.setValue(dic);
			} else {
			}
		}
	}

	/**
	 *
	 * Returns a Foundation object from JSON data in a given stream.
	 *
	 * @param arg1
	 * @param arg2
	 * @param error
	 * @return
	 */
	
	public static Object JSONObjectWithStream(NSInputStream stream, NSJSONReadingOptions opt, NSError error) {
		return null;
	}

	/**
	 *
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param error
	 * @return
	 */
	
	public static int writeJSONObject(Object obj, NSOutputStream stream, NSJSONReadingOptions nsjsonreadingmutablecontainers,
			NSError error) {
		// not yet covered
		return 0;
	}

	/**
	 * Returns JSON data from a Foundation object.
	 *
	 * @Declaration OBJECTIVE-C + (NSData *)dataWithJSONObject:(id)obj options:(NSJSONWritingOptions)opt error:(NSError **)error
	 * @param obj The object from which to generate JSON data. Must not be nil.
	 * @param opt Options for creating the JSON data.
	 *
	 *            See NSJSONWritingOptions for possible values. Pass 0 to specify no options.
	 * @param error If an internal error occurs, upon return contains an NSError object that describes the problem.
	 * @return Return Value JSON data for obj, or nil if an internal error occurs. The resulting data is a encoded in UTF-8.
	 * @Discussion If obj will not produce valid JSON, an exception is thrown. This exception is thrown prior to parsing and represents a
	 *             programming error, not an internal error. You should check whether the input will produce valid JSON before calling this
	 *             method by using isValidJSONObject:.
	 *
	 *             Setting the NSJSONWritingPrettyPrinted option will generate JSON with whitespace designed to make the output more
	 *             readable. If that option is not set, the most compact possible JSON will be generated.
	 */
	
	public static NSData dataWithJSONObject(Object obj, NSJSONWritingOptions opt, NSError error) {
        String string = "";
        Object toConvert = getJSONFromObject(obj);
        Gson gson = new Gson();
        if (toConvert instanceof HashMap) {

            string = gson.toJson((Map) toConvert);
        } else if (toConvert instanceof List) {

            string = gson.toJson((List) toConvert);

        } else if (toConvert instanceof String) {
            string = (String) toConvert;
            string = "{" + string + "}";

        }

        try {
            return new NSData(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info(String.valueOf(e));
            return null;
        } catch (Exception e) {
            LOGGER.info(String.valueOf(e));
            return null;
        }

	}

    private static Object getJSONFromObject(Object obj) {

        // jsonObject.
        if (obj instanceof NSDictionary) {
            NSDictionary dict = (NSDictionary) obj;
            Map lMap = new HashMap<String, Object>();
            Map<NSString, Object> map = dict.getWrappedDictionary();
            for (Entry<NSString, Object> entry : map.entrySet()) {

                lMap.put(entry.getKey().getWrappedString(), getJSONFromObject(entry.getValue()));
            }
            return lMap;
        } else if (obj instanceof NSString) {
            NSString str = (NSString) obj;
            return str.getWrappedString();
        } else if (obj instanceof NSArray) {
            List result = new ArrayList();
            List list = ((NSArray) obj).getWrappedList();
            for (Object object : list) {
                result.add(getJSONFromObject(object));
            }
            return result;
        } else if (obj instanceof NSNumber) {

            return ((NSNumber) obj).getValue();
        } else {
            return obj;
        }
    }

	/**
	 * Returns a Boolean value that indicates whether a given object can be converted to JSON data.
	 *
	 * @param obj
	 * @return
	 */
	
	public static boolean isValidJSONObject(Object obj) {
		return true;
	}

	
	public static enum NSJSONReadingOptions {
		NSJSONReadingMutableContainers, NSJSONReadingMutableLeaves, NSJSONReadingAllowFragments
	}

	
	public static enum NSJSONWritingOptions {
		NSJSONWritingPrettyPrinted
	}

}