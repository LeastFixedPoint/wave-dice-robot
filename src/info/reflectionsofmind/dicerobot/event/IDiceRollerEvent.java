package info.reflectionsofmind.dicerobot.event;

import info.reflectionsofmind.dicerobot.DiceRobot;

public interface IDiceRollerEvent
{
	void dispatch(DiceRobot diceRobot);
}
