package org.kie.guvnor.client.handlers;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.kie.commons.data.Pair;
import org.kie.guvnor.client.resources.i18n.Constants;
import org.kie.guvnor.client.resources.images.ImageResources;
import org.kie.guvnor.commons.ui.client.handlers.NewResourceHandler;
import org.kie.guvnor.commons.ui.client.resources.i18n.CommonConstants;
import org.kie.guvnor.project.service.ProjectService;
import org.kie.guvnor.projecteditor.client.places.ProjectEditorPlace;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.common.ErrorPopup;
import org.uberfire.client.context.WorkbenchContext;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.widgets.events.NotificationEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

/**
 * Handler for the creation of new Projects
 */
@ApplicationScoped
public class NewProjectHandler
        implements NewResourceHandler {

    private static String FILE_TYPE = null;

    @Inject
    protected WorkbenchContext context;

    @Inject
    private PlaceManager placeManager;

    @Inject
    private Event<NotificationEvent> notificationEvent;

    @Inject
    private Caller<ProjectService> projectServiceCaller;

    @Override
    public String getFileType() {
        return FILE_TYPE;
    }

    @Override
    public String getDescription() {
        return Constants.INSTANCE.newProjectDescription();
    }

    @Override
    public IsWidget getIcon() {
        return new Image(ImageResources.INSTANCE.newProjectIcon());
    }

    @Override
    public void create(final String projectName) {
        Path activePath = context.getActivePath();
        if (activePath != null) {
            projectServiceCaller.call(new RemoteCallback<Path>() {
                @Override
                public void callback(Path pathToPom) {

                    notificationEvent.fire(new NotificationEvent(CommonConstants.INSTANCE.ItemCreatedSuccessfully()));
                    placeManager.goTo(new ProjectEditorPlace(pathToPom));
                }
            }).newProject(activePath, projectName);
        } else {
            ErrorPopup.showMessage(Constants.INSTANCE.NoRepositorySelectedPleaseSelectARepository());
        }

    }

    @Override
    public List<Pair<String, IsWidget>> getExtensions() {
        return null;
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public boolean requiresProjectPath() {
        return false;
    }

}
