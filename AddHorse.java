/**
 * EditAddHorse
 * 馬要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class AddHorse extends Add{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO horse(name, birthday, sex) VALUES (?, ?, ?) ";
    String sql2 = " SELECT ID, name FROM ?, WHERE name LIKE ? ";
    String sql3 = " SELECT ID, name FROM ?, WHERE ID = ? ";
    String sql4 = " INSERT INTO training(horseID, trainerID) VALUES (?, ?, ?) ";
    String sql5 = " INSERT INTO ownner(ID, name) VALUES (?, ?) ";

    @Override
    void DoAdd(){
        try{
            System.out.println("馬要素の入力: 名前(VARCHAR(9)),生年月日(DATE),性別(VARCHAR(2))");
            String horseData = scanner.nextLine();
            String splitHorseData[] = horseData.split(",");

            DBChange(sql1, splitHorseData);

            System.out.println("調教要素の入力:");

            String horseName = splitHorseData[0];

            String trainerID = Integer.toString(returnID("trainer", "調教師名"));

            DBChange(sql4, horseName, trainerID);

            System.out.println("馬主所有要素の入力:");

            String ownerID = Integer.toString(returnID("owner", "馬主名"));
            
            DBChange(sql5, horseName, ownerID);

            st.executeUpdate();
        } catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}

    int returnID(String tableName, String target){
        int reID = 0;
        try{
            //検索
            this.rs=DBInquory(this.sql2 ,tableName ,"%"+InputKeyword(target)+"%");
            if (!rs.isBeforeFirst() ) {    
                System.out.println("存在しません。新規登録してください。");
                return 0;
                //あとで
            } else{
                //結果
                List<Integer> ID=InquoryResultDisplay(this.rs,1);
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
                    this.rs=DBInquory(this.sql3 ,tableName ,String.valueOf(ID.get(key)));
                    List<Integer> ID2=InquoryResultDisplay(this.rs,key+1);
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


    List<Integer> InquoryResultDisplay(ResultSet rs,int i){
        List<Integer> ID=new ArrayList<Integer>();
		try {
            while(rs.next()){
                String name = rs.getString("");
                ID.add(rs.getInt("ID"));
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
    AddHorse(){
        super();
    }
}