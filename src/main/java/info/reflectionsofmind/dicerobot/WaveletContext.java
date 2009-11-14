package info.reflectionsofmind.dicerobot;

import com.google.wave.api.Wavelet;

public class WaveletContext implements IRollExecutionContext
{
	private final Wavelet wavelet;
	
	public WaveletContext(final Wavelet wavelet)
	{
		this.wavelet = wavelet;
	}
	
	@Override
	public String getDefaultConfig()
	{
		return this.wavelet.getDataDocument(DiceRobotServlet.WAVELET_DEFAULT_CONFIG_KEY);
	}
}
