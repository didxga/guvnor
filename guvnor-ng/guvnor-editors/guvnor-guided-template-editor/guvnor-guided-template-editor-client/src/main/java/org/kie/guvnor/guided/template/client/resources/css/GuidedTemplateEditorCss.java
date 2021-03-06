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
package org.kie.guvnor.guided.template.client.resources.css;

import com.google.gwt.resources.client.CssResource;

/**
 * CSS for Guided Template Editor
 */
public interface GuidedTemplateEditorCss
        extends
        CssResource {

    int rowHeight();

    int rowHeaderHeight();

    int rowHeaderSplitterHeight();

    int rowHeaderSorterHeight();

    int sidebarWidth();

    int borderWidth();

    int borderWidthThick();

    String cellTable();

    String cellTableEvenRow();

    String cellTableOddRow();

    String cellTableCell();

    String cellTableCellSelected();

    String cellTableCellMultipleValues();

    String cellTableCellOtherwise();

    String cellTableCellDiv();

    String cellTableGroupDiv();

    String cellTableTextDiv();

    String headerRowBottom();

    String headerRowIntermediate();

    String headerText();

    String headerSplitter();

    String headerResizer();

    String selectorSpacer();

    String selectorSpacerOuterDiv();

    String selectorSpacerInnerDiv();

    String selectorCell();

    String metaColumn();

    String conditionColumn();

    String actionColumn();

    String templateColumn();

    String patternSectionHeader();

    String patternConditionSectionHeader();

    String columnLabelHidden();

    String contextMenu();

    String contextMenuItem();

    String contextMenuItemEnabled();

    String contextMenuItemDisabled();

}
