package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {
	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner scan = new Scanner(System.in);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		System.out.println("Enter rental data");
		System.out.print("Car model: ");
		String carModel = scan.nextLine();
		System.out.print("Withdrawal (dd/MM/yyyy hh:mm): ");
		LocalDateTime start = LocalDateTime.parse(scan.nextLine(), dtf);
		System.out.print("Return (dd/MM/yyyy hh:mm): ");
		LocalDateTime finish = LocalDateTime.parse(scan.nextLine(), dtf);
		
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));
		
		System.out.print("Enter with a per hour price: ");
		double pricePerHour = scan.nextDouble();
		System.out.print("Enter with a per day price: ");
		double pricePerDay = scan.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		
		rentalService.processInvoice(cr);
		
		System.out.println("INVOICE:");
		System.out.println("Basic Payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Total Payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		
		scan.close();
	}

}
