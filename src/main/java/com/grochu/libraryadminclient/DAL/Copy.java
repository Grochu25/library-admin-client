package com.grochu.libraryadminclient.DAL;

import lombok.Data;


@Data //automatic getters and setters
public class Copy 
{
	private Long id;
	private Book book;
	private Boolean destroyed;

	public Copy(Long id, Book book)
	{
		this.id = id;
		this.book = book;
		destroyed = false;
	}

	public Copy()
	{
		this.id = null;
		this.book = null;
		destroyed = false;
	}
}
