/**
 * EditChangeHeld
 * 開催要素の変更に責任を持つクラス
 * @author 
 */

 import java.util.*;
 import java.sql.*;

 public class ChangeHeld extends Change{
    String sql1="SELECT name,barnName,ID FROM held,trainer WHERE trainerID=ID AND name LIKE ? ORDER BY name;";
    String sql2="SELECT name,barnName,ID FROM held,trainer WHERE trainerID=ID AND trainerID = ? ORDER BY name;";
    String sql3="UPDATE held SET barnName = ? WHERE trainerID = ?;";
    String sql4="SELECT name FROM barn ORDER BY  FIELD(name, '美浦', '栗東', '地方', '外国');";
    @Override
    void DoChange(){
        try{
            //検索
            this.rs=DBInquory(this.sql1,"%"+InputKeyword("変更したい開催")+"%");
            if (!rs.isBeforeFirst()) {    
                System.out.println("No data"); 
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
                    this.rs=DBInquory(this.sql2,ID.get(key));
                    List<Integer> ID2=InquoryResultDisplay(this.rs,key+1);
                    System.out.println("この開催でよろしいでしょうか<y/n>");
                    do{
                        Scanner scanner = new Scanner(System.in);
                        confirmation=scanner.nextLine();
                    }while(!confirmation.matches("[yYnN]"));
                }while(!confirmation.matches("[yY]"));
                
                System.out.println("変更先を選択してください");
                List<String> barn=BarnDisplay();
                do{
                    key2 = Integer.parseInt(InputKeyword("番号"))-1;
                }while(key2>= barn.size()||key2<0);
                DBChange(sql3,barn.get(key2),String.valueOf(ID.get(key)));
                //表示
                this.rs=DBInquory(this.sql2,ID.get(key));
                List<Integer> ID2 = InquoryResultDisplay(this.rs,key+1);
            }
        


		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}

    }
    
    List<Integer> InquoryResultDisplay(ResultSet rs,int i){
        List<Integer> ID= new ArrayList<Integer>();
		try {
            while(rs.next()){
                String name = rs.getString("name");
                String barnName = rs.getString("barnName");
                ID.add(rs.getInt("ID"));
                System.out.println(i+"." + "\t"+ name + "\t" + barnName);
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
    List<String> BarnDisplay(){
        int i=1;
        List<String> barn=new ArrayList<String>();
		try {
            ResultSet rs = conn.prepareStatement(sql4).executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                barn.add(name);
                System.out.println("\t"+i+"." + "\t"+ name );
                i++;
            }
		} catch (SQLException se) {
			System.out.println("SQL Error: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
        return barn;

    }
    ChangeHeld(){
        super();
    }
}