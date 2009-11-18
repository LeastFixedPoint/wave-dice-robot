package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

import com.google.wave.api.Wavelet;

public class WaveletContext implements IRequestContext
{
	private final Wavelet wavelet;
	
	public WaveletContext(final Wavelet wavelet)
	{
		this.wavelet = wavelet;
	}
	
	@Override
	public String getDefaultMethodCode()
	{
		return this.wavelet.getDataDocument(DiceRobotServlet.DEFAULT_METHOD_KEY);
	}
	
	@Override
	public IRollConfig getConfig(final IRollingMethod method)
	{
		return method.createConfig(this.wavelet.getDataDocuments());
	}
}
