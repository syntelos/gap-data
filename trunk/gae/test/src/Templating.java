
import gap.data.Store;
import gap.hapax.TemplateException;


/**
 * Test template parsing and storage.
 */
public class Templating
    extends AbstractTemplating
{
    private final static String IndexHtml = "index.html";

    private final static String DivLogonHtml = "div.logon.html";


    public Templating(){
        super();
    }


    public void testIndexHtml(){
        System.err.println("-- Test IndexHtml");
        Store.Test();
        try {
            this.load(IndexHtml);

            final boolean test = this.validate(IndexHtml);
            System.err.println("-- ");
            assertTrue(test);
        }
        catch (TemplateException exc){

            //exc.printStackTrace();
            System.err.println(exc);
            System.err.println("-- ");
            assertTrue(false);
        }
        finally {
            Store.Exit();
        }
    }

    public void testIndexDivLogonHtml(){
        System.err.println("-- Test IndexDivLogonHtml");
        Store.Test();
        try {
            this.load(IndexHtml);
            this.load(DivLogonHtml);

            final boolean test = this.validate(DivLogonHtml);
            System.err.println("-- ");
            assertTrue(test);
        }
        catch (TemplateException exc){

            //exc.printStackTrace();
            System.err.println(exc);
            System.err.println("-- ");
            assertTrue(false);
        }
        finally {
            Store.Exit();
        }
    }

    public void testDivLogonHtml(){
        System.err.println("-- Test DivLogonHtml");
        Store.Test();
        try {
            this.load(DivLogonHtml);

            final boolean test = this.validate(DivLogonHtml);
            System.err.println("-- ");
            assertTrue(test);
        }
        catch (TemplateException exc){

            //exc.printStackTrace();
            System.err.println(exc);
            System.err.println("-- ");
            assertTrue(false);
        }
        finally {
            Store.Exit();
        }
    }
}
