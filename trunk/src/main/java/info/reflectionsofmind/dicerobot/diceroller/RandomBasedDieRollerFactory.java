package info.reflectionsofmind.dicerobot.diceroller;

public class RandomBasedDieRollerFactory implements IDieRollerFactory
{
	public synchronized IDieRoller createDieRoller()
	{
		return new RandomBasedDieRoller();
	}
}
