package kasir;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DataTransaksi {
	
	public static String url = "jdbc:mysql://localhost/tb_bpl?serverTimezone=Asia/Jakarta";
	public static String user = "root";
	public static String password = "";
	
	public static String NoResiTr;
	public static String UnameTr;
	public static String TglTr;
	public static String IdTr;
	public static String ambilSku;
	
	public static int Qty ;
	public static int HargaBrg;
	
	Scanner sc = new Scanner(System.in);
	
	public static void NoResi() {
		
		try {
			
			Connection c = DriverManager.getConnection(url, user, password);
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT noresi FROM transaksi");
			
			Date date = new Date();
			SimpleDateFormat bln = new SimpleDateFormat("MM");
			String blnTransaksi = bln.format(date);
			
			SimpleDateFormat tgl = new SimpleDateFormat("dd");
			String tglTransaksi = tgl.format(date);
			
			
			int i = 1;
			while (rs.next()) {
				i++;
			}
			
			String formatResi = blnTransaksi + tglTransaksi + i;
			NoResiTr = formatResi;
		}
		catch(SQLException e) {
			System.out.println("Terjadi Kesalahan Pada : " + e);
		}
	}
	
	public static void Username() {
		User admin = new User();
		UnameTr = admin.usr; 
	}
	
	public void Tanggal() {
		Date sekarang = new Date();
		TglTr = String.format("%tF", sekarang); //%tF buat apa
	}
	
	public void DtTransaksi() {
		
		try {
			
			Connection c = DriverManager.getConnection(url, user, password);
			Statement stmt = c.createStatement();
			
			boolean ulangStok = true;
			int stok = 0;
			
			System.out.print("\nSKU Barang : ");
			ambilSku = sc.next();
			ResultSet rs = stmt.executeQuery("SELECT sku, harga_jual, stock FROM data_master WHERE sku= '" + ambilSku + "';");
			
			if(rs.next()) {
				ambilSku = rs.getString("sku");
				HargaBrg = rs.getInt("harga_jual");
				stok = rs.getInt("stock");
			}
			
			do {
				System.out.print("Inputkan Jumlah : ");
				Qty = sc.nextInt();
				
				if(stok>=Qty) {
					HargaBrg = HargaBrg * Qty;
					stok = stok - Qty;
					String sqlupdate = "UPDATE data_master SET stock=? where sku=?";
					PreparedStatement update = c.prepareStatement(sqlupdate);
					stmt = c.createStatement();
					update.setInt(1, stok);
					update.setString(2, ambilSku);
					update.executeUpdate();
					
					ulangStok = false;
				}
				
				else {
					System.out.println("Persediaan TIDAK cukup!");
				}
			}
			while(ulangStok);
		}
		catch(SQLException e) {
			System.out.println("Terjadi Kesalahan Pada : " + e);
		}
	}
	
}
