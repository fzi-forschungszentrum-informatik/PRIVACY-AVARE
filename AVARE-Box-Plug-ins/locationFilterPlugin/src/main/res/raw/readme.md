The .poly files in this folder are taken from http://download.geofabrik.de/
Geofabrik takes its data from the OpenStreetMap project (http://www.openstreetmap.org/)

The command used to download the .poly files is:

URL="http://download.geofabrik.de/europe.html"
wget -nd -np -r --level=2 -A poly -R europe.poly,alps.poly,british-isles.poly,dach.poly $URL