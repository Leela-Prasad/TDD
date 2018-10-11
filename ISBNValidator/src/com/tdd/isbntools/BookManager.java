package com.tdd.isbntools;

public class BookManager {

	private ExternalISBNDataService webService;
	private ExternalISBNDataService databaseService;
	
	public void setWebService(ExternalISBNDataService webService) {
		this.webService = webService;
	}

	public void setDatabaseService(ExternalISBNDataService databaseService) {
		this.databaseService = databaseService;
	}

	public String getLocatorCode(String isbn) {
		Book book = databaseService.lookup(isbn);
		if(null == book)
			book = webService.lookup(isbn);
		StringBuilder locatorCode = new StringBuilder();
		locatorCode.append(book.getIsbn().substring(isbn.length()-4));
		locatorCode.append(book.getAuthor().substring(0, 1));
		locatorCode.append(book.getTitle().split(" ").length);
		return locatorCode.toString();
	}

	
}
