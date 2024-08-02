/**
 * AddRace
 * レース要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;
import java.io.*;

public class AddRace extends Add{
    
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO race(ID, year, date, raceNum, name, baba, raceRank, num, dist) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    void DoAdd(){
        try{
            System.out.println("レース要素の入力: csvのパス");
            // ID(INT), 開催年(YEAR), 開催日(DATE), レース番号(INT), レース名(VARCHAR(20)), 馬場(VARCHAR(3)), レースランク(VARCHAR(2)), 頭数(INT), 距離(INT)
            
            String filePass = scanner.nextLine();

            File file = new File(filePass);
            FileInputStream input = new FileInputStream(file);  
            InputStreamReader stream = new InputStreamReader(input,"SJIS");
            BufferedReader buffer = new BufferedReader(stream);
       
            String line;
            
            while ((line = buffer.readLine()) != null) {
 
                byte[] b = line.getBytes();
                line = new String(b, "UTF-8");
                String race[] = line.split(",");
                DBChange(sql1, race);
            }

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

    AddRace(){
        super();
    }
}
