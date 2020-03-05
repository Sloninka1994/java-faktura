package pl.edu.agh.mwo.invoice;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity == 0 || quantity < 0) throw new IllegalArgumentException();
		products.put(product, quantity);
	}

	public BigDecimal getSubtotal() {
		BigDecimal netPrice = BigDecimal.ZERO;

		for (Product product : products.keySet()) {
			Integer quantity = products.get(product);
			netPrice = netPrice.add(product.getPrice().multiply(new BigDecimal(quantity)));
		}
		return netPrice;
	}

	public BigDecimal getTax() {
		return getTotal().subtract(getSubtotal());
	}

	public BigDecimal getTotal() {
		BigDecimal grossPrice = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			Integer quantity = products.get(product);
			grossPrice = grossPrice.add(product.getPriceWithTax().multiply(new BigDecimal(quantity)));
		}
		return grossPrice;
	}
}