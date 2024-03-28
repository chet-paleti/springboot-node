package chet.springboot_node;

import java.util.List;

public interface ShopService {
	
	public List<Product> getProducts();

	public Product findById(int productid);

	public void saveProduct(Product product);

	public void delProduct(int prodid);

	public void addCart(int prodid);

	public List<CartItem> getCart();

	public void delCartItem(int cartitemid);

	public List<Order> getOrders();

	public void createOrder();


}
