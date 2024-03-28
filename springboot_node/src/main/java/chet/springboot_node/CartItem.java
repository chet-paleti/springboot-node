package chet.springboot_node;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cartitems")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="productId")
	private Product product;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="cartId")
	private Cart cart;
	
	@Column(name="quantity")
	private int qty ;
	
	@Column(name="productId" , insertable=false, updatable=false)
	private int prod_id  ;
	

	public CartItem() {
			
		}
	
	
	public CartItem(Product product, Cart cart, int id) {
	
	this.cart = cart ;
	this.product = product ;
	this.qty = 1 ;
	this.prod_id = id ;
		
	}

	public int getProd_id() {
	return prod_id;
}

public void setProd_id(int prod_id) {
	this.prod_id = prod_id;
}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getQty() {
		return qty;
	}

	public void setQty() {
		this.qty++ ;
	}
	
	

}
