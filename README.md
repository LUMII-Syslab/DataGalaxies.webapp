# DataGalaxies.webapp
DataGalaxies app for webAppOS

## Installation

To install the DataGalaxies app for webAppOS, just clone its git repository into the "apps" subdirectory.

```bash
cd webAppOS/dist/apps
git clone https://github.com/LUMII-Syslab/DataGalaxies.webapp.git
```

Do not forget to restart webAppOS afterwards.

## Sample projects

To try our sample projects, just copy them from the "apps/DataGalaxies.webapp/sample projects" directory
into your user's home directory within webAppOS (e.g., into "home/webappos" for the
default user "webappos").

```bash
cd webAppOS/dist
mkdir -p home/webappos
cp apps/DataGalaxies.webapp/sample\ projects/${html`*`} home/webappos/
```

Then after launching the DataGalaxies app (e.g., from the webAppOS desktop),
click "Browse" and choose the desired .datagalaxy project.
