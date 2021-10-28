package self.camel.demo;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.SupervisingRouteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("routeStartUpController")
public class RouteStartUpController {
	private static final Logger LOG = LoggerFactory.getLogger(RouteStartUpController.class);
	@Value("${script_file_name}")
	private String scriptFile;
	public void startRoutes() throws Exception {	
		/*CamelContext dCtx = new DefaultCamelContext();
    	dCtx.setAutoStartup(false);
    	SupervisingRouteController svc = dCtx.getRouteController().supervising();
	    svc.setThreadPoolSize(2);
	    svc.tartRoute("ddlScriptGeneratorRoute");
	    File cmdFile = null;
	    do{
	    	cmdFile = new File(scriptFile);
	    	LOG.debug("Generating ddl script");
	    }while(!cmdFile.exists());
	    svc.startRoute("ddlScriptRunRoute");
	    dCtx.shutdown();
	    dCtx.close();*/
	}
}
