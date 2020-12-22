package kasir;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LogIn {

	static Date date = new Date();
	static Scanner scn = new Scanner(System.in);
	
	public static int landingPage() throws Exception {
		
		Integer pilihan = 0;
		System.out.println("+============================================+");
  		System.out.println("|             SELAMAT DATANG DI              |");
  		System.out.println("|              TOKO KELOMPOK 6               |");
  		System.out.println("+============================================+");
		System.out.println("|        "+date+"        |");
		System.out.println("+============================================+\n");
		
			System.out.println("+============================================+");
			System.out.println("| [1] Login                                  |");
			System.out.println("+============================================+");
			System.out.println("| [2] Register                               |");
			System.out.println("+============================================+");
			System.out.print("| Pilih : ");
			
			pilihan = scn.nextInt();
			System.out.println("+============================================+");
			
			switch (pilihan) {
			case 1:
				user.login();
				break;

			case 2:
				user.TambahAkun();
				break;
			
			default:
				System.out.println("+------------------------+");
				System.out.println("| Pilihan Tidak Tersedia |");
				System.out.println("+------------------------+\n");
				landingPage();
				break;
			}
		
		return pilihan;
	}
}
