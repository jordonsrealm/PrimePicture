package runners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ReadingThread implements Runnable{
	
	private static final Logger logger = LogManager.getLogger(Logger.class.getName());
	
	private InputStream picFile = null;
	private ArrayList<String> primeArray = new ArrayList<>();
	private Thread thread;
	private int maxFileSize = 0;
	
	
	public ReadingThread(InputStream inputStream) {
		this.picFile = inputStream;
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
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.picFile))){
			String line = "";
			while((line = reader.readLine()) != null ) {
				primeArray.add(line);
			}
			
			setMaxFileSize(primeArray.size());
		} catch (IOException e) {
			logger.error("Some bad shit happened when reading the input stream", e);
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
