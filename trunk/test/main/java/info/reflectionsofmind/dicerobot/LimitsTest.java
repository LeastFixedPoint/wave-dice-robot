package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import info.reflectionsofmind.dicerobot.method.impl.DefaultMethodFactory;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import org.junit.Test;

public class LimitsTest
{
	@Test
	public void shouldNotAllowTooLongRequests() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxRequestLength(4);
		final MockOutput output = new MockOutput();

		robot.executeRequest(new RollRequest(output, "sum", "2d6+5"), null);

		assertEquals("<red>request too long (max 4)</red>", output.getFormatted());
	}

	@Test
	public void shouldNotAllowTooLongResults() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxResultLength(4);
		final MockOutput output = new MockOutput();

		robot.executeRequest(new RollRequest(output, "sum", "1+2+3"), mock(IRequestContext.class));

		assertEquals("<red>result too long (max 4)</red>", output.getFormatted());
	}

	@Test
	public void shouldNotAllowTooManyRolls() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxNumberOfRolls(3);
		final MockOutput output = new MockOutput();

		robot.executeRequest(new RollRequest(output, "sum", "2d6+2d8"), mock(IRequestContext.class));

		assertEquals("<red>too many rolls (max 3)</red>", output.getFormatted());
	}

	@Test
	public void shouldNotAllowNumbersTooLong() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxNumberOfRolls(3);
		final MockOutput output = new MockOutput();

		robot.executeRequest(new RollRequest(output, "sum", "100d9999999999999999"), mock(IRequestContext.class));

		assertEquals("<red>invalid number</red>", output.getFormatted());
	}
}
