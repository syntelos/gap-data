
import com.google.appengine.api.datastore.Key;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;

/**
 * Test selection set.
 */
public class Selection {

    public static void main(String[] args){
        int errors = 0;
        LocalServiceTestHelper test = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
        try {
            test.setUp();

            gap.service.Selection selection = new gap.service.Selection(oso.data.Person.KIND);
            for (String uid: args){
                selection.add(uid);
            }

            for (String uid: args){
                Key uik = oso.data.Person.KIND.keyForId(uid);
                if (selection.containsNot(uik))
                    errors += 1;
            }
            System.err.printf("Errors %d%n",errors);
        }
        finally {
            test.tearDown();
        }
        System.exit(errors);
    }
}
