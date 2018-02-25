## Getting Started
This is mostly a summary, plus a few additions, of the very nice work done by Nick Tyler (github.com/tylern4).
Pull Nick's image of clas6db:
```
docker pull tylern4/clas6db
```
Clone `docker-clas6` (forked from Nick), download the requirements, and build the clas6 image:
```
git clone https://github.com/naharrison/docker-clas6.git
cd docker-clas6/clas6
svn co --username nathanh https://jlabsvn.jlab.org/svnroot/clas/trunk/ clas-software
docker build -t clas6:test .
```
Run the image with:
```
docker run -v`pwd`:/root/data -it clas6:test
```
The `-v` option mounts a volume (your current working directory) to the running docker image (at /root/data/).

## More stuff to try
* In the same `docker-clas6` repository, see `docker-clas6/clasdb` for documentation on running a docker container with clasdb.
* Copy spgen from jlab farm (originally from Stepan): /home/nathanh/spgen
* Copy clasDIS from jlab farm from either:
	* /home/nathanh/clasDIS
	* /group/clas/builds/centos65/trunk/simulation/generators/clasDIS
	* /group/clas/builds/centos7/trunk/simulation/generators/clasDIS <-- preferred
* Get gpp_2008
* Rebuild clas6 image with: vim, tcsh, ...
