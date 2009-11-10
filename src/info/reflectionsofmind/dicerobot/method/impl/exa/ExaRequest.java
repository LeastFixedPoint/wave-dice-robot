package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.ArrayList;
import java.util.List;

public class ExaRequest implements IRollRequest
{
	private final List<Group> groups = new ArrayList<Group>();
	private static final int DEFAULT_TN = 7;
	private boolean explodeTens = true;
	private int difficulty = 1;
	
	public ExaRequest add(final int howMany)
	{
		return add(howMany, DEFAULT_TN);
	}
	
	public ExaRequest add(final int howMany, final int targetNumber)
	{
		this.groups.add(new Group(targetNumber, howMany));
		return this;
	}
	
	public ExaRequest explodeTens(final boolean explodeTens)
	{
		this.explodeTens = explodeTens;
		return this;
	}
	
	public boolean isTensExplosive()
	{
		return this.explodeTens;
	}
	
	public List<Group> getGroups()
	{
		return this.groups;
	}
	
	public int getDifficulty()
	{
		return this.difficulty;
	}
	
	public ExaRequest difficulty(final int difficulty)
	{
		this.difficulty = difficulty;
		return this;
	}
	
	@Override
	public String toString()
	{
		return this.groups + (this.explodeTens ? "+10x2" : "") + "+vs" + this.difficulty;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (!(obj instanceof ExaRequest))
			return false;
		
		final ExaRequest that = (ExaRequest) obj;
		
		return that.explodeTens == this.explodeTens
				&& that.difficulty == this.difficulty
				&& that.groups.equals(this.groups);
	}
	
	public static final class Group
	{
		private final int targetNumber;
		private final int numberOfDice;
		
		public Group(final int targetNumber, final int numberOfDice)
		{
			this.targetNumber = targetNumber;
			this.numberOfDice = numberOfDice;
		}
		
		public int getTargetNumber()
		{
			return this.targetNumber;
		}
		
		public int getNumberOfDice()
		{
			return this.numberOfDice;
		}
		
		@Override
		public String toString()
		{
			return this.numberOfDice + "/" + this.targetNumber;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			return obj instanceof Group &&
					((Group) obj).targetNumber == this.targetNumber &&
					((Group) obj).numberOfDice == this.numberOfDice;
		}
	}
}
