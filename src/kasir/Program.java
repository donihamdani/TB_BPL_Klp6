package kasir;

import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;

public class Program {

	static Scanner scn = new Scanner(System.in);
	static User user = new User();
	static Date date = new Date();
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int pilihan; 
		
		switch (LogIn.landingPage()) {
		case 1:
			user.login();
			break;

		case 2:
			user.TambahAkun();
			break;
		}
	}

}
