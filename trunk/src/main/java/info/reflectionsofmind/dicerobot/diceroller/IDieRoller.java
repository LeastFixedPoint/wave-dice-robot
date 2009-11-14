package info.reflectionsofmind.dicerobot.diceroller;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

public interface IDieRoller
{
	Integer roll(Integer dieSize) throws CannotMakeRollException;
}
