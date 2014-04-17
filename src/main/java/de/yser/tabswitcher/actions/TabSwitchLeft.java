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
package de.yser.tabswitcher.actions;

import de.yser.tabswitcher.TabSwitcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import org.openide.awt.*;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 1.0
 */
@ActionID(
	category = "Tools",
	id = "de.yser.tabswitcher.actions.TabSwitchLeft"
)
@ActionRegistration(
	iconBase = "de/yser/tabswitcher/actions/1leftarrow.png",
	displayName = "#CTL_TabSwitchLeft"
)
@ActionReferences({
	@ActionReference(path = "Menu/GoTo", position = 2900, separatorBefore = 2850),
	@ActionReference(path = "Shortcuts", name = "A-D-LEFT")
})
@Messages("CTL_TabSwitchLeft=TabSwitch Left")
public final class TabSwitchLeft implements ActionListener {

	Lookup.Result<TabSwitcher> result = Lookup.getDefault().lookupResult(TabSwitcher.class);
	private Collection<? extends TabSwitcher> services = result.allInstances();

	public TabSwitchLeft() {

		result.addLookupListener(new LookupListener() {
			@Override
			public void resultChanged(LookupEvent event) {
				services = result.allInstances();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		for (TabSwitcher service : services) {
			service.activateLeftTab();
		}
	}
}
