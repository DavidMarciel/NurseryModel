package com.nursery.number.format;

import java.text.DecimalFormat;

import com.nursery.exceptions.FormatSizeException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoordenatesMixer {
	
	public static final double RANGE = 100.00d;

	/** This method receives two doubles as longitude and latitude and merges them in a single string conatining the numbers mixed.
	 *  It works the following way: 
	 *  
	 *  longitude= abc,d;
	 *  latitude= efg,h;
	 *  
	 *  returned= aebfcg,dh
	 *  
	 *  This way we can do a single value comparition hence we are able to sort by a single field and perform binary searches on the bbdd
	 * 
	 * @param value1
	 * @param value2
	 * @return (value1 & value2) mixed char by char
	 */
	public Double merger(double value1, double value2) {

		if ( value1 > 9999.99 || value2 > 9999.99) {
			throw new FormatSizeException();
		}
		
		try {

			String customFormat1 = customFormat("0000.00", value1);
			String customFormat2 = customFormat("0000.00", value2);
			
			StringBuilder output = new StringBuilder(customFormat1.length() * 2);

			for (int i = 0; i < customFormat1.length(); i++) {

				output.append(customFormat1.charAt(i));
				output.append(customFormat2.charAt(i));
			}

			output.deleteCharAt( customFormat1.length() +1);
			output.setCharAt( customFormat1.length() +1, '.');

			return Double.valueOf(output.toString());

		} catch (NumberFormatException e) {
			throw e;
		}
	}

	private String customFormat(String pattern, double value) {

		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);

		return output;
	}

	private void stringSanitization(String value1) {
		Sanitizer sanitizer = new Sanitizer();

		sanitizer.sanitizeString(value1);
	}


	/** This method receives two doubles as longitude and latitude and returns an object with a minimum and a maximum range to do a first filter.
	 *  This can help to filter when the database is too big by giving an small subset of close positions.
	 *  It is not perfect, but it allows the sort of the database by a single value and this way it can minimize the search from O(N) to O(log N)
	 *  
	 *  It works the following way: 
	 *  
	 *  longitude= abc,d;
	 *  latitude= efg,h;
	 *  
	 *  returned= aebfcg,dh +- RANGE
	 *  
	 *  This way we can do a single value comparition hence we are able to sort by a single field and perform binary searches on the bbdd
	 * 
	 * @param value1
	 * @param value2
	 * @return (value1 & value2) mixed char by char
	 */
	public AllowedRangeInterval getMergeBounds(double value1, double value2, Double range) {
		double valueAsDouble = merger( value1, value2);
		
		double totalRange = getTotalRange(range);
		
		double minRange = valueAsDouble - totalRange;
		double maxRange = valueAsDouble + totalRange;

		return new AllowedRangeInterval(minRange, maxRange);
	}

	private double getTotalRange(Double range) {
		double totalRange;
		
		if (range == null) {
			totalRange = RANGE * RANGE;
		} else {
			totalRange = range * range;
		}
		return totalRange;
	}
	
	@Getter
	@AllArgsConstructor
	public class AllowedRangeInterval {
		private double minRange;
		private double maxRange;
	}
	
}