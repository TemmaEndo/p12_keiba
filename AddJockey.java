/**
 * EditAddJockey
 * 騎手要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class AddJockey extends Add{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO jockey(jockey.ID, jockey.name) VALUES (?, ?) ";
    String sql2 = " SELECT MAX(jockey.ID) FROM jockey ";

    @Override
    void DoAdd(){
        try{
            System.out.println("騎手要素の入力: 名前(VARCHAR(9))");
            String JockeyName = scanner.nextLine();

            rs = st.executeQuery(sql2);
            int JockeyID = rs.getInt("jockey.ID");
            JockeyID++;
            
            DBChange(sql1, JockeyID);

        } catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
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