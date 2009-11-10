package info.reflectionsofmind.dicerobot.method.impl.exa;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.List;

public class ExaResult extends AbstractRollResult<ExaRequest>
{
	private final List<Group> groups = new ArrayList<Group>();
	
	public ExaResult(final ExaRequest request)
	{
		super(request);
	}
	
	public ExaResult add(final Integer targetNumber, final Integer... rolls)
	{
		this.groups.add(new Group(targetNumber, asList(rolls)));
		return this;
	}
	
	public List<Group> getGroups()
	{
		return this.groups;
	}
	
	@Override
	public String toString()
	{
		return this.groups.toString();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof ExaResult && ((ExaResult) obj).groups.equals(this.groups);
	}
	
	public final class Group
	{
		private final int targetNumber;
		private final List<Integer> rolls;
		
		public Group(final int targetNumber, final List<Integer> rolls)
		{
			this.targetNumber = targetNumber;
			this.rolls = rolls;
		}
		
		public int getTargetNumber()
		{
			return this.targetNumber;
		}
		
		public List<Integer> getRolls()
		{
			return this.rolls;
		}
		
		@Override
		public String toString()
		{
			return this.rolls + "/" + this.targetNumber;
		}
		
		@Override
		public boolean equals(final Object obj)
		{
			return obj instanceof Group &&
					((Group) obj).targetNumber == this.targetNumber &&
					((Group) obj).rolls.equals(this.rolls);
		}
	}
}
