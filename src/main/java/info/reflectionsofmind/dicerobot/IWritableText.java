package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public interface IWritableText
{
	String getText();
	
	IFormattedBufferedOutput createOutput(int start);
}
