/**
 * EditAddHorse
 * 馬要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class AddHorse extends Add{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO horse(horse.name, birthday, sex) VALUES (?, ?, ?) ";
    String sql2 = " SELECT MAX(trainer.ID) FROM trainer INTO TrainerID";
    String sql3 = " INSERT INTO trainer(trainer.ID, trainer.name) VALUES (?, ?) ";
    String sql4 = " SELECT MAX(owner.ID) FROM owner ";
    String sql5 = " INSERT INTO ownner(owner.ID, owner.name) VALUES (?, ?) ";

    @Override
    void DoAdd(){
        try{
            System.out.println("馬要素の入力: 名前(VARCHAR(9)),生年月日(DATE),性別(VARCHAR(2))");
            String horseData = scanner.nextLine();

            String splitHorseData[] = horseData.split(",");

            DBChange(sql1, splitHorseData);

            System.out.println("調教師要素の入力: 名前(VARCHAR(9))");
            String TrainerName = scanner.nextLine();

            rs = st.executeQuery(sql2);
            int TrainerID = rs.getInt("trainer.ID");
            TrainerID++;

            DBChange(sql3, TrainerID, TrainerName);

            System.out.println("馬主要素の入力: 名前(VARCHAR(9))");
            String OwnerName = scanner.nextLine();

            rs = st.executeQuery(sql3);
            int OwnerID = rs.getInt("owner.ID");
            OwnerID++;

            DBChange(sql5, OwnerID, OwnerName);

            st.executeUpdate();
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