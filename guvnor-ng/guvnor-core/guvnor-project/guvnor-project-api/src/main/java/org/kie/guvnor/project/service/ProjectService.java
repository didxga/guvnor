/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.project.service;

import org.jboss.errai.bus.server.annotations.Remote;
import org.kie.guvnor.commons.data.workingset.WorkingSetSettings;
import org.kie.guvnor.project.model.POM;
import org.uberfire.backend.vfs.Path;

/**
 *
 */
@Remote
public interface ProjectService {

    WorkingSetSettings loadWorkingSetConfig( final Path project );

    /**
     * Given a Resource path resolve it to the containing Project Path. A Project path is the folder containing pom.xml
     * @param resource
     * @return Path to the folder containing the Project's pom.xml file or null if the resource was not in a Project
     */
    Path resolveProject( final Path resource );

    /**
     * Given a Resource path resolve it to the containing Package Path. A Package path is the folder containing the resource.
     * The folder must be within a valid Project structure and at least reference /src/main/resources or deeper.
     * @param resource
     * @return Path to the folder containing the resource file.
     */
    Path resolvePackage( final Path resource );

    /**
     * Creates a new project to the given path.
     * @param activePath
     * @param name
     * @return
     */
    public Path newProject( Path activePath,
                            final String name );

}
