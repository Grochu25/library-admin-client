package com.grochu.libraryadminclient.Domain;

import lombok.Data;

@Data //automatic getters and setters
public class Author {
	private Long id;
	private String name;
	private Integer birthYear;
	private String description;
}
