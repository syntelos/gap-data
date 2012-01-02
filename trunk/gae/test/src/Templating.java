
import gap.data.List;
import gap.data.Store;
import gap.hapax.Template;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateException;
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;
import gap.service.Templates;

import com.google.appengine.api.datastore.Key;

import lxl.Map;

/**
 * Test template parsing and storage.
 */
public class Templating
    extends AbstractTest
    implements TemplateDataDictionary
{
    private final static String Result = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n  <head>\n    <title>Test</title>\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"/top.css\" />\n    <link rel=\"icon\" href=\"/favicon.ico\" />\n  </head>\n  <body>\n    <div class=\"logon\"><span class=\"logon\"><b><a href=\"\"></a></b></span>\n    </div>\n    <div id=\"first\">\n\n      <h1>Test</h1>\n    </div>\n  </body>\n</html>\n";


    private final Abstract td = new Abstract();

    private final Map<TemplateName,String> var = new Map();


    public Templating(){
        super();
    }


    public void testTemplating(){
        Store.Test();
        try {
            TemplateRenderer template = Templates.GetTemplate("index.html");

            String product = template.renderToString(this);

            if (Result.equals(product))
                assertTrue(true);
            else {
                System.err.println(product);
                assertTrue(false);
            }
        }
        catch (TemplateException exc){
        }
        finally {
            Store.Exit();
        }
    }


    public void renderComplete(){

        this.td.renderComplete();
    }
    public TemplateDataDictionary clone(){

        return this.td.clone();
    }
    public TemplateDataDictionary clone(TemplateDataDictionary parent){

        return this.td.clone(parent);
    }
    public TemplateDataDictionary getParent(){

        return this.td.getParent();
    }
    public void setParent(TemplateDataDictionary p){

        this.td.setParent(p);
    }
    public boolean hasVariable(TemplateName name){

        return this.var.containsKey(name);
    }
    public String getVariable(TemplateName name){

        return this.var.get(name);
    }
    public void setVariable(TemplateName name, String value){

        this.var.put(name,value);
    }
    public List.Short<TemplateDataDictionary> getSection(TemplateName name){

        return this.td.getSection(name);
    }
    public List.Short<TemplateDataDictionary> showSection(TemplateName name){

        return this.td.showSection(name);
    }
    public TemplateDataDictionary addSection(TemplateName name){

        return this.td.addSection(name);
    }
    public TemplateDataDictionary addSection(TemplateName name, TemplateDataDictionary section){

        return this.td.addSection(name,section);
    }

}
