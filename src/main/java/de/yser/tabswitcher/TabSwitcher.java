/*
 * Copyright 2014 Michael Koppen.
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
package de.yser.tabswitcher;

import de.yser.tabswitcher.impl.NoRelatedProjectFoundException;
import java.util.LinkedList;
import java.util.Map;
import org.netbeans.api.project.Project;
import org.openide.windows.TopComponent;

/**
 * The TabSwitcher looks for windows in the 'editor' WindowMode.
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 1.0
 */
public interface TabSwitcher {

	/**
	 * Returns all open tabs in the 'editor' WindowMode.
	 *
	 * @return list of TopComponents.
	 */
	public Map<Project, LinkedList<TopComponent>> getAllOpenedEditorTabs();

	/**
	 * Returns the currently active tab in 'editor' WindowMode.
	 *
	 * @return TopComponent of active window.
	 */
	public TopComponent getActiveTab();

	/**
	 * Returns the right tab from the currently active tab in 'editor'
	 * WindowMode.
	 *
	 * @return TopComponent of the tab
	 * @throws de.yser.tabswitcher.impl.NoRelatedProjectFoundException
	 */
	public TopComponent getNextTab() throws NoRelatedProjectFoundException;

	/**
	 * Returns the left tab from the currently active tab in 'editor'
	 * WindowMode.
	 *
	 * @return TopComponent of the tab
	 * @throws de.yser.tabswitcher.impl.NoRelatedProjectFoundException
	 */
	public TopComponent getLeftTab() throws NoRelatedProjectFoundException;

	/**
	 * Returns the first tab of the upper project from the currently active
	 * project tab in the 'editor' WindowMode.
	 *
	 * @return TopComponent of the tab
	 * @throws NoRelatedProjectFoundException
	 */
	public TopComponent getLastTabOfUpperProject() throws NoRelatedProjectFoundException;

	/**
	 * Returns the first tab of the below project from the currently active
	 * project tab in the 'editor' WindowMode.
	 *
	 * @return TopComponent of the tab
	 * @throws NoRelatedProjectFoundException
	 */
	public TopComponent getFirstTabOfBelowProject() throws NoRelatedProjectFoundException;

	/**
	 * Sets the focus on the first tab of the upper project of the currently
	 * active project tab in 'editor' WindowMode.
	 */
	public void activateFirstTabOfUpperProject();

	/**
	 * Sets the focus on the first tab of the below project of the currently
	 * active project tab in 'editor' WindowMode.
	 */
	public void activateLastTabOfBelowProject();

	/**
	 * Sets the focus on the right tab of the currently active tab in 'editor'
	 * WindowMode.
	 */
	public void activateRightTab();

	/**
	 * Sets the focus on the left tab of the currently active tab in 'editor'
	 * WindowMode.
	 */
	public void activateLeftTab();

}
