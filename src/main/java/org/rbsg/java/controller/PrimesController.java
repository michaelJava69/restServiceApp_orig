package org.rbsg.java.controller;

import org.apache.log4j.Logger;

import org.rbsg.java.model.Pizza;
import org.rbsg.java.model.PrimesResponse;
import org.rbsg.java.service.PrimeNumberService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

//for content negotiator/viewresolver to work this controller can noot be rest or return a responseBody
//Need to investigate further why
@Controller  
public class PrimesController {

	final static Logger logger = Logger.getLogger(PrimesController.class);

	    /**
         * Constructor for the PrimesController class
         *
  	     * @param upperLimit - Prime Number upper limit
         * @param model      - Container map that will containing retrieved data
         */
	
	     @RequestMapping(value = "/primes/{upperLimit}", method = RequestMethod.GET)
	     // @ResponseBody
	     @Cacheable(value = "primes", key = "#upperLimit")
	     public String getPrimeNumbers(@PathVariable final Integer upperLimit, ModelMap model) {
	   	
	     PrimeNumberService primeService = new PrimeNumberService();

	   	 // Ehcache not woking so my custom cache
	   	 final PrimesResponse primesResponse; 
	   	 
	   	 
	   	 // CacheManager.getInstance().addCache("xyz"); // creates a cache called xyz.
	        
	   	 logger.info(" **** Test : Inside PrimeController  ***"); 
	   	  
	   	 
	   	 Cache xyz = CacheManager.getInstance().getCache("primes");
	        //Check
	   	 if (xyz.get(upperLimit)==null) {
	   	       
	   		 logger.info( "Getting data outside of the cachce.********....." );
	   		
	   	     primesResponse = new PrimesResponse(upperLimit, primeService.getPrimeNumbers(upperLimit));
	   	     xyz.put(new Element(upperLimit, primesResponse));
	   	 }else{
	   		 
	   		 logger.info("Test : Inside my Cache.********");
	   		 
	   		 Element e = xyz.get(upperLimit);
	   		 primesResponse =  (PrimesResponse)   e.getObjectValue();
	   	 }
	         
	         
	         
	         
	         model.addAttribute("primesResponse", primesResponse);
	         return "primesResponse";
	     }
}
