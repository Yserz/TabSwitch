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
package de.yser.tabswitcher.actions;

import de.yser.tabswitcher.TabSwitcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;

/**
 * Action to switch to the right tab from the currently active tab. This Action
 * switches to the next or first project group if the end of the current one is
 * reached.
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 1.0
 */
@ActionID(
	category = "Tools",
	id = "de.yser.tabswitcher.actions.TabSwitchRight"
)
@ActionRegistration(
	iconBase = "de/yser/tabswitcher/actions/1rightarrow.png",
	displayName = "#CTL_TabSwitchRight"
)
@ActionReferences({
	@ActionReference(path = "Menu/GoTo", position = 3000),
	@ActionReference(path = "Shortcuts", name = "A-D-RIGHT")
})
@Messages("CTL_TabSwitchRight=TabSwitch Right")
public final class TabSwitchRight implements ActionListener {

	Lookup.Result<TabSwitcher> result = Lookup.getDefault().lookupResult(TabSwitcher.class);
	private Collection<? extends TabSwitcher> services = result.allInstances();

	public TabSwitchRight() {

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
			service.activateRightTab();
		}
	}
}
