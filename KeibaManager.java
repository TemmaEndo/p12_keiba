/**
 * KeibaManager
 * 競馬管理アプリケーションの本体となるクラス
 * @author Temma Endo
 */

 import java.util.*;
 import java.sql.*;

 public class KeibaManagerManager {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Search s11 = new SearchRaceName();
		Search s12 = new SearchRaceYear();

		Search s21 = new SearchHorseAge();
		Search s22 = new SearchHorseName();

		Search s3 = new SearchJockey();

		Edit e11 = new EditAddHorse();
		Edit e12 = new EditAddRace();
		Edit e13 = new EditAddTrainer();
		Edit e14 = new EditAddretired();
		Edit e15 = new EditAddJockey();

		Edit e201 = new EditDeletionAffiation();
		Edit e202 = new EditDeletionFamily();
		Edit e203 = new EditDeletionHeld();
		Edit e204 = new EditDeletionHorse();
		Edit e205 = new EditDeletionJockey();
		Edit e206 = new EditDeletionOwner();
		Edit e207 = new EditDeletionOwnership();
		Edit e208 = new EditDeletionPartification();
		Edit e209 = new EditDeletionRace();
		Edit e210 = new EditDeletionRetiredHorse();
		Edit e211 = new EditDeletionTrainer();
		Edit e212 = new EditDeletionTraining();

		Edit e301 = new EditChangeAffiation();
		Edit e302 = new EditChangeFamily();
		Edit e303 = new EditChangeHeld();
		Edit e304 = new EditChangeHorse();
		Edit e305 = new EditChangeJockey();
		Edit e306 = new EditChangeOwner();
		Edit e307 = new EditChangeOwnership();
		Edit e308 = new EditChangePartification();
		Edit e309 = new EditChangeRace();
		Edit e310 = new EditChangeRetiredHorse();
		Edit e311 = new EditChangeTrainer();
		Edit e312 = new EditChangeTraining();


		System.out.println("=== 競馬データベース ===");
		System.out.println("メニューを選択してください: ");
		System.out.println("1: 検索");
		System.out.println("2: 編集");
		System.out.println("番号を入力してください: ");

		String lineSorE = scanner.nextLine();
		switch(lineSorE) {
			case "1":
				System.out.println("検索する項目を選択してください: ");
				System.out.println("1: レース");
				System.out.println("2: 馬");
				System.out.println("3: 騎手");
				System.out.println("番号を入力してください: ");
				
				String lineS = scanner.nextLine();
				switch(lineS){
					case "1":
						System.out.println("使用する項目を選択してください: ");
						System.out.println("1: 名前");
						System.out.println("2: 開催年");
						System.out.println("番号を入力してください: ");

						String lineSR = scanner.nextLine();
						
						switch(lineSR){
							case "1":
								s11.BDsearch();
								break;
							case "2":
								s12.BDsearch();
								break;
							default:
								System.out.println("Incorrect number");
						}
					case "2":
						System.out.println("使用する項目を選択してください: ");
						System.out.println("1: 年齢");
						System.out.println("2: 名前");
						System.out.println("番号を入力してください: ");

						String lineSH = scanner.nextLine();
						
						switch(lineSH){
							case "1":
								s21.BDsearch();
								break;
							case "2":
								s22.BDsearch();
								break;
							default:
								System.out.println("Incorrect number");
						}
					case "3":
						s3.DBSearch();
						break;
					default:
						System.out.println("Incorrect number");
				}
			case "2":
				System.out.println("メニューを選択してください: ");
				System.out.println("1: 追加");
				System.out.println("2: 削除");
				System.out.println("3: 変更");
				System.out.println("番号を入力してください: ");

				String lineE = scanner.nextLine();
				switch(lineE){
					case "1":
						System.out.println("追加するデータの項目を選択してください: ");
						System.out.println("1: 馬");
						System.out.println("2: レース");
						System.out.println("3: 調教師");
						System.out.println("4: 引退馬");
						System.out.println("5: 騎手");
						System.out.println("番号を入力してください: ");

						String lineAdd = scanner.nextLine();
						switch(lineAdd) {
							case "1":
								e11.DBEdit();
								break;
							case "2":
								e12.DBEdit();
								break;
							case "3":
								e13.DBEdit();
								break;
							case "4":
								e14.DBEdit();
								break;
							case "5":
								e15.DBEdit();
								break;
							default:
								System.out.println("Incorrect number");
						}
					case "2":
						System.out.println("削除するデータの項目を選択してください: ");
						System.out.println("01: 所属");
						System.out.println("02: 親");
						System.out.println("03: 開催");
						System.out.println("04: 馬");
						System.out.println("05: 騎手");
						System.out.println("06: 馬主");
						System.out.println("07: 所有");
						System.out.println("08: 出走");
						System.out.println("09: レース");
						System.out.println("10: 引退馬");
						System.out.println("11: 調教師");
						System.out.println("12: 調教");
						System.out.println("番号を入力してください: ");
						
						String linedel = scanner.nextLine();
						switch(linedel) {
							case "01":
								e201.DBEdit();
								break;
							case "02":
								e202.DBEdit();
								break;
							case "03":
								e203.DBEdit();
								break;
							case "04":
								e204.DBEdit();
								break;
							case "05":
								e205.DBEdit();
								break;
							case "06":
								e206.DBEdit();
								break;
							case "07":
								e207.DBEdit();
								break;
							case "08":
								e208.DBEdit();
								break;
							case "09":
								e209.DBEdit();
								break;
							case "10":
								e210.DBEdit();
								break;
							case "11":
								e211.DBEdit();
								break;
							case "12":
								e212.DBEdit();
								break;
							
							default:
								System.out.println("Incorrect number");
						}
					case "3":
						System.out.println("変更するデータの項目を選択してください: ");
						System.out.println("01: 所属");
						System.out.println("02: 親");
						System.out.println("03: 開催");
						System.out.println("04: 馬");
						System.out.println("05: 騎手");
						System.out.println("06: 馬主");
						System.out.println("07: 所有");
						System.out.println("08: 出走");
						System.out.println("09: レース");
						System.out.println("10: 引退馬");
						System.out.println("11: 調教師");
						System.out.println("12: 調教");
						System.out.println("番号を入力してください: ");
						
						String linecha = scanner.nextLine();
						switch(linecha) {
							case "01":
								e301.DBEdit();
								break;
							case "02":
								e302.DBEdit();
								break;
							case "03":
								e303.DBEdit();
								break;
							case "04":
								e304.DBEdit();
								break;
							case "05":
								e305.DBEdit();
								break;
							case "06":
								e306.DBEdit();
								break;
							case "07":
								e307.DBEdit();
								break;
							case "08":
								e308.DBEdit();
								break;
							case "09":
								e309.DBEdit();
								break;
							case "10":
								e310.DBEdit();
								break;
							case "11":
								e311.DBEdit();
								break;
							case "12":
								e312.DBEdit();
								break;
							
							default:
								System.out.println("Incorrect number");
						}
					default:
						System.out.println("Incorrect number");
				}
			default:
				System.out.println("Incorrect number");
		}
	}
}