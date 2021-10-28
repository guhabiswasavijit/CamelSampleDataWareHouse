package self.camel.demo;

import java.io.File;

import org.apache.camel.ExchangeProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputeDelay {
	private static final Logger LOG = LoggerFactory.getLogger(ComputeDelay.class);
	
	@SuppressWarnings("static-access")
	public long computeDelay(@ExchangeProperty("output_directory") String scriptDir, @ExchangeProperty("output_file_name") String scriptFile) throws InterruptedException {
		
	    StringBuilder filePath = new StringBuilder(scriptDir);
	    filePath.append(File.separator);
	    filePath.append(scriptFile);
	    File cmdFile = new File(filePath.toString());
	    LOG.debug("Got file path:{}",filePath.toString());
	    while(!cmdFile.exists()) {
	    	LOG.debug("Thread Name:{}",Thread.currentThread().getName());
	    	Thread.currentThread().sleep(100);
	    }
		return 0;
		
	}

}
