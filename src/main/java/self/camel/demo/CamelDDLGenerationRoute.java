package self.camel.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.springframework.stereotype.Component;


@Component("CamelDDLGenerationRoute")
public class CamelDDLGenerationRoute extends RouteBuilder {

	    @Override
	    public void configure() throws Exception {
	      CamelContext ctx = this.getContext();
		  ctx.addComponent("rabbitmqLog", new RabbitMqLoggingComponent());
		  JacksonXMLDataFormat xmlDataFormat = new JacksonXMLDataFormat();
		  from("file://{{input_directory}}/?idempotent=true&move=backup/$simple{date:now:yyyyMMdd}/$simple{file:name}")
		    .routeId("ddlScriptGeneratorRoute")
		    .onCompletion().onCompleteOnly()
		    	.setHeader(RabbitMQConstants.ROUTING_KEY, simple("${properties:camel.rabbitmq.routingKey}"))
		    	.setBody(constant("run ddl script"))
		    	.to("rabbitmqLog:{{demo.exchangeName}}?connectionFactory=#rabbitConnectionFactory&declare=false&autoDelete=false&arg.queue.x-message-ttl=20000")
		    	.end()
		 	.log("Processing ${header.CamelFileNameOnly} "+ Thread.currentThread().getName())
			.process(new XlsMetaDataProcessor())
			.marshal(xmlDataFormat)
			.log("Got xml format:"+body())
			.multicast()				
			    .to("direct:generateDDL")
			    .to("direct:generateInsertSqlTemplate")			
			.end();
		  
		    from("direct:generateDDL")
		    	.to("xslt-saxon:transformSql.xslt")
		    	.log("Got text format:"+body())
		    	.setHeader("CamelFileName",simple("${properties:output_file_name}"))
		    	.to("file://{{output_directory}}")
		    	.end();
		    from("direct:generateInsertSqlTemplate")
		    	.to("xslt-saxon:transformSqlInsert.xslt")
		    	.setHeader("CamelFileName",simple("${properties:vm_file_name}"))
		    	.to("file://{{template_directory}}")
		    	.end();
	  
	    }

}
