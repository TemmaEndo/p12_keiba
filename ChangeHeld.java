import java.util.*;
import java.sql.*;

public class ChangeHeld extends Change {
    String sql1 = "SELECT * FROM horse WHERE name LIKE ? ORDER BY name;";
    String sql2 = "SELECT * FROM horse WHERE name = ?";
    String sql3 = "UPDATE horse SET birthday = ? WHERE name = ?";

    @Override
    void DoChange() {
        try {
            // 検索
            this.rs = DBInquory(this.sql1, "%" + InputKeyword("変更したい馬") + "%");
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
                DBChange(sql3, Birthday, ID.get(key).toString());

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
                String name = rs.getString("name");
                ID.add(name);
                String birthday = rs.getString("birthday");
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

    ChangeHeld() {
        super();
    }
}
