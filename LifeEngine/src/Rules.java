import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Rules {
	
	public static RuleSet rule;
	
	public static void setUpRules(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			InputStream ruleFile = GraphicsManager.class.getResourceAsStream("/rules/GameOfLife_rules.json");
			RuleSet ruleset = mapper.readValue(ruleFile, RuleSet.class);
			
			rule = ruleset;
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}
	
}
class RuleSet{
	public int[] deathValues;
	public int[] survivialValues;
	public int[] populationValues;
	public int neighborPadding;
}

