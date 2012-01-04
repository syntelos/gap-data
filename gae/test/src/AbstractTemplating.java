
import gap.data.List;
import gap.data.Store;
import gap.hapax.Template;
import gap.hapax.TemplateDataDictionary;
import gap.hapax.TemplateException;
import gap.hapax.TemplateName;
import gap.hapax.TemplateRenderer;
import gap.service.Templates;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import lxl.Map;

/**
 * Test template parsing and storage.
 */
public class AbstractTemplating
    extends AbstractTest
    implements TemplateDataDictionary
{


    private final Abstract td = new Abstract();

    private final Map<TemplateName,String> var = new Map();


    public AbstractTemplating(){
        super();
    }


    /**
     * Get/Create Template
     */
    protected TemplateRenderer load(String name)
        throws TemplateException
    {
        return Templates.GetTemplate(name);
    }
    /**
     * Lookup Existing Template
     */
    protected Template peek(String name)
        throws TemplateException
    {
        Template template = Template.ForLongName(name);
        // if (null == template)
        //     template = Template.ForLongName(name+".xtm");

        return template;
    }
    /**
     * Validate Existing Template Content
     */
    protected boolean validate(String name)
        throws TemplateException
    {
        Template template = this.peek(name);
        if (null != template){

            Text source = template.getTemplateSourceHapax();
            String target = template.toString();

            if (null != source && null != target && target.equals(source.getValue()))
                return true;
            else if (null == source)
                throw new TemplateException(String.format("Missing source for '%s'",name));
            else if (null == target)
                throw new TemplateException(String.format("Missing target for '%s'",name));
            else {
                String sourceString = source.getValue();
                if (null == sourceString)
                    throw new TemplateException(String.format("Missing source string for '%s'",name));
                else {
                    System.err.printf("Template Source '%s'%n",name);
                    System.err.println(sourceString);
                    System.err.printf("Template Target '%s'%n",name);
                    System.err.println(target);
                    return false;
                }
            }
        }
        else {
            throw new TemplateException(String.format("Missing template '%s'",name));
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
