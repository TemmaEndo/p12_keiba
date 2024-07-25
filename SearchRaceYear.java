/**
 * SearchRaceYear
 * レースの開催年検索に責任を持つクラス
 * @author Daiki Onda
 */

import java.util.*;
import java.sql.*;
public class SearchRaceYear extends SearchRace{ 


    SearchRaceYear(){
        super("SELECT race.ID,race.name,raceRank,year,tracksName,raceNum,baba,dist,going,temperature,weather FROM race,held,tracks WHERE ID =raceID AND tracksName =tracks.name AND year LIKE ? ORDER BY raceID;","開催年");
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
}