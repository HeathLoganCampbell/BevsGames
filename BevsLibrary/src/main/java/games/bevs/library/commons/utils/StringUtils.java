package games.bevs.library.commons.utils;

import games.bevs.library.commons.CC;

import java.util.Arrays;
import java.util.List;


public class StringUtils {
	private static List<String> ignoreCapWords =  Arrays.asList(new String[]{ "a", "an", "and", "or", "of", "to", "but", "out", "by", "from", "the" });
	
	public static String listToString(List<String> listStr) 
	{
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < listStr.size(); i++) 
		{
			if (i != 0) strBuilder.append(" ");

			String element = listStr.get(i);
			strBuilder.append(element);

			if (i < (listStr.size() - 1))
				strBuilder.append((i == (listStr.size() - 2) ? " and" : ","));
		}
		return strBuilder.toString();
	}

	/**
	 * will turn a string into a boolean
	 * 
	 * @param stateStr
	 * @return
	 */
	public static boolean toBoolean(String stateStr) {
		return Arrays.asList(new String[] { "yes", "true", "on" }).contains(stateStr.toLowerCase());
	}

	/**
	 * States number of times string appaers in string
	 */
	public static int count(String string, String lookingFor) {
		int number = 0;
		int index = -lookingFor.length();

		while ((index = string.indexOf(lookingFor, index + lookingFor.length())) != -1) {
			number++;
		}

		return number;
	}
	
	/**
	 * Repeat a string multiple times
	 * @param string
	 * @param times
	 * @return repeated string
	 */
	public static String repeat(String string, int times)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < times; i++)
            s.append(string);
        return s.toString(); 
    }
	
	private static boolean isIgnoreCaps(String word)
	{
		return ignoreCapWords.contains(word);
	}
	
	public static String capitalize(String original)
	{
		StringBuilder str = new StringBuilder();
		
		for(String word : original.split(" "))
		{
			if(isIgnoreCaps(word))
				continue;
			
			
			str.append(capitalizeFirstLetter(word));
		}
		
		return str.toString();
	}
	
	public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	public static String error(String header, String message) {
		return CC.red + header + CC.bRed + " !! " + CC.r + CC.gray + message;
	}

	public static String success(String header, String message) {
		return CC.green + header + CC.bGreen + " ** " + CC.r + CC.gray + message;
	}

	public static String info(String header, String message) {
		return CC.aqua + header + CC.bAqua + " ?? " + CC.r + CC.gray + message;
	}
}
