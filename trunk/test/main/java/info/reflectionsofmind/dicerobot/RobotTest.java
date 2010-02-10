package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import info.reflectionsofmind.dicerobot.diceroller.LimitedRandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.diceroller.RandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.impl.DefaultMethodFactory;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumConfig;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumConfig.Grouping;
import info.reflectionsofmind.dicerobot.output.LimitingWriter;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.util.List;

import org.junit.Test;

public class RobotTest {
	private static void assertRequest(final String config, final String input, final int start,
			final IWritableText textMock, final RollRequest request) {
		assertEquals(config, request.getMethodCode());
		assertEquals(input, request.getRequest());
		verify(textMock).createOutput(eq(start));
	}

	@Test
	public void shouldExtractQualifiedRequests() {
		final DiceRobot robot = new DiceRobot(null);

		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [sum:2d6+3] blah blah");

		final List<RollRequest> requests = robot.extractRequests(text);

		assertEquals(1, requests.size());
		assertRequest("sum", "2d6+3", 20, text, requests.get(0));
	}

	@Test
	public void shouldExtractNonQualifiedRequests() {
		final DiceRobot robot = new DiceRobot(null);

		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [2d6+3] blah blah");

		final List<RollRequest> requests = robot.extractRequests(text);

		assertEquals(1, requests.size());
		assertRequest(null, "2d6+3", 16, text, requests.get(0));
	}

	@Test
	public void shouldNotExtractEvaluatedRequests() {
		final DiceRobot robot = new DiceRobot(null);

		final IWritableText text = mock(IWritableText.class);
		when(text.getText()).thenReturn("blah blah [2d6+3 = 11 + 3 = 14] blah blah");

		final List<RollRequest> requests = robot.extractRequests(text);

		assertEquals(0, requests.size());
	}

	@Test
	public void shouldCreateLimitedFactoryIfRollLimitIsSet() throws Exception {
		final DiceRobot robot = new DiceRobot(null);
		robot.setMaxNumberOfRolls(10);

		assertEquals(LimitedRandomBasedDieRollerFactory.class, robot.createDieRollerFactory().getClass());
	}

	@Test
	public void shouldCreateUnlimitedFactoryIfRollLimitIsNotSet() throws Exception {
		final DiceRobot robot = new DiceRobot(null);
		assertEquals(RandomBasedDieRollerFactory.class, robot.createDieRollerFactory().getClass());
	}

	@Test
	public void shouldWrapOutputInLimitedWrapperIfWriteLimitIsSet() throws Exception {
		final DiceRobot robot = new DiceRobot(null);
		robot.setMaxResultLength(10);
		assertEquals(LimitingWriter.class, robot.wrapOutput(mock(RollRequest.class)).getClass());
	}

	@Test
	public void shouldTakeMethodCodeFromRequestIfAvailable() {
		final DiceRobot robot = new DiceRobot(null);
		final String code = robot.resolveMethodCode(new RollRequest(null, "sum", null), null);
		assertEquals("sum", code);
	}

	@Test
	public void shouldTakeMethodCodeFromContextIfNotSuppliedInRequest() {
		final DiceRobot robot = new DiceRobot(null);
		final IRequestContext context = mock(IRequestContext.class);
		when(context.getDefaultMethodCode()).thenReturn("sum");
		final String code = robot.resolveMethodCode(new RollRequest(null, null, null), context);
		assertEquals("sum", code);
	}

	@Test
	public void shouldComplainOnRequestsTooLong() throws Exception {
		final DiceRobot robot = new DiceRobot(null).setMaxRequestLength(10);
		final MockOutput output = new MockOutput();
		final RollRequest request = new RollRequest(output, "sum", "12345678901");
		robot.executeRequest(request, null);
		assertEquals("<red>request too long (max 10)</red>", output.getFormatted());
	}

	@Test
	public void shouldComplainOnTooLongOutput() throws Exception {
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxResultLength(2);
		final MockOutput output = new MockOutput();
		final RollRequest request = new RollRequest(output, "sum", "d6+d6");
		robot.executeRequest(request, null);
		assertEquals("<red>result too long (max 2)</red>", output.getFormatted());
	}

	@Test
	public void shouldComplainOnTooManyRolls() throws Exception {
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxNumberOfRolls(10);
		final MockOutput output = new MockOutput();
		final RollRequest request = new RollRequest(output, "sum", "11d6");
		final IRequestContext context = mock(IRequestContext.class);
		when(context.getConfig(any(IRollingMethod.class))).thenReturn(new SumConfig(Grouping.GROUPED));
		robot.executeRequest(request, context);
		assertEquals("<red>too many rolls (max 10)</red>", output.getFormatted());
	}

	@Test
	public void sandbox() throws Exception {
	}
}
