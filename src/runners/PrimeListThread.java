package runners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.Starter;


public class PrimeListThread implements Runnable{
	
	private static final Logger logger = LogManager.getLogger(PrimeListThread.class.getName());
	
	private static final String ONE_MILLION_PRIMES = "OneMillionPrimes.txt";
	private ArrayList<String> primeArray = new ArrayList<>();
	private Thread thread;
	
	
	public void startReadingPrimes() {
		if( thread == null ) {
			thread = new Thread(this, "ReadingThread");
			thread.start();
			stopReadingPrimes();
		}
	}

	public void stopReadingPrimes() {
		if( thread != null ) {
			try {
				thread.join();
				thread = null;
			} catch (InterruptedException e) {
				logger.error("Interrupted Exception occured", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void run() {
		File testFile = new File("PrimeNumberList.txt");
		BufferedReader reader = null;
		ClassLoader loader = Starter.class.getClassLoader();
		URL url = loader.getResource("main/Starter.class");
		System.out.println("getPath: " + url.getPath());
		
		try {
			reader = new BufferedReader(new FileReader(testFile));
			
			String line = "";
			while((line = reader.readLine()) != null ) {
				primeArray.add(line);
			}
		} catch(IOException e) {
			logger.error("Error when trying to create BufferedReader", e);
		}
		
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Error when trying to close BufferedReader", e);
			}
		}
	}
	
	public List<String> getPrimes(){
		return primeArray;
	}
}
