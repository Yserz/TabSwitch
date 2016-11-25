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
package de.yser.tabswitch.actions;

import de.yser.tabswitch.TabSwitch;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import org.openide.awt.*;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;

/**
 * Action to switch to the left tab from the currently active tab. This Action switches to the previous or last project group if
 * the end of the current one is reached.
 *
 * @author Michael Koppen (michael.koppen at googlemail.com)
 * @since 0.1
 */
@ActionID(
        category = "Tools",
        id = "de.yser.tabswitch.actions.TabSwitchLeft"
)
@ActionRegistration(
        iconBase = "de/yser/tabswitch/actions/arrowleft.png",
        displayName = "#CTL_TabSwitchLeft"
)
@ActionReferences({
    @ActionReference(path = "Menu/GoTo", position = 2900, separatorBefore = 2850)
    ,@ActionReference(path = "Shortcuts", name = "A-D-LEFT")
})
@Messages("CTL_TabSwitchLeft=TabSwitch Left")
public final class TabSwitchLeft implements ActionListener {

    Lookup.Result<TabSwitch> result = Lookup.getDefault().lookupResult(TabSwitch.class);
    private Collection<? extends TabSwitch> services = result.allInstances();

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
        for (TabSwitch service : services) {
            service.activateLeftTab();
        }
    }
}
