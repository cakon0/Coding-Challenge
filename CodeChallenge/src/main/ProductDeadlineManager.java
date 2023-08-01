package main;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class ProductDeadlineManager {
	private static final String CSV_FILE = "products.csv";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static final long CHECK_INTERVAL = 60 * 1000; // Check every minute

	private List<Product> products = new ArrayList<>();

	public void addProduct(String name, double initialPrice, LocalDate deadline, IQualityHandler qualityHandler) {
		products.add(new Product(name, initialPrice, deadline, qualityHandler));
	}

	public void saveProductsToCSV() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
			for (Product product : products) {
				writer.println(product.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeExpiredProduct(Product product) {
		products.remove(product);
		saveProductsToCSV();
		System.out.println("Product " + product.getName() + " has expired and removed.");
	}

	public void updateProductQuality() {
		for (Product product : products) {
			product.updateQuality();
		}
	}

	static class CheckExpiredProductsTask extends TimerTask {
		private ProductDeadlineManager manager;

		public CheckExpiredProductsTask(ProductDeadlineManager manager) {
			this.manager = manager;
		}

		@Override
		public void run() {
			LocalDate today = LocalDate.now();
			List<Product> expiredProducts = new ArrayList<>();

			for (Product product : manager.products) {
				if (product.getDeadline().isBefore(today)) {
					expiredProducts.add(product);
				}
			}

			for (Product expiredProduct : expiredProducts) {
				manager.removeExpiredProduct(expiredProduct);
			}

			manager.updateProductQuality();
			manager.saveProductsToCSV();
		}
	}
}
