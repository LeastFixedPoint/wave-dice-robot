package info.reflectionsofmind.dicerobot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class DiceRobotProfile extends HttpServlet
{
	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			final JSONObject json = new JSONObject()
					.put("profileUrl", DiceRobotServlet.BASE_URL)
					.put("imageUrl", DiceRobotServlet.BASE_URL + "/images/avatar.jpg")
					.put("name", "Dicy");
			
			response.getWriter().write(json.toString());
		}
		catch (final JSONException e)
		{
			throw new RuntimeException(e);
		}
		
	}
}
