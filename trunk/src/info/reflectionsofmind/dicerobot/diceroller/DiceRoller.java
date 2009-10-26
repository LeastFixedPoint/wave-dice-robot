package info.reflectionsofmind.dicerobot.diceroller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiceRoller
{
	private final IDieRoller roller;
	
	public DiceRoller(final IDieRoller roller)
	{
		this.roller = roller;
	}
	
	public List<DieRollResult> roll(final Integer number, final Integer die)
	{
		final List<DieRollResult> rolls = new ArrayList<DieRollResult>(number);
		
		for (Integer i = 0; i < number; i++)
			rolls.add(new DieRollResult(die, this.roller.roll(die)));
		
		return rolls;
	}
	
	public static final class DieRollResult
	{
		public final Integer dieSize;
		public final Integer result;
		
		public DieRollResult(final Integer die, final Integer result)
		{
			this.dieSize = die;
			this.result = result;
		}
		
		public static final Comparator<DieRollResult> DIE_SIZE_COMPARATOR = new Comparator<DieRollResult>()
						{
			public int compare(final DieRollResult o1, final DieRollResult o2)
							{
				return new Integer(o1.dieSize).compareTo(o2.dieSize);
			}
		};
		
		public static final Comparator<DieRollResult> RESULT_COMPARATOR = new Comparator<DieRollResult>()
						{
			public int compare(final DieRollResult o1, final DieRollResult o2)
							{
				return new Integer(o1.result).compareTo(o2.result);
			}
		};
	}
	
}
