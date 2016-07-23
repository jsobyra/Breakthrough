package Model;

/**
 * Created by KUBA on 2016-07-23.
 */
public class Helper {
    private static Helper instance = new Helper();
    private String value;

    private Helper(){

    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Helper getInstance(){
        return instance;
    }


}
