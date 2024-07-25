/**
 * EditDeletionHorse
 * 馬要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class DeleteHorse extends Delete {
    public String horseName;

    public DeleteHorse() {
    }

    @Override
    public void enterName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("削除したい馬の名前を入力してください");
        horseName = scanner.nextLine();
    }
 
    @Override
    public String getSQLtemplate() {
        return "DELETE FROM horse WHERE name=?";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        st.setString(1,horseName);
    }

    // training,own,runテーブルの horseName を NULL にするメソッド
    @Override
    public void nullify(Connection conn) throws SQLException {
        String updateSQL1 = "UPDATE training SET horseName = '' WHERE horseName = ?";
        String updateSQL2 = "UPDATE own SET horseName = '' WHERE horseName = ?";
        String updateSQL3 = "UPDATE run SET horseName = '' WHERE horseName = ?";
        PreparedStatement st1 = conn.prepareStatement(updateSQL1);
        PreparedStatement st2 = conn.prepareStatement(updateSQL2);
        PreparedStatement st3 = conn.prepareStatement(updateSQL3);
        st1.setString(1, horseName);
        st2.setString(1, horseName);
        st3.setString(1, horseName);
        int rowsAffected1 = st1.executeUpdate();
        int rowsAffected2 = st2.executeUpdate();
        int rowsAffected3 = st3.executeUpdate();
        System.out.println("trainingテーブルを" + rowsAffected1 + "列更新しました");
        System.out.println("ownテーブルを" + rowsAffected2 + "列更新しました");
        System.out.println("runテーブルを" + rowsAffected3 + "列更新しました");
        st1.close();
        st2.close();
        st3.close();
    }

    // horse テーブルから該当するレコードを表示するメソッド
    @Override
    public String showDetails(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM horse WHERE name = ?";
        PreparedStatement st = conn.prepareStatement(querySQL);
        st.setString(1, horseName);
        ResultSet rs = st.executeQuery();

        List<String> horses = new ArrayList<>();
        System.out.println("選択した馬の詳細:");
        while (rs.next()) {
            String name = rs.getString("name");
            Date birthday = rs.getDate("birthday");
            String sex = rs.getString("sex");

            // birthdayがnullの場合に'0000-00-00'を設定
            String birthdayStr = (birthday != null) ? birthday.toString():"0000-00-00";
            String horseDetail = "名前: " + name + ", 生年月日: " + birthdayStr + ", 性別: " + sex;
            horses.add(horseDetail);
            System.out.println(horses.size() + ":  " + horseDetail);
        }
        
        rs.close();
        st.close();

        if (horses.isEmpty()) {
            System.out.println(horseName + "という馬は見つかりませんでした");
            return null;
        }

        if (horses.size() == 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("これでよろしいですか？ (y/n):");
            String answer = scanner.nextLine();
            scanner.close();

            if (answer.equals("y")) {
                return horses.get(0).split(",")[0].split(": ")[1];
            } else {
                System.out.println("削除をキャンセルしました");
                return null;
            }

        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("削除するレースを選んでください: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline
            scanner.close();

            if (choice < 1 || choice > horses.size()) {
                System.out.println("無効な選択です。削除できませんでした。");
                return null;
            } else {
                return horses.get(choice - 1).split(",")[0].split(": ")[1];
            }
        }
    }
}
