package self.camel.demo;

public enum SqlColumnType {
	
	VARCHAR("VARCHAR"), 
	INT("INT"); 
	
    private String type;
 
    SqlColumnType(String i_Type) {
        this.type = i_Type;
    }
 
    public String getType() {
        return this.type;
    }

}
