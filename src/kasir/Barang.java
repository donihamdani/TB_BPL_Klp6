package kasir;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Barang extends koneksi implements KelolaBarang {
	
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	
	Scanner input = new Scanner(System.in);
	Scanner input1 = new Scanner(System.in);
	User user = new User();
	
	String sku;
	String nama;
	Integer stock;
	Integer harga_beli;
	Integer harga_jual;
	String jwb;
	
	//Menu Data
    public void Menu() {
        System.out.println("+==================================+");
    	System.out.println("|         MENU DATA BARANG         |");
    	System.out.println("+==================================+");
        System.out.println("| 1. Tambah Barang                 |");
        System.out.println("| 2. Cari Barang                   |");
        System.out.println("| 3. Ubah Barang                   |");
        System.out.println("| 4. Hapus Barang                  |");
        System.out.println("| 5. Lihat Data Barang             |");
        System.out.println("| 0. Kembali                       |");
        System.out.println("+==================================+");
        System.out.print("| Masukkan Pilihan : ");

        try {
            Integer pilih = input1.nextInt();
	    System.out.println("+==================================+");
            switch (pilih) {
                case 0:
                    user.user_pilih();
                    break;
                case 1:
                    TambahBarang();
                    break;
                case 2:
                    CariBarang();
                    break;
                case 3:
                    UbahBarang();
                    break;
                case 4:
                    HapusBarang();
                    break;
                case 5 :
                	LihatData();
                	break;
                default:
                    System.out.println("+-----------------------------------------+");
                    System.out.println("| Pilihan yang anda masukkan tidak valid! |");
                    System.out.println("+-----------------------------------------+\n");
                    Menu();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Tambah Data Barang
	@Override
	public void TambahBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		DataBarang brg = new DataBarang();
		
		System.out.print("\n\n");
		System.out.println("+==================================+");
    		System.out.println("|           TAMBAH DATA            |");
    		System.out.println("+==================================+");
		
		sku = brg.sku();
		nama = brg.nama();
		stock = brg.stock();
		harga_beli = brg.harga_beli();
		harga_jual = brg.harga_jual();
       
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO data_master VALUES ('"+sku+"', '"+nama+"', '"+stock+"', '"+harga_beli+"', '"+harga_jual+"')";   

            stmt.execute(sql);
            System.out.println("+-------------------------+");
            System.out.println("| Data Berhasil Tersimpan |");
            System.out.println("+-------------------------+\n");
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }            
		LihatData();
        
	}
	
	//Cari Data Barang
	@Override
	public void CariBarang() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		ArrayList <DataBarang> data = new ArrayList<>();
		
        	System.out.print("\n\n");
		System.out.println("+==========================================+");
    		System.out.println("|             PENCARIAN DATA               |");
    		System.out.println("+==========================================+");
		  System.out.print("| Masukkan nama barang yang ingin di cari : ");
        
		System.out.print("Masukkan nama barang yang ingin di cari : ");
		String cari = input.nextLine();

		stmt = conn.createStatement();
		String sql = "SELECT * FROM data_master WHERE nama='"+cari+"'";
		
		try {
			rs = stmt.executeQuery(sql);
			
        	if(rs.next()) { 
        		DataBarang p = new DataBarang();
        		
        		p.setSku(rs.getString("sku"));
        		p.setNama(rs.getString("nama"));
        		p.setStock(rs.getInt("stock"));
        		p.setHarga_beli(rs.getInt("harga_beli"));
        		p.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(p);
        		
				System.out.println(" ");
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA");
         		System.out.print("\t\t");
         		System.out.print("  STOCK");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL ");
         		
         		for(DataBarang barang : data) {
    	        	System.out.print("  " +barang.sku);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.nama);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.stock);
    	        	System.out.print("\t\t");
    	        	System.out.print("  " +barang.harga_beli);
    	        	System.out.print("\t\t\t");
    	        	System.out.println("  " +barang.harga_jual);
            	}
        	}
        	else {
        		System.out.println("\nBarang Tidak Tersedia");
        	}
        	
            System.out.println("\nCari Barang Lagi? Y/T");
            System.out.print("Jawaban : ");
            jwb = input.nextLine();
            
            if(jwb.equalsIgnoreCase("y")) {
            	CariBarang();
            }
            else {
                System.out.println("\nKembali ke Menu? Y/T");
                System.out.print("Jawaban : ");
                jwb = input.nextLine();
                
                if(jwb.equalsIgnoreCase("y")) {
                	Menu();
                }
                else {
                	user.logout();
                }
            }
        		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//Ubah Barang
	@Override
	public void UbahBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		String sql;
		DataBarang brg = new DataBarang();
		
        	System.out.print("\n\n");
		System.out.println("+==========================================+");
    		System.out.println("|               UBAH BARANG                |");
    		System.out.println("+==========================================+");
        	System.out.println("| 1. Nama                                  |");
        	System.out.println("| 2. Stock                                 |");
        	System.out.println("| 3. Harga Beli                            |");
        	System.out.println("| 4. Harga Jual                            |");
        	System.out.println("+==========================================+");
		System.out.print("| Pilih data yang ingin diubah : ");
		Integer ubah = input1.nextInt();
		System.out.println("+==========================================+");
		
        stmt = conn.createStatement();
        
        try {
			switch (ubah) {
				case 1 :
					System.out.println("+---------------------------------------------+");
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
					System.out.println("+---------------------------------------------+");
			        nama = brg.nama();
			        
			        sql = "UPDATE data_master SET nama='"+nama+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				case 2 :
					System.out.println("+---------------------------------------------+");
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
					System.out.println("+---------------------------------------------+");
			        stock = brg.stock();
			        
			        sql = "UPDATE data_master SET stock='"+stock+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				case 3 :
					System.out.println("+---------------------------------------------+");
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
					System.out.println("+---------------------------------------------+");
			        harga_beli = brg.harga_beli();
			        
			        sql = "UPDATE data_master SET harga_beli='"+harga_beli+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				case 4 :
					System.out.println("+---------------------------------------------+");
					System.out.print("Masukkan SKU pada data yang ingin di ubah : ");
					this.sku = input.nextLine();
					System.out.println("+---------------------------------------------+");
			        harga_jual = brg.harga_jual();
			        
			        sql = "UPDATE data_master SET harga_jual='"+harga_jual+"' WHERE sku='"+sku+"'";   
			        stmt.execute(sql);
					break;
				default :
					System.out.println("+------------------------+");
					System.out.println("| Pilihan Tidak Tersedia |");
					System.out.println("+------------------------+\n");
					UbahBarang();
			}

		}
        
        catch (Exception e) {
        	e.printStackTrace();
        }			
		LihatData();
		
	}
	
	//Hapus Data Barang
	@Override
	public void HapusBarang() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
		System.out.print("\n\n");
		System.out.println("+==========================================+");
    		System.out.println("|               HAPUS DATA                 |");
    		System.out.println("+==========================================+");
		System.out.print("| Masukkan SKU pada data yang akan di hapus : ");
		this.sku = input.nextLine();
		System.out.println("+==========================================+");
		
		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM data_master WHERE sku='"+sku+"'";
		
			stmt.execute(sql);
			System.out.println("+------------------------+");
			System.out.println("| Data Berhasil Terhapus |");
			System.out.println("+------------------------+\n");

		}
		
		catch(Exception e) {
			e.printStackTrace();
		}			
		LihatData();

	}
	
	//Melihat Data
	@Override
	public void LihatData() throws Exception {
    	Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
    	stmt = conn.createStatement();
    	
    	ArrayList <DataBarang> data = new ArrayList<>();
        
        String sql = "SELECT * FROM data_master";
        rs = stmt.executeQuery(sql);

        try {
            System.out.print("\n\n");
    		System.out.println("+=====================================================+");
        	System.out.println("|               DATA BARANG KELOMPOK 6                |");
        	System.out.println("+=====================================================+");
            
         		System.out.print("  SKU");
         		System.out.print("\t\t");
         		System.out.print("  NAMA");
         		System.out.print("\t\t");
         		System.out.print("  STOCK");
         		System.out.print("\t\t");
         		System.out.print("  HARGA BELI");
         		System.out.print("\t\t");
         		System.out.println("  HARGA JUAL");
        	
        	while(rs.next()) { 
        		DataBarang n = new DataBarang();
        		
        		n.setSku(rs.getString("sku"));
        		n.setNama(rs.getString("nama"));
        		n.setStock(rs.getInt("stock"));
        		n.setHarga_beli(rs.getInt("harga_beli"));
        		n.setHarga_jual(rs.getInt("harga_jual"));
        		
        		data.add(n);
        	}

        	for(DataBarang barang : data) {
	        	System.out.print("  " +barang.sku);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.nama);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.stock);
	        	System.out.print("\t\t");
	        	System.out.print("  " +barang.harga_beli);
	        	System.out.print("\t\t\t");
	        	System.out.println("  " +barang.harga_jual);
        	}
        	
            System.out.println("\nKembali ke Menu? Y/T");
            System.out.print("Jawaban : ");
            jwb = input.nextLine();
            
            if(jwb.equalsIgnoreCase("y")) {
            	Menu();
            }
            else {
            	user.logout();
            }

        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	//Restock Barang
	@Override
	public void RestokBarang() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		
        	System.out.print("\n\n");
		System.out.println("+=====================================================+");
    		System.out.println("|               RESTOK BARANG KELOMPOK 6              |");
    		System.out.println("+=====================================================+");
		System.out.print("| Masukkan nama barang : ");
		String cek = input.nextLine();
		System.out.println("+=====================================================+");
        
		System.out.print("Masukkan nama barang : ");
		String cek = input.nextLine();
		
		try {
                        stmt = conn.createStatement();
                        String sql = "SELECT * FROM data_master WHERE nama='"+cek+"'";
			rs = stmt.executeQuery(sql);
			
        	if(rs.next()) { 
			String namabarang = rs.getString(nama);
                        Integer stok = rs.getInt("stock");
                        System.out.print("Jumlah Restok\t : ");
                        Integer tambah = input1.nextInt();
                        int restok = Integer.valueOf(stock) + Integer.valueOf(tambah);
                        String sql2 = "UPDATE data_master SET stock = '"+restok+"' WHERE nama = '"+cek+"'";
                        stmt.execute(sql2);
                        stmt.close();
                        System.out.println("Stok sudah bertambah!\n");
        	}
                
        	else {
        		System.out.println("\nBarang Tidak Ada");
        	}
        	
            System.out.println("\nRestok Barang Lagi? Y/T");
            System.out.print("Jawaban : ");
            jwb = input.nextLine();
            
            if(jwb.equalsIgnoreCase("y")) {
            	CariBarang();
            }
            else {
                System.out.println("\nKembali ke Menu? Y/T");
                System.out.print("Jawaban : ");
                jwb = input.nextLine();
                
                if(jwb.equalsIgnoreCase("y")) {
                	Menu();
                }
                else {
                	user.logout();
                }
            }
        		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
