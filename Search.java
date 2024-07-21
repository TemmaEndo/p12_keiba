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

    //検索本体
    abstract void DoSearch();
    //SQL文とキーワードから検索を行い結果を返す
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
    //キーワードを取得
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