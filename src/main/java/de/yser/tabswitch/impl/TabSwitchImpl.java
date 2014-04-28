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
package de.yser.tabswitch.impl;

import de.yser.tabswitch.TabSwitch;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.*;

/**
 *
 * Default implementation of {@link TabSwitch}.
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 1.0
 */
@ServiceProvider(service = TabSwitch.class)
public class TabSwitchImpl implements TabSwitch {

	private static final Logger LOG = Logger.getLogger(TabSwitchImpl.class.getName());

	private final Mode editorMode = WindowManager.getDefault().findMode("editor");

	public TabSwitchImpl() {
	}

	@Override
	public LinkedHashMap<Project, LinkedList<TopComponent>> getAllOpenedEditorTabs() {
		LinkedHashMap<Project, LinkedList<TopComponent>> projectEditorTabMap = new LinkedHashMap<>();

		// May activate TabSwitch for other WindowModes too.
		List<TopComponent> allEditorTabs = Arrays.asList(WindowManager.getDefault().getOpenedTopComponents(editorMode));

		for (TopComponent topComponent : allEditorTabs) {
			try {
				Project p = getProjectOfTab(topComponent);

				LinkedList<TopComponent> projectEditorTabs = projectEditorTabMap.get(p);
				if (projectEditorTabs == null) {
					projectEditorTabs = new LinkedList<>();
					projectEditorTabMap.put(p, projectEditorTabs);
				}
				projectEditorTabs.add(topComponent);
			} catch (NoRelatedProjectFoundException ex) {
				Exceptions.printStackTrace(ex);
			}
		}

		return projectEditorTabMap;
	}

	@Override
	public TopComponent getActiveTab() {
		return editorMode.getSelectedTopComponent();
	}

	@Override
	public TopComponent getNextTab() throws NoRelatedProjectFoundException {
		LinkedHashMap<Project, LinkedList<TopComponent>> openTabs = getAllOpenedEditorTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);
		LinkedList<TopComponent> projectTabGroup = openTabs.get(projectOfActiveComponent);

		TopComponent nextTab = null;
		int indexActive = projectTabGroup.indexOf(activeComponent);
		int indexRight = indexActive + 1;

		if (projectTabGroup.size() > 0) {
			if (indexRight % projectTabGroup.size() == 0) {
				nextTab = getFirstTabOfBelowProject();
			} else {
				nextTab = projectTabGroup.get(indexRight);
			}
		}

		return nextTab;
	}

	@Override
	public TopComponent getLeftTab() throws NoRelatedProjectFoundException {
		LinkedHashMap<Project, LinkedList<TopComponent>> openTabs = getAllOpenedEditorTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);
		LinkedList<TopComponent> projectTabGroup = openTabs.get(projectOfActiveComponent);

		TopComponent previousTab = null;
		int indexActive = projectTabGroup.indexOf(activeComponent);
		int indexLeft = indexActive - 1;

		if (projectTabGroup.size() > 0) {
			if (indexActive % projectTabGroup.size() == 0) {
				previousTab = getLastTabOfUpperProject();
			} else {
				previousTab = projectTabGroup.get(indexLeft);
			}
		}

		return previousTab;
	}

	@Override
	public void activateRightTab() {
		try {
			TopComponent rightTab = getNextTab();
			rightTab.requestActive();
		} catch (NoRelatedProjectFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	@Override
	public void activateLeftTab() {
		try {
			TopComponent leftTab = getLeftTab();
			leftTab.requestActive();
		} catch (NoRelatedProjectFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	/**
	 * Returns the last TopComponent tab of the upper project group from the
	 * currently focused TopComponent.
	 *
	 * @return
	 * @throws NoRelatedProjectFoundException
	 */
	private TopComponent getLastTabOfUpperProject() throws NoRelatedProjectFoundException {
		LinkedHashMap<Project, LinkedList<TopComponent>> openTabs = getAllOpenedEditorTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);

		if (openTabs.size() > 0) {
			Map.Entry<Project, LinkedList<TopComponent>> prevEntry = null;
			for (Map.Entry<Project, LinkedList<TopComponent>> entry : openTabs.entrySet()) {
				if (entry.getKey() != null) {
					if (entry.getKey().equals(projectOfActiveComponent)) {
						break;
					}
				} else if (projectOfActiveComponent == null) {
					break;
				}
				prevEntry = entry;
			}
			if (prevEntry != null) {
				// There is an upper project
				LinkedList<TopComponent> upperProjectTabs = prevEntry.getValue();
				if (upperProjectTabs.isEmpty()) {
					LOG.log(Level.WARNING, "TabGroup of upper project {0} is empty!", new Object[]{prevEntry.getKey().getProjectDirectory()});
				} else {
					return upperProjectTabs.getLast();
				}
			} else {
				// Theres no upper project -> select the last project
				LinkedList<TopComponent> lastProjectTabs = new LinkedList<>();
				for (Map.Entry<Project, LinkedList<TopComponent>> entry : openTabs.entrySet()) {
					lastProjectTabs = entry.getValue();
				}

				if (!lastProjectTabs.isEmpty()) {
					return lastProjectTabs.getLast();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the first TopComponent tab of the below project group from the
	 * currently focused TopComponent.
	 *
	 * @return TopComponent tab
	 * @throws NoRelatedProjectFoundException
	 */
	private TopComponent getFirstTabOfBelowProject() throws NoRelatedProjectFoundException {
		LinkedHashMap<Project, LinkedList<TopComponent>> openTabs = getAllOpenedEditorTabs();
		TopComponent activeComponent = getActiveTab();
		Project projectOfActiveComponent = getProjectOfTab(activeComponent);

		if (openTabs.size() > 0) {
			Map.Entry<Project, LinkedList<TopComponent>> belowProjectEntry = null;
			boolean foundActualProject = false;
			for (Map.Entry<Project, LinkedList<TopComponent>> entry : openTabs.entrySet()) {
				if (foundActualProject) {
					belowProjectEntry = entry;
					break;
				}
				if (entry.getKey() != null) {
					if (entry.getKey().equals(projectOfActiveComponent)) {
						foundActualProject = true;
					}
				} else if (projectOfActiveComponent == null) {
					foundActualProject = true;
				}
			}
			if (belowProjectEntry != null) {
				// There is an below project
				LinkedList<TopComponent> belowProjectTabs = belowProjectEntry.getValue();
				if (belowProjectTabs.isEmpty()) {
					LOG.log(Level.WARNING, "TabGroup of below project {0} is empty!", new Object[]{belowProjectEntry.getKey().getProjectDirectory()});
				} else {
					return belowProjectTabs.getFirst();
				}
			} else {
				// Theres no below project -> select the first project
				LinkedList<TopComponent> firstProjectTabs = openTabs.entrySet().iterator().next().getValue();
				if (!firstProjectTabs.isEmpty()) {
					return firstProjectTabs.getFirst();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the related project of the given TopComponent.
	 *
	 * @param topComponent
	 * @return Project related to the TopComponent
	 * @throws NoRelatedProjectFoundException if no project is related to the
	 * given TopComonent.
	 */
	private Project getProjectOfTab(TopComponent topComponent) throws NoRelatedProjectFoundException {
		Project p;
		if (topComponent == null) {
			throw new NoRelatedProjectFoundException("Can't determine project of null-TopComponent!");
		}

		p = topComponent.getLookup().lookup(Project.class);
		if (p == null) {
			DataObject dob = topComponent.getLookup().lookup(DataObject.class);
			if (dob != null) {
				FileObject fo = dob.getPrimaryFile();
				p = FileOwnerQuery.getOwner(fo);
			}
		}

		return p;
	}

}
