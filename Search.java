/**
 * SearchRace
 * レース検索に責任を持つクラス
 * @author Daiki Onda
 */

 import java.util.*;
 import java.sql.*;

public abstract class Search{
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;

    abstract void DoSearch();
    ResultSet  DBInquory(String sql,String keyWord){
		try {
            st=conn.prepareStatement(sql);

            // 本来はここで入力された文字列が不正なものでないか検査した方が良い
            st.setString(1, keyWord); // ここでSQLの ? の場所に値を埋め込んでいる

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
    String InputKeyword(String targetName){
        Scanner scanner = new Scanner(System.in);
        System.out.println(targetName+"を入力してください:");
        String keyWord=scanner.nextLine();
        return keyWord;
    }
    Search(){
        //keyWords=new ArrayList<String>();

		try {
            conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost/test?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
                        "root", ""
                    );		 

		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} 
    }
}