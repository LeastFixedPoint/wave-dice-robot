package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

import java.util.ArrayList;
import java.util.List;

public class D6Request implements IRollRequest
{
	private final List<Token> tokens = new ArrayList<Token>();
	
	public D6Request add(final Token... tokens)
	{
		this.tokens.addAll(asList(tokens));
		return this;
	}
	
	public List<Token> getTokens()
	{
		return this.tokens;
	}
	
	public abstract static class Token
	{
		private final int value;
		
		public Token(final int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return this.value;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			return (obj.getClass() == this.getClass()) && (((Token) obj).value == this.value);
		}
		
		public abstract void render(IFormattedBufferedOutput output, D6Result result);
	}
	
	public static class Die extends Token
	{
		public Die(final int value)
		{
			super(value);
		}
		
		@Override
		public void render(final IFormattedBufferedOutput output, final D6Result result)
		{
			result.getRolls(this);
		}
	}
	
	public static class Pip extends Token
	{
		public Pip(final int value)
		{
			super(value);
		}
		
		@Override
		public void render(final IFormattedBufferedOutput output, final D6Result result)
		{
		}
	}
	
	public static class Wild extends Token
	{
		public Wild()
		{
			super(1);
		}
		
		@Override
		public void render(final IFormattedBufferedOutput output, final D6Result result)
		{
		}
	}
}
