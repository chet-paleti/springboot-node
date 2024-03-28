package chet.springboot_node;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;



@Repository
public class ShopServiceImpl implements ShopService {
	
/*	
	@Autowired
	private SessionFactory sessionFactory;
*/
	
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public ShopServiceImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	

	@Override
	@Transactional
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		
//Session thesession = sessionFactory.getCurrentSession();

Session thesession = entityManager.unwrap(Session.class);
		
		Query<Product> myquery = thesession.createQuery("from Product", Product.class); 
		List<Product> products =new ArrayList<>();
		
		products = myquery.getResultList(); 
		
		return products;
		//return null;
	}

	@Override
	@Transactional
	public Product findById(int productid) {
		// TODO Auto-generated method stub
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		Product product = thesession.find(Product.class, productid) ;
		return product;
	}

	@Override
	@Transactional
	public void saveProduct(Product product) {
		// TODO Auto-generated method stub
		
		int usr = 1 ;
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		User user = thesession.find(User.class,usr) ;
		product.setUser(user);
		
		thesession.saveOrUpdate(product);
		
	}

	@Override
	@Transactional
	public void delProduct(int prodid) {
		// TODO Auto-generated method stub
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		
		Query delete_query = thesession.createQuery("delete from Product where id=:prodid");
		delete_query.setParameter("prodid", prodid);
		delete_query.executeUpdate();
		
	}

	@Override
	@Transactional
	public void addCart(int prodid) {
		
		int usr = 1 ;
		
		CartItem cartitem ;
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		
		Query q1 = thesession.createQuery("select c from Cart c JOIN c.user u where u.id=:userid");
		q1.setParameter("userid", usr);
		Cart cart = (Cart) q1.list().get(0) ;
		

		Product product = thesession.get(Product.class, prodid);
		
		//CartItem cartitem = cir.findByProduct(prodid);
		
		Query q2 = thesession.createQuery("select ci.id from CartItem ci JOIN ci.cart c where ci.prod_id=:prodid and c.id = :cartid");
		q2.setParameter("prodid", prodid);
		q2.setParameter("cartid", cart.getId());
		
		List x = q2.list() ;
		
		
		
		//System.out.println("Prod Id " + prodid);
		
		//System.out.println("select cart " + x.get(0));
		
		if (x.isEmpty()) {
			cartitem =  new CartItem(product,cart, prodid) ;
			
		} else {
			
			int ci = (int) x.get(0) ;
			
			cartitem = thesession.get(CartItem.class, ci);
			cartitem.setQty();
			
		}

		thesession.saveOrUpdate(cartitem);
		
	}

	@Override
	@Transactional
	public List<CartItem> getCart() {
		// TODO Auto-generated method stub
		
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		
		int usr = 1 ;
		//CartItem cartitem ;
		
		//Query q1 = thesession.createQuery("select c from Cart c JOIN c.user u where u.id=:userid");
		//q1.setParameter("userid", usr);
		//Cart cart = (Cart) q1.list().get(0) ;
		//Cart cart = thesession.get(Cart.class, usr);
		//Query<CartItem> myquery = thesession.createQuery("from CartItem", CartItem.class); 
		
		//Query<CartItem> myquery = thesession.createQuery("select ci from CartItem ci JOIN ci.cart c where c.id = :cartid"); 
		Query<CartItem> myquery = thesession.createQuery("select ci from CartItem ci JOIN ci.cart c JOIN c.user u where u.id = :userid"); 
		
		myquery.setParameter("userid", usr);
		List<CartItem> cartitems =new ArrayList<>();
		
		cartitems = myquery.getResultList(); 
		return cartitems ;
	}

	@Override
	@Transactional
	public void delCartItem(int cartitemid) {
		// TODO Auto-generated method stub
		
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		
		Query delete_query = thesession.createQuery("delete from CartItem where id=:cartitemid");
		delete_query.setParameter("cartitemid", cartitemid);
		delete_query.executeUpdate();
		
	}

	@Override
	@Transactional
	public List<Order> getOrders() {
		// TODO Auto-generated method stub
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		
		int usr = 1 ;
		
		Query myquery = thesession.createQuery("select o from Order o JOIN o.user u where u.id=:userid");
		myquery.setParameter("userid", usr);
		//Query<Order> myquery = thesession.createQuery("from Order", Order.class); 
		
		List<Order> orders =new ArrayList<>();
		
		orders = myquery.list(); 
		return orders ;
	}

	@Override
	@Transactional
	public void createOrder() {
		// TODO Auto-generated method stub
		
		//Session thesession = sessionFactory.getCurrentSession();
		Session thesession = entityManager.unwrap(Session.class);
		int usr = 1 ;
		User user = thesession.find(User.class,usr) ;
		
		Order order = new Order(user) ;
		thesession.save(order);
		
		//Query q1 = thesession.createQuery("select c from Cart c JOIN c.user u where u.id=:userid");
		//q1.setParameter("userid", usr);
		//Cart cart = (Cart) q1.list().get(0) ;
		//Cart cart = thesession.get(Cart.class, usr);
		
		//Query<CartItem> myquery = thesession.createQuery("select ci from CartItem ci JOIN ci.cart c where c.id = :cartid"); 
		Query<CartItem> myquery = thesession.createQuery("select ci from CartItem ci JOIN ci.cart c JOIN c.user u where u.id = :userid"); 
		
		//myquery.setParameter("cartid", cart.getId());
		myquery.setParameter("userid", usr);
		
		Query delete_query = thesession.createQuery("delete from CartItem where id=:cartitemid");
		List<CartItem> cartitems =new ArrayList<>();
		//List<OrderItem> orderitems = new ArrayList<>() ;
		
		cartitems = myquery.getResultList();
		for(CartItem cartitem : cartitems) {
			
			OrderItem o = new OrderItem(cartitem.getQty(),order,cartitem.getProduct()) ;
			thesession.save(o);
			
			delete_query.setParameter("cartitemid", cartitem.getId());
			delete_query.executeUpdate();
			
		} ;
		
		
		
		
		
		
		//delete_query.executeUpdate();
		
		
	}

}
