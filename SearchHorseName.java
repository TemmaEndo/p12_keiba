/**
 * SearchRaceName
 * レースの名前検索に責任を持つクラス
 * @author Daiki Onda
 */

import java.util.*;
import java.sql.*;
public class SearchHorseName extends SearchHorse{ 


    SearchHorseName(){
        super("SELECT name,birthday FROM horce WHERE name LIKE ? ORDER BY birthday DESC;","馬名");
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
    //void horceInfoDisplay(ResultSet rs){
}