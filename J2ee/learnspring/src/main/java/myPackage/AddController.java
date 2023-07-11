package myPackage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddController {

	
	@RequestMapping("/add")
	private ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("Add method Called");
		
		int a=Integer.valueOf(request.getParameter("n1"));
		int b=Integer.valueOf(request.getParameter("n1"));
		
		int c=a+b;
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("calculationpage.jsp");
		mv.addObject("result",c);
		
		return mv;
	}

}
