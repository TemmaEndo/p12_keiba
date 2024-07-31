/**
 * EditChangeHorse
 * 馬要素の変更に責任を持つクラス
 * @author Kaito Sugita
 */

import java.util.*;
import java.sql.*;

public class ChangeHorse extends Change {
    String sql1 = "SELECT name FROM horse WHERE name LIKE ? ORDER BY name;"; // 馬を選択
    String sql2 = "SELECT name, birthday, sex FROM horse WHERE name = ?"; // 馬の詳細を取得
    String sql3 = "UPDATE horse SET birthday = ? WHERE name = ?"; // 誕生日を更新

    @Override
    void DoChange() {
        try {
            // 検索
            this.rs = DBInquory(this.sql1, "%" + InputKeyword("変更したい馬") + "%");
            if (!this.rs.isBeforeFirst()) {
                System.out.println("No data");
            } else {
                // 結果
                List<Integer> ID = InquoryResultDisplay(this.rs, 1);
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
                    List<Integer> ID2 = InquoryResultDisplay(this.rs, key + 1);
                    System.out.println("この馬でよろしいでしょうか<y/n>");
                    do {
                        Scanner scanner = new Scanner(System.in);
                        confirmation = scanner.nextLine();
                    } while (!confirmation.matches("[yYnN]"));
                } while (!confirmation.matches("[yY]"));

                // 変更先の誕生日を入力
                System.out.println("変更先の誕生日を入力してください (YYYY-MM-DD): ");
                Scanner scanner = new Scanner(System.in);
                String Birthday = scanner.nextLine();

                // 誕生日を更新
                DBChange(sql3, Birthday, String.valueOf(ID.get(key)));

                // 表示
                this.rs = DBInquory(this.sql2, ID.get(key));
                List<Integer> ID2 = InquoryResultDisplay(this.rs, key + 1);
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " " + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }

    List<Integer> InquoryResultDisplay(ResultSet rs, int i) {
        List<Integer> ID = new ArrayList<Integer>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String birthday = rs.getString("birthday");
                ID.add(rs.getInt("ID"));
                System.out.println(i + "." + "\t" + name + "\t" + birthday);
                i++;
            }
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " " + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        return ID;
    }

    ChangeHorse() {
        super();
    }
}
