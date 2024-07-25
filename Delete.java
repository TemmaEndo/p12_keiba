/**
 * EditDeletion
 * 要素の削除に責任を持つクラス
 * @author Shota Kitagawa
 */

 import java.sql.*;

public abstract class Delete {
    public abstract String getSQLtemplate();
    public abstract void setQuery(PreparedStatement st) throws SQLException;
    public abstract void nullify(Connection conn) throws SQLException;
    public abstract String showDetails(Connection conn) throws SQLException;    
    public abstract enterName();
    public void preQuery() { } // 必要に応じてオーバーライド. 前処理がいらないならそのまま使う
 
    public final void DoDel() {
        try {
            enterName();
            // (必要なら)前処理 
            preQuery();

            // 接続
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/keiba?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true&zeroDateTimeBehavior=convertToNull", 
                "root", ""
            );		 
            

            String selectedDetails = showDetails(conn);

            if (selectedDetails == null) {
                conn.close();
                return;
            }

            // SQLの前処理. getSQLtemplate() で個別のSQLを作る
            PreparedStatement st = conn.prepareStatement(getSQLtemplate());
             
            // SQLに値を埋め込む. その他、個別に必要な処理もここで行う
            setQuery(st);
 
            // SQLを実行して結果を得る.
            int rowsAffected = st.executeUpdate();

            System.out.println("Deleted " + rowsAffected + " row(s).");
 
            
            nullify(conn);
            // 終了処理
            st.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("SQL Error: " + se.toString() + " "
                + se.getErrorCode() + " " + se.getSQLState());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
    }
}
