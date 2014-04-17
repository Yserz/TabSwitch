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
package de.yser.tabswitcher;

import java.util.List;
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
	public List<TopComponent> getAllOpenedTabs();

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
	 * @return TopComponent
	 */
	public TopComponent getRightTab();

	/**
	 * Returns the left tab from the currently active tab in 'editor'
	 * WindowMode.
	 *
	 * @return TopComponent
	 */
	public TopComponent getLeftTab();

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
