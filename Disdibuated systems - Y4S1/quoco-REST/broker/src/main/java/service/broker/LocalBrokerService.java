package service.broker;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import service.core.AbstractQuotationService;
import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.NoSuchQuotationException;
import service.core.Quotation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.NumberFormat;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import service.core.ClientInfo;
import service.core.Quotation;
import service.core.Constants;

import org.springframework.http.HttpHeaders;

@RestController
public class LocalBrokerService {

	private static int appID = 0;
	private Map<Integer, ClientApplication> applications = new HashMap<>();

	@RequestMapping(value = "/applications", method = RequestMethod.POST)
	public ResponseEntity<ClientApplication> createAppMessage(@RequestBody ClientInfo info) {
		List<Quotation> quotesList  = getQuotations(info);

		ClientApplication returnMessage = new ClientApplication(appID++, info, quotesList);
		HttpHeaders headers = new HttpHeaders();
		
		String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/quotations/" + Integer.toString(appID);

		try {
			headers.setLocation(new URI(path));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		applications.put(appID, returnMessage);

		return new ResponseEntity<>(returnMessage, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="/quotations/{ID}",method=RequestMethod.GET)
	public ClientApplication getResource(@PathVariable("ID") int ID) {
 		ClientApplication clientApp = applications.get(ID);
 		if (clientApp == null) throw new NoSuchQuotationException();
 		return clientApp;
	} 

	@RequestMapping(method=RequestMethod.GET)
	public List<ClientApplication> getAll() {
		List<ClientApplication> appsList  = new ArrayList<ClientApplication>();

		for (ClientApplication value : applications.values()) {
			appsList.add(value);
		}

		return appsList;
	} 

	private List<Quotation> getQuotations(ClientInfo info){
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ClientInfo> request = new HttpEntity<>(info);

		List<Quotation> quotesList  = new ArrayList<Quotation>();
		for(int i = 8081; i<=8083; i++){
			String currentURL = "http://localhost:" + Integer.toString(i) + "/quotations";
			Quotation quotation =
				restTemplate.postForObject(currentURL, request, Quotation.class);
			quotesList.add(quotation);
		}

		return quotesList;
	}	   
}