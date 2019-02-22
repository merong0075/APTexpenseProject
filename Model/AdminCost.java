package Model;

public class AdminCost {
	private String dong;
	private String ho;
	private String electric;
	private String water;
	private String gas;
	private String heat;
	private String joint;
	private String receive;
	private String tatalCost;
	private String reciveDay;
	private String info;
	private String dongho;
	
	
	

	public AdminCost(String electric, String water, String gas, String heat, String joint, String reciveDay) {
		super();
		this.electric = electric;
		this.water = water;
		this.gas = gas;
		this.heat = heat;
		this.joint = joint;
		this.reciveDay = reciveDay;
	}
	public AdminCost(String electric, String water, String gas, String heat, String joint, String tatalCost,
			String reciveDay, String receive) {
		super();
		this.electric = electric;
		this.water = water;
		this.gas = gas;
		this.heat = heat;
		this.joint = joint;
		this.tatalCost = tatalCost;
		this.reciveDay = reciveDay;
		this.receive=receive;
		
	}
	public AdminCost(String dong, String ho, String electric, String water, String gas, String heat, String joint,
			String receive, String tatalCost, String reciveDay) {
		super();
		this.dong = dong;
		this.ho = ho;
		this.electric = electric;
		this.water = water;
		this.gas = gas;
		this.heat = heat;
		this.joint = joint;
		this.receive = receive;
		this.tatalCost = tatalCost;
		this.reciveDay = reciveDay;
	}
	public AdminCost(String info,String dongho, String dong, String ho, String electric, String water, String gas, String heat, String joint,
			String receive, String tatalCost, String reciveDay) {
	
		this.dong = dong;
		this.ho = ho;
		this.electric = electric;
		this.water = water;
		this.gas = gas;
		this.heat = heat;
		this.joint = joint;
		this.receive = receive;
		this.tatalCost = tatalCost;
		this.reciveDay = reciveDay;
		this.info=info;
		this.dongho=dongho;
	}
	
	
	
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getHo() {
		return ho;
	}
	public void setHo(String ho) {
		this.ho = ho;
	}
	public String getElectric() {
		return electric;
	}
	public void setElectric(String electric) {
		this.electric = electric;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getHeat() {
		return heat;
	}
	public void setHeat(String heat) {
		this.heat = heat;
	}
	public String getJoint() {
		return joint;
	}
	public void setJoint(String joint) {
		this.joint = joint;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getTatalCost() {
		return tatalCost;
	}
	public void setTatalCost(String tatalCost) {
		this.tatalCost = tatalCost;
	}
	public String getReciveDay() {
		return reciveDay;
	}
	public void setReciveDay(String reciveDay) {
		this.reciveDay = reciveDay;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDongho() {
		return dongho;
	}
	public void setDongho(String dongho) {
		this.dongho = dongho;
	}
	
	
}
