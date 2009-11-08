package info.reflectionsofmind.dicerobot.diceroller;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;


public interface IDieRoller
{
	int roll(int dieSize) throws CannotMakeRollException;
}
