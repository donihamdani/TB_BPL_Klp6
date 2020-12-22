package kasir;

import java.sql.*;
import java.util.Scanner;

public class Transaksi extends koneksi implements KelolaTransaksi {

	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Scanner sc = new Scanner(System.in);
	User user = new User();
	
	String noresi, username, tgl;
	
	//Menu Data
    public void Menu() {
    	Integer pilih=0;
    	do
    	{
        System.out.println("\n--MENU DATA TRANSAKSI--");
        System.out.println("1. Lakukan Transaksi");
        System.out.println("0. Kembali");
        System.out.print("Masukkan Pilihan : ");

        try {
            pilih = sc.nextInt();
            switch (pilih) {
                case 0:
                    user.user_pilih();
                    break;
                case 1:
                    SaveData();
                    break;

                default:
                    System.out.println("Pilihan yang anda masukkan tidak valid!");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    	}while(pilih<0 || pilih>1);
        
    }
	
	@Override
	public void SaveData() throws Exception {
		
		try {
		
		int i =1;
		boolean ulang=true;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		stmt = conn.createStatement();
		PreparedStatement insert;

		DataTransaksi trs = new DataTransaksi();
		trs.NoResi();
		trs.Tanggal();
		trs.Username();
		
		while(ulang) {
			System.out.println(" ------------------------------------------------------- ");
			System.out.println("|                >> MENU DATA TRANSAKSI <<              |");
			System.out.println(" ------------------------------------------------------- \n");
			
			System.out.println("=====>> Data Master Barang <<====");
			
			ResultSet rs = stmt.executeQuery("SELECT sku, nama, stock, harga_jual FROM barang");
			
			
			System.out.println("=====================================================================================");
			String format1 = "%s\t  %-20s %-20s %-20s %-20s\n";
			System.out.printf(format1, " NO ", "SKU", "Nama Barang", "Stock", "Harga");
			System.out.println("=====================================================================================");
			int tampil=1;
			
			while(rs.next()) {
				String SKU = rs.getString("sku");
				String nama = rs.getString("nama");
				Integer stock = rs.getInt("stock");
				Integer hargajual = rs.getInt("harga_jual");
				
			
			String format = "[ %s ]\t  %-20s %-20s %-20s %-20s\n";
			System.out.printf(format, tampil, SKU, nama, stock, hargajual);
			tampil++;
			}
			
			if (i==1) {
				String sqlinsert1 = "INSERT INTO transaksi (noresi, tanggal, username)" + "VALUES (?, ?, ?)";
				
				insert = conn.prepareStatement(sqlinsert1);
				insert.setString(1, trs.NoResiTr);
				insert.setString(2, trs.TglTr);
				insert.setString(3, trs.UnameTr);
				insert.executeUpdate();
				
				i++;
			}
			
			trs.DtTransaksi();
			
			System.out.println("=====>> Data Master Setelah Transaksi <<====");
			rs = stmt.executeQuery("SELECT sku, nama, stock, harga_jual harga_jual FROM barang");
			
			System.out.println("=======================================================================================");
			String format2 = "%s\t  %-20s %-20s %-20s %-20s\n";
			System.out.printf(format2, " NO ", "SKU", "Nama Barang", "Stock", "Harga");
			System.out.println("=======================================================================================");
			int tampil2=1;
			
			while(rs.next()) {
				String SKU = rs.getString("sku");
				String nama = rs.getString("nama");
				Integer stock = rs.getInt("stock");
				Integer hargajual = rs.getInt("harga_jual");
				
			
			String format = "[ %s ]\t  %-20s %-20s %-20s %-20s\n";
			System.out.printf(format, tampil2, SKU, nama, stock, hargajual);
			tampil2++;
			}
			
			String sqlinsert2 = "INSERT INTO transaksi_detail (sku, noresi, jumlah, harga)" + "VALUES (?, ?, ?, ?)";
			
			insert = conn.prepareStatement(sqlinsert2);
			insert.setString(1, trs.ambilSku);
			insert.setString(2, trs.NoResiTr);
			insert.setInt(3, trs.Qty);
			insert.setInt(4, trs.HargaBrg);
			insert.executeUpdate();
			
			System.out.println("\nData Berhasil Disimpan!");
			
			boolean tanya = true;
			while(tanya) {
				System.out.print("Ada Tambahan Barang lagi? (Y/N)");
				String jawab = sc.next().toUpperCase();
				
				switch(jawab) 
				{
				
				case "Y":
					tanya = false;
					break;
				
				case "N":
					tanya = false;
					ulang = false;
					break;
					
					default:
						System.out.println("PILIHAN JAWABAN -> Y/N");
				}
			}
		}
	}
		catch(SQLException e) {
		e.printStackTrace();
		}
		
		Menu();
	}
}
