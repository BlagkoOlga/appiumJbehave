package runner;

import io.appium.java_client.android.AndroidDriver;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.*;
import steps.SignUpSteps;

import java.text.SimpleDateFormat;

/**
 * Created by lio on 6/30/15.
 */
public class StoryEmbedder extends Embedder {

    private AndroidDriver driver;

    public StoryEmbedder(AndroidDriver driver) {
        this.driver = driver;
    }

    @Override
    public EmbedderControls embedderControls() {
        return new EmbedderControls().doIgnoreFailureInStories(true).doIgnoreFailureInView(true);
    }

    @Override
    public Configuration configuration() {
        Class<? extends StoryEmbedder> embedderClass = this.getClass();
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(embedderClass.getClassLoader()))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embedderClass))
                        .withDefaultFormats()
                        .withFormats(Format.CONSOLE, Format.TXT)
                        .withCrossReference(new CrossReference()))
                .useParameterConverters(new ParameterConverters()
                        .addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd"))))
                .useParameterControls(new ParameterControls().useNameDelimiterLeft("<").useNameDelimiterRight(">"))
                .useStepPatternParser(new RegexPrefixCapturingPatternParser(
                        "$"))
                .useStepMonitor(new SilentStepMonitor());
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(),
                new SignUpSteps(driver));
    }
}
