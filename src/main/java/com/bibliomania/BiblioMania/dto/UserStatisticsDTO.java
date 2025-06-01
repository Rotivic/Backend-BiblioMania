// src/main/java/com/bibliomania/BiblioMania/dto/UserStatisticsDTO.java
package com.bibliomania.BiblioMania.dto;

import java.util.List;
import java.util.Map;

public class UserStatisticsDTO {

    private int totalReadBooks;
    private int totalListsCreated;

    private Map<String, Integer> booksReadPerMonth;
    private Map<String, Integer> categoriesConsumed;

    public UserStatisticsDTO() {}

    public UserStatisticsDTO(int totalReadBooks, int totalListsCreated,
                             Map<String, Integer> booksReadPerMonth,
                             Map<String, Integer> categoriesConsumed) {
        this.totalReadBooks = totalReadBooks;
        this.totalListsCreated = totalListsCreated;
        this.booksReadPerMonth = booksReadPerMonth;
        this.categoriesConsumed = categoriesConsumed;
    }

	public int getTotalReadBooks() {
		return totalReadBooks;
	}

	public void setTotalReadBooks(int totalReadBooks) {
		this.totalReadBooks = totalReadBooks;
	}

	public int getTotalListsCreated() {
		return totalListsCreated;
	}

	public void setTotalListsCreated(int totalListsCreated) {
		this.totalListsCreated = totalListsCreated;
	}

	public Map<String, Integer> getBooksReadPerMonth() {
		return booksReadPerMonth;
	}

	public void setBooksReadPerMonth(Map<String, Integer> booksReadPerMonth) {
		this.booksReadPerMonth = booksReadPerMonth;
	}

	public Map<String, Integer> getCategoriesConsumed() {
		return categoriesConsumed;
	}

	public void setCategoriesConsumed(Map<String, Integer> categoriesConsumed) {
		this.categoriesConsumed = categoriesConsumed;
	}

    
}
