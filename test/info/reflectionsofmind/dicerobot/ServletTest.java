package info.reflectionsofmind.dicerobot;

import static info.reflectionsofmind.dicerobot.TestingUtil.replicate;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.google.wave.api.Blip;
import com.google.wave.api.Event;
import com.google.wave.api.Gadget;
import com.google.wave.api.GadgetView;
import com.google.wave.api.RobotMessageBundle;
import com.google.wave.api.TextView;
import com.google.wave.api.Wavelet;

/** This is more like a functional test suite, with randoms and stuff. */
public class ServletTest
{
	public static RobotMessageBundle mockRollBundle(final String blipText, final String defaultMethod)
	{
		final RobotMessageBundle bundle = mock(RobotMessageBundle.class);
		final Event event = mock(Event.class);
		final Blip blip = mock(Blip.class);
		final TextView textView = mock(TextView.class);
		final GadgetView gadgetView = mock(GadgetView.class);
		final Wavelet wavelet = mock(Wavelet.class);
		
		when(event.getWavelet()).thenReturn(wavelet);
		when(wavelet.getDataDocument(eq(DiceRobotServlet.WAVELET_DEFAULT_CONFIG_KEY))).thenReturn(defaultMethod);
		when(textView.getText()).thenReturn(blipText);
		when(textView.getGadgetView()).thenReturn(gadgetView);
		when(blip.getDocument()).thenReturn(textView);
		when(event.getBlip()).thenReturn(blip);
		when(bundle.getEvents()).thenReturn(asList(event));
		
		return bundle;
	}
	
	public static RobotMessageBundle mockChangeMethodBundle(final String newMethod)
	{
		final RobotMessageBundle bundle = mock(RobotMessageBundle.class);
		final Event event = mock(Event.class);
		final Blip blip = mock(Blip.class);
		final TextView textView = mock(TextView.class);
		final GadgetView gadgetView = mock(GadgetView.class);
		final Gadget gadget = mock(Gadget.class);
		final Wavelet wavelet = mock(Wavelet.class);
		
		when(gadget.getField(eq(DiceRobotServlet.GADGET_DEFAULT_CONFIG_KEY))).thenReturn(newMethod);
		when(gadgetView.getGadget(eq(DiceRobotServlet.GADGET_URL))).thenReturn(gadget);
		when(textView.getGadgetView()).thenReturn(gadgetView);
		when(textView.getText()).thenReturn("");
		when(blip.getDocument()).thenReturn(textView);
		when(event.getBlip()).thenReturn(blip);
		when(event.getWavelet()).thenReturn(wavelet);
		when(bundle.getEvents()).thenReturn(asList(event));
		
		return bundle;
	}
	
	@Test
	public void shouldRollSimpleRolls()
	{
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockRollBundle("[2d6]", "sum");
		final TextView textView = bundle.getEvents().get(0).getBlip().getDocument();
		
		servlet.processEvents(bundle);
		
		verify(textView).insert(eq(4), matches(" = \\d+"));
	}
	
	@Test
	public void shouldRollQualifiedRolls()
	{
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockRollBundle("[ditv:2d6]", "sum");
		final TextView textView = bundle.getEvents().get(0).getBlip().getDocument();
		
		servlet.processEvents(bundle);
		
		verify(textView).insert(eq(9), matches(" = \\d\\d \\d\\d"));
	}
	
	@Test
	public void shouldSetDefaultFromGadget()
	{
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockChangeMethodBundle("ditv");
		final Wavelet wavelet = bundle.getEvents().get(0).getWavelet();
		
		servlet.processEvents(bundle);
		
		verify(wavelet).setDataDocument(eq(DiceRobotServlet.WAVELET_DEFAULT_CONFIG_KEY), eq("ditv"));
	}
	
	@Test
	public void shouldNotAllowRollsLargerThan10000()
	{
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockRollBundle("[10001d6]", "sum");
		final TextView textView = bundle.getEvents().get(0).getBlip().getDocument();
		
		servlet.processEvents(bundle);
		
		verify(textView).insert(eq(8), eq(" = too many rolls (max 10000)"));
	}
	
	@Test
	public void shouldNotAllowResultsLongerThan200()
	{
		final String request = replicate(50, "d6+") + "d6";
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockRollBundle("[" + request + "]", "sum");
		final TextView textView = bundle.getEvents().get(0).getBlip().getDocument();
		
		servlet.processEvents(bundle);
		
		verify(textView).insert(eq(153), eq(" = result too long (max 200)"));
	}
	
	@Test
	public void shouldNotAllowRequestsLongerThan200()
	{
		final String request = replicate(50, "1d6+") + "1d6";
		final DiceRobotServlet servlet = new DiceRobotServlet();
		final RobotMessageBundle bundle = mockRollBundle("[" + request + "]", "sum");
		final TextView textView = bundle.getEvents().get(0).getBlip().getDocument();
		
		servlet.processEvents(bundle);
		
		verify(textView).insert(eq(204), eq(" = request too long (max 200)"));
	}
}
