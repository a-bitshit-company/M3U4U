package src.Types;

public class File {
    private int songId;
    private String filePath;

    public File(int songId, String filePath){
        this.songId = songId;
        this.filePath = filePath;
    }

	public int getSongId() {
		return songId;
	}

	public String getFilePath() {
		return filePath;
	}
    
}
