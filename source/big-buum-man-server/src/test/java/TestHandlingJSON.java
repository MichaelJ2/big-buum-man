import at.big_buum_man.server.gui.helper.JSONStreambuilder;
import org.junit.Test;

public class TestHandlingJSON 
{
	@Test
	public static void main(String[] args) 
	{
		JSONStreambuilder jsb = new JSONStreambuilder();
		jsb.createJSONObject();
		jsb.setString("1234", "Tests");
		jsb.setBoolean(true, "lala");
		jsb.setInt(666, "Devil");
		System.out.println(jsb.getStream());
	}
}
