import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		
        Scanner lukija = new Scanner(System.in);
        int toiminto = 0;
        String nimi;
        int tuotetunniste = 0;
        boolean loytyi = false;
        double tuoteHinta;
        
        
        
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "käyttäjä");
        connConfig.setProperty("password", "salasana");


        
        while (toiminto != 4) {
        	
        	/*
P��toiminto 1: Varaston hallinta
		Toiminto 1: Varastosaldo
		Toiminto 2: Osta lis�� tuotetta varastoon


P��toiminto 2: Tuotehallinta
		Toiminto 1: Lis�� tuote
		Toiminto 2: Poista tuote
		Toiminto 3: Tulosta tuotteet
		
P��toiminto 3: Seuranta
		Toiminto 1: Selaa kuitteja
		Toiminto 2: Etsi kuitti

		
P��toiminto 4: Poistu ohjelmasta

        	 
        	 */
        	
        	System.out.print("\nP��toiminto 1: Varaston hallinta\nP��toiminto 2: Tuotehallinta\nP��toiminto 3: Seuranta\n"
        			+ "P��toiminto 4: Poista ohjelmasta\n");
        	toiminto = lukija.nextInt();
        	lukija.nextLine();
        	
        	switch(toiminto) {
    		case 1:

    			System.out.println("Varaston hallinta");
    			
    			break;
    		case 2:
    			System.out.println("Tuotehallinta");
    			
    			toiminto = 0;
		    		while (toiminto != 4) {
		    		System.out.println("Tuotteiden hallinta");
		
		    		System.out.print("\nToiminto 1: Lis�� tuote\nToiminto 2: Poista tuote\nToiminto 3: Tulosta tuotteet\n"
		    				+ "Toiminto 4: Poistu tuotehallinnasta\n");
		    			
		    			
		            		toiminto = lukija.nextInt();
		            		lukija.nextLine();
		            		
		            		switch(toiminto) {
		            		case 1:
		            			System.out.println("Tuotteen nimi:");
		            			nimi = lukija.nextLine();
		            			System.out.println("Tuotteen yksikk�hinta:");
		            			tuoteHinta = lukija.nextDouble();
		
		            			try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
		    		             	PreparedStatement stmt = conn.prepareStatement("INSERT INTO tuote (tuotenimi, yksikk�hinta) VALUES (?, ?)");
		    		            	stmt.setString(1, nimi);
		    		            	stmt.setDouble(2, tuoteHinta);
		    		            	stmt.executeQuery(); 

		    		                    
		    		        } catch (Exception e) {
		    		            e.printStackTrace();
		    		        }
		            		
		            		
		            		break;
		    				case 2:

		    	    			System.out.println("Mik� tuote poistetaan? sy�t� luku 0, jos et halua poistaa mit��n");
		    	    			tuotetunniste = lukija.nextInt();
		    	    			lukija.nextLine();
		    	    			
		    	    			
		    	    			if(tuotetunniste != 0) {
		            			try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
		    		             	PreparedStatement stmt = conn.prepareStatement("DELETE FROM tuote WHERE tuotetunniste = ?");
		    		            	stmt.setInt(1, tuotetunniste);
		    		            	stmt.executeQuery(); 

		    		                    
		    		        } catch (Exception e) {
		    		            e.printStackTrace();
		    		        }
		    	    	}
		
		    	    			break;
		            		
							case 3:
								System.out.println("Seuranta");
		
								
				    			System.out.println("Tulosta tuotteet");
				    			
				    	        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
				    	            try (Statement stmt = conn.createStatement()) {
				    	                try (ResultSet tuotetiedot = stmt.executeQuery("SELECT * FROM tuote")) {
				    	                    while (tuotetiedot.next()) {
				    	                    	
				    	                    	System.out.println(tuotetiedot.getString("tuotetunniste")+ " " +tuotetiedot.getString("tuotenimi")+ " " +tuotetiedot.getString("yksikk�hinta")+ "�");
				    	                    }
				    	                }
				    	            }
				    	        } catch (Exception e) {
				    	            e.printStackTrace();
				    	        }
				    			
				    			break;
		            		}
		    			}
		    		toiminto = 0;
    		
    			break;
    			
    		case 3:
    			
    			System.out.println("Seuranta");
    			

    			break;
    		case 4:
    			System.out.println("Poistutaan ohjelmasta");
    			break;

    		case 5:
    			System.out.println("Tulosta korttien tiedot");
    			
    	        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
    	            try (Statement stmt = conn.createStatement()) {
    	                try (ResultSet tilitiedot = stmt.executeQuery("SELECT * FROM pankki")) {
    	                    while (tilitiedot.next()) {
    	                    	
    	                    	System.out.println(tilitiedot.getString("tunniste")+ " " +tilitiedot.getString("nimi")+ " " +tilitiedot.getString("saldo")+ "�");
    	                    }
    	                }
    	            }
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	        
    			System.out.println("\n\nTulosta kuitit");
    			
    	        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
    	            try (Statement stmt = conn.createStatement()) {
    	                try (ResultSet kuittitiedot = stmt.executeQuery("SELECT * FROM kuitti")) {
    	                    while (kuittitiedot.next()) {
    	                    	
    	                    	System.out.println(kuittitiedot.getInt("kuittitunnus")+ " " +kuittitiedot.getTimestamp("osto_aika")+ " " +kuittitiedot.getDouble("kokonaishinta")+ "�");
    	                    }
    	                }
    	            }
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    			

    			break;
    			
    		default:
    			System.out.println("Vain numerot 1-4");
    		}
        
        }

	}

}
