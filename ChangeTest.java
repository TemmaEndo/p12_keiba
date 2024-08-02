/**
* ChangeHeld
* 開催の変更に責任を持つクラス
* @author Sugita Kaito
*/

import java.util.*;
import java.sql.*;

public class ChangeTest extends Change {
    String sql1 = "SELECT * FROM held WHERE raceID LIKE ? ORDER BY raceID;";
    String sql2 = "SELECT * from held WHERE raceID = ?;";
    String sql3 = "UPDATE held SET going = ?,temperature = ?,weather = ? WHERE raceID = ?";

    @Override
    void DoChange() {
        try {
            // 検索
            this.rs = DBInquory(this.sql1, "%" + InputKeyword("変更したいレース情報") + "%");
            if (!this.rs.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                // 結果
                List<String> ID = InquoryResultDisplay(this.rs, 1);
                // 確認
                String confirmation;
                int key = -1;
                do {
                    // 選択
                    do {
                        key = Integer.parseInt(InputKeyword("番号")) - 1;
                    } while (key >= ID.size() || key < 0);
                    // 表示
                    this.rs = DBInquory(this.sql2, ID.get(key));
                    List<String> ID2 = InquoryResultDisplay(this.rs, key + 1);
                    System.out.println("このレースでよろしいでしょうか<y/n>");
                    do {
                        Scanner scanner = new Scanner(System.in);
                        confirmation = scanner.nextLine();
                    } while (!confirmation.matches("[yYnN]"));
                } while (!confirmation.matches("[yY]"));

                // 馬場の状態を入力
                System.out.println("馬場の状態を入力してください(1字): ");
                Scanner scanner1 = new Scanner(System.in);
                String going = scanner1.nextLine();

                // 気温を入力
                System.out.println("気温を入力してください: ");
                Scanner scanner2 = new Scanner(System.in);
                String temp = scanner2.nextLine();

                // 天気を入力してください
                System.out.println("天気を入力してください(1字): ");
                Scanner scanner3 = new Scanner(System.in);
                String weather = scanner3.nextLine();

                // 馬場、気温、天気を更新
                DBChange(sql3, going, temp, weather, ID.get(key));

                // 表示
                this.rs = DBInquory(this.sql2, ID.get(key));
                List<String> ID4 = InquoryResultDisplay(this.rs, key + 1);
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " " + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    List<String> InquoryResultDisplay(ResultSet rs, int i) {
        List<String> ID = new ArrayList<String>();
        try {
            while (rs.next()) {
                String raceID = rs.getString("raceID");
                ID.add(raceID);
                String going = rs.getString("going");
                String temp = rs.getString("temperature");
                String weather = rs.getString("going");
                System.out.println(i + "." + "\t" + raceID + "\t" + going + "\t" + temp + "\t" + weather);
                i++;
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " " + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        return ID;
    }

    ChangeTest() {
        super();
    }
}
