
import java.util.ArrayList;
import javax.swing.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Scanner;
import org.openqa.selenium.By;
import java.awt.Font;
import java.util.Arrays;

public class Main {
	public static int i = 0;
	public static ArrayList<String> stockMarketData = new ArrayList<>();
	public static String stockMarketFinalData = "";
	public static ArrayList<String> stocks = new ArrayList<>();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the abbreviated names of the stocks you want to check out (enter 'done' to finish):");
		String stock = "";

		while (true) {
			stock = scanner.nextLine();
			if (stock.equalsIgnoreCase("done")) {
				break;
			}
			stocks.add(stock);
		}
		getData(i, stocks);
		uploadData(stockMarketData);
	}

	public static void getData(int num, ArrayList<String> stocks) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/lispy/eclipse-workspace/Selenium/src/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		String stock = stocks.get(num);
		driver.manage().window().minimize();
		driver.get("https://finance.yahoo.com/quote/" + stock);
		String price = driver
				.findElement(By.xpath("//*[@id=\"quote-header-info\"]/div[3]/div[1]/div[1]/fin-streamer[1]")).getText();
		String name = driver.findElement(By.xpath("//*[@id=\"quote-header-info\"]/div[2]/div[1]/div[1]/h1")).getText();
		String earning = driver
				.findElement(By.xpath("//*[@id=\"quote-header-info\"]/div[3]/div[1]/div/fin-streamer[2]")).getText();
		String volume = driver
				.findElement(By.xpath("//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[7]/td[2]/fin-streamer"))
				.getText();

		stockMarketData.add(
				"Name: " + name + "\n" + "Price: " + price + "\n" + "Earning: " + earning + "\n" + "Volume: " + volume);

		i++;
		if (i < stocks.size()) {
			getData(i, stocks);
		}
	}

	public static void uploadData(ArrayList<String> importedData) {
		for (int i = 0; i < importedData.size(); i++) {
			stockMarketFinalData += "╭──  ⋅ ⋅  ── ✩ ──  ⋅ ⋅  ──╮╭──  ⋅ ⋅  ── ✩ ──  ⋅ ⋅  ──╮" + "<br>";
			stockMarketFinalData += importedData.get(i) + "<br>";
			stockMarketFinalData += "╰──  ⋅ ⋅  ── ✩ ──  ⋅ ⋅  ──╯╰──  ⋅ ⋅  ── ✩ ──  ⋅ ⋅  ──╯" + "<br>";
		}
		JFrame window = new JFrame("JFrame with text");
		JLabel label = new JLabel("My label");
		label.setText("<html>" + stockMarketFinalData + "</html>");
		label.setFont(new Font("Verdana", Font.PLAIN, 18));
		window.add(label);
		window.setTitle("Stock Market WebScraper");
		window.setSize(450, 450);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
