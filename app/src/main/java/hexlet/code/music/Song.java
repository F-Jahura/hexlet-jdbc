package hexlet.code.music;

public class Song {
    private int id;
    private String title;
    private String singer_name;

    public Song(String title, String singer_name) {
        this.title = title;
        this.singer_name = singer_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }
}
