/**
 * EditAddJockey
 * 騎手要素の追加に責任を持つクラス
 * @author Temma Endo
 */

 import java.util.*;
 import java.sql.*;
 
 public class AddJockey extends Add{
     private Scanner scanner = new Scanner(System.in);
 
     String sql1 = " INSERT INTO jockey(ID, name) SELECT MAX(ID)+1, ? FROM jockey ";
 
     @Override
     void DoAdd(){
         try{
             System.out.println("騎手要素の入力: 名前(VARCHAR(9))");
             String JockeyName = scanner.nextLine();
             
             DBChange(sql1, JockeyName);
 
         }catch (Exception e) {
             System.out.println("Error: " + e.toString() + e.getMessage());
         }
     }
 
     protected void finalize() throws Throwable {
         // 終了処理
         rs.close();
         st.close();
         conn.close();
     }
     AddJockey(){
         super();
     }
 }