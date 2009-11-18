package info.reflectionsofmind.dicerobot.output;

import info.reflectionsofmind.dicerobot.exception.output.OutputException;

import java.util.ArrayList;
import java.util.List;

public class WrappingWriter implements IFormattedBufferedOutput
{
	private final IFormattedBufferedOutput output;
	private final List<Action> actions = new ArrayList<Action>();
	
	public WrappingWriter(final IFormattedBufferedOutput output)
	{
		this.output = output;
	}
	
	@Override
	public WrappingWriter append(final Object object) throws OutputException
	{
		this.actions.add(new Append(object));
		return this;
	}
	
	@Override
	public WrappingWriter with(final IStyle style)
	{
		this.actions.add(new With(style));
		return this;
	}
	
	@Override
	public void flush() throws OutputException
	{
		for (final Action action : this.actions)
		{
			action.flush(this.output);
		}
		
		reset();
	}
	
	public void reset()
	{
		this.actions.clear();
	}
	
	private static abstract class Action
	{
		abstract void flush(IFormattedBufferedOutput output) throws OutputException;
	}
	
	private static final class Append extends Action
	{
		private final Object object;
		
		public Append(final Object object)
		{
			this.object = object;
		}
		
		@Override
		void flush(final IFormattedBufferedOutput output) throws OutputException
		{
			output.append(this.object);
		}
	}
	
	private static final class With extends Action
	{
		private final IStyle style;
		
		public With(final IStyle style)
		{
			this.style = style;
		}
		
		@Override
		void flush(final IFormattedBufferedOutput output) throws OutputException
		{
			output.with(this.style);
		}
	}
}
