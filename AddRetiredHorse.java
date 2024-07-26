/**
 * EditAddRetiredHorse
 * 引退馬要素の追加に責任を持つクラス
 * @author Temma Endo
 */

import java.util.*;
import java.sql.*;

public class AddRetiredHorse extends Add{
    private Scanner scanner = new Scanner(System.in);

    String sql1 = " INSERT INTO family(name, date) VALUES (?, ?) ";

    @Override
    void DoAdd(){
        try{
            System.out.println("引退馬要素の入力: 親の名前(VARCHAR(9)),引退日(DATE)");
            String RetiredHorseData = scanner.nextLine();
            String splitRetiredHorseData[] = RetiredHorseData.split(",");

            DBChange(sql1, splitRetiredHorseData);

        } catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}

    protected void finalize() throws Throwable {
        // 終了処理
        rs.close();
        st.close();
        conn.close();
    }

    AddRetiredHorse(){
        super();
    }
}