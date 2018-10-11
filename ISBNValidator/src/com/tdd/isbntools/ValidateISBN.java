package com.tdd.isbntools;

public class ValidateISBN {

	private static final int LONG_ISBN_LENGTH = 13;
	private static final int SHORT_ISBN_LENGTH = 10;

	public boolean validateISBN(String isbn) {
		
		if(isbn.length() == SHORT_ISBN_LENGTH) {
			return isThisValideShortISBN(isbn); 
		}
		else if(isbn.length() == LONG_ISBN_LENGTH) {
			return isThisValidLongISBN(isbn);
		}
		else {
			throw new NumberFormatException("ISBN Length should be 10 or 13 digits long");
		}
			
	}

	private boolean isThisValidLongISBN(String isbn) {
		final int LONG_ISBN_MULTIPLIER = 10;
		int total=0;
		for(int i=0;i<LONG_ISBN_LENGTH;++i) {
			if(Character.isDigit(isbn.charAt(i))) {
				if(i%2 == 0)
					total += Character.getNumericValue(isbn.charAt(i)) * 1;
				else
					total += Character.getNumericValue(isbn.charAt(i)) * 3;
			}else {
				throw new NumberFormatException("ISBN should contain only numeric characters");
			}
			
		}
		
		return (total%LONG_ISBN_MULTIPLIER == 0);
	}

	private boolean isThisValideShortISBN(String isbn) {
		final int SHORT_ISBN_MULTIPLIER = 11;
		int total=0;
		for(int i=0;i<SHORT_ISBN_LENGTH;++i) {
			if(!Character.isDigit(isbn.charAt(i))) {
				if(i == (SHORT_ISBN_LENGTH-1) && isbn.charAt(i) == 'X') {
					total += 10;
				}
				else {
					throw new NumberFormatException("ISBN should contain only numeric characters");
				}
					
			}else {
				total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH-i);
			}
		}
		
		return (total%SHORT_ISBN_MULTIPLIER == 0);
	}

}
