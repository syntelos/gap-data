
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * 
 */
public abstract class AbstractTest
    extends junit.framework.TestCase
{
    protected final static LocalServiceTestHelper D =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    protected final static LocalServiceTestHelper C =
        new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());




    public AbstractTest(){
        super();
    }


    @Override
    protected void setUp() {

        D.setUp();
        C.setUp();
    }
    @Override
    protected void tearDown() {
        try {
            D.tearDown();
            C.tearDown();
        }
        catch (Exception any){
        }
    }
}
