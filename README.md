# TabSwitch
TabSwitch is developed for the NetBeans&copy; IDE.
TabSwitch allows you to switch the editor-tabs in the NetBeans&copy; IDE the way Google Chrome&copy; and XtraFinder&copy; do.
One could say NetBeans already provides this kind of functionality but in distinction to the build-in feature of the NetBeans IDE, TabSwitch does not loose the context of the project.
So if you use for example the tab-row-per-project-feature TabSwitch will not suddenly jump between different tab-rows like the build-in NetBeans IDE feature does.

![TabSwitch with Shortcuts](https://raw.github.com/Yserz/TabSwitch/master/doc/TabSwitchTabs.gif)

![TabSwitch Menu Actions](https://raw.github.com/Yserz/TabSwitch/master/doc/TabSwitchMenu.png)


##### To switch to the left tab from the currently active one simply use 

- <code>⌥ + ⌘ + &#8592; </code> for Mac OS X,
- <code>Alt + Ctrl + &#8592; </code> for Windows & Linux or
- Click on <code>Navigate -> TabSwitch Left</code>.

##### To switch to the right tab simply use 

- <code>⌥ + ⌘ + &#10141; </code> for Mac OS X,
- <code>Alt + Ctrl + &#10141; </code> for Windows & Linux or
- Click on <code>Navigate -> TabSwitch Right</code>.

## Download
You can download the snapshot-version from <a href="https://bitbucket.org/api/1.0/repositories/Yserz/ownmavenrepo/raw/HEAD/de/yser/TabSwitcher/1.0-SNAPSHOT/TabSwitcher-1.0-20140420.123832-6.nbm" type="application/x-download">here</a>.

## Issue Management
If you find a bug or have an idea for a feature, feel free to post it [here](https://github.com/Yserz/TabSwitch/issues).

## Making a Release
- [Maven Release Plugin: The Final Nail in the Coffin](http://axelfontaine.com/blog/final-nail.html)

1. Checking the software out: <code>git clone</code>
2. Giving it a version: <code>mvn versions:set</code>
3. Building, testing, packaging and deploying it to an artifact repository: <code>mvn deploy</code>
4. Tagging this state in SCM: <code>mvn scm:tag</code>

## Sources

### Netbeans Platform Sources
- [NetBeans Platform Learning Trail](https://netbeans.org/features/platform/all-docs.html)
- [DZone Refcardz Netbeans Platform 7.0](http://refcardz.dzone.com/refcardz/netbeans-platform-70)
- [NetBeans Platform Teaching Resources](https://edu.netbeans.org/contrib/slides/netbeans-platform/)
- [NetBeans Platform 8.0 JavaDoc KeyStroke](http://bits.netbeans.org/dev/javadoc/org-openide-util/org/openide/util/Utilities.html#keyToString(javax.swing.KeyStroke))
- [NetBeans Platform Maven NBM Plugin](http://mojo.codehaus.org/nbm-maven/nbm-maven-plugin/nbm-mojo.html)

### Maven Release Sources
- [Maven Release Plugin: The Final Nail in the Coffin](http://axelfontaine.com/blog/final-nail.html)

### License Sources
- [Open-Source-Lizenzen](http://www.heise.de/open/artikel/Open-Source-Lizenzen-221957.html)

### Markdown Sources
- [Markdown: Syntax](http://daringfireball.net/projects/markdown/syntax)
- [HTML Codes](http://character-code.com/arrows-html-codes.php)
