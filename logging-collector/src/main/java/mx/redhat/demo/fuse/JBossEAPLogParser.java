package mx.redhat.demo.fuse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JBossEAPLogParser implements Processor 
{
	private Logger log = LoggerFactory.getLogger(JBossEAPLogParser.class); 
	
	private String pattern = "(?<time>.{12}+) (?<level>.{5}+) \\[(?<class>.+)\\] \\((?<thread>[^\\)]+)\\) (?<message>.+)";
	private Pattern compile = Pattern.compile(pattern);
	private String[] tokens = { "time", "level", "class", "thread", "message"};
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		String payload = exchange.getIn().getBody(String.class);
		
		log.trace("Processing payload: %s", payload);
		
		Map<String, String> map = processMessage(payload);
		
		map.put("message", payload);
		map.put("@timestamp", formatter.format(new Date()));
		
		exchange.getOut().setBody(map);
	}
	
	public Map<String, String> processMessage(String data) 
	{
		Map<String, String> map = new HashMap<String, String>();
		
		Matcher matcher = compile.matcher(data);

		if(matcher.find())
		{
			for(String token : tokens) 
			{
				String myNamedGroup= matcher.group(token);
				
				log.trace("Token %s group: %s \n", token, myNamedGroup);
				
				map.put(token, matcher.group(token));				
			}
		}

		return map;
	}

}
