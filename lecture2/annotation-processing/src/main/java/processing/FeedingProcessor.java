package processing;

import annotations.Feeding;
import annotations.Feedings;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Annotation processor using the {@link Feeding} annotation.
 *
 * @author Created by tom on 03.10.2016.
 */
@SupportedAnnotationTypes({"annotations.Feeding", "annotations.Feedings"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FeedingProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // first for single Feeding annotations
        for (Element element : roundEnv.getElementsAnnotatedWith(Feeding.class)) {
            Feeding feedingAnnotation = element.getAnnotation(Feeding.class);
            String data = "at " + feedingAnnotation.feedingTime() + " give your animal " +
                    feedingAnnotation.foodType() + "; ";
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, data, element);
        }

        // now for repeated Feeding annotations
        for (Element element : roundEnv.getElementsAnnotatedWith(Feedings.class)) {
            String data = "";
            for (Feeding feedingAnnotation : element.getAnnotationsByType(Feeding.class)) {
                data += "at " + feedingAnnotation.feedingTime() + " give your animal " +
                        feedingAnnotation.foodType() + "; ";
            }
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, data, element);
        }

        return true;
    }
}
