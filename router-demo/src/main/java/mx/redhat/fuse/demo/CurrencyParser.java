package mx.redhat.fuse.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CurrencyParser implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String payload = exchange.getIn().getBody(String.class);
		// do something with the payload and/or exchange here
		
		Map<String, String> map = new HashMap<String, String>();
		
		StringTokenizer st = new StringTokenizer(payload, "|");
		
		String token;
		String[] decomposedToken;
		
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			decomposedToken = token.split(":");
			map.put(decomposedToken[0], decomposedToken[1]);
		}
	
		
		exchange.getOut().setBody(map);
	}

}
