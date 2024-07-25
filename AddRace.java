/**
 * EditAddRace
 * レース要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class AddRace extends Add{
    String sql1 = " INSERT INTO Race(ID, year, date, raceNum, name, baba, raceRank, num, distance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    String sql2 = " SELECT MAX(race.ID) FROM race ";

    @Override
    void DbAdd(){
        try{
            System.out.println("レース要素の入力: csvのパス");
            // ID(INT), 開催年(YEAR), 開催日(DATE), レース番号(INT), レース名(VARCHAR(20)), 馬場(VARCHAR(3)), レースランク(VARCHAR(2)), 頭数(INT), 距離(INT)
           
            rs = st.executeQuery(sql1);
            int RaceID = rs.getInt("race.ID");
            RaceID++;

           //CSVからデータを受け取り、各変数に格納する。

            st.setInt(1, RaceID);

            st.setYEAR(2, Year);
            st.setDATE(3, Date);
            st.setInt(4, RaceNum);
            st.setString(5, Name);
            st.setString(6, TrackCondition);
            st.setString(7, RaceRank);
            st.setInt(8, NumHorses);
            st.setInt(9, Distance);

            st.executeUpdate();
        }catch (SQLException se) {
            System.out.println("SQL Error 1: " + se.toString() + " "
                + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
}