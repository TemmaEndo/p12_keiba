/**
 * AddTrainer
 * 調教師要素の追加に責任を持つクラス
 * @author Temma Endo
 */

 import java.util.*;
 import java.sql.*;
 
 public class AddTrainer extends Add{
     private Scanner scanner = new Scanner(System.in);
 
     String sql1 = " INSERT INTO trainer(ID, name) SELECT MAX(ID)+1 , ?  FROM trainer";
     String sql2 = " INSERT INTO affiliation(trainerID, barnName) SELECT MAX(trainer.ID) , ? FROM trainer, affiliation ";
     String sql3 = " SELECT name FROM barn WHERE name LIKE ?";
     String sql4 = " SELECT name FROM barn WHERE name = ?";
 
     @Override
     void DoAdd(){
         try{
             System.out.println("調教師要素の入力: 名前(VARCHAR(9))");
             String trainerName = scanner.nextLine();
 
             DBChange(sql1, trainerName);
 
             System.out.println("厩舎所属要素の入力:");
 
             DBChange(sql2, returnName("厩舎名"));
 
         }catch (Exception e) {
             System.out.println("Error: " + e.toString() + e.getMessage());
         }
     }
 
     String returnName(String target){
         String reID = "";
         try{
             //検索
             this.rs=DBInquory(this.sql3,"%"+InputKeyword(target)+"%");
             if (!rs.isBeforeFirst() ) {    
                 System.out.println("存在しません。新規登録してください。");
                 return "";
                 //あとで
             } else{
                 //結果
                 List<String> ID=InquoryResultDisplay(this.rs,1);
                 //確認
                 String confirmation;
                 int key=-1;
                 int key2=-1;
                 do{
                     //選択
                     do{
                         key = Integer.parseInt(InputKeyword("番号"))-1;
                     }while(key>= ID.size()||key<0);
                     //表示
                     this.rs=DBInquory(this.sql4,ID.get(key));
                     List<String> ID2=InquoryResultDisplay(this.rs,key+1);
                     System.out.println("この" + target + "でよろしいでしょうか<y/n>");
                     do{
                         Scanner scanner = new Scanner(System.in);
                         confirmation=scanner.nextLine();
                     }while(!confirmation.matches("[yYnN]"));
                 }while(!confirmation.matches("[yY]"));
 
                 reID = ID.get(key);
                 return reID;
             }
         
         } catch (SQLException se) {
             System.out.println("SQL Error: " + se.toString() + " "
                 + se.getErrorCode() + " " + se.getSQLState());
         } catch (Exception e) {
             System.out.println("Error: " + e.toString() + e.getMessage());
         }
         return reID;
     }
 
     List<String> InquoryResultDisplay(ResultSet rs,int i){
         List<String> ID=new ArrayList<String>();
         try {
             while(rs.next()){
                 String name = rs.getString("name");
                 ID.add(name);
                 System.out.println(i+"." + "\t"+ name);
                 i++;
             }
         } catch (SQLException se) {
             System.out.println("SQL Error: " + se.toString() + " "
                 + se.getErrorCode() + " " + se.getSQLState());
         } catch (Exception e) {
             System.out.println("Error: " + e.toString() + e.getMessage());
         }
         return ID;
     }
      
     protected void finalize() throws Throwable {
         // 終了処理
         rs.close();
         st.close();
         conn.close();
     }
     AddTrainer(){
         super();
     }
 } 