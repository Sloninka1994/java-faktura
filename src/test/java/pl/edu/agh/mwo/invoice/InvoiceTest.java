package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceTest {
	private Invoice invoice;

	@Before
	public void createEmptyInvoiceForTheTest() {
		invoice = new Invoice(); }

	@Test
	public void testEmptyInvoiceHasEmptySubtotal() {
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getSubtotal())); }

	@Test
	public void testEmptyInvoiceHasEmptyTaxAmount() {
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTax())); }

	@Test
	public void testEmptyInvoiceHasEmptyTotal() {
		Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTotal())); }

	@Test
	public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
		Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
		invoice.addProduct(taxFreeProduct);
		Assert.assertThat(invoice.getTotal(), Matchers.comparesEqualTo(invoice.getSubtotal())); }

	@Test
	public void testInvoiceHasProperSubtotalForManyProducts() {
		invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
		invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
		invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
		Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getSubtotal())); }

	@Test
	public void testInvoiceHasProperTaxValueForManyProduct() {
		invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
		invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
		invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
		Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTax())); }

	@Test
	public void testInvoiceHasProperTotalValueForManyProduct() {
		invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
		invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
		invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
		Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getTotal())); }

	@Test
	public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
		invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
		invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
		invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
		Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getSubtotal())); }

	@Test
	public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
		invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
		invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
		invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
		Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getTotal())); }

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithZeroQuantity() {
		invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0); }

	@Test(expected = IllegalArgumentException.class)
	public void testInvoiceWithNegativeQuantity() {
		invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1); }
}