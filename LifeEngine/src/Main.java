
import java.awt.FontFormatException;
import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException, FontFormatException {
		WindowManager.CreateWindow();
		Rules.setUpRules();
		CellularAutomata.play();
	}
	

}
