BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "administrator" (
	"id"	INTEGER,
	"email"	TEXT,
	"password"	TEXT,
	"uniqueId"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "inspector" (
	"id"	INTEGER,
	"name"	TEXT,
	"lastName"	TEXT,
	"birthdate"	TEXT,
	"jmbg"	TEXT,
	"gender"	INTEGER,
	"idNumber"	TEXT,
	"residence"	TEXT,
	"phoneNumber"	TEXT,
	"email"	TEXT,
	"loginEmail"	TEXT,
	"password"	TEXT,
	"license"	INTEGER,
	"uniqueId"	TEXT,
	"inspectorType"	TEXT,
	"inspectionArea"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "loginData" (
	"userID"	INTEGER,
	"loginDate"	TEXT,
	"stayLogged"	INTEGER,
	"uniqueId"	TEXT,
	PRIMARY KEY("userID")
);
CREATE TABLE IF NOT EXISTS "actionLog" (
	"id"	INTEGER,
	"dateTime"	TEXT,
	"action"	TEXT,
	"uniqueId"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "loginLog" (
	"id"	INTEGER,
	"loginDateTime"	TEXT,
	"logoutDateTime"	TEXT,
	"uniqueId"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "object" (
	"id"	INTEGER,
	"ownerId"	INTEGER,
	"name"	TEXT,
	"address"	TEXT,
	"type"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "witness" (
	"id"	INTEGER,
	"reportId"	INTEGER,
	"name"	TEXT,
	"surename"	TEXT,
	"jmbg"	TEXT,
	"statement"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "owner" (
	"id"	INTEGER,
	"name"	TEXT,
	"surename"	TEXT,
	"jmbg"	TEXT,
	"phoneNumber"	INTEGER,
	"email"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "task" (
	"id"	INTEGER,
	"objectId"	INTEGER,
	"inspectorId"	INTEGER,
	"datetime"	TEXT,
	"notes"	TEXT,
	"done"	INTEGER,
	"assignedInspectorId"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "report" (
	"id"	INTEGER,
	"inspectorId"	INTEGER,
	"inspectionDate"	TEXT,
	"description"	TEXT,
	"violation"	INTEGER,
	"fine"	NUMERIC,
	"additionalRequirements"	TEXT,
	"recordedWorkersNumber"	INTEGER,
	"criminalOffense"	INTEGER,
	"phytocertificate"	INTEGER,
	"sampleTaken"	INTEGER,
	"daysClosed"	INTEGER,
	"openingCondition"	TEXT,
	"reportedWorksite"	INTEGER,
	"employeeNumber"	INTEGER,
	"openingCertificateNumber"	INTEGER,
	"defect"	TEXT,
	"firstWitnessId"	INTEGER,
	"secondWitnessId"	INTEGER,
	"objectId"	INTEGER,
	"objectName"	TEXT,
	"objectAddress"	TEXT,
	"uniqueId"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "administrator" VALUES (1,'admin@gmail.com','admin','admin1');
INSERT INTO "inspector" VALUES (0,'Adnan','Hodzic','07.04.2000','1234567891234',1,'ABCD123','Kakanj','062734086','adnan@gmail.com','ahodzic11@etf.unsa.ba','adnan123',1,'ASDFEO','Federal inspector','Business property');
INSERT INTO "loginData" VALUES (5,'25/09/2021 04:05:03',1,'ABCDPF');
INSERT INTO "loginLog" VALUES (0,'13/09/2021 04:05:03','13/09/2021 06:07:08','PLSFOE');
INSERT INTO "loginLog" VALUES (1,'03/10/2021 09:05:04','10/10/2021 08:53:12','PASDAS');
INSERT INTO "loginLog" VALUES (2,'03/11/2021 09:05:04','10/11/2021 08:53:12','ASGWDS');
INSERT INTO "loginLog" VALUES (3,'03/12/2021 09:05:04','10/12/2021 08:53:12','ASDFSA');
INSERT INTO "witness" VALUES (0,1239,'Dino','Mujkić','1295120351235','I have found a product with an expired date');
INSERT INTO "witness" VALUES (1,1239,'Adnan','Hodzic','1350124060239','Product date expired...');
INSERT INTO "owner" VALUES (0,'Adnan','Hodzic','1234567891234',8539340,'ahodzic11@etf.unsa.ba');
INSERT INTO "task" VALUES (0,3,1,'13/09/2021 07:12:53','No notes taken',1,13);
INSERT INTO "report" VALUES (0,2,'13/09/2021 04:05:03','Description filled','Products expired',135,'None',0,0,1,1,25,'None',1,15,123539,'No defect',13,14,3,'Del','Alije Izetbegovića 13','ABCDEF');
COMMIT;
