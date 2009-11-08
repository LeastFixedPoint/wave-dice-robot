package info.reflectionsofmind.dicerobot.method.impl;

public final class Roll
{
	private final int dieSize;
	private final int count;
	
	public Roll(final int count, final int dieSize)
	{
		this.dieSize = dieSize;
		this.count = count;
	}
	
	public int getDieSize()
	{
		return this.dieSize;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	@Override
	public int hashCode()
	{
		return getDieSize() * 31 + getCount();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Roll)
		{
			return ((Roll) obj).getDieSize() == getDieSize() &&
					((Roll) obj).getCount() == getCount();
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return this.count + "d" + this.dieSize;
	}
}