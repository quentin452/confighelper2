package jml.evilnotch.lib.line;

public class Comment {
	
	public String comment;
	
	public Comment(String str)
	{
		this('#', str);
	}
	
	public Comment(char start, String comment)
	{
		int index = comment.indexOf(start);
		this.comment = index < 0 ? comment : comment.substring(index, comment.length());
	}

}
