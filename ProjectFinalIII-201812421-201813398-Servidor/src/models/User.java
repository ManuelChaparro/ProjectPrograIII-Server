package models;

public class User {

	private String nameUser;
	private String codeUser;
	private String password;

	public User(String nameUser, String codeUser, String password) {
		this.nameUser = nameUser;
		this.codeUser = codeUser;
		this.password = password;
	}

	public User(String codeUser) {
		this.codeUser = codeUser;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getCodeUser() {
		return codeUser;
	}

	public void setCodeUser(String code) {
		this.codeUser = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "User [nameUser=" + nameUser + ", code=" + codeUser + ", password=" + password + "]";
	}
	
	
	public static void main(String[] args) {
		String palabra = "sql";
		String texto = "lenguaje sql";
		boolean resultado = texto.contains(palabra);

		if(resultado){
		    System.out.println("palabra encontrada");
		}else{
		    System.out.println("palabra no encontrada");
		}
	}
}