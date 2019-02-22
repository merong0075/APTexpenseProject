package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Citizen;
import Model.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class SignUpDAO {
	
	
	
	public static ArrayList<Citizen> dbArrayList=new ArrayList<>();
	
	public static int insertAdminCostData(Citizen citizen) {
		StringBuffer insertSignUp=new StringBuffer();
		insertSignUp.append("insert into citizen ");
		insertSignUp.append("(dongho,address,address2,password,password2,name,gender,birth,email,phone) ");
		insertSignUp.append("values ");
		insertSignUp.append("(?,?,?,?,?,?,?,?,?,?) ");

		
		Connection con= null;
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertSignUp.toString());
			
			psmt.setString(1, SignUpController.dongho);
			psmt.setString(2, citizen.getAddress());
			psmt.setString(3, citizen.getAddress2());
			psmt.setString(4, citizen.getPassword());
			psmt.setString(5, citizen.getPassword2());
			psmt.setString(6, citizen.getName());
			psmt.setString(7, citizen.getGender());
			psmt.setString(8, citizen.getBirth());
			psmt.setString(9, citizen.getEmail());
			psmt.setString(10, citizen.getPhone());

			count=psmt.executeUpdate();
			if(count == 0) {
				callAlert("오류 발생 : 다시다시");
				return count;
			}
		
		} catch (SQLException e) {
			callAlert("빈칸 발생 : 회원가입을 다시 진행하세요");
	
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}
		return count;
		
	}
	
	public static ArrayList<Citizen> getCitizenDataTotalData(){
		
		String selectCitizen= "select * from citizen ";
		
		Connection con= null;
		PreparedStatement psmt= null;
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectCitizen);
			
	
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select 실패 :  select 쿼리문실패 점검바람.");
				return null;
			}
			dbArrayList.clear();
			while(rs.next()) {
				Citizen citizen=new Citizen(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10));
				dbArrayList.add(citizen);
			}
			
			
		} catch (SQLException e) {
			callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}		
		return dbArrayList;
		
	}

	public static int updateCitizenData(Citizen citizen) {
		StringBuffer updatecitizen =new StringBuffer();
		updatecitizen.append("update citizen set ");
		updatecitizen.append("address=?,address2=?,password=?,name=?,gender=?,birth=?,email=?,phone=? where dongho=? ");

			Connection con= null;
			PreparedStatement psmt= null;
			int count=0;
			try {
				String Dongho=citizen.getAddress()+citizen.getAddress2();
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updatecitizen.toString());
				
				psmt.setString(1, citizen.getAddress());
				psmt.setString(2, citizen.getAddress2());
				psmt.setString(3, citizen.getPassword());
				psmt.setString(4, citizen.getName());
				psmt.setString(5, citizen.getGender());
				psmt.setString(6, citizen.getBirth());
				psmt.setString(7, citizen.getEmail());
				psmt.setString(8, citizen.getPhone());
				
				psmt.setString(9,Dongho);
				

				count=psmt.executeUpdate();
				if(count == 0) {
					
					callAlert("세대주소 변경 불가 : 주소는 변경하실 수 없습니다.");
				
					return count;
				}
				
			} catch (SQLException e) {
				callAlert("수정 실패 : 데이타베이스 수정실패했어요.. 점검바람.");
				e.printStackTrace();
			} finally {
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					callAlert("자원닫기실패 : psmt, con 닫기실패.");
				}	
			}
	return count;
}

	
	public static int deleteCitizenData(String dongho) {
		String deletecitizen = "delete from citizen where dongho = ? ";

		Connection con = null;
		PreparedStatement psmt = null;
		int count=0;
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deletecitizen);
			psmt.setString(1, dongho);

	System.out.println(dongho);
			count= psmt.executeUpdate(); 
			if (count==0) {
				callAlert("delete 실패 : delete쿼리문 실패. 점검바람");
				return count;
			}

		} catch (SQLException e) {
			callAlert("delete 실패 : 데이터베이스 delete 실패했습니다. 점검바람");
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				callAlert("자원닫기 실패 : psmt,con 닫기실패");
			}
		}	
	
		return count;
	}
public static void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("경고!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}


