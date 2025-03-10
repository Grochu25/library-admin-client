package com.grochu.libraryadminclient.DAL;

import lombok.Data;


@Data //automatic getters and setters
public class Book 
{
	private Long id;
	private String title;
	private Author author;
	private int publishYear;
}
