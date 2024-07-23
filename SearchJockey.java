/**
 * SearchJockey
 * 騎手名年検索に責任を持つクラス
 * @author DAIKI ONDA
 */

 import java.util.*;
 import java.sql.*;

public class SearchJockey extends Search{
    // 接続
    // characterEncoding=utf8 <- 文字エンコーディングとしてutf-8を使用
    // &useServerPrepStmts=true <- 静的プレースホルダを使用
    String sql1 = "SELECT * FROM jockey WHERE name LIKE ? ORDER BY ID;";
    String sql2 = "SELECT * FROM jockey WHERE ID=?;" ;
    String sql3 = " SELECT date,race.name,horceName,leg,horceRank From jockey,run,race WHERE jockey.ID=run.jockeyID AND race.ID=raceID AND jockey.ID=? ORDER BY date DESC;";
    String target;
    //List<Integer> keyWords;

    @Override
    void DoSearch(){
		try {
            //検索
            this.rs=DBInquory(this.sql1,"%"+InputKeyword("騎手名")+"%");
            if (!rs.isBeforeFirst()) {    
                System.out.println("No data"); 
            } else{
                //結果
                List<Integer> ID=InquoryResultDisplay(this.rs,1);
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
                    List<Integer> ID2=InquoryResultDisplay(this.rs,key+1);
                    System.out.println("この騎手でよろしいでしょうか<y/n>");
                    do{
                        Scanner scanner = new Scanner(System.in);
                        confirmation=scanner.nextLine();
                    }while(!confirmation.matches("[yYnN]"));
                }while(!confirmation.matches("[yY]"));
                //レース情報表示
                this.rs=DBInquory(this.sql3,ID.get(key));
                EntryRaceDisplay(this.rs);
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }
    
    List<Integer> InquoryResultDisplay(ResultSet rs,int i){
        List<Integer> ID=new ArrayList<Integer>();
		try {
            while(rs.next()){
                String name = rs.getString("name");
                ID.add(rs.getInt("ID"));
                System.out.println(i+"." +  "\t" + name);
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
    void EntryRaceDisplay(ResultSet rs){
        //" SELECT date,race.name,horceName,leg,horceRank From jockey,run,race WHERE jockey.ID=run.jockeyID AND race.ID=raceID AND jockey.I
		try {
            System.out.println("\nレース戦績");                             
            System.out.println("出走日    \tレース名　　　　　　　　　　　\t馬名　　　　　　　\t脚質\t順位");
            while(rs.next()){
                String date = rs.getString("date");
                String raceName = rs.getString("race.name");
                String horceName = rs.getString("horceName");
                String leg = rs.getString("leg");
                int horceRank = rs.getInt("horceRank");
                System.out.println(date+"\t"+PlusSpace(raceName,15)+"\t"+PlusSpace(horceName,9)+"\t"+leg+"\t"+horceRank);
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }

    SearchJockey(){
        super();
    }

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }
}