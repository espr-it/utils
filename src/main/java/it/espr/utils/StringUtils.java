package it.espr.utils;

public class StringUtils {

	public boolean isBlank(String text) {
		if (text == null)
			return true;
		if (text.trim().equals(""))
			return true;
		return false;
	}
}
