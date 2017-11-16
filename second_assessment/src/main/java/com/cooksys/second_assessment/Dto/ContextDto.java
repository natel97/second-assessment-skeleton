package com.cooksys.second_assessment.Dto;

import java.util.List;

public class ContextDto {

	private TweetDto target;
	private List<TweetDto> before;
	private List<TweetDto> after;
	
	public ContextDto() {  }
	
	public TweetDto getTarget() { return target; }
	public List<TweetDto> getBefore() { return before; }
	public List<TweetDto> getAfter() { return after; }
	
	public void setTarget(TweetDto tweet) { this.target = tweet; }
	public void setBefore(List<TweetDto> before) { this.before = before; }
	public void setAfter(List<TweetDto> after) { this.after = after; }	
}
