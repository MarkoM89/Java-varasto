# Java varastonhallinta

## 1. Mist� on kyse?

Javalla tehty varastonhallinta, joka on samanlainen kuin toinen tekem�ni projekti, jossa on Python-ohjelmointikieli k�yt�ss�. Ohjelmalla toteutan sovelluksen mill� hallitaan kaupan varastoja tuotteiden osalta, niiden lis��minen kaupan tietokantaan, poistaminen tietokannasta, seuranta ja lis�m��rien osto.

Tuotteet ovat MariaDb-tietokannassa.

## 2. Rajoitukset

Ohjelma on rajoitettu tuotteisiin, niiden k�sittelyyn, seurantaan ja ostamiseen vaikka kaupalla voisi my�s esimerkiksi olla kaupan henkil�st�n hallinta.

## 3. Tietokannan rakenne

...

## 3. Ohjelman k�ytt��notto

### 3.1 Tietokannan asennus

Ohjelma k�ytt�� MariaDB-tietokantaj�rjestelm��, p��ohjelmassa sy�tet��n alussa tietokannasta osat tiedoista, jotka ovat k�ytt�nimi, salasana, verkkokone / ip-osoite, portti sek� tietokannan nimi:

	    Properties connConfig = new Properties();
        connConfig.setProperty("user", "root");
        connConfig.setProperty("password", "T13t0k4!?t4");
        
Kun, tietokantaan otetaan yhteytt�, sy�tet��n seuraavaan k�skyt loput tiedot, verkkokone / ip-osoite, portti sek� tietokannan nimi:

		(Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/kokeilutietokanta", connConfig)
		
connConfig on Properties-muuttujan nimi, johon sy�tet��n k�ytt�j�nimi ja salasana

### 3.2 Taulukkojen luonti ja esiluotujen tietojen sy�tt�

CREATE TABLE pankki(
tunniste INT PRIMARY KEY AUTO_INCREMENT,
nimi VARCHAR(30) NOT NULL,
saldo DECIMAL(12,4) NOT NULL
);

INSERT INTO pankki (nimi, saldo)
VALUES
('Mikko', 200.00),
('Noora', 200.00),
('Tapio', 200.00),
('Otto', 200.00);

CREATE TABLE tuote(
tuotetunniste INT PRIMARY KEY AUTO_INCREMENT,
tuotenimi VARCHAR(30) NOT NULL,
yksikk�hinta DECIMAL(6,2) NOT NULL
);

INSERT INTO tuote (tuotenimi, yksikk�hinta)
VALUES
('Kurkku', 0.48),
('Tomaatti', 1.29),
('Ananas', 2.72),
('Omena', 0.41);

CREATE TABLE kuitti(
kuittitunnus INT PRIMARY KEY AUTO_INCREMENT,
osto_aika TIMESTAMP NOT NULL,
kokonaishinta DECIMAL(8,4) NOT NULL
);

CREATE TABLE ostettu_tuote(
tunnus INT PRIMARY KEY AUTO_INCREMENT,
kuittitunnus INT NOT NULL,
tuotetunnus INT NOT NULL,
tuotem��r� INT NOT NULL,
FOREIGN KEY (kuittitunnus) REFERENCES kuitti(kuittitunnus),
FOREIGN KEY (tuotetunnus) REFERENCES tuote(tuotetunniste)
);
