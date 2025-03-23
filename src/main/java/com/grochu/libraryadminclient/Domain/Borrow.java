package com.grochu.libraryadminclient.Domain;

import java.time.LocalDate;
import lombok.Data;

@Data //automatic getters and setters
public class Borrow 
{
	private Long id;
	private Copy copy;
	private User user;
	private LocalDate since;
	private LocalDate until;
}
