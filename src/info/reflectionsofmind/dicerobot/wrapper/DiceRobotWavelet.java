package info.reflectionsofmind.dicerobot.wrapper;

import com.google.wave.api.Blip;
import com.google.wave.api.Wavelet;

public class DiceRobotWavelet
{
	private static final String DEFAULT_ROLLING_METHOD_KEY = "defaultRollingMethod";
	private final Wavelet wavelet;
	
	public DiceRobotWavelet(final Wavelet wavelet)
	{
		this.wavelet = wavelet;
	}
	
	public void setDefaultRollingMethod(final String method)
	{
		this.wavelet.setDataDocument(DEFAULT_ROLLING_METHOD_KEY, method);
	}
	
	public String getDefaultRollingMethod()
	{
		return this.wavelet.getDataDocument(DEFAULT_ROLLING_METHOD_KEY);
	}
	
	public DiceRobotBlip appendBlip()
	{
		return new DiceRobotBlip(this.wavelet.appendBlip());
	}
	
	public DiceRobotHomeBlip createHomeBlip()
	{
		final Blip homeBlip = this.wavelet.appendBlip();
		homeBlip.getDocument().append("Hello, I'm Dicy!");
		homeBlip.getDocument().getGadgetView().append(new DiceRobotGadget().getGadget());
		return new DiceRobotHomeBlip(homeBlip);
	}
}
