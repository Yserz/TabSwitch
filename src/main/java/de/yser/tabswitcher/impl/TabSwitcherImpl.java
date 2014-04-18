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
package de.yser.tabswitcher.impl;

import de.yser.tabswitcher.TabSwitcher;
import java.util.*;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.*;

/**
 *
 * Default implementation of {@link TabSwitcher}.
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 1.0
 */
@ServiceProvider(service = TabSwitcher.class)
public class TabSwitcherImpl implements TabSwitcher {

	private final Mode editorMode = WindowManager.getDefault().findMode("editor");

	public TabSwitcherImpl() {
	}

	@Override
	public Map<Project, List<TopComponent>> getAllOpenedTabs() {
		Map<Project, List<TopComponent>> projectEditorTabMap = new HashMap<>();
		List<TopComponent> allEditorTabs = Arrays.asList(WindowManager.getDefault().getOpenedTopComponents(editorMode));

		for (TopComponent topComponent : allEditorTabs) {
			Project p = getProjectOfTab(topComponent);

			List<TopComponent> projectEditorTabs = projectEditorTabMap.get(p);
			if (projectEditorTabs == null) {
				projectEditorTabs = new ArrayList<>();
				projectEditorTabMap.put(p, projectEditorTabs);
			}
			projectEditorTabs.add(topComponent);
		}

		// May activate TabSwitch for other WindowModes too.
		return projectEditorTabMap;
	}

	@Override
	public TopComponent getActiveTab() {
		return editorMode.getSelectedTopComponent();
	}

	@Override
	public TopComponent getRightTab() {
		Map<Project, List<TopComponent>> openTabs = getAllOpenedTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);
		List<TopComponent> projectTabGroup = openTabs.get(projectOfActiveComponent);

		int indexActive = projectTabGroup.indexOf(activeComponent);
		int indexRight = indexActive + 1;

		if (projectTabGroup.size() > 0) {
			if (indexRight % projectTabGroup.size() == 0) {
				// get first tab
				indexRight = 0;
			}
		} else {
			// NO TABS ARE OPEN
			return null;
		}

		return projectTabGroup.get(indexRight);
	}

	@Override
	public TopComponent getLeftTab() {
		Map<Project, List<TopComponent>> openTabs = getAllOpenedTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);
		List<TopComponent> projectTabGroup = openTabs.get(projectOfActiveComponent);

		int indexActive = projectTabGroup.indexOf(activeComponent);
		int indexLeft = indexActive - 1;

		if (projectTabGroup.size() > 0) {
			if (indexActive % projectTabGroup.size() == 0) {
				// get last tab
				indexLeft = projectTabGroup.size() - 1;
			}
		} else {
			// NO TABS ARE OPEN
			return null;
		}

		return projectTabGroup.get(indexLeft);
	}

	@Override
	public void activateRightTab() {
		TopComponent rightTab = getRightTab();
		if (rightTab != null) {
			rightTab.requestActive();
		}

	}

	@Override
	public void activateLeftTab() {
		TopComponent leftTab = getLeftTab();
		if (leftTab != null) {
			leftTab.requestActive();
		}
	}

	private Project getProjectOfTab(TopComponent topComponent) {
		Project p = topComponent.getLookup().lookup(Project.class);
		if (p == null) {
			DataObject dob = topComponent.getLookup().lookup(DataObject.class);
			if (dob != null) {
				FileObject fo = dob.getPrimaryFile();
				p = FileOwnerQuery.getOwner(fo);
			} else {
				System.out.println("Could not find DataObject!");
			}
		} else {
			System.out.println("Could not find Project!");
		}
		return p;
	}

}
