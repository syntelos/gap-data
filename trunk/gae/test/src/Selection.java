
import com.google.appengine.api.datastore.Key;

/**
 * Test selection set.
 */
public class Selection
    extends AbstractTest
{
    private final static String[] args = {
      "ax@by.cz", "bx@cy.dz", "cx@dy.ez", "dx@ey.fz", "ex@fy.gz", "fx@gy.hz", "gx@hy.iz", "hx@iy.jz", "ix@jy.kz", "jx@ky.lz"
    };


    public Selection(){
        super();
    }

    public void testSelection(){
        int errors = 0;

        gap.service.Selection selection = new gap.service.Selection(oso.data.Person.KIND);
        for (String uid: args){
            selection.add(uid);
        }

        for (String uid: args){
            Key uik = oso.data.Person.KIND.keyIdFor(uid);
            if (selection.containsNot(uik))
                errors += 1;
        }
        System.err.printf("Test Selection Errors %d%n",errors);
        assertTrue(0 == errors);
    }
}
