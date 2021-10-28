package self.camel.demo;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlComponent extends DefaultComponent {
	private static final Logger LOG = LoggerFactory.getLogger(MySqlComponent.class);
	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		    
        LOG.debug("URI entered:{}", uri);
        LOG.debug("URI remaining entered:{}", remaining);
        LOG.debug("URI params entered:");
        parameters.forEach((key,value) -> {
        	LOG.debug("Param key:{}",key);
        	LOG.debug("Param value:{}",value);
        });
        String mySqlCmd = "mysql --host=mysql_host --port=mysql_port --user=username --database=databasename --password=mySqlpassword < filepath";
        
        mySqlCmd = mySqlCmd.replace("username", (String)parameters.get("userName"));
        mySqlCmd = mySqlCmd.replace("databasename",remaining);
        mySqlCmd = mySqlCmd.replace("mySqlpassword", (String)parameters.get("userPassword"));
        mySqlCmd = mySqlCmd.replace("mysql_host", (String)parameters.get("host"));
        mySqlCmd = mySqlCmd.replace("mysql_port", (String)parameters.get("port"));
        StringBuilder filePath = new StringBuilder((String)parameters.get("scriptDir"));
        filePath.append(File.separator);
        filePath.append((String)parameters.get("scriptFile"));
        mySqlCmd = mySqlCmd.replace("filepath",filePath.toString());
        
        LOG.debug("MySql Command:{}",mySqlCmd);
        File cmdFile = new File(filePath.toString());
        
        if(cmdFile.exists()) {        	
        	LOG.debug("About to fire MySql Command:{}",mySqlCmd);
	        String homeDirectory = System.getProperty("user.home");
	        Process process = Runtime.getRuntime().exec(String.format(mySqlCmd, homeDirectory)); 
	        try(InputStream in = process.getInputStream()){
	        	String result = new String(in.readAllBytes(), StandardCharsets.UTF_8);
	            LOG.debug("MySql Process output:{}",result);
	        }
	        try(InputStream in = process.getErrorStream()){
	        	String result = new String(in.readAllBytes(), StandardCharsets.UTF_8);
	            LOG.debug("MySql Process error:{}",result);
	        }
        }
        
        Endpoint endpoint = this.createEndpoint(uri, remaining, parameters);
		return endpoint;
	}

}
