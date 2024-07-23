/**
 * SearchRace
 * レース検索に責任を持つクラス
 * @author Daiki Onda
 */

 import java.util.*;
 import java.sql.*;

public class SearchHorse extends Search{
    // 接続
    // characterEncoding=utf8 <- 文字エンコーディングとしてutf-8を使用
    // &useServerPrepStmts=true <- 静的プレースホルダを使用
    String sql1;
    String sql2 = " SELECT name,sex,birthday,LEAST(a.parentName, b.parentName) AS parentName1, GREATEST(a.parentName, b.parentName) AS parentName2 FROM horce,family as a,family as b WHERE name=a.childName AND name = b.childName AND a.parentName != b.parentName AND name = ? GROUP BY name, birthday, parentName1, parentName2;";
    String sql3 = " SELECT date,race.name,jockey.name,bracketNum,horceRank,time FROM horce,run,race,jockey WHERE horce.name=horceName AND jockey.ID=jockeyID AND race.ID = raceID  AND horce.Name=? ORDER BY date DESC;";
    String target;
    //List<String> keyWords;

    @Override
    void DoSearch(){
		try {
            //検索
            this.rs=DBInquory(this.sql1,"%"+InputKeyword(target)+"%");
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
                horceInfoDisplay(this.rs);
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
    
    List<String> InquoryResultDisplay(ResultSet rs,int i){
        //SELECT name,birthday FROM horce WHERE name LIKE ? ORDER BY birthday DESC;
        List<String> ID=new ArrayList<String>();
		try {
            while(rs.next()){
                String name = rs.getString("name");
                String birthday = rs.getString("birthday");
                ID.add(name);
                System.out.println(i+"." + "\t"+ birthday + "\t" + name);
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
    void horceInfoDisplay(ResultSet rs){
        // " SELECT name,birthday,LEAST(a.parentName, b.parentName) AS parentName1, GREATEST(a.parentName, b.parentName) AS parentName2 
		try {
            System.out.println("\n馬情報");
            System.out.println("馬名　　　　　　　\t性別\t誕生      \t親");
            while(rs.next()){
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                String birthday = rs.getString("birthday");
                String p1 = rs.getString("parentName1");
                String p2 = rs.getString("parentName2");
                System.out.println(PlusSpace(name,9)+"\t"+sex+"\t"+birthday+"\t"+p1+" --  "+ p2);
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }
    void EntryRaceInfoDisplay(ResultSet rs){
        //SELECT dete,race.name,jockey.name,bracketNum,horceRank,time 
		try {
            System.out.println("\n出走馬情報");                
            System.out.println("出走日      \tレース名　　　　　　　　　　　\t騎手　　　　　　　　\t枠番\t順位\tタイム");
            while(rs.next()){
                String date= rs.getString("date");
                String raceName = rs.getString("race.name");
                String jockeyName = rs.getString("jockey.name");
                int bracketNum = rs.getInt("bracketNum");
                int horceRank = rs.getInt("horceRank");
                float time = rs.getFloat("time");
                System.out.println(date+"\t"+PlusSpace(raceName,15)+"\t"+PlusSpace(jockeyName,10)+"\t"+bracketNum+"\t"+horceRank+"\t"+(int)(time/60)+":"+String.format("%02d"+"."+"%1d",(int)(time%60),(int)((time-(int)time)*10)));
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }

    SearchHorse(String sql,String target){
        super();
        this.target=target;
        this.sql1=sql;
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
}