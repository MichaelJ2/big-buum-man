import at.big_buum_man.server.gui.Main;
import org.junit.Test;

public class TestMain {

    @Test
    public void testMain() {
        Main main = new Main();
        try {
            main.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
