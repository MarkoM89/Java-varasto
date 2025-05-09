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
		int kuittitunnus;
        
        
        
        Properties connConfig = new Properties();
        connConfig.setProperty("user", "käyttäjä");
        connConfig.setProperty("password", "salasana");


        
        while (toiminto != 4) {
        	
        	/*
Päätoiminto 1: Varaston hallinta
		Toiminto 1: Varastosaldo
		Toiminto 2: Osta lisää tuotetta varastoon


Päätoiminto 2: Tuotehallinta
		Toiminto 1: Lisää tuote
		Toiminto 2: Poista tuote
		Toiminto 3: Tulosta tuotteet
		
Päätoiminto 3: Seuranta
		Toiminto 1: Selaa kuitteja
		Toiminto 2: Etsi kuitti

		
Päätoiminto 4: Poistu ohjelmasta

        	 
        	 */
        	
        	System.out.print("\nPäätoiminto 1: Varaston hallinta\nPäätoiminto 2: Tuotehallinta\nPäätoiminto 3: Seuranta\n"
        			+ "Päätoiminto 4: Poista ohjelmasta\n");
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
		
		    		System.out.print("\nToiminto 1: Lisää tuote\nToiminto 2: Poista tuote\nToiminto 3: Tulosta tuotteet\n"
		    				+ "Toiminto 4: Poistu tuotehallinnasta\n");
		    			
		    			
		            		toiminto = lukija.nextInt();
		            		lukija.nextLine();
		            		
		            		switch(toiminto) {
		            		case 1:
		            			System.out.println("Tuotteen nimi:");
		            			nimi = lukija.nextLine();
		            			System.out.println("Tuotteen yksikköhinta:");
		            			tuoteHinta = lukija.nextDouble();
		
		            			try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
		    		             	PreparedStatement stmt = conn.prepareStatement("INSERT INTO tuote (tuotenimi, yksikköhinta) VALUES (?, ?)");
		    		            	stmt.setString(1, nimi);
		    		            	stmt.setDouble(2, tuoteHinta);
		    		            	stmt.executeQuery(); 

		    		                    
		    		        } catch (Exception e) {
		    		            e.printStackTrace();
		    		        }
		            		
		            		
		            		break;
		    				case 2:

		    	    			System.out.println("Mikä tuote poistetaan? syötä luku 0, jos et halua poistaa mitään");
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
				    	                    	
				    	                    	System.out.println(tuotetiedot.getString("tuotetunniste")+ " " +tuotetiedot.getString("tuotenimi")+ " " +tuotetiedot.getString("yksikköhinta")+ "€");
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
    			
        System.out.println("\nPäätoiminto 3: Ostoseuranta\n");

        toiminto = 0;

        while (toiminto != 3){


            System.out.println("Toiminto 1: Selaa kuitteja\nToiminto 2: Etsi kuitti\nToiminto 3: Poistu ostoseurannasta\n");
                               
			toiminto = lukija.nextInt();
			lukija.nextLine();

            if (toiminto == 1) {


                System.out.println("\n\nKuitit\n-----------------------------------------\n");
    			
    	        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
    	            try (Statement stmt = conn.createStatement()) {
    	                try (ResultSet kuittitiedot = stmt.executeQuery("SELECT * FROM kuitti")) {
    	                    while (kuittitiedot.next()) {
    	                    	
    	                    	System.out.println(kuittitiedot.getInt("kuittitunnus")+ " " +kuittitiedot.getTimestamp("osto_aika")+ " " +kuittitiedot.getDouble("kokonaishinta")+ "€");
    	                    }
    	                }
    	            }
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }

                System.out.println("\n");
			}


            if (toiminto == 2){

				System.out.println("Anna kuittinumero");

				kuittitunnus = lukija.nextInt();
				lukija.nextLine();


				System.out.println("\n\nKuitti\n-----------------------------------------\n");
    			
		            			try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/pankki_kauppa", connConfig)) {
		    		             	PreparedStatement stmt = conn.prepareStatement("SELECT * FROM kuitti\r\n" + //
																				"INNER JOIN ostettu_tuote ON kuitti.kuittitunnus = ostettu_tuote.kuittitunnus\r\n" + //
																				"INNER JOIN tuote ON tuote.tuotetunniste = ostettu_tuote.tuotetunnus\r\n" + //
																				"WHERE kuitti.kuittitunnus = ?;");
		    		            	stmt.setInt(1, kuittitunnus);
		    		            	ResultSet kuittitiedot = stmt.executeQuery();

								while (kuittitiedot.next()) {
    	                    	System.out.println(kuittitiedot.getInt("kuittitunnus")+ " " +kuittitiedot.getTimestamp("osto_aika")+ " " +kuittitiedot.getDouble("kokonaishinta")+ "€");
    	                    }

		    		                    
		    		        } catch (Exception e) {
		    		            e.printStackTrace();
		    		        }
							System.out.println("\n");

		}
		}


    			break;
    		case 4:
    			System.out.println("Poistutaan ohjelmasta");
    			break;


    			
    		default:
    			System.out.println("Vain numerot 1-4");
    		}
        
        }

	}

}
