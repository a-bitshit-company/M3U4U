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

CREATE TABLE Songs
(
	SongId INT PRIMARY KEY AUTO_INCREMENT,
	PlaylistId int,
	name VARCHAR(128),
	FOREIGN KEY (PlaylistId) REFERENCES Playlists(Playlistid)
);

CREATE TABLE Files
(
	SongId int NOT NULL,
	file LONGBLOB NOT NULL,
	FOREIGN KEY (SongID) REFERENCES Songs(SongId)
);

