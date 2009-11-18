package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;

public interface IFormattedBufferedOutput
{
	IFormattedBufferedOutput append(Object object) throws OutputException;
	
	IFormattedBufferedOutput with(IStyle style) throws OutputException;
	
	/** Flushes changes buffered in this buffer to the underlying stream. */
	void flush() throws OutputException;
}
