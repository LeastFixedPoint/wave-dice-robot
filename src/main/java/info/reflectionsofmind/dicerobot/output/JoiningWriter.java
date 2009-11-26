package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;

public class JoiningWriter implements IFormattedBufferedOutput
{
	private final IFormattedBufferedOutput output;
	private final String separator;
	private boolean first = true;
	
	public JoiningWriter(final IFormattedBufferedOutput output, final String separator)
	{
		this.separator = separator;
		this.output = output;
	}
	
	/**
	 * For the first call, does nothing. For the rest - appends separator.
	 * In other words, identival to {@link #append("")}.
	 */
	public JoiningWriter separate() throws OutputException
	{
		if (!this.first)
			this.output.append(this.separator);
		else
			this.first = false;
		
		return this;
	}
	
	/** Appends a new token to the stream, prepending it with separator if needed. */
	public JoiningWriter append(final Object object) throws OutputException
	{
		separate();
		this.output.append(object);
		return this;
	}
	
	/**
	 * Allows to specify whether you want to join new token to
	 * the stream or just append some info after the previous one.
	 * 
	 * @param object
	 *            what to append
	 * @param join
	 *            whether to prepend separator if necessary
	 * @return this
	 * @throws OutputException
	 */
	public JoiningWriter appendPlain(final Object object, final boolean join) throws OutputException
	{
		(join ? this : this.output).append(object);
		return this;
	}
	
	@Override
	public JoiningWriter with(final IStyle style) throws OutputException
	{
		this.output.with(style);
		return this;
	}
	
	@Override
	public void flush() throws OutputException
	{
		this.output.flush();
	}
	
	public boolean isEmpty()
	{
		return this.first;
	}
}
