package src.Types;

import java.io.File;

public class MP3File {
    private int songId;
    private File file;

    public MP3File(int songId, File file){
        this.songId = songId;
        this.file = file;
    }

	public int getSongId() {
		return songId;
	}

	public File getFile() {
		return file;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public void setFilePath(File file) {
		this.file = file;
	}
}
