package kasir;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LogIn {

	static Date date = new Date();
	static Scanner scn = new Scanner(System.in);
	
	public static int landingPage() {
		
		Integer pilihan = 0;
		
		System.out.println("       Toko Kelompok 6      ");
		System.out.println("============================");
		System.out.println(date+"\n");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.print("Tentukan pilihanmu : ");
		
		try {
			pilihan = scn.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Input yang anda masukkan salah");
			
			System.out.println("       Toko Kelompok 6      ");
			System.out.println("============================");
			System.out.println(date+"\n");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.print("Tentukan pilihanmu : ");
			pilihan = scn.nextInt();
		}
		
		return pilihan;
	}
}
