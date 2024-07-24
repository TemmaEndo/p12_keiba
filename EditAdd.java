/**
 * EditAdd
 * 要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public abstract class EditAdd {

    Connection conn;
    PreparedStatement st;
    ResultSet rs;

    abstract void DoAdd();
    ResultSet DBInquory(String sql){
		try {
            st=conn.prepareStatement(sql);

            // SQLを実行して、実行結果をResultSetに入れる
            rs=st.executeQuery();
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
        return rs;
    }
}