package hexlet.code.music;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class MusicService {
    private static SongDAO songDAO;
    public static void initSongDAO(SongDAO dao) {
        songDAO = dao;
    }
    static {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./hexlet");
            songDAO = new SongDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Song> getSongByTitle (String condition) throws SQLException {
        List<Song> songs = songDAO.find(condition);
        return songs;
    }
}
