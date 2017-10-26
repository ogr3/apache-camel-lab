package se.cag.controllers;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CamelController {
	
	@Autowired
    CamelContext camelContext;
	
	@Autowired
    ProducerTemplate producerTemplate;
	
	@GetMapping("/{id}")
	public void startGetCamel(@PathVariable long id) {
		producerTemplate.sendBody("direct:firstRoute", "Calling via Spring Boot Rest Controller, id " + id);
	}

	@PostMapping(value = "/{id}")
	public void startPostCamel(@PathVariable long id, @RequestBody String body) {
		producerTemplate.sendBody("direct:firstRoute", "Calling via Spring Boot Rest Controller, id " + id + ", text: " + body);
	}
}
