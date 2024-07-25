/**
 * EditDeletionOwner
 * 馬主要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

public class DeleteOwner extends Delete {
    public String ownerName;
    private int selectedOwnerID;
    private String selectedOwnerDetail;

    public DeleteOwner() {
    }

    @Override
    public void enterName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("削除したい馬主の名前を入力してください");
        ownerName = scanner.nextLine();
    }
 
    @Override
    public String getSQLtemplate() {
        return "DELETE FROM owner WHERE ID=?";
    }

    @Override
    public void setQuery(PreparedStatement st) throws SQLException {
        st.setInt(1, selectedOwnerID); // 選択したレースのIDを使用
    }

    // ownテーブルの ownerID を NULL にするメソッド
    @Override
    public void nullify(Connection conn) throws SQLException {
        String updateSQL1 = "UPDATE own SET ownerID = 0 WHERE ownerID = ?";
        try(PreparedStatement st1 = conn.prepareStatement(updateSQL1)) {
            st1.setInt(1, selectedOwnerID);
            int rowsAffected1 = st1.executeUpdate();
            System.out.println("ownテーブルを" + rowsAffected1 + "列更新しました");
            st1.close();
        }
    }

    // ownerテーブルから該当するレコードを表示するメソッド
    @Override
    public String showDetails(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM owner WHERE name = ?";
        try(PreparedStatement st = conn.prepareStatement(querySQL)) {
            st.setString(1, ownerName);
            try(ResultSet rs = st.executeQuery()) {
 
                List<Integer> ownerIDs = new ArrayList<>();
                List<String> owners = new ArrayList<>();
                System.out.println("選択した馬主の詳細:");
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("name");
                    String ownerDetail = "ID: " + id + ", 名前: " + name;
                    ownerIDs.add(id);
                    owners.add(ownerDetail);
                    System.out.println(owners.size() + ":  " + ownerDetail);
                }
                
                rs.close();
                st.close();
        
                if (owners.isEmpty()) {
                    System.out.println(ownerName + "という馬主は見つかりませんでした");
                    return null;
                }
        
                if (owners.size() == 1) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("これでよろしいですか？ (y/n) : ");
                    String answer = scanner.nextLine();
                    scanner.close();
                    if (answer.equals("y")) {
                        selectedOwnerID = ownerIDs.get(0);
                        return String.valueOf(selectedOwnerID);
                    } else {
                        System.out.println("削除をキャンセルしました");
                        return null;
                    }
                    
                } else {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("削除する馬主を選んでください (1-" + owners.size() + "): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    scanner.close();
                    if (choice < 1 || choice > owners.size()) {
                        System.out.println("無効な選択です。削除できませんでした。");
                        return null;
                    } else {
                        selectedOwnerDetail = owners.get(choice - 1);
                        System.out.println(choice + ":  " + selectedOwnerDetail);
                        System.out.print("これでよろしいですか？ (y/n) : ");
                        String answer = scanner.nextLine();
                        scanner.close();
                        if (answer.equals("y")) {
                            selectedOwnerID = ownerIDs.get(choice - 1);
                            return String.valueOf(selectedOwnerID);
                        } else {
                            System.out.println("削除はキャンセルされました");
                            return null;
                        }
                    }
                }
            }
        }
    }
}
