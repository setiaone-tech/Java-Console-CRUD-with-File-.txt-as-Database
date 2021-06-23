/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nilaimahasiswa;

/**
 *
 * @author 88komputer
 */
import java.io.*;
import java.util.*;
import javax.swing.*;

public class proses_sistem {
    public static boolean getYesorNo(String message){
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n" + message + " (y/n)? ");
        String pilihanUser = terminalInput.next();
        
        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("Pilihan tidak ditemukan!");
            System.out.print("\n" + message + " (y/n)? ");
            pilihanUser = terminalInput.next();
        }
        if(pilihanUser.equals("n")){
            System.exit(0);
        }
        
        return pilihanUser.equalsIgnoreCase("y");
    }
    public static void tambahData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader ( System.in));
        try {
            System.out.println("NIM : ");
            String nimMhs = br.readLine();
            System.out.println("Nama : ");
            String namaMhs = br.readLine();
            System.out.println("Jurusan : ");
            String jurusanMhs = br.readLine();
            System.out.println("Nilai Akhir : ");
            String nilaiakhirMhs = br.readLine();
            
            File fl = new File("db_Mahasiswa.txt");
            if(!fl.exists()) {
                fl.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fl.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(nimMhs + "@" + namaMhs + "@" + jurusanMhs + "@" + nilaiakhirMhs + "\n");
            bw.close();
            System.out.println("Data berhasil dimasukan!");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static void modifyFile( String filepath, String oldString, String newString) {
        BufferedReader reader = null;
        File fileToBeModified = new File(filepath);
        String oldContent = "";
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));
            String line = reader.readLine();
            while (line != null){
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(oldString, newString);
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader ( System.in));
        System.out.print("Masukan data awal : ");
        String data_sebelum = br.readLine();
        System.out.print("Masukan data akhir : ");
        String data_sesudah = br.readLine();
        
        modifyFile("db_Mahasiswa.txt", data_sebelum, data_sesudah);
        System.out.println("Data berhasil diperbarui!");
    }
    public static void lihatData() throws IOException {
        FileReader fileInput;
        BufferedReader bufferInput;
        try {
            fileInput = new FileReader("db_Mahasiswa.txt");
            bufferInput = new BufferedReader(fileInput);
        }
        catch (Exception e){
            System.err.println("File tidak ditemukan!");
            return;
        }
        System.out.println("\n| No |\tNIM   |\tNama Mahasiswa   |\tJurusan   |\tNilai Akhir   ");
        System.out.println("---------------------------------------------------------------------");
        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null){
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, "@");
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%-10s", stringToken.nextToken());
            System.out.printf("|\t%-12s", stringToken.nextToken());
            System.out.printf("|\t%-12s", stringToken.nextToken());
            System.out.printf("|\t%-12s", stringToken.nextToken());
            System.out.print("\n");
            data = bufferInput.readLine();
        }
    }
    static void tampilData() {
        try (BufferedReader br = new BufferedReader(
        new FileReader("db_Mahasiswa.txt"))) {
           String sCurrentLine;int no = 0;
           while ((sCurrentLine = br.readLine()) != null) {
               no++;
               System.out.println(no + " " + sCurrentLine);
           }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void hapusData() throws IOException {
        File database = new File("db_Mahasiswa.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);
        File tempDB = new File("db_Mahasiswatemp.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);
        Scanner terminalInput = new Scanner(System.in);
        tampilData();
        System.out.print("Masukan nomer : ");
        int deleteNum = terminalInput.nextInt();
        boolean isFound = false;
        
        int entryCounts = 0;
        String data = bufferedInput.readLine();
        while(data != null){
            entryCounts++;
            boolean isDelete = false;
            if(deleteNum == entryCounts){
                isDelete = true;
                isFound = true;
            }
            if(isDelete){
                System.out.println("Data berhasil dihapus!");
            }
            else{
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }
        if(!isFound){
            System.err.println("Data tidak ditemukan!");
        }
        bufferedOutput.flush();
        bufferedOutput.close();
        bufferedInput.close();
        fileInput.close();
        System.gc();
        database.delete();
        tempDB.renameTo(database);
        tampilData();
    }
    public static String db_table = "db_Mahasiswa.txt";
    private static boolean cariDatanilai(String[] keywords, boolean isDisplay) throws IOException{
        FileReader fileInput = new FileReader(db_table);
        BufferedReader bufferInput = new BufferedReader(fileInput);
        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;
        if (isDisplay) {
            System.out.println("\n| No |\tNIM   |\tNama Mahasiswa   |\tJurusan   |\tNilai Akhir   ");
            System.out.println("--------------------------------------------------------------------");
        }
        while(data != null){
            isExist = true;
            for(String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }
            if(isExist){
                if(isDisplay){
                    nomorData++;
                    StringTokenizer stringToken = new StringTokenizer(data, "@");
                    System.out.printf("| %2d ", nomorData);
                    System.out.printf("|\t%-10s", stringToken.nextToken());
                    System.out.printf("|\t%-12s", stringToken.nextToken());
                    System.out.printf("|\t%-12s", stringToken.nextToken());
                    System.out.printf("|\t%-12s", stringToken.nextToken());
                    System.out.print("\n");
                }
                else {
                    break;
                }
            }
            data = bufferInput.readLine();
        }
        if(isDisplay){
            System.out.println("--------------------------------------------------------------------");
        }
        return isExist;
    }
    public static void cariData() throws IOException {
        try {
            File file = new File(db_table);
        }
        catch (Exception e){
            System.err.println("Database tidak ditemukan!");
            System.err.println("Silahkan tambah data terlebih dahulu.");
            return;
        }
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci : ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");
        cariDatanilai(keywords, true);
    }
    public static void menu_pengguna() throws IOException{
        Scanner terminalInput = new Scanner(System.in);
            String pilihanUser;
            boolean isLanjutkan = true;

            while(isLanjutkan){
                System.out.println("Data Mahasiswa");
                System.out.println("1.\tLihat seluruh data mahasiswa");
                System.out.println("2.\tCari data mahasiswa");
                System.out.println("99.\tExit");

                System.out.print("\n\nPilihan anda : ");
                pilihanUser = terminalInput.next();

                switch(pilihanUser){
                    case "1":
                        System.out.println("\n=====================================");
                        System.out.println("LIST SELURUH DATA MAHASISWA");
                        System.out.println("=======================================");
                        lihatData();
                        break;
                    case "2":
                        System.out.println("\n=====================================");
                        System.out.println("CARI DATA MAHASISWA");
                        System.out.println("=======================================");
                        cariData();
                        break;
                    case "99":
                        System.out.println("Terima kasih:)");
                        System.exit(0);
                    default:
                        System.out.println("\nPilihan tidak ditemukan!\nSilahkan pilih (1-5)");
                }
                isLanjutkan = getYesorNo("Apakah ingin kembali ke menu");
            }
    }
    public static void menu_superadmin() throws IOException{
        Scanner terminalInput = new Scanner(System.in);
            String pilihanUser;
            boolean isLanjutkan = true;

            while(isLanjutkan){
                System.out.println("Data Mahasiswa");
                System.out.println("1.\tLihat seluruh data mahasiswa");
                System.out.println("2.\tTambah data mahasiswa");
                System.out.println("3.\tUbah data mahasiswa");
                System.out.println("4.\tHapus data mahasiswa");
                System.out.println("99.\tExit");

                System.out.print("\n\nPilihan anda : ");
                pilihanUser = terminalInput.next();

                switch(pilihanUser){
                    case "1":
                        System.out.println("\n=====================================");
                        System.out.println("LIST SELURUH DATA MAHASISWA");
                        System.out.println("=======================================");
                        lihatData();
                        break;
                    case "2":
                        System.out.println("\n=====================================");
                        System.out.println("TAMBAH DATA MAHASISWA");
                        System.out.println("=======================================");
                        tambahData();
                        break;
                    case "3":
                        System.out.println("\n=====================================");
                        System.out.println("UBAH DATA MAHASISWA");
                        System.out.println("=======================================");
                        updateData();
                        break;
                    case "4":
                        System.out.println("\n=====================================");
                        System.out.println("HAPUS DATA MAHASISWA");
                        System.out.println("=======================================");
                        hapusData();
                        break;
                    case "99":
                        System.out.println("Terima kasih:)");
                        System.exit(0);
                    default:
                        System.out.println("\nPilihan tidak ditemukan!\nSilahkan pilih (1-5)");
                }
                isLanjutkan = getYesorNo("Apakah ingin kembali ke menu");
            }
    }
    public static boolean loginUser(String[] usernm,String passwd) throws IOException{
        FileReader fileInput = new FileReader("pengguna.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);
        String data = bufferInput.readLine();
        boolean isExists = false;
        while(data != null){
            isExists = true;
            for(String keyword:usernm){
                    isExists = isExists && data.toLowerCase().contains(keyword.toLowerCase());
                }
            if(isExists){
                String hasil[] = data.split("\\@");
                String password = hasil[1];
                String pangkat = hasil[2];
                if(password.equals(passwd)){
                    if(pangkat.equals("pengguna")){
                        menu_pengguna();
                    }
                    else{
                        menu_superadmin();
                    }
                }
                else{
                    alert();
                    login();
                }
            }
            data = bufferInput.readLine();
        }
        if(isExists == false){
            alert();
            login();
        }
        return isExists;
    }
    public static void login() throws IOException {
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Username : ");
        String user = terminalInput.nextLine();
        String[] usernm = user.split("\\s+");
        System.out.print("Password : ");
        String passwd = terminalInput.nextLine();
        loginUser(usernm, passwd);
    }
    public static void alert() throws IOException{
        JOptionPane.showMessageDialog(null, "Username dan password salah!!", "INFO KESALAHAN", JOptionPane.ERROR_MESSAGE);
    }
    public static void message() throws IOException{
        JOptionPane.showMessageDialog(null, "Selamat Datang di Data Mahasiswa", "Welcome!!", JOptionPane.INFORMATION_MESSAGE);
    }
}
