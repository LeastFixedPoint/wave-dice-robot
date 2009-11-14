package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.ArrayList;
import java.util.List;

public final class DitvRequest implements IRollRequest
{
	private final List<Roll> rolls = new ArrayList<Roll>();
	
	public DitvRequest add(final int count, final int dieSize)
	{
		this.rolls.add(new Roll(count, dieSize));
		return this;
	}
	
	public List<Roll> getRolls()
	{
		return this.rolls;
	}
	
	public static final class Roll
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
}
