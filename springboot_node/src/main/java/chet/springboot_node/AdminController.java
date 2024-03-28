package chet.springboot_node;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@ComponentScan("chet")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ShopService shopservice;
	
	@GetMapping("/products")
	public String ShopHome(Model mymodel) {
		
		List<Product> products =new ArrayList<>();
		
		products = shopservice.getProducts();
		
		mymodel.addAttribute("products", products);
		
		return "admin-shop";
	}
	
	@GetMapping("/edit-product/{productid}")
	public String getProduct(@PathVariable int productid, Model mymodel) {
		
		Product product = shopservice.findById(productid);
		
		if (product == null) {
			throw new RuntimeException("Product id not found - " + productid);
		}
		
		mymodel.addAttribute("product", product);
		
		return "admin-product";
	}
	
	@GetMapping("/add-product")
	public String addProduct(Model myModel) {
		Product newproduct = new Product() ;
		myModel.addAttribute("product", newproduct);
		return "admin-product";
	}
	
	@PostMapping("/product")
	public String postProduct(@ModelAttribute("product") Product product) {
		
		shopservice.saveProduct(product);
		return "redirect:products";
	}
	
	@PostMapping("/delete-product")
	public String postDeleteProduct(@RequestParam("productId") int prodid, Model mymodel) {
		
		shopservice.delProduct(prodid);
		return "redirect:products";
	}

}
