package service.broker;

public class LocalBrokerService {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ClientInfo> request = new HttpEntity<>(clients[0]);
		Quotation quotation =
		restTemplate.postForObject("http://localhost:8080/quotations",
			request, Quotation.class);
		displayProfile(clients[0]);
		displayQuotation(quotation);
	   } 
	   
}