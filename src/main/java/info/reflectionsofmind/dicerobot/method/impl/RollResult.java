package info.reflectionsofmind.dicerobot.method.impl;

import java.util.Comparator;

public final class RollResult
{
	private final int dieSize;
	private final int result;
	
	public RollResult(final int result, final int dieSize)
	{
		this.dieSize = dieSize;
		this.result = result;
	}
	
	public int getDieSize()
	{
		return this.dieSize;
	}
	
	public int getResult()
	{
		return this.result;
	}
	
	@Override
	public int hashCode()
	{
		return this.result * 31 + this.dieSize;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof RollResult)
		{
			return ((RollResult) obj).getDieSize() == getDieSize() &&
					((RollResult) obj).getResult() == getResult();
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return getResult() + "/" + getDieSize();
	}
	
	public static final Comparator<RollResult> DIE_SIZE_COMPARATOR = new Comparator<RollResult>()
				{
		public int compare(final RollResult o1, final RollResult o2)
					{
			return Integer.valueOf(o1.dieSize).compareTo(o2.dieSize);
		}
	};
	
	public static final Comparator<RollResult> RESULT_COMPARATOR = new Comparator<RollResult>()
				{
		public int compare(final RollResult o1, final RollResult o2)
					{
			return Integer.valueOf(o1.result).compareTo(o2.result);
		}
	};
}