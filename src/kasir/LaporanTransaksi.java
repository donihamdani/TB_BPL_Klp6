package kasir;

import java.util.Date;
import java.util.Scanner;
import java.sql.*;

public class LaporanTransaksi extends koneksi {
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	private int id, harga_beli, harga_jual, jumlah, harga, hbeli_total, hjual_total, untung;
	private String sku, nama, noresi;
	private Date tanggal;
	
	User us = new User();
	
	public void menu() throws Exception {
		System.out.println("\n+--------------------------------+");
		System.out.println("|          MENU LAPORAN          |");
		System.out.println("+--------------------------------+");
		System.out.println("1. Laporan Harian");
		System.out.println("2. Laporan Bulanan");
		System.out.println("3. Kembali ke Menu Utama");
		System.out.print("Masukkan Pilihan Anda : ");
		int masuk = input.nextInt();
		switch(masuk) {
			case 1:
				laporanHari();
				break;
			case 2:
				laporanBulan();
				break;
			case 3:
				us.user_pilih();
				break;
			default:
				System.out.println("\nPilihan Anda Salah");
				System.out.println("\n");
				menu();
		}
	}
	
	private void ulang() throws Exception {
		System.out.println("\n\nApakah anda ingin melanjutkan program?(Y/T)");
		String masuk = input1.nextLine();
		masuk.toUpperCase();
		if(masuk.equalsIgnoreCase("Y")) {
			menu();
		}
		else if(masuk.equalsIgnoreCase("T")) {
			System.out.println("Kembali ke Menu Utama");
			us.user_pilih();
		}
		else {
			System.out.println("\nPilihan Salah");
			System.out.println("\n");
			ulang();
		}
	}
	
	private void kembali() throws Exception {
		System.out.println("Apakah anda ingin melanjutkan program?(Y/T)");
		String masuk = input1.nextLine();
		masuk.toUpperCase();
		if(masuk.equalsIgnoreCase("Y")) {
			System.out.println("\n");
			laporanBulan();
		}
		else if(masuk.equalsIgnoreCase("T")) {
			System.out.println("\nKembali ke menu");
			menu();
		}
		else {
			System.out.println("\nPilihan Salah");
			System.out.println("\n");
			kembali();
		}
	}
	
	private void laporanHari() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	        stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s\t|   %d\t|   %d\t|    %d\t\t| %d\t|";
	        
	        System.out.print("\nMasukkan Tanggal (YYYY/MM/DDD) : ");
	        String tgl = input1.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE tanggal='"+tgl+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("Data Tidak Ada atau Format Salah");
	        	System.out.println("\n");
	        	laporanHari();
	        }
	        else {
	        	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.println("|                                       TAMPILAN LAPORAN PENJUALAN HARIAN                                       |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	hbeli_total=hbeli_total+(harga_beli*jumlah);
			        	hjual_total=hjual_total+(harga_jual*jumlah);
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} while(rs.next());
		        untung = hjual_total-hbeli_total;
		        System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|Total penjualan barang per hari      : "+hjual_total+"                                                                    |");
		        System.out.println("|Total modal barang terpakai per hari : "+hbeli_total+"                                                                    |");
		        System.out.println("|Keuntungan per hari                  : "+untung+"                                                                    |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        ulang();
	        } 
	        
		}
		catch (Exception e){
			 e.printStackTrace();
		}

	}
	private void laporanBulan() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        
	        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	        stmt = conn.createStatement();
	        
	        String format = "|%s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t| %s\t|";
	        String format1 = "|%d\t| %s\t\t| %s\t|  %s\t| %s\t|   %d\t|   %d\t|    %d\t\t| %d\t|";
	        
	        Bulan();
	        System.out.print("Pilih Bulan : ");
	        String tgl = input1.nextLine();
	        System.out.print("Masukkan Tahun (YYYY) : ");
	        String tgl1 = input1.nextLine();
	        
	        String sql = "SELECT * FROM transaksi_detail INNER JOIN barang  ON transaksi_detail.sku = barang.sku INNER JOIN transaksi ON transaksi_detail.noresi = transaksi.noresi WHERE MONTH(tanggal)='"+tgl+"' AND YEAR(tanggal)='"+tgl1+"'";
	        rs = stmt.executeQuery(sql);
	        
	        if(rs.next()==false) {
	        	System.out.println("\nData Tidak Ada atau Format Salah");
	        	System.out.println("\n");
	        	kembali();
	        }
	        else {
	        	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.println("|                                       TAMPILAN LAPORAN PENJUALAN BULANAN                                      |");
	        	System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	        	System.out.printf(format, "ID", "No.Resi", "Tanggal", "SKU", "Nama", "Harga Beli", "Harga Jual", "Jumlah", "Total");
	          	do {
			        	id = rs.getInt("id");
			        	noresi = rs.getString("noresi");
			        	tanggal = rs.getDate("tanggal");
			        	sku = rs.getString("sku");
			        	nama = rs.getString("nama");
			        	harga_beli = rs.getInt("harga_beli");
			        	harga_jual = rs.getInt("harga_jual");
			        	jumlah = rs.getInt("jumlah");
			        	harga = rs.getInt("harga");
			        	
			        	hbeli_total=hbeli_total+(harga_beli*jumlah);
			        	hjual_total=hjual_total+(harga_jual*jumlah);
			        	
			        	System.out.println("\n");
			        	System.out.printf(format1, id, noresi, tanggal, sku, nama, harga_beli, harga_jual, jumlah, harga);
	          	} while(rs.next());
		        untung = hjual_total-hbeli_total;
	          	System.out.println("\n+---------------------------------------------------------------------------------------------------------------+");
	          	System.out.println("|                                                                                                               |");
		        System.out.println("|                                                                                                               |");
		        System.out.println("|Total penjualan barang per bulan      : "+hjual_total+"                                                                   |");
		        System.out.println("|Total modal barang terpakai per bulan : "+hbeli_total+"                                                                   |");
		        System.out.println("|Keuntungan per bulan                  : "+untung+"                                                                   |");
		        System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		        ulang();
	        }    
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
	}
		
	private void Bulan() {
		System.out.println("\n+------------------------------------------------------+");
		System.out.println("|                     Daftar Bulan                     |");
		System.out.println("+------------------------------------------------------+");
		System.out.println("|1. Januari    2.  Februari  3.  Maret     4.  April   |");
		System.out.println("|5. Mei        6.  Juni      7.  Juli      8.  Agustus |");
		System.out.println("|9. September  10. Oktober   11. November  12. Desember|");
		System.out.println("+------------------------------------------------------+");
	}
}
