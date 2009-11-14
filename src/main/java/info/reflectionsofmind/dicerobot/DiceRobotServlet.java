package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.impl.DefaultMethodFactory;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.Annotation;
import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.Gadget;
import com.google.wave.api.Range;
import com.google.wave.api.RobotMessageBundle;

public class DiceRobotServlet extends AbstractRobotServlet
{
	private static final String SIGNATURE_ANNOTATION = "dicy/signature";
	
	private static final Pattern SIGNABLE_PATTERN = Pattern.compile("\\[[^\\]*]\\]");
	
	public static final String BASE_URL = "http://dice-y.appspot.com";
	public static final String GADGET_URL = BASE_URL + "/gadget.jsp";
	
	public static final String GADGET_DEFAULT_CONFIG_KEY = "defaultRollingMethod";
	public static final String WAVELET_DEFAULT_CONFIG_KEY = GADGET_DEFAULT_CONFIG_KEY;
	public static final String SELF_ADDRESS = "dice-y@appspot.com";
	
	private final IDiceRobotRoller robot;
	private final String defaultConfig;
	
	public DiceRobotServlet()
	{
		this("sum");
	}
	
	public DiceRobotServlet(final String defaultConfig)
	{
		this.defaultConfig = defaultConfig;
		
		this.robot = new DiceRobot(new DefaultMethodFactory())
				.setMaxNumberOfRolls(10000)
				.setMaxRequestLength(200)
				.setMaxResultLength(200);
	}
	
	@Override
	public void processEvents(final RobotMessageBundle bundle)
	{
		if (bundle.wasSelfAdded())
		{
			onSelfAdded(bundle);
			Logger.getAnonymousLogger().severe("=== Hello, world! ======================");
		}
		
		for (final Event event : bundle.getEvents())
		{
			final Gadget gadget = event.getBlip().getDocument().getGadgetView().getGadget(GADGET_URL);
			
			if (gadget != null) onGadgetChanged(event, gadget);
			
			final List<RollRequest> requests = this.robot.extractRequests(new BlipDocument(event.getBlip()));
			
			for (final RollRequest request : requests)
				onRequest(event, request);
			
			// if (event.getType() == EventType.DOCUMENT_CHANGED)
			// {
			// if (!event.getModifiedBy().equals(SELF_ADDRESS))
			// {
			// verifySignatures(event.getBlip());
			// }
			// }
		}
	}
	
	public void onRequest(final Event event, final RollRequest request)
	{
		try
		{
			request.getOutput().append(" = ");
			
			try
			{
				this.robot.executeRequest(request, new WaveletContext(event.getWavelet()));
				request.getOutput().flush();
				// updateSignatures(event.getBlip());
			}
			catch (final FatalException e)
			{
				request.getOutput().append("fatal error").with("style/color", "red").flush();
				throw new RuntimeException(e);
			}
		}
		catch (final OutputException e1)
		{
			throw new RuntimeException(e1);
		}
	}
	
	public void onGadgetChanged(final Event event, final Gadget gadget)
	{
		final String newDefaultConfig = gadget.getField(GADGET_DEFAULT_CONFIG_KEY);
		event.getWavelet().setDataDocument(WAVELET_DEFAULT_CONFIG_KEY, newDefaultConfig);
	}
	
	public void onSelfAdded(final RobotMessageBundle bundle)
	{
		final Blip homeBlip = bundle.getWavelet().appendBlip();
		homeBlip.getDocument().append("Hello, I'm Dicy!");
		final Gadget gadget = new Gadget(GADGET_URL);
		homeBlip.getDocument().getGadgetView().append(gadget);
		gadget.setField(GADGET_DEFAULT_CONFIG_KEY, this.defaultConfig);
	}
	
	/** Updates all signatures in the blip to be correct. */
	public static void updateSignatures(final Blip blip)
	{
		final Matcher matcher = SIGNABLE_PATTERN.matcher(blip.getDocument().getText());
		
		while (matcher.find())
		{
			final Range range = new Range(matcher.start(), matcher.end());
			final List<Annotation> annotations = blip.getDocument().getAnnotations(range);
			boolean hasSignature = false;
			
			for (final Annotation annotation : annotations)
			{
				if (annotation.getName().equals(SIGNATURE_ANNOTATION))
				{
					annotation.setValue(matcher.group());
					hasSignature = true;
				}
			}
			
			if (!hasSignature)
			{
				blip.getDocument().setAnnotation(range, SIGNATURE_ANNOTATION, matcher.group());
				Logger.getAnonymousLogger().severe("Adding signature for " + matcher.group());
			}
		}
	}
	
	/** Verifies that all signatures in the blip are correct. */
	public static void verifySignatures(final Blip blip)
	{
		final Matcher matcher = SIGNABLE_PATTERN.matcher(blip.getDocument().getText());
		
		while (matcher.find())
		{
			final Range range = new Range(matcher.start(), matcher.end());
			final List<Annotation> annotations = blip.getDocument().getAnnotations(range);
			
			for (final Annotation annotation : annotations)
			{
				if (annotation.getName().equals(SIGNATURE_ANNOTATION))
				{
					if (!verifySignature(matcher.group(), annotation.getValue()))
					{
						Logger.getAnonymousLogger().severe("Signature violation for " + matcher.group());
						blip.createChild().getDocument().append("Signature violation!");
					}
				}
			}
		}
	}
	
	public static boolean verifySignature(final String text, final String signature)
	{
		return text.equals(signature);
	}
}