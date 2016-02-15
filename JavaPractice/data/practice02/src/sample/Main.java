package sample;

import sample.beans.Employee;

public class Main {

	public static void main(String[] args) {
		Employee e1 = new Employee();
		e1.setName("山田 太郎");
		e1.setAddress("東京都・・・");
		e1.setContractType("03");
		System.out.println(e1);
	}

}
