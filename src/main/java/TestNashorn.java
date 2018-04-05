import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestNashorn {
    private static Logger logger = LogManager.getLogger();

	public static void main(String[] args) throws ScriptException {

		InputStream in = TestNashorn.class.getClassLoader().getResourceAsStream("hello.js");
		
		InputStreamReader reader = new InputStreamReader(in);
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		
        Compilable compilable = (Compilable) engine;
        CompiledScript compiled = compilable.compile(reader);
        Bindings bindings = engine.createBindings();
        bindings.put( "logger", logger );

        long acum = 0;
        long counter = 0;
        for(int i=0; i < 10000; i++) {
        	long t0 = System.currentTimeMillis();
            compiled.eval(bindings);
            long t1 = System.currentTimeMillis();
            counter++;
            acum += (t1-t0);
        }
        
        System.out.println("execution time was: " + (double)acum/counter);
	}
}
