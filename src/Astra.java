import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Astra {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/javaMobil";
    static final String USER = "root";
    static final String PASS = "verry1.?";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
//                Login();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    static void showMenu(){
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");
        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);  
                    break;
                case 1:
                    Register();
                    break;
                case 2:
                    Login();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void showMenuAdmin() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data Mobil");
        System.out.println("2. Show Data Mobil");
        System.out.println("3. Edit Data Mobil");
        System.out.println("4. Delete Data Mobil");
        System.out.println("5. Logout");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertMobil();
                    break;
                case 2:
                    showData();
                    break;
                case 3:
                    updateMobil();
                    break;
                case 4:
                    deleteMobil();
                    break;
                case 5:
                    showMenu();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    
    static void showMenuUser() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Lihat Mobil");
        System.out.println("2. Upgrade ke Member");
        System.out.println("3. Logout");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    showDatas();
                    break;
                case 2:
                    upgradeToMember();
                    break;
                case 3:
                    showMenu();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void showMenuMember() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Pinjam Mobil");
        System.out.println("2. Pengembalian Mobil ");
        System.out.println("3. Logout");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    pinjamMobil();
                    break;
                case 2:
                    pengembalian();
                    break;
                case 3:
                    showMenu();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void Register() {
        try {
            // ambil input dari user
            System.out.print("Username: ");
            String username = input.readLine().trim();
            System.out.print("Password: ");
            String password = input.readLine().trim();
            
            // query simpan
            String sql = "INSERT INTO login(username, password) VALUE('%s', '%s')";
            sql = String.format(sql, username, password);

            // simpan buku
            stmt.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    static void Login(){
        try {
            System.out.print("Username: ");
            String username = input.readLine().trim();
            System.out.print("Password: ");
            String password = input.readLine().trim();
            
            String login = "SELECT*FROM login WHERE username='%s' AND password='%s'";
            login = String.format(login, username, password);
            rs = stmt.executeQuery(login);
            
            if(rs.next()){
                if(rs.getString("role").equals("admin")){
                    showMenuAdmin();
                }else if(rs.getString("role").equals("member")){
                    showMenuMember();
                }else if(rs.getString("role").equals("user")){
                    showMenuUser();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void showData() {
        String sql = "SELECT * FROM mobil";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA MOBIL DI RENTAL PS     |");
            System.out.println("+--------------------------------+");

            System.out.println("| ID | Nama | Merk | Harga | Status |");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String merk = rs.getString("merk");
                int harga = rs.getInt("harga");
                String status = rs.getString("status");

                
                System.out.println(String.format("| %d | %s | %s | %s | %s |", id, nama, merk, harga, status));
                showMenuAdmin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertMobil() {
        try {
            // ambil input dari user
            System.out.print("Nama: ");
            String nama = input.readLine().trim();
            System.out.print("Merk: ");
            String merk = input.readLine().trim();
            System.out.print("Harga: ");
            String harga = input.readLine().trim();
            System.out.print("Status: ");
            String status = input.readLine().trim();
            
            // query simpan
            String sql = "INSERT INTO mobil (nama, merk, harga, status) VALUE('%s', '%s', '%s', '%s')";
            sql = String.format(sql, nama, merk, harga, status);

            // simpan buku
            stmt.execute(sql);
            System.out.println("Insert Berhasil...");
            showMenuAdmin();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateMobil() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int id = Integer.parseInt(input.readLine());
            System.out.print("Nama: ");
            String nama = input.readLine().trim();
            System.out.print("Merk: ");
            String merk = input.readLine().trim();
            System.out.print("Harga: ");
            String harga = input.readLine().trim();
            System.out.print("Status: ");
            String status = input.readLine().trim();
            

            // query update
            String sql = "UPDATE mobil SET nama='%s', merk='%s',harga='%s', status='%s' WHERE id=%d";
            sql = String.format(sql, nama, merk, harga, status, id);

            // update data buku
            stmt.execute(sql);
            
            System.out.println("Update Berhasil...");
            showMenuAdmin();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteMobil() {
        try {
            
            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int id = Integer.parseInt(input.readLine());
            
            // buat query hapus
            String sql = String.format("DELETE FROM mobil WHERE id=%d", id);

            // hapus data
            stmt.execute(sql);
            
            System.out.println("Data telah terhapus...");
            showMenuAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void upgradeToMember() {
        try {
            
            // ambil input dari user
            System.out.print("Masukan Username Anda: ");
            String username = input.readLine();
            

            // query update
            String sql = "UPDATE login SET role='member' WHERE username='%s'";
            sql = String.format(sql,username);

            // update data buku
            stmt.execute(sql);
            
            System.out.println("Anda Menjadi Member...");
            showMenuMember();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void pinjamMobil(){
//        String sql = "SELECT * FROM mobil";

            
        try {
//            rs = stmt.executeQuery(sql);
            
            
            System.out.print("Username: ");
            String username = input.readLine().trim();
            
//            System.out.println("+--------------------------------+");
//            System.out.println("|    DATA MOBIL DI RENTAL PS     |");
//            System.out.println("+--------------------------------+");
//
//            System.out.println("| ID | Nama | Merk | Harga | Status |");
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String nama = rs.getString("nama");
//                String merk = rs.getString("merk");
//                int harga = rs.getInt("harga");
//                String status = rs.getString("status");
//
//                
//                System.out.println(String.format("| %d | %s | %s | %s | %s |", id, nama, merk, harga, status));
//            }
              showDatas();
            
            System.out.print("ID Mobil yang ingin di pinjam: ");
            int IDmobil = Integer.parseInt(input.readLine());
            
            String pinjam = "INSERT INTO peminjaman (username, id_mobil) VALUE ('%s','%d')";
            pinjam = String.format(pinjam, username, IDmobil);
            
            String pinjamMobil = "UPDATE mobil SET status='dipinjam' WHERE id='%d'";
            pinjamMobil = String.format(pinjamMobil,IDmobil);

            // update data buku
            stmt.execute(pinjam);
            // simpan buku
            stmt.execute(pinjamMobil);
            System.out.println("Mobil Terpinjam...");
            showMenuMember();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void pengembalian(){
        try {
            System.out.print("Username: ");
            String username = input.readLine().trim();
            
            System.out.print("ID Mobil yang ingin di balikan: ");
            int IDmobil = Integer.parseInt(input.readLine());
            
            String sql = String.format("DELETE FROM peminjaman WHERE username='%s'", username);
            String balikMobil = String.format("UPDATE mobil SET status='ada' WHERE id='%d'", IDmobil);
            
            
            stmt.execute(balikMobil);
            stmt.execute(sql);
            
            System.out.println("Mobil Telah di Kembalikan...");
            showMenuMember();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void showDatas() {
        String sql = "SELECT * FROM mobil";

        try {
            rs = stmt.executeQuery(sql);
            
            System.out.println("+--------------------------------+");
            System.out.println("|    DATA MOBIL DI RENTAL PS     |");
            System.out.println("+--------------------------------+");

            System.out.println("| ID | Nama | Merk | Harga | Status |");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String merk = rs.getString("merk");
                int harga = rs.getInt("harga");
                String status = rs.getString("status");

                
                System.out.println(String.format("| %d | %s | %s | %s | %s |", id, nama, merk, harga, status));
                showMenuUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}