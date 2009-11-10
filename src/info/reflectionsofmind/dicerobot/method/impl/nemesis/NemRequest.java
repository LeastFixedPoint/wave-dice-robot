package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.HashSet;
import java.util.Set;

public class NemRequest implements IRollRequest
{
	private final Set<Integer> expertDice = new HashSet<Integer>();
	private int standardDice = 0;
	private int trumpDice = 0;
	
	public NemRequest addStandard(final int howMany)
	{
		this.standardDice += howMany;
		return this;
	}
	
	public NemRequest addExpert(final Integer... values)
	{
		this.expertDice.addAll(asList(values));
		return this;
	}
	
	public NemRequest addTrump(final int howMany)
	{
		this.trumpDice += howMany;
		return this;
	}
	
	public int getStandardDice()
	{
		return this.standardDice;
	}
	
	public int getTrumpDice()
	{
		return this.trumpDice;
	}
	
	public Set<Integer> getExpertDice()
	{
		return this.expertDice;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = this.standardDice;
		hashCode = hashCode * 31 + this.trumpDice;
		hashCode = hashCode * 33 + this.expertDice.hashCode();
		return hashCode;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof NemRequest)
		{
			final boolean stdOk = ((NemRequest) obj).standardDice == this.standardDice;
			final boolean trmOk = ((NemRequest) obj).trumpDice == this.trumpDice;
			final boolean expOk = ((NemRequest) obj).expertDice.equals(this.expertDice);
			
			return stdOk && trmOk && expOk;
		}
		else
		{
			return false;
		}
	}
}
