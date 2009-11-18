package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.util.Arrays.asList;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollResult;

import java.util.ArrayList;
import java.util.List;

public class D6Result extends AbstractRollResult<D6Request>
{
	private final List<Integer> diceRolls = new ArrayList<Integer>();
	private final List<Integer> wildRolls = new ArrayList<Integer>();
	
	public D6Result(final D6Request request)
	{
		super(request);
	}
	
	public D6Result addDice(final Integer... rolls)
	{
		this.diceRolls.addAll(asList(rolls));
		return this;
	}
	
	public D6Result addWild(final Integer... rolls)
	{
		this.wildRolls.addAll(asList(rolls));
		return this;
	}
	
	public List<Integer> getDiceRolls()
	{
		return this.diceRolls;
	}
	
	public List<Integer> getWildRolls()
	{
		return this.wildRolls;
	}
}
