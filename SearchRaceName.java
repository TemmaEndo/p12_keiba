/**
 * SearchRaceName
 * レースの名前検索に責任を持つクラス
 * @author Daiki Onda
 */

import java.util.*;
import java.sql.*;
public class SearchRaceName extends SearchRace{ 


    SearchRaceName(){
        super("SELECT race.ID,race.name,raceRank,year,tracksName,raceNum,baba,dist,going,temperature,weather FROM race,held,tracks WHERE ID =raceID AND tracksName =tracks.name AND race.name LIKE ? ORDER BY year;","レース名");
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
    //void horceInfoDisplay(ResultSet rs){
}