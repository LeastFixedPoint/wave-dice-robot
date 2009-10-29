package info.reflectionsofmind.dicerobot.event;

import info.reflectionsofmind.dicerobot.DiceRobot;
import info.reflectionsofmind.dicerobot.wrapper.DiceRobotWavelet;

import com.google.wave.api.Wavelet;

public class SelfAddedEvent extends AbstractDiceRobotEvent
{
	private final DiceRobotWavelet wavelet;
	
	public SelfAddedEvent(final Wavelet wavelet)
	{
		super(null);
		this.wavelet = new DiceRobotWavelet(wavelet);
	}
	
	@Override
	public DiceRobotWavelet getWavelet()
	{
		return this.wavelet;
	}
	
	@Override
	public void dispatch(final DiceRobot diceRobot)
	{
		diceRobot.onEvent(this);
	}
}
