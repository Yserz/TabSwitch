/*
 * Copyright (C) 2014 Michael Koppen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
