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
CREATE TABLE IF NOT EXISTS "administrator" (
	"id"	INTEGER,
	"email"	TEXT,
	"password"	TEXT,
	"uniqueId"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "inspector" (
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
INSERT INTO "vlasnik" VALUES (0,'Dino','Kovačević','123123',23021,'dinok@gmail.com');
INSERT INTO "izvjestaj" VALUES (0,-1,'30/08/2021','abBIOSAMTUADMIN',2,2,'2',1,1,0,0,2,'',1,2,2,'2',0,1,1,'Praonica','Povezice',NULL);
INSERT INTO "izvjestaj" VALUES (1,1,'01/09/2021','s','a',232323232,'2',1,1,1,1,2,'',1,2,2,'212',6,7,2,'Del','Alije Izetbegovića BB','');
INSERT INTO "objekat" VALUES (1,0,'Praonica','Povezice','Obrazovna institucija');
INSERT INTO "objekat" VALUES (2,0,'Del','Alije Izetbegovića BB','Ugostiteljski objekat');
INSERT INTO "logovi" VALUES (0,'02/09/2021 18:52:11','03/09/2021 18:20:32','ASFOKD');
INSERT INTO "logovi" VALUES (1,'03/09/2021 16:17:30','03/09/2021 16:17:53','admin');
INSERT INTO "logovi" VALUES (2,'03/09/2021 16:18:00','03/09/2021 18:20:32','ASFOKD');
INSERT INTO "logovi" VALUES (3,'03/09/2021 16:26:29','03/09/2021 18:20:32','ASFOKD');
INSERT INTO "logovi" VALUES (4,'03/09/2021 18:20:38','03/09/2021 18:25:22','admin');
INSERT INTO "logovi" VALUES (5,'03/09/2021 18:34:58','','admin');
INSERT INTO "loginPodaci" VALUES (-1,'03/09/2021 18:34:58',1,'admin');
INSERT INTO "logoviAkcija" VALUES (0,'02-09-2021 17:54:19','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (1,'02-09-2021 17:55:19','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (2,'02/09/2021 17:55:33','Administrator [admin] exported profile - Adnan Hodžić [ASFOKD] ','admin');
INSERT INTO "logoviAkcija" VALUES (3,'02-09-2021 18:00:31','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (4,'02-09-2021 18:02:13','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (5,'02-09-2021 18:05:33','Administrator[admin] deleted account - Federal inspector 2 2[]','admin');
INSERT INTO "logoviAkcija" VALUES (6,'02-09-2021 18:05:58','Administrator[admin] deleted account - Federal inspector 2 3[]','admin');
INSERT INTO "logoviAkcija" VALUES (7,'02-09-2021 18:16:10','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (8,'02-09-2021 18:49:17','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (9,'03-09-2021 16:17:33','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (10,'03-09-2021 19:25:45','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (11,'03-09-2021 19:25:56','Administrator[admin] opened account - Adnan Hodžić[ASFOKD]','admin');
INSERT INTO "logoviAkcija" VALUES (12,'03/09/2021 19:33:54','Administrator [admin] exported profile - Adnan Hodžić [UNIQUE ID: ASFOKD] ','admin');
INSERT INTO "administrator" VALUES (1,'admin','admin','admin');
INSERT INTO "inspector" VALUES (0,'Adnan','Hodžić','01.09.2021','1231251232423',1,'FASOJDFOS','Kakanj','062734086','hodzicadnan00@gmail.com','adnan','adnan',1,'ASFOKD','Major federal inspector','Inspektor rada');
COMMIT;
