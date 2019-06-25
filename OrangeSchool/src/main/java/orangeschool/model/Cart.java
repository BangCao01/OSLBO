package orangeschool.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Cart")
public class Cart implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "cart_id")
    private Integer cartId;
    
    public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer id) {
		this.cartId = id;
	}
	
	@OneToMany(mappedBy="cart")
    private Set<Items> items;
}
