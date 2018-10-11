package com.tdd.isbntools;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidateISBNTest {

	@Test
	public void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.validateISBN("0140449116");
		assertTrue("first value",result);
		
		result = validator.validateISBN("0140177396");
		assertTrue("second value",result);
	}
	
	@Test
	public void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.validateISBN("9781853260087");
		assertTrue("first value", result);
		
		result = validator.validateISBN("9781853267338");
		assertTrue("second value",result);
	}
	
	@Test
	public void tenDigitISBNEndingWithXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.validateISBN("012000030X");
		assertTrue(result);
	}
	
	@Test
	public void checkAnInValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.validateISBN("0140449117");
		assertFalse(result);
	}
	
	@Test
	public void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.validateISBN("9781853267328");
		assertFalse(result);
	}
	
	@Test(expected=NumberFormatException.class)
	public void nineDigitISBNAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.validateISBN("123456789");
	}
	
	@Test(expected=NumberFormatException.class)
	public void nonNumeric10DigitISBNAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		validator.validateISBN("helloworld");
	}
	
	@Test(expected=NumberFormatException.class)
	public void nonNumeric13DigitISBNAreNotAllowed2() {
		ValidateISBN validator = new ValidateISBN();
		validator.validateISBN("helloworldlee");
	}
}
