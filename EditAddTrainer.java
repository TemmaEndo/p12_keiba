/**
 * EditAddTrainer
 * 調教師要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class EditAddTrainer extends EditAdd{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO trainer(TrainerID, TrainerName) VALUES (?, ?) ";
    String sql2 = " SELECT MAX(TrainerID) FROM trainer ";
    String sql3 = " INSERT INTO barn(Name, Address) VALUES (?, ?) ";

    @Override
    void DoAdd(){
        try{
            System.out.println("馬主要素の入力: 名前(VARCHAR(9))");
            String JockeyName = scanner.nextLine();

            int TrainerID = 1 + DBInquory(this.sql1);
            
            st = conn.trainer(sql2);

            st.setInt(1, TrainerID);
            st.setString(2, TrainerName);

            st.executeUpdate();

            System.out.println("厩舎要素の入力: 名前(VARCHAR(3)), 所在(VARCHAR(128)");
            String OwnerName = scanner.nextLine();

            st = conn.barn(sql3);

            st.setString(3, Name);
            st.setString(4, Address);

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