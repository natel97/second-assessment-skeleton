package com.cooksys.second_assessment.Dto;

import java.sql.Timestamp;

public class HashtagDto {

	private String label;
	private Timestamp firstUsed;
	private Timestamp lastUsed;
	
	public HashtagDto() { }
	
	public HashtagDto(String label, Timestamp firstUsed, Timestamp lastUsed) {
		this.label = label;
		this.firstUsed = firstUsed;
		this.lastUsed = lastUsed;
	}

	public String getLabel() { return label; }
	public Timestamp getFirstUsed() { return firstUsed; }
	public Timestamp getLastUsed() { return lastUsed; }
	
	public void setLabel(String label) { this.label = label; }
	public void setFirstUsed(Timestamp firstUsed) { this.firstUsed = firstUsed; }
	public void setLastUsed(Timestamp lastUsed) { this.lastUsed = lastUsed; }
	
	
}
