

public class Tuote {
	
	private String nimi;
	private double hinta;
	
	public Tuote (String nimi, double hinta) {
		this.nimi = nimi;
		this.hinta = hinta;
	}
	
	public String haeNimi () {
		return this.nimi;
	}
	
	public double haeHinta() {
		return this.hinta;
	}
	
	
	public void tulostaTuote() {
		System.out.println(this.nimi+ ", hinta " +this.hinta+ "€");
	}

}
