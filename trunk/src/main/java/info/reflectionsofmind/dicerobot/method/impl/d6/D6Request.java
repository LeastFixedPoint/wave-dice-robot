package info.reflectionsofmind.dicerobot.method.impl.d6;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

import java.util.ArrayList;
import java.util.List;

public class D6Request implements IRollRequest
{
	private final List<Integer> pips = new ArrayList<Integer>();
	private int numberOfDice = 0;
	
	public int getNumberOfDice()
	{
		return this.numberOfDice;
	}
	
	public List<Integer> getPips()
	{
		return this.pips;
	}
	
	public D6Request addDice(final int howMany)
	{
		this.numberOfDice += howMany;
		return this;
	}
	
	public D6Request addPips(final int howMany)
	{
		this.pips.add(howMany);
		return this;
	}
}
