package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.AdminCost;
import Model.Citizen;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AdminCostDAO {
	public static ArrayList<AdminCost> dbArrayList=new ArrayList<>();

	
	
	public static int insertAdminCostData(AdminCost admincost) {
		StringBuffer insertAdminCost=new StringBuffer();
		insertAdminCost.append("insert into admincost ");
		insertAdminCost.append("(info,dongho,dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay) ");
		insertAdminCost.append("values ");
		insertAdminCost.append("(?,?,?,?,?,?,?,?,?,?,?,?) ");

		
		Connection con= null;
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertAdminCost.toString());
			
			psmt.setString(1, AdminCostController.info);
			psmt.setString(2, AdminCostController.dongho);
			psmt.setString(3, admincost.getDong());
			psmt.setString(4, admincost.getHo());
			psmt.setString(5, admincost.getElectric());
			psmt.setString(6, admincost.getWater());
			psmt.setString(7, admincost.getGas());
			psmt.setString(8, admincost.getHeat());
			psmt.setString(9, admincost.getJoint());
			psmt.setString(10, admincost.getReceive());
			psmt.setString(11, admincost.getTatalCost());
			psmt.setString(12, admincost.getReciveDay());


			
			count=psmt.executeUpdate();
			if(count == 0) {
				AdminCostController.callAlert("삽입 쿼리실패 : 삽입 쿼리문실패 점검바람.");
				return count;
			}
			
		} catch (SQLException e) {
			AdminCostController.callAlert("미가입 주민 : 회원가입을 진행해주세요");
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}
		return count;
		
	}
	public static ArrayList<AdminCost> getAdminCostTotalData(){
		
		String selectAdminCost= "select dong,ho,electric,water,gas,heat,joint,receive,tatalCost,reciveDay from admincost ";
		
		Connection con= null;
		PreparedStatement psmt= null;
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectAdminCost);
			
			rs=psmt.executeQuery();
			if(rs == null) {
				AdminCostController.callAlert("select 실패 :  select 쿼리문실패 점검바람.");
				return null;
			}
			dbArrayList.clear();
			while(rs.next()) {
				AdminCost admincost=new AdminCost(
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
					
				dbArrayList.add(admincost);
			}
			
			
		} catch (SQLException e) {
			AdminCostController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}		
		return dbArrayList;
		
	}
	
	public static int deleteAdminCostData(String info) {
				String deleteAdminCost = "delete from admincost where info = ? ";
				
				Connection con= null;
				PreparedStatement psmt= null;
				int count =0;

				try {
					con=DBUtility.getConnection();
					psmt = con.prepareStatement(deleteAdminCost);
					psmt.setString(1, info);
					System.out.println(info);
				
					count=psmt.executeUpdate();
					if(count == 0) {
						AdminCostController.callAlert("delete 실패 :  delete 쿼리문실패 점검바람.");
						return count;
					}

				} catch (SQLException e) {
					AdminCostController.callAlert("delete 실패 : 데이타베이스 delete실패했어요.. 점검바람.");
				} finally {
					 try {
						if(psmt != null) {psmt.close();}
						if(con != null) { con.close(); }
					} catch (SQLException e) {
						AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
					}	
				}	
		return count;

	}
	public static int updateAdminCostData(AdminCost admincost) {
		String updateadmincost = "update admincost set "+"dong=?,ho=?,electric=?,water=?,gas=?,heat=?,joint=?,receive=?,tatalCost=?,reciveDay=?,dongho=? where info=?";

			
			Connection con= null;
			PreparedStatement psmt= null;
			int count=0;
			try {
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updateadmincost.toString());
				
				
				psmt.setString(1, admincost.getDong());
				psmt.setString(2, admincost.getHo());
				psmt.setString(3, admincost.getElectric());
				psmt.setString(4, admincost.getWater());
				psmt.setString(5, admincost.getGas());
				psmt.setString(6, admincost.getHeat());
				psmt.setString(7, admincost.getJoint());
				psmt.setString(8, admincost.getReceive());
				psmt.setString(9, admincost.getTatalCost());
				psmt.setString(10, admincost.getReciveDay());
				psmt.setString(11, AdminCostController.dongho);
				psmt.setString(12, AdminCostController.info);
				
				
				System.out.println(AdminCostController.info);
				
				count=psmt.executeUpdate();
				if(count == 0) {
					AdminCostController.callAlert("수정 쿼리실패 : 수정 쿼리문실패 점검바람.");
					return count;
				}
				
			} catch (SQLException e) {
				AdminCostController.callAlert("수정 실패 : 데이타베이스 수정실패했어요.. 점검바람.");
			} finally {
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
					e.printStackTrace();
				}	
			}
	return count;
}

	
	
	public static ArrayList<AdminCost> adminCostList = new ArrayList<AdminCost>();
public static ArrayList<AdminCost> getcostData(String id, String id2){
	
		String selectAdminCost= "select electric,water,gas,heat,joint,tatalcost,reciveday,receive from admincost where dongho=? ";
		
		Connection con= null;
		PreparedStatement psmt= null;
	
		
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectAdminCost);
			psmt.setString(1,id+id2);
			rs=psmt.executeQuery();
			if(rs == null) {
				AdminCostController.callAlert("select 실패 :  select 쿼리문실패 점검바람.");
				return null;
			}
			adminCostList.clear();
			while(rs.next()) {
				AdminCost admincost=new AdminCost(
						
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8)
						);
				adminCostList.add(admincost);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			AdminCostController.callAlert("삽입 실패333 : 데이타베이스 삽입실패했어요.. 점검바람.");
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
			}	
		}		
		return adminCostList;
		
	}



public static ArrayList<AdminCost> getchart(){

	String info=null;
	Connection con= null;
	PreparedStatement psmt= null;
	ResultSet rs = null;
	
	String sql= "select info "+ "from admincost where dongho=? ";
	try {
	con=DBUtility.getConnection();
	psmt=con.prepareStatement(sql);
	psmt.setString(1, AdminCostController.dongho);
			
			 rs=psmt.executeQuery();
			  
			  while(rs.next()) { 
				  info=rs.getString("info"); 
			  System.out.println(info); 
			  }
			 
	
			String sql2="select electric,water,gas,heat,joint,reciveDay from admincost where info=? ";
			psmt=con.prepareStatement(sql2);
			psmt.setString(1, AdminCostController.info);
			
		
			rs=psmt.executeQuery();
			while(rs.next()) {
					AdminCost admincost=new AdminCost(
							rs.getString("electric"), 
							rs.getString("water"), 
							rs.getString("gas"), 
							rs.getString("heat"), 
							rs.getString("joint"), 
							rs.getString("tatalCost"), 
							rs.getString("reciveDay"),
							rs.getString("receive")
							);
					
					adminCostList.add(admincost);
					
				
					
				}
			
		}catch (SQLException e) {
	
			} finally {
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
				AdminCostController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
				}	
			
		}		
		return adminCostList;
	
}


public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
