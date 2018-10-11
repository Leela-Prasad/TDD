package com.tdd.isbntools;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class BookManagerTest {

	/*@Test
	public void testGetLocatorCode() {
		
		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn,"O Mice and Men","J Steinbeck");
			}
		};
		
		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
			@Override
			public Book lookup(String isbn) {
				return null;
			}
		};
		
		BookManager bookManager = new BookManager();
		bookManager.setDatabaseService(testDatabaseService);
		bookManager.setWebService(testWebService);
		String isbn="0140177396";
		String locatorCode = bookManager.getLocatorCode(isbn);
		assertEquals("7396J4",locatorCode);
	}*/
	
	private ExternalISBNDataService databaseService;
	private ExternalISBNDataService webService;
	private BookManager bookManager;
	
	@Before
	public void setUp() {
		databaseService = mock(ExternalISBNDataService.class);
		webService = mock(ExternalISBNDataService.class);
		bookManager = new BookManager();
		bookManager.setDatabaseService(databaseService);
		bookManager.setWebService(webService);
	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {
		when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396","title","author"));
		
		String isbn="0140177396";
		String locatorCode = bookManager.getLocatorCode(isbn);
		//assertEquals("7396J4",locatorCode);
		
		verify(databaseService,times(1)).lookup("0140177396");
		//verify(webService,times(0)).lookup(anyString());
		verify(webService,never()).lookup(anyString());
	}
	
	@Test
	public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
		when(databaseService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396","title","author"));
		
		String isbn="0140177396";
		String locatorCode = bookManager.getLocatorCode(isbn);
		//assertEquals("7396J4",locatorCode);
		
		verify(databaseService,times(1)).lookup("0140177396");
		verify(webService,times(1)).lookup("0140177396");
	}
}
