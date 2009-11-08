package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.ArrayList;
import java.util.List;

public class SumRequest implements IRollRequest
{
	private final List<Token> tokens = new ArrayList<Token>();
	
	public SumRequest add(final int number)
	{
		this.tokens.add(new Number(number));
		return this;
	}
	
	public SumRequest add(final int count, final int dieSize)
	{
		this.tokens.add(new Roll(count, dieSize));
		return this;
	}
	
	public List<Token> getTokens()
	{
		return this.tokens;
	}
	
	public static abstract class Token
	{
	}
	
	public static final class Number extends Token
	{
		private final int number;
		
		public Number(final int number)
		{
			this.number = number;
		}
		
		public int getNumber()
		{
			return this.number;
		}
		
		@Override
		public int hashCode()
		{
			return this.number;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			return (obj instanceof Number) && (((Number) obj).number == this.number);
		}
		
		@Override
		public String toString()
		{
			return String.valueOf(this.number);
		}
	}
	
	public static final class Roll extends Token
	{
		private final int count;
		private final int dieSize;
		
		public Roll(final int count, final int dieSize)
		{
			this.count = count;
			this.dieSize = dieSize;
		}
		
		public int getCount()
		{
			return this.count;
		}
		
		public int getDieSize()
		{
			return this.dieSize;
		}
		
		@Override
		public int hashCode()
		{
			return this.count * 31 + this.dieSize;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			return (obj instanceof Roll) && (((Roll) obj).count == this.count) && (((Roll) obj).dieSize == this.dieSize);
		}
		
		@Override
		public String toString()
		{
			return this.count + "d" + this.dieSize;
		}
	}
}
