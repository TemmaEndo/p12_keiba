/**
 * EditAddRetiredHorse
 * 引退馬要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class EditAddHorse extends EditAdd{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO family(Name, Date) VALUES (?, ?) ";
    //String sql2 = " SELECT MAX(???) FROM family "; データベースの量分からん

    @Override
    void DoAdd(){
        try{
            System.out.println("引退馬要素の入力: 親の名前(VARCHAR(9)), 引退日(DATE)");
            String Name = scanner.nextLine();

            st = conn.horse(sql1);

            //int ID = 1 + DBInquory(this.sql2); データベースの量分からん

            st.setString(1, Name);
            st.setDATE(2, Date);

            st.executeUpdate();

        } catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}