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
    ResultSet  DBInquory(String sql,String ...keyWord){
		try {
            st=conn.prepareStatement(sql);
            // 本来はここで入力された文字列が不正なものでないか検査した方が良い
            for(int i=0;i<keyWord.length;i++){
                if(keyWord[i].matches("^\\d{4}-\\d{2}-\\d{2}$")){
                    //年月日の場合
                    java.sql.Date date= java.sql.Date.valueOf(keyWord[i]);
                    st.setDate(i+1, date); // ここでSQLの ? の場所に値を埋め
                }else if(keyWord[i].matches("^[^\\d]+$")){
                    //文字列
                    st.setString(i+1, keyWord[i]); // ここでSQLの ? の場所に値を埋め込んでいる
                }else if(keyWord[i].matches("^\\d+\\.\\d+$")){
                    //float
                    float fl = Float.parseFloat(keyWord[i]);
                    st.setFloat(i+1, fl); // ここでSQLの ? の場所に値を
                }else if(keyWord[i].matches("^\\{12}$")){
                    //レースID
                    st.setString(i+1, keyWord[i]); // ここでSQLの ? の場所に値を
                }else if(keyWord[i].matches("^\\d{1,10}$")){
                    //レースID
                    int num = Integer.parseInt(keyWord[i]);
                    st.setInt(i+1, num); // ここでSQLの ? の場所に値を埋め
                }else{
                    return null;
                } 
            }
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
    ResultSet  DBInquory(String sql,int ...keyWord){
		try {
            st=conn.prepareStatement(sql);

            // 本来はここで入力された文字列が不正なものでないか検査した方が良い
            for(int i=0;i<keyWord.length;i++){
                st.setInt(i+1, keyWord[i]); // ここでSQLの ? の場所に値を埋め込んでいる
            }
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
    String PlusSpace(String str,int num){
        while(str.length()<num) str+="　";
        return str;
    }
    Search(){
        //keyWords=new ArrayList<String>();

		try {
            conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost/keiba?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
                        "root", ""
                    );		 

		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} 
    }
}
