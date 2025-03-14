package com.example.sample1app;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample1app.repositories.PersonRepository;


@Controller
public class HelloController {

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonDAOPersonImpl dao;
	
	
//	@RequestMapping("/")
//	public ModelAndView index(ModelAndView mav) {
//		mav.setViewName("index");
//		mav.addObject("title", "Hello Page");
//		mav.addObject("msg", "this is JPA sample data.");
//		Iterable<Person> list = repository.findAll();
//		mav.addObject("data",list);
//		return mav;
//	}
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav,@ModelAttribute("formModel") Person Person) {
//		mav.setViewName("index");
//		mav.addObject("title", "Hello page");
//		mav.addObject("msg", "this is JPA sample data.");
//		List<Person> list = repository.findAllOrderByName();
//		mav.addObject("data", list);
//		return mav;
		
		mav.setViewName("index");
		mav.addObject("title", "Hello Page");
		mav.addObject("msg", "this is JPA sample data");
		List<Person> list = dao.getAll();
		mav.addObject("data",list);
		return mav;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional
	public ModelAndView form(
			@ModelAttribute("formModel") @Validated Person Person,
			BindingResult result,
			ModelAndView mav
			) {
		ModelAndView res = null;
		System.out.println(result.getFieldErrors());
		if(!result.hasErrors()) {
			repository.saveAndFlush(Person);
			res = new ModelAndView("redirect:/");
		}else {
			mav.setViewName("index");
			mav.addObject("title", "Hello page");
			mav.addObject("msg", "sorry, error is occurred...");
			Iterable<Person> list = repository.findAll();
			mav.addObject("datalist", list);
			res = mav;
		}
		return res;
	}
	
	@PostConstruct
	public void init() {
		Person p1 = new Person();
		p1.setName("タロウ");
		p1.setAge(39);
		p1.setMail("taro@yamada");
		repository.saveAndFlush(p1);
		
		Person p2 = new Person();
		p2.setName("ハナコ");
		p2.setAge(28);
		p2.setMail("hanako@flower");
		repository.saveAndFlush(p2);
		
		Person p3 = new Person();
		p3.setName("サチコ");
		p3.setAge(17);
		p3.setMail("sachiko@happy");
		repository.saveAndFlush(p3);
		
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute Person Person, @PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit Person.");
		Optional<Person> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
			
		return mav;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional
	public ModelAndView update(@ModelAttribute Person Person, ModelAndView mav) {
		repository.saveAndFlush(Person);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title", "Delete Person.");
		mav.addObject("msg", "Can I delete this record?");
		
		Optional<Person> data = repository.findById((long)id);
		
		mav.addObject("formModel",data.get());
		
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
//		mav.setViewName("find");
//		String param = request.getParameter("find_str");
//		
//		if(param == "") {
//			mav = new ModelAndView("redirect:/find");
//		}else {
//			mav.addObject("title", "Find result.");
//			mav.addObject("msg","「" + param + "」の検索結果");
//			mav.addObject("value", param);
//			
//			List<Person> list = dao.find(param);
//			mav.addObject("data", list);
//			
//		}
//		
//		return mav;
		
//		mav.setViewName("find");
//		String param = request.getParameter("find_str");
//		if(param == "") {
//			mav = new ModelAndView("redirect:/find");
//		}else {
//			String[] params = param.split(",");
//			mav.addObject("title", "Find result");
//			mav.addObject("msg", "「" + param + "」の検索結果");
//			mav.addObject("value", param);
//			
//			List<Person> list = repository.findByAge(
//					Integer.parseInt(params[0]),
//					Integer.parseInt(params[1])
//					);
//			mav.addObject("data", list);
//		}
//		return mav;
		
		mav.setViewName("find");
		String param = request.getParameter("find_str");
		if(param == "") {
			mav = new ModelAndView("redirect:/find");
		}else {
			List<Person> list = dao.find(param);
			mav.addObject("data",list);
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("title", "Find Person.");
		mav.addObject("msg","Personのサンプルです。");
		Iterable<Person> list = dao.getAll();
		mav.addObject("data", list);
		return mav;
	}
	
	@RequestMapping(value = "/page/{page}",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav, @PathVariable int page) {
		mav.setViewName("find");
		mav.addObject("msg","Personのサンプルです。");
		int num = 2;
		Iterable<Person> list = dao.getPage(page, num);
		mav.addObject("data", list);
		return mav;
	}
	
	
	
}
