/**
 * SearchHorseAge
 * 馬の年齢検索に責任を持つクラス
 * @author Daiki Onda
 */



import java.util.*;
import java.sql.*;
public class SearchHorseAge extends SearchHorse{ 

    @Override
    void DoSearch(){
		try {
            //検索
            String keyWord=InputKeyword(target);
            this.rs=DBInquory(this.sql1,"%"+keyWord);
            if (!rs.isBeforeFirst() ) {    
                System.out.println("No data"); 
            } else{
                //結果
                List<String> ID=InquoryResultDisplay(this.rs,1);
                //確認
                String confirmation;
                int key=-1;
                do{
                    //選択
                    do{
                        key = Integer.parseInt(InputKeyword("番号"))-1;
                    }while(key>= ID.size()||key<0);
                    //表示
                    this.rs=DBInquory(this.sql2,ID.get(key));
                    List<String> ID2=InquoryResultDisplay(this.rs,key+1);
                    System.out.println("この馬でよろしいでしょうか<y/n>");
                    do{
                        Scanner scanner = new Scanner(System.in);
                        confirmation=scanner.nextLine();
                    }while(!confirmation.matches("[yYnN]"));
                }while(!confirmation.matches("[yY]"));
                //馬情報表示
                this.rs=DBInquory(this.sql2,ID.get(key));
                horseInfoDisplay(this.rs);
                //出走情報表示
                this.rs=DBInquory(this.sql3,ID.get(key));
                EntryRaceInfoDisplay(this.rs);
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }

    SearchHorseAge(){
        super("SELECT name,birthday FROM horse WHERE DATE_FORMAT(birthday,'%Y') LIKE ? ORDER BY birthday DESC","馬の誕生年");
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
    //void horseInfoDisplay(ResultSet rs){
}
