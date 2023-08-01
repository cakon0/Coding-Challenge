package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class DefaultQualityHandler implements IQualityHandler {
	@Override
	public void updateQuality(Product product) {
		product.decreaseQuality(1);
	}
}

class CheeseQualityHandler implements IQualityHandler {
	@Override
	public void updateQuality(Product product) {
		product.decreaseQuality(1);
	}
}

class WineQualityHandler implements IQualityHandler {
	@Override
	public void updateQuality(Product product) {
		int daysPassedFromCutOffDate = (int) product.getDeadline().until(LocalDate.now()).getDays();
		int qualityIncrease = daysPassedFromCutOffDate / 10;
		int newQuality = Math.min(product.getQuality() + qualityIncrease, 50);
		product.setQuality(newQuality);
	}
}

class LaptopQualityHandler implements IQualityHandler {
	@Override
	public void updateQuality(Product product) {
		// Add custom quality update logic for laptops (if needed)
	}
}

public class Product {
	private String name;
	private double initialPrice;
	private double dailyPrice;
	private int quality;
	private LocalDate deadline;
	private IQualityHandler qualityHandler;

	public Product(String name, double initialPrice, LocalDate deadline, IQualityHandler qualityHandler) {
		this.name = name;
		this.initialPrice = initialPrice;
		this.quality = 50; // Initial quality (you can change this as needed)
		this.deadline = deadline;
		this.qualityHandler = qualityHandler;
		this.dailyPrice = initialPrice + 0.10 * quality;
	}

	public void setQuality(int newQuality) {
		quality = newQuality;
		updateDailyPrice();
	}

	public String getName() {
		return name;
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public double getDailyPrice() {
		return dailyPrice;
	}

	public int getQuality() {
		return quality;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void updateDailyPrice() {
		this.dailyPrice = initialPrice + 0.10 * quality;
	}

	public void decreaseQuality(int amount) {
		quality = Math.max(0, quality - amount);
		updateDailyPrice();
	}

	public void updateQuality() {
		qualityHandler.updateQuality(this);
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return name + "," + initialPrice + "," + quality + "," + deadline.format(formatter) + "," + dailyPrice;
	}
}
