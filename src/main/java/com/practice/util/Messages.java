/**
 *
 */
package com.practice.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.practice.constants.enums.MessageCodes;

/**
 * @author sulabhtiwari
 *
 */
public final class Messages {

	private static final String BUNDLE_NAME = "messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		// To hide default constructor;
	}

	public static String getMesssge(MessageCodes key) {
		try {
			return RESOURCE_BUNDLE.getString(key.getMessageCode());
		} catch (final MissingResourceException ex) {
			return key + " not present in message.properties file";
		}
	}

}
