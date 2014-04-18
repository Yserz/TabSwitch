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
import java.util.Arrays;
import java.util.List;
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
	public List<TopComponent> getAllOpenedTabs() {
		// May activate TabSwitch for other WindowModes too.
		return Arrays.asList(WindowManager.getDefault().getOpenedTopComponents(editorMode));
	}

	@Override
	public TopComponent getActiveTab() {
		return editorMode.getSelectedTopComponent();
	}

	@Override
	public TopComponent getRightTab() {
		List<TopComponent> openTabs = getAllOpenedTabs();
		int indexActive = openTabs.indexOf(getActiveTab());
		int indexRight = indexActive + 1;

		if (indexRight >= openTabs.size()) {
			if (openTabs.size() > 0) {
				// select the first tab
				indexRight = 0;
			} else {
				// NO TABS ARE OPEN
				return null;
			}
		}

		return openTabs.get(indexRight);
	}

	@Override
	public TopComponent getLeftTab() {
		List<TopComponent> openTabs = getAllOpenedTabs();
		int indexActive = openTabs.indexOf(getActiveTab());
		int indexLeft = indexActive - 1;

		if (indexLeft < 0) {
			if (openTabs.size() > 0) {
				// select the last tab
				indexLeft = openTabs.size() - 1;
			} else {
				// NO TABS ARE OPEN
				return null;
			}
		}

		return openTabs.get(indexLeft);
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

}
