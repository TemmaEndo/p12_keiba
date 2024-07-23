/**
 * EditAddHorse
 * 馬要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class EditAddHorse extends EditAdd{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO horse(Horse, HorseBirthday, HorseSex) VALUES (?, ?, ?) ";
    String sql2 = " SELECT MAX(TrainerID) FROM trainer ";
    String sql3 = " INSERT INTO trainer(TrainerID, TrainerName) VALUES (?, ?) ";
    String sql4 = " SELECT MAX(OwnerID) FROM owner ";
    String sql5 = " INSERT INTO trainer(OwnerID, OwnerName) VALUES (?, ?) ";

    @Override
    void DoAdd(){
        try{
            System.out.println("馬要素の入力: 名前(VARCHAR(9)),生年月日(DATE),性別(VARCHAR(2))");
            String horseData = scanner.nextLine();

            st = conn.horse(sql1);

            st.setString(1, HorseName);
            st.setDATE(2, HorseBirthday);
            st.setString(3, HorseSex);

            st.executeUpdate();

            System.out.println("調教師要素の入力: 名前(VARCHAR(9))");
            String TrainerName = scanner.nextLine();

            int TrainerID = 1 + DBInquory(this.sql2);

            st = conn.trainer(sql3);

            st.setString(4, TrainerID);
            st.setDATE(5, TrainerName);

            st.executeUpdate();

            System.out.println("馬主要素の入力: 名前(VARCHAR(9))");
            String OwnerName = scanner.nextLine();

            int OwnerID = 1 + DBInquory(this.sql4);

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
}