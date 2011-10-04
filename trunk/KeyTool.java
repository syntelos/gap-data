
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyTool {

    public static void main(String[] argv){
        final int argc = argv.length;
        for (int argx = 0; argx < argc; argx++){
            String string = argv[argx];
            Key key = KeyFactory.stringToKey(string);
            System.out.println(key);
        }
    }
}
