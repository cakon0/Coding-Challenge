package main;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Timer;

public class MainApp {
	public static void main(String[] args) {
		ProductDeadlineManager manager = new ProductDeadlineManager();

		// Add some sample products
		manager.addProduct("Laptop", 1000.0, LocalDate.now().plusDays(7), new DefaultQualityHandler());
		manager.addProduct("Phone", 500.0, LocalDate.now().plusDays(3), new DefaultQualityHandler());
		manager.addProduct("TV", 800.0, LocalDate.now().plusDays(5), new DefaultQualityHandler());
		manager.addProduct("Cheese Feta", 10.0, LocalDate.now().plusDays(80), new CheeseQualityHandler());
		manager.addProduct("Wine Chianti", 20.0, LocalDate.now().plusDays(90), new WineQualityHandler());

		// Add more wine and cheese samples
		manager.addProduct("Cheese Cheddar", 12.0, LocalDate.now().plusDays(70), new CheeseQualityHandler());
		manager.addProduct("Wine Bordeaux", 18.0, LocalDate.now().plusDays(100), new WineQualityHandler());

		manager.saveProductsToCSV();

		Timer timer = new Timer();
		timer.schedule(new ProductDeadlineManager.CheckExpiredProductsTask(manager), 0,
				ProductDeadlineManager.CHECK_INTERVAL);

	}
}
