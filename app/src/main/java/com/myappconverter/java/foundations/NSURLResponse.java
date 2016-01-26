
package com.myappconverter.java.foundations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;


public class NSURLResponse extends NSObject implements NSCopying, NSSecureCoding {

	URLConnection urlConnection;
	JSONObject jsonReponse;
	String nameFile;
	URL murl;
	String MIMEType;
	Integer length;
	String name;
	NSURL url ;

	public NSURLResponse initWithJSONResponse(JSONObject response) {
		this.jsonReponse = response;
		return this;
	}

	/**
	 * @Declaration : -Â (id)initWithURL:(NSURL *)URL MIMEType:(NSString *)MIMEType expectedContentLength:(NSInteger)length
	 *              textEncodingName:(NSString *)name
	 * @Description : Returns an initialized NSURLResponse object with the URL, MIME type, length, and text encoding set to given values.
	 * @param URL The URL for the new object.
	 * @param MIMEType The MIME type.
	 * @param length The expected content length.This value should be â€“1 if the expected length is undetermined
	 * @param name The text encoding name. This value may be nil.
	 * @return Return Value An initialized NSURLResponse object with the URL set to URL, the MIME type set to MIMEType, length set to
	 *         length, and text encoding name set to name.
	 * @Discussion This is the designated initializer for NSURLResponse.
	 **/

	public URLConnection initWithURLMIMETypeExpectedContentLengthTextEncodingName(NSURL URL, NSString MIMEType, int length, NSString name) {
		urlConnection = null;
		try {
			urlConnection = murl.openConnection();
			urlConnection.setRequestProperty("content-type", MIMEType.getWrappedString());
			urlConnection.setRequestProperty("Content-Length", "" + length);
			urlConnection.setRequestProperty("Accept-Encoding", name.getWrappedString());
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return urlConnection;
	}

	/**
	 * @Declaration : -Â (long long)expectedContentLength
	 * @Description : Returns the receiverâ€™s expected content length
	 * @return Return Value The receiverâ€™s expected content length, or NSURLResponseUnknownLength if the length canâ€™t be determined.
	 * @Discussion Some protocol implementations report the content length as part of the response, but not all protocols guarantee to
	 *             deliver that amount of data. Clients should be prepared to deal with more or less data.
	 **/

	public long expectedContentLength() {
		return urlConnection.getContentLength();
	}

	/**
	 * @Declaration : -Â (NSString *)suggestedFilename
	 * @Description : Returns a suggested filename for the response data.
	 * @return Return Value A suggested filename for the response data.
	 * @Discussion The method tries to create a filename using the following, in order: A filename specified using the content disposition
	 *             header. The last path component of the URL. The host of the URL. If the host of URL can't be converted to a valid
	 *             filename, the filename â€œunknownâ€? is used. In most cases, this method appends the proper file extension based on the MIME
	 *             type. This method will always return a valid filename regardless of whether or not the resource is saved to disk.
	 **/

	public NSString suggestedFilename() {
		File file = new File(murl.getHost());
		if (!file.exists()) {

			try {
				file.createNewFile();
				StringBuilder text = new StringBuilder();
				urlConnection = murl.openConnection();
				urlConnection.connect();
				InputStreamReader in = new InputStreamReader((InputStream) urlConnection.getContent());
				BufferedReader buff = new BufferedReader(in);
				String line;
				do {
					line = buff.readLine();
					text.append(line + "\n");
				} while (line != null);

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(text.toString());
				bw.close();
				return new NSString(murl.getHost());
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}

		}
		return null;

	}

	/**
	 * @Declaration : -Â (NSString *)MIMEType
	 * @Description : Returns the receiverâ€™s MIME type.
	 * @return Return Value The receiverâ€™s MIME type.
	 * @Discussion The MIME type is often provided by the responseâ€™s originating source. However, that value may be changed or corrected by
	 *             a protocol implementation if it can be determined that the responseâ€™s source reported the information incorrectly. If the
	 *             responseâ€™s originating source does not provide a MIME type, an attempt to guess the MIME type may be made.
	 **/

	public NSString MIMEType() {
		return new NSString(urlConnection.getContentType());
	}

	/**
	 * @Declaration : -Â (NSString *)textEncodingName
	 * @Description : Returns the name of the receiverâ€™s text encoding provided by the responseâ€™s originating source.
	 * @return Return Value The name of the receiverâ€™s text encoding provided by the responseâ€™s originating source, or nil if no text
	 *         encoding was provided by the protocol.
	 * @Discussion You can convert this string to a CFStringEncoding value by calling CFStringConvertIANACharSetNameToEncoding. You can
	 *             subsequently convert that value to an NSStringEncoding value by calling CFStringConvertEncodingToNSStringEncoding.
	 **/

	public NSString textEncodingName() {
		return new NSString(urlConnection.getContentEncoding());

	}

	/**
	 * @Declaration : -Â (NSURL *)URL
	 * @Description : Returns the receiverâ€™s URL.
	 * @return Return Value The receiverâ€™s URL.
	 **/

	public NSURL URL() {
		NSURL nsurl = new NSURL();
		nsurl.setWrappedURL(urlConnection.getURL());
		return nsurl;

	}

	public URLConnection getUrlConnection() {
		return urlConnection;
	}

	public void setUrlConnection(URLConnection urlConnection) {
		this.urlConnection = urlConnection;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public NSURL getUrl() {
		return url;
	}

	public void setUrl(NSURL url) {
		this.url = url;
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}