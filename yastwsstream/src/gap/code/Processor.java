package gap.code;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import java.util.Set;

/**
 * 
 */
@SupportedOptions({})
@SupportedAnnotationTypes({"gap.data.Persistent"})
@SupportedSourceVersion(SourceVersion.RELEASE_5)
public class Processor
    extends javax.annotation.processing.AbstractProcessor
{

    public Processor(){
        super();
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        for (TypeElement type : annotations){
            if (type.getQualifiedName().contentEquals("gap.data.Persistent"))
                return true;
        }
        return false;
    }

}
