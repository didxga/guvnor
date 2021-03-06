/*
 * Copyright 2005 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.metadata.client.widget;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.ioc.client.container.IOC;
import org.kie.commons.java.nio.base.version.VersionRecord;
import org.kie.guvnor.metadata.client.resources.ImageResources;
import org.kie.guvnor.metadata.client.resources.Images;
import org.kie.guvnor.metadata.client.resources.i18n.MetadataConstants;
import org.kie.guvnor.services.metadata.model.Metadata;
import org.kie.guvnor.services.version.VersionService;
import org.uberfire.backend.vfs.Path;
import org.uberfire.backend.vfs.PathFactory;
import org.uberfire.client.common.ClickableLabel;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.mvp.PathPlaceRequest;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.*;
import static org.kie.commons.validation.PortablePreconditions.*;

/**
 * This widget shows a list of versions for packages or assets
 */
public class VersionBrowser extends Composite {

    private final Metadata  metadata;
    private       Image     refresh;
    private       FlexTable layout;

    private PlaceManager placeManager = null;

    public VersionBrowser( final Metadata metadata ) {
        this.metadata = checkNotNull( "metadata", metadata );

        final HorizontalPanel wrapper = new HorizontalPanel();

        final ClickHandler clickHandler = new ClickHandler() {
            public void onClick( ClickEvent event ) {
                showBusyIcon();
                MessageBuilder.createCall( new RemoteCallback<List<VersionRecord>>() {
                    @Override
                    public void callback( final List<VersionRecord> response ) {
                        loadHistoryData( response );
                    }
                }, VersionService.class ).getVersion( metadata.getPath() );
            }
        };

        layout = new FlexTable();
        final ClickableLabel vh = new ClickableLabel( MetadataConstants.INSTANCE.VersionHistory1(), clickHandler );
        layout.setWidget( 0, 0, vh );
        layout.getCellFormatter().setStyleName( 0, 0, "metadata-Widget" );

        final FlexCellFormatter formatter = layout.getFlexCellFormatter();
        formatter.setHorizontalAlignment( 0, 0, ALIGN_LEFT );

        refresh = Images.INSTANCE.Refresh();

        refresh.addClickHandler( clickHandler );

        layout.setWidget( 0, 1, refresh );
        formatter.setHorizontalAlignment( 0, 1, ALIGN_RIGHT );

        wrapper.setStyleName( "version-browser-Border" );

        wrapper.add( layout );

        layout.setWidth( "100%" );
        wrapper.setWidth( "100%" );

        initWidget( wrapper );

        loadHistoryData( metadata.getVersion() );
    }

    /**
     * Actually load the history data, as demanded.
     */
    protected void loadHistoryData( final List<VersionRecord> versions ) {

        if ( versions == null || versions.size() == 0 ) {
            layout.setWidget( 1, 0, new Label( MetadataConstants.INSTANCE.NoHistory() ) );
            showStaticIcon();
            return;
        }

        final ListBox history = new ListBox( true );

        for ( int i = 0; i < versions.size(); i++ ) {
            final VersionRecord version = versions.get( i );

            final String s = MetadataConstants.INSTANCE.property0ModifiedOn1By23( String.valueOf( i + 1 ),
                                                                          version.date().toString(),
                                                                          version.author(),
                                                                          version.comment() );
            history.addItem( s, version.uri() );
        }

        layout.setWidget( 1, 0, history );
        FlexCellFormatter formatter = layout.getFlexCellFormatter();

        formatter.setColSpan( 1, 0, 2 );

        final Button open = new Button( MetadataConstants.INSTANCE.View() );

        open.addClickHandler( new ClickHandler() {

            public void onClick( ClickEvent event ) {
                final Path path = PathFactory.newPath( metadata.getPath().getFileSystem(), metadata.getPath().getFileName(), history.getValue( history.getSelectedIndex() ) );
                placeManager().goTo( new PathPlaceRequest( path ).addParameter( "readOnly", "yes" ) );
            }

        } );

        layout.setWidget( 2, 0, open );
        formatter.setColSpan( 2, 1, 3 );
        formatter.setHorizontalAlignment( 2, 1, ALIGN_CENTER );

        showStaticIcon();
    }

    private void showBusyIcon() {
        refresh.setResource( ImageResources.INSTANCE.searching() );
    }

    private void showStaticIcon() {
        refresh.setResource( ImageResources.INSTANCE.refresh() );
    }

    private PlaceManager placeManager() {
        if ( placeManager == null ) {
            placeManager = IOC.getBeanManager().lookupBean( PlaceManager.class ).getInstance();
        }
        return placeManager;
    }

}
