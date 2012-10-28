
public class CheckBot
{
	public static char decision (String input)
	{
		matchState parser = new matchState();
		parser.updateState(input);
		char check = 'c';
		return check;
	}
	
}
