package kasir;

import java.util.Scanner;

import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;

public class User extends koneksi implements Kelola{
	
	static Connection conn;
	static Statement stmt;
	
	Scanner input = new Scanner(System.in);
	Date date = new Date();
	LogIn login = new LogIn();

	String usr;
	String pass;

	public static String username;
	String password;
	String email;
	String str;
	String query;
	
	
	// LogIn
	@Override
    public void login() throws Exception {
		
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		int login = 0 ; 
		do {
			System.out.println("+===================+");
	    	System.out.println("|       LOGIN       |");
	    	System.out.println("+===================+");
	    	System.out.print("| Username : ");
	    	this.username = input.next();
			System.out.print("| Password : ");
			this.password = input.next();
			System.out.println("+===================+");

			this.str = String.format("%tF", date);

			this.query = "SELECT*FROM user WHERE username='"+username+"'"
	    				+ " AND password='"+password+"'";
			
			stmt=conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				String sql = "UPDATE user SET login_terakhir='"+str+"' WHERE username='"+username+"'";
				stmt.executeUpdate(sql);
				System.out.println("+----------------+");
				System.out.println("| Login Berhasil |");
				System.out.println("+----------------+\n");
				usr=username;
				pass=password;
				user_pilih();
			} else {
				System.out.println("+----------------------------------+");
				System.out.println("| Username dan/atau Password Salah |");
				System.out.println("+----------------------------------+\n");
				login++;
				if (login==3) {
					reset();
				}
			}
		} while (login>=0 && login<=2);
    	
	}
    
	
	public void reset () throws Exception {
		String resetpass = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String randompass = "";
		int length = 5;
		Random random = new Random();
		char [] pass = new char [length];
		
		for (int a=0 ; a<length ; a++  ) {
			pass[a] = resetpass.charAt(random.nextInt(resetpass.length()));
		}
		
		for (int a=0 ; a<pass.length ; a++) {
			randompass += pass[a];
		}
		String sql = "UPDATE user SET password='"+randompass+"' WHERE username='"+username+"'";
		stmt.executeUpdate(sql);
		login();
	}
	
	
    // Register data
 	@Override
 	public void TambahAkun() throws Exception{
 		
 		System.out.println("+======================+");
    	System.out.println("|       REGISTER       |");
    	System.out.println("+======================+");

 		Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        System.out.println("+======================+");
 		System.out.print("| Masukkan Username : ");
 		this.username = input.next();

 		this.str = String.format("%tF", date);

 		System.out.print("| Masukkan Email : ");
 		this.email = input.next();

 		System.out.print("| Masukkan Password : ");
 		this.password = input.next();
 		System.out.println("+======================+");

 		// Melakukan pengecekan validitas email
 		if (email.contains("@")) {

 			// Melakukan pengecekan username sudah tersedia atau belum
 			String cek = "SELECT username FROM user WHERE username='"+username+"'";
 			try {
 				stmt = conn.createStatement();
 				ResultSet resultCek = stmt.executeQuery(cek);
 				
 				if (resultCek.next()) {
 					System.out.println("+--------------------------+");
 					System.out.println("| Username Sudah Terdaftar |");
 					System.out.println("+--------------------------+\n");
 					TambahAkun();
 				} else{

 					this.query = "INSERT INTO user VALUES ('"+username+"','"+str+"','"+email+"','"+password+"')";
 		
 					try {
 						stmt = conn.createStatement();

 						if (stmt.executeUpdate(query) == 1) {
 							System.out.println("+--------------------------+");
 							System.out.println("| Data Berhasil Diinputkan |");
 							System.out.println("+--------------------------+\n");
 							login();
 						} else{
 							System.out.println("+-----------------------+");
 							System.out.println("| Data Gagal Diinputkan |");
 							System.out.println("+-----------------------+\n");
 							TambahAkun();
 						}
 						
 					} catch (SQLException e) {
 						System.out.println("+-------------------+");
 						System.out.println("| Terjadi Kesalahan |");
 						System.out.println("+-------------------+");
 					}

 				}

 			} catch (SQLException e) {
 				System.out.println("+-------------------+");
 				System.out.println("| Terjadi Kesalahan |");
 				System.out.println("+-------------------+");
 			}

 		} else{
 			System.out.println("+-----------------------------+");
 			System.out.println("| Masukkan Email Dengan Benar |");
 			System.out.println("+-----------------------------+");
 			TambahAkun();
 		}

 		conn.close();

 	}

 	
 // Pilihan menu
  	public void user_pilih() throws Exception {

  		Scanner scan = new Scanner(System.in);
  		Barang brg = new Barang();
  		Transaksi trs = new Transaksi();
  		LaporanTransaksi lt = new LaporanTransaksi();

  		System.out.println("+=========================+");
    	System.out.println("|       DAFTAR MENU       |");
    	System.out.println("+=========================+");
  		System.out.println("| 1. Pengaturan Akun      |");
  		System.out.println("| 2. Beli Barang          |");
  		System.out.println("| 3. Data Master          |");
  		System.out.println("| 4. Laporan Transaksi    |");
  		System.out.println("| 5. Terminate            |");
  		System.out.println("+=========================+");
  		System.out.print("| Tentukan Pilihanmu : ");
  		
  		try {
  			Integer pilihan = scan.nextInt();
  			System.out.println("+=========================+");

  			switch (pilihan) {
  				case 1:
  					user_setting();
  					break;
  				
  				case 2:
  					trs.Menu();
  					break;	

  				case 3:
  					brg.Menu();
  					break;
  					
  				case 4:
  					lt.menu();
  					break;
  				
  				case 5:
  					System.out.println("Program ditutup");
  					System.out.println("Terima Kasih");
  					System.exit(0);
  					
  				default:
  					System.out.println("+------------------------+");
  					System.out.println("| Pilihan Tidak Tersedia |");
  					System.out.println("+------------------------+\n");
  					user_pilih();
  					break;
  			}
  		} catch (InputMismatchException e) {
  			System.out.println("+------------------------+");
			System.out.println("| Pilihan Tidak Tersedia |");
			System.out.println("+------------------------+");
  		}

  		scan.close();

  	}


  	// Pilihan setting
  	public void user_setting() throws Exception {

  		Scanner scan = new Scanner(System.in);
  			
  		System.out.println("\n\n");
  		System.out.println("+===================================+");
    	System.out.println("|     PENGELOLAAN DATA PENGGUNA     |");
    	System.out.println("+===================================+");
  		System.out.println("| 1. Edit Akun                      |");
  		System.out.println("| 2. Hapus Akun                     |");
  		System.out.println("| 3. Cari Data                      |");
  		System.out.println("| 4. Lihat Data                     |");
  		System.out.println("| 5. Kembali                        |");
  		System.out.println("| 6. LogOut                         |");
  		System.out.println("+===================================+");
  		System.out.print("| Tentukan Pilihanmu : ");
  		Integer pilihan = scan.nextInt();	
  		System.out.println("+===================================+");
  		
  		switch (pilihan) {
  			case 1:
  				EditAkun();
  				break;
  		
  			case 2:
  				HapusAkun();
  				break;

  			case 3:
  				CariAkun();
  				break;

  			case 4:
  				LihatAkun();
  				break;
  				
  			case 5:
  				user_pilih();
  				break;
  				
  			case 6:
  				logout();
  				break;

  			default:
  				System.out.println("+------------------------+");
  				System.out.println("| Pilihan Tidak Tersedia |");
  				System.out.println("+------------------------+\n");
  				user_setting();
  				break;
  		}

  		scan.close();

  	}
  	
  	// LogOut
  	public void logout() throws Exception {
  		boolean cek = true;
  		String jawab;
  		do {
  			System.out.println("\n\n");
  			System.out.println("+----------------------------+");
  			System.out.println("| Apakah Anda Ingin Keluar ? |");
  			System.out.println("+----------------------------+");
  			System.out.println("| Jawab Y/T                  |");
  			jawab = input.next();
  			System.out.println("+----------------------------+\n");
  			if(jawab.equalsIgnoreCase("Y")) {
  				cek = false;
  				System.out.println("+----------------------+");
  				System.out.println("| Anda Berhasil Keluar |");
  				System.out.println("+----------------------+");
  			}
  		} 
  		while(cek);
  		
  		Scanner scan = new Scanner(System.in);
  		
  		System.out.println("+=====================+");
		System.out.println("| 1. Login            |");
		System.out.println("| 2. Register         |");
		System.out.println("+=====================+");
  		System.out.print("| Tentukan Pilihanmu : ");
  		Integer pilihan = scan.nextInt();
  		System.out.println("+=====================+");
  		
  		switch (pilihan) {
			case 1:
			try {
				login();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		
			case 2:
			try {
				TambahAkun();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			default:
				System.out.println("+------------------------+");
  				System.out.println("| Pilihan Tidak Tersedia |");
  				System.out.println("+------------------------+");
  				login.landingPage();
  				break;
  		}
  		
  		scan.close();
  	}

 	// Mengedit username, email, dan password akun
 	@Override
 	public void EditAkun() throws Exception {

 		System.out.println("\n\n");
 		System.out.println("+========================+");
    	System.out.println("|         UPDATE         |");
    	System.out.println("+========================+");
 		System.out.println("| 1. Ubah Username       |");
 		System.out.println("| 2. Ubah Email          |");
 		System.out.println("| 3. Ubah Password       |");
 		System.out.println("| 4. Kembali             |");
 		System.out.println("+========================+");
 		System.out.print("| Tentukan Pilihanmu : ");

 		try {
 			Integer pilihan = input.nextInt();
 			System.out.println("+========================+\n");
 			switch (pilihan) {

 				// Ubah Username
 				case 1:
 					System.out.println("+-------------------------+");
					System.out.println("|      Ubah Username      |");
					System.out.println("+-------------------------+");
					System.out.print("| Masukkan Username Baru : ");
 					this.username = input.next();
 					System.out.print("| Masukkan Password Anda : ");
 					this.password = input.next();
 					System.out.println("+-------------------------+");
 	
 					if (password.equals(pass)) {
 					
 						this.query = "UPDATE user SET username='"+username+"' WHERE username='"+usr+"'";
 		
 						try {
 							stmt = conn.createStatement();
 								
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("+---------------------------+");
 								System.out.println("| Username Berhasil Di Ubah |");
 								System.out.println("+---------------------------+\n");
 								user_pilih();
 							} else{
 								System.out.println("+------------------------+");
 								System.out.println("| Username Gagal Di Ubah |");
 								System.out.println("+------------------------+\n");
 								EditAkun();
 							}
 								
 						} catch (SQLException e) {
 							System.out.println("+-------------------+");
 							System.out.println("| Terjadi Kesalahan |");
 							System.out.println("+-------------------+");
 						}
 	
 					} else {
 						System.out.println("+-----------------------------------+");
						System.out.println("| Password Yang Anda Masukkan Salah |");
						System.out.println("+-----------------------------------+");
 						EditAkun();
 					}
 	
 					break;
					
 			
 				// Ubah email
 				case 2:
 					System.out.println("+-------------------------+");
					System.out.println("|        Ubah Email       |");
					System.out.println("+-------------------------+");
 					System.out.print("| Masukkan Email Baru :");
 					this.email = input.next();
 					System.out.print("| Masukkan Password Anda : ");
 					this.password = input.next();
 					System.out.println("+-------------------------+");
 	
 					if (email.contains("@")) {
 	
 						if (password.equals(pass)) {
 					
 							this.query = "UPDATE user SET email='"+email+"' WHERE username='"+usr+"'";
 		
 							try {
 								stmt = conn.createStatement();
 								
 								if (stmt.executeUpdate(query) == 1) {
 									System.out.println("+------------------------+");
 									System.out.println("| Email Berhasil Di Ubah |");
 									System.out.println("+------------------------+\n");
 									user_pilih();
 								} else{
 									System.out.println("+---------------------+");
 									System.out.println("| Email Gagal Di Ubah |");
 									System.out.println("+---------------------+\n");
 									EditAkun();
 								}
 								
 							} catch (SQLException e) {
 								System.out.println("+-------------------+");
 								System.out.println("| Terjadi Kesalahan |");
 								System.out.println("+-------------------+");
 							}
 		
 						} else {
 							System.out.println("+-----------------------------------+");
 							System.out.println("| Password Yang Anda Masukkan Salah |");
 							System.out.println("+-----------------------------------+\n");
 							EditAkun();
 						}
 	
 					} else {
 						System.out.println("+-----------------------------+");
 						System.out.println("| Masukkan Email Dengan Benar |");
 						System.out.println("+-----------------------------+\n");
 						EditAkun();
 					}
 	
 					break;
 				
 				// Ubah password
 				case 3:
 					System.out.println("+----------------------------+");
					System.out.println("|        Ubah Password       |");
					System.out.println("+----------------------------+");
 					System.out.print("| Masukkan Password Lama : ");
 					String passwordLama = input.next();
 					System.out.print("| Masukkan Password Baru :");
 					String passwordBaru = input.next();
 					System.out.println("+----------------------------+");
 					
 					if (passwordLama.equals(pass)) {
 						
 						this.query = "UPDATE user SET password='"+passwordBaru+"' WHERE username='"+usr+"'";
 		
 						try {
 							stmt = conn.createStatement();
 							
 							if (stmt.executeUpdate(query) == 1) {
 								System.out.println("+-------------------------+");
 								System.out.println("Password Berhasil Di Ubah |");
 								System.out.println("+-------------------------+\n");
 								user_pilih();
 							} else {
 								System.out.println("+------------------------+");
 								System.out.println("| Password Gagal Di Ubah |");
 								System.out.println("+------------------------+\n");
 								EditAkun();
 							}
 							
 						} catch (SQLException e) {
 							System.out.println("+-------------------+");
 							System.out.println("| Terjadi Kesalahan |");
 							System.out.println("+-------------------+");
 						}
 	
 					} else {
 						System.out.println("+-----------------------------------+");
 						System.out.println("| Password Yang Anda Masukkan Salah |");
 						System.out.println("+-----------------------------------+\n");
 						EditAkun();
 					}
 	
 					break;
 					
 				//Kembali ke menu pengelolaan data user
 				case 4:
 					user_setting();
 			
 				default:
 					System.out.println("+------------------------+");
 	  				System.out.println("| Pilihan Tidak Tersedia |");
 	  				System.out.println("+------------------------+");
 	  				EditAkun();
 					break;
 			}

 		} catch (InputMismatchException e) {
 			System.out.println("+------------------------+");
			System.out.println("| Pilihan Tidak Tersedia |");
			System.out.println("+------------------------+");
 		}

 	}


 	// Hapus akun
 	@Override
 	public void HapusAkun() throws Exception {

 		System.out.println("+-----------------------------------------------+");
		System.out.println("|                  HAPUS AKUN                   |");
		System.out.println("+-----=-----------------------------------------+");
 		System.out.println("| Apakah Anda Yakin Ingin Menghapus Akun Anda ? |");
 		System.out.println("| Jawab Y/T                                     |");
 		System.out.println("| Jawabanmu : ");
 		String lanjut = input.next();
 		System.out.println("+-----------------------------------------------+");

 		if (lanjut.equals("y")) {
 			this.query = "DELETE FROM user WHERE username='"+usr+"'";
 			try {
 				stmt = conn.createStatement();
 				stmt.execute(query);
 				System.out.println("+------------------------+");
 				System.out.println("| Data Berhasil Di Hapus |");
 				System.out.println("+------------------------+\n");
 			} catch (SQLException e) {
 				System.out.println("+---------------------+");
 				System.out.println("| Data Gagal Di Hapus |");
 				System.out.println("+---------------------+\n");
 			}
 		} 
 		
 		Scanner scan = new Scanner(System.in);
  		
  		System.out.println("+=====================+");
		System.out.println("| 1. Login            |");
		System.out.println("| 2. Register         |");
		System.out.println("+=====================+");
  		System.out.print("| Tentukan Pilihanmu : ");
  		Integer pilihan = scan.nextInt();
  		System.out.println("+=====================+");
  		
  		switch (pilihan) {
			case 1:
			try {
				login();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		
			case 2:
			try {
				TambahAkun();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			default:
				System.out.println("+------------------------+");
  				System.out.println("| Pilihan Tidak Tersedia |");
  				System.out.println("+------------------------+");
  				login.landingPage();
  				break;
  		}
  		
  		scan.close();

 	}


 	// Cari data akun atau data transaksi
 	@Override
 	public void CariAkun() throws Exception {
 		System.out.println("+---------------------+");
		System.out.println("|      CARI AKUN      |");
		System.out.println("+---------------------+");
 		System.out.print("| Masukkan Username : ");
 		String kunci = input.next();
 		System.out.println("+---------------------+");

 		this.query = "SELECT*FROM user WHERE username LIKE '%"+kunci+"%'";
 		try {
 			stmt = conn.createStatement();
 			
 			ResultSet result = stmt.executeQuery(query);

 			while (result.next()) {
 					
 				System.out.println("+-------------------------------------------------------------+");
 				System.out.print("| ");
 				System.out.print(result.getString("username"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.print(result.getDate("login_terakhir"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.print(result.getString("email"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.println(result.getString("password"));
 				System.out.println("+-------------------------------------------------------------+");
 			}
 		} catch (SQLException e) {
 			System.out.println("+--------------------------------+");
 			System.out.println("| Tidak Dapat Mengakses Database |");
 			System.out.println("+--------------------------------+\n");
 		}
 		user_setting();
 	}


 	// Lihat data akun atau data transaksi??
 	@Override
 	public void LihatAkun() throws Exception {
 		System.out.println("+---------------------+");
		System.out.println("|     LIHAT AKUN      |");
		System.out.println("+---------------------+");

 		this.query = "SELECT*FROM user";
 		try {
 			stmt = conn.createStatement();
 			
 			ResultSet result = stmt.executeQuery(query);

 			while (result.next()) {
 				
 				System.out.println("+-------------------------------------------------------------+");
 				System.out.print("| ");
 				System.out.print(result.getString("username"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.print(result.getDate("login_terakhir"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.print(result.getString("email"));
 				System.out.print("\t");
 				System.out.print("| ");
 				System.out.println(result.getString("password"));
 				System.out.println("+-------------------------------------------------------------+");
 			}
 		} catch (SQLException e) {
 			System.out.println("+--------------------------------+");
 			System.out.println("| Tidak Dapat Mengakses Database |");
 			System.out.println("+--------------------------------+\n");
 		}
 		user_setting();
 	}

}
