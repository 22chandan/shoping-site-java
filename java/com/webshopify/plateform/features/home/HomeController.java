package com.webshopify.plateform.features.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value= {"home","/",""})
	public String renderHomepage() {
		return "home.html";
	}
	
	

}
