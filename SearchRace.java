/**
 * SearchRace
 * レース検索に責任を持つクラス
 * @author Daiki Onda
 */

 import java.util.*;
 import java.sql.*;

public class SearchRace extends Search{
    // 接続
    // characterEncoding=utf8 <- 文字エンコーディングとしてutf-8を使用
    // &useServerPrepStmts=true <- 静的プレースホルダを使用
    String sql1;
    String sql2 = "SELECT race.ID,race.name,raceRank,year,tracksName,raceNum,baba,dist,going,temperature,weather FROM race,held,tracks WHERE ID =raceID AND tracksName =tracks.name AND race.ID=?;";
    String sql3 = "SELECT horceRank,odds,bracketNum,horceName,weight,sex,age,time,jockey.name FROM race,run,horce,jockey WHERE race.ID =raceID AND jockey.ID=jockeyID AND horce.name=horceName AND raceID=? ORDER BY horceRank;";
    String target;
    //List<String> keyWords;

    @Override
    void DoSearch(){
        //検索
        this.rs=DBInquory(this.sql1,"%"+InputKeyword(target)+"%");
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
            System.out.println("このレースでよろしいでしょうか<y/n>");
            do{
                Scanner scanner = new Scanner(System.in);
                confirmation=scanner.nextLine();
            }while(!confirmation.matches("[yYnN]"));
        }while(!confirmation.matches("[yY]"));
        //レース情報表示
        this.rs=DBInquory(this.sql2,ID.get(key));
        RaceInfoDisplay(this.rs);
        this.rs=DBInquory(this.sql3,ID.get(key));
        EntryHorceInfoDisplay(this.rs);
        //出走馬情報表示
    }
    
    List<String> InquoryResultDisplay(ResultSet rs,int i){
        List<String> ID=new ArrayList<String>();
		try {
            while(rs.next()){
                int id = rs.getInt("year");
                String name = rs.getString("race.name");
                ID.add(rs.getString("race.ID"));
                System.out.println(i+"." + "\t"+ id + "\t" + name);
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
    void RaceInfoDisplay(ResultSet rs){
        //    String sql1 = "SELECT race.ID,race.name,raceRank,year,tracksName,raceNum,baba,dist,going,temperature,weather FROM race,held,tracks WHERE ID =raceID AND tracksName =tracks.name AND race.name=?;";
		try {
            System.out.println("\nレース情報");
            while(rs.next()){
                int year = rs.getInt("year");
                String name = rs.getString("race.name");
                String raceRank = rs.getString("raceRank");
                String tracksName = rs.getString("tracksName");
                int raceNum = rs.getInt("raceNum");
                String baba = rs.getString("baba");
                int dist = rs.getInt("dist");
                String going = rs.getString("going");
                float temperature = rs.getFloat("temperature");
                String weather = rs.getString("weather");
                System.out.println(year + "\t" + name + "\t"+raceRank+"\t"+tracksName+"\t"+raceNum+"R\t"+baba+"\t"+dist +"m\t"+temperature+"℃\t"+weather+"\t"+going);
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }
    void EntryHorceInfoDisplay(ResultSet rs){
        //SELECT horceRank,odds,bracketNum,horceName,weight,sex,age,time 
		try {
            System.out.println("\n出走馬情報");                
            System.out.println("順位"+"\t"+"人気"+"\t"+"枠番"+"\t"+"馬名　　　　　　"+"\t"+"騎手　　　　　　　　"+"\t"+"体重"+"\t"+"年齢"+"\t"+"タイム");
            while(rs.next()){
                int horceRank = rs.getInt("horceRank");
                int odds = rs.getInt("odds");
                int bracketNum = rs.getInt("bracketNum");
                String horceName = rs.getString("horceName");
                String jockeyName = rs.getString("jockey.name");
                int weight = rs.getInt("weight");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                float time = rs.getFloat("time");
                while(horceName.length()<9){
                    horceName+="　";
                }
                while(jockeyName.length()<10){
                    jockeyName+="　";
                }
                System.out.println(horceRank+"\t"+odds+"\t"+bracketNum+"\t"+horceName+"\t"+jockeyName+"\t"+weight+"\t"+sex+age+"\t"+(int)(time/60)+":"+String.format("%3.1f",time%60));
                //System.out.printf("%2d\t%-2d\t%-2d\t\t%9s\t\t%-3d\t%-1s%-2d\t\n",horceRank,odds,bracketNum,horceName,weight,sex,age);

            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
    }

    SearchRace(String sql,String target){
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