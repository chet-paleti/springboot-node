package chet.springboot_node;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopservice;
	
	@GetMapping("/home")
	public String ShopHome(Model mymodel) {
		
		List<Product> products =new ArrayList<>();
		
		products = shopservice.getProducts();
		
		mymodel.addAttribute("products", products);
		
		return "shop";
	}
	
	@GetMapping("/product/{productid}")
	public String getEmployee(@PathVariable int productid, Model mymodel) {
		
		Product product = shopservice.findById(productid);
		
		if (product == null) {
			throw new RuntimeException("Product id not found - " + productid);
		}
		
		mymodel.addAttribute("product", product);
		
		return "shop-product";
	}
	
	@GetMapping("/cart")
	public String getCart(Model mymodel) {
		
		List<CartItem> cartitems = shopservice.getCart() ;
		mymodel.addAttribute("cartitems", cartitems);
		
		return "cart";
	}
	
	@PostMapping("/add-cart")
	public String postAddCart(@RequestParam("productId") int prodid, Model mymodel) {
		
		shopservice.addCart(prodid);
		return "redirect:cart";
	}
	
	@PostMapping("/cart-delete-item")
	public String delCartItem(@RequestParam("cartitemid") int cartitemid, Model mymodel) {
		
		shopservice.delCartItem(cartitemid);
		return "redirect:cart";
	}
	
	@GetMapping("/orders")
	public String getOrders(Model mymodel) {
		
		List<Order> orders = shopservice.getOrders() ;
		mymodel.addAttribute("orders", orders);
		
		return "order";
	}
	
	@PostMapping("/create-order")
	public String delCartItem(Model mymodel) {
		
		shopservice.createOrder();
		return "redirect:orders";
	}
	
	

}
