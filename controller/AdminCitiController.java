package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import Model.Citizen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminCitiController implements Initializable{
	@FXML public TableView<Citizen> citizenTableView;
	@FXML public Button citiBtnEdit;
	@FXML public Button citiBtnClear;
	@FXML public Button citiBtnClose;
	@FXML public Button citiBtnSearch;
	@FXML public TextField citiTextSearch;

	public int selectCitizenindex;
	public Citizen selectCitizen;
	public Stage adcitistage;
	public static String dong=null;
	ObservableList<Citizen> citiListData=FXCollections.observableArrayList();
	ArrayList<Citizen> dbArrayList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//테이블 셋팅
		setCitizenTableView();
		//수정 버튼
		citiBtnEdit.setOnAction((e)-> { handlerBtnEditAction(); });
		//삭제버튼
		citiBtnClear.setOnAction((e)-> { handlerBtnClearAction(); });
		//찾기 버튼
		citiBtnSearch.setOnAction((e)->{ handlerBtnSearchAction();	});
		//닫기 버튼
		citiBtnClose.setOnAction((e)->{ handlerBtnCloseAction();	});
		
		
	}
	

	//테이블 셋팅
	private void setCitizenTableView() {
		TableColumn tcaddress=citizenTableView.getColumns().get(0);
		tcaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcaddress.setStyle("-fx-alignment: CENTER");	
		TableColumn tcaddress2=citizenTableView.getColumns().get(1);
		tcaddress2.setCellValueFactory(new PropertyValueFactory<>("address2"));
		tcaddress2.setStyle("-fx-alignment: CENTER");	
		TableColumn tcpassword=citizenTableView.getColumns().get(2);
		tcpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		tcpassword.setStyle("-fx-alignment: CENTER");	
		TableColumn tcname=citizenTableView.getColumns().get(3);
		tcname.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcname.setStyle("-fx-alignment: CENTER");	
		TableColumn tcgender=citizenTableView.getColumns().get(4);
		tcgender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		tcgender.setStyle("-fx-alignment: CENTER");	
		TableColumn tcbirth=citizenTableView.getColumns().get(5);
		tcbirth.setCellValueFactory(new PropertyValueFactory<>("birth"));
		tcbirth.setStyle("-fx-alignment: CENTER");	
		TableColumn tcemail=citizenTableView.getColumns().get(6);
		tcemail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcemail.setStyle("-fx-alignment: CENTER");	
		TableColumn tcphone=citizenTableView.getColumns().get(7);
		tcphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcphone.setStyle("-fx-alignment: CENTER");	
		
		dbArrayList=SignUpDAO.getCitizenDataTotalData();
		for(Citizen citizen:dbArrayList){
			citiListData.add(citizen);
		}		
		
		citizenTableView.setItems(citiListData);
		
		
	}
	//수정 버튼
		private void handlerBtnEditAction() {
			Parent root=null;
			  try {
			Stage czEditStage=new Stage(StageStyle.UTILITY);
			czEditStage.initModality(Modality.WINDOW_MODAL);
			czEditStage.initOwner(adcitistage);
			czEditStage.setTitle("세대관리 수정창");
			  FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/adminciti.fxml"));
		
				root = loader.load();
		
			  
			
			  //employee값을 설정
			  Button caBtnAdd=(Button)root.lookup("#caBtnAdd");
			  Button caBtnCancel=(Button)root.lookup("#caBtnCancel");
			  Button caBtnClear=(Button)root.lookup("#caBtnClear");
			  TextField caTextAddr=(TextField)root.lookup("#caTextAddr");
			  TextField caTextAddr1=(TextField)root.lookup("#caTextAddr1");
			  TextField caTextPass=(TextField)root.lookup("#caTextPass");
			  TextField caTextName=(TextField)root.lookup("#caTextName");
			  TextField caTextday=(TextField)root.lookup("#caTextday");
			  ComboBox<String> caTextGender=(ComboBox)root.lookup("#caTextGender");
			  TextField caTextNumber=(TextField)root.lookup("#caTextNumber");
			  TextField caTextEmail=(TextField)root.lookup("#caTextEmail");
			  
			    selectCitizen=citizenTableView.getSelectionModel().getSelectedItem();
		       selectCitizenindex=citizenTableView.getSelectionModel().getSelectedIndex();
		 
		      ObservableList<String>ListGender=FXCollections.observableArrayList();
			
			  //콤보박스
		      ListGender.addAll("남","여");
			  caTextGender.setItems(ListGender);
	
			 
//			  caTextAddr.setDisable(false);
//			  caTextAddr1.setDisable(false);
			  
			  
			  caTextAddr.setText(selectCitizen.getAddress());
			  caTextAddr1.setText(selectCitizen.getAddress2());
			  caTextPass.setText(selectCitizen.getPassword());
			  caTextName.setText(selectCitizen.getName());
			  caTextGender.getSelectionModel().select(selectCitizen.getGender());
			  caTextday.setText(selectCitizen.getBirth());
			  caTextEmail.setText(selectCitizen.getEmail());
			  caTextNumber.setText(selectCitizen.getPhone());
			  
			  
			  
			  //추가
			  caBtnAdd.setOnAction((e)->{
				  String addr=caTextAddr.getText();
				  String addr2=caTextAddr1.getText();
				  String pass=caTextPass.getText();
				  String name=caTextName.getText();
				  String Gender=caTextGender.getSelectionModel().getSelectedItem();
				  String day=caTextday.getText();
				  String emil=caTextEmail.getText();
				  String phone=caTextNumber.getText();
				  
				  Citizen citizen=new Citizen(addr, addr2, pass, name, Gender, day, emil, phone);
					 int count=SignUpDAO.updateCitizenData(citizen);
					 if(count!=0) {
						 citiListData.remove(selectCitizenindex);
						 citiListData.add(selectCitizenindex, citizen);
						 int arrayIndex=dbArrayList.indexOf(selectCitizen);
							dbArrayList.set(arrayIndex, citizen);

						}else {
							return;
						}
					 czEditStage.close();
			  });
			  //초기화 버튼
			  caBtnClear.setOnAction((e)->{
				  caTextAddr.clear();
				  caTextAddr1.clear();
				  caTextPass.clear();
				  caTextName.clear();
				  caTextGender.getSelectionModel().clearSelection();
				  caTextday.clear();
				  caTextEmail.clear();
				  caTextNumber.clear();
				  
					  });
			  
			  
			  
			  //취소버튼 클릭시 수정창 닫기
			  caBtnCancel.setOnMouseClicked((e)->{
				  czEditStage.close();	
				  
				 });
				  
			  Scene scene=new Scene(root);
			  czEditStage.setScene(scene);
			  czEditStage.show();
			  
			  
				} catch (IOException e) {
					callAlert("경고창:빈칸을 누르지 마세요");
					e.printStackTrace();
				}
			
			
				  
				

			  
			  
	}
	//삭제 버튼
	private void handlerBtnClearAction() {
		dong=citizenTableView.getSelectionModel().getSelectedItem().getDongho();
			int count=SignUpDAO.deleteCitizenData(dong);
			if(count!=0) {				
				//citiListData.remove(selectCitizenindex);
				//dbArrayList.remove(selectCitizen);
				dbArrayList.clear();
				citiListData.clear();
				dbArrayList=SignUpDAO.getCitizenDataTotalData();
				for(Citizen citizen:dbArrayList) {
					citiListData.add(citizen);
				}
				
			}else {
				return;
			}	
			
		}		
	
	//찾기 버튼
	private void handlerBtnSearchAction() {
		for(Citizen citizen : dbArrayList) {
			if(citiTextSearch.getText().trim().equals(citizen.getAddress()+citizen.getAddress2())) {
				citizenTableView.getSelectionModel().select(citizen);
				citizenTableView.scrollTo(citizen);
				
			}
		}
		
	}	
	

	//닫기 버튼
	private void handlerBtnCloseAction() {
		try {
			Stage adminliststage=new Stage();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
			Parent root= loader.load();
			
			AdminListController adminListController=loader.getController();
			adminListController.adminliststage=adminliststage;
			
			Scene scene=new Scene(root);
			adminliststage.setScene(scene);
			adcitistage.close();
			adminliststage.show();//새로운 창을 띄운다.
		} catch (IOException e1) {	}
	}
	
	
	
	
	 
	
	private void callAlert(String contentText) {
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("경고!");
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		
		alert.showAndWait();
	}
	
	
	
	
}
