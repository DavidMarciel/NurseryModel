package com.nursery.number.format;

import com.nursery.exceptions.FormatValidationException;

public class Sanitizer {

	/** If the incomming string contains sql injections it throws a FormatValidationException
	 * 
	 * @param str
	 * @return the same string if it is valid
	 */
	public String sanitizeString(String str) {

		if (isntSafeString(str)) {
			throw new FormatValidationException();
		}

		return str;
	}

	private boolean isntSafeString(String str) {

		// TODO add sql injection sanitization

		return false;
	}

}
