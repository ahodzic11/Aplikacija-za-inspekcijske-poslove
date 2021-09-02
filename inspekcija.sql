BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "vlasnik" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"jmbg"	TEXT,
	"telefon"	INTEGER,
	"e-mail"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "svjedok" (
	"id"	INTEGER,
	"idIzvjestaja"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"jmbg"	TEXT,
	"izjava"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "izvjestaj" (
	"id"	INTEGER,
	"inspektorId"	INTEGER,
	"datumInspekcije"	TEXT,
	"opis"	TEXT,
	"prekrsaj"	INTEGER,
	"novcanaKazna"	NUMERIC,
	"dodatniZahtjevi"	TEXT,
	"brojEvidentiranihRadnika"	INTEGER,
	"prijavaOKrivicnomDjelu"	INTEGER,
	"fitocertifikat"	INTEGER,
	"uzetUzorak"	INTEGER,
	"brojDanaZabrane"	INTEGER,
	"uslovZabrane"	TEXT,
	"prijavljenoRadiliste"	INTEGER,
	"brojZaposlenih"	INTEGER,
	"brojPotvrdeORadu"	INTEGER,
	"nedostatak"	TEXT,
	"svjedok1"	INTEGER,
	"svjedok2"	INTEGER,
	"objekatId"	INTEGER,
	"nazivObjekta"	TEXT,
	"adresaObjekta"	TEXT,
	"jedinstvenaSifra"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "termin" (
	"id"	INTEGER,
	"objekatId"	INTEGER,
	"inspektorId"	INTEGER,
	"datumVrijeme"	TEXT,
	"napomene"	TEXT,
	"odrađen"	INTEGER,
	"zaduzeniInspektorId"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "objekat" (
	"id"	INTEGER,
	"vlasnikId"	INTEGER,
	"nazivObjekta"	TEXT,
	"adresa"	TEXT,
	"vrstaObjekta"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "logovi" (
	"id"	INTEGER,
	"datumVrijemePrijave"	TEXT,
	"datumVrijemeOdjave"	TEXT,
	"jedinstvenaSifra"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "inspektor" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"datumRodjenja"	TEXT,
	"jmbg"	TEXT,
	"spol"	INTEGER,
	"brojLicne"	TEXT,
	"mjestoPrebivalista"	TEXT,
	"kontaktTelefon"	TEXT,
	"personalniEmail"	TEXT,
	"pristupniEmail"	TEXT,
	"pristupnaSifra"	TEXT,
	"vozacka"	INTEGER,
	"jedinstvenaSifra"	TEXT,
	"tipInspektora"	TEXT,
	"oblastInspektora"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "administrator" (
	"id"	INTEGER,
	"email"	TEXT,
	"sifra"	TEXT,
	"jedinstvenaSifra"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "loginPodaci" (
	"idUsera"	INTEGER,
	"datumLogovanja"	TEXT,
	"ostaniUlogovan"	INTEGER,
	"jedinstvenaSifra"	TEXT,
	PRIMARY KEY("idUsera")
);
CREATE TABLE IF NOT EXISTS "logoviAkcija" (
	"id"	INTEGER,
	"datumVrijeme"	TEXT,
	"akcija"	TEXT,
	"jedinstvenaSifra"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "vlasnik" VALUES (0,'Dino','Kovačević','123123',23021,'dinok@gmail.com');
INSERT INTO "svjedok" VALUES (0,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (1,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (2,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (3,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (4,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (5,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (6,1,'q','q','q','q');
INSERT INTO "svjedok" VALUES (7,1,'q','qq','q','q');
INSERT INTO "svjedok" VALUES (8,2,'a','a','a','a');
INSERT INTO "svjedok" VALUES (9,2,'a','a','a','a');
INSERT INTO "svjedok" VALUES (10,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (11,0,'b','b','b','b');
INSERT INTO "svjedok" VALUES (12,0,'a','a','a','a');
INSERT INTO "svjedok" VALUES (13,0,'b','b','b','bb');
INSERT INTO "svjedok" VALUES (14,1,'a','a','a','a');
INSERT INTO "svjedok" VALUES (15,1,'a','a','a','a');
INSERT INTO "svjedok" VALUES (16,2,'b','b','b','b');
INSERT INTO "svjedok" VALUES (17,2,'b','b','b','b');
INSERT INTO "svjedok" VALUES (18,3,'aa','a','a','a');
INSERT INTO "svjedok" VALUES (19,3,'a','a','a','a');
INSERT INTO "izvjestaj" VALUES (0,-1,'30/08/2021','abBIOSAMTUADMIN',2,2,'2',1,1,0,0,2,'',1,2,2,'2',0,1,1,'Praonica','Povezice',NULL);
INSERT INTO "objekat" VALUES (1,0,'Praonica','Povezice','Obrazovna institucija');
INSERT INTO "objekat" VALUES (2,0,'Del','Alije Izetbegovića BB','Ugostiteljski objekat');
INSERT INTO "logovi" VALUES (0,'01/09/2021 11:39:17','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (1,'01/09/2021 11:39:22','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (2,'01/09/2021 11:48:28','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (3,'01/09/2021 11:48:54','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (4,'01/09/2021 11:48:58','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (5,'01/09/2021 11:50:34','01/09/2021 11:50:37','admin');
INSERT INTO "logovi" VALUES (6,'01/09/2021 11:50:39','01/09/2021 11:50:59','admin');
INSERT INTO "logovi" VALUES (7,'01/09/2021 11:51:01','01/09/2021 11:51:03','KGOPEFK');
INSERT INTO "logovi" VALUES (8,'01/09/2021 11:51:05','','admin');
INSERT INTO "logovi" VALUES (9,'01/09/2021 11:52:42','','admin');
INSERT INTO "logovi" VALUES (10,'01/09/2021 11:56:38','','admin');
INSERT INTO "logovi" VALUES (11,'01/09/2021 11:57:01','','admin');
INSERT INTO "logovi" VALUES (12,'01/09/2021 11:57:45','','admin');
INSERT INTO "logovi" VALUES (13,'01/09/2021 11:58:44','','admin');
INSERT INTO "logovi" VALUES (14,'01/09/2021 11:59:16','','admin');
INSERT INTO "inspektor" VALUES (0,'Adnan','Hodžić','07.04.2000','1900012400070',1,'SFSAFSADAS','Kakanj, Povezice','062734086','hodzicadnan00@gmail.com','adnan','adnan',0,'KGOPEFK','Federalni inspektor','');
INSERT INTO "inspektor" VALUES (1,'Lejan','Kozlić','01.09.2021','2',2,'2','2','2','2','lejan','lejan',0,'ASJFPPFS','Federalni inspektor','Sanitarni inspektor');
INSERT INTO "administrator" VALUES (1,'admin','admin','admin');
INSERT INTO "loginPodaci" VALUES (-1,'01/09/2021 11:59:15',0,'admin');
COMMIT;
