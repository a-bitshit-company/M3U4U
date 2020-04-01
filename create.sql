DROP DATABASE IF EXISTS M3U4U;
CREATE DATABASE M3U4U;
USE M3U4U;

CREATE TABLE Playlists
(
	PlaylistId INT PRIMARY KEY AUTO_INCREMENT,
	name varchar(128),
	genre varchar(128),
	description varchar(8000)
);

CREATE TABLE Files
(
	SongId int NOT NULL  KEY AUTO_INCREMENT,
	file LONGBLOB NOT NULL
);

CREATE TABLE Songs
(
	SongId INT NOT NULL,
	PlaylistId int,
	name VARCHAR(128),
	FOREIGN KEY (PlaylistId) REFERENCES Playlists(Playlistid),
	FOREIGN KEY (SongId) REFERENCES files(SongId)
);


