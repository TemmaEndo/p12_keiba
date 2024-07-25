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
    void DBAdd(){
        try{
            System.out.println("馬要素の入力: 名前(VARCHAR(9)),生年月日(DATE),性別(VARCHAR(2))");
            String horseData = scanner.nextLine();

            String splitHorseData[] = horseData.split(",");
            
            String HorseName = splitHorseData[0];
            Date HorseBirthday = java.sql.Date.valueOf(splitHorseData[1]);
            String HorseSex = splitHorseData[2];
            
            st = conn.horse(sql1);

            st.setString(1, HorseName);
            st.setDATE(2, HorseBirthday);
            st.setString(3, HorseSex);

            st.executeUpdate();

            System.out.println("調教師要素の入力: 名前(VARCHAR(9))");
            String TrainerName = scanner.nextLine();

            rs = trainer.executeQuery(sql2);

            int TrainerID = rs.getInt("trainer.ID");
            TrainerID++;

            st = conn.trainer(sql3);

            st.setInt(4, TrainerID);
            st.setString(5, TrainerName);

            st.executeUpdate();

            System.out.println("馬主要素の入力: 名前(VARCHAR(9))");
            String OwnerName = scanner.nextLine();

            rs = owner.executeQuery(sql3);

            int OwnerID = rs.getInt("owner.ID");
            OwnerID++;

            st = conn.owner(sql5);

            st.setString(4, OwnerID);
            st.setDATE(5, OwnerName);

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