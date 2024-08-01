/**
* ChangeRace
* レースの変更に責任を持つクラス
* @author Sugita Kaito
*/

import java.util.*;
import java.sql.*;

public class ChangeRace extends Change {
    String sql1 = "SELECT * FROM race WHERE name LIKE ? ORDER BY name;";
    String sql2 = "SELECT * FROM race WHERE ID = ?";
    String sql3 = "UPDATE race SET date = ? WHERE ID = ?";

    @Override
    void DoChange() {
        try {
            // 検索
            this.rs = DBInquory(this.sql1, "%" + InputKeyword("変更したいレース") + "%");
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

                // 日付を入力
                System.out.println("正しい日付を入力してください (YYYY-MM-DD): ");
                Scanner scanner = new Scanner(System.in);
                String date = scanner.nextLine();

                // 日付を更新
                DBChange(sql3, date, ID.get(key).toString());

                // 表示
                this.rs = DBInquory(this.sql2, ID.get(key));
                List<String> ID2 = InquoryResultDisplay(this.rs, key + 1);
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
                String raceID = rs.getString("ID");
                ID.add(raceID);
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String date = rs.getString("date");
                System.out.println(i + "." + "\t" + raceID + "\t" + year + "\t" + date + "\t" + name);
                i++;
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " " + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        return ID;
    }

    ChangeRace() {
        super();
    }
}
