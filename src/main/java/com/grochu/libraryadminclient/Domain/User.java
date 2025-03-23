package com.grochu.libraryadminclient.Domain;

import lombok.Data;

@Data //automatic getters and setters
public class User
{
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private String addressStreet;
	private String addressCity;
	private String addressState;
	private String addressZip;
}
