package runners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.Starter;


public class ReadingThread implements Runnable{
	
	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private static final String ONE_MILLION_PRIMES = "res/OneMillionPrimes.txt";
	private ArrayList<String> primeArray = new ArrayList<>();
	private Thread thread;
	private int maxFileSize = 0;
	
	
	public ReadingThread() {
	}
	
	public void startReadingPrimes() {
		logger.debug("Starting thread for reading primes");
		if(thread == null) {
			thread = new Thread(this, "ReadingThread");
			thread.start();
			stopReadingPrimes();
		}
	}

	public void stopReadingPrimes() {
		logger.debug("Stopping thread for reading primes");
		if(thread != null) {
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
		
		logger.debug("Finding prime number list in run method");
		try {
			if(testFile.exists()) {
				reader = new BufferedReader(new FileReader(testFile.getAbsoluteFile().getAbsolutePath()));
			} else {
				ClassLoader loader = Starter.class.getClassLoader();
				URL url = loader.getResource(ONE_MILLION_PRIMES);
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
			}
			
			String line = "";
			while((line = reader.readLine()) != null ) {
				primeArray.add(line);
			}
			
			setMaxFileSize(primeArray.size());
		}
		catch(IOException e) {
			logger.error("Error when trying to create BufferedReader", e);
		}
		
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Error when trying to close BufferedReader", e);
			}
		}


		logger.info("Ended reading of primes with length: {}", primeArray.size());
	}
	
	private void setMaxFileSize(int newValue) {
		this.maxFileSize = newValue;
	}
	
	public int getMaxFileSize() {
		return maxFileSize;
	}
	
	public List<String> getPrimes(){
		return this.primeArray;
	}
}
