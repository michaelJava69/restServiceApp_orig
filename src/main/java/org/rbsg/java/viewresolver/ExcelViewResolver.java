package org.rbsg.java.viewresolver;

 

import java.util.Locale;
 
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
 
public class ExcelViewResolver implements ViewResolver{
 
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	System.out.println("do we cone in hee fo xls "   );
    	
    	ExcelView view = new ExcelView();
        return view;
      }
     
}
